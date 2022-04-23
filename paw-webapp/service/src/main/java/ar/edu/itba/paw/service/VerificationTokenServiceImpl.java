package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.VerificationToken;
import ar.edu.itba.paw.persistence.VerificationTokenDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Optional;

public class VerificationTokenServiceImpl implements VerificationTokenService {

    private VerificationTokenDao verificationTokenDao;

    @Autowired
    public VerificationTokenServiceImpl(VerificationTokenDao verificationTokenDao) {
        this.verificationTokenDao = verificationTokenDao;
    }

    @Override
    public Optional<VerificationToken> getToken(long id) {
        return verificationTokenDao.getToken(id);
    }

    @Override
    public VerificationToken createToken(long userId, String token, LocalDateTime expiryDate) {
        return verificationTokenDao.createToken(userId, token, expiryDate);
    }

    @Override
    public void deleteTokenByUserId(long userId) {
        verificationTokenDao.deleteTokenByUserId(userId);
    }
}
