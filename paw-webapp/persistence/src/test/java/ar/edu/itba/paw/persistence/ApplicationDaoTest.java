package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.Application;
import ar.edu.itba.paw.ApplicationState;
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
    private static final long APPLICANT_ID_3 = 4;
    private static final long INVALID_ID = 20;
    private static final long PAGE_SIZE = 10;


    private static final String NAME = "name";
    private static final String SURNAME = "surname";
    private static final String TITLE = "title";
    private static final LocalDateTime CREATION_DATE = LocalDateTime.of(2022 ,7, 5, 14, 23, 30);
    private static final LocalDateTime CREATION_DATE_OLDER = LocalDateTime.of(2022 ,6, 5, 14, 23, 30);


    private static final Application PENDING_APP_AUD1 = new Application.ApplicationBuilder(1, APPLICANT_ID, ApplicationState.PENDING,CREATION_DATE).applicantName(NAME).applicantSurname(SURNAME).auditionTitle(TITLE).build();
    private static final Application PENDING_APP_2_AUD1 = new Application.ApplicationBuilder(1, APPLICANT_ID_2, ApplicationState.PENDING,CREATION_DATE).applicantName(NAME).applicantSurname(SURNAME).auditionTitle(TITLE).build();
    private static final Application PENDING_APP_2_AUD2 = new Application.ApplicationBuilder(2, APPLICANT_ID_2, ApplicationState.PENDING,CREATION_DATE).applicantName(NAME).applicantSurname(SURNAME).auditionTitle(TITLE).build();
    private static final Application ACCEPTED_APP_AUD2 = new Application.ApplicationBuilder(2, APPLICANT_ID, ApplicationState.ACCEPTED,CREATION_DATE).applicantName(NAME).applicantSurname(SURNAME).auditionTitle(TITLE).build();
    private static final Application REJECTED_APP_AUD3 = new Application.ApplicationBuilder(3, APPLICANT_ID, ApplicationState.REJECTED,CREATION_DATE).applicantName(NAME).applicantSurname(SURNAME).auditionTitle(TITLE).build();
    private static final Application PENDING_APP_4_AUD1 = new Application.ApplicationBuilder(1, APPLICANT_ID_3, ApplicationState.PENDING,CREATION_DATE).applicantName(NAME).applicantSurname(SURNAME).auditionTitle(TITLE).build();
    private static final Application PENDING_APP_4_AUD2 = new Application.ApplicationBuilder(2, APPLICANT_ID_3, ApplicationState.PENDING,CREATION_DATE).applicantName(NAME).applicantSurname(SURNAME).auditionTitle(TITLE).build();
    private static final Application PENDING_APP_4_AUD3 = new Application.ApplicationBuilder(3, APPLICANT_ID_3, ApplicationState.PENDING,CREATION_DATE).applicantName(NAME).applicantSurname(SURNAME).auditionTitle(TITLE).build();
    private static final Application PENDING_APP_4_AUD4 = new Application.ApplicationBuilder(4, APPLICANT_ID_3, ApplicationState.PENDING,CREATION_DATE).applicantName(NAME).applicantSurname(SURNAME).auditionTitle(TITLE).build();
    private static final Application PENDING_APP_4_AUD5 = new Application.ApplicationBuilder(5, APPLICANT_ID_3, ApplicationState.PENDING,CREATION_DATE).applicantName(NAME).applicantSurname(SURNAME).auditionTitle(TITLE).build();
    private static final Application PENDING_APP_4_AUD6 = new Application.ApplicationBuilder(6, APPLICANT_ID_3, ApplicationState.PENDING,CREATION_DATE).applicantName(NAME).applicantSurname(SURNAME).auditionTitle(TITLE).build();
    private static final Application PENDING_APP_4_AUD7 = new Application.ApplicationBuilder(7, APPLICANT_ID_3, ApplicationState.PENDING,CREATION_DATE).applicantName(NAME).applicantSurname(SURNAME).auditionTitle(TITLE).build();
    private static final Application PENDING_APP_4_AUD8 = new Application.ApplicationBuilder(8, APPLICANT_ID_3, ApplicationState.PENDING,CREATION_DATE).applicantName(NAME).applicantSurname(SURNAME).auditionTitle(TITLE).build();
    private static final Application PENDING_APP_4_AUD9 = new Application.ApplicationBuilder(9, APPLICANT_ID_3, ApplicationState.PENDING,CREATION_DATE).applicantName(NAME).applicantSurname(SURNAME).auditionTitle(TITLE).build();
    private static final Application PENDING_APP_4_AUD10 = new Application.ApplicationBuilder(10, APPLICANT_ID_3, ApplicationState.PENDING,CREATION_DATE).applicantName(NAME).applicantSurname(SURNAME).auditionTitle(TITLE).build();
    private static final Application PENDING_APP_4_AUD11 = new Application.ApplicationBuilder(11, APPLICANT_ID_3, ApplicationState.PENDING,CREATION_DATE_OLDER).applicantName(NAME).applicantSurname(SURNAME).auditionTitle(TITLE).build();
    private static final Application PENDING_APP_4_AUD12 = new Application.ApplicationBuilder(12, APPLICANT_ID_3, ApplicationState.PENDING,CREATION_DATE_OLDER).applicantName(NAME).applicantSurname(SURNAME).auditionTitle(TITLE).build();
    private static final Application PENDING_APP_4_AUD13 = new Application.ApplicationBuilder(13, APPLICANT_ID_3, ApplicationState.REJECTED,CREATION_DATE_OLDER).applicantName(NAME).applicantSurname(SURNAME).auditionTitle(TITLE).build();

    private static final Application PENDING_APP_5_AUD2 = new Application.ApplicationBuilder(2, 5, ApplicationState.PENDING,CREATION_DATE).applicantName(NAME).applicantSurname(SURNAME).auditionTitle(TITLE).build();
    private static final Application PENDING_APP_6_AUD2 = new Application.ApplicationBuilder(2, 6, ApplicationState.PENDING,CREATION_DATE).applicantName(NAME).applicantSurname(SURNAME).auditionTitle(TITLE).build();
    private static final Application PENDING_APP_7_AUD2 = new Application.ApplicationBuilder(2, 7, ApplicationState.PENDING,CREATION_DATE).applicantName(NAME).applicantSurname(SURNAME).auditionTitle(TITLE).build();
    private static final Application PENDING_APP_8_AUD2 = new Application.ApplicationBuilder(2, 8, ApplicationState.PENDING,CREATION_DATE).applicantName(NAME).applicantSurname(SURNAME).auditionTitle(TITLE).build();
    private static final Application PENDING_APP_9_AUD2 = new Application.ApplicationBuilder(2, 9, ApplicationState.PENDING,CREATION_DATE).applicantName(NAME).applicantSurname(SURNAME).auditionTitle(TITLE).build();
    private static final Application PENDING_APP_10_AUD2 = new Application.ApplicationBuilder(2, 10, ApplicationState.PENDING,CREATION_DATE).applicantName(NAME).applicantSurname(SURNAME).auditionTitle(TITLE).build();
    private static final Application PENDING_APP_11_AUD2 = new Application.ApplicationBuilder(2, 11, ApplicationState.PENDING,CREATION_DATE).applicantName(NAME).applicantSurname(SURNAME).auditionTitle(TITLE).build();
    private static final Application PENDING_APP_12_AUD2 = new Application.ApplicationBuilder(2, 12, ApplicationState.PENDING,CREATION_DATE).applicantName(NAME).applicantSurname(SURNAME).auditionTitle(TITLE).build();


    private static final List<Application> PENDING_APPS_AUD2 = Arrays.asList(PENDING_APP_2_AUD2, PENDING_APP_4_AUD2, PENDING_APP_5_AUD2, PENDING_APP_6_AUD2, PENDING_APP_7_AUD2, PENDING_APP_8_AUD2, PENDING_APP_9_AUD2, PENDING_APP_10_AUD2, PENDING_APP_11_AUD2, PENDING_APP_12_AUD2 );
    private static final List<Application> PENDING_APPS_APPLICANT_2 = Arrays.asList(PENDING_APP_2_AUD2, PENDING_APP_2_AUD1);
    private static final List<Application> PENDING_APPS_APPLICANT_4_PAGE_1 = Arrays.asList(PENDING_APP_4_AUD1, PENDING_APP_4_AUD2, PENDING_APP_4_AUD3, PENDING_APP_4_AUD4, PENDING_APP_4_AUD5, PENDING_APP_4_AUD6, PENDING_APP_4_AUD7, PENDING_APP_4_AUD8, PENDING_APP_4_AUD9, PENDING_APP_4_AUD10);
    private static final List<Application> PENDING_APPS_APPLICANT_4_PAGE_2 = Arrays.asList(PENDING_APP_4_AUD11, PENDING_APP_4_AUD12, PENDING_APP_4_AUD13);


    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    @Test
    public void testGetAuditionApplicationsByStatePendingFullPage() {
        List<Application> applications = applicationDao.getAuditionApplicationsByState(2, ApplicationState.PENDING, 1);
        assertNotNull(applications);
        assertTrue(PENDING_APPS_AUD2.containsAll(applications));
        assertFalse(Collections.singletonList(PENDING_APP_2_AUD2).containsAll(applications));
        assertEquals(PENDING_APPS_AUD2.size(), applications.size());
    }

    @Test
    public void testGetAuditionApplicationsByStateAcceptedNotFullPage() {
        List<Application> applications = applicationDao.getAuditionApplicationsByState(2, ApplicationState.ACCEPTED,1);
        assertNotNull(applications);
        assertTrue(Collections.singletonList(ACCEPTED_APP_AUD2).containsAll(applications));
        assertEquals(1, applications.size());
    }

    @Test
    public void testGetAuditionApplicationsByStateRejectedNotFullPage() {
        List<Application> applications = applicationDao.getAuditionApplicationsByState(3, ApplicationState.REJECTED,1);
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
    public void testGetMyApplicationsNotFullPage() {
        List<Application> applications = applicationDao.getMyApplications(APPLICANT_ID_3, 2);
        assertNotNull(applications);
        assertTrue(PENDING_APPS_APPLICANT_4_PAGE_2.containsAll(applications));
        assertEquals(PENDING_APPS_APPLICANT_4_PAGE_2.size(), applications.size());
    }

    @Test
    public void testGetMyApplicationsFullPage() {
        List<Application> applications = applicationDao.getMyApplications(APPLICANT_ID_3, 1);
        assertNotNull(applications);
        assertTrue(PENDING_APPS_APPLICANT_4_PAGE_1.containsAll(applications));
        assertEquals(PAGE_SIZE, applications.size());
        assertEquals(PENDING_APPS_APPLICANT_4_PAGE_1.size(), applications.size());
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
        int pages = applicationDao.getTotalUserApplicationPages(APPLICANT_ID_3);
        assertEquals(2, pages);
    }

    @Test
    public void testGetTotalInvalidUserApplicationPages() {
        int pages = applicationDao.getTotalUserApplicationPages(INVALID_ID);
        assertEquals(0, pages);
    }

    @Test
    public void testGetTotalUserApplicationFilterPages() {
        int pages = applicationDao.getTotalUserApplicationPagesFiltered(APPLICANT_ID_3, ApplicationState.REJECTED);
        assertEquals(1, pages);
    }

    @Test
    public void testGetTotalUserApplicationFilterPagesNone() {
        int pages = applicationDao.getTotalUserApplicationPagesFiltered(APPLICANT_ID_3, ApplicationState.ACCEPTED);
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

    @Test
    public void testGetTotalAuditionApplicationsByStatePages() {
        int pages = applicationDao.getTotalAuditionApplicationsByStatePages(1, ApplicationState.PENDING);
        assertEquals(2, pages);
    }

    @Test
    public void testGetTotalAuditionApplicationsByStatePagesZero() {
        int pages = applicationDao.getTotalAuditionApplicationsByStatePages(5, ApplicationState.REJECTED);
        assertEquals(0, pages);
    }

    @Test
    public void testGetTotalAuditionApplicationsByStatePagesInvalid() {
        int pages = applicationDao.getTotalAuditionApplicationsByStatePages(INVALID_ID, ApplicationState.REJECTED);
        assertEquals(0, pages);
    }
}
