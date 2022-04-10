package ar.edu.itba.paw.model.exceptions;

public class GenreNotFoundException extends RuntimeException {

    public GenreNotFoundException() {
        super("A specified music genre was not found");
    }
}
