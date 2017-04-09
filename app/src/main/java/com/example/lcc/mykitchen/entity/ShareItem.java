package com.example.lcc.mykitchen.entity;

import java.util.List;

/**
 * Created by lcc on 2016/12/12.
 * 作为
 */
public class ShareItem {
    private String context;//发布的内容
    private String time;//发布的时间
    private List<Image> images;
    private UserInfo info;

    public ShareItem() {
    }

    public ShareItem(String context, String time,List<Image> images,UserInfo info) {
        this.context = context;
        this.time = time;
        this.images = images;
        this.info=info;
    }

    public UserInfo getInfo() {
        return info;
    }

    public void setInfo(UserInfo info) {
        this.info = info;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

}
