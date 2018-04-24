package com.app.concertbud.concertbuddies.Networking.Responses;

import com.app.concertbud.concertbuddies.Networking.Heroku.ReqResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by hungnguyen on 4/21/18.
 */

public class MatchResponse implements Serializable {
    @Expose
    @SerializedName("match")
    private String match;
    @Expose
    @SerializedName("liked_firebase_token")
    private String likedFirebaseToken;
    @Expose
    @SerializedName("liked_id")
    private String likedId;
    @Expose
    @SerializedName("message")
    private String message;
    @Expose
    @SerializedName("req")
    private ReqResponse reqResponse;

    public String getMatch() {
        return match;
    }

    public void setMatch(String match) {
        this.match = match;
    }

    public String getLikedFirebaseToken() {
        return likedFirebaseToken;
    }

    public void setLikedFirebaseToken(String likedFirebaseToken) {
        this.likedFirebaseToken = likedFirebaseToken;
    }

    public String getLikedId() {
        return likedId;
    }

    public void setLikedId(String likedId) {
        this.likedId = likedId;
    }

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
}
