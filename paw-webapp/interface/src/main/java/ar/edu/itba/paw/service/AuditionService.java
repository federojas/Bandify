package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.persistence.Audition;

import java.util.List;
import java.util.Optional;

public interface AuditionService {

    Optional<Audition> getAuditionById(long id);

    Audition create(Audition.AuditionBuilder builder);

    List<Audition> getAll(int page);

    long getMaxAuditionId();

    int getTotalPages(String query);

    List<Audition> search(int page, String query);

    List<Audition> getBandAuditions(long userId);
}
