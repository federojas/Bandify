package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.persistence.User;

import javax.validation.constraints.Size;
import java.util.List;

public class BandEditForm extends UserEditForm {

    @Override
    public boolean isBand() {
        return true;
    }

    @Override
    public void initialize(User user, List<String> musicGenres, List<String> bandRoles) {
        super.initialize(user,musicGenres,bandRoles);
    }

}
