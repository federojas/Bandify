package ar.edu.itba.paw.model.exceptions;

public class UserNotInBandException extends RuntimeException{
    public UserNotInBandException() {
        super("An action on a band where the current user does not belong was attempted");
    }
}
