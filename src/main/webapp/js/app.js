var inAdminPanel = false;
var selectedSpecialityValue = null;
var selectedDisciplineValue = null;
var selectedAssignmentValue = null;

function DirectoryLocation() {
    this.includes = "includes/";
    this.adminPanel = this.includes + "adminPanel/";
    this.usersService = this.includes + "usersService/";
    this.assignmentsService = this.includes + "assignmentsService/";
}

var directoryLocation = new DirectoryLocation();

function TemplateFiles() {
    this.adminNavigation = directoryLocation.adminPanel + "adminNavigation.html";
    this.adminWelcomePage = directoryLocation.adminPanel + "adminWelcomePage.html";
    this.adminUsersPage = directoryLocation.adminPanel + "adminUsersPage.html";
    this.adminAddDegreeForm = directoryLocation.adminPanel + "addDegreeForm.html";
    this.adminAddUserForm = directoryLocation.adminPanel + "adminAddUserForm.html";
    this.adminAddStudentForm = directoryLocation.adminPanel + "adminAddStudentForm.html";
    this.adminAddLecturerForm = directoryLocation.adminPanel + "adminAddLecturerForm.html";
    this.adminAssignmentsPage = directoryLocation.adminPanel + "adminAssignmentsPage.html";
    this.adminAddAssignmentForm = directoryLocation.adminPanel + "adminAddAssignmentForm.html";    
    this.adminSpecialitiesPage = directoryLocation.adminPanel + "adminSpecialitiesPage.html";
    this.adminAddSpecialityForm = directoryLocation.adminPanel + "adminAddSpecialityForm.html";
    this.adminUpdateSpecialityForm = directoryLocation.adminPanel + "adminUpdateSpecialityForm.html";
    this.adminDisciplinesPage = directoryLocation.adminPanel + "adminDisciplinesPage.html";
    this.adminAddDisciplineForm = directoryLocation.adminPanel + "adminAddDisciplineForm.html";
    this.adminUpdateDisciplineForm = directoryLocation.adminPanel + "adminUpdateDisciplineForm.html";
    this.adminUpdateAssignmentForm = directoryLocation.adminPanel + "adminUpdateAssignmentForm.html";
    this.assignmentSubmitForm = directoryLocation.assignmentsService + "assignmentSubmitForm.html";
    this.assignmentsByDisciplinePage = directoryLocation.assignmentsService + "assignmentsByDisciplinePage.html";
}
;

var templateFiles = new TemplateFiles();

function isFunction(functionToCheck) {
    var getType = {};
    return functionToCheck && getType.toString.call(functionToCheck) === '[object Function]';
}

function executeAction(action) {
    action = window.location.hash.substr(1);
    if (isFunction(window[action])) {
        window[action]();
    }
}


$(document).ready(function () {
    $('body').on('click', '.hashLink', function (event) {

        var actionAndParams = $(this).attr("href");
        actionAndParams = actionAndParams.substr(1);
        actionAndParams = actionAndParams.split('/');
        if (isFunction(window[actionAndParams[0]])) {
            window[actionAndParams].apply(null, actionAndParams.slice(1));
        }
    });
});


// Builds the HTML Table out of jsonTableData.
function buildHtmlTable(jsonTableData, selector) {
    var columns = addAllColumnHeaders(jsonTableData, selector);

    for (var i = 0; i < jsonTableData.length; i++) {
        var row$ = $('<tr/>');
        for (var colIndex = 0; colIndex < columns.length; colIndex++) {
            var cellValue = jsonTableData[i][columns[colIndex]];

            if (cellValue == null) {
                cellValue = "";
            }

            row$.append($('<td/>').html(cellValue));
        }
        $(selector).append(row$);
    }
}

// Adds a header row to the table and returns the set of columns.
// Need to do union of keys from all records as some records may not contain
// all records
function addAllColumnHeaders(jsonTableData, selector) {
    var columnSet = [];
    var headerTr$ = $('<tr/>');

    for (var i = 0; i < jsonTableData.length; i++) {
        var rowHash = jsonTableData[i];
        for (var key in rowHash) {
            if ($.inArray(key, columnSet) == -1) {
                columnSet.push(key);
                headerTr$.append($('<th/>').html(key));
            }
        }
    }
    $(selector).append(headerTr$);

    return columnSet;
}

$(document).ready(function(){
    $('body').on('click', '#selectAllButton', function(){
        $("input:checkbox").attr('checked','checked');
    })
});