package com.example.lcc.mykitchen.entity;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * Created by lcc on 2017/4/9.
 */
public class CollectionFood extends BmobObject {
    private String userId;
    private FoodDetails foodDetails;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public FoodDetails getFoodDetails() {
        return foodDetails;
    }

    public void setFoodDetails(FoodDetails foodDetails) {
        this.foodDetails = foodDetails;
    }
}
