package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.User;

import java.util.Optional;

// DAO = Data Access Object
// es el objeto responsable a lo que es acceso a la fuente de datos
// borrar este comment desp
public interface UserDao {
    Optional<User> getUserById(long id);

    /**
     * Create a new user.
     *
     * @param username The name of the user.
     * @return The created user.
     */
    User create(String username, String password);
}
