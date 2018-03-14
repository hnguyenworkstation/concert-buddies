package com.app.concertbud.concertbuddies.Networking.Responses.Entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hungnguyen on 3/11/18.
 */
public class CityEntity {
    @Expose
    @SerializedName("state")
    private StateEntity state;
    @Expose
    @SerializedName("lng")
    private double lng;
    @Expose
    @SerializedName("country")
    private CountryEntity country;
    @Expose
    @SerializedName("lat")
    private double lat;
    @Expose
    @SerializedName("displayName")
    private String displayname;

    public StateEntity getState() {
        return state;
    }

    public void setState(StateEntity state) {
        this.state = state;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public CountryEntity getCountry() {
        return country;
    }

    public void setCountry(CountryEntity country) {
        this.country = country;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }
}
