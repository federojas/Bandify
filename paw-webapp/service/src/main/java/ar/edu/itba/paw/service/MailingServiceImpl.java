package ar.edu.itba.paw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.thymeleaf.spring4.SpringTemplateEngine;

@Service
public class MailingServiceImpl implements MailingService {

    private final Session session;
    private final SpringTemplateEngine templateEngine;

    @Autowired
    public MailingServiceImpl(Session session, SpringTemplateEngine templateEngine) {
        this.session = session;
        this.templateEngine = templateEngine;
    }

    @Async
    @Override
    public void sendAuditionEmail(String receiverAddress, String senderName, String email, String content, Locale locale) {

        Map<String, Object> variables = new HashMap<>();
        variables.put("senderName", senderName);
        variables.put("email", email);
        variables.put("content", content);
        sendThymeLeafAuditionEmail(variables, locale, receiverAddress);
    }

    private void sendThymeLeafAuditionEmail(Map<String, Object> variables, Locale locale, String receiverAddress) {
        final Context ctx = new Context(locale);
        ctx.setVariable("senderName", variables.get("senderName"));
        ctx.setVariable("email", variables.get("email"));
        ctx.setVariable("content", variables.get("content"));

        final String htmlContent = this.templateEngine.process("audition-application.html", ctx);
        sendMessage(htmlContent, receiverAddress, "Nuevo aplicante para audición");
    }


    private void sendMessage(String htmlContent, String receiverAddress, String subject) {
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("bandifypaw@gmail.com"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiverAddress));
            message.setSubject(subject);

            message.setContent(htmlContent, "text/html;charset=\"UTF-8\"");
            Transport.send(message);
        } catch (MessagingException mex) {
            throw new RuntimeException(mex.getMessage());
        }
    }
}






