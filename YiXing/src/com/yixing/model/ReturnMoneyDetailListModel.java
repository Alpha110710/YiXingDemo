package com.yixing.model;

import com.google.gson.annotations.SerializedName;

public class ReturnMoneyDetailListModel extends BaseModel{

	@SerializedName("RECOVER_DATE")
	private String data;// 日期
	
	@SerializedName("RECOVER_AMOUNT_CAPITAL")
	private String money;// 本金

	@SerializedName("RECOVER_AMOUNT_INTEREST")
	private String interest;// 利息
	
	
	@SerializedName("OVERDUE_FLG")
	private String type;// 0未逾期/1逾期
	
	@SerializedName("SUB_STATUS")
	private String RECOVER_FLG;// 0未回款/1已回款
	
	@SerializedName("OVERDUE_DAY")
	private String out_data;// 逾期天数
	
	@SerializedName("OVERDUE_INTEREST")
	private String out_interest;// 逾期利息
	
	@SerializedName("OVERDUE_FORFEIT")
	private String out_interest_punishment;// 逾期罚息
	
	
	
	
	public String getRECOVER_FLG() {
		return RECOVER_FLG;
	}
	public void setRECOVER_FLG(String rECOVER_FLG) {
		RECOVER_FLG = rECOVER_FLG;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getInterest() {
		return interest;
	}
	public void setInterest(String interest) {
		this.interest = interest;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getOut_data() {
		return out_data;
	}
	public void setOut_data(String out_data) {
		this.out_data = out_data;
	}
	public String getOut_interest() {
		return out_interest;
	}
	public void setOut_interest(String out_interest) {
		this.out_interest = out_interest;
	}
	public String getOut_interest_punishment() {
		return out_interest_punishment;
	}
	public void setOut_interest_punishment(String out_interest_punishment) {
		this.out_interest_punishment = out_interest_punishment;
	}
	
	
	
}
