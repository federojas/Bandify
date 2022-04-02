package ar.edu.itba.paw.model;

import java.util.Date;

public class Audition {
    private long id, bandId;
    private String title, description, location;
    private Date creationDate;
    private String musicGenres;
    private String lookingFor;

    public Audition(long id, long bandId, String title, String description, String location, Date creationDate, String musicGenres, String lookingFor) {
        this.id = id;
        this.bandId = bandId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.creationDate = creationDate;
        this.musicGenres = musicGenres;
        this.lookingFor = lookingFor;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getBandId() {
        return bandId;
    }

    public void setBandId(long bandId) {
        this.bandId = bandId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getMusicGenres() {
        return musicGenres;
    }

    public void setMusicGenres(String musicGenres) {
        this.musicGenres = musicGenres;
    }

    public String getLookingFor() {
        return lookingFor;
    }

    public void setLookingFor(String lookingFor) {
        this.lookingFor = lookingFor;
    }
}
