package com.example.lcc.mykitchen.entity;

import cn.bmob.v3.BmobObject;

public class Zan extends BmobObject{
	
	String shareUserId;//分享人
	String userId;//本机用户

	public String getShareUserId() {
		return shareUserId;
	}

	public void setShareUserId(String shareUserId) {
		this.shareUserId = shareUserId;
	}

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	

}
