/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package j_ee_project.j_ee_students_system.services;

import j_ee_project.j_ee_students_system.entities.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author Lightning
 */
@Provider
public class BeanValidationExceptionHadler implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(final ConstraintViolationException exception) {
        Set<ConstraintViolation<?>> constraintViolations = exception.getConstraintViolations();
        List<String> errors = new ArrayList(constraintViolations.size());
        for (ConstraintViolation<?> constraintViolation : constraintViolations) {
            errors.add(constraintViolation.getMessage());
        }

        return Response
                // Define your own status.
                .status(400)
                // Put an instance of Viewable in the response so that jersey-mvc-jsp can handle it.
                .entity(new ValidationErrorResponse(errors))
                .build();
    }

}
