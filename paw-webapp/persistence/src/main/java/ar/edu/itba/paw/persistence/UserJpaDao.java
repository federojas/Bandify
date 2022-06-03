package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Audition;
import ar.edu.itba.paw.model.FilterOptions;
import ar.edu.itba.paw.model.User;
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
public class UserJpaDao implements UserDao {

    private static final int PAGE_SIZE = 9;

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
        final TypedQuery<User> query = em.createQuery("SELECT u FROM User as u where u.email = :email", User.class);
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

    @Override
    public int getTotalPages(FilterOptions filterOptions) {

        Map<String,Object> args = new HashMap<>();
        StringBuilder sqlQueryBuilder = new StringBuilder("SELECT COUNT(DISTINCT u.uId) FROM " +
                "(SELECT DISTINCT users.id AS uId FROM users " +
                "LEFT JOIN usergenres ON id = usergenres.userid " +
                "LEFT JOIN userroles ON id = userroles.userid " +
                "LEFT JOIN locations ON users.locationid = locations.id " +
                "LEFT JOIN genres ON genres.id = usergenres.genreid " +
                "LEFT JOIN roles ON roles.id = userroles.roleid " +
                "WHERE isEnabled=true ");
        filterQuery(filterOptions, args, sqlQueryBuilder);

        sqlQueryBuilder.append(") AS u");
        Query query = em.createNativeQuery(sqlQueryBuilder.toString());

        for(Map.Entry<String,Object> entry : args.entrySet()) {
            query.setParameter(entry.getKey(),entry.getValue());
        }

        return (int) Math.ceil(((BigInteger) query.getSingleResult()).doubleValue() / PAGE_SIZE);

    }

    @Override
    public List<User> filter(FilterOptions filterOptions, int page) {
        LOGGER.info("Getting users filtered in page {}", page);

        Map<String,Object> args = new HashMap<>();
        StringBuilder sqlQueryBuilder = new StringBuilder("SELECT DISTINCT u.uId FROM " +
                "(SELECT users.id AS uId, name, surname FROM users " +
                "LEFT JOIN usergenres ON id = usergenres.userid " +
                "LEFT JOIN userroles ON id = userroles.userid " +
                "LEFT JOIN locations ON users.locationid = locations.id " +
                "LEFT JOIN genres ON genres.id = usergenres.genreid " +
                "LEFT JOIN roles ON roles.id = userroles.roleid " +
                "WHERE isEnabled=true ");
        filterQuery(filterOptions, args, sqlQueryBuilder);

        String requestedString = filterOptions.getTitle().replace("%", "\\%").replace("_", "\\_").toLowerCase();

        if(!Objects.equals(filterOptions.getTitle(), "")) {
            sqlQueryBuilder.append("ORDER BY CASE WHEN lower(CONCAT(name, ' ', surname)) LIKE '").append(requestedString).append("' THEN 1 ")
                    .append("WHEN lower(CONCAT(name, ' ', surname)) LIKE '").append(requestedString).append("%").append("' THEN 2 ")
                    .append("WHEN lower(CONCAT(name, ' ', surname)) LIKE '").append("%").append(requestedString).append("' THEN 3 ")
                    .append("ELSE 4 END");
        } else {
            sqlQueryBuilder.append("ORDER BY name, surname ASC");
        }
        sqlQueryBuilder.append(" LIMIT ").append(PAGE_SIZE).append(" OFFSET ").append((page - 1) * PAGE_SIZE).append(") AS u");


        Query query = em.createNativeQuery(sqlQueryBuilder.toString());

        for(Map.Entry<String,Object> entry : args.entrySet()) {
            query.setParameter(entry.getKey(),entry.getValue());
        }

        List<Long> ids = getUserIds(query);

        TypedQuery<User> users = em.createQuery("from User as u where u.id in :ids", User.class);
        users.setParameter("ids",ids);
        return users.getResultList();
    }


    private void filterQuery(FilterOptions filterOptions, Map<String, Object> args, StringBuilder sqlQueryBuilder) {
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
            sqlQueryBuilder.append("AND lower(CONCAT(name, ' ', surname)) LIKE :name ");
            args.put("name","%" + filterOptions.getTitle().replace("%", "\\%").
                    replace("_", "\\_").toLowerCase() + "%");
        }
    }

    private List<Long> getUserIds(Query query) {
        @SuppressWarnings("unchecked")
        List<Long> ids = (List<Long>) query.getResultList().stream().map(o -> ((Integer) o).longValue()).collect(Collectors.toList());

        if(ids.isEmpty())
            ids.add(-1L);

        return ids;
    }
}
