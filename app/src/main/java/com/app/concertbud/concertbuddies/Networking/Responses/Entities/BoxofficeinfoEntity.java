package com.app.concertbud.concertbuddies.Networking.Responses.Entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by huongnguyen on 3/14/18.
 */


public class BoxofficeinfoEntity implements Serializable {
    @Expose
    @SerializedName("willCallDetail")
    private String willcalldetail;
    @Expose
    @SerializedName("acceptedPaymentDetail")
    private String acceptedpaymentdetail;
    @Expose
    @SerializedName("openHoursDetail")
    private String openhoursdetail;
    @Expose
    @SerializedName("phoneNumberDetail")
    private String phonenumberdetail;

    public String getWillcalldetail() {
        return willcalldetail;
    }

    public void setWillcalldetail(String willcalldetail) {
        this.willcalldetail = willcalldetail;
    }

    public String getAcceptedpaymentdetail() {
        return acceptedpaymentdetail;
    }

    public void setAcceptedpaymentdetail(String acceptedpaymentdetail) {
        this.acceptedpaymentdetail = acceptedpaymentdetail;
    }

    public String getOpenhoursdetail() {
        return openhoursdetail;
    }

    public void setOpenhoursdetail(String openhoursdetail) {
        this.openhoursdetail = openhoursdetail;
    }

    public String getPhonenumberdetail() {
        return phonenumberdetail;
    }

    public void setPhonenumberdetail(String phonenumberdetail) {
        this.phonenumberdetail = phonenumberdetail;
    }
}