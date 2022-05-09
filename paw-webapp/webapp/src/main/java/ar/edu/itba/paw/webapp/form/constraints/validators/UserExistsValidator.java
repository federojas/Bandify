package ar.edu.itba.paw.webapp.form.constraints.validators;

import ar.edu.itba.paw.service.UserService;
import ar.edu.itba.paw.webapp.form.constraints.annotations.UserExists;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserExistsValidator implements ConstraintValidator<UserExists,String> {
    @Autowired
    private UserService userService;

    @Override
    public void initialize(UserExists userExists) {

    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return userService.findByEmail(s).isPresent();
    }
}
