package com.app.concertbud.concertbuddies.EventBuses;

import com.app.concertbud.concertbuddies.Networking.Responses.MatchProfileResponse;
import com.app.concertbud.concertbuddies.Networking.Responses.MatchResponse;

import java.util.List;

/**
 * Created by hungnguyen on 4/21/18.
 */

public class DeliverListMatchProfileBus {
    private String eventId;
    private List<MatchProfileResponse> matchProfileResponseList;
    private String toClass;
    private Type type;

    public static enum Type {
        POTENTIAL, ACTUAL
    }

    public DeliverListMatchProfileBus(String eventId, List<MatchProfileResponse> matchProfileResponseList,
                                      String toClass, Type type) {
        this.eventId = eventId;
        this.matchProfileResponseList = matchProfileResponseList;
        this.toClass = toClass;
        this.type = type;
    }

    public DeliverListMatchProfileBus(List<MatchProfileResponse> matchProfileResponseList, String toClass, Type type) {
        this.matchProfileResponseList = matchProfileResponseList;
        this.toClass = toClass;
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public List<MatchProfileResponse> getMatchProfileResponseList() {
        return matchProfileResponseList;
    }

    public void setMatchProfileResponseList(List<MatchProfileResponse> matchProfileResponseList) {
        this.matchProfileResponseList = matchProfileResponseList;
    }

    public String getToClass() {
        return toClass;
    }

    public void setToClass(String toClass) {
        this.toClass = toClass;
    }
}
