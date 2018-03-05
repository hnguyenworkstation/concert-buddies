package com.app.concertbud.concertbuddies.Networking.Responses.Entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MetroareaEntity {
    @Expose
    @SerializedName("state")
    private StateEntity state;
    @Expose
    @SerializedName("id")
    private int id;
    @Expose
    @SerializedName("country")
    private CountryEntity country;
    @Expose
    @SerializedName("displayName")
    private String displayname;
    @Expose
    @SerializedName("uri")
    private String uri;

    public StateEntity getState() {
        return state;
    }

    public void setState(StateEntity state) {
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CountryEntity getCountry() {
        return country;
    }

    public void setCountry(CountryEntity country) {
        this.country = country;
    }

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
