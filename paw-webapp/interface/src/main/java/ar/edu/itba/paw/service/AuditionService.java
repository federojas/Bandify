package ar.edu.itba.paw.service;

import ar.edu.itba.paw.AuditionFilter;
import ar.edu.itba.paw.Audition;

import java.util.List;
import java.util.Optional;

public interface AuditionService {

    Optional<Audition> getAuditionById(long id);

    Audition create(Audition.AuditionBuilder builder);

    List<Audition> getAll(int page);

    int getTotalPages();

    List<Audition> getBandAuditions(long userId, int page);

    int getTotalBandAuditionPages(long userId);

    void deleteAuditionById(long id);

    void editAuditionById(Audition.AuditionBuilder builder, long id);

    List<Audition> filter(AuditionFilter filter, int page);

    int getFilterTotalPages(AuditionFilter filter);
}
