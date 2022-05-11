package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.exceptions.GenreNotFoundException;
import ar.edu.itba.paw.Genre;
import ar.edu.itba.paw.persistence.GenreDao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GenreServiceTest {

    @Mock
    private GenreDao genreDao;

    @InjectMocks
    private GenreService genreService = new GenreServiceImpl();

    private static final Genre ROCK = new Genre(1, "ROCK");
    private static final Genre POP = new Genre(2, "POP");
    private static final List<Genre> GENRE_LIST = Arrays.asList(ROCK, POP);

//    @Test
//    public void testValidateAndReturnGenres() {
//        when(genreDao.getAll()).thenReturn(new HashSet<>(GENRE_LIST));
//        genreService.
//    }


    @Test(expected = GenreNotFoundException.class)
    public void testValidateAndReturnGenresWithInvalidGenre() {
        when(genreDao.getAll()).thenReturn(new HashSet<>(GENRE_LIST));
        genreService.getGenresByNames(Arrays.asList("INVALIDO", "INVALIDO2"));
        Assert.fail("Should have thrown GenreNotFoundException");
    }


}
