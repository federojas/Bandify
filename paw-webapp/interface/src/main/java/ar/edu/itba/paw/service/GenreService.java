package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Genre;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface GenreService {
    Set<Genre> getAll();
    Optional<Genre> getGenreById(Long id);
    Set<Genre> getGenresByNames(List<String> rolesNames);
}
