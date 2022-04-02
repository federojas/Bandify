package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Audition;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface AuditionDao {

    Optional<Audition> getAuditionById(long ind);

    Audition create(String title, String description, String location, Date creationDate, String musicGenres, String lookingFor);

    List<Audition> getAll(int page);

}
