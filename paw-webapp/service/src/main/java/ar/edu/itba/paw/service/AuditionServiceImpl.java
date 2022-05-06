package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.exceptions.AuditionNotFoundException;
import ar.edu.itba.paw.AuditionFilter;
import ar.edu.itba.paw.persistence.User;
import ar.edu.itba.paw.model.exceptions.UserNotFoundException;
import ar.edu.itba.paw.persistence.Audition;
import ar.edu.itba.paw.persistence.AuditionDao;
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
    private final RoleService roleService;
    private final GenreService genreService;
    private final LocationService locationService;


    private static final Logger LOGGER = LoggerFactory.getLogger(AuditionServiceImpl.class);

    @Autowired
    public AuditionServiceImpl(final AuditionDao auditionDao,
                               final MailingService mailingService,
                               final UserService userService,
                               final MessageSource messageSource,
                               final Environment environment,
                               final RoleService roleService,
                               final GenreService genreService,
                               final LocationService locationService
    ) {
        this.auditionDao = auditionDao;
        this.mailingService = mailingService;
        this.userService = userService;
        this.messageSource = messageSource;
        this.environment = environment;
        this.locationService = locationService;
        this.roleService = roleService;
        this.genreService = genreService;
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
    public void editAuditionById(Audition.AuditionBuilder builder, long id) {
        auditionDao.editAuditionById(builder, id);
    }

    @Override
    public List<Audition> getAll(int page) {
        return auditionDao.getAll(page);
    }

    @Override
    public int getTotalPages() {
        return auditionDao.getTotalPages();
    }

    @Override
    public long getMaxAuditionId() {
        return auditionDao.getMaxAuditionId();
    }

    @Override
    public List<Audition> getBandAuditions(long userId, int page) {
        return auditionDao.getBandAuditions(userId, page);
    }

    @Override
    public int getTotalBandAuditionPages(long userId) {
        return auditionDao.getTotalBandAuditionPages(userId);
    }

    @Override
    public void deleteAuditionById(long id) {
        auditionDao.deleteAuditionById(id);
    }

    @Override
    public List<Audition> filter(AuditionFilter filter, int page) {
        return auditionDao.filter(filter, page);
    }

    @Override
    public int getFilterTotalPages(AuditionFilter filter) {
        return auditionDao.getTotalPages(filter);
    }

    @Override
    public void sendApplicationEmail(long id, User user, String message) {
        try {
            Audition aud = getAuditionById(id).orElseThrow(AuditionNotFoundException::new);
            User band = userService.getUserById(aud.getBandId()).orElseThrow(UserNotFoundException::new);
            Locale locale = LocaleContextHolder.getLocale();

            final String url = new URL(environment.getRequiredProperty("app.protocol"), environment.getRequiredProperty("app.base.url"), environment.getRequiredProperty("app.group.directory") + "/user/" + user.getId()).toString();
            Map<String, Object> mailData = new HashMap<>();
            mailData.put("content", message);
            mailData.put("goToBandifyURL", url);
            String bandEmail = band.getEmail();

            mailingService.sendEmail(user, bandEmail,
                    messageSource.getMessage("audition-application.subject",null,locale),
                    "audition-application", mailData, locale);

        } catch (MessagingException e) {
            LOGGER.warn("Audition application email threw messaging exception");
        } catch (MalformedURLException e) {
            LOGGER.warn("Audition application email threw url exception");
        }
    }
}
