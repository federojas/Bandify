package ar.edu.itba.paw.webapp.form;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

public class UserBandForm extends UserForm {
    @Override
    public boolean isBand() {
        return true;
    }
}
