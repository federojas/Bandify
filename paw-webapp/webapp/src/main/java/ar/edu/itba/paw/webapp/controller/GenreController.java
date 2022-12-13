package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Genre;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.exceptions.GenreNotFoundException;
import ar.edu.itba.paw.model.exceptions.UserNotFoundException;
import ar.edu.itba.paw.service.AuditionService;
import ar.edu.itba.paw.service.GenreService;
import ar.edu.itba.paw.service.UserService;
import ar.edu.itba.paw.webapp.dto.GenreDto;
import ar.edu.itba.paw.webapp.form.UserGenresForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Path("genres")
@Component
public class GenreController {

    @Autowired
    private GenreService genreService;

    @Autowired
    private UserService userService;

    @Autowired
    private AuditionService auditionService;

    @Context
    private UriInfo uriInfo;

    @Context
    private SecurityContext securityContext;

    // TODO: paginacion?
    // TODO: si te mandan userId y auditionId aca es como que hace un OR, no debería ser un AND?
    // lo mismo pasa en roles y locations.
    @GET
    @Produces("application/vnd.genre-list.v1+json")
    public Response genres(@QueryParam("user") final Long userId,
                           @QueryParam("audition") final Long auditionId) {
        Set<GenreDto> genres;

        if(userId == null && auditionId == null) {
            genres = genreService.getAll()
                    .stream().map(g -> GenreDto.fromGenre(uriInfo, g)).collect(Collectors.toSet());
        }
        else {
            genres = new HashSet<>();
            if (userId != null)
                genres.addAll(userService.getUserById(userId).orElseThrow(UserNotFoundException::new)
                        .getUserGenres().stream().map(g -> GenreDto.fromGenre(uriInfo, g))
                        .collect(Collectors.toSet()));
            if (auditionId != null)
                genres.addAll(auditionService.getAuditionById(auditionId).getMusicGenres()
                        .stream().map(g -> GenreDto.fromGenre(uriInfo, g))
                        .collect(Collectors.toSet()));
        }

        if(genres.isEmpty())
            return Response.noContent().build();

        Response.ResponseBuilder response = Response.ok(new GenericEntity<Set<GenreDto>>(genres) {});
        return response.build();

    }

    @GET
    @Path("/{id}")
    @Produces("application/vnd.genre.v1+json")
    public Response getById(@PathParam("id") final long id) {
        final Genre genre = genreService.getGenreById(id).orElseThrow(GenreNotFoundException::new);
        return Response.ok(GenreDto.fromGenre(uriInfo, genre)).build();
    }

    @GET
    @Path("/user/{id}")
    public Response getUserGenres(@PathParam("id") final long id) {
        URI uri = uriInfo.getAbsolutePathBuilder()
                .replacePath("genres").queryParam("user",id).build();
        return Response.status(Response.Status.MOVED_PERMANENTLY).location(uri).build();
    }

    @PUT
    @Path("/user/{id}")
    public Response updateUserGenres(@Valid UserGenresForm form, @PathParam("id") final long id) {
        final User user = userService.findByEmail(
                securityContext.getUserPrincipal().getName()).orElseThrow(UserNotFoundException::new);
        checkOwnership(user, id);
        userService.updateUserGenres(form.getMusicGenres(), user);
        return Response.ok().build();
    }

    private void checkOwnership(User user, long userId) {
        if (user.getId() != userId) {
            throw new ForbiddenException();
        }
    }

}
