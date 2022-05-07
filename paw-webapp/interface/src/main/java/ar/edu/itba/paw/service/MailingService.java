package ar.edu.itba.paw.service;

import ar.edu.itba.paw.persistence.User;
import ar.edu.itba.paw.persistence.VerificationToken;

import javax.mail.MessagingException;
import java.util.Locale;
import java.util.Map;

public interface MailingService {

    void sendApplicationEmail(User applicant, String receiverEmail, String message);
    void sendVerificationEmail(User user, VerificationToken token);
    void sendResetPasswordEmail(User user, VerificationToken token);
}
