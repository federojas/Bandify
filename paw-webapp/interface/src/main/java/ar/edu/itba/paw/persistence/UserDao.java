package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Audition;
import ar.edu.itba.paw.model.FilterOptions;
import ar.edu.itba.paw.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    Optional<User> getUserById(long id);

    User create(User.UserBuilder userBuilder);

    Optional<User> findByEmail(String email);

    void changePassword(long userId, String newPassword);

    void verifyUser(long userId);

    List<User> filter(FilterOptions filterOptions, int page);

    int getTotalPages(FilterOptions filterOptions);
}
