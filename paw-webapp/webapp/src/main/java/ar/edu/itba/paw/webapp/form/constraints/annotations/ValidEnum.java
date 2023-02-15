package ar.edu.itba.paw.webapp.form.constraints.annotations;

import ar.edu.itba.paw.webapp.form.constraints.validators.EnumValidator;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.FIELD})
@Constraint(validatedBy = EnumValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEnum {

    Class<? extends Enum<?>> enumClass();

    String message() default "Invalid enum value";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
