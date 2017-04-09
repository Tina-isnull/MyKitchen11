package com.example.lcc.mykitchen.entity;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by lcc on 2017/1/26.
 */
public class UploadMenuBean extends BmobObject{
    private UserInfo user;
    private String menuHeader;
    private String menuName;
    private String menuIntro;
    private String menuMaterial;
    private List<FoodFromWeb.Steps> menuStepList;

    @Override
    public String toString() {
        return "UploadMenuBean{" +
                "user=" + user +
                ", menuHeader='" + menuHeader + '\'' +
                ", menuName='" + menuName + '\'' +
                ", menuIntro='" + menuIntro + '\'' +
                ", menuMaterial='" + menuMaterial + '\'' +
                ", menuStepList=" + menuStepList +
                '}';
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public String getMenuMaterial() {
        return menuMaterial;
    }

    public void setMenuMaterial(String menuMaterial) {
        this.menuMaterial = menuMaterial;
    }

    public String getMenuHeader() {
        return menuHeader;
    }

    public void setMenuHeader(String menuHeader) {
        this.menuHeader = menuHeader;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuIntro() {
        return menuIntro;
    }

    public void setMenuIntro(String menuIntro) {
        this.menuIntro = menuIntro;
    }


    public List<FoodFromWeb.Steps> getMenuStepList() {
        return menuStepList;
    }

    public void setMenuStepList(List<FoodFromWeb.Steps> menuStepList) {
        this.menuStepList = menuStepList;
    }




}
