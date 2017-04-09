package com.example.lcc.mykitchen.entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by lcc on 2017/4/9.
 */
public class Comments extends BmobObject {
   private String shareId;
   private String content;

    public String getShareId() {
        return shareId;
    }

    public void setShareId(String shareId) {
        this.shareId = shareId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
