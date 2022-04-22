package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.VerifiactionToken;

import java.time.LocalDateTime;
import java.util.Optional;

public interface VerificationTokenDao {

    Optional<VerifiactionToken> getToken(long id);

    VerifiactionToken createToken(long userId, String token, LocalDateTime expiryDate);

    void deleteTokenByUserId(long userId);
}
