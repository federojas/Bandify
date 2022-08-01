package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Location;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface LocationService {
    List<Location> getAll();

    Optional<Location> getLocationByName(String name);

    Set<Location> getLocationsByNames(List<String> locations);

    Optional<Location> getLocationById(Long id);
}
