package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Genre;
import ar.edu.itba.paw.model.Role;
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
    private GenreDao genreDao;
    @Autowired
    private DataSource ds;

    private JdbcTemplate jdbcTemplate;

    private static final Genre genre1 = new Genre("genre",1L);
    private static final Genre genre2 = new Genre("genre2",2L);
    private static final Genre genre3 = new Genre("genre3",3L);

    private static final List<Genre> GENRES = Arrays.asList(genre1, genre2, genre3);
    private static final List<String> GENRES_NAMES = Arrays.asList(genre1.getName(), genre2.getName(), genre3.getName());

    private static final long INVALID_ID = 20;
    private static final String INVALID_NAME = "INVALIDO";

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    @Test
    public void testGetGenreById() {
        final Optional<Genre> optionalGenre = genreDao.getGenreById(1L);

        assertNotNull(optionalGenre);
        assertTrue(optionalGenre.isPresent());

        assertEquals(genre1, optionalGenre.get());
    }

    @Test
    public void testGetGenreByInvalidId() {
        final Optional<Genre> optionalGenre = genreDao.getGenreById(INVALID_ID);
        assertNotNull(optionalGenre);
        assertFalse(optionalGenre.isPresent());
    }

    @Test
    public void testGetGenreByName() {
        final Optional<Genre> optionalGenre = genreDao.getGenreByName(genre1.getName());
        assertNotNull(optionalGenre);
        assertTrue(optionalGenre.isPresent());
        assertEquals(genre1, optionalGenre.get());
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

        assertNotNull(genres);
        assertEquals(3, genres.size());
        assertTrue(GENRES.containsAll(genres));
        assertEquals(GENRES.size(), genres.size());
        assertEquals(JdbcTestUtils.countRowsInTable(jdbcTemplate, "genres"), genres.size());
    }

    @Test
    public void testGetGenresByNames() {
        final Set<Genre> genreSet = genreDao.getGenresByNames(GENRES_NAMES);
        assertNotNull(genreSet);
        assertEquals(3, genreSet.size());
        assertTrue(GENRES.containsAll(genreSet));
        assertEquals(GENRES.size(), genreSet.size());
        assertEquals(JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "genres", "genre = '" + GENRES_NAMES.get(0) + "' OR genre = '" + GENRES_NAMES.get(1) + "' OR genre = '" + GENRES_NAMES.get(2) + "'"), genreSet.size());
    }

    @Test
    public void testGetGenresByInvalidNames() {
        final Set<Genre> genreSet = genreDao.getGenresByNames(Collections.singletonList(INVALID_NAME));
        assertTrue(genreSet.isEmpty());
    }

}