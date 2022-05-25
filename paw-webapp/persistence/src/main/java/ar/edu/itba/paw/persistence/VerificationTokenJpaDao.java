package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.TokenType;
import ar.edu.itba.paw.VerificationToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    // TODO: deberia recibir un user
    //  TODO: el modelo no tiene type ni state
    @Override
    public VerificationToken createToken(long userId, String token, LocalDateTime expiryDate, TokenType type) {
        LOGGER.debug("Creating {} token for user {}", type, userId);
        final VerificationToken verificationToken = new VerificationToken(token,userId,expiryDate);
        em.persist(verificationToken);
        return verificationToken;
    }

    //  TODO: el modelo no tiene type ni state
    @Override
    public void deleteTokenByUserId(long userId, TokenType type) {

        final TypedQuery<VerificationToken> query = em.createQuery("FROM VerificationToken as v where v.userId = :userId AND v.type = :type", VerificationToken.class);
        query.setParameter("userId", userId);
        query.setParameter("type", type);
        final List<VerificationToken> list = query.getResultList();
        if(!list.isEmpty()) {
            LOGGER.debug("Deleting {} token of user {}", type, userId);
            em.remove(list.stream().findFirst());
        }
    }

}
