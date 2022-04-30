package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> getUserById(long id);

    User create(User.UserBuilder userBuilder);

    Optional<User> findByEmail(String email);

    void verifyUser(String token);

    void sendResetEmail(String email);

    void changePassword(String token, String newPassword);

    void addUserRoles(List<String> rolesNames, long userId);

    void addUserGenres(List<String> genresNames, long userId);

    void resendUserVerification(String email);

    void createProfilePicture(long userId, byte[] image);

    void updateProfilePicture(long userId, byte[] image);

    Optional<byte[]> getProfilePictureByUserId(long userId);

}
