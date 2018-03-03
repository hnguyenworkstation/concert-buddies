package com.app.concertbud.concertbuddies.EventBuses;

/**
 * Created by hungnguyen on 3/3/18.
 */

public class TriggerViewBus {
    // Send signal to show which view of LocateEventMap
    int viewCode;

    public TriggerViewBus(int viewCode) {
        this.viewCode = viewCode;
    }

    public int getViewCode() {
        return viewCode;
    }
}
