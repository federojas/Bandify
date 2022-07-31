package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.exceptions.UserNotFoundException;
import ar.edu.itba.paw.service.AuditionService;
import ar.edu.itba.paw.service.LocationService;
import ar.edu.itba.paw.service.UserService;
import ar.edu.itba.paw.webapp.dto.LocationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response locations(@QueryParam("user") final Long userId,
                              @QueryParam("audition") final Long auditionId) {

        Set<LocationDto> locations;

        if(userId == null && auditionId == null) {
            locations = locationService.getAll()
                    .stream().map(l -> LocationDto.fromLoc(uriInfo, l)).collect(Collectors.toSet());
        }
        else {
            locations = new HashSet<>();
            if (userId != null)
                locations.add(LocationDto.fromLoc(uriInfo,
                        userService.getUserById(userId)
                                .orElseThrow(UserNotFoundException::new).getLocation()));
            if (auditionId != null)
                locations.add(LocationDto.fromLoc(uriInfo,
                        auditionService.getAuditionById(auditionId).getLocation()));

        }

        if(locations.isEmpty())
            return Response.noContent().build();

        Response.ResponseBuilder response = Response.ok(new GenericEntity<Set<LocationDto>>(locations) {});
        return response.build();
    }
}


