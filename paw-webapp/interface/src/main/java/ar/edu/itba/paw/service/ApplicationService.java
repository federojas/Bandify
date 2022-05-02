package ar.edu.itba.paw.service;

import ar.edu.itba.paw.persistence.Application;
import ar.edu.itba.paw.persistence.ApplicationState;
import ar.edu.itba.paw.persistence.User;

import java.util.List;

public interface ApplicationService {

    List<Application> getAllApplications(long bandId);

    List<Application> getApplicationsByState(long bandId, ApplicationState state);

    List<Application> getAuditionApplications(long auditionId);

    List<Application> getAuditionApplicationsByState(long auditionId, ApplicationState state);

    void apply(long auditionId, User user, String message);

    void setApplicationState(long auditionId, long applicantId, ApplicationState state);

}
