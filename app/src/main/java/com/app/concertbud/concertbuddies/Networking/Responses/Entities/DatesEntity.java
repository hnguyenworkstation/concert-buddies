package com.app.concertbud.concertbuddies.Networking.Responses.Entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by huongnguyen on 3/14/18.
 */

public class DatesEntity {
    @Expose
    @SerializedName("spanMultipleDays")
    private boolean spanmultipledays;
    @Expose
    @SerializedName("status")
    private StatusEntity status;
    @Expose
    @SerializedName("timezone")
    private String timezone;
    @Expose
    @SerializedName("start")
    private StartEntity start;

    public boolean getSpanmultipledays() {
        return spanmultipledays;
    }

    public void setSpanmultipledays(boolean spanmultipledays) {
        this.spanmultipledays = spanmultipledays;
    }

    public StatusEntity getStatus() {
        return status;
    }

    public void setStatus(StatusEntity status) {
        this.status = status;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public StartEntity getStart() {
        return start;
    }

    public void setStart(StartEntity start) {
        this.start = start;
    }
}