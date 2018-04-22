package com.app.concertbud.concertbuddies.EventBuses;

import com.app.concertbud.concertbuddies.Networking.Responses.MatchResponse;

/**
 * Created by hungnguyen on 4/21/18.
 */

public class DeliverMatchResponseBus {
    private MatchResponse matchResponse;
    private String toClass;

    public DeliverMatchResponseBus(MatchResponse matchResponse, String toClass) {
        this.matchResponse = matchResponse;
        this.toClass = toClass;
    }

    public String getToClass() {
        return toClass;
    }

    public void setToClass(String toClass) {
        this.toClass = toClass;
    }

    public DeliverMatchResponseBus(MatchResponse matchResponse) {
        this.matchResponse = matchResponse;
    }

    public MatchResponse getMatchResponse() {
        return matchResponse;
    }

    public void setMatchResponse(MatchResponse matchResponse) {
        this.matchResponse = matchResponse;
    }
}
