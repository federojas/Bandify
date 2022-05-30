package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.math.BigInteger;
import java.util.*;

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

    @Override
    public List<Audition> filter(AuditionFilter auditionFilter, int page) {
        LOGGER.info("Getting auditions filtered in page {}", page);

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Audition> cq = cb.createQuery(Audition.class);
        Root<Audition> root = cq.from(Audition.class);

        List<Predicate> predicates = new ArrayList<>();

        if(!auditionFilter.getLocations().isEmpty())
            predicates.add(root.get("location").in(auditionFilter.getLocations()));

        if(!auditionFilter.getTitle().equals("")) {
            String title =  "%" + auditionFilter.getTitle().replace("%", "\\%").
                            replace("_", "\\_").toLowerCase() + "%";
            predicates.add(cb.like(cb.lower(root.get("title")), title));
        }

        List<Predicate> genrePredicates = new ArrayList<>();
        Expression<Set<Genre>> genres = root.get("musicGenres");
        for(Genre genre : auditionFilter.getGenres()) {
            genrePredicates.add(cb.isMember(genre,genres));
        }
        if(!genrePredicates.isEmpty()) {
            Predicate genreOrPredicate = cb.or(genrePredicates.toArray(new Predicate[]{}));
            predicates.add(genreOrPredicate);
        }

        List<Predicate> rolePredicates = new ArrayList<>();
        Expression<Set<Role>> roles = root.get("lookingFor");
        for(Role role : auditionFilter.getRoles()) {
            rolePredicates.add(cb.isMember(role,roles));
        }
        if(!rolePredicates.isEmpty()) {
            Predicate roleOrPredicate = cb.or(rolePredicates.toArray(new Predicate[]{}));
            predicates.add(roleOrPredicate);
        }

        cq.where(cb.and(predicates.toArray(new Predicate[]{})));

        if(Objects.equals(auditionFilter.getOrder(), "DESC"))
            cq.orderBy(cb.desc(root.get("creationDate")));
        else
            cq.orderBy(cb.asc(root.get("creationDate")));

        return em.createQuery(cq).setFirstResult(PAGE_SIZE * (page - 1)).
                setMaxResults(PAGE_SIZE).getResultList();
    }

    // TODO: FALTA ESTO
    @Override
    public int getTotalPages(AuditionFilter auditionFilter) {
        LOGGER.info("Getting total filtered audition pages");

        return 0;
    }


}
