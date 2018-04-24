package com.app.concertbud.concertbuddies.Networking.Responses;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by huongnguyen on 3/27/18.
 */

public class Chatroom {
    private String lastMessage;
    private String timestamp;
    private Map<String, Object> users = new HashMap<>();

    public Chatroom() {}

    public Chatroom(String lastMessage, String timestamp) {
        this.lastMessage = lastMessage;
        this.timestamp = timestamp;
    }
    @Exclude
    public Map<String, Object> toMap () {
        HashMap<String, Object> result = new HashMap<>();
        result.put("lastMessage", lastMessage);
        result.put("timestamp", timestamp);
        result.put("users", users);
        return result;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public Map<String, Object> getUsers() {
        return users;
    }

    public void setUsers(Map<String, Object> users) {
        this.users = users;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
