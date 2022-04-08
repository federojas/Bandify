package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Genre;

import java.util.List;

public interface GenreDao {
    List<Genre> getAll();
    List<Genre> getGenresByAuditionId(long auditionId);

    void createAuditionGenre(List<Genre> genres, long auditionId);

    Genre getGenreById(Long id);
}
