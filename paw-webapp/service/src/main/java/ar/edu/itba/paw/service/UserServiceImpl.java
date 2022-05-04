package ar.edu.itba.paw.service;
import ar.edu.itba.paw.persistence.TokenType;
import ar.edu.itba.paw.persistence.User;
import ar.edu.itba.paw.persistence.VerificationToken;
import ar.edu.itba.paw.model.exceptions.DuplicateUserException;
import ar.edu.itba.paw.model.exceptions.EmailNotFoundException;
import ar.edu.itba.paw.model.exceptions.UserNotFoundException;
import ar.edu.itba.paw.persistence.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenService verificationTokenService;
    private final RoleService roleService;
    private final GenreService genreService;
    private final ImageService imageService;
    private final Environment environment;
    private final MailingService mailingService;
    private final MessageSource messageSource;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    //  TODO: uso de LOGGER

    @Autowired
    public UserServiceImpl(final UserDao userDao, final PasswordEncoder passwordEncoder,
                           final VerificationTokenService verificationTokenService,
                           final RoleService roleService, final GenreService genreService,
                           final ImageService imageService, final Environment environment,
                            final MailingService mailingService, final MessageSource messageSource) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.verificationTokenService = verificationTokenService;
        this.roleService = roleService;
        this.genreService = genreService;
        this.imageService = imageService;
        this.environment = environment;
        this.mailingService = mailingService;
        this.messageSource = messageSource;
    }

    @Override
    public Optional<User> getUserById(long id) {
        return userDao.getUserById(id);
    }

    @Override
    public User create(User.UserBuilder userBuilder) {
        userBuilder.password(passwordEncoder.encode(userBuilder.getPassword()));
        if(userDao.findByEmail(userBuilder.getEmail()).isPresent()) {
            throw new DuplicateUserException(userBuilder.getName(), userBuilder.getSurname(), userBuilder.isBand());
        }
        User user = userDao.create(userBuilder);
        final VerificationToken token = verificationTokenService.generate(user.getId(), TokenType.VERIFY);
        sendVerifyEmail(user, token);
        return user;
    }

    @Override
    public void resendUserVerification(String email) {
        User user = findByEmail(email).orElseThrow(UserNotFoundException::new);

        verificationTokenService.deleteTokenByUserId(user.getId(), TokenType.VERIFY);

        VerificationToken token = verificationTokenService.generate(user.getId(), TokenType.VERIFY);

        sendVerifyEmail(user, token);
    }

    private void sendVerifyEmail(User user, VerificationToken token) {
        try {
            Locale locale = LocaleContextHolder.getLocale();
            final String url = new URL("http", environment.getRequiredProperty("app.base.url"), "/paw-2022a-03/verify?token=" + token.getToken()).toString();
            final Map<String, Object> mailData = new HashMap<>();
            mailData.put("confirmationURL", url);
            mailingService.sendEmail(user, user.getEmail(), messageSource.getMessage("verify-account.subject",null,locale), "verify-account", mailData, locale);
        } catch (MessagingException | MalformedURLException e) {
            LOGGER.warn("Register verification email failed");
        }
    }

    @Override
    public void editUser(long userId, String name, String surname, String description, List<String> genresNames, List<String> rolesNames, byte[] image) {

        userDao.editUser(userId, name, surname, description);
        genreService.updateUserGenres(genresNames,userId);
        roleService.updateUserRoles(rolesNames,userId);
        imageService.updateProfilePicture(userId,image);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public void verifyUser(String token) {
        Long userId = verificationTokenService.validateToken(token, TokenType.VERIFY);

        if(userId != null) {
            userDao.verifyUser(userId);
            autoLogin(userId);
        } else {
            throw new UserNotFoundException();
        }
    }

    private void autoLogin(long userId) {
        final User user = getUserById(userId).orElseThrow(UserNotFoundException::new);
        final Collection<GrantedAuthority> authorities = new ArrayList<>();
        if(user.isBand())
            authorities.add(new SimpleGrantedAuthority("ROLE_BAND"));
        else
            authorities.add(new SimpleGrantedAuthority("ROLE_ARTIST"));
        Authentication auth = new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword(),authorities);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Override
    public void sendResetEmail(String email) {
        User user = userDao.findByEmail(email).orElseThrow(UserNotFoundException::new);

        verificationTokenService.deleteTokenByUserId(user.getId(), TokenType.RESET);
        VerificationToken token = verificationTokenService.generate(user.getId(),TokenType.RESET);
        try {
            Locale locale = LocaleContextHolder.getLocale();
            final String url = new URL("http", environment.getRequiredProperty("app.base.url"), "/paw-2022a-03/newPassword?token=" + token.getToken()).toString();
            final Map<String, Object> mailData = new HashMap<>();
            mailData.put("resetPasswordURL", url);
            mailingService.sendEmail(user, user.getEmail(), messageSource.getMessage("reset-password.subject",null,locale), "reset-password", mailData, locale);
        } catch (MessagingException | MalformedURLException e) {
            LOGGER.warn("Reset password email failed");
        }
    }

    @Override
    public void changePassword(String token, String newPassword) {
        Long userId = verificationTokenService.validateToken(token, TokenType.RESET);
        if(userId == null)
            throw new UserNotFoundException();
        userDao.changePassword(userId, passwordEncoder.encode(newPassword));
        autoLogin(userId);
    }

}
