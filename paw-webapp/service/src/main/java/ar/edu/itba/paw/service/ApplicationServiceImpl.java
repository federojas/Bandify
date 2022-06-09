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

    // TODO: long auditionId, long applicantId VS id

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

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationServiceImpl.class);

    @Override
    public List<Application> getAuditionApplicationsByState(long auditionId, ApplicationState state, int page) {
        User user = authFacadeService.getCurrentUser();
        Audition audition = auditionService.getAuditionById(auditionId).orElseThrow(AuditionNotFoundException::new);
        if(user.getId() != audition.getBand().getId())
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
    public boolean apply(long auditionId, User user, String message) {
        if(applicationDao.findApplication(auditionId,user.getId()).isPresent()) {
            LOGGER.info("User {} already applied to audition {}",user.getId(),auditionId);
            return false;
        }
        applicationDao.createApplication(new Application.ApplicationBuilder(auditionService.
                getAuditionById(auditionId).orElseThrow(AuditionNotFoundException::new)
                ,user,ApplicationState.PENDING, LocalDateTime.now(), message));
        Audition aud = auditionService.getAuditionById(auditionId).orElseThrow(AuditionNotFoundException::new);
        User band = userService.getUserById(aud.getBand().getId()).orElseThrow(UserNotFoundException::new);
        String bandEmail = band.getEmail();
        Locale locale = LocaleContextHolder.getLocale();
        LocaleContextHolder.setLocale(locale, true);
        LOGGER.debug("User {} applied to audition {}",user.getId(),auditionId);
        mailingService.sendApplicationEmail(user, bandEmail, message, locale);
        return true;
    }

    @Transactional
    @Override
    public void accept(long auditionId, long applicantId) {
        LOGGER.debug("User {} has been accepted for audition {}",applicantId,auditionId);
        checkIds(auditionId, applicantId);
        setApplicationState(auditionId,applicantId,ApplicationState.ACCEPTED);
    }

    @Transactional
    @Override
    public void reject(long auditionId, long applicantId) {
        LOGGER.debug("User {} has been rejected for audition {}",applicantId,auditionId);
        checkIds(auditionId, applicantId);
        setApplicationState(auditionId,applicantId,ApplicationState.REJECTED);
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

    private void setApplicationState(long auditionId, long applicantId, ApplicationState state) {
        Audition audition = auditionService.getAuditionById(auditionId).orElseThrow(AuditionNotFoundException::new);
        User band = userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(UserNotFoundException::new);
        User applicant = userService.getUserById(applicantId).orElseThrow(UserNotFoundException::new);
        if(!Objects.equals(audition.getBand().getId(), band.getId()))
            throw new AuditionNotOwnedException();
        if(state.equals(ApplicationState.ACCEPTED)) {
            Locale locale = LocaleContextHolder.getLocale();
            LocaleContextHolder.setLocale(locale, true);
            mailingService.sendApplicationAcceptedEmail(band, audition, applicant.getEmail(), locale);
        }

        Application app = applicationDao.findApplication(auditionId, applicantId).orElseThrow(ApplicationNotFoundException::new);
        app.setState(state);
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

    @Override
    public Optional<Application> getApplicationById(long auditionId, long applicationId)  {
        User user = authFacadeService.getCurrentUser();
        Audition audition = auditionService.getAuditionById(auditionId).orElseThrow(AuditionNotFoundException::new);
        if(user.getId() != audition.getBand().getId())
            throw new AuditionNotOwnedException();
        return applicationDao.findApplication(applicationId);
    }
}
