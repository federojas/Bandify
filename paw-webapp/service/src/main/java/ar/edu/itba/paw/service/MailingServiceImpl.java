package ar.edu.itba.paw.service;

import ar.edu.itba.paw.persistence.User;
import ar.edu.itba.paw.persistence.VerificationToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.thymeleaf.spring4.SpringTemplateEngine;

@Service
public class MailingServiceImpl implements MailingService {

    private final Session session;
    private final SpringTemplateEngine templateEngine;
    private final Environment environment;
    private final MessageSource messageSource;

    private static final Logger LOGGER = LoggerFactory.getLogger(MailingServiceImpl.class);

    @Autowired
    public MailingServiceImpl(final Session session, final SpringTemplateEngine templateEngine, final Environment environment
    , final MessageSource messageSource) {
        this.session = session;
        this.templateEngine = templateEngine;
        this.environment = environment;
        this.messageSource = messageSource;
    }

    @Async
    @Override
    public void sendApplicationEmail(User applicant, String receiverEmail, String message) {
        try {
            final String url = new URL("http", environment.getRequiredProperty("app.base.url"), "/paw-2022a-03/user/" + applicant.getId()).toString();
            Map<String, Object> mailData = new HashMap<>();
            Locale locale = LocaleContextHolder.getLocale();
            String subject = messageSource.getMessage("audition-application.subject",null,locale);
            mailData.put("content", message);
            mailData.put("goToBandifyURL", url);
            sendEmail(applicant, receiverEmail, subject, "audition-application", mailData,  locale);
        } catch (MalformedURLException e) {
            LOGGER.warn("Audition application email threw url exception");
        }
    }

    @Async
    @Override
    public void sendVerificationEmail(User user, VerificationToken token) {
        try {
            Locale locale = LocaleContextHolder.getLocale();
            final String url = new URL("http", environment.getRequiredProperty("app.base.url"), "/paw-2022a-03/verify?token=" + token.getToken()).toString();
            String subject = messageSource.getMessage("verify-account.subject",null,locale);
            final Map<String, Object> mailData = new HashMap<>();
            mailData.put("confirmationURL", url);
            sendEmail(user, user.getEmail(), subject, "verify-account", mailData, locale);
        } catch (MalformedURLException e) {
            LOGGER.warn("Register verification email threw url exception");
        }
    }

    @Async
    @Override
    public void sendResetPasswordEmail(User user, VerificationToken token) {
        try {
            Locale locale = LocaleContextHolder.getLocale();
            final String url = new URL("http", environment.getRequiredProperty("app.base.url"), "/paw-2022a-03/newPassword?token=" + token.getToken()).toString();
            final Map<String, Object> mailData = new HashMap<>();
            mailData.put("resetPasswordURL", url);
            String subject = messageSource.getMessage("reset-password.subject",null,locale);
            sendEmail(user, user.getEmail(), subject, "reset-password", mailData, locale);
        } catch (MalformedURLException e) {
            LOGGER.warn("Reset password email threw url exception");
        }

    }

    private void sendEmail(User sender, String receiverAddress, String subject, String template, Map<String, Object> mailData, Locale locale) {
        final Context ctx = new Context(locale);
        ctx.setVariable("senderName", sender.isBand() ? sender.getName() : (sender.getName() + " " + sender.getSurname()));
        ctx.setVariable("email", sender.getEmail());
        for (String data : mailData.keySet()) {
            ctx.setVariable(data, mailData.get(data));
        }
        sendThymeLeafEmail(receiverAddress, subject, template, ctx);
    }

    private void sendThymeLeafEmail(String receiverAddress, String subject, String template, Context ctx) {
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
            LOGGER.warn("Email sending threw messaging exception");
        }
    }

}






