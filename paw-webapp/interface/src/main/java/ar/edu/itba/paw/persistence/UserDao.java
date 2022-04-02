package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.User;

// DAO = Data Access Object
// es el objeto responsable a lo que es acceso a la fuente de datos
// borrar este comment desp
public interface UserDao {
    User getUserById(long id);
}
