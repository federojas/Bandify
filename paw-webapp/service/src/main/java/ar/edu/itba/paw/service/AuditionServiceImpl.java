package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.exceptions.AuditionGoneException;
import ar.edu.itba.paw.model.exceptions.AuditionNotFoundException;
import ar.edu.itba.paw.model.exceptions.AuditionNotOwnedException;
import ar.edu.itba.paw.model.exceptions.PageNotFoundException;
import ar.edu.itba.paw.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
public class AuditionServiceImpl implements AuditionService {
    
    @Autowired
    private AuditionDao auditionDao;
    @Autowired
    private AuthFacadeService authFacadeService;

    private static final int MAX_AUD_GENRES = 5;
    private static final int MAX_AUD_ROLES = 5;

    private static final Logger LOGGER = LoggerFactory.getLogger(AuditionServiceImpl.class);


    @Override
    public Audition getAuditionById(long id) {
        checkAuditionId(id);
        Optional<Audition> audition = auditionDao.getAuditionById(id);
        if(!audition.isPresent())
            throw new AuditionNotFoundException();
        if(!audition.get().getIsOpen())
            throw new AuditionGoneException();
        return audition.get();
    }

    @Transactional
    @Override
    public Audition create(Audition.AuditionBuilder builder) {
        if(builder.getLookingFor().size() > MAX_AUD_ROLES  || builder.getMusicGenres().size() > MAX_AUD_GENRES)
            throw new IllegalArgumentException("Invalid amount of roles or genres");
        return auditionDao.create(builder);
    }

    @Transactional
    @Override
    public Audition editAuditionById(Audition.AuditionBuilder builder, long id) {
        checkAuditionId(id);
        checkPermissions(id);
        Audition audition = getAuditionById(id);
        if(builder.getLookingFor().size() > MAX_AUD_ROLES  || builder.getMusicGenres().size() > MAX_AUD_GENRES)
            throw new IllegalArgumentException("Invalid amount of roles or genres");
        audition.edit(builder);
        return audition;
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
    public List<Audition> getBandAuditions(User band, int page) {
        if(!band.isBand())
            throw new AuditionNotFoundException();
        int lastPage = getTotalBandAuditionPages(band);
        lastPage = lastPage == 0 ? 1 : lastPage;
        checkPage(page, lastPage);
        return auditionDao.getBandAuditions(band.getId(), page);
    }

    @Override
    public int getTotalBandAuditionPages(User band) {
        if(!band.isBand())
            throw new AuditionNotFoundException();
        return auditionDao.getTotalBandAuditionPages(band.getId());
    }

    @Transactional
    @Override
    public Audition closeAuditionById(long id) {
        checkAuditionId(id);
        checkPermissions(id);
        LOGGER.debug("Audition {} will be closed",id);
        Audition audition = getAuditionById(id);
        if(audition.getIsOpen())
            audition.setIsOpen(false);
        return audition;
    }

    @Override
    public List<Audition> filter(FilterOptions filter, int page) {
        int lastPage = getFilterTotalPages(filter);
        lastPage = lastPage == 0 ? 1 : lastPage;
        checkPage(page, lastPage);
        return auditionDao.filter(filter, page);
    }
    
    @Override   
    public int getFilterTotalPages(FilterOptions filter) {
        return auditionDao.getTotalPages(filter);
    }
 
    private void checkPermissions(long id) {
        if(!Objects.equals(getAuditionById(id).getBand().getId(), authFacadeService.getCurrentUser().getId())) {
            LOGGER.warn("The authenticated user is not the audition owner");
            throw new AuditionNotOwnedException();
        }
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
