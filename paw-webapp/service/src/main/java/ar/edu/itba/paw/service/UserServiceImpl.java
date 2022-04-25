package ar.edu.itba.paw.service;
import ar.edu.itba.paw.model.TokenType;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.VerificationToken;
import ar.edu.itba.paw.model.exceptions.DuplicateUserException;
import ar.edu.itba.paw.model.exceptions.UserNotFoundException;
import ar.edu.itba.paw.persistence.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenService verificationTokenService;
    private final RoleService roleService;
    private final GenreService genreService;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    //  TODO: uso de LOGGER

    @Autowired
    public UserServiceImpl(final UserDao userDao, final PasswordEncoder passwordEncoder,
                           final VerificationTokenService verificationTokenService,
                           final RoleService roleService, final GenreService genreService) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.verificationTokenService = verificationTokenService;
        this.roleService = roleService;
        this.genreService = genreService;
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
        verificationTokenService.sendVerifyEmail(user, token);
        return user;
    }

    @Override
    public void resendUserVerification(String email) {
        Optional<User> user = findByEmail(email);

        if(!user.isPresent())
            throw new UserNotFoundException();

        verificationTokenService.deleteTokenByUserId(user.get().getId(), TokenType.VERIFY);

        VerificationToken token = verificationTokenService.generate(user.get().getId(), TokenType.VERIFY);

        verificationTokenService.sendVerifyEmail(user.get(), token);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public boolean verifyUser(String token) {
        Long userId = verificationTokenService.validateToken(token, TokenType.VERIFY);

        if(userId != null) {
            userDao.verifyUser(userId);
            return true;
        } else {
            throw new UserNotFoundException();
        }
    }

    @Override
    public void sendResetEmail(String email) {
        Optional<User> user = userDao.findByEmail(email);
        if(!user.isPresent())
            throw new UserNotFoundException();

        verificationTokenService.sendResetEmail(user.get());
    }

    @Override
    public void changePassword(String token, String newPassword) {
        Long userId = verificationTokenService.validateToken(token, TokenType.RESET);
        if(userId == null)
            throw new UserNotFoundException();
        userDao.changePassword(userId, passwordEncoder.encode(newPassword));
    }

    @Override
    public void addUserRoles(List<String> rolesNames, long userId) {
        if(getUserById(userId).isPresent())
            roleService.addUserRoles(rolesNames,userId);
        throw new UserNotFoundException();
    }

    @Override
    public void addUserGenres(List<String> genresNames, long userId) {
        if(getUserById(userId).isPresent())
            genreService.addUserGenres(genresNames,userId);
        throw new UserNotFoundException();
    }

}
