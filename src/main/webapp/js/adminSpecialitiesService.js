function loadSpecialitiesView(){
    isUserAuthenticatedViews(templateFiles.adminSpecialitiesPage);
}

function loadAdminAddSpecialityForm(){
    isUserAuthenticatedViews(templateFiles.adminAddSpecialityForm);
}


function loadAdminUpdateSpecialityForm(){
    var selectedValues = $("input:checkbox:checked.selectedSpecialities").map(function(){
        return this.value;
    }).get();
    if(selectedValues.length > 1 || selectedValues.length == 0){
        $("#generalDialogMessage").text("Трябва да изберете точно една специалност!");
        $("#generalDialog").dialog("open");
        return;
    }
    selectedSpecialityValue = selectedValues[0];    
    isUserAuthenticatedViews(templateFiles.adminUpdateSpecialityForm);
}