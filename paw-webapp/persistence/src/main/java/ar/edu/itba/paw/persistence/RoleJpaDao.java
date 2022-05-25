package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.Role;
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
public class RoleJpaDao implements RoleDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleJpaDao.class);

    @PersistenceContext
    private EntityManager em;

    //TODO usar List<Role> ???
    @Override
    public Set<Role> getAll() {
        LOGGER.info("Getting all roles");
        final TypedQuery<Role> query = em.createQuery("FROM Role as r", Role.class);
        return new HashSet<>(query.getResultList());
    }

    @Override
    public Optional<Role> getRoleById(Long id) {
        LOGGER.info("Getting role with id {}", id);
        return Optional.ofNullable(em.find(Role.class, id));
    }

    @Override
    public Optional<Role> getRoleByName(String name) {
        LOGGER.info("Getting role with name {}", name);
        final TypedQuery<Role> query = em.createQuery("FROM Role as r where r.name = :name", Role.class);
        query.setParameter("name", name);
        return query.getResultList().stream().findFirst();
    }

    @Override
    public Set<Role> getRolesByNames(List<String> rolesNames) {
        LOGGER.info("Getting roles with names {}", rolesNames);
        final TypedQuery<Role> query = em.createQuery("FROM Role as r where r.name in :names", Role.class);
        query.setParameter("names", rolesNames);
        return new HashSet<>(query.getResultList());
    }
}
