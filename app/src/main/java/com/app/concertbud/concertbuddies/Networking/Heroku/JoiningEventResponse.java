package com.app.concertbud.concertbuddies.Networking.Heroku;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by hungnguyen on 4/15/18.
 */

public class JoiningEventResponse implements Serializable {
    @Expose
    @SerializedName("message")
    private String message;
    @Expose
    @SerializedName("req")
    private ReqResponse reqResponse;
    @Expose
    @SerializedName("user_attending_event")
    private UserAttendingEventResponse userAttendingEventResponse;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ReqResponse getReqResponse() {
        return reqResponse;
    }

    public void setReqResponse(ReqResponse reqResponse) {
        this.reqResponse = reqResponse;
    }

    public UserAttendingEventResponse getUserAttendingEventResponse() {
        return userAttendingEventResponse;
    }

    public void setUserAttendingEventResponse(UserAttendingEventResponse userAttendingEventResponse) {
        this.userAttendingEventResponse = userAttendingEventResponse;
    }
}
