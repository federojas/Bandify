package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.exceptions.LocationNotFoundException;
import ar.edu.itba.paw.persistence.Location;
import ar.edu.itba.paw.persistence.LocationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LocationServiceImpl implements LocationService {

    private final LocationDao locationDao;

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
    public void validateLocations(List<String> locationsNames) {
        List<String> locations = locationDao.getAll().stream().map(Location::getName).collect(Collectors.toList());
        if(!locations.containsAll(locationsNames))
            throw new LocationNotFoundException();
    }

    @Override
    public Optional<Location> getLocationById(long location) {
        return locationDao.getLocationById(location);
    }

    @Override
    public Optional<Location> getLocationByName(String name) {
        return locationDao.getLocationByName(name);
    }
}
