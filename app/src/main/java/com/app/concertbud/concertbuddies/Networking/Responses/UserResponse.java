package com.app.concertbud.concertbuddies.Networking.Responses;

import android.support.annotation.Keep;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hungnguyen on 4/6/18.
 */
@Keep
public class UserResponse {
    @Expose
    @SerializedName("who")
    private String who;
    @Expose
    @SerializedName("used")
    private boolean used;
    @Expose
    @SerializedName("url")
    private String url;
    @Expose
    @SerializedName("type")
    private String type;
    @Expose
    @SerializedName("source")
    private String source;
    @Expose
    @SerializedName("publishedAt")
    private String publishedat;
    @Expose
    @SerializedName("desc")
    private String desc;
    @Expose
    @SerializedName("createdAt")
    private String createdat;
    @Expose
    @SerializedName("_id")
    private String Id;

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public boolean getUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getPublishedat() {
        return publishedat;
    }

    public void setPublishedat(String publishedat) {
        this.publishedat = publishedat;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCreatedAt() {
        return createdat;
    }

    public void setCreatedAt(String createdat) {
        this.createdat = createdat;
    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }
}
