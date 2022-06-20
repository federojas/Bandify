package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Membership;
import ar.edu.itba.paw.model.MembershipState;
import ar.edu.itba.paw.model.Role;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.persistence.MembershipDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MembershipServiceTest {

    @InjectMocks
    private final MembershipService membershipService = new MembershipServiceImpl();

    @Mock
    private Membership membership;

    @Mock
    private MembershipDao membershipDao;

    @Mock
    private ApplicationService applicationService;

    @Mock
    private MailingService mailingService;

    @Mock
    private AuthFacadeService authFacadeService;



    private static final Role role1 = new Role(1L, "role");
    private static final User ARTIST = new User.UserBuilder("artist@mail.com","12345678","name",false,false).surname("surname").description("description").id(1L).build();
    private static final User ARTIST2 = new User.UserBuilder("artist2@mail.com","12345678","name",false,false).surname("surname").description("description").id(3L).build();
    private static final User BAND = new User.UserBuilder("band@mail.com","12345678", "name", true, false).description("description").id(2L).build();
    private static final User BAND2 = new User.UserBuilder("band2@mail.com","12345678", "name", true, false).description("description").id(4L).build();
    private static final User BAND3 = new User.UserBuilder("band3@mail.com","12345678", "name", true, false).description("description").id(5L).build();

    private static final Membership mem1 = new Membership.Builder(ARTIST, BAND).roles(role1).description("description").state(MembershipState.PENDING).id(1L).build();
    private static final Membership mem2 = new Membership.Builder(ARTIST2, BAND).roles(role1).description("description").state(MembershipState.ACCEPTED).id(2L).build();
    private static final Membership mem4 = new Membership.Builder(ARTIST, BAND2).roles(role1).description("description").state(MembershipState.PENDING).id(4L).build();

    private static final User ARTIST_AVAILABLE = new User.UserBuilder("artist@mail.com","12345678","name",false,false).surname("surname").description("description").id(1L).available(true).build();
    private static final User ARTIST_NOT_AVAILABLE = new User.UserBuilder("artist@mail.com","12345678","name",false,false).surname("surname").description("description").id(1L).available(false).build();


    private static final Membership.Builder mem5Builder = new Membership.Builder(ARTIST_AVAILABLE, BAND2).roles(role1).description("description").state(MembershipState.PENDING).id(5L);
    private static final Membership mem5 = mem5Builder.build();

    List<Membership> MEMS_LIST = Arrays.asList(mem1, mem5);

    @Test
    public void testGetUserMemberships() {
        when(membershipDao.getUserMembershipsByState(ARTIST, MembershipState.PENDING, 1)).thenReturn(MEMS_LIST);
        List<Membership> mems = membershipService.getUserMemberships(ARTIST, MembershipState.PENDING, 1);
        assertEquals(MEMS_LIST, mems);
    }

    @Test
    public void testChangeStateToPending() {
        when(authFacadeService.getCurrentUser()).thenReturn(ARTIST);

        Membership memRet = membershipService.changeState(mem1, MembershipState.PENDING);
        assertEquals(memRet.getState(), MembershipState.PENDING);
    }

    @Test
    public void testChangeStateToAccepted() {
        when(authFacadeService.getCurrentUser()).thenReturn(ARTIST);

        doNothing().when(mailingService).sendInvitationAcceptedEmail(any(), any(), any());
        Membership memRet = membershipService.changeState(mem4, MembershipState.ACCEPTED);
        assertEquals(memRet.getState(), MembershipState.ACCEPTED);
    }

    @Test
    public void testChangeStateToRejected() {
        when(authFacadeService.getCurrentUser()).thenReturn(ARTIST);

        Membership memRet = membershipService.changeState(mem5, MembershipState.REJECTED);
        assertEquals(memRet.getState(), MembershipState.REJECTED);
    }

//    @Test
//    public void testCreateMembershipInvite() {
////        verify(ARTIST_AVAILABLE).setAvailable(true);
//
//        Membership memRet = membershipService.createMembershipInvite(mem5Builder);
//        when(membershipDao.createMembership(mem5Builder)).thenReturn(mem5);
//        assertEquals(memRet.getState(), MembershipState.PENDING);
//    }

    @Test
    public void testCreateMembershipByApplication() {
        when(membershipDao.membershipExists(any(), any())).thenReturn(false);
        doNothing().when(applicationService).select(1, mem5Builder.getArtist().getId());
        boolean created = membershipService.createMembershipByApplication(mem5Builder, 1);
        assertTrue(created);
    }

    @Test
    public void testEditMembershipById() {
        Set<Role> roles = new HashSet<Role>();
        String newDescription = "Nueva descripcion";

        roles.add(new Role(1, "Role1"));
        when(membershipDao.getMembershipById(1L)).thenReturn(Optional.of(mem5));
        when(authFacadeService.getCurrentUser()).thenReturn(BAND2);

        Membership editedMem = membershipService.editMembershipById(newDescription, roles, 1L);
        assertNotNull(editedMem);
        assertEquals(editedMem.getDescription(), newDescription);
        assertEquals(editedMem.getRoles(), roles);
    }

    @Test
    public void testCanBeAddedToBand() {
        when(membershipDao.membershipExists(BAND, ARTIST_AVAILABLE)).thenReturn(false);

        boolean canBeAddedToBand = membershipService.canBeAddedToBand(BAND, ARTIST_AVAILABLE);
        assertTrue(canBeAddedToBand);
    }

    @Test
    public void testCanNotBeAddedToBand() {
        when(membershipDao.membershipExists(BAND, ARTIST_NOT_AVAILABLE)).thenReturn(false);

        boolean canBeAddedToBand = membershipService.canBeAddedToBand(BAND, ARTIST_NOT_AVAILABLE);
        assertFalse(canBeAddedToBand);
    }

}
