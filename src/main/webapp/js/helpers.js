function traverseObjectPropertiesAndPopulateForm(obj, formFieldsSet) {
    for (var property in obj) {
        if (obj.hasOwnProperty(property)) {
            if (typeof obj[property] == "object"){
                traverseObjectPropertiesAndPopulateForm(obj[property], formFieldsSet);
            } else{               
                if(formFieldsSet.hasOwnProperty(property)){
                    $("[name="+property+"]").val(obj[property]);                  
                }
            }
        }
    }
}

function generateFormFieldsSet(formFieldsArray){
    var fieldSet = {};
     for (var i = 0; i < formFieldsArray.length; ++i){
         fieldSet[$(formFieldsArray[i]).attr("name")] = "";
     }
     
     return fieldSet;
}
