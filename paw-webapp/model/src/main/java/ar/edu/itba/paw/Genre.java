package ar.edu.itba.paw;

import javax.persistence.*;
import java.util.Objects;
@Entity
@Table(name="genres")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "genres_id_seq")
    @SequenceGenerator(sequenceName = "genres_id_seq",name="genres_id_seq")
    private long genreId;

    @Column(name = "genre", length = 50, nullable = false, unique = true)
    private String genreName;

    Genre() {
        //Hibernate
    }
    public Genre(String genreName,long genreId ) {
        this.genreName=genreName;
        this.genreId=genreId;
    }

    public long getId() {
        return genreId;
    }

    public String getName() {
        return genreName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genre genre = (Genre) o;
        return getId() == genre.getId() && Objects.equals(getName(), genre.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(genreId, genreName);
    }
}
