package com.app.concertbud.concertbuddies.Networking.Responses.Entities;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by huongnguyen on 3/14/18.
 */

public class ExternallinksEntity {
    @Expose
    @SerializedName("homepage")
    private List<HomepageEntity> homepage;
    @Expose
    @SerializedName("musicbrainz")
    private List<MusicbrainzEntity> musicbrainz;
    @Expose
    @SerializedName("wiki")
    private List<WikiEntity> wiki;
    @Expose
    @SerializedName("facebook")
    private List<FacebookEntity> facebook;
    @Expose
    @SerializedName("lastfm")
    private List<LastfmEntity> lastfm;
    @Expose
    @SerializedName("twitter")
    private List<ExternalLinksTwitterEntity> twitter;
    @Expose
    @SerializedName("youtube")
    private List<YoutubeEntity> youtube;

    public List<HomepageEntity> getHomepage() {
        return homepage;
    }

    public void setHomepage(List<HomepageEntity> homepage) {
        this.homepage = homepage;
    }

    public List<MusicbrainzEntity> getMusicbrainz() {
        return musicbrainz;
    }

    public void setMusicbrainz(List<MusicbrainzEntity> musicbrainz) {
        this.musicbrainz = musicbrainz;
    }

    public List<WikiEntity> getWiki() {
        return wiki;
    }

    public void setWiki(List<WikiEntity> wiki) {
        this.wiki = wiki;
    }

    public List<FacebookEntity> getFacebook() {
        return facebook;
    }

    public void setFacebook(List<FacebookEntity> facebook) {
        this.facebook = facebook;
    }

    public List<LastfmEntity> getLastfm() {
        return lastfm;
    }

    public void setLastfm(List<LastfmEntity> lastfm) {
        this.lastfm = lastfm;
    }

    public List<ExternalLinksTwitterEntity> getTwitter() {
        return twitter;
    }

    public void setTwitter(List<ExternalLinksTwitterEntity> twitter) {
        this.twitter = twitter;
    }

    public List<YoutubeEntity> getYoutube() {
        return youtube;
    }

    public void setYoutube(List<YoutubeEntity> youtube) {
        this.youtube = youtube;
    }
}