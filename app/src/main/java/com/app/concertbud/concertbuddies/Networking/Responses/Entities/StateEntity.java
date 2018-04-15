package com.app.concertbud.concertbuddies.Networking.Responses.Entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by huongnguyen on 3/14/18.
 */


public class StateEntity implements Serializable {
    @Expose
    @SerializedName("stateCode")
    private String statecode;
    @Expose
    @SerializedName("name")
    private String name;

    public String getStatecode() {
        return statecode;
    }

    public void setStatecode(String statecode) {
        this.statecode = statecode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}