package com.app.concertbud.concertbuddies.Networking.Responses;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by huongnguyen on 3/27/18.
 */

public class User {
    private String name;
    private String fb_id;
    private Map<String, Boolean> chatrooms = new HashMap<>();

    /* Required for calls to DataSnapshot.getValue(User.class) */
    public User() {}

    public User(String name, String fb_id) {
        this.name = name;
        this.fb_id = fb_id;
    }
    @Exclude
    public Map<String, Object> toMap () {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("fb_id", fb_id);
        result.put("chatrooms", chatrooms);

        return result;
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
