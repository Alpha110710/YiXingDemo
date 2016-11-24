package com.yixing.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class InvestmentMessageModel extends BaseModel{
	
	/**
	 * 用户名
	 */
	@SerializedName("LOAN_NAME")
	private String userName;
	/**
	 * 婚姻状况
	 */
	@SerializedName("MARRIAGE")
	private String maritalStatus;
	/**
	 * 性别
	 */
	@SerializedName("SEX")
	private String sex;
	/**
	 * 年龄
	 */
	@SerializedName("AGE")
	private String age;
	/**
	 * 月收入
	 */
	@SerializedName("EARNINGS")
	private String month_income;
	public String getMonth_income() {
		return month_income;
	}

	public void setMonth_income(String month_income) {
		this.month_income = month_income;
	}

	/**
	 * 住房条件
	 */
	@SerializedName("HOUSING")
	private String housConditions;
	/**
	 * 文化程度
	 */
	@SerializedName("CULTURE")
	private String culture;
	
	/**
	 * 车
	 */
	@SerializedName("CAR")
	private String car;
	/**
	 * 借款用途
	 */
	@SerializedName("SOURCE")
	private String borrow_use;
	/**
	 * 还款来源
	 */
	@SerializedName("PAYMENT")
	private String payment_source;
	/**
	 * 描述
	 */
	@SerializedName("PAYDESCRIPTION")
	private String describe;
	/**
	 * 审核意见
	 */
	@SerializedName("AUDIT_OPINION")
	private String audit_opinion;
	/**
	 * 资质证明
	 */
	@SerializedName("DESCRIPTION_LIST")
	private List<QualificationModel> qualification_proof;
	/**
	 * item1
	 * @return
	 */
	@SerializedName("VERIFY_BUSINESS")
	private String VERIFY_BUSINESS;
	
	/**
	 * item2
	 * @return
	 */
	@SerializedName("VERIFY_ORGANIZATION")
	private String VERIFY_ORGANIZATION;
	
	/**
	 * item3
	 * @return
	 */
	@SerializedName("VERIFY_LICENCE")
	private String VERIFY_LICENCE;
	
	/**
	 * item4
	 * @return
	 */
	@SerializedName("VERIFY_ENTERPRISE")
	private String VERIFY_ENTERPRISE;
	
	/**
	 * item5
	 * @return
	 */
	@SerializedName("VERIFY_TAX")
	private String VERIFY_TAX;
	
	public String getVERIFY_BUSINESS() {
		return VERIFY_BUSINESS;
	}

	public void setVERIFY_BUSINESS(String vERIFY_BUSINESS) {
		VERIFY_BUSINESS = vERIFY_BUSINESS;
	}

	public String getVERIFY_ORGANIZATION() {
		return VERIFY_ORGANIZATION;
	}

	public void setVERIFY_ORGANIZATION(String vERIFY_ORGANIZATION) {
		VERIFY_ORGANIZATION = vERIFY_ORGANIZATION;
	}

	public String getVERIFY_LICENCE() {
		return VERIFY_LICENCE;
	}

	public void setVERIFY_LICENCE(String vERIFY_LICENCE) {
		VERIFY_LICENCE = vERIFY_LICENCE;
	}

	public String getVERIFY_ENTERPRISE() {
		return VERIFY_ENTERPRISE;
	}

	public void setVERIFY_ENTERPRISE(String vERIFY_ENTERPRISE) {
		VERIFY_ENTERPRISE = vERIFY_ENTERPRISE;
	}

	public String getVERIFY_TAX() {
		return VERIFY_TAX;
	}

	public void setVERIFY_TAX(String vERIFY_TAX) {
		VERIFY_TAX = vERIFY_TAX;
	}

	
	
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getHousConditions() {
		return housConditions;
	}

	public void setHousConditions(String housConditions) {
		this.housConditions = housConditions;
	}

	public String getCulture() {
		return culture;
	}

	public void setCulture(String culture) {
		this.culture = culture;
	}

	public String getCar() {
		return car;
	}

	public void setCar(String car) {
		this.car = car;
	}

	public String getBorrow_use() {
		return borrow_use;
	}

	public void setBorrow_use(String borrow_use) {
		this.borrow_use = borrow_use;
	}

	public String getPayment_source() {
		return payment_source;
	}

	public void setPayment_source(String payment_source) {
		this.payment_source = payment_source;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getAudit_opinion() {
		return audit_opinion;
	}

	public void setAudit_opinion(String audit_opinion) {
		this.audit_opinion = audit_opinion;
	}

	public List<QualificationModel> getQualification_proof() {
		return qualification_proof;
	}

	public void setQualification_proof(List<QualificationModel> qualification_proof) {
		this.qualification_proof = qualification_proof;
	}


	
}










