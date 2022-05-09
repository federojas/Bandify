package ar.edu.itba.paw.service;

import ar.edu.itba.paw.persistence.TokenType;
import ar.edu.itba.paw.persistence.User;
import ar.edu.itba.paw.persistence.VerificationToken;
import ar.edu.itba.paw.persistence.VerificationTokenDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class VerificationTokenServiceImpl implements VerificationTokenService {

    private final VerificationTokenDao verificationTokenDao;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    public VerificationTokenServiceImpl(final VerificationTokenDao verificationTokenDao) {
        this.verificationTokenDao = verificationTokenDao;
    }

    @Override
    public Optional<VerificationToken> getToken(String token) {
        return verificationTokenDao.getToken(token);
    }

    @Transactional
    @Override
    public void deleteTokenByUserId(long userId, TokenType type) {
        verificationTokenDao.deleteTokenByUserId(userId, type);
    }

    @Transactional
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

    @Transactional
    @Override
    public VerificationToken generate(long userId, TokenType type) {
        final String token = UUID.randomUUID().toString();
        return verificationTokenDao.createToken(userId, token, VerificationToken.getNewExpiryDate(), type);
    }

}
