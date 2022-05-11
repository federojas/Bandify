package ar.edu.itba.paw.service;

import ar.edu.itba.paw.TokenType;
import ar.edu.itba.paw.VerificationToken;

import java.util.Optional;

public interface VerificationTokenService {

    Optional<VerificationToken> getToken(String token);

    void deleteTokenByUserId(long userId, TokenType type);

    VerificationToken generate(long userId, TokenType type);

    long getTokenOwner(String token, TokenType type);

    void isValid(String token);

}
