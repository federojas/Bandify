package ar.edu.itba.paw.service;

import ar.edu.itba.paw.persistence.*;
import ar.edu.itba.paw.model.exceptions.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

@Service
public class AuditionServiceImpl implements AuditionService {

    private final AuditionDao auditionDao;
    private final MailingService mailingService;
    private final UserService userService;
    private final MessageSource messageSource;
    private final Environment environment;

    private static final Logger LOGGER = LoggerFactory.getLogger(AuditionServiceImpl.class);

    @Autowired
    public AuditionServiceImpl(final AuditionDao auditionDao,
                               final MailingService mailingService,
                               final UserService userService,
                               final MessageSource messageSource,
                               final Environment environment) {
        this.auditionDao = auditionDao;
        this.mailingService = mailingService;
        this.userService = userService;
        this.messageSource = messageSource;
        this.environment = environment;
    }

    @Override
    public Optional<Audition> getAuditionById(long id) {
        return auditionDao.getAuditionById(id);
    }

    @Override
    public Audition create(Audition.AuditionBuilder builder) {
        return auditionDao.create(builder);
    }

    @Override
    public List<Audition> getAll(int page) {
        return auditionDao.getAll(page);
    }

    @Override
    public int getTotalPages(String query) {
        return auditionDao.getTotalPages(query);
    }

    @Override
    public long getMaxAuditionId() {
        return auditionDao.getMaxAuditionId();
    }

    @Override
    public List<Audition> search(int page, String query) {
        return auditionDao.search(page, query);
    }

    @Override
    public List<Audition> getBandAuditions(long userId) {
        return auditionDao.getBandAuditions(userId);
    }

    @Override
    public void sendApplicationEmail(long id, User user, String message) {
        try {
            Optional<Audition> aud = getAuditionById(id);
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
                }else {
                    throw new UserNotFoundException();
                }

            }
        } catch (MessagingException e) {
            LOGGER.warn("Audition application email threw messaging exception");
        } catch (MalformedURLException e) {
            LOGGER.warn("Audition application email threw url exception");
        }
    }

    @Override
    public List<Application> getAllApplications(long bandId) {
        return auditionDao.getAllApplications(bandId);
    }

    @Override
    public List<Application> getApplicationsByState(long bandId, ApplicationState state) {
        return auditionDao.getApplicationsByState(bandId, state);
    }
}
