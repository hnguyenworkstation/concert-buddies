package com.app.concertbud.concertbuddies.EventBuses;

import com.app.concertbud.concertbuddies.Networking.Responses.Entities.EventsEntity;

import java.util.List;

/**
 * Created by huongnguyen on 3/10/18.
 */

public class ConcertsNearbyBus {
    private List<EventsEntity> concerts;
    private boolean newLocation;

    // Making sure that the bus will be catched at the right class
    private String toClass;

    public  ConcertsNearbyBus(List<EventsEntity> concerts, boolean newLocation, String toClass) {
        this.concerts = concerts;
        this.newLocation = newLocation;
        this.toClass = toClass;
    }

    public List<EventsEntity> getConcerts() {
        return concerts;
    }

    public void setConcerts(List<EventsEntity> concerts) {
        this.concerts = concerts;
    }

    public boolean isNewLocation() {
        return newLocation;
    }

    public void setNewLocation(boolean newLocation) {
        this.newLocation = newLocation;
    }

    public String getToClass() {
        return toClass;
    }

    public void setToClass(String toClass) {
        this.toClass = toClass;
    }
}
