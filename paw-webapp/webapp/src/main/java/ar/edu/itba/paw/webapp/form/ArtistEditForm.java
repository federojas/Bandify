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

    private boolean available;

    @Override
    public boolean isBand() {
        return false;
    }

    @Override
    public void initialize(User user, List<String> musicGenres, List<String> bandRoles, Set<SocialMedia> socialMediaSet, String location) {
        super.initialize(user,musicGenres,bandRoles,socialMediaSet, location);
        this.surname = user.getSurname();
        this.available = user.isAvailable();
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public boolean getAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
