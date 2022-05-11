package ar.edu.itba.paw.webapp.form.constraints.annotations;

import ar.edu.itba.paw.webapp.form.constraints.validators.FieldsMatchValidator;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = { FieldsMatchValidator.class})
@Target({TYPE,ANNOTATION_TYPE })
@Retention(RUNTIME)
public @interface   FieldsMatch {

    String message() default "Fields don't match";

    Class<?>[] groups() default {};

    String field();

    String secondField();

    Class<? extends Payload>[] payload() default {};

    @Target({TYPE, ANNOTATION_TYPE})
    @Retention(RUNTIME)
    @interface List {
        FieldsMatch[] value();
    }

}
