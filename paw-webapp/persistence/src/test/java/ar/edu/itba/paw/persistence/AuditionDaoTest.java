package ar.edu.itba.paw.persistence;

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
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;


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
    private static final LocalDateTime CREATION_DATE = LocalDateTime.of(2022 ,7, 5, 14, 23, 30);
    private static final int PAGE_SIZE = 9;
    private static final long INVALID_ID = 20;

    private static final Location location =  new Location(1, "location");
    private static final Genre genre =  new Genre(1, "genre");
    private static final Role role =  new Role(1, "role");
    private static final Genre genre2 =  new Genre(2, "genre2");
    private static final Role role2 =  new Role(2, "role2");

    private static final List<Role> ALL_ROLES = Arrays.asList(role, role2);
    private static final List<Genre> ALL_GENRES = Arrays.asList(genre, genre2);

    private static final Audition aud1 = new Audition.AuditionBuilder(TITLE, DESCRIPTION, USER_ID, CREATION_DATE).location(location).lookingFor(new HashSet<>(ALL_ROLES)).musicGenres(new HashSet<>(ALL_GENRES)).id(1).build();
    private static final Audition aud2 = new Audition.AuditionBuilder(TITLE, DESCRIPTION, USER_ID, CREATION_DATE).location(location).lookingFor(role).musicGenres(genre).id(2).build();
    private static final Audition aud3 = new Audition.AuditionBuilder(TITLE, DESCRIPTION, USER_ID, CREATION_DATE).location(location).lookingFor(role).musicGenres(genre).id(3).build();
    private static final Audition aud4 = new Audition.AuditionBuilder(TITLE, DESCRIPTION, USER_ID, CREATION_DATE).location(location).lookingFor(role).musicGenres(genre).id(4).build();
    private static final Audition aud5 = new Audition.AuditionBuilder(TITLE, DESCRIPTION, USER_ID, CREATION_DATE).location(location).lookingFor(role).musicGenres(genre).id(5).build();
    private static final Audition aud6 = new Audition.AuditionBuilder(TITLE, DESCRIPTION, USER_ID, CREATION_DATE).location(location).lookingFor(role).musicGenres(genre).id(6).build();
    private static final Audition aud7 = new Audition.AuditionBuilder(TITLE, DESCRIPTION, USER_ID, CREATION_DATE).location(location).lookingFor(role).musicGenres(genre).id(7).build();
    private static final Audition aud8 = new Audition.AuditionBuilder(TITLE, DESCRIPTION, USER_ID, CREATION_DATE).location(location).lookingFor(role).musicGenres(genre).id(8).build();
    private static final Audition aud9 = new Audition.AuditionBuilder(TITLE, DESCRIPTION, USER_ID, CREATION_DATE).location(location).lookingFor(role).musicGenres(genre).id(9).build();
    private static final Audition aud10 = new Audition.AuditionBuilder(TITLE, DESCRIPTION, USER_ID, CREATION_DATE).location(location).lookingFor(role).musicGenres(genre).id(10).build();
    private static final Audition aud11 = new Audition.AuditionBuilder(TITLE, DESCRIPTION, USER_ID, CREATION_DATE).location(location).lookingFor(new HashSet<>(ALL_ROLES)).musicGenres(new HashSet<>(ALL_GENRES)).id(11).build();

    private static final List<Audition> PAGE_1_AUDITIONS = Arrays.asList(aud1, aud2, aud3, aud4, aud5, aud6, aud7, aud8, aud9);
    private static final List<Audition> PAGE_2_AUDITIONS = Arrays.asList(aud10, aud11);

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
        assertEquals(2, auditions.size());
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
}


