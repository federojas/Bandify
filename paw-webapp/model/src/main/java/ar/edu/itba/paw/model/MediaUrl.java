package ar.edu.itba.paw.model;

public class MediaUrl {
    private String Url;
    private UrlType type;

    public MediaUrl(String url, UrlType type) {
        Url = url;
        this.type = type;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public UrlType getType() {
        return type;
    }

    public void setType(UrlType type) {
        this.type = type;
    }
}
