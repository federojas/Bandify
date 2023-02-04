package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.exceptions.AuditionNotOwnedException;
import ar.edu.itba.paw.model.exceptions.PageNotFoundException;
import ar.edu.itba.paw.model.exceptions.UserAlreadyAppliedToAuditionException;
import ar.edu.itba.paw.model.exceptions.UserAlreadyInBandException;
import ar.edu.itba.paw.persistence.ApplicationDao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationServiceTest {

    @Mock
    private ApplicationDao applicationDao;

    @Mock
    private UserService userService;

    @Mock
    private AuditionService auditionService;

    @Mock
    private MembershipService membershipService;

    @Mock
    private AuthFacadeService authFacadeService;

    @Mock
    private MailingService mailingService;

    @InjectMocks
    private ApplicationService applicationService = new ApplicationServiceImpl();

    private static final User user = new User.UserBuilder("artist@mail.com","12345678", "name", false, false).surname("surname").description("description").id(5L).build();
    private static final Audition AUD = new Audition.AuditionBuilder("title", "desc", user, LocalDateTime.now()).location(new Location(1L, "location")).lookingFor(new Role(1, "role")).musicGenres(new Genre("genre",1)).id(1).build();

    private final Application PENDING_APP = new Application.ApplicationBuilder(AUD,user, ApplicationState.PENDING, LocalDateTime.MIN, "a").build();
    private final Application REJECTED_APP = new Application.ApplicationBuilder(AUD,user, ApplicationState.REJECTED, LocalDateTime.MIN, "a").build();
    private final Application ACCEPTED_APP = new Application.ApplicationBuilder(AUD,user, ApplicationState.ACCEPTED, LocalDateTime.MIN, "a").build();
    private final User USER_ALREADY_APPLIED = new User.UserBuilder("email@email.com","password","name",false,true).id(1L).build();
    private final User USER_NOT_APPLIED = new User.UserBuilder("email2@email.com","password","name",false,true).id(3L).build();
    private final static User BAND = new User.UserBuilder("band@email.com","password","band",true,true).id(2L).build();
    private static final Audition AUDITION = new Audition.AuditionBuilder("TITLE", "DESCRIPTION", user, LocalDateTime.MIN).id(1).build();
    private static final Audition BAND_AUDITION = new Audition.AuditionBuilder("TITLE", "DESCRIPTION", BAND, LocalDateTime.MIN).id(2).build();

    @Test
    public void testGetAuditionApplicationsByStateWithPendingState() {
        long auditionId = 1;
        when(applicationDao.getAuditionApplicationsByState(auditionId, ApplicationState.PENDING,1)).
                thenReturn(new ArrayList<>(Collections.singletonList(PENDING_APP)));
        when(authFacadeService.getCurrentUser()).thenReturn(BAND);
        when(auditionService.getAuditionById(auditionId)).thenReturn(BAND_AUDITION);

        List<Application> list = applicationService
                .getAuditionApplicationsByState(1,ApplicationState.PENDING,1);

        verify(applicationDao).getAuditionApplicationsByState(auditionId, ApplicationState.PENDING,1);
        assertEquals(1,list.size());
        assertTrue(list.containsAll(new ArrayList<>(Collections.singletonList(PENDING_APP))));
    }

    @Test
    public void testGetAuditionApplicationsByStateWithAllState() {
        long auditionId = 1;
        when(applicationDao.getAuditionApplicationsByState(auditionId, ApplicationState.PENDING,1)).
                thenReturn(new ArrayList<>(Collections.singletonList(PENDING_APP)));
        when(authFacadeService.getCurrentUser()).thenReturn(BAND);
        when(auditionService.getAuditionById(auditionId)).thenReturn(BAND_AUDITION);

        List<Application> list = applicationService.getAuditionApplicationsByState(1,ApplicationState.ALL,1);

        verify(applicationDao).getAuditionApplicationsByState(auditionId, ApplicationState.PENDING,1);
        assertEquals(1,list.size());
        assertTrue(list.containsAll(new ArrayList<>(Collections.singletonList(PENDING_APP))));
    }

    @Test
    public void testGetAuditionApplicationsByStateWithAcceptedState() {
        long auditionId = 1;
        when(applicationDao.getAuditionApplicationsByState(auditionId,ApplicationState.ACCEPTED,1)).
                thenReturn(new ArrayList<>(Collections.singletonList(ACCEPTED_APP)));
        when(authFacadeService.getCurrentUser()).thenReturn(BAND);
        when(auditionService.getAuditionById(auditionId)).thenReturn(BAND_AUDITION);

        List<Application> list = applicationService.getAuditionApplicationsByState(1,ApplicationState.ACCEPTED,1);

        verify(applicationDao).getAuditionApplicationsByState(auditionId, ApplicationState.ACCEPTED,1);
        assertEquals(1,list.size());
        assertTrue(list.containsAll(new ArrayList<>(Collections.singletonList(ACCEPTED_APP))));
    }

    @Test
    public void testGetAuditionApplicationsByStateWithRejectedState() {
        long auditionId = 1;
        when(applicationDao.getAuditionApplicationsByState(auditionId,ApplicationState.REJECTED,1)).
                thenReturn(new ArrayList<>(Collections.singletonList(REJECTED_APP)));
        when(authFacadeService.getCurrentUser()).thenReturn(BAND);
        when(auditionService.getAuditionById(auditionId)).thenReturn(BAND_AUDITION);

        List<Application> list = applicationService.getAuditionApplicationsByState(1,ApplicationState.REJECTED,1);

        verify(applicationDao).getAuditionApplicationsByState(auditionId, ApplicationState.REJECTED,1);
        assertEquals(1,list.size());
        assertTrue(list.containsAll(new ArrayList<>(Collections.singletonList(REJECTED_APP))));
    }

    @Test(expected = UserAlreadyAppliedToAuditionException.class)
    public void testApplyWithUserWhoAlreadyAppliedThisAudition() {
        long auditionId = 1;
        when(applicationDao.findApplication(auditionId, USER_ALREADY_APPLIED.getId())).thenThrow(UserAlreadyAppliedToAuditionException.class);
        applicationService.apply(auditionId, USER_ALREADY_APPLIED,"message");
        Assert.fail("Should have thrown UserAlreadyAppliedToAuditionException");
    }

    @Test(expected = UserAlreadyInBandException.class)
    public void testApplyWithUserWhoIsAlreadyInBand() {
        long auditionId = 1;
        when(applicationDao.findApplication(auditionId, USER_NOT_APPLIED.getId())).thenThrow(UserAlreadyInBandException.class);
        when(auditionService.getAuditionById(auditionId)).thenReturn(AUDITION);
        when(userService.getUserById(AUDITION.getBand().getId())).thenReturn(Optional.of(BAND));
        when(membershipService.isInBand(BAND, USER_NOT_APPLIED)).thenReturn(true);
        applicationService.apply(auditionId, USER_NOT_APPLIED,"message");
        Assert.fail("Should have thrown UserAlreadyInBandException");
    }

    @Test
    public void testApplyWithUserWhoDidntAppliedThisAudition() {
        long auditionId = 1;
        when(applicationDao.findApplication(auditionId, USER_NOT_APPLIED.getId())).thenReturn(Optional.empty());
        when(auditionService.getAuditionById(auditionId)).thenReturn(AUDITION);
        when(userService.getUserById(AUDITION.getBand().getId())).thenReturn(Optional.of(BAND));
        when(membershipService.isInBand(BAND, USER_NOT_APPLIED)).thenReturn(false);
        applicationService.apply(auditionId, USER_NOT_APPLIED,"message");
    }

    @Test
    public void testGetMyPendingApplications() {
        long applicantId = 1;
        when(applicationDao.getMyApplicationsFiltered(applicantId, 1, ApplicationState.PENDING)).
                thenReturn(new ArrayList<>(Collections.singletonList(PENDING_APP)));
        List<Application> myApps = applicationService.getMyApplicationsFiltered(applicantId, 1, ApplicationState.PENDING);
        assertEquals(1, myApps.size());
        assertTrue(myApps.containsAll(new ArrayList<>(Collections.singletonList(PENDING_APP))));
    }

    @Test
    public void testGetMyAcceptedApplications() {
        long applicantId = 1;
        when(applicationDao.getMyApplicationsFiltered(applicantId, 1, ApplicationState.ACCEPTED)).
                thenReturn(new ArrayList<>(Collections.singletonList(ACCEPTED_APP)));
        List<Application> myApps = applicationService.getMyApplicationsFiltered(applicantId, 1, ApplicationState.ACCEPTED);
        assertEquals(1, myApps.size());
        assertTrue(myApps.containsAll(new ArrayList<>(Collections.singletonList(ACCEPTED_APP))));
    }

    @Test
    public void testGetMyRejectedApplications() {
        long applicantId = 1;
        when(applicationDao.getMyApplicationsFiltered(applicantId, 1, ApplicationState.REJECTED)).
                thenReturn(new ArrayList<>(Collections.singletonList(REJECTED_APP)));
        List<Application> myApps = applicationService.getMyApplicationsFiltered(applicantId, 1, ApplicationState.REJECTED);
        assertEquals(1, myApps.size());
        assertTrue(myApps.containsAll(new ArrayList<>(Collections.singletonList(REJECTED_APP))));
    }

    @Test
    public void testGetApplicationById() {
        long applicationId = 1;
        long auditionId = 1;
        when(authFacadeService.getCurrentUser()).thenReturn(BAND);
        when(auditionService.getAuditionById(auditionId)).thenReturn(BAND_AUDITION);
        when(applicationDao.findApplication(applicationId)).thenReturn(Optional.of(PENDING_APP));
        Optional<Application> app = applicationService.getApplicationById(auditionId, applicationId);
        assertTrue(app.isPresent());
        assertEquals(PENDING_APP, app.get());
    }

    @Test
    public void testGetApplicationByIdEmpty() {
        long applicationId = 1;
        long auditionId = 1;
        when(authFacadeService.getCurrentUser()).thenReturn(BAND);
        when(auditionService.getAuditionById(auditionId)).thenReturn(BAND_AUDITION);
        when(applicationDao.findApplication(applicationId)).thenReturn(Optional.empty());
        Optional<Application> app = applicationService.getApplicationById(auditionId, applicationId);
        Assert.assertFalse(app.isPresent());
    }

    @Test
    public void testGetAcceptedApplicationById() {
        long applicationId = 1;
        long auditionId = 1;
        when(authFacadeService.getCurrentUser()).thenReturn(BAND);
        when(auditionService.getAuditionById(auditionId)).thenReturn(BAND_AUDITION);
        when(applicationDao.findApplication(applicationId)).thenReturn(Optional.of(ACCEPTED_APP));
        Optional<Application> app = applicationService.getAcceptedApplicationById(auditionId, applicationId);
        assertTrue(app.isPresent());
        assertEquals(ACCEPTED_APP, app.get());
    }

    @Test
    public void testGetAcceptedApplicationByIdButIsNotAccepted() {
        long applicationId = 1;
        long auditionId = 1;
        when(authFacadeService.getCurrentUser()).thenReturn(BAND);
        when(auditionService.getAuditionById(auditionId)).thenReturn(BAND_AUDITION);
        when(applicationDao.findApplication(applicationId)).thenReturn(Optional.of(PENDING_APP));
        Optional<Application> app = applicationService.getAcceptedApplicationById(auditionId, applicationId);
        assertFalse(app.isPresent());
    }


    @Test
    public void testCloseApplicationsByAuditionId() {
        long auditionId = 1;
        when(authFacadeService.getCurrentUser()).thenReturn(BAND);
        when(auditionService.getAuditionById(auditionId)).thenReturn(BAND_AUDITION);
        when(applicationDao.getAuditionApplicationsByState(auditionId, ApplicationState.PENDING)).thenReturn(new ArrayList<>(Collections.singletonList(PENDING_APP)));
        boolean closedApps = applicationService.closeApplicationsByAuditionId(auditionId);
        assertTrue(closedApps);
    }

    //TODO: descomentar
//    @Test
//    public void testSelect() {
//        long auditionId = 1;
//        long applicantId = 1;
//        when(auditionService.getAuditionById(auditionId)).thenReturn(BAND_AUDITION);
//        when(userService.findByEmail(any())).thenReturn(Optional.of(BAND));
//        when(userService.getUserById(applicantId)).thenReturn(Optional.of(user));
//        when(applicationDao.findApplication(auditionId, applicantId)).thenReturn(Optional.of(PENDING_APP));
//
//        Application app = applicationService.select(auditionId, applicantId);
//        assertNotNull(app);
//        assertEquals(ApplicationState.SELECTED, app.getState());
//    }



    @Test(expected = AuditionNotOwnedException.class)
    public void testCloseApplicationsByAuditionIdNotOwned() {
        long auditionId = 1;
        when(authFacadeService.getCurrentUser()).thenReturn(BAND);
        when(auditionService.getAuditionById(auditionId)).thenReturn(AUDITION);
        applicationService.closeApplicationsByAuditionId(auditionId);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCloseApplicationsByAuditionIdIllegalId() {
        long auditionId = -10;
        applicationService.closeApplicationsByAuditionId(auditionId);
    }

    @Test(expected = AuditionNotOwnedException.class)
    public void testGetApplicationByIdNotOwned() {
        long applicationId = 1;
        long auditionId = 1;
        when(authFacadeService.getCurrentUser()).thenReturn(BAND);
        when(auditionService.getAuditionById(auditionId)).thenReturn(AUDITION);
        applicationService.getApplicationById(applicationId,auditionId);
        Assert.fail("Should have thrown AuditionNotOwnedException");
    }

    @Test(expected = AuditionNotOwnedException.class)
    public void testGetAuditionApplicationsByStateWithIllegalOwner() {
        long auditionId = 1;
        when(authFacadeService.getCurrentUser()).thenReturn(BAND);
        when(auditionService.getAuditionById(auditionId)).thenReturn(AUDITION);
        applicationService.getAuditionApplicationsByState(auditionId, ApplicationState.ALL,-1);
        Assert.fail("Should have thrown AuditionNotOwnedException");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetAuditionApplicationsByStateWithIllegalPage() {
        long auditionId = 1;
        when(authFacadeService.getCurrentUser()).thenReturn(BAND);
        when(auditionService.getAuditionById(auditionId)).thenReturn(BAND_AUDITION);

        applicationService.getAuditionApplicationsByState(auditionId, ApplicationState.ALL,-1);
        Assert.fail("Should have thrown IllegalArgumentException");
    }

    @Test(expected = PageNotFoundException.class)
    public void testGetAuditionApplicationsByStateWithInvalidPage() {
        long auditionId = 1;
        when(authFacadeService.getCurrentUser()).thenReturn(BAND);
        when(auditionService.getAuditionById(auditionId)).thenReturn(BAND_AUDITION);

        applicationService.getAuditionApplicationsByState(auditionId, ApplicationState.ALL,100);
        Assert.fail("Should have thrown PageNotFoundException");
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
