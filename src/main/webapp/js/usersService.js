var warningUIComponent = '<span class="glyphicon glyphicon-warning-sign" style="font-size: 18px;"></span> Моля поправете следните грешки:';
var succesUIComponent = '<span class="glyphicon glyphicon-ok" style="font-size: 18px;"></span> Промените бяха запазени успешно!';
var succesUIComponentIcon = '<span class="glyphicon glyphicon-ok" style="font-size: 18px;"></span>';
var systemUser = null;

function templateHideLoginView(loadTemplate, data, callbackName) {
    if (systemUser === null) {
        $("#V_profileInformation").show();
        $("#loginDialog").dialog("close");
    }
    systemUser = data;
    populateUserProfileView();

    $("#main_middle_section").hide().load(loadTemplate, function () {
        if (callbackName != undefined) {
            callbackName();
        }
        
        updateAuthorizedAccessTemplate();

    }).fadeIn('1000');
}

function initTemplateUserViews(mainSectionTemplate, callbackName) {
    $("#main_header").load("includes/templateHeader.html");

    $("#main_user_data_section").load("includes/usersService/authenticatedUserNavigation.html", function () {
        isUserAuthenticatedViews(mainSectionTemplate, callbackName);
    });

}

function isUserAuthenticatedViews(mainSectionTemplate, callbackName) {
    $(document).ready(function () {
        $.ajax({
            url: 'services/users/is_user_authenticated',
            type: "GET",
            contentType: "application/json;charset=UTF-8",
            statusCode: {
                401: function () {
                    systemUser = null;
                    $("#dialogData").load("includes/usersService/loginFormDialog.html", function () {
                        $("#loginDialog").dialog({
                            autoOpen: false,
                            modal: true,
                            width: '600px',
                            height: 'auto',
                            show: {
                                effect: "explode",
                                duration: 300
                            },
                            hide: {
                                effect: "explode",
                                duration: 500
                            }
                        });
                        $("#V_F_loginForm").submit(function (event) {
                            event.preventDefault();
                            authenticateUser(mainSectionTemplate, callbackName);
                        });

                        $("#loginDialog").dialog("open");
                        $("#V_B_triggerLoginDialog").show();
                        $("#V_B_triggerLoginDialog").click(function () {
                            $("#loginDialog").dialog("open");
                        });
                    });
                    $("#main_middle_section").load("includes/welcomeMessageNotLogged.html");

                },
                200: function (data) {
                    templateHideLoginView(mainSectionTemplate, data, callbackName);
                }
            }
        });
    });
}



function populateUserProfileView() {
    if (systemUser !== null) {
        user = systemUser;
        $("#V_A_username").text(user.username + " (" + user.personName.firstName + " " + user.personName.lastName + ")");
        $("#V_A_username").attr('href', 'services/users/get_information/' + user.id);
    }
}

function updateAuthorizedAccessTemplate() {
    $('[id^="AUTH-"]').each(function () {
        var rightName = $(this).attr('id');
        rightName = rightName.substr(5);
        var selector = $(this);
        isUserAuthorized(rightName, function () {
            $(selector).show();
        },
        function(){
            $(selector).hide();
        });

    });
    
     $('[class^="AUTH-"]').each(function () {
        var rightName = $(this).attr('class');
        rightName = rightName.substr(5);
        rightName = rightName.split(" ")[0];
        var selector = $(this);
        isUserAuthorized(rightName, function () {
            $(selector).show();
        },
        function(){
            $(selector).hide();
        });

    });
}

function authenticateUser(mainSectionTemplate, callbackName) {
    $.ajax({
        url: 'services/users/login',
        type: 'POST',
        data: $("#V_F_loginForm").serializeArray(),
        statusCode: {
            200: function (data) {
                templateHideLoginView(mainSectionTemplate, data, callbackName);
            },
            400: function (data) {
                systemUser = null;
                var responseJSON = data.responseJSON;
                $("#V_F_errorsBox").html(buildListFromErrors(responseJSON.validationErrors));
                $("#V_F_errorsBox").show("slow");
            },
            401: function () {
                systemUser = null;
                 $("#V_F_errorsBox").html(warningUIComponent + '<br/>Грешно потребителско име и/или парола!');
                $("#V_F_errorsBox").show();

            }
        }
    });
}

function isUserAuthorized(rightName, authorizedAction, notAuthorizedAction) {
    $.ajax({
        url: 'services/users/is_user_authorized/' + rightName,
        type: "GET",
        contentType: "application/json;charset=UTF-8",
        statusCode: {
            200: function (data) {
                authorizedAction();
            },
            400: function (data) {
                notAuthorizedAction();
            },
            401: function (data) {
                notAuthorizedAction();
            },
            403: function (data) {
                notAuthorizedAction();
            }
        }
    });

}

function updateUserProile(mainSectionTemplate, callbackName) {
    $.ajax({
        url: 'services/users/update_profile',
        type: 'POST',
        data: $("#V_F_updateUserProfileForm").serializeArray(),
        statusCode: {
            200: function (data) {
                $("#V_F_ERRORS_BOX_updateUserProfileForm").attr("class", "alert alert-success");
                $("#V_F_ERRORS_BOX_updateUserProfileForm").html(succesUIComponent);
                $("#V_F_ERRORS_BOX_updateUserProfileForm").show("slow");
                $("html, body").stop().animate({scrollTop: 0}, '1000', 'swing');
            },
            400: function (data) {
                systemUser = null;
                var responseJSON = data.responseJSON;
                $("#V_F_ERRORS_BOX_updateUserProfileForm").html(buildListFromErrors(responseJSON.validationErrors));
                $("#V_F_ERRORS_BOX_updateUserProfileForm").show("slow");
                $("html, body").stop().animate({scrollTop: 0}, '1000', 'swing');
            },
            401: function () {
                isUserAuthenticatedViews(mainSectionTemplate, callbackName);

            }
        }
    });
}

function logoutUser() {
    $.ajax({
        url: 'services/users/logout',
        type: 'GET',
        contentType: "application/json;charset=UTF-8",
        data: $("#V_F_loginForm").serializeArray(),
        success: function () {
            location.reload();
        }
    });

}


function fillUpdateUserProfileForm() {
    if (systemUser !== null) {
        var formInputsSet = generateFormFieldsSet($("#V_F_updateUserProfileForm").find(":input"));
        traverseObjectPropertiesAndPopulateForm(systemUser, formInputsSet);
        $("#V_F_updateUserProfileForm").submit(function (event) {
            event.preventDefault();
            updateUserProile();
        });
    }
}

function loadUpdateUserProfileForm() {
    if (inAdminPanel) {
        window.location.href = "index.html#loadUpdateUserProfileForm";
    } else {
        isUserAuthenticatedViews("includes/usersService/updateUserProfileForm.html", fillUpdateUserProfileForm);

    }
}