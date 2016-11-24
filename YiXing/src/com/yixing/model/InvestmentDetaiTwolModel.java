package com.yixing.model;

import com.google.gson.annotations.SerializedName;

public class InvestmentDetaiTwolModel extends BaseModel{
	/**
	 * 电话号码
	 * 
	 */
	@SerializedName("MOBIL")
	private String phone;
	/**
	 * 标的类型 是否需要定向密码
	 * 
	 */
	@SerializedName("DIRECTIONAL_PWD")
	private String type;
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * 账户余额
	 */
	@SerializedName("USABLE_AMOUNT")
	private String user_Money;
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 最大投资金额
	 */
	@SerializedName("MAX_TENDER_AMOUNT")
	private String investment_Max_Money;
	/**
	 * 最小投资金额
	 */
	@SerializedName("MIN_TENDER_AMOUNT")
	private String investment_Min_Money;
	
	/**
	 * 红包个数
	 */
	@SerializedName("MYREDPACKET")
	private String myRed_Num;
	

	/**
	 * 加息卷个数
	 */
	@SerializedName("MY_TICKET")
	private String  my_Ticket;
	
	/**
	 * 开通银行卡是否是本行   0：绑定e存管   1：绑定商卡   null：没有任何绑定
	 */
	@SerializedName("EXTENSION")
	private String extension;
	
	public String getUser_Money() {
		return user_Money;
	}

	public void setUser_Money(String user_Money) {
		this.user_Money = user_Money;
	}

	public String getInvestment_Max_Money() {
		return investment_Max_Money;
	}

	public void setInvestment_Max_Money(String investment_Max_Money) {
		this.investment_Max_Money = investment_Max_Money;
	}

	public String getInvestment_Min_Money() {
		return investment_Min_Money;
	}

	public void setInvestment_Min_Money(String investment_Min_Money) {
		this.investment_Min_Money = investment_Min_Money;
	}

	public String getMyRed_Num() {
		return myRed_Num;
	}

	public void setMyRed_Num(String myRed_Num) {
		this.myRed_Num = myRed_Num;
	}

	public String getMy_Ticket() {
		return my_Ticket;
	}

	public void setMy_Ticket(String my_Ticket) {
		this.my_Ticket = my_Ticket;
	}
	public String getExtension() {
		return extension;
	}
	
	public void setExtension(String extension) {
		this.extension = extension;
	}

	
}
