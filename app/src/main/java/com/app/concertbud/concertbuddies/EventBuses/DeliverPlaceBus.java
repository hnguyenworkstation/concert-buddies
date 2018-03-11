package com.app.concertbud.concertbuddies.EventBuses;

import com.google.android.gms.location.places.Place;

/**
 * Created by hungnguyen on 3/11/18.
 */

public class DeliverPlaceBus {
    private Place place;

    public DeliverPlaceBus(Place place) {
        this.place = place;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }
}
