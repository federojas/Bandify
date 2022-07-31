package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.model.Location;

import javax.ws.rs.core.UriInfo;

public class LocationDto {

    private long id;
    private String locName;

    public static LocationDto fromLoc(final UriInfo uriInfo, final Location loc) {
        if(loc == null)
            return null;
        LocationDto dto = new LocationDto();
        dto.id = loc.getId();
        dto.locName = loc.getName();

        return dto;
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
}
