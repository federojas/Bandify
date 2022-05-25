package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class LocationJpaDao implements LocationDao {

    @PersistenceContext
    private EntityManager em;

    private static final Logger LOGGER = LoggerFactory.getLogger(LocationJpaDao.class);

    @Override
    public List<Location> getAll() {
        LOGGER.debug("Getting all locations");
        final TypedQuery<Location> query = em.createQuery("FROM Location", Location.class);
        return query.getResultList();
    }

    @Override
    public Optional<Location> getLocationByName(String name) {
        LOGGER.debug("Getting location with name {}", name);
        final TypedQuery<Location> query = em.createQuery("FROM Location AS l WHERE l.name = :name", Location.class);
        query.setParameter("name", name);
        return query.getResultList().stream().findFirst();
    }
}
