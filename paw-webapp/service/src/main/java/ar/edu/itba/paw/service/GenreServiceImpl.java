package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.exceptions.RoleNotFoundException;
import ar.edu.itba.paw.persistence.Genre;
import ar.edu.itba.paw.model.exceptions.GenreNotFoundException;
import ar.edu.itba.paw.persistence.GenreDao;
import ar.edu.itba.paw.persistence.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

        List<String> genres = genreDao.getAll().stream().map(Genre::getName).collect(Collectors.toList());

        if(!genres.containsAll(genresNames))
            throw new GenreNotFoundException();

        return genreDao.getGenresByNames(genresNames);
    }

    @Override
    public Set<Genre> getUserGenres(long userId) {
        return genreDao.getUserGenres(userId);
    }

    @Override
    public void updateUserGenres(List<String> genresNames, long userId) {
        if(genresNames == null || genresNames.isEmpty())
            return;
        Set<Genre> newGenres = validateAndReturnGenres(genresNames);
        genreDao.updateUserGenres(newGenres, userId);
    }

}
