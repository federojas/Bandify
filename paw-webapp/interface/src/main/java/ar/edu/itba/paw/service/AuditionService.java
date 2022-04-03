package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Audition;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface AuditionService {

    Optional<Audition> getAuditionById(long id);

    Audition create(String title, String description, String location, Date creationDate, List<String> musicGenres, List<String> lookingFor);

    List<Audition> getAll(int page);
}
