package ar.edu.itba.paw.service;

import javax.mail.MessagingException;

public interface MailingService {

    void sendAuditionEmail(String receiverAddress, String subject, String contactInfo, String content) throws MessagingException;

}
