package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Membership;
import ar.edu.itba.paw.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class MembershipJpaDao implements MembershipDao {
    // TODO: EN TODOS LOS DAOS DEJAR DE AGREGAR UN ID -1 CUANDO LA LISTA ES VACÍA
    // ES MEJOR NO HACER LA QUERY DIRECTAMENTE SI ES VACÍA
    private final int PAGE_SIZE = 5;

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Membership> getBandMemberships(User band, int page) {

        Query query = em.createNativeQuery("SELECT DISTINCT id FROM memberships" +
                " WHERE bandId = :bandId " +
                " LIMIT " + PAGE_SIZE + " OFFSET " + (page - 1) * PAGE_SIZE);
        query.setParameter("bandId", band.getId());

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
    public int getTotalBandMembershipsPages(User band) {
        return 0;
    }

    @Override
    public void addMember(Membership.Builder builder) {

    }

    @Override
    public void deleteMembership(Membership membership) {

    }

    @Override
    public List<Membership> getArtistMemberships(User artist, int page) {
        return null;
    }

    @Override
    public int getTotalArtistMembershipsPages(User band) {
        return 0;
    }
}
