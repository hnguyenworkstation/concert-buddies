package com.app.concertbud.concertbuddies.Networking.Responses.Entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by huongnguyen on 3/14/18.
 */

public class SeatmapEntity {
    @Expose
    @SerializedName("staticUrl")
    private String staticurl;

    public String getStaticurl() {
        return staticurl;
    }

    public void setStaticurl(String staticurl) {
        this.staticurl = staticurl;
    }
}