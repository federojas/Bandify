package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
public class UserJpaDao implements UserDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserJpaDao.class);

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<User> getUserById(long id) {
        LOGGER.info("Getting user with id {}", id);
        return Optional.ofNullable(em.find(User.class, id));
    }

    @Override
    public User create(User.UserBuilder userBuilder) {
        LOGGER.info("Creating user with email {}", userBuilder.getEmail());
        final User user = userBuilder.build();
        em.persist(user);
        return user;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        LOGGER.info("Getting user with email {}", email);
        final TypedQuery<User> query = em.createQuery("FROM User as u where u.email = :email", User.class);
        query.setParameter("email", email);
        return query.getResultList().stream().findFirst();
    }

    @Override
    public void changePassword(long userId, String newPassword) {
        LOGGER.info("Changing password for user with id {}", userId);
        Optional<User> user = getUserById(userId);
        user.ifPresent(value -> value.setPassword(newPassword));
    }

    @Override
    public void verifyUser(long userId) {
        LOGGER.info("Verifying user with id {}", userId);
        Optional<User> user = getUserById(userId);
        user.ifPresent(value -> value.setEnabled(true));
    }

}
