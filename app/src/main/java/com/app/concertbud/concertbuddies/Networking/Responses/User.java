package com.app.concertbud.concertbuddies.Networking.Responses;

/**
 * Created by huongnguyen on 3/27/18.
 */

public class User {
    private String name;
    private String fb_id;

    public User(String name, String fb_id) {
        this.name = name;
        this.fb_id = fb_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFb_id() {
        return fb_id;
    }

    public void setFb_id(String fb_id) {
        this.fb_id = fb_id;
    }
}
