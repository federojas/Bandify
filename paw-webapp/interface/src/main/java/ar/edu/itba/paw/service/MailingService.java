package ar.edu.itba.paw.service;

import ar.edu.itba.paw.Audition;
import ar.edu.itba.paw.User;
import ar.edu.itba.paw.VerificationToken;

public interface MailingService {

    void sendApplicationEmail(User applicant, String receiverEmail, String message);
    void sendVerificationEmail(User user, VerificationToken token);
    void sendResetPasswordEmail(User user, VerificationToken token);
    void sendApplicationAcceptedEmail(User band, Audition audition, String receiverEmail);
}
