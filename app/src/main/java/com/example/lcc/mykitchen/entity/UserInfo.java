package com.example.lcc.mykitchen.entity;

import java.io.Serializable;

import cn.bmob.v3.BmobUser;

/**
 * Created by lcc on 2017/1/9.
 */
public class UserInfo extends BmobUser implements Serializable {
    private String headerUrl;
    private Boolean gender;
    private String intro;
    private String money;

    public String getHeaderUrl() {
        return headerUrl;
    }

    public void setHeaderUrl(String headerUrl) {
        this.headerUrl = headerUrl;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
