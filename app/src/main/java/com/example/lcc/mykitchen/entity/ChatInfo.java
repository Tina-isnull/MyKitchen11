package com.example.lcc.mykitchen.entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by lcc on 2017/5/3.
 */
public class ChatInfo extends BmobObject {
    private String userId;
    private String chatContent;
    private String receiveId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getChatContent() {
        return chatContent;
    }

    public void setChatContent(String chatContent) {
        this.chatContent = chatContent;
    }

    public String getReceiveId() {
        return receiveId;
    }

    public void setReceiveId(String receiveId) {
        this.receiveId = receiveId;
    }
}
