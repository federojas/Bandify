package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Membership;
import ar.edu.itba.paw.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class MembershipJpaDao implements MembershipDao {
    // TODO: EN TODOS LOS DAOS DEJAR DE AGREGAR UN ID -1 CUANDO LA LISTA ES VACÍA
    // ES MEJOR NO HACER LA QUERY DIRECTAMENTE SI ES VACÍA
    private final int PAGE_SIZE = 5;

    @PersistenceContext
    private EntityManager em;

    private static final Logger LOGGER = LoggerFactory.getLogger(MembershipJpaDao.class);

    @Override
    public List<Membership> getUserMemberships(User user, int page) {
        Query query;
        if(user.isBand()) {
            query = em.createNativeQuery("SELECT id FROM memberships" +
                    " WHERE bandId = :bandId " +
                    " LIMIT " + PAGE_SIZE + " OFFSET " + (page - 1) * PAGE_SIZE);
            query.setParameter("bandId", user.getId());
        } else {
            query = em.createNativeQuery("SELECT id FROM memberships" +
                    " WHERE artistId = :artistId " +
                    " LIMIT " + PAGE_SIZE + " OFFSET " + (page - 1) * PAGE_SIZE);
            query.setParameter("artistId", user.getId());
        }

        @SuppressWarnings("unchecked")
        List<Long> ids = (List<Long>) query.getResultList().stream().map(o -> ((Number) o).longValue()).collect(Collectors.toList());

        if(!ids.isEmpty()) {
            TypedQuery<Membership> membershipsQuery = em.createQuery("from Membership as m where m.id in :ids ORDER BY m.id asc", Membership.class);
            membershipsQuery.setParameter("ids",ids);
            return membershipsQuery.getResultList();
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public int getTotalUserMembershipsPages(User user) {
        return 0;
    }

    @Override
    public Membership createMembership(Membership.Builder builder) {
        LOGGER.info("Creating new membership");
        final Membership membership = builder.build();
        em.persist(membership);
        return membership;
    }

    @Override
    public void deleteMembership(long id) {
        LOGGER.info("Deleting membership with id {}", id);
        Optional<Membership> membership = getMembershipById(id);
        membership.ifPresent(value -> em.remove(value));
    }

    private Optional<Membership> getMembershipById(long id) {
        LOGGER.info("Getting membership with id {}", id);
        return Optional.ofNullable(em.find(Membership.class, id));
    }

}
