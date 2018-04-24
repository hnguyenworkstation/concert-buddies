package com.app.concertbud.concertbuddies.Networking.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by huongnguyen on 4/24/18.
 */

public class HerokuImageWrapper {
    @Expose
    @SerializedName("data")
    HerokuImage image;

    public HerokuImage getImage() {
        return image;
    }

    public void setImage(HerokuImage image) {
        this.image = image;
    }
}
