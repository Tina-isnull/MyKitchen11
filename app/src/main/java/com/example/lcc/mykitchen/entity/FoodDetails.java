package com.example.lcc.mykitchen.entity;

import java.io.Serializable;

public class FoodDetails implements Serializable{
    private String id;
    private String kitterImgUrl;
    private String name;
    private FoodFromWeb.Detials detial;

    public FoodDetails(String id,String imgUrl,String name, FoodFromWeb.Detials detial) {
        this.id=id;
        this.kitterImgUrl=imgUrl;
        this.name = name;
        this.detial = detial;
    }

    public String getKitterImgUrl() {
        return kitterImgUrl;
    }

    public void setKitterImgUrl(String kitterImgUrl) {
        this.kitterImgUrl = kitterImgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FoodFromWeb.Detials getDetial() {
        return detial;
    }

    public void setDetial(FoodFromWeb.Detials detial) {
        this.detial = detial;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
