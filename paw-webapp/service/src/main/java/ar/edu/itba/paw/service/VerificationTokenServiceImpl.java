package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.exceptions.InvalidTokenException;
import ar.edu.itba.paw.persistence.TokenType;
import ar.edu.itba.paw.persistence.VerificationToken;
import ar.edu.itba.paw.persistence.VerificationTokenDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public long getTokenOwner(String token, TokenType type) {
        Optional<VerificationToken> t = getToken(token);

        if(!t.isPresent()) {
            throw new InvalidTokenException();
        }

        deleteTokenByUserId(t.get().getUserId(), type);
        if(!t.get().isValid()) {
            throw new InvalidTokenException();
        }

        return t.get().getUserId();
    }

    @Override
    public void isValid(String token) {
        Optional<VerificationToken> t = getToken(token);
        if(!t.isPresent()) {
            throw new InvalidTokenException();
        }

        if(!t.get().isValid()) {
            deleteTokenByUserId(t.get().getUserId(), TokenType.RESET);
            throw new InvalidTokenException();
        }
    }

    @Transactional
    @Override
    public VerificationToken generate(long userId, TokenType type) {
        final String token = UUID.randomUUID().toString();
        return verificationTokenDao.createToken(userId, token, VerificationToken.getNewExpiryDate(), type);
    }

}
