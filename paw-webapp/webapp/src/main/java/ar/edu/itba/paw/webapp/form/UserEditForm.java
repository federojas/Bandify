package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.model.MediaUrl;
import ar.edu.itba.paw.model.SocialMedia;
import ar.edu.itba.paw.model.UrlType;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.webapp.form.constraints.annotations.ImageType;
import ar.edu.itba.paw.webapp.form.constraints.annotations.MaxFileSize;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import javax.validation.constraints.Size;
import java.util.*;

public abstract class UserEditForm {

    @NotBlank
    @Size(max = 50)
    private String name;

    @Size(max = 500)
    private String description;

    @Size(max = 15)
    private List<String> musicGenres;

    @Size(max = 15)
    private List<String> lookingFor;

    @NotEmpty
    @Size(max = 100)
    private String location;

    @MaxFileSize(8)
    @ImageType(types = {"image/png", "image/jpeg"})
    private CommonsMultipartFile profileImage;

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

    private boolean isBand;

    public CommonsMultipartFile getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(CommonsMultipartFile profileImage) {
        this.profileImage = profileImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getMusicGenres() {
        return musicGenres;
    }

    public void setMusicGenres(List<String> musicGenres) {
        this.musicGenres = musicGenres;
    }

    public List<String> getLookingFor() {
        return lookingFor;
    }

    public void setLookingFor(List<String> lookingFor) {
        this.lookingFor = lookingFor;
    }

    public boolean isBand() {
        return isBand;
    }

    public void setBand(boolean band) {
        isBand = band;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void initialize(User user, List<String> musicGenres, List<String> bandRoles, Set<SocialMedia> socialMediaSet, String location) {
        this.setMusicGenres(musicGenres);
        this.setLookingFor(bandRoles);
        this.setDescription(user.getDescription());
        this.setName(user.getName());
        this.setBand(user.isBand());
        this.setLocation(location);
        Optional<SocialMedia> twitter = socialMediaSet.stream().filter(socialMedia -> socialMedia.getType().equals(UrlType.TWITTER)).findFirst();
        twitter.ifPresent(socialMedia -> this.setTwitterUrl(socialMedia.getUrl()));

        Optional<SocialMedia> instagram = socialMediaSet.stream().filter(socialMedia -> socialMedia.getType().equals(UrlType.INSTAGRAM)).findFirst();
        instagram.ifPresent(socialMedia -> this.setInstagramUrl(socialMedia.getUrl()));

        Optional<SocialMedia> facebook = socialMediaSet.stream().filter(socialMedia -> socialMedia.getType().equals(UrlType.FACEBOOK)).findFirst();
        facebook.ifPresent(socialMedia -> this.setFacebookUrl(socialMedia.getUrl()));

        Optional<SocialMedia> youtube = socialMediaSet.stream().filter(socialMedia -> socialMedia.getType().equals(UrlType.YOUTUBE)).findFirst();
        youtube.ifPresent(socialMedia -> this.setYoutubeUrl(socialMedia.getUrl()));

        Optional<SocialMedia> spotify = socialMediaSet.stream().filter(socialMedia -> socialMedia.getType().equals(UrlType.SPOTIFY)).findFirst();
        spotify.ifPresent(socialMedia -> this.setSpotifyUrl(socialMedia.getUrl()));

        Optional<SocialMedia> soundcloud = socialMediaSet.stream().filter(socialMedia -> socialMedia.getType().equals(UrlType.SOUNDCLOUD)).findFirst();
        soundcloud.ifPresent(socialMedia -> this.setSoundcloudUrl(socialMedia.getUrl()));
    }

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
}
