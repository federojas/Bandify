package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.User;

import javax.mail.MessagingException;
import java.util.Locale;

public interface MailingService {

    public void sendAuditionEmail(String receiverAddress, User user , String content, Locale locale) throws MessagingException;

    public void sendRecoverPasswordEmail(String receiverAddress, String token, Locale locale) throws MessagingException;;
}
