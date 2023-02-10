package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.Application;
import ar.edu.itba.paw.model.exceptions.ApplicationNotFoundException;
import ar.edu.itba.paw.model.exceptions.InvalidApplicationStateException;
import ar.edu.itba.paw.model.exceptions.NotABandException;
import ar.edu.itba.paw.model.exceptions.UserNotFoundException;
import ar.edu.itba.paw.service.*;
import ar.edu.itba.paw.webapp.controller.utils.PaginationLinkBuilder;
import ar.edu.itba.paw.webapp.dto.ApplicationDto;
import ar.edu.itba.paw.webapp.dto.AuditionDto;
import ar.edu.itba.paw.webapp.form.ApplicationForm;
import ar.edu.itba.paw.webapp.form.AuditionForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
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

    @Context
    private SecurityContext securityContext;

    @POST
    @Consumes("application/vnd.audition.v1+json")
    public Response createAudition(@Valid AuditionForm auditionForm) {
        Audition audition = auditionService.create(auditionForm.toBuilder(userService.findByEmail(
                               securityContext.getUserPrincipal().getName()
                        ).orElseThrow(UserNotFoundException::new))
                .lookingFor(roleService.getRolesByNames(auditionForm.getLookingFor()))
                .musicGenres(genreService.getGenresByNames(auditionForm.getMusicGenres()))
                .location(locationService.getLocationByName(auditionForm.getLocation())));
        final URI uri = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(audition.getId())).build();
        return Response.created(uri).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateAudition(@Valid AuditionForm auditionForm,
                                   @PathParam("id") final long auditionId) {
        final User user = userService.findByEmail(securityContext.getUserPrincipal().getName()).orElseThrow(UserNotFoundException::new);
        Audition.AuditionBuilder builder = new Audition.AuditionBuilder(auditionForm.getTitle(),
                auditionForm.getDescription(), user, LocalDateTime.now());
        builder.musicGenres(genreService.getGenresByNames(auditionForm.getMusicGenres()))
               .lookingFor(roleService.getRolesByNames(auditionForm.getLookingFor()))
               .location(locationService.getLocationByName(auditionForm.getLocation()));

        auditionService.editAuditionById(builder,auditionId);
        return Response.ok().build();
    }

    @DELETE
    @Produces("application/vnd.audition.v1+json")
    @Path("/{id}")
    public Response deleteAudition(@PathParam("id") final long auditionId) {
        applicationService.closeApplicationsByAuditionId(auditionId);
        auditionService.closeAuditionById(auditionId);
        return Response.noContent().build();
    }

    @GET
    @Produces("application/vnd.audition-list.v1+json")
    public Response getAuditions(@QueryParam("page") @DefaultValue("1") final int page,
                                 @QueryParam("query") @DefaultValue("") final String query,
                                 @QueryParam("genre") final List<String> genres,
                                 @QueryParam("role") final List<String> roles,
                                 @QueryParam("location")  final List<String> locations,
                                 @QueryParam("order") @DefaultValue("DESC") final String order,
                                 @QueryParam("bandId") final Long bandId) {
        List<AuditionDto> auditionDtos;
        int lastPage;
        if(bandId != null) {
            User band = userService.getBandById(bandId);
            auditionDtos = auditionService.getBandAuditions(band, page).stream()
                    .map(audition -> AuditionDto.fromAudition(uriInfo,audition))
                    .collect(Collectors.toList());
            lastPage = auditionService.getTotalBandAuditionPages(band);
        } else {
            FilterOptions filter = new FilterOptions.FilterOptionsBuilder().
                    withGenres(genres)
                    .withRoles(roles)
                    .withLocations(locations)
                    .withTitle(query)
                    .withOrder(order).build();
            auditionDtos = auditionService.filter(filter,page).stream()
                    .map(audition -> AuditionDto.fromAudition(uriInfo,audition))
                    .collect(Collectors.toList());
            lastPage = auditionService.getFilterTotalPages(filter);
        }

        if(auditionDtos.isEmpty())
            return Response.noContent().build();

        Response.ResponseBuilder responseBuilder = Response.ok(new GenericEntity<List<AuditionDto>>(auditionDtos){});
        PaginationLinkBuilder.getResponsePaginationLinks(responseBuilder, uriInfo, page, lastPage);
        return responseBuilder.build();

    }

    @GET
    @Path("/{id}")
    @Produces("application/vnd.audition.v1+json")
    public Response getAuditionById(@PathParam("id") final long auditionId) {
        final Audition audition = auditionService.getAuditionById(auditionId);
        return Response.ok(AuditionDto.fromAudition(uriInfo, audition)).build();
    }

    @GET
    @Path("/{id}/applications")
    @Produces("application/vnd.application-list.v1+json")
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
    @Produces("application/vnd.application.v1+json")
    public Response getApplication(@PathParam("auditionId") final long auditionId,
                                   @PathParam("id") final long applicationId) {
        final Application application = applicationService.getApplicationById(auditionId,applicationId)
                .orElseThrow(ApplicationNotFoundException::new);
        return Response.ok(ApplicationDto.fromApplication(uriInfo, application)).build();
    }


    @POST
    @Path("/{auditionId}/applications")
    @Consumes("application/vnd.application.v1+json")
    public Response createApplication(@PathParam("auditionId") final long auditionId,
                                      @Valid ApplicationForm applicationForm) {
        Application application = applicationService.apply(
                auditionId,
                userService.findByEmail(
                        securityContext.getUserPrincipal().getName())
                        .orElseThrow(UserNotFoundException::new),
                applicationForm.getMessage()
        );
        final URI uri = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(application.getId())).build();
        return Response.created(uri).build();
    }

    @PUT
    @Path("/{auditionId}/applications/{applicationId}")
    @Consumes("application/vnd.application.v1+json")
    public Response changeApplicationStatus(@PathParam("auditionId") final long auditionId,
                                            @PathParam("applicationId") final long applicationId,
                                            @QueryParam("state") final String state) {
        applicationService.changeState(auditionId, applicationId, state);
        return Response.ok().build();
    }
}
