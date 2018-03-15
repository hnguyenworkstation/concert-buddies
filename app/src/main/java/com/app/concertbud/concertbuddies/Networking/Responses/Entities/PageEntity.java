package com.app.concertbud.concertbuddies.Networking.Responses.Entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by huongnguyen on 3/14/18.
 */

public class PageEntity {
    @Expose
    @SerializedName("number")
    private int number;
    @Expose
    @SerializedName("totalPages")
    private int totalpages;
    @Expose
    @SerializedName("totalElements")
    private int totalelements;
    @Expose
    @SerializedName("size")
    private int size;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getTotalpages() {
        return totalpages;
    }

    public void setTotalpages(int totalpages) {
        this.totalpages = totalpages;
    }

    public int getTotalelements() {
        return totalelements;
    }

    public void setTotalelements(int totalelements) {
        this.totalelements = totalelements;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}