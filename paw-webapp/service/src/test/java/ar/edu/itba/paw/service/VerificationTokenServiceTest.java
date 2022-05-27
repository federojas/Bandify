package ar.edu.itba.paw.service;

import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class VerificationTokenServiceTest {
/*
    @Mock
    private VerificationTokenDao tokenDao;

    @InjectMocks
    private VerificationTokenService tokenService = new VerificationTokenServiceImpl();

    private static final String TOKEN_VALUE = "token";
    private static final String INVALID = "invalid";
    private static final VerificationToken TOKEN = new VerificationToken(1, TOKEN_VALUE, 1, LocalDateTime.now());
    private static final VerificationToken EXPIRED_TOKEN = new VerificationToken(1, TOKEN_VALUE, 1, LocalDateTime.now().minusDays(2));



    @Test(expected = InvalidTokenException.class)
    public void testGetTokenOwnerInvalidToken() {
        tokenService.getTokenOwner(INVALID,TokenType.VERIFY);
        Assert.fail("Should have thrown InvalidTokenException");
    }

    @Test(expected = InvalidTokenException.class)
    public void testGetTokenOwnerExpiredToken() {
        when(tokenDao.getToken(Mockito.eq(TOKEN_VALUE))).thenReturn(Optional.of(EXPIRED_TOKEN));
        tokenService.getTokenOwner(TOKEN_VALUE,TokenType.VERIFY);
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


 */
}
