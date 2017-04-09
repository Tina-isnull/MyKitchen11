package com.example.lcc.mykitchen.entity;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by lcc on 2017/1/19.
 */
public class TypeFoodsShow {
    @DatabaseField(id=true)
    private String id;
    @DatabaseField
    private String name;
    @DatabaseField
    private String list;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getList() {
        return list;
    }

    public void setList(String list) {
        this.list = list;
    }
}
