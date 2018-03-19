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

    public NewUserRequest(String fb_token) {
        this.fb_token = fb_token;
    }

    public String getFb_token() {
        return fb_token;
    }

    public void setFb_token(String fb_token) {
        this.fb_token = fb_token;
    }
}
