package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserService {
    Optional<User> getUserById(long id);

    User create(User.UserBuilder userBuilder);

    Optional<User> findByEmail(String email);

    void verifyUser(String token);

    void sendResetEmail(String email);

    void changePassword(String token, String newPassword);

    void resendUserVerification(String email);

    void editUser(long userId, String name, String surname, String description, List<String> genresNames, List<String> rolesNames, byte[] image);

    Set<Role> getUserRoles(User user);

    void updateUserRoles(List<String> rolesNames, User user);

    Set<Genre> getUserGenres(User user);

    void updateUserGenres(List<String> genreNames, User user);

    void updateProfilePicture(User user, byte[] image);

    byte[] getProfilePicture(long userId) throws IOException;

    void updateSocialMedia(User user, Set<MediaUrl> mediaUrls);
}
