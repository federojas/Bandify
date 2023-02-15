package ar.edu.itba.paw.model.exceptions;

public class UserAlreadyInBandException extends RuntimeException {
    public UserAlreadyInBandException() {
        super("User already in band");
    }
}
