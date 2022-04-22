package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Location;

import java.util.List;
import java.util.Optional;

public interface LocationService {
    List<Location> getAll();

    Optional<Location> getLocationByAuditionId(long auditionId);

    Optional<Location> getLocationById(long location);

    Optional<Location> getLocationByName(String name);
}
