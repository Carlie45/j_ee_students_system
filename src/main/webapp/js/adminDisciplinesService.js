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

function loadAdminDeleteSelectedDiscipline() {
    selectedDisciplineValue = $('[name=selectedDiscipline]:checked').val();
    $.ajax({
       url: 'services/disciplines/delete_discipline/' + selectedDisciplineValue,
       type: 'POST',
       success: function() {
           var disciplineId = "#discipline" + selectedDisciplineValue;
           $(disciplineId).hide();
       }
    });
}