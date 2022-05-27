package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.User;

public interface AuthFacadeService {
    boolean isAuthenticated();
    User getCurrentUser();
}
