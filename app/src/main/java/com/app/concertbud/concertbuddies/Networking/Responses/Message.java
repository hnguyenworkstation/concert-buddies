package com.app.concertbud.concertbuddies.Networking.Responses;

/**
 * Created by huongnguyen on 3/25/18.
 */

public class Message {
    private String content;
    private String senderName;
    private String timestamp;

    /* Required for calls to DataSnapshot.getValue(Message.class) */
    public Message () {}
    public Message (String content, String senderName, String timestamp) {
        this.content = content;
        this.senderName = senderName;
        this.timestamp = timestamp;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
