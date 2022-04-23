package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.VerificationToken;

import java.time.LocalDateTime;
import java.util.Optional;

public interface VerificationTokenDao {

    Optional<VerificationToken> getToken(long id);

    VerificationToken createToken(long userId, String token, LocalDateTime expiryDate);

    void deleteTokenByUserId(long userId);
}
