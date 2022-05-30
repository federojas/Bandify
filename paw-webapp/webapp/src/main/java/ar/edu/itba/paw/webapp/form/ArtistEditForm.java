package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.model.SocialMedia;
import ar.edu.itba.paw.model.User;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

public class ArtistEditForm extends UserEditForm {

    @NotBlank
    @Size(max = 50)
    private String surname;

    @Override
    public boolean isBand() {
        return false;
    }

    @Override
    public void initialize(User user, List<String> musicGenres, List<String> bandRoles, Set<SocialMedia> socialMediaSet) {
        super.initialize(user,musicGenres,bandRoles,socialMediaSet);
        this.surname = user.getSurname();
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

}
