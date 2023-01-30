package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Application;
import ar.edu.itba.paw.model.ApplicationState;
import ar.edu.itba.paw.model.Audition;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.exceptions.*;
import ar.edu.itba.paw.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    @Autowired
    private ApplicationDao applicationDao;
    @Autowired
    private MailingService mailingService;
    @Autowired
    private UserService userService;
    @Autowired
    private AuditionService auditionService;
    @Autowired
    private AuthFacadeService authFacadeService;
    @Autowired
    private MembershipService membershipService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationServiceImpl.class);

    // TODO: saque el chequeo de owner porque no tenemos security todavia
    @Override
    public List<Application> getAuditionApplicationsByState(long auditionId, ApplicationState state, int page) {
        User user = authFacadeService.getCurrentUser();
        Audition audition = auditionService.getAuditionById(auditionId);

        if(!Objects.equals(user.getId(), audition.getBand().getId()))
            throw new AuditionNotOwnedException();

        int lastPage = getTotalAuditionApplicationByStatePages(auditionId, state);
        lastPage = lastPage == 0 ? 1 : lastPage;
        checkPage(page, lastPage);
        if(state == ApplicationState.ALL)
            state = ApplicationState.PENDING;

        return applicationDao.getAuditionApplicationsByState(auditionId,state, page);
    }

    @Transactional
    @Override
    public Application apply(long auditionId, User user, String message) {
        if(user.isBand()) throw new BandsCannotApplyException();
        if(applicationDao.findApplication(auditionId,user.getId()).isPresent()) {
            LOGGER.info("User {} already applied to audition {}",user.getId(),auditionId);
            throw new UserAlreadyAppliedToAuditionException();
        }

        Audition aud = auditionService.getAuditionById(auditionId);
        User band = userService.getUserById(aud.getBand().getId()).orElseThrow(UserNotFoundException::new);
        if(membershipService.isInBand(band, user)) {
            LOGGER.info("User {} already in band ",user.getId());
            throw new UserAlreadyInBandException();
        }

        Application toReturn = applicationDao.createApplication(new Application.ApplicationBuilder(auditionService.
                getAuditionById(auditionId),user,ApplicationState.PENDING, LocalDateTime.now(), message));
        String bandEmail = band.getEmail();
        Locale locale = LocaleContextHolder.getLocale();
        LocaleContextHolder.setLocale(locale, true);
        LOGGER.debug("User {} applied to audition {}",user.getId(),auditionId);
        mailingService.sendApplicationEmail(user, bandEmail, message, locale);
        return toReturn;
    }

    @Transactional
    @Override
    public Application accept(long auditionId, long applicantId) {
        LOGGER.debug("User {} has been accepted for audition {}",applicantId,auditionId);
        checkIds(auditionId, applicantId);
        return setApplicationState(auditionId,applicantId,ApplicationState.ACCEPTED);
    }

    @Transactional
    @Override
    public Application reject(long auditionId, long applicantId) {
        LOGGER.debug("User {} has been rejected for audition {}",applicantId,auditionId);
        checkIds(auditionId, applicantId);
        return setApplicationState(auditionId,applicantId,ApplicationState.REJECTED);
    }

    @Transactional
    @Override
    public Application select(long auditionId, long bandId, long applicantId) {
        LOGGER.debug("User {} has been selected for the audition {}",applicantId, auditionId);
        checkIds(auditionId, applicantId);
        Application toReturn = setApplicationState(auditionId,applicantId,ApplicationState.SELECTED);
        closeApplications(bandId,applicantId);
        return toReturn;
    }

    @Override
    public void closeApplications(long bandId, long applicantId) {
        applicationDao.closeApplications(bandId,applicantId);
    }

    @Override
    public List<Application> getMyApplications(long applicantId, int page) {
        int lastPage = getTotalUserApplicationPages(applicantId);
        lastPage = lastPage == 0 ? 1 : lastPage;
        checkPage(page, lastPage);
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
    public int getTotalUserApplicationsFiltered(long userId, ApplicationState state) {
        return applicationDao.getTotalUserApplicationsFiltered(userId, state);
    }


    @Override
    public List<Application> getMyApplicationsFiltered(long applicantId, int page, ApplicationState state) {
        int lastPage = getTotalUserApplicationPagesFiltered(applicantId, state);
        lastPage = lastPage == 0 ? 1 : lastPage;
        checkPage(page, lastPage);
        if(state == ApplicationState.ALL)
            return getMyApplications(applicantId,page);
        return applicationDao.getMyApplicationsFiltered(applicantId,page,state);
    }

    @Override
    public int getTotalAuditionApplicationByStatePages(long auditionId, ApplicationState state) {
        if(state == ApplicationState.ALL)
            state = ApplicationState.PENDING;
        return applicationDao.getTotalAuditionApplicationsByStatePages(auditionId,state);
    }

    private Application setApplicationState(long auditionId, long applicantId, ApplicationState state) {
        Audition audition = auditionService.getAuditionById(auditionId);
        User applicant = userService.getUserById(applicantId).orElseThrow(UserNotFoundException::new);
        User band = userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(UserNotFoundException::new);
        if(!Objects.equals(audition.getBand().getId(), band.getId()))
            throw new AuditionNotOwnedException();
        if(state.equals(ApplicationState.ACCEPTED)) {
            Locale locale = LocaleContextHolder.getLocale();
            LocaleContextHolder.setLocale(locale, true);
            mailingService.sendApplicationAcceptedEmail(band, audition, applicant.getEmail(), locale);
        }

        Application app = applicationDao.findApplication(auditionId, applicantId).orElseThrow(ApplicationNotFoundException::new);
        app.setState(state);
        return app;
    }

    private void checkPage(int page, int lastPage) {
        if(page <= 0)
            throw new IllegalArgumentException();
        if(page > lastPage)
            throw new PageNotFoundException();
    }

    private void checkIds(long auditionId, long applicantId) {
            if(auditionId <= 0 || applicantId <= 0)
                throw new IllegalArgumentException();
    }

    @Override
    public boolean alreadyApplied(long auditionId, long applicantId) {
        return applicationDao.findApplication(auditionId,applicantId).isPresent();
    }

    // TODO: descomentar authfacade
    @Override
    public Optional<Application> getApplicationById(long auditionId, long applicationId)  {
        User user = authFacadeService.getCurrentUser();
        Audition audition = auditionService.getAuditionById(auditionId);
        if(!Objects.equals(user.getId(), audition.getBand().getId()))
            throw new AuditionNotOwnedException();
        Optional<Application> application = applicationDao.findApplication(applicationId);
        if (application.isPresent() && !application.get().getAudition().getId().equals(auditionId)
                && application.get().getState().equals(ApplicationState.SELECTED))
            return Optional.empty();
        return application;
    }

    @Override
    public Optional<Application> getAcceptedApplicationById(long auditionId, long applicationId)  {
        Optional<Application> application = getApplicationById(auditionId,applicationId);
        if(application.isPresent() && application.get().getAudition().getId().equals(auditionId) &&
                application.get().getState().equals(ApplicationState.ACCEPTED))
            return application;
        return Optional.empty();
    }

    @Transactional
    @Override
    public boolean closeApplicationsByAuditionId(long id) {
        if(id < 0)
            throw new IllegalArgumentException();
        User user = authFacadeService.getCurrentUser();
        Audition audition = auditionService.getAuditionById(id);
        if(!Objects.equals(user.getId(), audition.getBand().getId()))
            throw new AuditionNotOwnedException();
        for (Application app : getAuditionApplicationsByState(id, ApplicationState.PENDING)) {
            app.setState(ApplicationState.REJECTED);
        }
        return true;
    }

    @Override
    public List<Application> getAuditionApplicationsByState(long auditionId, ApplicationState state) {
        User user = authFacadeService.getCurrentUser();
        Audition audition = auditionService.getAuditionById(auditionId);
        if(!Objects.equals(user.getId(), audition.getBand().getId()))
            throw new AuditionNotOwnedException();
        if(state == ApplicationState.ALL)
            state = ApplicationState.PENDING;
        return applicationDao.getAuditionApplicationsByState(auditionId,state);
    }
}
