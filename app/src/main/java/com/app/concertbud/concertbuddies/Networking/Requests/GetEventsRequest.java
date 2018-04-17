package com.app.concertbud.concertbuddies.Networking.Requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by cmunroe on 4/17/18.
 */

public class GetEventsRequest {
    @Expose
    @SerializedName("fb_token")
    private String fb_token;

    public GetEventsRequest(String fb_token) {
        this.fb_token = fb_token;
    }

    public String getFb_token() {
        return fb_token;
    }

    public void setFb_token(String fb_token) {
        this.fb_token = fb_token;
    }
}
