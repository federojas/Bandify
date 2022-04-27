package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.TokenType;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.VerificationToken;
import ar.edu.itba.paw.persistence.VerificationTokenDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class VerificationTokenServiceImpl implements VerificationTokenService {

    private final VerificationTokenDao verificationTokenDao;
    private final MailingService mailingService;
    private final MessageSource messageSource;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private final Environment environment;

    @Autowired
    public VerificationTokenServiceImpl(final VerificationTokenDao verificationTokenDao,
                                        final MailingService mailingService,
                                        final MessageSource messageSource,
                                        final Environment environment) {
        this.verificationTokenDao = verificationTokenDao;
        this.mailingService = mailingService;
        this.messageSource = messageSource;
        this.environment = environment;
    }

    @Override
    public Optional<VerificationToken> getToken(String token) {
        return verificationTokenDao.getToken(token);
    }


    @Override
    public void deleteTokenByUserId(long userId, TokenType type) {
        verificationTokenDao.deleteTokenByUserId(userId, type);
    }

    @Override
    public Long validateToken(String token, TokenType type) {
        Optional<VerificationToken> t = getToken(token);

        if(!t.isPresent()) {
            return null;
        }

        deleteTokenByUserId(t.get().getUserId(), type);
        if(LocalDateTime.now().isAfter(t.get().getExpiryDate())) {
            return null;
        }
        return t.get().getUserId();
    }

    @Override
    public boolean isValid(String token) {
        Optional<VerificationToken> t = getToken(token);
        return t.isPresent();
    }


    @Override
    public VerificationToken generate(long userId, TokenType type) {
        final String token = UUID.randomUUID().toString();
        return verificationTokenDao.createToken(userId, token, VerificationToken.getNewExpiryDate(), type);
    }

    @Override
    public void sendVerifyEmail(User user, VerificationToken token) {
        try {
            Locale locale = LocaleContextHolder.getLocale();
            final String url = new URL("http", environment.getRequiredProperty("app.base.url"), "/paw-2022a-03/verify?token=" + token.getToken()).toString();
            final Map<String, Object> mailData = new HashMap<>();
            mailData.put("confirmationURL", url);
            mailingService.sendEmail(user, user.getEmail(), messageSource.getMessage("verify-account.subject",null,locale), "verify-account", mailData, locale);
        } catch (MessagingException | MalformedURLException e) {
            LOGGER.warn("Register verification email failed");
        }
    }

    @Override
    public void sendResetEmail(User user) {
        VerificationToken token = generate(user.getId(),TokenType.RESET);
        try {
            Locale locale = LocaleContextHolder.getLocale();
            final String url = new URL("http", environment.getRequiredProperty("app.base.url"), "/paw-2022a-03/newPassword?token=" + token.getToken()).toString();
            final Map<String, Object> mailData = new HashMap<>();
            mailData.put("resetPasswordURL", url);
            mailingService.sendEmail(user, user.getEmail(), messageSource.getMessage("reset-password.subject",null,locale), "reset-password", mailData, locale);
        } catch (MessagingException | MalformedURLException e) {
            LOGGER.warn("Reset password email failed");
        }
    }
}
