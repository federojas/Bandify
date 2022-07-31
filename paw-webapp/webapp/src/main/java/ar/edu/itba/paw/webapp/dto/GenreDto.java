package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.model.Genre;
import javax.ws.rs.core.UriInfo;

public class GenreDto {

    private long id;
    private String genreName;

    public static GenreDto fromGenre(final UriInfo uriInfo, final Genre genre) {
        if(genre == null)
            return null;
        GenreDto dto = new GenreDto();
        dto.id = genre.getId();
        dto.genreName = genre.getName();

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
}
