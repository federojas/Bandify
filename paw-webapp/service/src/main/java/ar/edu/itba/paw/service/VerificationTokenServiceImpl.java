package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.VerificationToken;
import ar.edu.itba.paw.persistence.VerificationTokenDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class VerificationTokenServiceImpl implements VerificationTokenService {

    private VerificationTokenDao verificationTokenDao;

    @Autowired
    public VerificationTokenServiceImpl(VerificationTokenDao verificationTokenDao) {
        this.verificationTokenDao = verificationTokenDao;
    }

    @Override
    public Optional<VerificationToken> getToken(String token) {
        return verificationTokenDao.getToken(token);
    }

    @Override
    public VerificationToken createToken(long userId, String token, LocalDateTime expiryDate) {
        return verificationTokenDao.createToken(userId, token, expiryDate);
    }

    @Override
    public void deleteTokenByUserId(long userId) {
        verificationTokenDao.deleteTokenByUserId(userId);
    }

    @Override
    public boolean verify(String token) {
        Optional<VerificationToken> t = getToken(token);

        if(!t.isPresent())
            return false;

        deleteTokenByUserId(t.get().getUserId());

        if(LocalDateTime.now().isAfter(t.get().getExpiryDate()))
            return false;
            // TODO: poner en true el campo de verificado en user.
        return true;

    }
}
