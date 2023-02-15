package ar.edu.itba.paw.model.exceptions;

public class ProfileNotOwnedException extends RuntimeException {
    public ProfileNotOwnedException() {
        super("Specified user is not current logged in user");
    }
}