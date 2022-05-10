package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.exceptions.AuditionNotFoundException;
import ar.edu.itba.paw.model.exceptions.AuditionNotOwnedException;
import ar.edu.itba.paw.AuditionFilter;
import ar.edu.itba.paw.model.exceptions.PageNotFoundException;
import ar.edu.itba.paw.persistence.*;
import ar.edu.itba.paw.model.exceptions.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
public class AuditionServiceImpl implements AuditionService {

    private final AuditionDao auditionDao;
    private final UserService userService;
    private static final Logger LOGGER = LoggerFactory.getLogger(AuditionServiceImpl.class);

    @Autowired
    public AuditionServiceImpl(final AuditionDao auditionDao,
                               final UserService userService) {
        this.auditionDao = auditionDao;
        this.userService = userService;
    }

    @Override
    public Optional<Audition> getAuditionById(long id) {
        checkAuditionId(id);
        return auditionDao.getAuditionById(id);
    }

    @Transactional
    @Override
    public Audition create(Audition.AuditionBuilder builder) {
        return auditionDao.create(builder);
    }

    @Transactional
    @Override
    public void editAuditionById(Audition.AuditionBuilder builder, long id) {
        checkAuditionId(id);
        checkPermissions(id);
        auditionDao.editAuditionById(builder, id);
    }

    @Override
    public List<Audition> getAll(int page) {
        int lastPage = getTotalPages();
        lastPage = lastPage == 0 ? 1 : lastPage;
        checkPage(page, lastPage);
        return auditionDao.getAll(page);
    }

    @Override
    public int getTotalPages() {
        return auditionDao.getTotalPages();
    }

    @Override
    public List<Audition> getBandAuditions(long userId, int page) {
        int lastPage = getTotalBandAuditionPages(userId);
        lastPage = lastPage == 0 ? 1 : lastPage;
        checkPage(page, lastPage);
        return auditionDao.getBandAuditions(userId, page);
    }

    @Override
    public int getTotalBandAuditionPages(long userId) {
        return auditionDao.getTotalBandAuditionPages(userId);
    }

    @Transactional
    @Override
    public void deleteAuditionById(long id) {
        checkAuditionId(id);
        checkPermissions(id);
        auditionDao.deleteAuditionById(id);
    }
    
    @Override
    public List<Audition> filter(AuditionFilter filter, int page) {
        int lastPage = getFilterTotalPages(filter);
        lastPage = lastPage == 0 ? 1 : lastPage;
        checkPage(page, lastPage);
        return auditionDao.filter(filter, page);
    }
    
    @Override   
    public int getFilterTotalPages(AuditionFilter filter) {

         return auditionDao.getTotalPages(filter);
    }
 
    private void checkPermissions(long id) {
        if(getAuditionById(id).orElseThrow(AuditionNotFoundException::new).getBandId() !=
                userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(UserNotFoundException::new).getId())
            throw new AuditionNotOwnedException();
    }

    private void checkPage(int page, int lastPage) {
        if(page <= 0)
            throw new IllegalArgumentException();
        if(page > lastPage)
            throw new PageNotFoundException();
    }

    private void checkAuditionId(long id) {
        if(id < 0)
            throw new IllegalArgumentException();
    }
}
