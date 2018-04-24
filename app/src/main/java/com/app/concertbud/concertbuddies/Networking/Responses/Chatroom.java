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
    private boolean read;
    private Map<String, Boolean> users = new HashMap<>();

    public Chatroom() {}

    public Chatroom(String lastMessage, String timestamp, boolean read) {
        this.lastMessage = lastMessage;
        this.timestamp = timestamp;
        this.read = read;
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

    public Map<String, Boolean> getUsers() {
        return users;
    }

    public boolean getRead() {
        return read;
    }

    public void setSenderId(boolean read) {
        this.read = read;
    }

    public void setUsers(Map<String, Boolean> users) {
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
