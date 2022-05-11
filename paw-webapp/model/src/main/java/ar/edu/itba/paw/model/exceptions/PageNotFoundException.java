package ar.edu.itba.paw.model.exceptions;


public class PageNotFoundException extends RuntimeException {
    public PageNotFoundException() {
        super("A specified page number was not found");
    }
}
