package ar.edu.itba.paw.service;

import ar.edu.itba.paw.TokenType;
import ar.edu.itba.paw.User;
import ar.edu.itba.paw.model.exceptions.DuplicateUserException;
import ar.edu.itba.paw.model.exceptions.UserNotFoundException;
import ar.edu.itba.paw.persistence.UserDao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserDao userDao;

    @Mock
    private VerificationTokenService verificationTokenService;

    @Mock
    private MailingService mailingService;

    @InjectMocks
    private UserService userService = new UserServiceImpl();

    private static final String TOKEN_VALUE = "token";
    private static final String PASSWORD = "12345678";
    private static final String INVALID_EMAIL = "invalid@mail.com";
    private static final User.UserBuilder USER_BUILDER = new User.UserBuilder("artist@mail.com","12345678", "name", false, false).surname("surname").description("description");

    @Test(expected = DuplicateUserException.class)
    public void testCreateWithDuplicatedEmail() {
        when(userDao.create(Mockito.any())).thenThrow(new DuplicateUserException());
        userService.create(USER_BUILDER);
        Assert.fail("Should have thrown DuplicateUserException");
    }

    @Test(expected = UserNotFoundException.class)
    public void testResendUserVerificationInvalidEmail() {
        when(userDao.findByEmail(Mockito.eq(INVALID_EMAIL))).thenThrow(new UserNotFoundException());
        userService.resendUserVerification(INVALID_EMAIL);
        Assert.fail("Should have thrown UserNotFoundException");
    }

    @Test(expected = UserNotFoundException.class)
    public void testSendResetEmailInvalidEmail() {
        when(userDao.findByEmail(Mockito.eq(INVALID_EMAIL))).thenThrow(new UserNotFoundException());
        userService.sendResetEmail(INVALID_EMAIL);
        Assert.fail("Should have thrown UserNotFoundException");
    }

    @Test(expected = UserNotFoundException.class)
    public void testVerifyUserInvalidEmail() {
        when(verificationTokenService.getTokenOwner(Mockito.eq(TOKEN_VALUE), Mockito.eq(TokenType.VERIFY))).thenReturn(Long.valueOf(1));
        when(userDao.getUserById(Mockito.eq(Long.valueOf(1)))).thenThrow(new UserNotFoundException());
        userService.verifyUser(TOKEN_VALUE);
        Assert.fail("Should have thrown UserNotFoundException");
    }

    @Test(expected = UserNotFoundException.class)
    public void testChangePasswordInvalidEmail() {
        when(verificationTokenService.getTokenOwner(Mockito.eq(TOKEN_VALUE), Mockito.eq(TokenType.RESET))).thenReturn(Long.valueOf(1));
        when(userDao.getUserById(Mockito.eq(Long.valueOf(1)))).thenThrow(new UserNotFoundException());
        userService.verifyUser(TOKEN_VALUE);
        Assert.fail("Should have thrown UserNotFoundException");
    }


}
