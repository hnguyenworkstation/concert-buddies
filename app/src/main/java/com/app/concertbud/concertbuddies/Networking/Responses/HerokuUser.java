package com.app.concertbud.concertbuddies.Networking.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by csadmin on 4/23/2018.
 */

public class HerokuUser {
    @Expose
    @SerializedName("id")
    private String fb_token;
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("picture")
    private HerokuImageWrapper image;
    @Expose
    @SerializedName("firebase_token")
    private String firebase_token;

    public String getFb_token() {
        return fb_token;
    }

    public void setFb_token(String fb_token) {
        this.fb_token = fb_token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HerokuImageWrapper getImage() {
        return image;
    }

    public void setImage(HerokuImageWrapper image) {
        this.image = image;
    }

    public String getFirebase_token() {
        return firebase_token;
    }

    public void setFirebase_token(String firebase_token) {
        this.firebase_token = firebase_token;
    }
}
