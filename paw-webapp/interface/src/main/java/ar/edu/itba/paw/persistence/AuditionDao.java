package ar.edu.itba.paw.persistence;

import java.util.List;
import java.util.Optional;

public interface AuditionDao {

    Optional<Audition> getAuditionById(long id);

    Audition create(Audition.AuditionBuilder builder);

    List<Audition> getAll(int page);

    int getTotalPages(String query);

    long getMaxAuditionId();

    List<Audition> search(int page, String query);

    List<Audition> getBandAuditions(long userId, int page);

    int getTotalBandAuditionPages(long userId);

    void deleteAuditionById(long id);

    void editAuditionById(Audition.AuditionBuilder builder, long id);
}
