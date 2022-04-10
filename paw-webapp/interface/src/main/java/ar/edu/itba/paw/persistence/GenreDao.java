package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreDao {
    List<Genre> getAll();
    List<Genre> getGenresByAuditionId(long auditionId);

    void createAuditionGenre(List<Genre> genres, long auditionId);

    Optional<Genre> getGenreById(Long id);
}
