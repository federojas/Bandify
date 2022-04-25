package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Genre;
import ar.edu.itba.paw.model.exceptions.GenreNotFoundException;
import ar.edu.itba.paw.persistence.GenreDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GenreServiceImpl implements GenreService{

    private GenreDao genreDao;

    @Autowired
    public GenreServiceImpl(GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    @Override
    public List<Genre> getAll() {
        return genreDao.getAll();
    }

    @Override
    public List<Genre> getGenresByAuditionId(long auditionId) {
        return genreDao.getGenresByAuditionId(auditionId);
    }

    @Override
    public List<Genre> validateAndReturnGenres(List<String> genresNames) {
        // TODO: MEJORAR
        List<Genre> genres = new ArrayList<>();
        for(String name : genresNames) {
            genres.add(genreDao.getGenreByName(name).orElseThrow(GenreNotFoundException::new));
        }
        return genres;
    }

    @Override
    public List<Genre> getUserGenres(long userId) {
        return genreDao.getUserGenres(userId);
    }

    @Override
    public void addUserGenres(List<String> genresNames, long userId) {
        genreDao.addUserGenres(genresNames,userId);
    }

}
