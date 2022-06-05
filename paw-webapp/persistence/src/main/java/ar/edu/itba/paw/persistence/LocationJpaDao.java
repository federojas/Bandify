package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Genre;
import ar.edu.itba.paw.model.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public class LocationJpaDao implements LocationDao {

    @PersistenceContext
    private EntityManager em;

    private static final Logger LOGGER = LoggerFactory.getLogger(LocationJpaDao.class);

    @Override
    public List<Location> getAll() {
        LOGGER.debug("Getting all locations");
        final TypedQuery<Location> query = em.createQuery("FROM Location as l ORDER BY l.name ASC", Location.class);
        return query.getResultList();
    }

    @Override
    public Optional<Location> getLocationByName(String name) {
        LOGGER.debug("Getting location with name {}", name);
        final TypedQuery<Location> query = em.createQuery("FROM Location AS l WHERE l.name = :name", Location.class);
        query.setParameter("name", name);
        return query.getResultList().stream().findFirst();
    }

    @Override
    public Set<Location> getLocationsByNames(List<String> locationNames) {
        LOGGER.info("Getting locations with names {}", locationNames);
        final TypedQuery<Location> query = em.createQuery("FROM Location as l where l.name in :names", Location.class);
        query.setParameter("names", locationNames);
        final List<Location> list = query.getResultList();
        return new HashSet<>(list);
    }
}
