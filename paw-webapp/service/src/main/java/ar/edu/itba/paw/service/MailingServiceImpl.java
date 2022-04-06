package ar.edu.itba.paw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Service
public class MailingServiceImpl implements MailingService{

    private final Session session;

    @Autowired
    public MailingServiceImpl(Session session) {
        this.session = session;
    }

    @Override
    public void sendAuditionEmail(String receiverAddress, String subject, String contactInfo, String content) {
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("bandifypaw@gmail.com"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiverAddress));
            message.setSubject(subject);
            message.setText("Email del aspirante: " + contactInfo + "\n\n" + content);
            Transport.send(message);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}

