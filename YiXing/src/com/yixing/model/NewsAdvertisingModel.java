package com.yixing.model;

import com.google.gson.annotations.SerializedName;

public class NewsAdvertisingModel extends BaseModel {

	@SerializedName("NEWS_IMG")
	private String newsImg;
	
	@SerializedName("PUBLISH_DATE")
	private String publishDate;
	
	@SerializedName("TITLE")
	private String title;
	
	@SerializedName("NEWSID")
	private String id;
	
	
	public String getNewsImg() {
		return newsImg;
	}

	public void setNewsImg(String newsImg) {
		this.newsImg = newsImg;
	}
	
	public String getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}