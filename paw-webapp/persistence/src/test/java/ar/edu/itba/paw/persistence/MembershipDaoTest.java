package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.*;
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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.util.*;


import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:membershipDaoTest.sql")
@Rollback
@Transactional
public class MembershipDaoTest {

    @Autowired
    private MembershipDao membershipDao;

    @Autowired
    private DataSource ds;

    @PersistenceContext
    private EntityManager em;

    private JdbcTemplate jdbcTemplate;

    private static final Role role1 = new Role(1L, "role");
    private static final User ARTIST = new User.UserBuilder("artist@mail.com","12345678","name",false,false).surname("surname").description("description").id(1L).build();
    private static final User ARTIST2 = new User.UserBuilder("artist2@mail.com","12345678","name",false,false).surname("surname").description("description").id(3L).build();
    private static final User BAND = new User.UserBuilder("band@mail.com","12345678", "name", true, false).description("description").id(2L).build();
    private static final User BAND2 = new User.UserBuilder("band2@mail.com","12345678", "name", true, false).description("description").id(4L).build();
    private static final User BAND3 = new User.UserBuilder("band3@mail.com","12345678", "name", true, false).description("description").id(5L).build();


    private static final Membership mem1 = new Membership.Builder(ARTIST, BAND).roles(role1).description("description").state(MembershipState.PENDING).id(1L).build();
    private static final Membership mem2 = new Membership.Builder(ARTIST2, BAND).roles(role1).description("description").state(MembershipState.ACCEPTED).id(2L).build();
    private static final Membership mem4 = new Membership.Builder(ARTIST, BAND2).roles(role1).description("description").state(MembershipState.PENDING).id(4L).build();

    private static final List<Membership> artist1Memberships = Arrays.asList(mem1, mem4);

    private static final List<Membership> band1PendingMemberships = Collections.singletonList(mem2);

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    @Test
    public void testCreateMembership() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate,"membershiproles");
        JdbcTestUtils.deleteFromTables(jdbcTemplate,"memberships");
        final Membership membership = membershipDao.createMembership(new Membership.Builder(
                new User.UserBuilder("artist@mail.com","12345678","name",false,false).surname("surname").description("description").id(1L).build(),
                new User.UserBuilder("band3@mail.com","12345678", "name", true, false).description("description").id(5L).build()).description("description"));
        em.flush();
        assertNotNull(membership);
        assertEquals("description", membership.getDescription());
        assertEquals(ARTIST, membership.getArtist());
        assertEquals(BAND3, membership.getBand());
        assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "memberships", "id = " + membership.getId()));
    }

    @Test
    public void testGetUserMembershipsByStateArtist() {
        List<Membership> memberships = membershipDao.getUserMembershipsByState(ARTIST, MembershipState.PENDING, 1);
        assertEquals(artist1Memberships.size(), memberships.size());
        assertTrue(artist1Memberships.containsAll(memberships));
    }

    @Test
    public void testGetUserMembershipsByStateBand() {
        List<Membership> memberships = membershipDao.getUserMembershipsByState(BAND, MembershipState.ACCEPTED, 1);
        assertEquals(band1PendingMemberships.size(), memberships.size());
        assertTrue(band1PendingMemberships.containsAll(memberships));
    }


    @Test
    public void testGetUserMembershipsByStateNone() {
        List<Membership> memberships = membershipDao.getUserMembershipsByState(ARTIST, MembershipState.ACCEPTED, 1);
        assertTrue(memberships.isEmpty());
    }

    @Test
    public void testGetUserMembershipsById() {
        Optional<Membership> membership = membershipDao.getMembershipById(mem1.getId());
        assertNotNull(membership);
        assertTrue(membership.isPresent());
        assertEquals(mem1, membership.get());
    }

    @Test
    public void testGetUserMembershipsPreviewArtist() {
        List<Membership> memberships = membershipDao.getUserMembershipsPreview(ARTIST2);
        assertEquals(1, memberships.size());
        assertEquals(mem2, memberships.get(0));
    }

    @Test
    public void testGetUserMembershipsPreviewBand() {
        List<Membership> memberships = membershipDao.getUserMembershipsPreview(BAND);
        assertEquals(1, memberships.size());
        assertEquals(mem2, memberships.get(0));
    }

    @Test
    public void testGetTotalUserMembershipsByStatePages() {
        int pages = membershipDao.getTotalUserMembershipsByStatePages(BAND, MembershipState.PENDING);
        assertEquals(1, pages);
    }

    @Test
    public void testDeleteMembershipById() {
        membershipDao.deleteMembership(mem1.getId());
        em.flush();
        assertEquals(0, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "memberships", "id = " + mem1.getId()));
        assertEquals(0, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "memberships", "artistid = " + mem1.getArtist().getId() + " and bandid = " + mem1.getBand().getId()));
        assertEquals(0, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "membershiproles", "membershipid = " + mem1.getId()));
    }

    @Test
    public void testGetMembershipById() {
        Optional<Membership> membership = membershipDao.getMembershipById(1L);
        assertNotNull(membership);
        assertTrue(membership.isPresent());
        assertEquals(mem1, membership.get());
    }

    @Test
    public void testGetMembershipByUsers() {
        Optional<Membership> membership = membershipDao.getMembershipByUsers(BAND, ARTIST);
        assertNotNull(membership);
        assertTrue(membership.isPresent());
        assertEquals(mem1, membership.get());
    }

    @Test
    public void testMembershipExists() {
        boolean exists = membershipDao.membershipExists(BAND, ARTIST);
        assertTrue(exists);
    }

    @Test
    public void testMembershipNotExists() {
        boolean exists = membershipDao.membershipExists(BAND3, ARTIST);
        assertFalse(exists);
    }

    @Test
    public void testIsInBand() {
        boolean isInBand = membershipDao.isInBand(BAND, ARTIST2);
        assertTrue(isInBand);
    }

    @Test
    public void testNotInBand() {
        boolean isInBand = membershipDao.isInBand(BAND, ARTIST);
        assertFalse(isInBand);
    }

    @Test
    public void testGetPendingMembershipsCount() {
        int count = membershipDao.getPendingMembershipsCount(ARTIST);
        assertEquals(2, count);
    }

}