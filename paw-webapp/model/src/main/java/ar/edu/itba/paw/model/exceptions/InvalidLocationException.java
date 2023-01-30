package ar.edu.itba.paw.model.exceptions;

public class InvalidLocationException extends RuntimeException {

    public InvalidLocationException() {
        super("A specified location is invalid");
    }
}

