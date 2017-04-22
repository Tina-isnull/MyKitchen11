package com.example.lcc.mykitchen.entity;

import com.j256.ormlite.field.DatabaseField;

/**
 *
 * @author tina
 * name 定义收藏的菜名或者厨师名
 * imgHeader 定义收藏的。。头像
 * introduce 定义收藏的。。介绍
 */
public class CollectFood {
	@DatabaseField(id=true)
	private String id;
	@DatabaseField
	private String name;
	@DatabaseField
	private int imgHeader;
	@DatabaseField
	private String introduce;
	public CollectFood(){
	}
	public CollectFood(String name, int imgHeader, String introduce) {
		this.name = name;
		this.imgHeader = imgHeader;
		this.introduce = introduce;
	}

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
	public int getImgHeader() {
		return imgHeader;
	}
	public void setImgHeader(int imgHeader) {
		this.imgHeader = imgHeader;
	}
	public String getIntroduce() {
		return introduce;
	}
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}


}

