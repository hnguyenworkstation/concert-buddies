package com.app.concertbud.concertbuddies.Networking.Responses.Entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PerformanceEntity {
    @Expose
    @SerializedName("billing")
    private String billing;
    @Expose
    @SerializedName("id")
    private int id;
    @Expose
    @SerializedName("billingIndex")
    private int billingindex;
    @Expose
    @SerializedName("displayName")
    private String displayname;
    @Expose
    @SerializedName("artist")
    private ArtistEntity artist;

    public String getBilling() {
        return billing;
    }

    public void setBilling(String billing) {
        this.billing = billing;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBillingindex() {
        return billingindex;
    }

    public void setBillingindex(int billingindex) {
        this.billingindex = billingindex;
    }

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public ArtistEntity getArtist() {
        return artist;
    }

    public void setArtist(ArtistEntity artist) {
        this.artist = artist;
    }
}
