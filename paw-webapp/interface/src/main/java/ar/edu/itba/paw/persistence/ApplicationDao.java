package ar.edu.itba.paw.persistence;

import java.util.List;

public interface ApplicationDao {
    List<Application> getApplicationsByState(long bandId, ApplicationState state);

    List<Application> getAllApplications(long bandId);

    List<Application> getAuditionApplications(long auditionId);

    List<Application> getAuditionApplicationsByState(long auditionId, ApplicationState state);

    Application createApplication(Application.ApplicationBuilder applicationBuilder);

    void setApplicationState(long auditionId, long applicantId, ApplicationState state);

    List<Application> getMyApplications(long applicantId);
}
