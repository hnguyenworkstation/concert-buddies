package com.app.concertbud.concertbuddies.Networking.Body;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by hungnguyen on 4/15/18.
 */

public class EventRequestBody implements Serializable {
    @Expose
    @SerializedName("eventId")
    private String eventId;
    @Expose
    @SerializedName("fb_token")
    private String fbToken;

    public EventRequestBody(String eventId, String fbToken) {
        this.eventId = eventId;
        this.fbToken = fbToken;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getFbToken() {
        return fbToken;
    }

    public void setFbToken(String fbToken) {
        this.fbToken = fbToken;
    }
}
