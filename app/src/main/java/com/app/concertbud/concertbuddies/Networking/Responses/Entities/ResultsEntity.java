package com.app.concertbud.concertbuddies.Networking.Responses.Entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResultsEntity {
    @Expose
    @SerializedName("event")
    private List<EventEntity> event;
    @Expose
    @SerializedName("location")
    private List<LocationResultEntity> locationResults;

    public List<EventEntity> getEvent() {
        return event;
    }

    public void setEvent(List<EventEntity> event) {
        this.event = event;
    }

    public List<LocationResultEntity> getLocationResults() {
        return locationResults;
    }

    public void setLocationResults(List<LocationResultEntity> locationResults) {
        this.locationResults = locationResults;
    }
}
