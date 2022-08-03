package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Application;
import ar.edu.itba.paw.model.ApplicationState;
import ar.edu.itba.paw.model.Audition;
import ar.edu.itba.paw.model.FilterOptions;
import ar.edu.itba.paw.model.exceptions.ApplicationNotFoundException;
import ar.edu.itba.paw.model.exceptions.LocationNotFoundException;
import ar.edu.itba.paw.model.exceptions.UserNotFoundException;
import ar.edu.itba.paw.service.*;
import ar.edu.itba.paw.webapp.controller.utils.PaginationLinkBuilder;
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
    public Response createAudition(@Valid AuditionForm auditionForm) {
        Audition audition = auditionService.create(auditionForm.toBuilder(userService.getUserById(1)
                        .orElseThrow(UserNotFoundException::new))
                .lookingFor(roleService.getRolesByNames(auditionForm.getLookingFor()))
                .musicGenres(genreService.getGenresByNames(auditionForm.getMusicGenres()))
                .location(locationService.getLocationByName(auditionForm.getLocation())
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
        int lastPage = auditionService.getFilterTotalPages(filter);
        PaginationLinkBuilder.getResponsePaginationLinks(responseBuilder, uriInfo, page, lastPage);
        return responseBuilder.build();

    }

    // TODO: y si no esta presente? o esta closed?
    @GET
    @Path("/{id}")
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response getAuditionById(@PathParam("id") final long auditionId) {
        final Audition audition = auditionService.getAuditionById(auditionId);
        return Response.ok(AuditionDto.fromAudition(uriInfo, audition)).build();
    }


    // TODO: las aplicaciones que da son por defecto las pendientes
    // le podes pasar para que te de las del estado que quieras, podemos dejarlo asi
    // o que por defecto te de las que sean de cualquier estado.
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
        PaginationLinkBuilder.getResponsePaginationLinks(responseBuilder, uriInfo, page, lastPage);
        return responseBuilder.build();
    }

    @GET
    @Path("/{auditionId}/applications/{id}")
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response getApplication(@PathParam("auditionId") final long auditionId,
                                   @PathParam("id") final long applicationId) {
        final Application application = applicationService.getApplicationById(auditionId,applicationId)
                .orElseThrow(ApplicationNotFoundException::new);
        return Response.ok(ApplicationDto.fromApplication(uriInfo, application)).build();
    }

}
