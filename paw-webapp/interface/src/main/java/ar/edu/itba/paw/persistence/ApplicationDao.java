package ar.edu.itba.paw.persistence;

import java.util.List;

public interface ApplicationDao {
    List<Application> getApplicationsByState(long bandId, ApplicationState state);

    List<Application> getAllApplications(long bandId);

}
