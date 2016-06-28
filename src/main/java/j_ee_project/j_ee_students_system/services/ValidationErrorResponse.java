/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package j_ee_project.j_ee_students_system.services;

import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Lightning
 */
@XmlRootElement
public class ValidationErrorResponse {

    private List<String> validationErrors;

    public ValidationErrorResponse(List<String> errors) {
        this.validationErrors = errors;
    }

    public List<String> getValidationErrors() {
        return validationErrors;
    }

    
    

}
