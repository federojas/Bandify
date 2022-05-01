package ar.edu.itba.paw.persistence;

public class Location {
    private long id;
    private String name;

    protected Location(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}