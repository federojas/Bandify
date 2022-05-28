package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Genre;
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
public class GenreJpaDao implements GenreDao {

    @PersistenceContext
    private EntityManager em;

    private static final Logger LOGGER = LoggerFactory.getLogger(GenreJpaDao.class);

    // TODO: revisar si seguimos usando SET o cambiamos a list
    @Override
    public Set<Genre> getAll() {
        LOGGER.debug("Getting all genres");
        final TypedQuery<Genre> query = em.createQuery("FROM Genre", Genre.class);
        final List<Genre> list = query.getResultList();
        return new HashSet<>(list);
    }

    @Override
    public Optional<Genre> getGenreById(Long id) {
        LOGGER.debug("Getting genre with id {}", id);
        return Optional.ofNullable(em.find(Genre.class, id));
    }

    @Override
    public Optional<Genre> getGenreByName(String name) {
        LOGGER.debug("Getting genre with name {}", name);
        final TypedQuery<Genre> query = em.createQuery("FROM Genre as g where g.genreName = :name", Genre.class);
        query.setParameter("name", name);
        return query.getResultList().stream().findFirst();
    }

    @Override
    public Set<Genre> getGenresByNames(List<String> genresNames) {
        LOGGER.info("Getting genres with names {}", genresNames);
        final TypedQuery<Genre> query = em.createQuery("FROM Genre as g where g.genreName in :names", Genre.class);
        query.setParameter("names", genresNames);
        final List<Genre> list = query.getResultList();
        return new HashSet<>(list);
    }

}
