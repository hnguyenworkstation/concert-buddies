package com.app.concertbud.concertbuddies.Networking.Responses;

/**
 * Created by huongnguyen on 3/25/18.
 */

public class Message {
    private String content;
    private String username;

    public Message () {}
    public Message (String content, String username) {
        this.content = content;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
