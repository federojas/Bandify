//package ar.edu.itba.paw.service;
//
//import ar.edu.itba.paw.model.exceptions.GenreNotFoundException;
//import ar.edu.itba.paw.persistence.Genre;
//import ar.edu.itba.paw.persistence.GenreDao;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//
//import java.util.Arrays;
//import java.util.Collections;
//
//import static org.mockito.Mockito.*;
//import static org.junit.Assert.*;
//
//@RunWith(MockitoJUnitRunner.class)
//public class GenreServiceTest {
//
//
//    @Mock
//    private GenreDao genreDao;
//
//    @InjectMocks
//    private GenreServiceImpl genreService = new GenreServiceImpl(genreDao);
//
//
//    @Test
//    public void testValidateAndReturnGenres() {
//
//    }
//
//
////    @Test(expected = GenreNotFoundException.class)
////    public void testValidateAndReturnGenresWithInvalidGenre() {
////        when(genreDao.getAll()).thenReturn(Arrays.asList());
////        genreService.validateAndReturnGenres(Collections.singletonList("INVALIDO"));
////    }
//
//
//}
