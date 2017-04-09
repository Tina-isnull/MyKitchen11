package com.example.lcc.mykitchen.entity;

/**
 * Created by lcc on 2016/12/12.
 */
public class Image {
    private String imgUrl;
    private int width;
    private int height;

    public Image(String imgUrl,int width, int height) {
        this.imgUrl = imgUrl;
        this.width = width;
        this.height = height;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

}
