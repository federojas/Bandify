package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.persistence.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(final UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public Optional<User> getUserById(long id) {
        return userDao.getUserById(id);
    }

    //TODO IMPLEMENTAR
    @Override
    public Optional<User> findByUsername(String username) {
        return null;
    }

    /* probablemente aca hacemos un chequeo de los parametros
    tmb te mandamos un mail de bienvenida, generamos token, etc. es decir
    aca van las reglas de negocios
     */
    @Override
    public User create(String username, String password) {
        return userDao.create(username, password);
    }
}
