package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Location;

import java.util.List;
import java.util.Optional;

public interface LocationDao {
    List<Location> getAll();
    Location getLocationByAuditionId(long auditionId);

    Location getLocationById(long location);
}
