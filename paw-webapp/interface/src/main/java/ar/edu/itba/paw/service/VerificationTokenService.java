package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.VerifiactionToken;

import java.time.LocalDateTime;
import java.util.Optional;

public interface VerificationTokenService {

    Optional<VerifiactionToken> getToken(long id);

    VerifiactionToken createToken(long userId, String token, LocalDateTime expiryDate);

    void deleteTokenByUserId(long userId);
}
