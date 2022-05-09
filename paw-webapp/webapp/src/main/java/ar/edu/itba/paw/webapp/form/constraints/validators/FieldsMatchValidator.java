package ar.edu.itba.paw.webapp.form.constraints.validators;

import ar.edu.itba.paw.webapp.form.constraints.annotations.FieldsMatch;
import org.springframework.beans.BeanWrapperImpl;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class FieldsMatchValidator implements ConstraintValidator<FieldsMatch,Object> {
    private String field;
    private String secondField;

    @Override
    public void initialize(FieldsMatch constraint) {
        this.field = constraint.field();
        this.secondField = constraint.secondField();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        Object fieldValue = new BeanWrapperImpl(value).getPropertyValue(field);
        Object secondFieldValue = new BeanWrapperImpl(value).getPropertyValue(secondField);
        boolean toReturn = Objects.equals(fieldValue,secondFieldValue);
        if(!toReturn) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate()).
                    addNode(secondField).addConstraintViolation();
        }
        return toReturn;
    }


}
