package ar.edu.itba.paw.persistence;

import java.util.List;
import java.util.Optional;

public interface LocationDao {
    List<Location> getAll();
    Optional<Location> getLocationByName(String name);
}
