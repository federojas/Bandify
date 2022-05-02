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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final ImageService imageService;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    //  TODO: uso de LOGGER

    @Autowired
    public UserServiceImpl(final UserDao userDao, final PasswordEncoder passwordEncoder,
                           final VerificationTokenService verificationTokenService,
                           final RoleService roleService, final GenreService genreService,
                           final ImageService imageService) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.verificationTokenService = verificationTokenService;
        this.roleService = roleService;
        this.genreService = genreService;
        this.imageService = imageService;
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
    public void editUser(User.UserBuilder userBuilder, List<String> genresNames, List<String> rolesNames, byte[] image) {
        long id = findByEmail(userBuilder.getEmail()).orElseThrow(UserNotFoundException::new).getId();
        userDao.editUser(id,userBuilder);
        genreService.updateUserGenres(genresNames,id);
        roleService.updateUserRoles(rolesNames,id);
        imageService.updateProfilePicture(id,image);
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
        Optional<User> user = userDao.findByEmail(email);
        if(!user.isPresent())
            throw new EmailNotFoundException();

        verificationTokenService.deleteTokenByUserId(user.get().getId(), TokenType.RESET);

        verificationTokenService.sendResetEmail(user.get());
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
