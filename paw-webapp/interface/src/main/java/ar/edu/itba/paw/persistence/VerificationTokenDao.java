package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.TokenType;
import ar.edu.itba.paw.model.VerificationToken;

import java.time.LocalDateTime;
import java.util.Optional;

public interface VerificationTokenDao {

    Optional<VerificationToken> getToken(String token);

    VerificationToken createToken(long userId, String token, LocalDateTime expiryDate, TokenType type);

    void deleteTokenByUserId(long userId, TokenType type);
}