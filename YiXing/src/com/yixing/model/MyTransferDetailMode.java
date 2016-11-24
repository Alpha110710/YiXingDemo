package com.yixing.model;

import com.google.gson.annotations.SerializedName;
import com.yixing.utils.android.QuickSetParcelableUtil;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class MyTransferDetailMode extends BaseModel implements Parcelable{
	
	/**
	 * 原债权金额
	 */
	@SerializedName("DETAIL_TENDER_AMOUNT")
	private String DETAIL_TENDER_AMOUNT;
	/**
	 * 转出金额
	 */
	@SerializedName("DETAIL_TRANSFER_CAPITAL")
	private String DETAIL_TRANSFER_CAPITAL;
	/**
	 * 转出债权公允价值
	 */
	@SerializedName("DETAIL_FAIR_VALUE")
	private String DETAIL_FAIR_VALUE;
	/**
	 * 转出债权成交价格
	 */
	@SerializedName("DETAIL_TRANSFER_AMOUNT")
	private String DETAIL_TRANSFER_AMOUNT;
	/**
	 * 这让比例
	 */
	@SerializedName("DETAIL_DISCOUNT_SCALE")
	private String DETAIL_DISCOUNT_SCALE;
	/**
	 * 折让金
	 */
	@SerializedName("DETAIL_DISCOUNT_AMOUNT")
	private String DETAIL_DISCOUNT_AMOUNT;
	/**
	 * 转让时间
	 */
	@SerializedName("DETAIL_TRANSFER_TIME")
	private String DETAIL_TRANSFER_TIME;
	/**
	 * 服务费
	 */
	@SerializedName("DETAIL_TRANSFER_MANAGE_FEE")
	private String DETAIL_TRANSFER_MANAGE_FEE;
	/**
	 * 转让状态
	 */
	@SerializedName("DETAIL_TRANSFER_STATUS")
	private String DETAIL_TRANSFER_STATUS;
	public String getDETAIL_TENDER_AMOUNT() {
		return DETAIL_TENDER_AMOUNT;
	}
	public void setDETAIL_TENDER_AMOUNT(String dETAIL_TENDER_AMOUNT) {
		DETAIL_TENDER_AMOUNT = dETAIL_TENDER_AMOUNT;
	}
	public String getDETAIL_TRANSFER_CAPITAL() {
		return DETAIL_TRANSFER_CAPITAL;
	}
	public void setDETAIL_TRANSFER_CAPITAL(String dETAIL_TRANSFER_CAPITAL) {
		DETAIL_TRANSFER_CAPITAL = dETAIL_TRANSFER_CAPITAL;
	}
	public String getDETAIL_FAIR_VALUE() {
		return DETAIL_FAIR_VALUE;
	}
	public void setDETAIL_FAIR_VALUE(String dETAIL_FAIR_VALUE) {
		DETAIL_FAIR_VALUE = dETAIL_FAIR_VALUE;
	}
	public String getDETAIL_TRANSFER_AMOUNT() {
		return DETAIL_TRANSFER_AMOUNT;
	}
	public void setDETAIL_TRANSFER_AMOUNT(String dETAIL_TRANSFER_AMOUNT) {
		DETAIL_TRANSFER_AMOUNT = dETAIL_TRANSFER_AMOUNT;
	}
	public String getDETAIL_DISCOUNT_SCALE() {
		return DETAIL_DISCOUNT_SCALE;
	}
	public void setDETAIL_DISCOUNT_SCALE(String dETAIL_DISCOUNT_SCALE) {
		DETAIL_DISCOUNT_SCALE = dETAIL_DISCOUNT_SCALE;
	}
	public String getDETAIL_DISCOUNT_AMOUNT() {
		return DETAIL_DISCOUNT_AMOUNT;
	}
	public void setDETAIL_DISCOUNT_AMOUNT(String dETAIL_DISCOUNT_AMOUNT) {
		DETAIL_DISCOUNT_AMOUNT = dETAIL_DISCOUNT_AMOUNT;
	}
	public String getDETAIL_TRANSFER_TIME() {
		return DETAIL_TRANSFER_TIME;
	}
	public void setDETAIL_TRANSFER_TIME(String dETAIL_TRANSFER_TIME) {
		DETAIL_TRANSFER_TIME = dETAIL_TRANSFER_TIME;
	}
	public String getDETAIL_TRANSFER_MANAGE_FEE() {
		return DETAIL_TRANSFER_MANAGE_FEE;
	}
	public void setDETAIL_TRANSFER_MANAGE_FEE(String dETAIL_TRANSFER_MANAGE_FEE) {
		DETAIL_TRANSFER_MANAGE_FEE = dETAIL_TRANSFER_MANAGE_FEE;
	}
	public String getDETAIL_TRANSFER_STATUS() {
		return DETAIL_TRANSFER_STATUS;
	}
	public void setDETAIL_TRANSFER_STATUS(String dETAIL_TRANSFER_STATUS) {
		DETAIL_TRANSFER_STATUS = dETAIL_TRANSFER_STATUS;
	}
	
	public static Parcelable.Creator<MyTransferDetailMode> CREATOR = new Creator<MyTransferDetailMode>() {

		@Override
		public MyTransferDetailMode[] newArray(int size) {
			return new MyTransferDetailMode[size];
		}

		@Override
		public MyTransferDetailMode createFromParcel(Parcel source) {
			MyTransferDetailMode transferDetail = (MyTransferDetailMode) QuickSetParcelableUtil.read(source, MyTransferDetailMode.class);

			return transferDetail;
		}
	};
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
	
	

}
