package com.yixing.model;

import com.google.gson.annotations.SerializedName;

public class ReturnMoneyPlanModel extends BaseModel {

	@SerializedName("PRODUCTS_TITLE")
	private String productsTitle;// 标题

	@SerializedName("RECOVER_AMOUNT_LVEL")
	private String financeInterestRate;// 待回利息

	@SerializedName("RECOVER_AMOUNT_CAPITAL")
	private String financePeriod;// 待回本金

	@SerializedName("TENDER_FROM") // 1非债权 2债权
	private String interestRateType;// 债权-非债权

	@SerializedName("FINANCE_INTEREST_RATE")
	private String recoverDate;// 日期

	@SerializedName("CURRENT_PERIOD")
	private String currentPeriod;// 期数

	@SerializedName("OID_TENDER_ID")
	private String OID_TENDER_ID;// 回款详情需要传的参数

	@SerializedName("OVERDUE_FLG")
	private String OVERDUE_FLG;// 判断是否逾期

	@SerializedName("RECOVER_AMOUNT_TOTAL")
	private String RECOVER_AMOUNT_TOTAL;
	
	@SerializedName("FINANCE_PERIOD_FORMAT")
	private String FINANCE_PERIOD_FORMAT;//剩余期限
	
	@SerializedName("TRANSFER_CONTRACT_ID")
	private String TRANSFER_CONTRACT_ID;//债权编号
	
	
	
	
	
	public String getTRANSFER_CONTRACT_ID() {
		return TRANSFER_CONTRACT_ID;
	}

	public void setTRANSFER_CONTRACT_ID(String tRANSFER_CONTRACT_ID) {
		TRANSFER_CONTRACT_ID = tRANSFER_CONTRACT_ID;
	}

	public String getFINANCE_PERIOD_FORMAT() {
		return FINANCE_PERIOD_FORMAT;
	}

	public void setFINANCE_PERIOD_FORMAT(String fINANCE_PERIOD_FORMAT) {
		FINANCE_PERIOD_FORMAT = fINANCE_PERIOD_FORMAT;
	}

	public String getRECOVER_AMOUNT_TOTAL() {
		return RECOVER_AMOUNT_TOTAL;
	}

	public void setRECOVER_AMOUNT_TOTAL(String rECOVER_AMOUNT_TOTAL) {
		RECOVER_AMOUNT_TOTAL = rECOVER_AMOUNT_TOTAL;
	}

	public String getOVERDUE_FLG() {
		return OVERDUE_FLG;
	}

	public void setOVERDUE_FLG(String oVERDUE_FLG) {
		OVERDUE_FLG = oVERDUE_FLG;
	}

	public String getProductsTitle() {
		return productsTitle;
	}

	public void setProductsTitle(String productsTitle) {
		this.productsTitle = productsTitle;
	}

	public String getFinanceInterestRate() {
		return financeInterestRate;
	}

	public void setFinanceInterestRate(String financeInterestRate) {
		this.financeInterestRate = financeInterestRate;
	}

	public String getFinancePeriod() {
		return financePeriod;
	}

	public void setFinancePeriod(String financePeriod) {
		this.financePeriod = financePeriod;
	}

	public String getInterestRateType() {
		return interestRateType;
	}

	public void setInterestRateType(String interestRateType) {
		this.interestRateType = interestRateType;
	}

	public String getRecoverDate() {
		return recoverDate;
	}

	public void setRecoverDate(String recoverDate) {
		this.recoverDate = recoverDate;
	}

	public String getCurrentPeriod() {
		return currentPeriod;
	}

	public void setCurrentPeriod(String currentPeriod) {
		this.currentPeriod = currentPeriod;
	}

	public String getOID_TENDER_ID() {
		return OID_TENDER_ID;
	}

	public void setOID_TENDER_ID(String oID_TENDER_ID) {
		OID_TENDER_ID = oID_TENDER_ID;
	}

}
