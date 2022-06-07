package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Application;
import ar.edu.itba.paw.model.ApplicationState;
import ar.edu.itba.paw.model.Audition;
import ar.edu.itba.paw.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;
import static java.lang.Math.toIntExact;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:applicationDaoTest.sql")
@Rollback
@Transactional
public class ApplicationDaoTest {

    @Autowired
    private ApplicationJpaDao applicationDao;

    @Autowired
    private DataSource ds;

    private JdbcTemplate jdbcTemplate;

    private static final long APPLICANT_ID = 5;
    private static final long APPLICANT_ID_2 = 3;
    private static final long APPLICANT_ID_3 = 4;
    private static final long INVALID_ID = 20;
    private static final long PAGE_SIZE = 10;

    private static final String PWD = "12345678";
    private static final String NAME = "name";
    private static final String SURNAME = "surname";
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";

    private static final String APPLICATION_MESSAGE = "message";

    private static final List<String> BAND_EMAILS = Arrays.asList(
            "band@mail.com", "band2@mail.com"
    );

    private static final List<String> ARTIST_EMAILS = Arrays.asList(
            "artist@mail.com",
            "artist2@mail.com",
            "artist3@mail.com",
            "artist4@mail.com",
            "artist5@mail.com",
            "artist6@mail.com",
            "artist7@mail.com",
            "artist8@mail.com",
            "artist9@mail.com",
            "artist10@mail.com",
            "artist11@mail.com"
    );

    private static final List<User> BAND_USERS = Arrays.asList(
            new User.UserBuilder(BAND_EMAILS.get(0), PWD, NAME, true, false).id(1L).surname(null).description(DESCRIPTION).build(),
            new User.UserBuilder(BAND_EMAILS.get(1), PWD, NAME, true, false).id(13L).surname(null).description(DESCRIPTION).build()
    );

    private static final List<User> ARTIST_USERS = Arrays.asList(
            new User.UserBuilder(ARTIST_EMAILS.get(0), PWD, NAME, false, false).id(2L).surname(SURNAME).description(DESCRIPTION).build(),
            new User.UserBuilder(ARTIST_EMAILS.get(1), PWD, NAME, false, false).id(3L).surname(SURNAME).description(DESCRIPTION).build(),
            new User.UserBuilder(ARTIST_EMAILS.get(2), PWD, NAME, false, false).id(4L).surname(SURNAME).description(DESCRIPTION).build(),
            new User.UserBuilder(ARTIST_EMAILS.get(3), PWD, NAME, false, false).id(5L).surname(SURNAME).description(DESCRIPTION).build(),
            new User.UserBuilder(ARTIST_EMAILS.get(4), PWD, NAME, false, false).id(6L).surname(SURNAME).description(DESCRIPTION).build(),
            new User.UserBuilder(ARTIST_EMAILS.get(5), PWD, NAME, false, false).id(7L).surname(SURNAME).description(DESCRIPTION).build(),
            new User.UserBuilder(ARTIST_EMAILS.get(6), PWD, NAME, false, false).id(8L).surname(SURNAME).description(DESCRIPTION).build(),
            new User.UserBuilder(ARTIST_EMAILS.get(7), PWD, NAME, false, false).id(9L).surname(SURNAME).description(DESCRIPTION).build(),
            new User.UserBuilder(ARTIST_EMAILS.get(8), PWD, NAME, false, false).id(10L).surname(SURNAME).description(DESCRIPTION).build(),
            new User.UserBuilder(ARTIST_EMAILS.get(9), PWD, NAME, false, false).id(11L).surname(SURNAME).description(DESCRIPTION).build(),
            new User.UserBuilder(ARTIST_EMAILS.get(10), PWD, NAME, false, false).id(12L).surname(SURNAME).description(DESCRIPTION).build()
    );

    private static final LocalDateTime CREATION_DATE = LocalDateTime.of(2022 ,7, 5, 14, 23, 30);

    private static final List<Audition> AUDITIONS = Arrays.asList(
            new Audition.AuditionBuilder(TITLE, DESCRIPTION, BAND_USERS.get(0), CREATION_DATE).id(1L).build(),
            new Audition.AuditionBuilder(TITLE, DESCRIPTION, BAND_USERS.get(0), CREATION_DATE).id(2L).build()
    );


    private static final Application PENDING_APP_AUD1_1 = new Application.ApplicationBuilder(AUDITIONS.get(0), ARTIST_USERS.get(0), ApplicationState.PENDING, CREATION_DATE, APPLICATION_MESSAGE).id(1L).build();
    private static final Application PENDING_APP_AUD1_2 = new Application.ApplicationBuilder(AUDITIONS.get(0), ARTIST_USERS.get(1), ApplicationState.PENDING, CREATION_DATE, APPLICATION_MESSAGE).id(2L).build();
    private static final Application ACCEPTED_APP_AUD1_1 = new Application.ApplicationBuilder(AUDITIONS.get(0), ARTIST_USERS.get(2), ApplicationState.ACCEPTED, CREATION_DATE, APPLICATION_MESSAGE).id(3L).build();
    private static final Application REJECTED_APP_AUD1_1 = new Application.ApplicationBuilder(AUDITIONS.get(0), ARTIST_USERS.get(3), ApplicationState.REJECTED, CREATION_DATE, APPLICATION_MESSAGE).id(4L).build();
    private static final Application REJECTED_APP_AUD1_2 = new Application.ApplicationBuilder(AUDITIONS.get(0), ARTIST_USERS.get(3), ApplicationState.REJECTED, CREATION_DATE, APPLICATION_MESSAGE).id(5L).build();

    private static final Application EXTRA_PAGE_APP_1 = new Application.ApplicationBuilder(AUDITIONS.get(1), ARTIST_USERS.get(0), ApplicationState.PENDING, CREATION_DATE, APPLICATION_MESSAGE).id(6L).build();
    private static final Application EXTRA_PAGE_APP_2 = new Application.ApplicationBuilder(AUDITIONS.get(1), ARTIST_USERS.get(1), ApplicationState.PENDING, CREATION_DATE, APPLICATION_MESSAGE).id(7L).build();
    private static final Application EXTRA_PAGE_APP_3 = new Application.ApplicationBuilder(AUDITIONS.get(1), ARTIST_USERS.get(2), ApplicationState.PENDING, CREATION_DATE, APPLICATION_MESSAGE).id(8L).build();
    private static final Application EXTRA_PAGE_APP_4 = new Application.ApplicationBuilder(AUDITIONS.get(1), ARTIST_USERS.get(3), ApplicationState.PENDING, CREATION_DATE, APPLICATION_MESSAGE).id(9L).build();
    private static final Application EXTRA_PAGE_APP_5 = new Application.ApplicationBuilder(AUDITIONS.get(1), ARTIST_USERS.get(4), ApplicationState.PENDING, CREATION_DATE, APPLICATION_MESSAGE).id(10L).build();
    private static final Application EXTRA_PAGE_APP_6 = new Application.ApplicationBuilder(AUDITIONS.get(1), ARTIST_USERS.get(5), ApplicationState.PENDING, CREATION_DATE, APPLICATION_MESSAGE).id(11L).build();
    private static final Application EXTRA_PAGE_APP_7 = new Application.ApplicationBuilder(AUDITIONS.get(1), ARTIST_USERS.get(6), ApplicationState.PENDING, CREATION_DATE, APPLICATION_MESSAGE).id(12L).build();
    private static final Application EXTRA_PAGE_APP_8 = new Application.ApplicationBuilder(AUDITIONS.get(1), ARTIST_USERS.get(7), ApplicationState.PENDING, CREATION_DATE, APPLICATION_MESSAGE).id(13L).build();
    private static final Application EXTRA_PAGE_APP_9 = new Application.ApplicationBuilder(AUDITIONS.get(1), ARTIST_USERS.get(8), ApplicationState.PENDING, CREATION_DATE, APPLICATION_MESSAGE).id(14L).build();
    private static final Application EXTRA_PAGE_APP_10 = new Application.ApplicationBuilder(AUDITIONS.get(1), ARTIST_USERS.get(9), ApplicationState.PENDING, CREATION_DATE, APPLICATION_MESSAGE).id(15L).build();
    private static final Application EXTRA_PAGE_APP_11 = new Application.ApplicationBuilder(AUDITIONS.get(1), ARTIST_USERS.get(10), ApplicationState.PENDING, CREATION_DATE, APPLICATION_MESSAGE).id(16L).build();

    private static final List<Application> EXTRA_PAGE_APPS = Arrays.asList(
            EXTRA_PAGE_APP_1, EXTRA_PAGE_APP_2, EXTRA_PAGE_APP_3, EXTRA_PAGE_APP_4, EXTRA_PAGE_APP_5, EXTRA_PAGE_APP_6, EXTRA_PAGE_APP_7, EXTRA_PAGE_APP_8, EXTRA_PAGE_APP_9, EXTRA_PAGE_APP_10, EXTRA_PAGE_APP_11
    );


    private static final List<Application> PENDING_APPS_AUD1 = Arrays.asList(
            PENDING_APP_AUD1_1,
            PENDING_APP_AUD1_2
    );

    private static final List<Application> REJECTED_APPS_AUD1 = Arrays.asList(
            REJECTED_APP_AUD1_1,
            REJECTED_APP_AUD1_2
    );

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    @Test
    public void testGetAuditionApplicationsByStatePending() {
        List<Application> applications = applicationDao.getAuditionApplicationsByState(1, ApplicationState.PENDING, 1);
        assertNotNull(applications);
        assertTrue(applications.containsAll(PENDING_APPS_AUD1));
        assertEquals(PENDING_APPS_AUD1.size(), applications.size());
    }

    @Test
    public void testGetAuditionApplicationsByStateAccepted() {
        List<Application> applications = applicationDao.getAuditionApplicationsByState(1, ApplicationState.ACCEPTED,1);
        assertNotNull(applications);
        assertEquals(ACCEPTED_APP_AUD1_1, applications.get(0));
        assertEquals(1, applications.size());
    }

    @Test
    public void testGetAuditionApplicationsByStateRejected() {
        List<Application> applications = applicationDao.getAuditionApplicationsByState(1, ApplicationState.REJECTED,1);
        assertNotNull(applications);
        assertEquals(REJECTED_APP_AUD1_1, applications.get(0));
        assertEquals(2, applications.size());
    }

    @Test
    public void testGetAuditionApplicationsByFullPage() {
        List<Application> applications = applicationDao.getAuditionApplicationsByState(2, ApplicationState.PENDING, 1);
        assertNotNull(applications);
        assertTrue(EXTRA_PAGE_APPS.containsAll(applications));
        assertEquals(PAGE_SIZE, applications.size());
    }

    @Test
    public void testGetAuditionApplicationsByNotFullPage() {
        List<Application> applications = applicationDao.getAuditionApplicationsByState(2, ApplicationState.PENDING, 2);
        assertNotNull(applications);
        assertTrue(EXTRA_PAGE_APPS.containsAll(applications));
        assertEquals(1, applications.size());
    }

    @Test
    public void testCreateApplication() {
        final Application application = applicationDao.createApplication(
                new Application.
                        ApplicationBuilder(AUDITIONS.get(0), ARTIST_USERS.get(0), ApplicationState.PENDING, CREATION_DATE, APPLICATION_MESSAGE));

        assertNotNull(application);
        assertEquals(ApplicationState.PENDING, application.getState());
        assertEquals(ARTIST_USERS.get(0), application.getApplicant());
        assertEquals(CREATION_DATE,application.getCreationDate());
    }


    @Test
    public void testGetMyApplications() {
        List<Application> applications = applicationDao.getMyApplications(APPLICANT_ID, 1);
        assertNotNull(applications);

        assertTrue(applications.containsAll(REJECTED_APPS_AUD1));
        assertEquals(3, applications.size());
    }

    @Test
    public void testGetTotalUserApplicationPages() {
        int pages = applicationDao.getTotalUserApplicationPages(APPLICANT_ID);
        assertEquals(1, pages);
    }

    @Test
    public void testGetTotalInvalidUserApplicationPages() {
        int pages = applicationDao.getTotalUserApplicationPages(INVALID_ID);
        assertEquals(0, pages);
    }

    @Test
    public void testGetTotalUserApplicationFilterPages() {
        int pages = applicationDao.getTotalUserApplicationPagesFiltered(APPLICANT_ID, ApplicationState.REJECTED);
        assertEquals(1, pages);
    }

    @Test
    public void testGetTotalUserApplicationFilterPagesNone() {
        int pages = applicationDao.getTotalUserApplicationPagesFiltered(APPLICANT_ID_3, ApplicationState.REJECTED);
        assertEquals(0, pages);
    }

    @Test
    public void testGetMyApplicationsFiltered() {
        List<Application> applications = applicationDao.getMyApplicationsFiltered(APPLICANT_ID_2, 1, ApplicationState.PENDING);
        assertEquals(applications.get(0), PENDING_APP_AUD1_2);
    }

    @Test
    public void testGetMyApplicationsFilteredNone() {
        List<Application> applications = applicationDao.getMyApplicationsFiltered(APPLICANT_ID_2, 1, ApplicationState.ACCEPTED);
        assertTrue(applications.isEmpty());
    }

    @Test
    public void testGetTotalAuditionApplicationsByStatePages() {
        int pages = applicationDao.getTotalAuditionApplicationsByStatePages(1, ApplicationState.PENDING);
        assertEquals(1, pages);
    }

    @Test
    public void testGetTotalAuditionApplicationsByStatePagesZero() {
        int pages = applicationDao.getTotalAuditionApplicationsByStatePages(5, ApplicationState.ACCEPTED);
        assertEquals(0, pages);
    }

    @Test
    public void testGetTotalAuditionApplicationsByStatePagesInvalid() {
        int pages = applicationDao.getTotalAuditionApplicationsByStatePages(INVALID_ID, ApplicationState.REJECTED);
        assertEquals(0, pages);
    }

    @Test
    public void testFindApplication() {
        Optional<Application> application = applicationDao.findApplication(1, APPLICANT_ID_2);
        assertNotNull(application);
        assertTrue(application.isPresent());
        assertEquals(PENDING_APP_AUD1_2, application.get());
    }
}
