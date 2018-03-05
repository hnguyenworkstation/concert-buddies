package com.app.concertbud.concertbuddies.EventBuses;

import android.location.Location;

/**
 * Created by huongnguyen on 3/5/18.
 */

public class DeliverLocationBus {
    /* deliver last known location*/
    Location location;

    public DeliverLocationBus(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
