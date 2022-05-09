package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.AuditionFilter;
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
@Sql("classpath:auditionDaoTest.sql")
@Rollback
@Transactional
public class AuditionDaoTest {
    @Autowired
    private AuditionJdbcDao auditionDao;
    @Autowired
    private DataSource ds;

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


    private static final String NEW_TITLE = "new title";
    private static final String NEW_DESCRIPTION = "new description";

    private static final Location location =  new Location(1, "location");
    private static final Location location2 =  new Location(2, "location2");
    private static final Location location3 =  new Location(3, "location3");
    private static final Location editLocation =  new Location(4, "locationUnique");
    private static final Genre genre =  new Genre(1, "genre");
    private static final Role role =  new Role(1, "role");
    private static final Genre genre2 =  new Genre(2, "genre2");
    private static final Role role2 =  new Role(2, "role2");
    private static final Genre editGenre =  new Genre(3, "genreUnique");
    private static final Role editRole =  new Role(3, "roleUnique");

    private static final List<Role> ALL_ROLES = Arrays.asList(role, role2);
    private static final List<Genre> ALL_GENRES = Arrays.asList(genre, genre2);

    private static final Audition aud1 = new Audition.AuditionBuilder(TITLE, DESCRIPTION, USER_ID, CREATION_DATE).location(location).lookingFor(new HashSet<>(ALL_ROLES)).musicGenres(new HashSet<>(ALL_GENRES)).id(1).build();
    private static final Audition aud2 = new Audition.AuditionBuilder(TITLE, DESCRIPTION, USER_ID, CREATION_DATE).location(location).lookingFor(role).musicGenres(genre).id(2).build();
    private static final Audition aud3 = new Audition.AuditionBuilder(TITLE, DESCRIPTION, USER_ID, CREATION_DATE).location(location).lookingFor(role).musicGenres(genre).id(3).build();
    private static final Audition aud4 = new Audition.AuditionBuilder(TITLE, DESCRIPTION, USER_ID, CREATION_DATE).location(location2).lookingFor(role).musicGenres(genre).id(4).build();
    private static final Audition aud5 = new Audition.AuditionBuilder(TITLE, DESCRIPTION, USER_ID, CREATION_DATE).location(location2).lookingFor(role).musicGenres(genre).id(5).build();
    private static final Audition aud6 = new Audition.AuditionBuilder(TITLE, DESCRIPTION, USER_ID, CREATION_DATE).location(location2).lookingFor(role2).musicGenres(genre).id(6).build();
    private static final Audition aud7 = new Audition.AuditionBuilder(TITLE, DESCRIPTION, USER_ID, CREATION_DATE).location(location3).lookingFor(role).musicGenres(genre).id(7).build();
    private static final Audition aud8 = new Audition.AuditionBuilder(TITLE, DESCRIPTION, USER_ID, CREATION_DATE).location(location3).lookingFor(role).musicGenres(genre).id(8).build();
    private static final Audition aud9 = new Audition.AuditionBuilder(TITLE, DESCRIPTION, USER_ID, CREATION_DATE).location(location).lookingFor(role).musicGenres(genre).id(9).build();
    private static final Audition aud10 = new Audition.AuditionBuilder(TITLE, DESCRIPTION, USER_ID, CREATION_DATE).location(location).lookingFor(role).musicGenres(genre).id(10).build();
    private static final Audition aud11 = new Audition.AuditionBuilder(TITLE, DESCRIPTION, USER_ID, CREATION_DATE).location(location).lookingFor(new HashSet<>(ALL_ROLES)).musicGenres(new HashSet<>(ALL_GENRES)).id(11).build();

    private static final Audition new_audition = new Audition.AuditionBuilder(TITLE, DESCRIPTION, USER_ID, CREATION_DATE).location(location).lookingFor(new HashSet<>(ALL_ROLES)).musicGenres(new HashSet<>(ALL_GENRES)).id(14).build();

    private static final Audition otherUserAuditionPage1 = new Audition.AuditionBuilder(TITLE, DESCRIPTION, OTHER_USER_ID, CREATION_DATE_PAGE1).location(location).lookingFor(role).musicGenres(genre).id(12).build();
    private static final Audition otherUserAuditionPage2 = new Audition.AuditionBuilder(TITLE, DESCRIPTION, OTHER_USER_ID, CREATION_DATE_PAGE2).location(location).lookingFor(role).musicGenres(genre).id(13).build();

    private static final List<Audition> PAGE_1_AUDITIONS = Arrays.asList(otherUserAuditionPage1, aud1, aud2, aud3, aud4, aud5, aud6, aud7, aud8);
    private static final List<Audition> PAGE_2_AUDITIONS = Arrays.asList(aud9, aud10, aud11, otherUserAuditionPage2);
    private static final List<Audition> PAGE_1_LOCATION_AUDITIONS = Arrays.asList(otherUserAuditionPage1, aud1, aud2, aud3, aud4, aud5, aud6, aud9, aud10);
    private static final List<Audition> PAGE_2_LOCATION_AUDITIONS = Arrays.asList(aud11, otherUserAuditionPage2);


    private static final List<Audition> PAGE_1_BAND_AUDITIONS = Arrays.asList(aud1, aud2, aud3, aud4, aud5, aud6, aud7, aud8, aud9);
    private static final List<Audition> PAGE_2_BAND_AUDITIONS = Arrays.asList(aud10, aud11);

    private static final AuditionFilter locationFilter = new AuditionFilter.AuditionFilterBuilder().withLocations(Arrays.asList(location.getName(), location2.getName())).build();
    private static final AuditionFilter genreFilter = new AuditionFilter.AuditionFilterBuilder().withGenres(Arrays.asList(genre.getName(), genre2.getName())).build();
    private static final AuditionFilter roleFilter = new AuditionFilter.AuditionFilterBuilder().withRoles(Collections.singletonList(role.getName())).build();
    private static final AuditionFilter allFilter = new AuditionFilter.AuditionFilterBuilder().withLocations(Collections.singletonList(location2.getName())).withGenres(Collections.singletonList(genre.getName())).withRoles(Collections.singletonList(role2.getName())).build();
    private static final AuditionFilter impossibleFilter = new AuditionFilter.AuditionFilterBuilder().withLocations(Collections.singletonList(editLocation.getName())).withGenres(Collections.singletonList(editGenre.getName())).withRoles(Collections.singletonList(editRole.getName())).build();


    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    @Test
    public void testCreateAudition() {
        final Audition audition = auditionDao.create(new Audition.AuditionBuilder(TITLE, DESCRIPTION, USER_ID, CREATION_DATE).location(location).lookingFor(new HashSet<>(ALL_ROLES)).musicGenres(new HashSet<>(ALL_GENRES)));
        assertNotNull(audition);
        assertEquals(TITLE, audition.getTitle());
        assertEquals(DESCRIPTION, audition.getDescription());
        assertEquals(USER_ID, audition.getBandId());
        assertEquals(CREATION_DATE, audition.getCreationDate());
        assertEquals(location, audition.getLocation());
        assertTrue(ALL_ROLES.containsAll(audition.getLookingFor()));
        assertTrue(ALL_GENRES.containsAll(audition.getMusicGenres()));
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
    public void testCreate(){
        Audition audition = auditionDao.create(new Audition.AuditionBuilder(TITLE, DESCRIPTION, USER_ID, CREATION_DATE).location(location).lookingFor(new HashSet<>(ALL_ROLES)).musicGenres(new HashSet<>(ALL_GENRES)).id(12));
        assertNotNull(audition);
        assertEquals(new_audition, audition);
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
    public void testDeleteAuditionById() {
        auditionDao.deleteAuditionById(aud1.getId());
        assertEquals(0, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "auditions", "id = " + aud1.getId()));
    }

    @Test
    public void testEditAuditionById() {
        auditionDao.editAuditionById(new Audition.AuditionBuilder(NEW_TITLE, NEW_DESCRIPTION, USER_ID, CREATION_DATE).id(1).musicGenres(editGenre).lookingFor(editRole).location(editLocation), aud1.getId());
        assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "auditions", "title = '" + NEW_TITLE + "' AND description = '" + NEW_DESCRIPTION + "' AND locationid = " + editLocation.getId()));
        assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "auditiongenres", "genreid = " + editGenre.getId()));
        assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "auditionroles", "roleid = " + editRole.getId()));
        assertEquals(0, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "auditions", "title = '" + TITLE + "' AND description = '" + DESCRIPTION + "' AND locationid = " + location.getId() + "AND id = " + aud1.getId()));
        assertEquals(0, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "auditiongenres", "genreid = " + genre.getId() + "AND auditionid = " + aud1.getId()));
        assertEquals(0, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "auditionroles", "roleid = " + role.getId()+ "AND auditionid = " + aud1.getId()));
        assertEquals(0, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "auditiongenres", "genreid = " + genre2.getId()+ "AND auditionid = " + aud1.getId()));
        assertEquals(0, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "auditionroles", "roleid = " + role2.getId()+ "AND auditionid = " + aud1.getId()));
    }

    @Test
    public void testGetMaxAuditionId() {
        long id = auditionDao.getMaxAuditionId();
        assertEquals(13, id);
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
    public void testGetAuditionsByImpossibleFilter() {
        List<Audition> auditions = auditionDao.filter(impossibleFilter, 1);
        assertTrue(auditions.isEmpty());
    }



    //TODO funciones filter

}


