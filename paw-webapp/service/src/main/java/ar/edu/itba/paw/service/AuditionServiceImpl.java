package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.User;
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

    @Autowired
    private MessageSource messageSource;

    private static final Logger LOGGER = LoggerFactory.getLogger(AuditionServiceImpl.class);

    @Autowired
    public AuditionServiceImpl(AuditionDao auditionDao, MailingService mailingService) {
        this.auditionDao = auditionDao;
        this.mailingService = mailingService;
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
                //final String url = new URL("http", environment.getRequiredProperty("app.base.url"), "/paw-2022a-03/").toString();
                final String url = new URL("http","localhost", "/").toString();
                Map<String, Object> mailData = new HashMap<>();
                mailData.put("content", message);
                mailData.put("goToBandifyURL", url);

                mailingService.sendEmail(user, aud.get().getEmail(),
                        messageSource.getMessage("audition-application.subject",null,locale),
                        "audition-application", mailData, locale);
            }
        } catch (MessagingException e) {
            LOGGER.warn("Audition application email threw messaging exception");
        } catch (MalformedURLException e) {
            LOGGER.warn("Audition application email threw url exception");
        }
    }
}
