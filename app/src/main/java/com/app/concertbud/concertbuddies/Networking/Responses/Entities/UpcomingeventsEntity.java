package com.app.concertbud.concertbuddies.Networking.Responses.Entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by huongnguyen on 3/14/18.
 */

public class UpcomingeventsEntity implements Serializable {

    @Expose
    @SerializedName("mfx-be")
    private int mfx_be;
    @Expose
    @SerializedName("ticketmaster")
    private int ticketmaster;
    @Expose
    @SerializedName("tmr")
    private int tmr;
    @Expose
    @SerializedName("mfx-nl")
    private int mfx_nl;
    @Expose
    @SerializedName("_total")
    private int Total;

    public int getMfx_be() {
        return mfx_be;
    }

    public void setMfx_be(int mfx_be) {
        this.mfx_be = mfx_be;
    }

    public int getMfx_nl() {
        return mfx_nl;
    }

    public void setMfx_nl(int tmfx_nl) {
        mfx_nl = tmfx_nl;
    }

    public int getTicketmaster() {
        return ticketmaster;
    }

    public void setTicketmaster(int ticketmaster) {
        this.ticketmaster = ticketmaster;
    }

    public int getTmr() {
        return tmr;
    }

    public void setTmr(int tmr) {
        this.tmr = tmr;
    }
    public int getTotal() {
        return Total;
    }

    public void setTotal(int Total) {
        this.Total = Total;
    }
}