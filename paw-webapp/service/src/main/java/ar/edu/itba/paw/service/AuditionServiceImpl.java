package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Audition;
import ar.edu.itba.paw.persistence.AuditionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AuditionServiceImpl implements AuditionService {

    private final AuditionDao auditionDao;

    @Autowired
    public AuditionServiceImpl(AuditionDao auditionDao) {
        this.auditionDao = auditionDao;
    }

    @Override
    public Optional<Audition> getAuditionById(long id) {
        return auditionDao.getAuditionById(id);
    }

    @Override
    public Audition create(String title, String description, String location, Date creationDate, List<String> musicGenres, List<String> lookingFor) {
        return auditionDao.create(title,description,location,creationDate,musicGenres,lookingFor);
    }

    @Override
    public List<Audition> getAll(int page) {
        return auditionDao.getAll(page);
    }
}
