package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.persistence.UserDao;
import org.springframework.stereotype.Repository;

@Repository
public class UserJdbcDao implements UserDao {
    @Override
    public User getUserById(long id) {
        return new User(1,"PAW from the DB","secret");
    }
}
