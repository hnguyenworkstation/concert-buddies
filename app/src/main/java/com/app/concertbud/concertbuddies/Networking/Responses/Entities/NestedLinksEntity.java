package com.app.concertbud.concertbuddies.Networking.Responses.Entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by huongnguyen on 3/14/18.
 */

public class NestedLinksEntity implements Serializable {

    @Expose
    @SerializedName("venues")
    private List<NestedLinksVenuesEntity> venues;
    @Expose
    @SerializedName("attractions")
    private List<NestedLinksAttractionsEntity> attractions;
    @Expose
    @SerializedName("self")
    private SelfEntity self;

    public List<NestedLinksVenuesEntity> getVenues() {
        return venues;
    }

    public void setVenues(List<NestedLinksVenuesEntity> venues) {
        this.venues = venues;
    }

    public List<NestedLinksAttractionsEntity> getAttractions() {
        return attractions;
    }

    public void setAttractions(List<NestedLinksAttractionsEntity> attractions) {
        this.attractions = attractions;
    }

    public SelfEntity getSelf() {
        return self;
    }

    public void setSelf(SelfEntity self) {
        this.self = self;
    }

}
