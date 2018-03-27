package com.app.concertbud.concertbuddies.Networking.Responses.Entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by huongnguyen on 3/14/18.
 */

public class SocialTwitterEntity implements Serializable {

    @Expose
    @SerializedName("handle")
    private String handle;

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }
}
