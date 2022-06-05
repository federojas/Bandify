package ar.edu.itba.paw.model;

public enum UrlType {
    TWITTER("twitter"),
    INSTAGRAM("instagram"),
    FACEBOOK("facebook"),
    YOUTUBE("youtube"),
    SPOTIFY("spotify"),
    SOUNDCLOUD("soundcloud");

    private final String type;

    UrlType(String type){
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
