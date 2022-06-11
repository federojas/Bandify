package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Audition;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.VerificationToken;

import java.util.Locale;

public interface MailingService {

    void sendApplicationEmail(User applicant, String receiverEmail, String message, Locale locale);
    void sendVerificationEmail(User user, VerificationToken token, Locale locale);
    void sendResetPasswordEmail(User user, VerificationToken token, Locale locale);
    void sendAddedToBandEmail(User band, String receiverEmail, Locale locale);
    void sendApplicationAcceptedEmail(User band, Audition audition, String receiverEmail, Locale locale);
}
