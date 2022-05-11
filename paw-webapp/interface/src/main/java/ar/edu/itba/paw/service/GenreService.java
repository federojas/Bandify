package ar.edu.itba.paw.service;

import ar.edu.itba.paw.Genre;

import java.util.List;
import java.util.Set;

public interface GenreService {
    Set<Genre> getAll();

    Set<Genre> getGenresByNames(List<String> rolesNames);

    Set<Genre> getUserGenres(long userId);

    void updateUserGenres(List<String> genresNames, long userId);
}
