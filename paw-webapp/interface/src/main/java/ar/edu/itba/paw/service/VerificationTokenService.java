package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.TokenType;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.VerificationToken;

import java.util.Optional;

public interface VerificationTokenService {

    Optional<VerificationToken> getToken(String token);

    void deleteTokenByUserId(long userId, TokenType type);

    VerificationToken generate(User user, TokenType type);

    long getTokenOwner(String token, TokenType type);

    boolean isValid(String token);

    Optional<VerificationToken> getRefreshToken(User user);

}
