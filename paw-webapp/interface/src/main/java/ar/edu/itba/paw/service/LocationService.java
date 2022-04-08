package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Location;

import java.util.List;
import java.util.Optional;

public interface LocationService {
    List<Location> getAll();

    Location getLocationByAuditionId(long auditionId);

    Location validateAndGetLocation(long location);
}
