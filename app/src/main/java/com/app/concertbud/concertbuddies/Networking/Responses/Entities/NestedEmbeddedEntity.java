package com.app.concertbud.concertbuddies.Networking.Responses.Entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by huongnguyen on 3/14/18.
 */

public class NestedEmbeddedEntity implements Serializable {
    @Expose
    @SerializedName("attractions")
    private List<NestedEmbeddedAttractionsEntity> attractions;
    @Expose
    @SerializedName("venues")
    private List<NestedEmbeddedVenuesEntity> venues;

    public List<NestedEmbeddedAttractionsEntity> getAttractions() {
        return attractions;
    }

    public void setAttractions(List<NestedEmbeddedAttractionsEntity> attractions) {
        this.attractions = attractions;
    }

    public List<NestedEmbeddedVenuesEntity> getVenues() {
        return venues;
    }

    public void setVenues(List<NestedEmbeddedVenuesEntity> venues) {
        this.venues = venues;
    }
}
