package com.app.concertbud.concertbuddies.Networking.Responses.Entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hungnguyen on 3/11/18.
 */

public class LocationResultEntity {
    @Expose
    @SerializedName("city")
    private CityEntity city;
    @Expose
    @SerializedName("metroArea")
    private MetroAreaEntity metroarea;

    public CityEntity getCity() {
        return city;
    }

    public void setCity(CityEntity city) {
        this.city = city;
    }

    public MetroAreaEntity getMetroarea() {
        return metroarea;
    }

    public void setMetroarea(MetroAreaEntity metroarea) {
        this.metroarea = metroarea;
    }
}
