package com.app.concertbud.concertbuddies.Networking.Heroku;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by hungnguyen on 4/15/18.
 */

public class ReqResponse implements Serializable {
    @Expose
    @SerializedName("fb_token")
    private String fbToken;
    @Expose
    @SerializedName("event_id")
    private String eventId;
    @Expose
    @SerializedName("like")
    private boolean isLiked;

    public String getFbToken() {
        return fbToken;
    }

    public void setFbToken(String fbToken) {
        this.fbToken = fbToken;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }
}
