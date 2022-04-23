package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.User;

import javax.mail.MessagingException;
import java.util.Locale;
import java.util.Map;

public interface MailingService {

    void sendEmail(User sender, String receiverAddress, String subject, String template, Map<String, Object> mailData,Locale locale) throws MessagingException;

}
