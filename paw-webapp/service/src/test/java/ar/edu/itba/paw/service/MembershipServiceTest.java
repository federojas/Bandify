package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Membership;
import ar.edu.itba.paw.model.MembershipState;
import ar.edu.itba.paw.model.Role;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.persistence.MembershipDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MembershipServiceTest {

    @InjectMocks
    private final MembershipService membershipService = new MembershipServiceImpl();

    @Mock
    private MembershipDao membershipDao;

    @Mock
    private MailingService mailingService;

    @Mock
    private RoleService roleService;

    @Mock
    private AuthFacadeService authFacadeService;

    @Mock
    private ApplicationService applicationService;


    private static final Role role1 = new Role(1L, "role");
    private static final User ARTIST = new User.UserBuilder("artist@mail.com","12345678","name",false,false).surname("surname").description("description").id(1L).build();
    private static final User BAND = new User.UserBuilder("band@mail.com","12345678", "name", true, false).description("description").id(2L).build();
    private static final User BAND2 = new User.UserBuilder("band2@mail.com","12345678", "name", true, false).description("description").id(4L).build();

    private static final Membership mem1 = new Membership.Builder(ARTIST, BAND).roles(role1).description("description").state(MembershipState.PENDING).id(1L).build();
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
        when(membershipDao.getMembershipById(mem1.getId())).thenReturn(Optional.of(mem1));

        Membership memRet = membershipService.changeState(mem1.getId(), MembershipState.PENDING);
        assertEquals(MembershipState.PENDING, memRet.getState());
    }

    @Test
    public void testGetUserMembershipsPreview() {
        when(membershipDao.getUserMembershipsPreview(ARTIST)).thenReturn(MEMS_LIST);
        List<Membership> memsList = membershipService.getUserMembershipsPreview(ARTIST);
        assertEquals(MEMS_LIST, memsList);
    }

    @Test
    public void testCreateMembershipInvite() {
        Membership.Builder memBuilder = mem5Builder.state(MembershipState.PENDING);
        Membership memExpected = memBuilder.build();
        when(membershipDao.membershipExists(memBuilder.getBand(), memBuilder.getArtist())).thenReturn(false);
        when(membershipDao.createMembership(memBuilder)).thenReturn(memExpected);

        Membership mem = membershipService.createMembershipInvite(memBuilder);
        assertNotNull(mem);
        assertEquals(memExpected, mem);
    }

    @Test
    public void testChangeStateToAccepted() {
        when(authFacadeService.getCurrentUser()).thenReturn(ARTIST);
        when(membershipDao.getMembershipById(mem4.getId())).thenReturn(Optional.of(mem4));

        doNothing().when(mailingService).sendInvitationAcceptedEmail(any(), any(), any());
        Membership memRet = membershipService.changeState(mem4.getId(), MembershipState.ACCEPTED);

        assertEquals(MembershipState.ACCEPTED, memRet.getState());
    }

    @Test
    public void testChangeStateToRejected() {
        when(authFacadeService.getCurrentUser()).thenReturn(ARTIST);
        when(membershipDao.getMembershipById(mem5.getId())).thenReturn(Optional.of(mem5));

        Membership memRet = membershipService.changeState(mem5.getId(), MembershipState.REJECTED);
        assertEquals(MembershipState.REJECTED, memRet.getState());
    }

    @Test
    public void testEditMembershipById() {
        Set<Role> roles = new HashSet<>();
        String newDescription = "Nueva descripcion";
        List<String> roleList = Collections.singletonList("Role1");
        roles.add(new Role(1, "Role1"));
        when(membershipDao.getMembershipById(1L)).thenReturn(Optional.of(mem5));
        when(authFacadeService.getCurrentUser()).thenReturn(BAND2);
        when(roleService.getRolesByNames(roleList)).thenReturn(roles);

        Membership editedMem = membershipService.editMembershipById(newDescription, roleList, 1L);
        assertNotNull(editedMem);
        assertEquals(newDescription, editedMem.getDescription());
        assertEquals(roles, editedMem.getRoles());
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

    @Test
    public void testIsInBand() {
        when(membershipDao.isInBand(BAND, ARTIST)).thenReturn(true);

        boolean isInBand = membershipService.isInBand(BAND, ARTIST);
        assertTrue(isInBand);
    }

    @Test
    public void testIsNotInBand() {
        boolean isInBand = membershipService.isInBand(ARTIST, ARTIST);
        assertFalse(isInBand);
    }
}
