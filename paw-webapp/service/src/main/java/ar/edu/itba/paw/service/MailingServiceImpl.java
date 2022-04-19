package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
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
    private final UserService userService;

    @Autowired
    Environment environment;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    public MailingServiceImpl(Session session, SpringTemplateEngine templateEngine, UserService userService) {
        this.session = session;
        this.templateEngine = templateEngine;
        this.userService = userService;
    }

    @Async
    @Override
    public void sendAuditionEmail(String receiverAddress, User user , String content, Locale locale) {
        User receiver = getUser(receiverAddress);
        Map<String, Object> variables = new HashMap<>();
        String fullname = user.getName() + user.getSurname();
        variables.put("senderName", fullname);
        variables.put("email", user.getEmail());
        variables.put("content", content);
        sendThymeLeafAuditionEmail(variables, locale, receiverAddress);
    }

    @Async
    @Override
    public void sendRecoverPasswordEmail(String receiverAddress, String token, Locale locale) {
        User receiver = getUser(receiverAddress);
        Map<String, Object> variables = new HashMap<>();
        variables.put("token", token);
        sendThymeLeafRecoverPasswordEmail(variables, locale, receiverAddress);
    }

    private User getUser(String email) throws UserNotFoundException {
        return userService.findByEmail(email).orElseThrow(UserNotFoundException::new);
    }

    private void sendThymeLeafRecoverPasswordEmail(Map<String, Object> variables, Locale locale, String receiverAddress) {
        final Context ctx = new Context(locale);
        ctx.setVariable("token", variables.get("token"));

        final String htmlContent = this.templateEngine.process("reset-password.html", ctx);
        sendMessage(htmlContent, receiverAddress, messageSource.getMessage("audition-application.subject",null,locale).toString() );
    }

    private void sendThymeLeafAuditionEmail(Map<String, Object> variables, Locale locale, String receiverAddress) {
        final Context ctx = new Context(locale);
        ctx.setVariable("senderName", variables.get("senderName"));
        ctx.setVariable("email", variables.get("email"));
        ctx.setVariable("content", variables.get("content"));

        final String htmlContent = this.templateEngine.process("audition-application.html", ctx);
        sendMessage(htmlContent, receiverAddress, messageSource.getMessage("reset-password.subject",null,locale).toString() );
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






