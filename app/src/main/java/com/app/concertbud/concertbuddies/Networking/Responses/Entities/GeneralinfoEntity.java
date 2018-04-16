package com.app.concertbud.concertbuddies.Networking.Responses.Entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by huongnguyen on 3/14/18.
 */
public class GeneralinfoEntity implements Serializable {
    @Expose
    @SerializedName("childRule")
    private String childrule;
    @Expose
    @SerializedName("generalRule")
    private String generalrule;

    public String getChildrule() {
        return childrule;
    }

    public void setChildrule(String childrule) {
        this.childrule = childrule;
    }

    public String getGeneralrule() {
        return generalrule;
    }

    public void setGeneralrule(String generalrule) {
        this.generalrule = generalrule;
    }
}