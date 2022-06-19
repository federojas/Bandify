package ar.edu.itba.paw.model.exceptions;

public class UserNotAvailableException extends RuntimeException {
    public UserNotAvailableException() {
        super("The specified user is not available");
    }
}
