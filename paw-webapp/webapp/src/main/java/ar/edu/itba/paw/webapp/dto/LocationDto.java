package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.model.Location;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Objects;

public class LocationDto {

    private long id;
    private String locName;
    private URI self;

    public static LocationDto fromLoc(final UriInfo uriInfo, final Location loc) {
        if(loc == null)
            return null;
        LocationDto dto = new LocationDto();
        dto.id = loc.getId();
        dto.locName = loc.getName();
        final UriBuilder userUriBuilder = uriInfo.getBaseUriBuilder()
                .path("locations").path(String.valueOf(loc.getId()));
        dto.self = userUriBuilder.build();

        return dto;
    }

    public URI getSelf() {
        return self;
    }

    public void setSelf(URI self) {
        this.self = self;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLocName() {
        return locName;
    }

    public void setLocName(String locName) {
        this.locName = locName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocationDto that = (LocationDto) o;
        return id == that.id && Objects.equals(locName, that.locName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, locName);
    }
}
