package ar.edu.itba.paw.service;

import ar.edu.itba.paw.Location;

import java.util.List;
import java.util.Optional;

public interface LocationService {
    List<Location> getAll();

    Optional<Location> getLocationByName(String name);

}
