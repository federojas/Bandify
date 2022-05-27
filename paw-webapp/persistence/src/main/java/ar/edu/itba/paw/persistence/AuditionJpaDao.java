package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Audition;
import ar.edu.itba.paw.model.AuditionFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Repository
public class AuditionJpaDao implements AuditionDao {

    @PersistenceContext
    private EntityManager em;

    private static final int PAGE_SIZE = 9;

    private static final Logger LOGGER = LoggerFactory.getLogger(AuditionJpaDao.class);


    @Override
    public Optional<Audition> getAuditionById(long id) {
        LOGGER.info("Getting audition with id {}", id);
        return Optional.ofNullable(em.find(Audition.class, id));
    }

    @Override
    public Audition create(Audition.AuditionBuilder builder) {
        LOGGER.info("Creating new audition");
        final Audition audition = builder.build();
        em.persist(audition);
        return audition;
    }

    @Override
    public List<Audition> getAll(int page) {
        LOGGER.info("Getting auditions in page {}", page);
        final TypedQuery<Audition> query = em.createQuery("FROM Audition as a ORDER BY a.creationDate DESC", Audition.class);
        query.setFirstResult(PAGE_SIZE * (page - 1)).setMaxResults(PAGE_SIZE);
        return query.getResultList();
    }

    // TODO: se puede hacer mejor?

    @Override
    public int getTotalPages() {
        LOGGER.info("Getting total audition page count");
        return (int) Math.ceil(((BigInteger) em.createNativeQuery("SELECT COUNT(*) FROM auditions").getSingleResult()).doubleValue() / PAGE_SIZE);
    }

    @Override
    public List<Audition> getBandAuditions(long userId, int page) {
        LOGGER.info("Getting auditions from user with id {} in page {}", userId, page);
        final TypedQuery<Audition> query = em.createQuery("FROM Audition as a WHERE a.band.id = :userId ORDER BY a.creationDate DESC", Audition.class);
        query.setParameter("userId", userId);
        query.setFirstResult(PAGE_SIZE * (page - 1)).setMaxResults(PAGE_SIZE);
        return query.getResultList();
    }

    @Override
    public int getTotalBandAuditionPages(long userId) {
        LOGGER.info("Getting total audition page count from user with id {}", userId);
        return (int) Math.ceil(((BigInteger) em.createNativeQuery("SELECT COUNT(*) FROM auditions WHERE bandId = :userId")
                .setParameter("userId", userId).getSingleResult()).doubleValue() / PAGE_SIZE);
    }

    @Override
    public void deleteAuditionById(long id) {
        LOGGER.info("Deleting audition with id {}", id);
        Optional<Audition> audition = getAuditionById(id);
        if(audition.isPresent())
            em.remove(audition);
    }

    // TODO: TERMINAR
    @Override
    public List<Audition> filter(AuditionFilter filter, int page) {
        LOGGER.info("Getting auditions filtered in page {}", page);
        String filteredTitle = filter.getTitle().equals("") ? null : "%" + filter.getTitle().replace("%", "\\%").replace("_", "\\_").toLowerCase() + "%";

        String queryString = "FROM Audition as a WHERE a.location.name IN :locationFilter AND LOWER(a.title) LIKE :titleFilter ORDER BY a.creationDate " + filter.getOrder();

        final TypedQuery<Audition> query = em.createQuery(queryString, Audition.class);
        query.setParameter("locationFilter", filter.getLocations());
        query.setParameter("titleFilter", filteredTitle);
        query.setFirstResult(PAGE_SIZE * (page - 1)).setMaxResults(PAGE_SIZE);
        return query.getResultList();
    }

    // TODO: TERMINAR

    @Override
    public int getTotalPages(AuditionFilter filter) {
        LOGGER.info("Getting total filtered audition page");
        return 0;
    }

}
