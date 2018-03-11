package com.app.concertbud.concertbuddies.Networking.Responses;

import com.app.concertbud.concertbuddies.Networking.Responses.Entities.LocationEntity;
import com.app.concertbud.concertbuddies.Networking.Responses.Entities.PerformanceEntity;
import com.app.concertbud.concertbuddies.Networking.Responses.Entities.StartEntity;
import com.app.concertbud.concertbuddies.Networking.Responses.Entities.VenueEntity;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by huongnguyen on 3/5/18.
 */

public class ConcertResponse {
    @Expose
    @SerializedName("popularity")
    private double popularity;
    @Expose
    @SerializedName("status")
    private String status;
    @Expose
    @SerializedName("venue")
    private VenueEntity venue;
    @Expose
    @SerializedName("location")
    private LocationEntity location;
    @Expose
    @SerializedName("performance")
    private List<PerformanceEntity> performance;
    @Expose
    @SerializedName("start")
    private StartEntity start;
    @Expose
    @SerializedName("displayName")
    private String displayname;
    @Expose
    @SerializedName("uri")
    private String uri;
    @Expose
    @SerializedName("type")
    private String type;
    @Expose
    @SerializedName("id")
    private int id;

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public VenueEntity getVenue() {
        return venue;
    }

    public void setVenue(VenueEntity venue) {
        this.venue = venue;
    }

    public LocationEntity getLocation() {
        return location;
    }

    public void setLocation(LocationEntity location) {
        this.location = location;
    }

    public List<PerformanceEntity> getPerformance() {
        return performance;
    }

    public void setPerformance(List<PerformanceEntity> performance) {
        this.performance = performance;
    }

    public StartEntity getStart() {
        return start;
    }

    public void setStart(StartEntity start) {
        this.start = start;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
