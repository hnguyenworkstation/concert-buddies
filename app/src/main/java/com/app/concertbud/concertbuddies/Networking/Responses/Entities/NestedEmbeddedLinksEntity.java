package com.app.concertbud.concertbuddies.Networking.Responses.Entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by huongnguyen on 3/14/18.
 */

public class NestedEmbeddedLinksEntity implements Serializable {

    @Expose
    @SerializedName("self")
    private SelfEntity self;

    public SelfEntity getSelf() {
        return self;
    }

    public void setSelf(SelfEntity self) {
        this.self = self;
    }

}
