package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.exceptions.InvalidTokenException;
import ar.edu.itba.paw.model.TokenType;
import ar.edu.itba.paw.model.VerificationToken;
import ar.edu.itba.paw.persistence.VerificationTokenDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
public class VerificationTokenServiceImpl implements VerificationTokenService {

    @Autowired
    private VerificationTokenDao verificationTokenDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(VerificationTokenServiceImpl.class);

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
    public long getTokenOwner(String token, TokenType type) {
        Optional<VerificationToken> t = getToken(token);

        if(!t.isPresent()) {
            LOGGER.warn("Given token is invalid");
            throw new InvalidTokenException();
        }

        deleteTokenByUserId(t.get().getUser().getId(), type);
        if(!t.get().isValid()) {
            LOGGER.warn("Given token is expired");
            throw new InvalidTokenException();
        }

        return t.get().getUser().getId();
    }

    @Transactional
    @Override
    public void isValid(String token) {
        Optional<VerificationToken> t = getToken(token);
        if(!t.isPresent()) {
            LOGGER.warn("Given token is invalid");
            throw new InvalidTokenException();
        }

        if(!t.get().isValid()) {
            LOGGER.warn("Given token is expired");
            deleteTokenByUserId(t.get().getUser().getId(), TokenType.RESET);
            throw new InvalidTokenException();
        }
    }

    @Transactional
    @Override
    public VerificationToken generate(User user, TokenType type) {
        final String token = UUID.randomUUID().toString();
        return verificationTokenDao.createToken(user, token, VerificationToken.getNewExpiryDate(), type);
    }

}
