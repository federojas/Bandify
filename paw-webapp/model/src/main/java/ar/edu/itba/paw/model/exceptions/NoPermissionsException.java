package ar.edu.itba.paw.model.exceptions;

public class NoPermissionsException extends RuntimeException {
    public NoPermissionsException() {
        super("No permissions for this action");
    }
}
