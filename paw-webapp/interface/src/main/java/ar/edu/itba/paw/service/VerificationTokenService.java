package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.TokenType;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.VerificationToken;

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
