package com.yixing.model;

import com.google.gson.annotations.SerializedName;

public class InvestmentMoreModel extends BaseModel{
	
	/**
	 * 投资人电话
	 * 
	 */
	@SerializedName("USER_PHONE")
	private String PHONE;
	/**
	 * 投标类型(自动投标，手动投标)
	 * 
	 */
	@SerializedName("TENDER_TYPE")
	private String type;
	/**
	 * 投标金额
	 * 
	 */
	@SerializedName("TENDER_AMOUNT_FORMAT")
	private String money;
	/**
	 * 投标时间
	 * 
	 */
	@SerializedName("TENDER_DATE")
	private String time;
	public String getPHONE() {
		return PHONE;
	}
	public void setPHONE(String pHONE) {
		PHONE = pHONE;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	
}









