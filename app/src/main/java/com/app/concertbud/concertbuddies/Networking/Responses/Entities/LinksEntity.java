package com.app.concertbud.concertbuddies.Networking.Responses.Entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by huongnguyen on 3/14/18.
 */

public class LinksEntity implements Serializable {
    @Expose
    @SerializedName("last")
    private LastEntity last;
    @Expose
    @SerializedName("next")
    private NextEntity next;
    @Expose
    @SerializedName("self")
    private SelfEntity self;
    @Expose
    @SerializedName("first")
    private FirstEntity first;

    public LastEntity getLast() {
        return last;
    }

    public void setLast(LastEntity last) {
        this.last = last;
    }

    public NextEntity getNext() {
        return next;
    }

    public void setNext(NextEntity next) {
        this.next = next;
    }

    public SelfEntity getSelf() {
        return self;
    }

    public void setSelf(SelfEntity self) {
        this.self = self;
    }

    public FirstEntity getFirst() {
        return first;
    }

    public void setFirst(FirstEntity first) {
        this.first = first;
    }
}