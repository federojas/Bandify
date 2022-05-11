package ar.edu.itba.paw.service;

import ar.edu.itba.paw.Application;
import ar.edu.itba.paw.ApplicationState;
import ar.edu.itba.paw.Audition;
import ar.edu.itba.paw.User;
import ar.edu.itba.paw.model.exceptions.PageNotFoundException;
import ar.edu.itba.paw.persistence.ApplicationDao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.*;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationServiceTest {

    @Mock
    private ApplicationDao applicationDao;

    @Mock
    private MailingService mailingService;

    @Mock
    private UserService userService;

    @Mock
    private AuditionService auditionService;

    @InjectMocks
    private ApplicationService applicationService = new ApplicationServiceImpl();

    private final Application PENDING_APP = new Application.ApplicationBuilder(1,1,ApplicationState.PENDING, LocalDateTime.MIN).build();
    private final Application REJECTED_APP = new Application.ApplicationBuilder(1,1,ApplicationState.REJECTED, LocalDateTime.MIN).build();
    private final Application ACCEPTED_APP = new Application.ApplicationBuilder(1,1,ApplicationState.ACCEPTED, LocalDateTime.MIN).build();
    private final User USER_ALREADY_APPLIED = new User.UserBuilder("email@email.com","password","name",false,true).id(1).build();
    private final User USER_NOT_APPLIED = new User.UserBuilder("email2@email.com","password","name",false,true).id(3).build();
    private final User BAND = new User.UserBuilder("band@email.com","password","band",true,true).id(2).build();
    private static final Audition AUDITION = new Audition.AuditionBuilder("TITLE", "DESCRIPTION", 2, LocalDateTime.MIN).id(1).build();

    @Test(expected = IllegalArgumentException.class)
    public void testGetAuditionApplicationsByStateWithIllegalPage() {
        applicationService.getAuditionApplicationsByState(1, ApplicationState.ALL,-1);
        Assert.fail("Should have thrown IllegalArgumentException");
    }

    @Test(expected = PageNotFoundException.class)
    public void testGetAuditionApplicationsByStateWithInvalidPage() {
        applicationService.getAuditionApplicationsByState(1, ApplicationState.ALL,100);
        Assert.fail("Should have thrown PageNotFoundException");
    }

    @Test
    public void testGetAuditionApplicationsByStateWithPendingState() {
        when(applicationDao.getAuditionApplicationsByState(1,ApplicationState.PENDING,1)).
                thenReturn(new ArrayList<>(Collections.singletonList(PENDING_APP)));
        List<Application> list = applicationService.getAuditionApplicationsByState(1,ApplicationState.PENDING,1);
        Assert.assertEquals(1,list.size());
        Assert.assertTrue(list.containsAll(new ArrayList<>(Collections.singletonList(PENDING_APP))));
    }

    @Test
    public void testGetAuditionApplicationsByStateWithAllState() {
        when(applicationDao.getAuditionApplicationsByState(1,ApplicationState.PENDING,1)).
                thenReturn(new ArrayList<>(Collections.singletonList(PENDING_APP)));
        List<Application> list = applicationService.getAuditionApplicationsByState(1,ApplicationState.ALL,1);
        Assert.assertEquals(1,list.size());
        Assert.assertTrue(list.containsAll(new ArrayList<>(Collections.singletonList(PENDING_APP))));
    }

    @Test
    public void testGetAuditionApplicationsByStateWithAcceptedState() {
        when(applicationDao.getAuditionApplicationsByState(1,ApplicationState.ACCEPTED,1)).
                thenReturn(new ArrayList<>(Collections.singletonList(ACCEPTED_APP)));
        List<Application> list = applicationService.getAuditionApplicationsByState(1,ApplicationState.ACCEPTED,1);
        Assert.assertEquals(1,list.size());
        Assert.assertTrue(list.containsAll(new ArrayList<>(Collections.singletonList(ACCEPTED_APP))));
    }

    @Test
    public void testGetAuditionApplicationsByStateWithRejectedState() {
        when(applicationDao.getAuditionApplicationsByState(1,ApplicationState.REJECTED,1)).
                thenReturn(new ArrayList<>(Collections.singletonList(REJECTED_APP)));
        List<Application> list = applicationService.getAuditionApplicationsByState(1,ApplicationState.REJECTED,1);
        Assert.assertEquals(1,list.size());
        Assert.assertTrue(list.containsAll(new ArrayList<>(Collections.singletonList(REJECTED_APP))));
    }

    @Test
    public void testApplyWithUserWhoAlreadyAppliedThisAudition() {
        when(applicationDao.exists(1, USER_ALREADY_APPLIED.getId())).thenReturn(true);
        boolean applied = applicationService.apply(1, USER_ALREADY_APPLIED,"message");
        Assert.assertFalse(applied);
    }

    @Test
    public void testApplyWithUserWhoDidntAppliedThisAudition() {
        when(auditionService.getAuditionById(1)).thenReturn(Optional.of(AUDITION));
        when(userService.getUserById(2)).thenReturn(Optional.of(BAND));
        boolean applied = applicationService.apply(1, USER_NOT_APPLIED,"message");
        Assert.assertTrue(applied);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAcceptWithInvalidAuditionId() {
        applicationService.accept(-1,1);
        Assert.fail("Should have thrown IllegalArgumentException");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAcceptWithInvalidApplicantId() {
        applicationService.accept(1,-1);
        Assert.fail("Should have thrown IllegalArgumentException");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRejectWithInvalidAuditionId() {
        applicationService.reject(-1,1);
        Assert.fail("Should have thrown IllegalArgumentException");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRejectWithInvalidApplicantId() {
        applicationService.reject(1,-1);
        Assert.fail("Should have thrown IllegalArgumentException");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetMyApplicationsFilteredInvalidPage() {
        applicationService.getMyApplicationsFiltered(1,-1,ApplicationState.PENDING);
        Assert.fail("Should have thrown IllegalArgumentException");
    }

    @Test(expected = PageNotFoundException.class)
    public void testGetMyApplicationsFilteredPageNotFound() {
        applicationService.getMyApplicationsFiltered(1,100,ApplicationState.PENDING);
        Assert.fail("Should have thrown PageNotFoundException");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetMyApplicationsInvalidPage() {
        applicationService.getMyApplications(1,-1);
        Assert.fail("Should have thrown IllegalArgumentException");
    }

    @Test(expected = PageNotFoundException.class)
    public void testGetMyApplicationsPageNotFound() {
        when(applicationDao.getTotalUserApplicationPages(1)).thenReturn(1);
        applicationService.getMyApplications(1,100);
        Assert.fail("Should have thrown PageNotFoundException");
    }
}
