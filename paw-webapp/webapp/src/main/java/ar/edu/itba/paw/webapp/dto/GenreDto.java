package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.model.Genre;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.util.Objects;
import java.net.URI;


public class GenreDto {

    private long id;
    private String genreName;

    private URI self;

    public static GenreDto fromGenre(final UriInfo uriInfo, final Genre genre) {
        if(genre == null)
            return null;
        GenreDto dto = new GenreDto();
        dto.id = genre.getId();
        dto.genreName = genre.getName();
        final UriBuilder userUriBuilder = uriInfo.getAbsolutePathBuilder()
                .replacePath("genres").path(String.valueOf(genre.getId()));
        dto.self = userUriBuilder.build();

        return dto;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    public URI getSelf() {
        return self;
    }

    public void setSelf(URI self) {
        this.self = self;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GenreDto genreDto = (GenreDto) o;
        return id == genreDto.id && Objects.equals(genreName, genreDto.genreName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, genreName);
    }
}
