package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.service.LocationService;
import ar.edu.itba.paw.webapp.dto.GenreDto;
import ar.edu.itba.paw.webapp.dto.LocationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.*;
import java.util.List;
import java.util.stream.Collectors;

@Path("locations")
@Component
public class LocationController {
    @Autowired
    private LocationService ls;

    @Context
    private UriInfo uriInfo;

    @GET
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response locations(@QueryParam("names") final List<String> names) {
        List<LocationDto> locations;
        if(names == null || names.isEmpty())
            locations = ls.getAll()
                    .stream().map(l -> LocationDto.fromLoc(uriInfo, l)).collect(Collectors.toList());
        else
            locations = ls.getLocationsByNames(names)
                    .stream().map(l -> LocationDto.fromLoc(uriInfo, l)).collect(Collectors.toList());

        if(locations.isEmpty())
            return Response.noContent().build();

        Response.ResponseBuilder response = Response.ok(new GenericEntity<List<LocationDto>>(locations) {});
        return response.build();
    }
}


