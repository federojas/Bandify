package ar.edu.itba.paw.persistence;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.omg.PortableInterceptor.USER_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:applicationDaoTest.sql")
@Rollback
@Transactional
public class ApplicationDaoTest {

    @Autowired
    private ApplicationJdbcDao applicationDao;
    @Autowired
    private DataSource ds;

    private JdbcTemplate jdbcTemplate;

    private static final long APPLICANT_ID = 2;
    private static final long APPLICANT_ID_2 = 3;
    private static final long INVALID_ID = 20;


    private static final String NAME = "name";
    private static final String SURNAME = "surname";
    private static final String TITLE = "title";
    private static final LocalDateTime CREATION_DATE = LocalDateTime.of(2022 ,7, 5, 14, 23, 30);

    private static final Application PENDING_APP_AUD1 = new Application.ApplicationBuilder(1, APPLICANT_ID, ApplicationState.PENDING,CREATION_DATE).applicantName(NAME).applicantSurname(SURNAME).auditionTitle(TITLE).build();
    private static final Application PENDING_APP_2_AUD1 = new Application.ApplicationBuilder(1, APPLICANT_ID_2, ApplicationState.PENDING,CREATION_DATE).applicantName(NAME).applicantSurname(SURNAME).auditionTitle(TITLE).build();
    private static final Application PENDING_APP_2_AUD2 = new Application.ApplicationBuilder(2, APPLICANT_ID_2, ApplicationState.PENDING,CREATION_DATE).applicantName(NAME).applicantSurname(SURNAME).auditionTitle(TITLE).build();
    private static final Application ACCEPTED_APP_AUD2 = new Application.ApplicationBuilder(2, APPLICANT_ID, ApplicationState.ACCEPTED,CREATION_DATE).applicantName(NAME).applicantSurname(SURNAME).auditionTitle(TITLE).build();
    private static final Application REJECTED_APP_AUD3 = new Application.ApplicationBuilder(3, APPLICANT_ID, ApplicationState.REJECTED,CREATION_DATE).applicantName(NAME).applicantSurname(SURNAME).auditionTitle(TITLE).build();

    private static final List<Application> PENDING_APPS_AUD1 = Arrays.asList(PENDING_APP_AUD1, PENDING_APP_2_AUD1);
    private static final List<Application> APPLICANT_APPS = Arrays.asList(PENDING_APP_AUD1, ACCEPTED_APP_AUD2, REJECTED_APP_AUD3);
    private static final List<Application> PENDING_APPS_APPLICANT_2 = Arrays.asList(PENDING_APP_2_AUD2, PENDING_APP_2_AUD1);


    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    @Test
    public void testGetAuditionApplicationsByStatePending() {
        List<Application> applications = applicationDao.getAuditionApplicationsByState(1, ApplicationState.PENDING);
        assertNotNull(applications);
        assertTrue(PENDING_APPS_AUD1.containsAll(applications));
        assertTrue(!Collections.singletonList(PENDING_APP_2_AUD2).containsAll(applications));
        assertEquals(PENDING_APPS_AUD1.size(), applications.size());
    }

    @Test
    public void testGetAuditionApplicationsByStateAccepted() {
        List<Application> applications = applicationDao.getAuditionApplicationsByState(2, ApplicationState.ACCEPTED);
        assertNotNull(applications);
        assertTrue(Collections.singletonList(ACCEPTED_APP_AUD2).containsAll(applications));
        assertEquals(1, applications.size());
    }

    @Test
    public void testGetAuditionApplicationsByStateRejected() {
        List<Application> applications = applicationDao.getAuditionApplicationsByState(3, ApplicationState.REJECTED);
        assertNotNull(applications);
        assertTrue(Collections.singletonList(REJECTED_APP_AUD3).containsAll(applications));
        assertEquals(1, applications.size());
    }

    @Test
    public void testCreateApplication() {
        final Application application = applicationDao.createApplication(new Application.ApplicationBuilder(3, APPLICANT_ID_2, ApplicationState.PENDING, CREATION_DATE).applicantName(NAME).applicantSurname(SURNAME).auditionTitle(TITLE));
        assertNotNull(application);
        assertEquals(ApplicationState.PENDING, application.getState());
        assertEquals(NAME, application.getApplicantName());
        assertEquals(SURNAME, application.getApplicantSurname());
        assertEquals(TITLE, application.getAuditionTitle());
        assertEquals(APPLICANT_ID_2 ,application.getApplicantId());
        assertEquals(3,application.getAuditionId());
        assertEquals(CREATION_DATE,application.getCreationDate());
    }

    @Test
    public void testSetApplicationState() {
        applicationDao.setApplicationState(1, APPLICANT_ID, ApplicationState.ACCEPTED);
        assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "applications", "auditionid = 1 AND applicantid = " + APPLICANT_ID + " AND state = '" + ApplicationState.ACCEPTED.getState() + "'"));
        assertEquals(0, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "applications", "auditionid = 1 AND applicantid = " + APPLICANT_ID + " AND state = '" + ApplicationState.PENDING.getState() + "'"));
        assertEquals(0, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "applications", "auditionid = 1 AND applicantid = " + APPLICANT_ID + " AND state = '" + ApplicationState.REJECTED.getState() + "'"));
    }

    @Test
    public void testGetMyApplications() {
        List<Application> applications = applicationDao.getMyApplications(APPLICANT_ID, 1);
        assertNotNull(applications);
        assertTrue(APPLICANT_APPS.containsAll(applications));
        assertEquals(APPLICANT_APPS.size(), applications.size());
    }

    @Test
    public void testApplicationExists() {
        boolean exists = applicationDao.exists(1, APPLICANT_ID);
        assertTrue(exists);
    }

    @Test
    public void testApplicationNotExists() {
        boolean exists = applicationDao.exists(3, APPLICANT_ID_2);
        assertFalse(exists);
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
        int pages = applicationDao.getTotalUserApplicationPagesFiltered(APPLICANT_ID, ApplicationState.ACCEPTED);
        assertEquals(1, pages);
    }

    @Test
    public void testGetTotalUserApplicationFilterPagesNone() {
        int pages = applicationDao.getTotalUserApplicationPagesFiltered(APPLICANT_ID_2, ApplicationState.ACCEPTED);
        assertEquals(0, pages);
    }

    @Test
    public void testGetMyApplicationsFiltered() {
        List<Application> applications = applicationDao.getMyApplicationsFiltered(APPLICANT_ID_2, 1, ApplicationState.PENDING);
        assertTrue(PENDING_APPS_APPLICANT_2.containsAll(applications));
        assertEquals(PENDING_APPS_APPLICANT_2.size(), applications.size());
    }

    @Test
    public void testGetMyApplicationsFilteredNone() {
        List<Application> applications = applicationDao.getMyApplicationsFiltered(APPLICANT_ID_2, 1, ApplicationState.ACCEPTED);
        assertTrue(applications.isEmpty());
    }

}
