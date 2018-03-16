package com.app.concertbud.concertbuddies.Networking.Requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by huongnguyen on 3/15/18.
 */

public class NewUserRequest {
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("dob")
    private String dob;
    @Expose
    @SerializedName("gender")
    private String gender;
    @Expose
    @SerializedName("fb_token")
    private String fb_token;

    public NewUserRequest(String name, String dob, String gender, String fb_token) {
        this.name = name;
        this.dob = dob;
        this.gender = gender;
        this.fb_token = fb_token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFb_token() {
        return fb_token;
    }

    public void setFb_token(String fb_token) {
        this.fb_token = fb_token;
    }
}
