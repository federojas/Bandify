package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Genre;

import java.util.List;

public interface GenreService {
    List<Genre> getAll();

    List<Genre> getGenresByAuditionId(long auditionId);

    List<Genre> validateAndReturnGenres(List<String> rolesNames);
}
