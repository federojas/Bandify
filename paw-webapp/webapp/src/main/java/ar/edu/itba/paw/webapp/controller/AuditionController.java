package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.ApplicationState;
import ar.edu.itba.paw.model.Audition;
import ar.edu.itba.paw.model.FilterOptions;
import ar.edu.itba.paw.model.exceptions.LocationNotFoundException;
import ar.edu.itba.paw.model.exceptions.UserNotFoundException;
import ar.edu.itba.paw.service.*;
import ar.edu.itba.paw.webapp.dto.ApplicationDto;
import ar.edu.itba.paw.webapp.dto.AuditionDto;
import ar.edu.itba.paw.webapp.form.AuditionForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Path("auditions")
@Component
public class AuditionController {

    @Autowired
    private AuditionService auditionService;

    @Autowired
    private UserService userService;

    @Context
    private UriInfo uriInfo;

    @Autowired
    private RoleService roleService;

    @Autowired
    private GenreService genreService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private ApplicationService applicationService;

    // TODO: Obtener usuario logueado, por ahora esta hardcodeado el ID
    @POST
    @Consumes(value = {MediaType.APPLICATION_JSON, })
    public Response createAudition(@Valid AuditionForm form) {
        Audition audition = auditionService.create(form.toBuilder(userService.getUserById(1)
                        .orElseThrow(UserNotFoundException::new))
                .lookingFor(roleService.getRolesByNames(form.getLookingFor()))
                .musicGenres(genreService.getGenresByNames(form.getMusicGenres()))
                .location(locationService.getLocationByName(form.getLocation())
                        .orElseThrow(LocationNotFoundException::new)));
        final URI uri = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(audition.getId())).build();
        return Response.created(uri).build();
    }

    @GET
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response getAuditions(@QueryParam("page") @DefaultValue("1") final int page,
                                 @QueryParam("query") @DefaultValue("") final String query,
                                 @QueryParam("genre") final List<String> genres,
                                 @QueryParam("role") final List<String> roles,
                                 @QueryParam("location")  final List<String> locations) {

        FilterOptions filter = new FilterOptions.FilterOptionsBuilder().
                withGenres(genres)
                .withRoles(roles)
                .withLocations(locations)
                .withTitle(query).build();
        List<AuditionDto> auditionDtos = auditionService.filter(filter,page).stream()
                .map(audition -> AuditionDto.fromAudition(uriInfo,audition))
                .collect(Collectors.toList());

        if(auditionDtos.isEmpty())
            return Response.noContent().build();
        Response.ResponseBuilder responseBuilder = Response.ok(new GenericEntity<List<AuditionDto>>(auditionDtos){});
        getResponsePaginationLinks(responseBuilder, page, filter);
        return responseBuilder.build();

    }

    private void getResponsePaginationLinks(Response.ResponseBuilder response, int currentPage, FilterOptions filter) {
        int lastPage = auditionService.getFilterTotalPages(filter);
        if(currentPage != 1)
            response.link(uriInfo.getAbsolutePathBuilder().queryParam("page", currentPage - 1).build(), "prev");
        if(currentPage != lastPage)
            response.link(uriInfo.getAbsolutePathBuilder().queryParam("page", currentPage + 1).build(), "next");
        response.link(uriInfo.getAbsolutePathBuilder().queryParam("page", 1).build(), "first");
        response.link(uriInfo.getAbsolutePathBuilder().queryParam("page", lastPage).build(), "last");
    }

    // TODO: y si no esta presente? o esta closed?
    @GET
    @Path("/{id}")
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response getAuditionById(@PathParam("id") final long auditionId) {
        final Audition audition = auditionService.getAuditionById(auditionId);
        return Response.ok(AuditionDto.fromAudition(uriInfo, audition)).build();
    }

    // TODO: codigo repetido al final
    @GET
    @Path("/{id}/applications")
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response getAuditionsApplications(@PathParam("id") final long auditionId,
                                             @QueryParam("page") @DefaultValue("1") final int page,
                                             @QueryParam("state") @DefaultValue("PENDING") final String state) {
        List<ApplicationDto> applicationDtos =
                applicationService.getAuditionApplicationsByState(auditionId, ApplicationState.valueOf(state), page)
                        .stream().map(application -> ApplicationDto.
                                fromApplication(uriInfo,application)).collect(Collectors.toList());
        if(applicationDtos.isEmpty())
            return Response.noContent().build();
        Response.ResponseBuilder responseBuilder = Response.ok(new GenericEntity<List<ApplicationDto>>(applicationDtos){});
        int lastPage = applicationService.getTotalAuditionApplicationByStatePages(auditionId,ApplicationState.valueOf(state));
        if(page != 1)
            responseBuilder.link(uriInfo.getAbsolutePathBuilder().queryParam("page", page - 1).build(), "prev");
        if(page != lastPage)
            responseBuilder.link(uriInfo.getAbsolutePathBuilder().queryParam("page", page + 1).build(), "next");
        responseBuilder.link(uriInfo.getAbsolutePathBuilder().queryParam("page", 1).build(), "first");
        responseBuilder.link(uriInfo.getAbsolutePathBuilder().queryParam("page", lastPage).build(), "last");
        return responseBuilder.build();
    }

}
