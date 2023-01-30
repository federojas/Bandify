package ar.edu.itba.paw.model.exceptions;

public class BandsCannotApplyException extends RuntimeException{
    public BandsCannotApplyException() {
        super("Bands cannot apply to auditions");
    }
}
