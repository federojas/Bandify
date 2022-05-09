package ar.edu.itba.paw.webapp.form.constraints.annotations;

import ar.edu.itba.paw.webapp.form.constraints.validators.DuplicatedEmailValidator;
import ar.edu.itba.paw.webapp.form.constraints.validators.UserExistsValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = { UserExistsValidator.class})
@Target({ METHOD, FIELD, PARAMETER })
@Retention(RUNTIME)
@Documented
public @interface UserExists {

    String message() default "User doesn't exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}