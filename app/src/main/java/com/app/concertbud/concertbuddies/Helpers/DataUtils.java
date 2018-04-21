package com.app.concertbud.concertbuddies.Helpers;

import com.app.concertbud.concertbuddies.Networking.Responses.Entities.EventsEntity;

import java.util.List;

/**
 * Created by hungnguyen on 4/21/18.
 */

public class DataUtils {
    private static List<EventsEntity> subscribedEvent;

    public static void setSubscribedEvent(List<EventsEntity> subscribedEvent) {
        DataUtils.subscribedEvent = subscribedEvent;
    }

    public static List<EventsEntity> getSubscribedEvent() {
        return subscribedEvent;
    }

    public static void addSubscribedEvent(EventsEntity eventsEntity) {
        DataUtils.subscribedEvent.add(eventsEntity);
    }

    public static boolean isSubscribed(EventsEntity eventsEntity) {
        for (EventsEntity event: DataUtils.subscribedEvent) {
            if (eventsEntity ==event || eventsEntity.getId().equals(event.getId())) {
                return true;
            }
        }

        return false;
    }

    public static void removedSubscribedEvent(EventsEntity eventsEntity) {
        for (EventsEntity event: DataUtils.subscribedEvent) {
            if (eventsEntity == event || eventsEntity.getId().equals(event.getId())) {
                DataUtils.subscribedEvent.remove(eventsEntity);
                return;
            }
        }
    }
}
