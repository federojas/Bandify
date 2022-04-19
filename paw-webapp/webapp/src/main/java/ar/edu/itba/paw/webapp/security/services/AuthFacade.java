package ar.edu.itba.paw.webapp.security.services;

import ar.edu.itba.paw.model.User;

public interface AuthFacade {
    public User getCurrentUser();
}
