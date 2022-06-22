package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Application;
import ar.edu.itba.paw.model.ApplicationState;

import java.util.List;
import java.util.Optional;

public interface ApplicationDao {

    List<Application> getAuditionApplicationsByState(long auditionId, ApplicationState state, int page);

    List<Application> getAuditionApplicationsByState(long auditionId, ApplicationState state);

    Optional<Application> findApplication(long auditionId, long applicantId);

    Optional<Application> findApplication(long applicationId);

    Application createApplication(Application.ApplicationBuilder applicationBuilder);

    List<Application> getMyApplications(long applicantId, int page);

    int getTotalUserApplicationPages(long userId);

    int getTotalUserApplicationPagesFiltered(long userId, ApplicationState state);

    List<Application> getMyApplicationsFiltered(long applicantId, int page, ApplicationState state);

    int getTotalAuditionApplicationsByStatePages(long auditionId, ApplicationState state);

    int getTotalUserApplicationsFiltered(long userId, ApplicationState state);

    void closeApplications(long bandId, long artistId);

}
