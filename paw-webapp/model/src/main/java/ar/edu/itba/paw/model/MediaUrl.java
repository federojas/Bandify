package ar.edu.itba.paw.model;

import java.net.URL;


public class MediaUrl {

    private URL url;
    private UrlType type;

    public MediaUrl(URL url, UrlType type) {
        this.url = url;
        this.type = type;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public UrlType getType() {
        return type;
    }

    public void setType(UrlType type) {
        this.type = type;
    }
}
