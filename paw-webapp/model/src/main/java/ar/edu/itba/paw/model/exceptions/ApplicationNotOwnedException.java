package ar.edu.itba.paw.model.exceptions;

public class ApplicationNotOwnedException extends RuntimeException {
    public ApplicationNotOwnedException() {
        super("A specified application is not owned by the current user");
    }
}