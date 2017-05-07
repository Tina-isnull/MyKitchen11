package com.example.lcc.mykitchen.entity;

import cn.bmob.v3.BmobObject;

public class Zan extends BmobObject{
	
	 String  dynamicId;//分享人
	String userId;//本机用户

	public String getDynamicId() {
		return dynamicId;
	}

	public void setDynamicId(String dynamicId) {
		this.dynamicId = dynamicId;
	}

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	

}
