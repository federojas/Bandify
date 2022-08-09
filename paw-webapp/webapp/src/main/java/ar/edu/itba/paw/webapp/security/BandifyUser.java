package ar.edu.itba.paw.webapp.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import java.util.Collection;

public class BandifyUser extends User {

    private final boolean isEnabled;

    public BandifyUser(String username, String password,
                       Collection<? extends GrantedAuthority> authorities,
                       boolean isEnabled) {
        super(username, password, authorities);
        this.isEnabled = isEnabled;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

}
