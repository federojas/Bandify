package ar.edu.itba.paw.model.exceptions;

public class LocationNotFoundException extends RuntimeException{

    public LocationNotFoundException() {
        super("The specified location was not found");
    }
}
