package ar.edu.itba.paw.model.exceptions;

public class InvalidGenreException extends RuntimeException {

    public InvalidGenreException() {
        super("A specified genre is invalid");
    }
}
