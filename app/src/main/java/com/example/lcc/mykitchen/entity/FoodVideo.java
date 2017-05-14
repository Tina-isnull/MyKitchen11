package com.example.lcc.mykitchen.entity;

import cn.bmob.v3.BmobObject;

public class FoodVideo extends BmobObject {
	//用户基本信息
	private UserInfo userInfo;
	//视频的url
	private String videoUrl;
	//视频的名称
	private String videoName;
	private String videoMoney;

	public String getVideoMoney() {
		return videoMoney;
	}

	public void setVideoMoney(String videoMoney) {
		this.videoMoney = videoMoney;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public String getVideoName() {
		return videoName;
	}

	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}
}


