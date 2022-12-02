package ar.edu.itba.paw.webapp.security.services;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.service.UserService;
import ar.edu.itba.paw.webapp.security.BandifyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.Collection;

@Component
public class BandifyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService us;

    @Autowired
    public BandifyUserDetailsService(final UserService us) {
        this.us = us;
    }

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        final User user = us.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("No user with email " + email));
        final Collection<GrantedAuthority> authorities = new ArrayList<>();
        if(user.isBand())
            authorities.add(new SimpleGrantedAuthority("BAND"));
        else
            authorities.add(new SimpleGrantedAuthority("ARTIST"));
        return new BandifyUser(email, user.getPassword(), authorities, user.isEnabled());
    }

}
