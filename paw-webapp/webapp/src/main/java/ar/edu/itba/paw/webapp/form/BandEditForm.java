package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.model.User;

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
