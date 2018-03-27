package com.app.concertbud.concertbuddies.EventBuses;

import com.app.concertbud.concertbuddies.Networking.Responses.Entities.EventsEntity;

import java.util.List;

/**
 * Created by huongnguyen on 3/10/18.
 */

public class ConcertsNearbyBus {
    private List<EventsEntity> concerts;
    private boolean newLocation;
    public  ConcertsNearbyBus(List<EventsEntity> concerts, boolean newLocation) {
        this.concerts = concerts;
        this.newLocation = newLocation;
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
}
