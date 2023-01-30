package ar.edu.itba.paw.model.exceptions;

public class BandCannotApplyException extends RuntimeException{
    public BandCannotApplyException() {
        super("A band cannot apply to an audition");
    }
}
