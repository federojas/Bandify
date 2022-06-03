package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Location;
import ar.edu.itba.paw.model.Role;
import ar.edu.itba.paw.model.exceptions.LocationNotFoundException;
import ar.edu.itba.paw.model.exceptions.RoleNotFoundException;
import ar.edu.itba.paw.persistence.LocationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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
    public Optional<Location> getLocationByName(String name) {
        return locationDao.getLocationByName(name);
    }

    @Override
    public Set<Location> getLocationsByNames(List<String> locationNames) {
        if(locationNames == null)
            return new HashSet<>();
        List<String> locations = locationDao.getAll().stream().map(Location::getName).collect(Collectors.toList());

        if(!locations.containsAll(locationNames))
            throw new LocationNotFoundException();

        return locationDao.getLocationsByNames(locationNames);
    }
}
