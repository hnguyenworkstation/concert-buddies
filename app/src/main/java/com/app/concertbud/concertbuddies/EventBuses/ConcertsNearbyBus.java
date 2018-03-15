package com.app.concertbud.concertbuddies.EventBuses;

import com.app.concertbud.concertbuddies.Networking.Responses.Entities.EventsEntity;

import java.util.List;

/**
 * Created by huongnguyen on 3/10/18.
 */

public class ConcertsNearbyBus {
    private List<EventsEntity> concerts;
    public  ConcertsNearbyBus(List<EventsEntity> concerts) {
        this.concerts = concerts;
    }

    public List<EventsEntity> getConcerts() {
        return concerts;
    }

    public void setConcerts(List<EventsEntity> concerts) {
        this.concerts = concerts;
    }
}
