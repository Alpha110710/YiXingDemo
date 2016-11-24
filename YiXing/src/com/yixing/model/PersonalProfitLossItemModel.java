package com.yixing.model;

import com.google.gson.annotations.SerializedName;

public class PersonalProfitLossItemModel extends BaseModel{
	
	@SerializedName("FUND_TYPE")
	private String fundType;//前面小title
	
	@SerializedName("AMOUNT")
	private String amount;//金额
	
	@SerializedName("DATE")
	private String date;//日期
	
	@SerializedName("REVENUE_EXPEND_TYPE")
	private String revenueExpendType;//判断正负

	public String getFundType() {
		return fundType;
	}

	public void setFundType(String fundType) {
		this.fundType = fundType;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getRevenueExpendType() {
		return revenueExpendType;
	}

	public void setRevenueExpendType(String revenueExpendType) {
		this.revenueExpendType = revenueExpendType;
	}
	
	

}
