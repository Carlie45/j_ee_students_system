function loadAssignmentSubmitForm(assignmentId) {
    isUserAuthenticatedViews(templateFiles.assignmentSubmitForm);
}
function loadAssignmentsByDisciplinePage(disciplineId) {
    $.ajax({
        url: 'services/assignments/get_assignments_by_discipline_id/' + disciplineId,
        type: 'GET',
        contentType: "application/json;charset=UTF-8",
        statusCode: {
            200: function (data) {
                var assignmentsDetails = {};
                var assignmentsList = '<ul id="assingmentsDiscipline' + disciplineId + '" class="list-group" style="display:none;">';
                for (var i = 0; i !== data.length; ++i) {
                    assignmentsDetails[data[i].id] = data[i];
                    assignmentsList += '<li class="list-group-item"><a class="assignmentLink" href="'
                            + data[i].id + '">' + data[i].title + '</a>' +
                            '<div id="assignmentDetails' + data[i].id + '"></div>'
                            + '</li>';
                }
                if (data.length === 0) {
                    assignmentsList += '<li class="list-group-item"><h5>Все още няма добавени домашни по тази дисциплина.</h5></li>';
                }
                assignmentsList += '</ul>';
                $("#disciplineId" + disciplineId).append(assignmentsList);
                $("#assingmentsDiscipline" + disciplineId).show("slow");                
                

                $(".assignmentLink").click(function (event) {
                    event.preventDefault();
                    var assignmentId = $(this).attr('href');
                    $("#pageDialogMessage").load(templateFiles.assignmentSubmitForm, function () {
                        var endSubmissionDate = $.datepicker.parseDate("dd.mm.yy", assignmentsDetails[assignmentId].endTime)
                        var nowDate = new Date();
                        if (nowDate.getTime() > endSubmissionDate.getTime()) {
                            $("#fileUploadButton").attr('disabled', 'disabled');
                            $("#fileupload").attr('disabled', 'disabled');
                        }
                        $("#assignmentTitle").text(assignmentsDetails[assignmentId].title);
                        $("#fileupload").attr('data-url', 'services/file_upload/' + assignmentId);
                        $("#endTime").text(assignmentsDetails[assignmentId].endTime + 'г.');
                        $("#attachedFile").html('<a href="services/file_upload/donwloadAssignmentAttachement/' + assignmentsDetails[assignmentId].attachedFile + '" style="color: #ffffff" class="btn btn-danger button_select_item" target="_blank"> <span class="glyphicon glyphicon-info-sign"></span>' + assignmentsDetails[assignmentId].attachedFile + ' </a>');
                        $("#assignmentDescription").text(assignmentsDetails[assignmentId].description);
                        $('#fileupload').fileupload({
                            progressall: function (e, data) {
                                var progress = parseInt(data.loaded / data.total * 100, 10);
                                $('#progress .bar').css(
                                        'width',
                                        progress + '%'
                                        );
                            },
                            done: function (e, data) {
                                var fileNname = JSON.parse(data.response().result);
                                $("#uploadedFile").val(fileNname.fileName);
                                $("#fileupload").attr("disabled", "disabled");
                                $("#fileUploadButton").attr("disabled", "disabled");
                                $("#fileuploadStatus").html("Файлът " + fileNname.fileName + " е успешно съхранен!");
                                $("#fileuploadStatus").show("slow");
                            }
                        });
                        $("#assignmentDetailsAndSolution").show("slow");
                        $("#pageDialog").dialog('option', 'title', assignmentsDetails[assignmentId].title);
                        $("#pageDialog").dialog('open');

                    });

                });
            }
        }
    });
}