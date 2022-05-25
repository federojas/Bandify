package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.Genre;
import ar.edu.itba.paw.Role;
import ar.edu.itba.paw.User;

import java.util.Optional;
import java.util.Set;

public interface UserDao {
    Optional<User> getUserById(long id);

    User create(User.UserBuilder userBuilder);

    Optional<User> findByEmail(String email);

    void changePassword(long userId, String newPassword);

    void verifyUser(long userId);

    void editUser(long id, String name, String surname, String description);

    byte[] updateProfilePicture(long userId, byte[] image);

    Optional<byte[]> getProfilePicture(long userId);
}
