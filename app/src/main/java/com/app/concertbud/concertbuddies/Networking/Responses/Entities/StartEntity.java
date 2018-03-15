package com.app.concertbud.concertbuddies.Networking.Responses.Entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by huongnguyen on 3/14/18.
 */
public class StartEntity {
    @Expose
    @SerializedName("noSpecificTime")
    private boolean nospecifictime;
    @Expose
    @SerializedName("timeTBA")
    private boolean timetba;
    @Expose
    @SerializedName("dateTBA")
    private boolean datetba;
    @Expose
    @SerializedName("dateTBD")
    private boolean datetbd;
    @Expose
    @SerializedName("dateTime")
    private String datetime;
    @Expose
    @SerializedName("localTime")
    private String localtime;
    @Expose
    @SerializedName("localDate")
    private String localdate;

    public boolean getNospecifictime() {
        return nospecifictime;
    }

    public void setNospecifictime(boolean nospecifictime) {
        this.nospecifictime = nospecifictime;
    }

    public boolean getTimetba() {
        return timetba;
    }

    public void setTimetba(boolean timetba) {
        this.timetba = timetba;
    }

    public boolean getDatetba() {
        return datetba;
    }

    public void setDatetba(boolean datetba) {
        this.datetba = datetba;
    }

    public boolean getDatetbd() {
        return datetbd;
    }

    public void setDatetbd(boolean datetbd) {
        this.datetbd = datetbd;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getLocaltime() {
        return localtime;
    }

    public void setLocaltime(String localtime) {
        this.localtime = localtime;
    }

    public String getLocaldate() {
        return localdate;
    }

    public void setLocaldate(String localdate) {
        this.localdate = localdate;
    }
}