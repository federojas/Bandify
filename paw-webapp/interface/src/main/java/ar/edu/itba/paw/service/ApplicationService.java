package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Application;
import ar.edu.itba.paw.model.ApplicationState;
import ar.edu.itba.paw.model.User;

import java.util.List;

public interface ApplicationService {

    List<Application> getAuditionApplicationsByState(long auditionId, ApplicationState state, int page);

    boolean apply(long auditionId, User user, String message);

    void accept(long auditionId, long applicantId);

    void reject(long auditionId, long applicantId);

    List<Application> getMyApplications(long applicantId, int page);

    int getTotalUserApplicationPages(long userId);

    int getTotalUserApplicationPagesFiltered(long userId, ApplicationState state);

    List<Application> getMyApplicationsFiltered(long applicantId, int page, ApplicationState state);

    int getTotalAuditionApplicationByStatePages(long auditionId, ApplicationState state);
}
