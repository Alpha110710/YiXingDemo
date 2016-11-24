package com.yixing.model;

import com.google.gson.annotations.SerializedName;
import com.yixing.utils.android.QuickSetParcelableUtil;

import android.os.Parcel;
import android.os.Parcelable;

public class MyInvestPaymentModel extends BaseModel implements Parcelable {

	@SerializedName("FINANCE_PRODUCTS_NAME")
	private String loanFlg;// 贷款类型 小图片

	@SerializedName("PRODUCTS_TITLE")
	private String title;// 标题

	@SerializedName("TENDER_AMOUNT_FORMAT")
	private String financingAmount;// 融资金额

	@SerializedName("RECOVER_AMOUNT_TOTAL_YES_FORMAT")
	private String receivedAmount;// 已收金额

	@SerializedName("RECOVER_AMOUNT_INTEREST")
	private String profit;// 收益
	
	@SerializedName("OID_TRANSFER_ID")
	private String typeFlg;//债权或者非债权
	
	@SerializedName("OID_TENDER_ID")
    private String OID_TENDER_ID;
	
	@SerializedName("TENDER_FROM")
    private String TENDER_FROM;
	
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

	public String getTypeFlg() {
		return typeFlg;
	}

	public void setTypeFlg(String typeFlg) {
		this.typeFlg = typeFlg;
	}

	

	public String getOID_TENDER_ID() {
		return OID_TENDER_ID;
	}

	public void setOID_TENDER_ID(String oID_TENDER_ID) {
		OID_TENDER_ID = oID_TENDER_ID;
	}

	public String getTENDER_FROM() {
		return TENDER_FROM;
	}

	public void setTENDER_FROM(String tENDER_FROM) {
		TENDER_FROM = tENDER_FROM;
	}

	public String getLoanFlg() {
		return loanFlg;
	}

	public void setLoanFlg(String loanFlg) {
		this.loanFlg = loanFlg;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFinancingAmount() {
		return financingAmount;
	}

	public void setFinancingAmount(String financingAmount) {
		this.financingAmount = financingAmount;
	}

	public String getReceivedAmount() {
		return receivedAmount;
	}

	public void setReceivedAmount(String receivedAmount) {
		this.receivedAmount = receivedAmount;
	}

	public String getProfit() {
		return profit;
	}

	public void setProfit(String profit) {
		this.profit = profit;
	}

	public static Parcelable.Creator<MyInvestPaymentModel> getCREATOR() {
		return CREATOR;
	}

	public static void setCREATOR(Parcelable.Creator<MyInvestPaymentModel> cREATOR) {
		CREATOR = cREATOR;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		QuickSetParcelableUtil.write(dest, this);

	}

	public static Parcelable.Creator<MyInvestPaymentModel> CREATOR = new Creator<MyInvestPaymentModel>() {

		@Override
		public MyInvestPaymentModel createFromParcel(Parcel source) {
			MyInvestPaymentModel obj = (MyInvestPaymentModel) QuickSetParcelableUtil.read(source, MyInvestPaymentModel.class);
			return obj;
		}

		@Override
		public MyInvestPaymentModel[] newArray(int size) {
			return new MyInvestPaymentModel[size];
		}

	};

}
