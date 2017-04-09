package com.example.lcc.mykitchen.entity;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by lcc on 2017/1/17.
 */
public class ShareFriends extends BmobObject {
    private String content;
    private List<String> imgs;
    private UserInfo userInfo;
    private Integer countLove;

    public Integer getCountLove() {
        return countLove;
    }

    public void setCountLove(Integer countLove) {
        this.countLove = countLove;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getImgs() {
        return imgs;
    }

    public void setImgs(List<String> imgs) {
        this.imgs = imgs;
    }
}
