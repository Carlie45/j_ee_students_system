package j_ee_project.j_ee_students_system.security.validation;

import j_ee_project.j_ee_students_system.entities.User;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;

/**
 *
 * @author Lightning
 */
@Constraint(validatedBy = {ValidUsername.Validator.class})
@Documented
@Target({ElementType.METHOD,
    ElementType.FIELD,
    ElementType.ANNOTATION_TYPE,
    ElementType.CONSTRUCTOR,
    ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidUsername {

    String message() default "{j_ee_project.j_ee_students_system.security.validation.ValidUsername}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
    
    public class Validator implements ConstraintValidator<ValidUsername, String>{

        @Override
        public void initialize(final ValidUsername constraintAnnotation) {
            
        }

        @Override
        public boolean isValid(final String value, final ConstraintValidatorContext context) {
            int length = value.length();           
            return length >= User.MIN_USERNAME_SIZE && length <= User.MAX_USERNAME_SIZE && java.util.regex.Pattern.matches("^([a-z0-9_])+", value);
        }
        
    }
  
    
}
