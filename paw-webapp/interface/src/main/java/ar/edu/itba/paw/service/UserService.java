package ar.edu.itba.paw.service;

import ar.edu.itba.paw.persistence.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> getUserById(long id);

    User create(User.UserBuilder userBuilder);

    Optional<User> findByEmail(String email);

    void verifyUser(String token);

    void sendResetEmail(String email);

    void changePassword(String token, String newPassword);

    void resendUserVerification(String email);

    void editUser(long userId, String name, String surname, String description, List<String> genresNames, List<String> rolesNames, byte[] image);

    Optional<byte[]> getProfilePictureByUserId(long userId);

}
