package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Application;
import ar.edu.itba.paw.model.ApplicationState;
import ar.edu.itba.paw.model.Audition;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ApplicationJpaDao implements ApplicationDao {

    private final int PAGE_SIZE = 10;

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Application> getAuditionApplicationsByState(long auditionId, ApplicationState state, int page) {
        Query query = em.createNativeQuery("SELECT DISTINCT a.id FROM " +
                "(SELECT id, creationDate FROM applications " +
                "WHERE auditionId = :auditionId AND state = :state " +
                " LIMIT " + PAGE_SIZE + " OFFSET " + (page-1) * PAGE_SIZE + " ) AS a");
        query.setParameter("auditionId", auditionId);
        query.setParameter("state", state.getState());

        List<Long> ids = getApplicationIds(query);

        TypedQuery<Application> applications = em.createQuery("from Application as a where a.id in :ids ORDER BY a.creationDate DESC", Application.class);
        applications.setParameter("ids",ids);
        return applications.getResultList();
    }

    @Override
    public Optional<Application> findApplication(long auditionId, long applicantId) {
        final TypedQuery<Application> query = em.createQuery("FROM Application as a where a.applicant.id =:applicantId and  a.audition.id =:auditionId",
                Application.class);
        query.setParameter("applicantId", applicantId);
        query.setParameter("auditionId", auditionId);
        return query.getResultList().stream().findFirst();
    }

    @Override
    public Application createApplication(Application.ApplicationBuilder applicationBuilder) {
        final Application application = applicationBuilder.build();
        em.persist(application);
        return application;
    }

    @Override
    public List<Application> getMyApplications(long applicantId, int page) {

        Query query = em.createNativeQuery("SELECT DISTINCT a.id FROM (SELECT id, creationDate FROM applications WHERE applicantId = :applicantId ORDER BY creationDate DESC LIMIT "+ PAGE_SIZE + " OFFSET " + (page-1) * PAGE_SIZE + " ) AS a");
        query.setParameter("applicantId", applicantId);

        List<Long> ids = getApplicationIds(query);

        TypedQuery<Application> applications = em.createQuery("from Application as a where a.id in :ids ORDER BY a.creationDate DESC", Application.class);
        applications.setParameter("ids",ids);
        return applications.getResultList();
    }

    @Override
    public List<Application> getMyApplicationsFiltered(long applicantId, int page, ApplicationState state) {

        Query query = em.createNativeQuery(
                "SELECT DISTINCT a.id FROM " +
                        "(SELECT id, creationDate FROM applications" +
                        " WHERE applicantId = :applicantId AND state = :state ORDER BY creationDate DESC LIMIT " + PAGE_SIZE + " OFFSET " + (page-1) * PAGE_SIZE + ") AS a");
        query.setParameter("applicantId", applicantId);
        query.setParameter("state", state.getState());

        List<Long> ids = getApplicationIds(query);

        TypedQuery<Application> applications = em.createQuery("from Application as a where a.id in :ids ORDER BY a.creationDate DESC", Application.class);
        applications.setParameter("ids",ids);
        return applications.getResultList();
    }

    @Override
    public int getTotalAuditionApplicationsByStatePages(long auditionId, ApplicationState state) {
        return (int) Math.ceil(((BigInteger) em.createNativeQuery(
                "SELECT COUNT(*) FROM applications WHERE auditionId = :auditionId AND state=cast(:state AS text)")
                .setParameter("auditionId", auditionId)
                .setParameter("state", state.getState()).getSingleResult()).doubleValue() / PAGE_SIZE);
    }

    @Override
    public int getTotalUserApplicationPages(long userId) {
        return (int) Math.ceil(((BigInteger) em.createNativeQuery(
                "SELECT COUNT(*) FROM applications WHERE applicantId = :applicantId")
                .setParameter("applicantId", userId).getSingleResult()).doubleValue() / PAGE_SIZE);
    }

    @Override
    public int getTotalUserApplicationPagesFiltered(long userId, ApplicationState state) {
        return (int) Math.ceil(((BigInteger) em.createNativeQuery(
                "SELECT COUNT(*) FROM applications WHERE applicantid=:applicantId AND state=cast(:state AS text)")
                .setParameter("applicantId", userId)
                .setParameter("state", state.getState()).getSingleResult()).doubleValue() / PAGE_SIZE);
    }

    private List<Long> getApplicationIds(Query query) {
        @SuppressWarnings("unchecked")
        List<Long> ids = (List<Long>) query.getResultList().stream().map(o -> ((Integer) o).longValue()).collect(Collectors.toList());

        if(ids.isEmpty())
            ids.add(-1L);

        return ids;
    }
}
