package ar.edu.itba.paw.model.exceptions;

public class UserAlreadyVerifiedException extends RuntimeException {
    public UserAlreadyVerifiedException() {
        super("The specified user is already verified");
    }
}

