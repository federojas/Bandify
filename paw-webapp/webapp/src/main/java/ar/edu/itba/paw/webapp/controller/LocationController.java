package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Location;
import ar.edu.itba.paw.model.exceptions.LocationNotFoundException;
import ar.edu.itba.paw.model.exceptions.UserNotFoundException;
import ar.edu.itba.paw.service.AuditionService;
import ar.edu.itba.paw.service.LocationService;
import ar.edu.itba.paw.service.UserService;
import ar.edu.itba.paw.webapp.controller.utils.ConditionalCacheUtil;
import ar.edu.itba.paw.webapp.dto.LocationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Path("locations")
@Component
public class LocationController {
    @Autowired
    private LocationService locationService;

    @Autowired
    private UserService userService;

    @Autowired
    private AuditionService auditionService;

    @Context
    private UriInfo uriInfo;

    // TODO: paginacion?
    @GET
    @Produces("application/vnd.location-list.v1+json")
    public Response locations(@QueryParam("user") final Long userId,
                              @QueryParam("audition") final Long auditionId) {

        Set<LocationDto> locations;

        if(userId == null && auditionId == null) {
            locations = locationService.getAll()
                    .stream().map(l -> LocationDto.fromLoc(uriInfo, l)).collect(Collectors.toSet());
        }
        else {
            locations = new HashSet<>();
            if (userId != null) {
                Location loc = userService.getUserById(userId).orElseThrow(UserNotFoundException::new).getLocation();
                if(loc != null) locations.add(LocationDto.fromLoc(uriInfo,loc));
            }
            if (auditionId != null)
                locations.add(LocationDto.fromLoc(uriInfo,
                        auditionService.getAuditionById(auditionId).getLocation()));
        }

        if(locations.isEmpty())
            return Response.noContent().build();

        Response.ResponseBuilder response = Response.ok(new GenericEntity<Set<LocationDto>>(locations) {});
        ConditionalCacheUtil.setUnconditionalCache(response);
        return response.build();
    }

    @GET
    @Path("/{id}")
    @Produces("application/vnd.location.v1+json")
    public Response getById(@PathParam("id") final long id) {
        final Location location = locationService.getLocationById(id).orElseThrow(LocationNotFoundException::new);
        Response.ResponseBuilder response = Response.ok(LocationDto.fromLoc(uriInfo, location));
        ConditionalCacheUtil.setUnconditionalCache(response);
        return response.build();
    }

}


