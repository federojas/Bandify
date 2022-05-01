package ar.edu.itba.paw.service;

import ar.edu.itba.paw.persistence.TokenType;
import ar.edu.itba.paw.persistence.User;
import ar.edu.itba.paw.persistence.VerificationToken;

import java.util.Optional;

public interface VerificationTokenService {

    Optional<VerificationToken> getToken(String token);

    void deleteTokenByUserId(long userId, TokenType type);

    VerificationToken generate(long userId, TokenType type);

    void sendVerifyEmail(User user, VerificationToken token);

    void sendResetEmail(User user);

    Long validateToken(String token, TokenType type);

    boolean isValid(String token);

}
