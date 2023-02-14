package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Membership;
import ar.edu.itba.paw.model.MembershipState;
import ar.edu.itba.paw.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class MembershipJpaDao implements MembershipDao {

    private final int PAGE_SIZE = 5;
    private final int PREVIEW_SIZE = 3;
    private final int NO_OFFSET = 0;
    private final int DEFAULT_PAGE = 1;

    @PersistenceContext
    private EntityManager em;

    private static final Logger LOGGER = LoggerFactory.getLogger(MembershipJpaDao.class);

    @Override
    public List<Membership> getUserMembershipsByState(User user, MembershipState state, int page) {
        return getMemberships(user, state, PAGE_SIZE, (page-1) * PAGE_SIZE, page);
    }

    @Override
    public List<Membership> getUserMemberships(User user, int page) {
        return getMemberships(user, null, PAGE_SIZE, (page-1) * PAGE_SIZE, page);
    }

    @Override
    public int getTotalUserMembershipsByStatePages(User user) {
        Query query = membershipCountQuery(user, null);
        return (int) Math.ceil(((BigInteger) query.getSingleResult()).doubleValue() / PAGE_SIZE);
    }

    @Override
    public List<Membership> getUserMembershipsPreview(User user) {
        return getMemberships(user, MembershipState.ACCEPTED, PREVIEW_SIZE, NO_OFFSET, DEFAULT_PAGE);
    }

    private List<Membership> getMemberships(User user, MembershipState state, int page_size, int offset, int page) {
        Query query;
        if(state != null) {
            if(user.isBand()) {
                query = em.createNativeQuery("SELECT id FROM memberships" +
                        " WHERE bandId = :bandId AND state = :state ORDER BY id ASC " +
                        " LIMIT " + page_size + " OFFSET " + offset);
                query.setParameter("bandId", user.getId());
            } else {
                query = em.createNativeQuery("SELECT id FROM memberships" +
                        " WHERE artistId = :artistId AND state = :state ORDER BY id ASC " +
                        " LIMIT " + page_size + " OFFSET " + offset);
                query.setParameter("artistId", user.getId());
            }
            query.setParameter("state", state.getState());
        } else {
            if(user.isBand()) {
                query = em.createNativeQuery("SELECT id FROM memberships" +
                        " WHERE bandId = :bandId ORDER BY id ASC " +
                        " LIMIT " + page_size + " OFFSET " + offset);
                query.setParameter("bandId", user.getId());
            } else {
                query = em.createNativeQuery("SELECT id FROM memberships" +
                        " WHERE artistId = :artistId ORDER BY id ASC " +
                        " LIMIT " + page_size + " OFFSET " + offset);
                query.setParameter("artistId", user.getId());
            }
        }


        @SuppressWarnings("unchecked")
        List<Long> ids = (List<Long>) query.getResultList().stream().map(o -> ((Number) o).longValue()).collect(Collectors.toList());

        if(!ids.isEmpty()) {
            TypedQuery<Membership> membershipsQuery = em.createQuery("from Membership as m where m.id in :ids ORDER BY m.id asc", Membership.class);
            membershipsQuery.setParameter("ids",ids);
            return membershipsQuery.getResultList();
        }
        return Collections.emptyList();
    }

    @Override
    public int getTotalUserMembershipsByStatePages(User user, MembershipState state) {
        Query query = membershipCountQuery(user, state);
        return (int) Math.ceil(((BigInteger) query.getSingleResult()).doubleValue() / PAGE_SIZE);
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

    @Override
    public Optional<Membership> getMembershipById(long id) {
        LOGGER.info("Getting membership with id {}", id);
        return Optional.ofNullable(em.find(Membership.class, id));
    }

    @Override
    public List<Membership> getMembershipsByUsers(User band, User artist) {
        LOGGER.info("Getting membership with band id {} and artist id {}", band.getId(), artist.getId());
        final TypedQuery<Membership> query = em.createQuery("SELECT m FROM Membership as m where m.band.id = :bandId and m.artist.id = :artistId AND m.state = :state", Membership.class);
        query.setParameter("bandId", band.getId());
        query.setParameter("artistId", artist.getId());
        query.setParameter("state", MembershipState.ACCEPTED);
        return query.getResultList();
    }

    @Override
    public boolean membershipExists(User band, User artist) {
        final TypedQuery<Membership> query = em.createQuery("FROM Membership as m where m.artist.id = :artistId and m.band.id = :bandId AND m.state <> :state",
                Membership.class);
        query.setParameter("artistId", artist.getId());
        query.setParameter("bandId", band.getId());
        query.setParameter("state", MembershipState.REJECTED);
        return query.getResultList().stream().findFirst().isPresent();
    }

    @Override
    public boolean isInBand(User band, User artist) {
        final TypedQuery<Membership> query = em.createQuery("FROM Membership as m where m.artist.id = :artistId and m.band.id = :bandId AND m.state = :state",
                Membership.class);
        query.setParameter("artistId", artist.getId());
        query.setParameter("bandId", band.getId());
        query.setParameter("state", MembershipState.ACCEPTED);
        return query.getResultList().stream().findFirst().isPresent();
    }

    @Override
    public int getPendingMembershipsCount(User user) {
        Query query = membershipCountQuery(user, MembershipState.PENDING);
        return ((Number) query.getSingleResult()).intValue();
    }

    private Query membershipCountQuery(User user, MembershipState state) {
        Query query;
        if(state != null) {
            if(user.isBand()) {
                query = em.createNativeQuery(
                        "SELECT COUNT(*) FROM memberships WHERE bandId = :bandId AND state= :state");
                query.setParameter("bandId", user.getId());
            } else {
                query = em.createNativeQuery(
                        "SELECT COUNT(*) FROM memberships WHERE artistId = :artistId AND state= :state");
                query.setParameter("artistId", user.getId());
            }
            query.setParameter("state", state.getState());
        } else {
            if(user.isBand()) {
                query = em.createNativeQuery(
                        "SELECT COUNT(*) FROM memberships WHERE bandId = :bandId");
                query.setParameter("bandId", user.getId());
            } else {
                query = em.createNativeQuery(
                        "SELECT COUNT(*) FROM memberships WHERE artistId = :artistId");
                query.setParameter("artistId", user.getId());
            }
        }

        return query;
    }

}
