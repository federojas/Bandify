package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.User;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;
import java.util.List;

public class ArtistEditForm extends UserEditForm {

    @NotBlank
    @Size(max = 50)
    private String surname;

    @Override
    public boolean isBand() {
        return false;
    }

    @Override
    public void initialize(User user, List<String> musicGenres, List<String> bandRoles) {
        super.initialize(user,musicGenres,bandRoles);
        this.surname = user.getSurname();
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

}
