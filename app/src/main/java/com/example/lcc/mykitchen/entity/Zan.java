package com.example.lcc.mykitchen.entity;

import cn.bmob.v3.BmobObject;

public class Zan extends BmobObject{
	
	 ShareFriends shareId;//分享人
	String userId;//本机用户

	public ShareFriends getShareId() {
		return shareId;
	}

	public void setShareId(ShareFriends shareId) {
		this.shareId = shareId;
	}

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	

}
