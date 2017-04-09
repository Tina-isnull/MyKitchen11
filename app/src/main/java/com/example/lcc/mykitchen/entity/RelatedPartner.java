package com.example.lcc.mykitchen.entity;
import cn.bmob.v3.BmobObject;

/**
 * Created by lcc on 2017/1/23.
 */
public class RelatedPartner extends BmobObject {
    private String userName;
    private UserInfo relatedName;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public UserInfo getRelatedName() {
        return relatedName;
    }

    public void setRelatedName(UserInfo relatedName) {
        this.relatedName = relatedName;
    }
}
