package ar.edu.itba.paw.model.exceptions;

public class SocialMediaNotFoundException extends RuntimeException {
    public SocialMediaNotFoundException() {
        super("A specified social media was not found");
    }
}
