package com.app.concertbud.concertbuddies.Networking.Responses.Entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by huongnguyen on 3/14/18.
 */


public class CountryEntity implements Serializable {
    @Expose
    @SerializedName("countryCode")
    private String countrycode;
    @Expose
    @SerializedName("name")
    private String name;

    public String getCountrycode() {
        return countrycode;
    }

    public void setCountrycode(String countrycode) {
        this.countrycode = countrycode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
