package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.VerificationToken;
import ar.edu.itba.paw.persistence.UserDao;
import ar.edu.itba.paw.persistence.VerificationTokenDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenDao verificationTokenDao;
    private final MailingService mailingService;

    @Autowired
    private Environment environment;

    @Autowired
    private MessageSource messageSource;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    public UserServiceImpl(final UserDao userDao, final PasswordEncoder passwordEncoder, final VerificationTokenDao verificationTokenDao, MailingService mailingService) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.verificationTokenDao = verificationTokenDao;
        this.mailingService = mailingService;
    }

    @Override
    public Optional<User> getUserById(long id) {
        return userDao.getUserById(id);
    }

    @Override
    public User create(User.UserBuilder userBuilder) {
        userBuilder.password(passwordEncoder.encode(userBuilder.getPassword()));
        User user = userDao.create(userBuilder);
        final VerificationToken token = generateVerificationToken(user.getId());
        sendVerificationTokenEmail(user, token);
        return user;
    }

    private void sendVerificationTokenEmail(User user, VerificationToken token) {
        try {
            Locale locale = LocaleContextHolder.getLocale();
            final String url = new URL("http", environment.getRequiredProperty("app.base.url"), "/paw-2022a-03/verifyAccount?token=" + token.getToken()).toString();
            final Map<String, Object> mailData = new HashMap<>();
            mailData.put("confirmationURL", url);
            mailingService.sendEmail(user, user.getEmail(), messageSource.getMessage("verify-account.subject",null,locale).toString(), "verify-account", mailData, locale);
        } catch (MessagingException | MalformedURLException e) {
            LOGGER.warn("Register verification email failed");
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userDao.findByEmail(email);
    }



    private VerificationToken generateVerificationToken(long userId) {
        final String token = UUID.randomUUID().toString();
        return verificationTokenDao.createToken(userId, token, VerificationToken.getNewExpiryDate());
    }
}
