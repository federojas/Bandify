package ar.edu.itba.paw.model.exceptions;

public class InvalidApplicationStateException extends RuntimeException {

    public InvalidApplicationStateException() {
        super("Invalid application state specified");
    }
}