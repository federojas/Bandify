package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Genre;
import ar.edu.itba.paw.model.exceptions.GenreNotFoundException;
import ar.edu.itba.paw.persistence.GenreDao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GenreServiceTest {

    @Mock
    private GenreDao genreDao;

    @InjectMocks
    private GenreService genreService = new GenreServiceImpl();

    private static final String INVALID = "INVALID";

    private static final Genre ROCK = new Genre("ROCK", 1);
    private static final Genre POP = new Genre("POP", 2);
    private static final Genre CUMBIA = new Genre("CUMBIA", 3);
    private static final Genre JAZZ = new Genre("JAZZ", 4);
    private static final Genre COUNTRY = new Genre("COUNTRY", 5);
    private static final Genre REGGAE = new Genre("REGGAE", 6);

    private static final List<Genre> GENRE_LIST = Arrays.asList(ROCK, POP, CUMBIA, JAZZ, COUNTRY, REGGAE);

    @Test
    public void testGetAll() {
        when(genreDao.getAll()).thenReturn(new HashSet<>(GENRE_LIST));
        Assert.assertEquals(new HashSet<>(GENRE_LIST), genreService.getAll());
    }

    @Test
    public void testGetAllEmpty() {
        when(genreDao.getAll()).thenReturn(new HashSet<>());
        Assert.assertEquals(new HashSet<>(), genreService.getAll());
    }

    @Test
    public void testGetGenresByNames() {
        Set<Genre> expected = new HashSet<>(Arrays.asList(ROCK, POP, CUMBIA));
        when(genreDao.getAll()).thenReturn(new HashSet<>(GENRE_LIST));
        when(genreDao.getGenresByNames(Arrays.asList("ROCK", "POP", "CUMBIA"))).thenReturn(expected);

        Set<Genre> genres = genreService.getGenresByNames(Arrays.asList("ROCK", "POP", "CUMBIA"));

        Assert.assertEquals(expected, genres);
    }


    @Test(expected = GenreNotFoundException.class)
    public void testValidateAndReturnGenresWithInvalidGenre() {
        when(genreDao.getAll()).thenReturn(new HashSet<>(GENRE_LIST));
        genreService.getGenresByNames(Collections.singletonList(INVALID));
        Assert.fail("Should have thrown GenreNotFoundException");
    }

    @Test
    public void testValidateAndReturnGenresWithNullGenre() {
        Set<Genre> genreSet = genreService.getGenresByNames(null);
        Assert.assertEquals(0,genreSet.size());
    }

    @Test
    public void testGetGenresByNamesWithEmptyNamesList() {
        when(genreDao.getAll()).thenReturn(new HashSet<>(GENRE_LIST));
        Set<Genre> genreSet = genreService.getGenresByNames(new ArrayList<>());
        Assert.assertEquals(0,genreSet.size());
    }

}
