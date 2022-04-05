package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Audition;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface AuditionDao {

    Optional<Audition> getAuditionById(long id);

    Audition create(String title, String description, String location, Date creationDate, List<String> musicGenres, List<String> lookingFor);

    List<Audition> getAll(int page);

}
