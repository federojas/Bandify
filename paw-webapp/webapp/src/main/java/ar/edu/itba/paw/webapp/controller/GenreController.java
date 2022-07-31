package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.service.GenreService;
import ar.edu.itba.paw.webapp.dto.GenreDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;
import java.util.stream.Collectors;

@Path("genres")
@Component
public class GenreController {

    @Autowired
    private GenreService gs;

    @Context
    private UriInfo uriInfo;

    @GET
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response genres(@QueryParam("names") final List<String> names) {
        List<GenreDto> genres;
        if(names == null || names.isEmpty())
            genres = gs.getAll()
                    .stream().map(g -> GenreDto.fromGenre(uriInfo, g)).collect(Collectors.toList());
        else
            genres = gs.getGenresByNames(names)
                    .stream().map(g -> GenreDto.fromGenre(uriInfo, g)).collect(Collectors.toList());

        if(genres.isEmpty())
            return Response.noContent().build();

        Response.ResponseBuilder response = Response.ok(new GenericEntity<List<GenreDto>>(genres) {});
        return response.build();
    }
}
