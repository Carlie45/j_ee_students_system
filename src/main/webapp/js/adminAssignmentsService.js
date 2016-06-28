function loadAssignmentsView(){
    isUserAuthenticatedViews(templateFiles.adminAssignmentsPage);
}


function loadAdminAddAssignmentForm(){
    isUserAuthenticatedViews(templateFiles.adminAddAssignmentForm);
}

function loadAdminUpdateAssignmentsForm(){
    selectedAssignmentValue = $('[name=selectedAssignment]:checked').val();
    isUserAuthenticatedViews(templateFiles.adminUpdateAssignmentForm);
}