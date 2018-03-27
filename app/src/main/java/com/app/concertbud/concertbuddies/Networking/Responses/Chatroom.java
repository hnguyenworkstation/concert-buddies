package com.app.concertbud.concertbuddies.Networking.Responses;

/**
 * Created by huongnguyen on 3/27/18.
 */

public class Chatroom {
    private String chatRoomID;
    private String user1, user2;

    public Chatroom(String chatRoomID, String user1, String user2) {
        this.chatRoomID = chatRoomID;
        this.user1 = user1;
        this.user2 = user2;
    }

    public String getChatRoomID() {
        return chatRoomID;
    }

    public void setChatRoomID(String chatRoomID) {
        this.chatRoomID = chatRoomID;
    }

    public String getUser1() {
        return user1;
    }

    public void setUser1(String user1) {
        this.user1 = user1;
    }

    public String getUser2() {
        return user2;
    }

    public void setUser2(String user2) {
        this.user2 = user2;
    }
}
