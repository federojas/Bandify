package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Audition;
import ar.edu.itba.paw.model.AuditionFilter;
import ar.edu.itba.paw.model.FilterOptions;

import java.util.List;
import java.util.Optional;

public interface AuditionDao {

    Optional<Audition> getAuditionById(long id);

    Audition create(Audition.AuditionBuilder builder);

    List<Audition> getAll(int page);

    int getTotalPages();

    int getTotalPages(AuditionFilter filter);

    List<Audition> getBandAuditions(long userId, int page);

    int getTotalBandAuditionPages(long userId);

    void deleteAuditionById(long id);

    List<Audition> filter(AuditionFilter auditionFilter, int page);
}
