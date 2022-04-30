package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Genre;

import java.util.List;
import java.util.Set;

public interface GenreService {
    Set<Genre> getAll();

    Set<Genre> getGenresByAuditionId(long auditionId);

    Set<Genre> validateAndReturnGenres(List<String> rolesNames);

    Set<Genre> getUserGenres(long userId);

    void updateUserGenres(List<String> genresNames, long userId);
}
