package com.yixing.model;

import com.google.gson.annotations.SerializedName;

public class NewNoticeModel extends BaseModel {

	@SerializedName("ID")
	private String id;
	
	@SerializedName("STATUS")
	private String mailStatus;
	
	@SerializedName("TITLE")
	private String title;
	
	@SerializedName("OPEN_FLG")
	private String openFlg;
	
	@SerializedName("PUBLISH_DATE")
	private String date;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getMailStatus() {
		return mailStatus;
	}

	public void setMailStatus(String mailStatus) {
		this.mailStatus = mailStatus;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getOpenFlg() {
		return openFlg;
	}
	
	public void setOpenFlg(String openFlg) {
		this.openFlg = openFlg;
	}
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
}