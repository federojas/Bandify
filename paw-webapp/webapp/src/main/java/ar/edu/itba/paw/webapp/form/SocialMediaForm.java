package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.model.MediaUrl;
import ar.edu.itba.paw.model.UrlType;
import org.hibernate.validator.constraints.URL;

import java.util.HashSet;
import java.util.Set;

public class SocialMediaForm {
    @URL(protocol = "https", host = "twitter.com")
    private String twitterUrl;
    @URL(protocol = "https", host = "www.instagram.com")
    private String instagramUrl;
    @URL(protocol = "https", host = "www.facebook.com")
    private String facebookUrl;
    @URL(protocol = "https", host = "www.youtube.com")
    private String youtubeUrl;
    @URL(protocol = "https", host = "open.spotify.com")
    private String spotifyUrl;
    @URL(protocol = "https", host = "soundcloud.com")
    private String soundcloudUrl;

    public Set<MediaUrl> getSocialMedia() {
        Set<MediaUrl> mediaUrls = new HashSet<>();
        if(twitterUrl != null && !twitterUrl.equals(""))
            mediaUrls.add(new MediaUrl(twitterUrl, UrlType.TWITTER));
        if(instagramUrl != null && !instagramUrl.equals(""))
            mediaUrls.add(new MediaUrl(instagramUrl, UrlType.INSTAGRAM));
        if(facebookUrl != null && !facebookUrl.equals(""))
            mediaUrls.add(new MediaUrl(facebookUrl, UrlType.FACEBOOK));
        if(youtubeUrl != null && !youtubeUrl.equals(""))
            mediaUrls.add(new MediaUrl(youtubeUrl, UrlType.YOUTUBE));
        if(spotifyUrl != null && !spotifyUrl.equals(""))
            mediaUrls.add(new MediaUrl(spotifyUrl, UrlType.SPOTIFY));
        if(soundcloudUrl != null && !soundcloudUrl.equals(""))
            mediaUrls.add(new MediaUrl(soundcloudUrl, UrlType.SOUNDCLOUD));
        return mediaUrls;
    }

    public String getTwitterUrl() {
        return twitterUrl;
    }

    public void setTwitterUrl(String twitterUrl) {
        this.twitterUrl = twitterUrl;
    }

    public String getInstagramUrl() {
        return instagramUrl;
    }

    public void setInstagramUrl(String instagramUrl) {
        this.instagramUrl = instagramUrl;
    }

    public String getFacebookUrl() {
        return facebookUrl;
    }

    public void setFacebookUrl(String facebookUrl) {
        this.facebookUrl = facebookUrl;
    }

    public String getYoutubeUrl() {
        return youtubeUrl;
    }

    public void setYoutubeUrl(String youtubeUrl) {
        this.youtubeUrl = youtubeUrl;
    }

    public String getSpotifyUrl() {
        return spotifyUrl;
    }

    public void setSpotifyUrl(String spotifyUrl) {
        this.spotifyUrl = spotifyUrl;
    }

    public String getSoundcloudUrl() {
        return soundcloudUrl;
    }

    public void setSoundcloudUrl(String soundcloudUrl) {
        this.soundcloudUrl = soundcloudUrl;
    }
}
