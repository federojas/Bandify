package ar.edu.itba.paw.webapp.form.constraints.validators;

import ar.edu.itba.paw.service.UserService;
import ar.edu.itba.paw.webapp.form.constraints.annotations.NotDuplicatedEmail;
import org.springframework.beans.factory.annotation.Autowired;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DuplicatedEmailValidator implements ConstraintValidator<NotDuplicatedEmail,String> {

    @Autowired
    private UserService userService;

    @Override
    public void initialize(NotDuplicatedEmail notDuplicatedEmail) {}

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return !userService.findByEmail(s).isPresent();
    }

}
