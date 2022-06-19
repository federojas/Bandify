package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.FilterOptions;
import ar.edu.itba.paw.model.Audition;
import ar.edu.itba.paw.model.User;

import java.util.List;
import java.util.Optional;

public interface AuditionService {

    Audition getAuditionById(long id);

    Audition create(Audition.AuditionBuilder builder);

    List<Audition> getAll(int page);

    int getTotalPages();

    List<Audition> getBandAuditions(User band, int page);

    void closeAuditionById(long id);

    int getTotalBandAuditionPages(User band);

    void editAuditionById(Audition.AuditionBuilder builder, long id);

    List<Audition> filter(FilterOptions filter, int page);

    int getFilterTotalPages(FilterOptions filter);
}
