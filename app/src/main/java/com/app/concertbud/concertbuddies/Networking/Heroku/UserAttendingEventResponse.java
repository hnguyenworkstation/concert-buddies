package com.app.concertbud.concertbuddies.Networking.Heroku;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by hungnguyen on 4/15/18.
 */

public class UserAttendingEventResponse implements Serializable {
    @Expose
    @SerializedName("id")
    private int userId;
    @Expose
    @SerializedName("fb_user_id")
    private String fbUserId;
    @Expose
    @SerializedName("eventId")
    private String eventId;
    @Expose
    @SerializedName("seq_id")
    private int seqId;
    @Expose
    @SerializedName("updatedAt")
    private String updatedAt;
    @Expose
    @SerializedName("createdAt")
    private String createdAt;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFbUserId() {
        return fbUserId;
    }

    public void setFbUserId(String fbUserId) {
        this.fbUserId = fbUserId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public int getSeqId() {
        return seqId;
    }

    public void setSeqId(int seqId) {
        this.seqId = seqId;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
