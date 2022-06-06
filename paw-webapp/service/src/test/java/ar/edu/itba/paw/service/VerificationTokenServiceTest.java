package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.TokenType;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.VerificationToken;
import ar.edu.itba.paw.model.exceptions.InvalidTokenException;
import ar.edu.itba.paw.persistence.VerificationTokenDao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class VerificationTokenServiceTest {

    @Mock
    private VerificationTokenDao tokenDao;

    @InjectMocks
    private VerificationTokenService tokenService = new VerificationTokenServiceImpl();

    private static final String TOKEN_VALUE = "token";
    private static final String INVALID = "invalid";
    private static final User user = new User.UserBuilder("artist@mail.com","12345678", "name", false, false).surname("surname").description("description").id(1L).build();
    private static final VerificationToken EXPIRED_TOKEN = new VerificationToken( TOKEN_VALUE, user,  LocalDateTime.now().minusDays(2), TokenType.VERIFY);



    @Test(expected = InvalidTokenException.class)
    public void testGetTokenOwnerInvalidToken() {
        tokenService.getTokenOwner(INVALID,TokenType.VERIFY);
        Assert.fail("Should have thrown InvalidTokenException");
    }

    @Test(expected = InvalidTokenException.class)
    public void testGetTokenOwnerExpiredToken() {
        when(tokenDao.getToken(Mockito.eq(TOKEN_VALUE))).thenReturn(Optional.of(EXPIRED_TOKEN));
        tokenService.getTokenOwner(TOKEN_VALUE, TokenType.VERIFY);
        Assert.fail("Should have thrown InvalidTokenException");
    }

    @Test(expected = InvalidTokenException.class)
    public void testIsValidInvalidToken() {
        tokenService.isValid(INVALID);
        Assert.fail("Should have thrown InvalidTokenException");
    }

    @Test(expected = InvalidTokenException.class)
    public void testIsValidExpiredToken() {
        when(tokenDao.getToken(Mockito.eq(TOKEN_VALUE))).thenReturn(Optional.of(EXPIRED_TOKEN));
        tokenService.isValid(TOKEN_VALUE);
        Assert.fail("Should have thrown InvalidTokenException");
    }

}
