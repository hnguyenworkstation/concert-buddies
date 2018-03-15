package com.app.concertbud.concertbuddies.Networking.Responses.Entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by huongnguyen on 3/14/18.
 */


public class EmbeddedEntity {
    @Expose
    @SerializedName("events")
    private List<EventsEntity> events;

    public List<EventsEntity> getEvents() {
        return events;
    }

    public void setEvents(List<EventsEntity> events) {
        this.events = events;
    }
}