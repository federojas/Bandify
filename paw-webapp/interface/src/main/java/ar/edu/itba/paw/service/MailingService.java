package ar.edu.itba.paw.service;

import javax.mail.MessagingException;
import java.util.Locale;

public interface MailingService {

    public void sendAuditionEmail(String receiverAddress, String senderName,
                                  String email, String content, Locale locale) throws MessagingException;

}
