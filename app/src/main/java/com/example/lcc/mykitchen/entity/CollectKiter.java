package com.example.lcc.mykitchen.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 *
 * @author tarena
 * name 定义收藏的菜名或者厨师名
 * imgHeader 定义收藏的。。头像
 * introduce 定义收藏的。。介绍
 */
@DatabaseTable
public class CollectKiter {
	@DatabaseField(id=true)
	private String id;
	@DatabaseField
	private String name;
	@DatabaseField
	private String imgHeader;
	@DatabaseField
	private String introduce;
	public CollectKiter(){
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
	public String getImgHeader() {
		return imgHeader;
	}
	public void setImgHeader(String imgHeader) {
		this.imgHeader = imgHeader;
	}
	public String getIntroduce() {
		return introduce;
	}
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}


}

