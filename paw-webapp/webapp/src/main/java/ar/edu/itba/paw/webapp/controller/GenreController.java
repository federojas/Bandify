package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Genre;
import ar.edu.itba.paw.model.exceptions.GenreNotFoundException;
import ar.edu.itba.paw.model.exceptions.UserNotFoundException;
import ar.edu.itba.paw.service.AuditionService;
import ar.edu.itba.paw.service.GenreService;
import ar.edu.itba.paw.service.UserService;
import ar.edu.itba.paw.webapp.controller.utils.ConditionalCacheUtil;
import ar.edu.itba.paw.webapp.dto.GenreDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
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
        ConditionalCacheUtil.setUnconditionalCache(response);
        return response.build();

    }

    @GET
    @Path("/{id}")
    @Produces("application/vnd.genre.v1+json")
    public Response getById(@PathParam("id") final long id) {
        final Genre genre = genreService.getGenreById(id).orElseThrow(GenreNotFoundException::new);
        Response.ResponseBuilder response = Response.ok(GenreDto.fromGenre(uriInfo, genre));
        ConditionalCacheUtil.setUnconditionalCache(response);
        return response.build();
    }

}
