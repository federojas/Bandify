package ar.edu.itba.paw.model.exceptions;

public class ApplicationNotFoundException extends RuntimeException {
    public ApplicationNotFoundException() {
        super("A specified application was not found");
    }
}
