package com.app.concertbud.concertbuddies.Networking.Responses.Entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VenueEntity {
    @Expose
    @SerializedName("metroArea")
    private MetroAreaEntity metroarea;
    @Expose
    @SerializedName("lat")
    private double lat;
    @Expose
    @SerializedName("lng")
    private double lng;
    @Expose
    @SerializedName("uri")
    private String uri;
    @Expose
    @SerializedName("displayName")
    private String displayname;
    @Expose
    @SerializedName("id")
    private int id;

    public MetroAreaEntity getMetroarea() {
        return metroarea;
    }

    public void setMetroarea(MetroAreaEntity metroarea) {
        this.metroarea = metroarea;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
