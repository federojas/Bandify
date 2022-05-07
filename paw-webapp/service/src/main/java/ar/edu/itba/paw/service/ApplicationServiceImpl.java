package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.exceptions.AuditionNotFoundException;
import ar.edu.itba.paw.model.exceptions.NoPermissionsException;
import ar.edu.itba.paw.model.exceptions.UserNotFoundException;
import ar.edu.itba.paw.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.env.Environment;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

@Service
public class ApplicationServiceImpl implements ApplicationService {
    private final ApplicationDao applicationDao;
    private final MailingService mailingService;
    private final UserService userService;
    private final AuditionService auditionService;
    private final Environment environment;
    private final MessageSource messageSource;
    private static final Logger LOGGER = LoggerFactory.getLogger(AuditionServiceImpl.class);

    @Autowired
    public ApplicationServiceImpl(final ApplicationDao applicationDao,
                                  final MailingService mailingService,
                                  final UserService userService,
                                  final AuditionService auditionService,
                                  final Environment environment,
                                  final MessageSource messageSource) {
        this.applicationDao = applicationDao;
        this.mailingService = mailingService;
        this.userService = userService;
        this.auditionService = auditionService;
        this.environment = environment;
        this.messageSource = messageSource;
    }


    @Override
    public List<Application> getAllApplications(long bandId) {
        return applicationDao.getAllApplications(bandId);
    }

    @Override
    public List<Application> getApplicationsByState(long bandId, ApplicationState state) {
        return applicationDao.getApplicationsByState(bandId, state);
    }

    @Override
    public List<Application> getAuditionApplications(long auditionId) {
        return applicationDao.getAuditionApplications(auditionId);
    }

    @Override
    public List<Application> getAuditionApplicationsByState(long auditionId, ApplicationState state) {
        return applicationDao.getAuditionApplicationsByState(auditionId,state);
    }

    @Override
    public boolean apply(long auditionId, User user, String message) {
        if(applicationDao.exists(auditionId,user.getId()))
            return false;
        applicationDao.createApplication(new Application.ApplicationBuilder(auditionId,user.getId(),ApplicationState.PENDING));
        try {
            Optional<Audition> aud = auditionService.getAuditionById(auditionId);
            if (aud.isPresent()) {
                Locale locale = LocaleContextHolder.getLocale();
                final String url = new URL("http", environment.getRequiredProperty("app.base.url"), "/paw-2022a-03/").toString();
                Map<String, Object> mailData = new HashMap<>();
                mailData.put("content", message);
                mailData.put("goToBandifyURL", url);
                Optional<User> band = userService.getUserById(aud.get().getBandId());
                if(band.isPresent()) {
                    String bandEmail = band.get().getEmail();
                    mailingService.sendEmail(user, bandEmail,
                            messageSource.getMessage("audition-application.subject",null,locale),
                            "audition-application", mailData, locale);
                } else {
                    throw new UserNotFoundException();
                }
            }
        } catch (MessagingException e) {
            LOGGER.warn("Audition application email threw messaging exception");
        } catch (MalformedURLException e) {
            LOGGER.warn("Audition application email threw url exception");
        }
        return true;
    }

    @Override
    public void accept(long auditionId, long applicantId) {
        setApplicationState(auditionId,applicantId,ApplicationState.ACCEPTED);
    }

    @Override
    public void reject(long auditionId, long applicantId) {
        setApplicationState(auditionId,applicantId,ApplicationState.REJECTED);
    }

    @Override
    public List<Application> getMyApplications(long applicantId) {
        return applicationDao.getMyApplications(applicantId);
    }

    private void setApplicationState(long auditionId, long applicantId, ApplicationState state) {
        checkPermissions(auditionId);
        applicationDao.setApplicationState(auditionId, applicantId, state);
    }

    private void checkPermissions(long auditionId) {
        if(auditionService.getAuditionById(auditionId).orElseThrow(AuditionNotFoundException::new).getBandId() !=
                userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(UserNotFoundException::new).getId())
            throw new NoPermissionsException();
    }
}
