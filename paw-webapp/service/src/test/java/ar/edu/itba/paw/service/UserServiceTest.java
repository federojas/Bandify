package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.exceptions.DuplicateUserException;
import ar.edu.itba.paw.model.exceptions.UserNotFoundException;
import ar.edu.itba.paw.persistence.UserDao;
import ar.edu.itba.paw.persistence.VerificationTokenDao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserDao userDao;

    @Mock
    private VerificationTokenService verificationTokenService;

    @Mock

    private RoleService roleService;

    @Mock
    private GenreService genreService;

    @Mock
    private MailingService mailingService;

    @Mock
    private LocationService locationService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService = new UserServiceImpl();

    private static final String TOKEN_VALUE = "token";
    private static final String PASSWORD = "12345678";
    private static final String INVALID_EMAIL = "invalid@mail.com";
    private static final User.UserBuilder USER_BUILDER = new User.UserBuilder("artist@mail.com","12345678", "name", false, false).surname("surname").id(1L).description("description");
    private static final User USER = USER_BUILDER.build();
    private static final User.UserBuilder USER_BUILDER_BAND = new User.UserBuilder("band@mail.com","12345678","name",true,true).id(2L);
    private static final User USER_BAND = USER_BUILDER_BAND.build();

    private static final String EDIT_NAME = "editName";
    private static final String EDIT_SURNAME = "editSurname";
    private static final String EDIT_DESCRIPTION = "editDescription";
    private static final List<String> EDIT_GENRES = Arrays.asList("genre1", "genre2");
    private static final Set<Genre> EDIT_GENRES_SET = new HashSet<>(EDIT_GENRES.stream().map(g -> new Genre(g, 1L)).collect(Collectors.toList()));
    private static final List<String> EDIT_ROLES = Arrays.asList("role1", "role2");
    private static final Set<Role> EDIT_ROLES_SET = new HashSet<>(EDIT_ROLES.stream().map(r -> new Role(1L, r)).collect(Collectors.toList()));
    private static final byte[] EDIT_IMAGE = {69, 121, 101, 45, 62, 118, 101, 114, (byte) 196, (byte) 195, 61, 101, 98};
    private static final String EDIT_LOCATION = "location";

    @Test
    public void testGetUserById() {
        when(userDao.getUserById(1L)).thenReturn(Optional.ofNullable(USER));

        Optional<User> user = userService.getUserById(1L);
        assertTrue(user.isPresent());
        assertEquals(USER, user.get());
    }

    @Test
    public void testGetArtistById() {
        when(userDao.getUserById(1L)).thenReturn(Optional.ofNullable(USER));

        User user = userService.getArtistById(1L);
        assertEquals(USER, user);
    }

    @Test
    public void testGetUserByIdNotFound() {
        when(userDao.getUserById(1L)).thenReturn(Optional.empty());

        Optional<User> user = userService.getUserById(1L);
        assertFalse(user.isPresent());
    }

    @Test
    public void testCreate() {
        when(userDao.findByEmail(USER.getEmail())).thenReturn(Optional.empty());
        when(userDao.create(USER_BUILDER)).thenReturn(USER);

        User user = userService.create(USER_BUILDER);
        assertEquals(USER, user);
    }

    @Test
    public void testResendUserVerification() {
        when(userDao.findByEmail(USER.getEmail())).thenReturn(Optional.ofNullable(USER));
        userService.resendUserVerification(USER.getEmail());
        verify(verificationTokenService).deleteTokenByUserId(Mockito.anyLong(), Mockito.eq(TokenType.VERIFY));
        verify(verificationTokenService).generate(Mockito.eq(USER), Mockito.eq(TokenType.VERIFY));
        verify(mailingService).sendVerificationEmail(Mockito.eq(USER), any(), any());
    }

    @Test
    public void testEditUser() {
        when(userDao.getUserById(1L)).thenReturn(Optional.ofNullable(USER));
        when(locationService.getLocationByName(EDIT_LOCATION)).thenReturn(Optional.of(new Location(1L, EDIT_LOCATION)));
        when(genreService.getGenresByNames(EDIT_GENRES)).thenReturn(EDIT_GENRES_SET);
        when(roleService.getRolesByNames(EDIT_ROLES)).thenReturn(EDIT_ROLES_SET);

        User user = userService.editUser(1L, EDIT_NAME, EDIT_SURNAME, EDIT_DESCRIPTION, EDIT_GENRES, EDIT_ROLES, EDIT_IMAGE, EDIT_LOCATION);
        assertNotNull(user);
        assertEquals(EDIT_NAME, user.getName());
        assertEquals(EDIT_SURNAME, user.getSurname());
        assertEquals(EDIT_DESCRIPTION, user.getDescription());
    }

    @Test
    public void testUpdateUserLocation() {
        when(locationService.getLocationByName(EDIT_LOCATION)).thenReturn(Optional.of(new Location(1L, EDIT_LOCATION)));

        User user = userService.updateUserLocation(EDIT_LOCATION, USER);
        assertNotNull(user);
        assertEquals(EDIT_LOCATION, user.getLocation().getName());
    }

    @Test
    public void testUpdateUserGenres() {
        when(genreService.getGenresByNames(EDIT_GENRES)).thenReturn(EDIT_GENRES_SET);

        User user = userService.updateUserGenres(EDIT_GENRES, USER);
        assertNotNull(user);
        assertEquals(EDIT_GENRES, user.getUserGenres().stream().map(Genre::getName).collect(Collectors.toList()));
    }

    @Test
    public void testUpdateUserRoles() {
        when(roleService.getRolesByNames(EDIT_ROLES)).thenReturn(EDIT_ROLES_SET);

        User user = userService.updateUserRoles(EDIT_ROLES, USER);
        assertNotNull(user);
        assertEquals(EDIT_ROLES, user.getUserRoles().stream().map(Role::getName).collect(Collectors.toList()));
    }

    @Test
    public void testUpdateUserImage() {
        User user = userService.updateProfilePicture(USER, EDIT_IMAGE);
        assertNotNull(user);
        assertArrayEquals(EDIT_IMAGE, user.getProfileImage());
    }

    @Test
    public void testVerifyUser() {
        when(verificationTokenService.getTokenOwner(TOKEN_VALUE, TokenType.VERIFY)).thenReturn(USER.getId());
        doNothing().when(userDao).verifyUser(USER.getId());
        when(userService.getUserById(USER.getId())).thenReturn(Optional.ofNullable(USER));

        boolean verifiedAndAutoLogged = userService.verifyUser(TOKEN_VALUE);
        assertTrue(verifiedAndAutoLogged);
    }

    @Test
    public void testSendResetEmail() {
        when(userDao.findByEmail(USER.getEmail())).thenReturn(Optional.of(USER));
        when(verificationTokenService.generate(Mockito.eq(USER), Mockito.eq(TokenType.RESET))).thenReturn(any(VerificationToken.class));

        userService.sendResetEmail(USER.getEmail());
        verify(mailingService).sendResetPasswordEmail(Mockito.eq(USER), any(), any());
    }

    @Test
    public void testChangePassword() {
        when(verificationTokenService.getTokenOwner(TOKEN_VALUE, TokenType.RESET)).thenReturn(USER.getId());
        when(userService.getUserById(USER.getId())).thenReturn(Optional.of(USER));

        boolean changePassword = userService.changePassword(TOKEN_VALUE, PASSWORD);
        assertTrue(changePassword);
    }

    @Test
    public void testSetAvailableArtist() {
        User user = userService.setAvailable(true, USER);
        assertTrue(user.isAvailable());
    }

    @Test
    public void testSetAvailableBand() {
        User user = userService.setAvailable(false, USER_BAND);
        assertFalse(user.isAvailable());
    }

    @Test
    public void testFilter() {
        int page = 1;
        List<User> expectedUsers = Arrays.asList(USER, USER_BAND);
        FilterOptions filter = any();
        when(userDao.filter(filter, eq(page))).thenReturn(expectedUsers);

        List<User> users = userService.filter(filter, page);
        assertNotNull(users);
        assertEquals(expectedUsers, users);
    }

    @Test(expected = UserNotFoundException.class)
    public void testGetArtistByIdButIsBand() {
        when(userDao.getUserById(1L)).thenReturn(Optional.ofNullable(USER_BAND));

        userService.getArtistById(1L);
        fail("Should have thrown UserNotFoundException");
    }

    @Test(expected = DuplicateUserException.class)
    public void testCreateWithDuplicatedEmail() {
        when(userDao.create(any())).thenThrow(new DuplicateUserException());
        userService.create(USER_BUILDER);
        fail("Should have thrown DuplicateUserException");
    }

    @Test(expected = UserNotFoundException.class)
    public void testResendUserVerificationInvalidEmail() {
        when(userDao.findByEmail(Mockito.eq(INVALID_EMAIL))).thenThrow(new UserNotFoundException());
        userService.resendUserVerification(INVALID_EMAIL);
        fail("Should have thrown UserNotFoundException");
    }

    @Test(expected = UserNotFoundException.class)
    public void testSendResetEmailInvalidEmail() {
        when(userDao.findByEmail(Mockito.eq(INVALID_EMAIL))).thenThrow(new UserNotFoundException());
        userService.sendResetEmail(INVALID_EMAIL);
        fail("Should have thrown UserNotFoundException");
    }

    @Test(expected = UserNotFoundException.class)
    public void testVerifyUserInvalidEmail() {
        when(verificationTokenService.getTokenOwner(Mockito.eq(TOKEN_VALUE), Mockito.eq(TokenType.VERIFY))).thenReturn(Long.valueOf(1));
        when(userDao.getUserById(Mockito.eq(Long.valueOf(1)))).thenThrow(new UserNotFoundException());
        userService.verifyUser(TOKEN_VALUE);
        fail("Should have thrown UserNotFoundException");
    }

    @Test(expected = UserNotFoundException.class)
    public void testChangePasswordInvalidEmail() {
        userService.verifyUser(TOKEN_VALUE);
        fail("Should have thrown UserNotFoundException");
    }

}
