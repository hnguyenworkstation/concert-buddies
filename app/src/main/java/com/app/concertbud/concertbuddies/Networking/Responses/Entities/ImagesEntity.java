package com.app.concertbud.concertbuddies.Networking.Responses.Entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by huongnguyen on 3/14/18.
 */
public class ImagesEntity implements Serializable {
    @Expose
    @SerializedName("fallback")
    private boolean fallback;
    @Expose
    @SerializedName("height")
    private int height;
    @Expose
    @SerializedName("width")
    private int width;
    @Expose
    @SerializedName("url")
    private String url;
    @Expose
    @SerializedName("ratio")
    private String ratio;

    public boolean getFallback() {
        return fallback;
    }

    public void setFallback(boolean fallback) {
        this.fallback = fallback;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRatio() {
        return ratio;
    }

    public void setRatio(String ratio) {
        this.ratio = ratio;
    }
}