package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Genre;
import ar.edu.itba.paw.model.Role;
import ar.edu.itba.paw.model.User;
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
    private static final List<Genre> GENRE_LIST = Arrays.asList(ROCK, POP);

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
