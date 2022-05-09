package ar.edu.itba.paw.webapp.form;

import org.hibernate.validator.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserArtistForm extends UserForm {
    @NotBlank
    @Size(max = 50)
    private String surname;

    @Override
    public boolean isBand() {
        return false;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
