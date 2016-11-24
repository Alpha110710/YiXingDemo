package com.yixing.model;

import com.google.gson.annotations.SerializedName;

public class InvestmentModel extends BaseModel{
	
	/**
	 * 产品的唯一id标识符
	 * 
	 */
	@SerializedName("OID_PLATFORM_PRODUCTS_ID")
	private String id;
	/**
	 * 债权转让id
	 * 
	 */
	@SerializedName("OID_TRANSFER_ID")
	private String zqId;
	
	public String getZqId() {
		return zqId;
	}
	public void setZqId(String zqId) {
		this.zqId = zqId;
	}
	/**
	 * 产品类型（图标）
	 */
	@SerializedName("FINANCE_PRODUCTS_NAME")
	private String type;
	/**
	 * title
	 */
	@SerializedName("PRODUCTS_TITLE")
	private String title;
	/**
	 * 折让比例
	 */
	@SerializedName("TRANSFER_CAPITAL_SCALE")
	private String investment_Rewards;
	/**
	 * 年华收益
	 */
	@SerializedName("FINANCE_INTEREST_RATE")
	private String earning_Rate;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 产品期限
	 */
	@SerializedName("FINANCE_PERIOD")
	private String product_Deadline;
	/**
	 * 产品期限单位
	 */
	@SerializedName("INTEREST_RATE_TYPE")
	private String product_Deadline_Type;
	
	
	/**
	 * 剩余期限
	 */
	@SerializedName("RECOVER_DATE")
	private String RECOVER_DATE;
	public String getRECOVER_DATE() {
		return RECOVER_DATE;
	}
	public void setRECOVER_DATE(String rECOVER_DATE) {
		RECOVER_DATE = rECOVER_DATE;
	}
	public String getRECOVER_DATE_CP() {
		return RECOVER_DATE_CP;
	}
	public void setRECOVER_DATE_CP(String rECOVER_DATE_CP) {
		RECOVER_DATE_CP = rECOVER_DATE_CP;
	}
	/**
	 * 剩余期限(单位)
	 */
	@SerializedName("RECOVER_DATE_CP")
	private String RECOVER_DATE_CP;
	
	
	/**
	 * 融资金额
	 */
	@SerializedName("FINANCE_AMOUNT")
	private String product_Money;
	
	public String getProduct_Deadline_Type() {
		return product_Deadline_Type;
	}
	public void setProduct_Deadline_Type(String product_Deadline_Type) {
		this.product_Deadline_Type = product_Deadline_Type;
	}
	/**
	 * 融资金额单位
	 */
	@SerializedName("FINANCE_AMOUNT_CP")
	private String product_Money_type;
	
	/**
	 * 还款方式
	 */
	@SerializedName("FINANCE_REPAY_TYPE")
	private String FINANCE_REPAY_TYPE;
	
	
	public String getFINANCE_REPAY_TYPE() {
		return FINANCE_REPAY_TYPE;
	}
	public void setFINANCE_REPAY_TYPE(String fINANCE_REPAY_TYPE) {
		FINANCE_REPAY_TYPE = fINANCE_REPAY_TYPE;
	}
	public String getProduct_Money_type() {
		return product_Money_type;
	}
	public void setProduct_Money_type(String product_Money_type) {
		this.product_Money_type = product_Money_type;
	}
	/**
	 * 投资进度类型（例如：投标中，正在投资，投资结束状态值）
	 */
	@SerializedName("LABEL")
	private String investment_Type;
	/**
	 * 进度值
	 */
	@SerializedName("FINANCE_AMOUNT_SCALE")
	private String investment_Schedule;
	/**
	 * 投资倒计时(结束)
	 */
	@SerializedName("FINANCE_START_DATE")
	private String investment_Time_End;
	/**
	 * 投资倒计时(开始)
	 */
	@SerializedName("FINANCE_SYSTEM_DATE")
	private String investment_Time_Begin;
	
	public String getInvestment_Time_End() {
		return investment_Time_End;
	}
	public void setInvestment_Time_End(String investment_Time_End) {
		this.investment_Time_End = investment_Time_End;
	}
	public String getInvestment_Time_Begin() {
		return investment_Time_Begin;
	}
	public void setInvestment_Time_Begin(String investment_Time_Begin) {
		this.investment_Time_Begin = investment_Time_Begin;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getInvestment_Rewards() {
		return investment_Rewards;
	}
	public void setInvestment_Rewards(String investment_Rewards) {
		this.investment_Rewards = investment_Rewards;
	}
	public String getEarning_Rate() {
		return earning_Rate;
	}
	public void setEarning_Rate(String earning_Rate) {
		this.earning_Rate = earning_Rate;
	}
	public String getProduct_Deadline() {
		return product_Deadline;
	}
	public void setProduct_Deadline(String product_Deadline) {
		this.product_Deadline = product_Deadline;
	}
	public String getProduct_Money() {
		return product_Money;
	}
	public void setProduct_Money(String product_Money) {
		this.product_Money = product_Money;
	}
	public String getInvestment_Type() {
		return investment_Type;
	}
	public void setInvestment_Type(String investment_Type) {
		this.investment_Type = investment_Type;
	}
	public String getInvestment_Schedule() {
		return investment_Schedule;
	}
	public void setInvestment_Schedule(String investment_Schedule) {
		this.investment_Schedule = investment_Schedule;
	}
}









