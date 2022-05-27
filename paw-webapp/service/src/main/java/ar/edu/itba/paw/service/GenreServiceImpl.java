package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Genre;
import ar.edu.itba.paw.model.exceptions.GenreNotFoundException;
import ar.edu.itba.paw.persistence.GenreDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GenreServiceImpl implements GenreService{

    @Autowired
    private GenreDao genreDao;

    @Override
    public Set<Genre> getAll() {
        return genreDao.getAll();
    }

    @Override
    public Set<Genre> getGenresByNames(List<String> genresNames) {

        if(genresNames == null)
            throw new IllegalArgumentException();

        List<String> genres = genreDao.getAll().stream().map(Genre::getName).collect(Collectors.toList());

        if(!genres.containsAll(genresNames))
            throw new GenreNotFoundException();

        return genreDao.getGenresByNames(genresNames);
    }

}
