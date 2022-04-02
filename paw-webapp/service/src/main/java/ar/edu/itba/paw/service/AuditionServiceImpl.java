package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Audition;
import ar.edu.itba.paw.persistence.AuditionDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class AuditionServiceImpl implements AuditionService {

    private final AuditionDao auditionDao;

    @Autowired
    public AuditionServiceImpl(AuditionDao auditionDao) {
        this.auditionDao = auditionDao;
    }

    @Override
    public Optional<Audition> getAuditionById(long ind) {
        return Optional.empty();
    }

    @Override
    public Audition create(String title, String description, String location, Date creationDate, String musicGenres, String lookingFor) {
        return null;
    }

    @Override
    public List<Audition> getAll(int page) {
        return null;
    }
}
