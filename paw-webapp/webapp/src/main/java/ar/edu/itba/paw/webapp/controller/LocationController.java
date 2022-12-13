package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Location;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.exceptions.LocationNotFoundException;
import ar.edu.itba.paw.model.exceptions.UserNotFoundException;
import ar.edu.itba.paw.service.AuditionService;
import ar.edu.itba.paw.service.LocationService;
import ar.edu.itba.paw.service.UserService;
import ar.edu.itba.paw.webapp.dto.LocationDto;
import ar.edu.itba.paw.webapp.form.LocationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
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

    @Context
    private SecurityContext securityContext;

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
        return response.build();
    }

    @GET
    @Path("/{id}")
    @Produces("application/vnd.location.v1+json")
    public Response getById(@PathParam("id") final long id) {
        final Location location = locationService.getLocationById(id).orElseThrow(LocationNotFoundException::new);
        return Response.ok(LocationDto.fromLoc(uriInfo, location)).build();
    }

    @GET
    @Path("/user/{id}")
    public Response getUserLocation(@PathParam("id") final long id) {
        URI uri = uriInfo.getAbsolutePathBuilder()
                .replacePath("locations").queryParam("user",id).build();
        return Response.status(Response.Status.MOVED_PERMANENTLY).location(uri).build();
    }

    @PUT
    @Path("/user/{id}")
    public Response updateUserLocation(@Valid LocationForm form, @PathParam("id") final long id) {
        final User user = userService.findByEmail(
                securityContext.getUserPrincipal().getName()).orElseThrow(UserNotFoundException::new);
        checkOwnership(user, id);
        userService.updateUserLocation(form.getLocation(), user);
        return Response.ok().build();
    }

    private void checkOwnership(User user, long userId) {
        if (user.getId() != userId) {
            throw new ForbiddenException();
        }
    }
}


