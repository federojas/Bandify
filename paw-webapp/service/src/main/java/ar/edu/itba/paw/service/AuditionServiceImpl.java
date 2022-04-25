package ar.edu.itba.paw.service;

import ar.edu.itba.paw.persistence.Audition;
import ar.edu.itba.paw.persistence.AuditionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Audition create(Audition.AuditionBuilder builder) {
        return auditionDao.create(builder);
    }

    @Override
    public List<Audition> getAll(int page) {
        return auditionDao.getAll(page);
    }

    @Override
    public int getTotalPages(String query) {
        return auditionDao.getTotalPages(query);
    }

    @Override
    public long getMaxAuditionId() {
        return auditionDao.getMaxAuditionId();
    }

    @Override
    public List<Audition> search(int page, String query) {
        return auditionDao.search(page, query);
    }

    @Override
    public List<Audition> getBandAuditions(long userId) {
        return auditionDao.getBandAuditions(userId);
    }

}
