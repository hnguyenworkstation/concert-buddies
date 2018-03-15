package com.app.concertbud.concertbuddies.Networking.Responses.Entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by huongnguyen on 3/14/18.
 */

public class SocialEntity {
    @Expose
    @SerializedName("twitter")
    private SocialTwitterEntity twitter;

    public SocialTwitterEntity getTwitter() {
        return twitter;
    }

    public void setTwitter(SocialTwitterEntity twitter) {
        this.twitter = twitter;
    }
}