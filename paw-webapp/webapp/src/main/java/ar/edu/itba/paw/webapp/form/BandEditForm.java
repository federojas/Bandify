package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.model.SocialMedia;
import ar.edu.itba.paw.model.User;

import java.util.List;
import java.util.Set;

public class BandEditForm extends UserEditForm {

    @Override
    public boolean isBand() {
        return true;
    }

    @Override
    public void initialize(User user, List<String> musicGenres, List<String> bandRoles, Set<SocialMedia> socialMediaSet, String location) {
        super.initialize(user,musicGenres,bandRoles,socialMediaSet, location);
    }

}
