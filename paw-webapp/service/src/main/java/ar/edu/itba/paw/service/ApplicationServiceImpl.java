package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.exceptions.AuditionNotFoundException;
import ar.edu.itba.paw.model.exceptions.AuditionNotOwnedException;
import ar.edu.itba.paw.model.exceptions.UserNotFoundException;
import ar.edu.itba.paw.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ApplicationServiceImpl implements ApplicationService {
    private final ApplicationDao applicationDao;
    private final MailingService mailingService;
    private final UserService userService;
    private final AuditionService auditionService;
    private static final Logger LOGGER = LoggerFactory.getLogger(AuditionServiceImpl.class);

    @Autowired
    public ApplicationServiceImpl(final ApplicationDao applicationDao,
                                  final MailingService mailingService,
                                  final UserService userService,
                                  final AuditionService auditionService) {
        this.applicationDao = applicationDao;
        this.mailingService = mailingService;
        this.userService = userService;
        this.auditionService = auditionService;
    }

    @Override
    public List<Application> getAuditionApplicationsByState(long auditionId, ApplicationState state, int page) {
        if(state == ApplicationState.ALL)
            return applicationDao.getAuditionApplications(auditionId, page);
        return applicationDao.getAuditionApplicationsByState(auditionId,state, page);
    }

    @Transactional
    @Override
    public boolean apply(long auditionId, User user, String message) {
        if(applicationDao.exists(auditionId,user.getId()))
            return false;
        applicationDao.createApplication(new Application.ApplicationBuilder(auditionId,user.getId(),ApplicationState.PENDING, LocalDateTime.now()));
        Audition aud = auditionService.getAuditionById(auditionId).orElseThrow(AuditionNotFoundException::new);
        User band = userService.getUserById(aud.getBandId()).orElseThrow(UserNotFoundException::new);
        String bandEmail = band.getEmail();
        mailingService.sendApplicationEmail(user, bandEmail, message);
        return true;
    }

    @Transactional
    @Override
    public void accept(long auditionId, long applicantId) {
        setApplicationState(auditionId,applicantId,ApplicationState.ACCEPTED);
    }

    @Transactional
    @Override
    public void reject(long auditionId, long applicantId) {
        setApplicationState(auditionId,applicantId,ApplicationState.REJECTED);
    }

    @Override
    public List<Application> getMyApplications(long applicantId, int page) {
        return applicationDao.getMyApplications(applicantId, page);
    }

    @Override
    public int getTotalUserApplicationPages(long userId) {
        return applicationDao.getTotalUserApplicationPages(userId);
    }

    @Override
    public int getTotalUserApplicationPagesFiltered(long userId, ApplicationState state) {
        if(state == ApplicationState.ALL)
            return getTotalUserApplicationPages(userId);
        return applicationDao.getTotalUserApplicationPagesFiltered(userId, state);
    }

    @Override
    public List<Application> getMyApplicationsFiltered(long applicantId, int page, ApplicationState state) {
        if(state == ApplicationState.ALL)
            return getMyApplications(applicantId,page);
        return applicationDao.getMyApplicationsFiltered(applicantId,page,state);
    }

    @Override
    public int getTotalAuditionApplicationByStatePages(long id, ApplicationState state) {
        if(state == ApplicationState.ALL)
            return applicationDao.getTotalAuditionApplicationsPages(id);
        return applicationDao.getTotalAuditionApplicationsByStatePages(id,state);
    }

    private void setApplicationState(long auditionId, long applicantId, ApplicationState state) {
        Audition audition = auditionService.getAuditionById(auditionId).orElseThrow(AuditionNotFoundException::new);
        User band = userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(UserNotFoundException::new);
        User applicant = userService.getUserById(applicantId).orElseThrow(UserNotFoundException::new);
        if(audition.getBandId() != band.getId())
            throw new AuditionNotOwnedException();
        if(state.equals(ApplicationState.ACCEPTED)) {
            mailingService.sendApplicationAcceptedEmail(band, audition, applicant.getEmail());
        }
        applicationDao.setApplicationState(auditionId, applicantId, state);
    }

}
