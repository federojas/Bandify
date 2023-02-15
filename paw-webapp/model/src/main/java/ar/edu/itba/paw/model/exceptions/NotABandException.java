package ar.edu.itba.paw.model.exceptions;

public class NotABandException extends RuntimeException {
    public NotABandException() {
        super("Current user is not a band");
    }
}

