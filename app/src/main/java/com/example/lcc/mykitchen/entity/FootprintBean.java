package com.example.lcc.mykitchen.entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by lcc on 2017/4/22.
 */
public class FootprintBean extends BmobObject{
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
