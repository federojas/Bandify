package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.TokenType;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.VerificationToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class VerificationTokenJpaDao implements VerificationTokenDao {

    @PersistenceContext
    private EntityManager em;

    private static final Logger LOGGER = LoggerFactory.getLogger(GenreJpaDao.class);

    @Override
    public Optional<VerificationToken> getToken(String token) {
        final TypedQuery<VerificationToken> query = em.createQuery("FROM VerificationToken as v where v.token = :token", VerificationToken.class);
        query.setParameter("token", token);
        final List<VerificationToken> list = query.getResultList();
        return list.isEmpty() ? Optional.empty() : list.stream().findFirst();
    }

    @Override
    public VerificationToken createToken(User user, String token, LocalDateTime expiryDate, TokenType type) {
        LOGGER.debug("Creating {} token for user {}", type, user.getId());
        final VerificationToken verificationToken = new VerificationToken(token,user,expiryDate, type);
        em.persist(verificationToken);
        return verificationToken;
    }

    @Override
    public void deleteTokenByUserId(long userId, TokenType type) {
        final TypedQuery<VerificationToken> query = em.createQuery("SELECT v FROM VerificationToken as v where v.user.id = :userId AND v.type = :type", VerificationToken.class);
        query.setParameter("userId", userId);
        query.setParameter("type", type);
        final List<VerificationToken> list = query.getResultList();
        if(list.stream().findFirst().isPresent()) {
            LOGGER.debug("Deleting {} token of user {}", type, userId);
            em.remove(list.stream().findFirst().get());
        }
    }

}
