package ar.edu.itba.paw.service;

import ar.edu.itba.paw.User;

public interface AuthFacadeService {
    boolean isAuthenticated();
    User getCurrentUser();
}
