package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

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

        Query query = em.createNativeQuery("SELECT DISTINCT a.id FROM (SELECT id, creationDate FROM auditions ORDER BY creationDate DESC LIMIT " + PAGE_SIZE + " OFFSET " + (page-1) * PAGE_SIZE + ") AS a");

        List<Long> ids = getAuditionIds(query);

        TypedQuery<Audition> auditions = em.createQuery("from Audition as a where a.id in :ids ORDER BY a.creationDate DESC", Audition.class);
        auditions.setParameter("ids",ids);
        return auditions.getResultList();
    }

    @Override
    public int getTotalPages() {
        LOGGER.info("Getting total audition page count");
        return (int) Math.ceil(((BigInteger) em.createNativeQuery("SELECT COUNT(*) FROM auditions").getSingleResult()).doubleValue() / PAGE_SIZE);
    }

    @Override
    public List<Audition> getBandAuditions(long userId, int page) {
        LOGGER.info("Getting auditions from user with id {} in page {}", userId, page);

        Query query = em.createNativeQuery("SELECT DISTINCT a.id FROM (SELECT id, creationDate FROM auditions WHERE bandId = :userId ORDER BY creationDate DESC LIMIT " + PAGE_SIZE + " OFFSET " + (page-1) * PAGE_SIZE + ") AS a");
        query.setParameter("userId", userId);

        List<Long> ids = getAuditionIds(query);

        TypedQuery<Audition> auditions = em.createQuery("from Audition as a where a.id in :ids ORDER BY a.creationDate DESC", Audition.class);
        auditions.setParameter("ids",ids);
        return auditions.getResultList();
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
    public List<Audition> filter(FilterOptions filterOptions, int page) {
        LOGGER.info("Getting auditions filtered in page {}", page);

        Map<String,Object> args = new HashMap<>();
        StringBuilder sqlQueryBuilder = new StringBuilder("SELECT DISTINCT a.aId FROM " +
                "(SELECT DISTINCT auditions.id as aId, creationdate FROM auditions " +
                "JOIN auditiongenres ON id = auditiongenres.auditionid " +
                "JOIN auditionroles ON id = auditionroles.auditionid " +
                "JOIN locations ON auditions.locationid = locations.id " +
                "JOIN genres ON genres.id = auditiongenres.genreid " +
                "JOIN roles ON roles.id = auditionroles.roleid ");
        filterQuery(filterOptions, args, sqlQueryBuilder);
        sqlQueryBuilder.append("ORDER BY creationdate ").append(filterOptions.getOrder()).append(" LIMIT ").append(PAGE_SIZE).append(" OFFSET ").append((page - 1) * PAGE_SIZE).append(") AS a");

        Query query = em.createNativeQuery(sqlQueryBuilder.toString());

        for(Map.Entry<String,Object> entry : args.entrySet()) {
            query.setParameter(entry.getKey(),entry.getValue());
        }

        List<Long> ids = getAuditionIds(query);

        TypedQuery<Audition> auditions = em.createQuery("from Audition as a where a.id in :ids ORDER BY a.creationDate " + filterOptions.getOrder(), Audition.class);
        auditions.setParameter("ids",ids);
        return auditions.getResultList();
    }

    @Override
    public int getTotalPages(FilterOptions filterOptions) {

        Map<String,Object> args = new HashMap<>();
        StringBuilder sqlQueryBuilder = new StringBuilder("SELECT COUNT(DISTINCT a.aId) FROM " +
                "(SELECT DISTINCT auditions.id as aId, creationdate FROM auditions " +
                "JOIN auditiongenres ON id = auditiongenres.auditionid " +
                "JOIN auditionroles ON id = auditionroles.auditionid " +
                "JOIN locations ON auditions.locationid = locations.id " +
                "JOIN genres ON genres.id = auditiongenres.genreid " +
                "JOIN roles ON roles.id = auditionroles.roleid ");
        filterQuery(filterOptions, args, sqlQueryBuilder);

        sqlQueryBuilder.append(") AS a");
        Query query = em.createNativeQuery(sqlQueryBuilder.toString());

        for(Map.Entry<String,Object> entry : args.entrySet()) {
            query.setParameter(entry.getKey(),entry.getValue());
        }

        return (int) Math.ceil(((BigInteger) query.getSingleResult()).doubleValue() / PAGE_SIZE);

    }

    private void filterQuery(FilterOptions filterOptions, Map<String, Object> args, StringBuilder sqlQueryBuilder) {
        sqlQueryBuilder.append("WHERE true ");
        if(!filterOptions.getGenresNames().isEmpty()) {
            sqlQueryBuilder.append("AND genre IN (:genresNames) ");
            args.put("genresNames",filterOptions.getGenresNames());
        }
        if(!filterOptions.getRolesNames().isEmpty()) {
            sqlQueryBuilder.append("AND role IN (:rolesNames) ");
            args.put("rolesNames",filterOptions.getRolesNames());
        }
        if(!filterOptions.getLocations().isEmpty()) {
            sqlQueryBuilder.append("AND location IN (:locations) ");
            args.put("locations",filterOptions.getLocations());
        }
        if(!filterOptions.getTitle().equals("")) {
            sqlQueryBuilder.append("AND lower(title) LIKE :title ");
            args.put("title","%" + filterOptions.getTitle().replace("%", "\\%").
                    replace("_", "\\_").toLowerCase() + "%");
        }
    }

    private List<Long> getAuditionIds(Query query) {
        @SuppressWarnings("unchecked")
        List<Long> ids = (List<Long>) query.getResultList().stream().map(o -> ((Integer) o).longValue()).collect(Collectors.toList());

        if(ids.isEmpty())
            ids.add(-1L);

        return ids;
    }

}
