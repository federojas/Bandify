package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.exceptions.AuditionNotFoundException;
import ar.edu.itba.paw.model.exceptions.AuditionNotOwnedException;
import ar.edu.itba.paw.model.exceptions.PageNotFoundException;
import ar.edu.itba.paw.model.exceptions.UserNotFoundException;
import ar.edu.itba.paw.persistence.AuditionDao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AuditionServiceTest {

    @Mock
    private AuditionDao auditionDao;

    @Mock
    private AuthFacadeService authFacadeService;

    @InjectMocks
    private AuditionService auditionService = new AuditionServiceImpl();

    private static final User user = new User.UserBuilder("artist@mail.com","12345678", "name", false, false).surname("surname").description("description").id(1L).build();
    private static final User band = new User.UserBuilder("band@mail.com","12345678", "name", true, false).description("description").id(2L).build();
    private static final Audition.AuditionBuilder AUD_BUILDER = new Audition.AuditionBuilder("title", "desc", user, LocalDateTime.now()).location(new Location(1L, "location")).lookingFor(new Role(1, "role")).musicGenres(new Genre( "genre",1));
    private static final Audition AUD = new Audition.AuditionBuilder("title", "desc", user, LocalDateTime.now()).location(new Location(1L, "location")).lookingFor(new Role(1, "role")).musicGenres(new Genre("genre",1)).id(1).build();
    private static final FilterOptions FILTER = new FilterOptions.FilterOptionsBuilder().build();


    private static final long INVALID_ID = -2;
    private static final long AUD_ID = 1;
    private static final User USER = new User.UserBuilder("artist@mail.com","12345678", "name", false, false).surname("surname").description("description").id(2L).build();

    @Test(expected = IllegalArgumentException.class)
    public void testGetAuditionByInvalidId() {
        auditionService.getAuditionById(INVALID_ID);
        Assert.fail("Should have thrown IllegalArgumentException");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEditAuditionByInvalidId() {
        auditionService.editAuditionById(AUD_BUILDER, INVALID_ID);
        Assert.fail("Should have thrown IllegalArgumentException");
    }

    @Test(expected = AuditionNotFoundException.class)
    public void testEditAuditionByInvalidAuditionId() {
        when(auditionDao.getAuditionById(Mockito.eq(AUD_ID))).thenThrow(new AuditionNotFoundException());
        auditionService.editAuditionById(AUD_BUILDER, AUD_ID);
        Assert.fail("Should have thrown AuditionNotFoundException");
    }

    @Test(expected = AuditionNotOwnedException.class)
    public void testEditAuditionByIdNotOwned() {
        when(auditionService.getAuditionById(Mockito.eq(AUD_ID))).thenReturn(AUD);
        when(authFacadeService.getCurrentUser()).thenReturn(USER);
        auditionService.editAuditionById(AUD_BUILDER, AUD_ID);
        Assert.fail("Should have thrown AuditionNotOwnedException");
    }

    @Test(expected = UserNotFoundException.class)
    public void testEditAuditionByIdUserNotFound() {
        when(auditionService.getAuditionById(Mockito.eq(AUD_ID))).thenReturn(AUD);
        when(authFacadeService.getCurrentUser()).thenThrow(new UserNotFoundException());
        auditionService.editAuditionById(AUD_BUILDER, AUD_ID);
        Assert.fail("Should have thrown UserNotFoundException");
    }


    @Test(expected = AuditionNotFoundException.class)
    public void testEditAuditionNotExistsById() {
        auditionService.editAuditionById(AUD_BUILDER, 5);
        Assert.fail("Should have thrown AuditionNotFoundException");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetAllIllegalPage() {
        auditionService.getAll(-1);
        Assert.fail("Should have thrown IllegalArgumentException");
    }

    @Test(expected = PageNotFoundException.class)
    public void testGetAllInvalidPage() {
        when(auditionService.getTotalPages()).thenReturn(5);
        auditionService.getAll(500);
        Assert.fail("Should have thrown PageNotFoundException");
    }


    @Test(expected = IllegalArgumentException.class)
    public void testGetBandAuditionsIllegalPage() {
        auditionService.getBandAuditions(band,-1);
        Assert.fail("Should have thrown IllegalArgumentException");
    }

    @Test(expected = PageNotFoundException.class)
    public void testGetBandAuditionsInvalidPage() {
        when(auditionService.getTotalBandAuditionPages(band)).thenReturn(5);
        auditionService.getBandAuditions(band,500);
        Assert.fail("Should have thrown PageNotFoundException");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFilterIllegalPage() {
        auditionService.filter(FILTER,-1);
        Assert.fail("Should have thrown IllegalArgumentException");
    }

    @Test(expected = PageNotFoundException.class)
    public void testFilterInvalidPage() {
        when(auditionService.getFilterTotalPages(FILTER)).thenReturn(5);
        auditionService.filter(FILTER,500);
        Assert.fail("Should have thrown PageNotFoundException");
    }


}
