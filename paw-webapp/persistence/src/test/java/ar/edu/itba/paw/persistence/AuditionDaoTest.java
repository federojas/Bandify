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

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;


import java.util.*;


import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:auditionDaoTest.sql")
@Rollback
@Transactional
public class AuditionDaoTest {

    @Autowired
    private AuditionDao auditionDao;

    @Autowired
    private DataSource ds;

    @PersistenceContext
    private EntityManager em;

    private JdbcTemplate jdbcTemplate;

    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final long USER_ID = 1;
    private static final long OTHER_USER_ID = 2;
    private static final LocalDateTime CREATION_DATE = LocalDateTime.of(2022 ,7, 5, 14, 23, 30);
    private static final LocalDateTime CREATION_DATE_PAGE1 = LocalDateTime.of(2022 ,8, 5, 14, 23, 30);
    private static final LocalDateTime CREATION_DATE_PAGE2 = LocalDateTime.of(2022 ,6, 5, 14, 23, 30);
    private static final int PAGE_SIZE = 9;
    private static final long INVALID_ID = 20;

    private static final String QUERY_TITLE = "query title";
    private static final String NEW_TITLE = "new title";
    private static final String NEW_DESCRIPTION = "new description";

    private static final Location location =  new Location(1L, "location");
    private static final Location location2 =  new Location(2L, "location2");
    private static final Location location3 =  new Location(3L, "location3");
    private static final Location editLocation =  new Location(4L, "locationUnique");
    private static final Genre genre =  new Genre( "genre",1);
    private static final Role role =  new Role(1, "role");
    private static final Genre genre2 =  new Genre( "genre2",2);
    private static final Role role2 =  new Role(2, "role2");
    private static final Genre editGenre =  new Genre( "genreUnique",3);
    private static final Role editRole =  new Role(3, "roleUnique");

    private static final List<Role> ALL_ROLES = Arrays.asList(role, role2);
    private static final List<Genre> ALL_GENRES = Arrays.asList(genre, genre2);

    private static final User USER = new User.UserBuilder("band@mail.com","12345678","name",true,true).id(USER_ID).build();
    private static final User OTHER_USER = new User.UserBuilder("band2@mail.com","12345678","name",true,true).id(OTHER_USER_ID).build();

    private static final Audition aud1 = new Audition.AuditionBuilder(TITLE, DESCRIPTION, USER, CREATION_DATE).location(location).lookingFor(new HashSet<>(ALL_ROLES)).musicGenres(new HashSet<>(ALL_GENRES)).id(1L).build();
    private static final Audition aud2 = new Audition.AuditionBuilder(TITLE, DESCRIPTION, USER, CREATION_DATE).location(location).lookingFor(role).musicGenres(genre).id(2L).build();
    private static final Audition aud3 = new Audition.AuditionBuilder(TITLE, DESCRIPTION, USER, CREATION_DATE).location(location).lookingFor(role).musicGenres(genre).id(3L).build();
    private static final Audition aud4 = new Audition.AuditionBuilder(TITLE, DESCRIPTION, USER, CREATION_DATE).location(location2).lookingFor(role).musicGenres(genre).id(4L).build();
    private static final Audition aud5 = new Audition.AuditionBuilder(TITLE, DESCRIPTION, USER, CREATION_DATE).location(location2).lookingFor(role).musicGenres(genre).id(5L).build();
    private static final Audition aud6 = new Audition.AuditionBuilder(TITLE, DESCRIPTION, USER, CREATION_DATE).location(location2).lookingFor(role2).musicGenres(genre2).id(6L).build();
    private static final Audition aud7 = new Audition.AuditionBuilder(TITLE, DESCRIPTION, USER, CREATION_DATE).location(location3).lookingFor(role).musicGenres(genre).id(7L).build();
    private static final Audition aud8 = new Audition.AuditionBuilder(TITLE, DESCRIPTION, USER, CREATION_DATE).location(location3).lookingFor(role).musicGenres(genre).id(8L).build();
    private static final Audition aud9 = new Audition.AuditionBuilder(TITLE, DESCRIPTION, USER, CREATION_DATE).location(location).lookingFor(role).musicGenres(genre).id(9L).build();
    private static final Audition aud10 = new Audition.AuditionBuilder(QUERY_TITLE, DESCRIPTION, USER, CREATION_DATE).location(location).lookingFor(role).musicGenres(genre).id(10L).build();
    private static final Audition aud11 = new Audition.AuditionBuilder(TITLE, DESCRIPTION, USER, CREATION_DATE).location(location).lookingFor(new HashSet<>(ALL_ROLES)).musicGenres(new HashSet<>(ALL_GENRES)).id(11L).build();

    private static final Audition otherUserAuditionPage1 = new Audition.AuditionBuilder(TITLE, DESCRIPTION, OTHER_USER, CREATION_DATE_PAGE1).location(location).lookingFor(role).musicGenres(genre).id(12L).build();
    private static final Audition otherUserAuditionPage2 = new Audition.AuditionBuilder(TITLE, DESCRIPTION, OTHER_USER, CREATION_DATE_PAGE2).location(location).lookingFor(role).musicGenres(genre).id(13L).build();

    private static final List<Audition> PAGE_1_AUDITIONS = Arrays.asList(otherUserAuditionPage1, aud1, aud2, aud3, aud4, aud5, aud6, aud7, aud8);
    private static final List<Audition> PAGE_2_AUDITIONS = Arrays.asList(aud9, aud10, aud11, otherUserAuditionPage2);
    private static final List<Audition> PAGE_1_LOCATION_AUDITIONS = Arrays.asList(otherUserAuditionPage1, aud1, aud2, aud3, aud4, aud5, aud6, aud9, aud10);
    private static final List<Audition> PAGE_2_LOCATION_AUDITIONS = Arrays.asList(aud11, otherUserAuditionPage2);
    private static final List<Audition> PAGE_1_ROLE_AUDITIONS = Arrays.asList(otherUserAuditionPage1, aud1, aud2, aud3, aud4, aud5, aud7, aud8, aud9);
    private static final List<Audition> PAGE_1_GENRE_AUDITIONS = Arrays.asList(otherUserAuditionPage1, aud1, aud2, aud3, aud4, aud5, aud6, aud7, aud8);
    private static final List<Audition> PAGE_1_LOCATION_AUDITIONS_ASCENDING =Arrays.asList(otherUserAuditionPage2, aud1, aud2, aud3, aud4, aud5, aud6, aud9, aud10);

    private static final List<Audition> PAGE_1_BAND_AUDITIONS = Arrays.asList(aud1, aud2, aud3, aud4, aud5, aud6, aud7, aud8, aud9);
    private static final List<Audition> PAGE_2_BAND_AUDITIONS = Arrays.asList(aud10, aud11);

    private static final FilterOptions locationFilter = new FilterOptions.FilterOptionsBuilder().withLocations(Arrays.asList(location.getName(), location2.getName())).build();
    private static final FilterOptions genreFilter = new FilterOptions.FilterOptionsBuilder().withGenres(Arrays.asList(genre.getName(), genre2.getName())).build();
    private static final FilterOptions roleFilter = new FilterOptions.FilterOptionsBuilder().withRoles(Collections.singletonList(role.getName())).build();
    private static final FilterOptions allFilter = new FilterOptions.FilterOptionsBuilder().withLocations(Collections.singletonList(location2.getName())).withGenres(Collections.singletonList(genre2.getName())).withRoles(Collections.singletonList(role2.getName())).build();
    private static final FilterOptions impossibleFilter = new FilterOptions.FilterOptionsBuilder().withLocations(Collections.singletonList(editLocation.getName())).withGenres(Collections.singletonList(editGenre.getName())).withRoles(Collections.singletonList(editRole.getName())).build();
    private static final FilterOptions allQueryFilter = new FilterOptions.FilterOptionsBuilder().withLocations(Collections.singletonList(location.getName())).withGenres(Collections.singletonList(genre.getName())).withRoles(Collections.singletonList(role.getName())).withTitle(QUERY_TITLE).build();
    private static final FilterOptions locationFilterAscending = new FilterOptions.FilterOptionsBuilder().withLocations(Arrays.asList(location.getName(), location2.getName())).withOrder("ASC").build();


    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    @Test
    public void testCreateAudition() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate,"auditiongenres");
        JdbcTestUtils.deleteFromTables(jdbcTemplate,"auditionroles");
        JdbcTestUtils.deleteFromTables(jdbcTemplate,"auditions");
        final Audition audition = auditionDao.create(new Audition.AuditionBuilder(TITLE, DESCRIPTION, USER, CREATION_DATE));
        em.flush();
        assertNotNull(audition);
        assertEquals(TITLE, audition.getTitle());
        assertEquals(DESCRIPTION, audition.getDescription());
        assertEquals(USER, audition.getBand());
        assertEquals(CREATION_DATE, audition.getCreationDate());
        assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "auditions", "id = " + audition.getId()));
    }

    @Test
    public void testGetAllNotFullPage() {
        List<Audition> auditions = auditionDao.getAll(2);
        assertTrue(PAGE_2_AUDITIONS.containsAll(auditions));
        assertEquals(4, auditions.size());
    }

    @Test
    public void testGetAllFullPage() {
        List<Audition> auditions = auditionDao.getAll(1);
        assertTrue(PAGE_1_AUDITIONS.containsAll(auditions));
        assertEquals(PAGE_SIZE, auditions.size());
    }

    @Test
    public void testGetAllEmptyPage() {
        List<Audition> auditions = auditionDao.getAll(3);
        assertNotNull(auditions);
        assertTrue(auditions.isEmpty());
    }

    @Test
    public void testGetAuditionById() {
        Optional<Audition> audition = auditionDao.getAuditionById(aud1.getId());
        assertNotNull(audition);
        assertTrue(audition.isPresent());
        assertEquals(aud1, audition.get());
    }

    @Test
    public void testGetAuditionByInvalidId() {
        Optional<Audition> audition = auditionDao.getAuditionById(INVALID_ID);
        assertNotNull(audition);
        assertFalse(audition.isPresent());
    }

    @Test
    public void testGetFullPageBandAuditions() {
        List<Audition> auditions = auditionDao.getBandAuditions(USER_ID, 1);
        assertTrue(PAGE_1_BAND_AUDITIONS.containsAll(auditions));
        assertFalse(auditions.contains(otherUserAuditionPage1));
        assertEquals(PAGE_SIZE, auditions.size());
    }

    @Test
    public void testGetNotFullPageBandAuditions() {
        List<Audition> auditions = auditionDao.getBandAuditions(USER_ID, 2);
        assertTrue(PAGE_2_BAND_AUDITIONS.containsAll(auditions));
        assertFalse(auditions.contains(otherUserAuditionPage2));
        assertEquals(2, auditions.size());
    }

    @Test
    public void testGetInvalidBandAuditions() {
        List<Audition> auditions = auditionDao.getBandAuditions(INVALID_ID, 1);
        assertNotNull(auditions);
        assertTrue(auditions.isEmpty());
    }

    @Test
    public void testGetTotalPages() {
        int pages = auditionDao.getTotalPages();
        assertEquals(2, pages);
    }

    @Test
    public void testGetTotalBandAuditionPages() {
        int pages = auditionDao.getTotalBandAuditionPages(USER_ID);
        assertEquals(2,pages);
    }

    @Test
    public void testGetTotalInvalidBandAuditionPages() {
        int pages = auditionDao.getTotalBandAuditionPages(INVALID_ID);
        assertEquals(0,pages);
    }

    @Test
    public void testGetTotalPagesByLocationFilter() {
        int pages = auditionDao.getTotalPages(locationFilter);
        assertEquals(2, pages);
    }

    @Test
    public void testGetTotalPagesByRoleFilter() {
        int pages = auditionDao.getTotalPages(roleFilter);
        assertEquals(2, pages);
    }

    @Test
    public void testGetTotalPagesByGenreFilter() {
        int pages = auditionDao.getTotalPages(genreFilter);
        assertEquals(2, pages);
    }

    @Test
    public void testGetTotalPagesByAllFilter() {
        int pages = auditionDao.getTotalPages(allFilter);
        assertEquals(1, pages);
    }

    @Test
    public void testGetTotalPagesByImpossibleFilter() {
        int pages = auditionDao.getTotalPages(impossibleFilter);
        assertEquals(0, pages);
    }

    @Test
    public void testGetAuditionsByLocationFilterFullPage() {
        List<Audition> auditions = auditionDao.filter(locationFilter,1);
        assertTrue(PAGE_1_LOCATION_AUDITIONS.containsAll(auditions));
        assertEquals(PAGE_SIZE, auditions.size());
    }

    @Test
    public void testGetAuditionsByLocationFilterNotFullPage() {
        List<Audition> auditions = auditionDao.filter(locationFilter,2);
        assertTrue(PAGE_2_LOCATION_AUDITIONS.containsAll(auditions));
        assertEquals(2, auditions.size());
    }

    @Test
    public void testGetAuditionsByRoleFilterFullPage() {
        List<Audition> auditions = auditionDao.filter(roleFilter,1);
        assertTrue(PAGE_1_ROLE_AUDITIONS.containsAll(auditions));
        assertEquals(PAGE_SIZE, auditions.size());
    }

    @Test
    public void testGetAuditionsByGenreFilterFullPage() {
        List<Audition> auditions = auditionDao.filter(genreFilter,1);
        assertTrue(PAGE_1_GENRE_AUDITIONS.containsAll(auditions));
        assertEquals(PAGE_SIZE, auditions.size());
    }

    @Test
    public void testGetAuditionsByImpossibleFilter() {
        List<Audition> auditions = auditionDao.filter(impossibleFilter, 1);
        assertTrue(auditions.isEmpty());
    }

    @Test
    public void testGetAuditionsByAllFilter() {
        List<Audition> auditions = auditionDao.filter(allFilter,1);
        assertEquals(aud6, auditions.get(0));
        assertEquals(1, auditions.size());
    }

    @Test
    public void testGetAuditionsByAllQueryFilter() {
        List<Audition> auditions = auditionDao.filter(allQueryFilter,1);
        assertEquals(aud10, auditions.get(0));
        assertEquals(1, auditions.size());
    }

    @Test
    public void testGetAuditionsByLocationFilterAscending() {
        List<Audition> auditions = auditionDao.filter(locationFilterAscending,1);
        assertTrue(PAGE_1_LOCATION_AUDITIONS_ASCENDING.containsAll(auditions));
        assertEquals(PAGE_SIZE, auditions.size());
    }


}


