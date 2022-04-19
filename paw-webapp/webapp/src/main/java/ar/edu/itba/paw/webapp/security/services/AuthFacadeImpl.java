package ar.edu.itba.paw.webapp.security.services;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.webapp.security.BandifyUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthFacadeImpl implements AuthFacade {
    @Override
    public User getCurrentUser() {
        return ((BandifyUser) (SecurityContextHolder.getContext().getAuthentication().getPrincipal())).toUser();
    }
}

