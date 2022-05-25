package ar.edu.itba.paw.service;

import ar.edu.itba.paw.Genre;

import java.util.List;
import java.util.Set;

public interface GenreService {
    Set<Genre> getAll();

    Set<Genre> getGenresByNames(List<String> rolesNames);
}
