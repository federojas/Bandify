package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Genre;
import ar.edu.itba.paw.model.exceptions.GenreNotFoundException;
import ar.edu.itba.paw.persistence.GenreDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class GenreServiceImpl implements GenreService{

    private final GenreDao genreDao;

    @Autowired
    public GenreServiceImpl(GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    @Override
    public Set<Genre> getAll() {
        return genreDao.getAll();
    }

    @Override
    public Set<Genre> getGenresByAuditionId(long auditionId) {
        return genreDao.getGenresByAuditionId(auditionId);
    }

    @Override
    public Set<Genre> validateAndReturnGenres(List<String> genresNames) {
        Set<Genre> genres = genreDao.getGenresByNames(genresNames);
        if(genres.size() != genresNames.size())
            throw new GenreNotFoundException();
        return genres;
    }

    @Override
    public Set<Genre> getUserGenres(long userId) {
        return genreDao.getUserGenres(userId);
    }

    @Override
    public void addUserGenres(List<String> genresNames, long userId) {
        genreDao.addUserGenres(genresNames,userId);
    }

}
