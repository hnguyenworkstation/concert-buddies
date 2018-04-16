package com.app.concertbud.concertbuddies.Networking.Responses.Entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by huongnguyen on 3/14/18.
 */

public class PublicEntity implements Serializable {
    @Expose
    @SerializedName("endDateTime")
    private String enddatetime;
    @Expose
    @SerializedName("startTBD")
    private boolean starttbd;
    @Expose
    @SerializedName("startDateTime")
    private String startdatetime;

    public String getEnddatetime() {
        return enddatetime;
    }

    public void setEnddatetime(String enddatetime) {
        this.enddatetime = enddatetime;
    }

    public boolean getStarttbd() {
        return starttbd;
    }

    public void setStarttbd(boolean starttbd) {
        this.starttbd = starttbd;
    }

    public String getStartdatetime() {
        return startdatetime;
    }

    public void setStartdatetime(String startdatetime) {
        this.startdatetime = startdatetime;
    }
}