package com.yixing.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class InvestmentControlModel extends BaseModel {

	/**
	 * 担保方
	 * 
	 */
	@SerializedName("ASSURE")
	private String guarantor;
	/**
	 * 担保描述
	 * 
	 */
	@SerializedName("WARRDESC")
	private String guarantor_Description;
	
	/**
	 * list
	 * 
	 * 
	 */
	@SerializedName("DESCRIPTION_LIST")
	private List<ControlItem> controlItem;

	public String getGuarantor() {
		return guarantor;
	}

	public void setGuarantor(String guarantor) {
		this.guarantor = guarantor;
	}

	public String getGuarantor_Description() {
		return guarantor_Description;
	}

	public void setGuarantor_Description(String guarantor_Description) {
		this.guarantor_Description = guarantor_Description;
	}

	public List<ControlItem> getControlItem() {
		return controlItem;
	}

	public void setControlItem(List<ControlItem> controlItem) {
		this.controlItem = controlItem;
	}



}
