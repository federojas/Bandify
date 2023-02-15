package ar.edu.itba.paw.model.exceptions;

public class NotAnArtistException extends RuntimeException {
    public NotAnArtistException() {
        super("Current user is not an artist");
    }
}

