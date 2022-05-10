package ar.edu.itba.paw.model.exceptions;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException() {
        super("A specified verification token is expired or non existent");
    }
}
