package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.TokenType;
import ar.edu.itba.paw.User;
import ar.edu.itba.paw.VerificationToken;

import java.time.LocalDateTime;
import java.util.Optional;

public interface VerificationTokenDao {

    Optional<VerificationToken> getToken(String token);

    VerificationToken createToken(User user, String token, LocalDateTime expiryDate, TokenType type);

    void deleteTokenByUserId(User user, TokenType type);
}
