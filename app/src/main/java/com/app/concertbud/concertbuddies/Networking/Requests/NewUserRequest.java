package com.app.concertbud.concertbuddies.Networking.Requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by huongnguyen on 3/15/18.
 */

public class NewUserRequest {
    @Expose
    @SerializedName("fb_token")
    private String fb_token;
    @Expose
    @SerializedName("firebase_token")
    private String firebase_token;

    public NewUserRequest(String fb_token, String firebase_token) {
        this.fb_token = fb_token;
        this.firebase_token = firebase_token;
    }

    public String getFirebase_token() {
        return firebase_token;
    }

    public void setFirebase_token(String firebase_token) {
        this.firebase_token = firebase_token;
    }

    public String getFb_token() {
        return fb_token;
    }

    public void setFb_token(String fb_token) {
        this.fb_token = fb_token;
    }
}
