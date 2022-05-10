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

import java.util.*;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:genreDaoTest.sql")
@Rollback
@Transactional
public class GenreDaoTest {

    @Autowired
    private GenreJdbcDao genreDao;
    @Autowired
    private DataSource ds;

    private JdbcTemplate jdbcTemplate;

    private static final Genre genre = new Genre(1, "genre");
    private static final Genre genre2 = new Genre(2, "genre2");
    private static final Genre genre3 = new Genre(3, "genre3");

    private static final long USER_ID = 1;

    private static final List<Genre> ALL_GENRES = Arrays.asList(genre, genre2, genre3);
    private static final List<Genre> AUDITION_GENRES = Arrays.asList(genre, genre2);
    private static final List<Genre> USER_GENRES = Arrays.asList(genre, genre2);
    private static final List<Genre> GENRES = Arrays.asList(genre2, genre3);
    private static final List<String> GENRES_NAMES = Arrays.asList(genre2.getName(), genre3.getName());

    private static final long INVALID_ID = 20;
    private static final long AUDITION_ID = 1;
    private static final String INVALID_NAME = "INVALIDO";

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    @Test
    public void testGetGenreById() {
        final Optional<Genre> optionalGenre = genreDao.getGenreById(genre.getId());
        assertNotNull(optionalGenre);
        assertTrue(optionalGenre.isPresent());
        assertEquals(genre, optionalGenre.get());
    }

    @Test
    public void testGetGenreByInvalidId() {
        final Optional<Genre> optionalGenre = genreDao.getGenreById(INVALID_ID);
        assertNotNull(optionalGenre);
        assertFalse(optionalGenre.isPresent());
    }

    @Test
    public void testGetGenreByName() {
        final Optional<Genre> optionalGenre = genreDao.getGenreByName(genre.getName());
        assertNotNull(optionalGenre);
        assertTrue(optionalGenre.isPresent());
        assertEquals(genre, optionalGenre.get());
    }

    @Test
    public void testGetGenreByInvalidName() {
        final Optional<Genre> optionalGenre = genreDao.getGenreByName(INVALID_NAME);
        assertNotNull(optionalGenre);
        assertFalse(optionalGenre.isPresent());
    }

    @Test
    public void testGetAll() {
        final Set<Genre> genres = genreDao.getAll();
        assertTrue(ALL_GENRES.containsAll(genres));
        assertEquals(JdbcTestUtils.countRowsInTable(jdbcTemplate, "genres"), genres.size());
    }

    @Test
    public void testGetGenresByAuditionId() {
        final Set<Genre> genreSet = genreDao.getGenresByAuditionId(AUDITION_ID);
        assertTrue(AUDITION_GENRES.containsAll(genreSet));
        assertEquals(JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "auditiongenres", "auditionid = " + AUDITION_ID), genreSet.size());
    }

    @Test
    public void testGetGenresByInvalidAuditionId() {
        final Set<Genre> genreSet = genreDao.getGenresByAuditionId(INVALID_ID);
        assertTrue(genreSet.isEmpty());
    }

    @Test
    public void testGetGenresByNames() {
        final Set<Genre> genreSet = genreDao.getGenresByNames(GENRES_NAMES);
        assertTrue(GENRES.containsAll(genreSet));
        assertEquals(JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "genres", "genre = '" + GENRES_NAMES.get(0) + "' OR genre = '" + GENRES_NAMES.get(1) + "'"), genreSet.size());
    }

    @Test
    public void testGetGenresByInvalidNames() {
        final Set<Genre> genreSet = genreDao.getGenresByNames(Collections.singletonList(INVALID_NAME));
        assertTrue(genreSet.isEmpty());
    }

    @Test
    public void testGetUserGenres() {
        final Set<Genre> genreSet = genreDao.getUserGenres(USER_ID);
        assertTrue(USER_GENRES.containsAll(genreSet));
        assertEquals(USER_GENRES.size(), genreSet.size());
    }

    @Test
    public void testGetInvalidUserGenres() {
        final Set<Genre> genreSet = genreDao.getUserGenres(INVALID_ID);
        assertTrue(genreSet.isEmpty());
    }

    @Test
    public void testUpdateUserGenres() {
        genreDao.updateUserGenres(new HashSet<>(GENRES),USER_ID);
        assertEquals(GENRES.size(), JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "usergenres", "userid = " + USER_ID));
        assertEquals(0, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "usergenres", "userid = " + USER_ID + " AND genreid = " + genre.getId()));
        assertEquals(GENRES.size(), JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "usergenres", "userid = " + USER_ID + " AND (genreid = " + genre2.getId() + " OR genreid = " + genre3.getId() + ")"));
    }

    @Test
    public void testUpdateSameUserGenres() {
        genreDao.updateUserGenres(new HashSet<>(USER_GENRES),USER_ID);
        assertEquals(USER_GENRES.size(), JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "usergenres", "userid = " + USER_ID));
        assertEquals(0, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "usergenres", "userid = " + USER_ID + " AND genreid = " + genre3.getId()));
        assertEquals(USER_GENRES.size(), JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "usergenres", "userid = " + USER_ID + " AND (genreid = " + genre.getId() + " OR genreid = " + genre2.getId() + ")"));
    }
}