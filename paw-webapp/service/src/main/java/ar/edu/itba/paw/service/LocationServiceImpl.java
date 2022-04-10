package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Location;
import ar.edu.itba.paw.persistence.LocationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocationServiceImpl implements LocationService {

    LocationDao locationDao;

    @Autowired
    public LocationServiceImpl(LocationDao locationDao) {
        this.locationDao = locationDao;
    }

    @Override
    public List<Location> getAll() {
        return locationDao.getAll();
    }

    @Override
    public Optional<Location> getLocationByAuditionId(long auditionId) {
        return locationDao.getLocationByAuditionId(auditionId);
    }

    @Override
    public Optional<Location> getLocation(long location) {
        return locationDao.getLocationById(location);
    }
}
