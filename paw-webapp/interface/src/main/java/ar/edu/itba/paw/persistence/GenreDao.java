package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.Genre;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface GenreDao {
    Set<Genre> getAll();

    Optional<Genre> getGenreById(Long id);

    Optional<Genre> getGenreByName(String name);

    Set<Genre> getGenresByNames(List<String> genresNames);

    Set<Genre> getUserGenres(long userId);

    void updateUserGenres(Set<Genre> newGenres, long userId);
}
