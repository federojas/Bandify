package ar.edu.itba.paw;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "locations")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "locations_locationid_seq")
    @SequenceGenerator(sequenceName = "locations_locationid_seq", name = "locations_locationid_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "location", length = 100, nullable = false, unique = true)
    private String name;

    /* package */ Location() {

    }

    public long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return getId() == location.getId() && Objects.equals(getName(), location.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }

    public String getName() {
        return name;
    }
}