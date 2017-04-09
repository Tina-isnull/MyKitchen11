package com.example.lcc.mykitchen.entity;

import java.io.Serializable;

/**
 * Created by lcc on 2017/1/26.
 */
public class MenuMaterial implements Serializable {
    private String material;
    private String count;

    @Override
    public String toString() {
        return "MenuMaterial{" +
                "material='" + material + '\'' +
                ", count='" + count + '\'' +
                '}';
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
