package com.app.concertbud.concertbuddies.Networking.Responses;

/**
 * Created by huongnguyen on 3/25/18.
 */

public class Message {
    private String content;
    public Message () {}
    public Message (String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
