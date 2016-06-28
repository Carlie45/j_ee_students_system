function loadDisciplinesView(){
    isUserAuthenticatedViews(templateFiles.adminDisciplinesPage);
}

function loadAdminAddDisciplineForm(){
    isUserAuthenticatedViews(templateFiles.adminAddDisciplineForm);
}


function loadAdminUpdateDisciplineForm(){
    selectedDisciplineValue = $('[name=selectedDiscipline]:checked').val();
    isUserAuthenticatedViews(templateFiles.adminUpdateDisciplineForm);
}