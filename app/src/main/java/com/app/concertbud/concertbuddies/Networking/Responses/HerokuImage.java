package com.app.concertbud.concertbuddies.Networking.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by csadmin on 4/23/2018.
 */

public class HerokuImage {
    @Expose
    @SerializedName("height")
    private int height;
    @Expose
    @SerializedName("is_silhouette")
    private boolean is_silhouette;
    @Expose
    @SerializedName("url")
    private String url;
    @Expose
    @SerializedName("width")
    private int width;

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isIs_silhouette() {
        return is_silhouette;
    }

    public void setIs_silhouette(boolean is_silhouette) {
        this.is_silhouette = is_silhouette;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
