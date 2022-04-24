package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Locale;
import java.util.Map;

import org.thymeleaf.spring4.SpringTemplateEngine;

@Service
public class MailingServiceImpl implements MailingService {

    private final Session session;
    private final SpringTemplateEngine templateEngine;

    @Autowired
    private Environment environment;

    @Autowired
    public MailingServiceImpl(Session session, SpringTemplateEngine templateEngine) {
        this.session = session;
        this.templateEngine = templateEngine;
    }

    @Async
    @Override
    public void sendEmail(User sender, String receiverAddress, String subject, String template, Map<String, Object> mailData,Locale locale) {
        final Context ctx = new Context(locale);
        ctx.setVariable("senderName", sender.isBand() ? sender.getName() : (sender.getName() + " " + sender.getSurname()));
        ctx.setVariable("email", sender.getEmail());
        for (String data : mailData.keySet()) {
            ctx.setVariable(data, mailData.get(data));
        }
        sendThymeLeafEmail(sender, receiverAddress, subject, template, ctx);
    }

    private void sendThymeLeafEmail(User sender, String receiverAddress, String subject, String template, Context ctx) {
        final String htmlContent = this.templateEngine.process(template, ctx);
        sendMessage(htmlContent, receiverAddress, subject);
    }

    private void sendMessage(String htmlContent, String receiverAddress, String subject) {
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(environment.getRequiredProperty("mail.username")));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiverAddress));
            message.setSubject(subject);
            message.setContent(htmlContent, "text/html;charset=\"UTF-8\"");
            Transport.send(message);
        } catch (MessagingException mex) {
            throw new RuntimeException(mex.getMessage());
        }
    }

}






