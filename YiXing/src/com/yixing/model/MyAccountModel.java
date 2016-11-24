package com.yixing.model;

import com.google.gson.annotations.SerializedName;
import com.yixing.utils.android.QuickSetParcelableUtil;

import android.os.Parcel;
import android.os.Parcelable;

public class MyAccountModel extends BaseModel implements Parcelable {

	@SerializedName("HEADPIC") // 头像
	private String headPic;
	
	
	@SerializedName("USABLE_AMOUNT") // 可用余额
	private String USABLE_AMOUNT;

	@SerializedName("FROZE_AMOUNT") // 冻结金额
	private String frozeAmount;

	@SerializedName("DUEIN_AMOUNT") // 待收金额
	private String await;

	@SerializedName("EXTENSION_VALUE")
	private String bankOpenFlg;// 浙商银行存管账户已开通?
	
	@SerializedName("ID_CARD") // 身份号 全
	private String CARD_ID_TEXT;
	
	@SerializedName("MOBILE") // 手机号
	private String phoneNumber;
	
	@SerializedName("PROVINCE") // 省
	private String province;
	
	@SerializedName("CITY")//市
	private String cityName;

	@SerializedName("AREA")//区
	private String area;
	
	
	@SerializedName("ADDRESS")//地址
	private String address;

	@SerializedName("USER_PWD")//用户密码
	private String USER_PWD;
	
	
	@SerializedName("USER_PAY_PWD")//支付密码
	private String USER_PAY_PWD;
	
	@SerializedName("informationFlag")
	private boolean messageFlg;//判断是否有新消息?
	
	@SerializedName("CARD_NO")
	private String CARD_NO;//银行卡号全
	
	@SerializedName("CARD_NO2")
	private String CARD_NO2;//银行卡号中间隐藏
	
	@SerializedName("USER_NAME")
	private String USER_NAME;//用户名
	
	@SerializedName("VERIFY_FLG")
	private String VERIFY_FLG;//实名认证flg  1以实名
	
	@SerializedName("SING_MIN_AOUT")
	private String SING_MIN_AOUT;//最小充值金额
	
	
	
	
	
	
	/*******************************************/
	

	
	@SerializedName("REAL_NAME") // 用户实名 匿名
	private String REAL_NAME;

	@SerializedName("REAL_NAME_TEXT") // 实名全名
	private String REAL_NAME_TEXT;

	@SerializedName("WITHDRAW_MONEY") // 可提现金额
	private String WITHDRAW_MONEY;

	@SerializedName("INTEREST_YES")
	private String interestYes;

	

	@SerializedName("PHONE_NUMBER_CONCEAL")
	private String phoneNumberConceal;

	@SerializedName("CARD_ID") // 身份证 匿名
	private String CARD_ID;
	

	@SerializedName("REAL_FLG") // 实名flag
	private String REAL_FLG;

	@SerializedName("BANK_REAL_NAME") // 银行名
	private String bankRealName;

	
	
	
	
	
	

	@SerializedName("BRANCH")
	private String branch;

	@SerializedName("BANK_FLG") // 绑卡flag
	private String bankFlg;

	

	@SerializedName("AUTO_FINANCE_AMOUNT")
	private String AUTO_FINANCE_AMOUNT;

	@SerializedName("USER_HEAD_IMG")
	private String USER_HEAD_IMG;


	@SerializedName("BANK_CARD_NO")
	private String BANK_CARD_NO;

	@SerializedName("BANK_IMG")
	private String BANK_IMG;

	@SerializedName("WITHDRAW_PWD_FLG") // 提现密码相同
	private boolean WITHDRAW_PWD_FLG;

	@SerializedName("MIN_ACCOUNT_ONE")
	private String MIN_ACCOUNT_ONE;

	@SerializedName("MIN_ACCOUNT_OTHER")
	private String MIN_ACCOUNT_OTHER;
	/**
	 * 1 开启 ， 0未开启
	 */
	@SerializedName("AUTO_FLG")
	private String autoFlg;

	@SerializedName("PROVINCE_ID")
	private String provinceId;

	@SerializedName("PROVINCE_NAME")
	private String provinceName;

	@SerializedName("CITY_ID")
	private String cityId;

	
	@SerializedName("IS_VIP")
	private String isVip;

	@SerializedName("USER_INTEGRAL")
	private String userIntegral;

	@SerializedName("IS_SIGN")
	private String isSign;

	

	@SerializedName("CONTINUE_LOGIN_DATE")
	private String continueLoginDate;

	@SerializedName("WITHDRAW_EXPLAIN")
	private String withDrawExplain;

	@SerializedName("MAIL_ADDRESS")
	private String MAIL_ADDRESS;

	@SerializedName("MAIL_FLG")
	private String MAIL_FLG;

	/**
	 * 提现手续费
	 */
	@SerializedName("WITHDRAW_FEE")
	private String withDrawFee;
	
	
	

	

	
	
	
	

	public String getSING_MIN_AOUT() {
		return SING_MIN_AOUT;
	}

	public void setSING_MIN_AOUT(String sING_MIN_AOUT) {
		SING_MIN_AOUT = sING_MIN_AOUT;
	}

	public String getVERIFY_FLG() {
		return VERIFY_FLG;
	}

	public void setVERIFY_FLG(String vERIFY_FLG) {
		VERIFY_FLG = vERIFY_FLG;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getUSER_PWD() {
		return USER_PWD;
	}

	public void setUSER_PWD(String uSER_PWD) {
		USER_PWD = uSER_PWD;
	}

	public String getUSER_PAY_PWD() {
		return USER_PAY_PWD;
	}

	public void setUSER_PAY_PWD(String uSER_PAY_PWD) {
		USER_PAY_PWD = uSER_PAY_PWD;
	}

	public boolean isWITHDRAW_PWD_FLG() {
		return WITHDRAW_PWD_FLG;
	}

	public void setWITHDRAW_PWD_FLG(boolean wITHDRAW_PWD_FLG) {
		WITHDRAW_PWD_FLG = wITHDRAW_PWD_FLG;
	}

	public static Parcelable.Creator<MyAccountModel> getCREATOR() {
		return CREATOR;
	}

	public static void setCREATOR(Parcelable.Creator<MyAccountModel> cREATOR) {
		CREATOR = cREATOR;
	}

	public String getBankOpenFlg() {
		return bankOpenFlg;
	}

	public void setBankOpenFlg(String bankOpenFlg) {
		this.bankOpenFlg = bankOpenFlg;
	}

	public String getMIN_ACCOUNT_ONE() {
		return MIN_ACCOUNT_ONE;
	}

	public void setMIN_ACCOUNT_ONE(String mIN_ACCOUNT_ONE) {
		MIN_ACCOUNT_ONE = mIN_ACCOUNT_ONE;
	}

	public String getMIN_ACCOUNT_OTHER() {
		return MIN_ACCOUNT_OTHER;
	}

	public void setMIN_ACCOUNT_OTHER(String mIN_ACCOUNT_OTHER) {
		MIN_ACCOUNT_OTHER = mIN_ACCOUNT_OTHER;
	}

	public boolean getPWD_SAME_FLG() {
		return WITHDRAW_PWD_FLG;
	}

	public void setPWD_SAME_FLG(boolean pWD_SAME_FLG) {
		WITHDRAW_PWD_FLG = pWD_SAME_FLG;
	}

	public String getBANK_IMG() {
		return BANK_IMG;
	}

	public void setBANK_IMG(String bANK_IMG) {
		BANK_IMG = bANK_IMG;
	}

	public String getBANK_CARD_NO() {
		return BANK_CARD_NO;
	}

	public void setBANK_CARD_NO(String bANK_CARD_NO) {
		BANK_CARD_NO = bANK_CARD_NO;
	}

	public String getUSER_NAME() {
		return USER_NAME;
	}

	public void setUSER_NAME(String uSER_NAME) {
		USER_NAME = uSER_NAME;
	}


	public String getUSER_HEAD_IMG() {
		return USER_HEAD_IMG;
	}

	public void setUSER_HEAD_IMG(String uSER_HEAD_IMG) {
		USER_HEAD_IMG = uSER_HEAD_IMG;
	}

	public String getAUTO_FINANCE_AMOUNT() {
		return AUTO_FINANCE_AMOUNT;
	}

	public void setAUTO_FINANCE_AMOUNT(String aUTO_FINANCE_AMOUNT) {
		AUTO_FINANCE_AMOUNT = aUTO_FINANCE_AMOUNT;
	}

	public String getHeadPic() {
		return headPic;
	}
	
	public void setHeadPic(String headPic) {
		this.headPic = headPic;
	}
	
	public String getBalance() {
		return USABLE_AMOUNT;
	}

	public void setBalance(String balance) {
		this.USABLE_AMOUNT = balance;
	}

	public String getFrozeAmount() {
		return frozeAmount;
	}

	public void setFrozeAmount(String frozeAmount) {
		this.frozeAmount = frozeAmount;
	}

	public String getAwait() {
		return await;
	}

	public void setAwait(String await) {
		this.await = await;
	}

	public String getREAL_NAME() {
		return REAL_NAME;
	}

	public void setREAL_NAME(String REAL_NAME) {
		this.REAL_NAME = REAL_NAME;
	}

	public String getREAL_NAME_TEXT() {
		return REAL_NAME_TEXT;
	}

	public void setREAL_NAME_TEXT(String REAL_NAME_TEXT) {
		this.REAL_NAME_TEXT = REAL_NAME_TEXT;
	}

	public String getWITHDRAW_MONEY() {
		return WITHDRAW_MONEY;
	}

	public void setWITHDRAW_MONEY(String WITHDRAW_MONEY) {
		this.WITHDRAW_MONEY = WITHDRAW_MONEY;
	}

	public String getInterestYes() {
		return interestYes;
	}

	public void setInterestYes(String interestYes) {
		this.interestYes = interestYes;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPhoneNumberConceal() {
		return phoneNumberConceal;
	}

	public void setPhoneNumberConceal(String phoneNumberConceal) {
		this.phoneNumberConceal = phoneNumberConceal;
	}

	public String getCARD_ID() {
		return CARD_ID;
	}

	public void setCARD_ID(String CARD_ID) {
		this.CARD_ID = CARD_ID;
	}

	public String getCARD_ID_TEXT() {
		return CARD_ID_TEXT;
	}

	public void setCARD_ID_TEXT(String CARD_ID_TEXT) {
		this.CARD_ID_TEXT = CARD_ID_TEXT;
	}

	public String getREAL_FLG() {
		return REAL_FLG;
	}

	public void setREAL_FLG(String REAL_FLG) {
		this.REAL_FLG = REAL_FLG;
	}

	public String getBankRealName() {
		return bankRealName;
	}

	public void setBankRealName(String bankRealName) {
		this.bankRealName = bankRealName;
	}



	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getBankFlg() {
		return bankFlg;
	}

	public void setBankFlg(String bankFlg) {
		this.bankFlg = bankFlg;
	}

	

	public String getUSABLE_AMOUNT() {
		return USABLE_AMOUNT;
	}

	public void setUSABLE_AMOUNT(String uSABLE_AMOUNT) {
		USABLE_AMOUNT = uSABLE_AMOUNT;
	}

	public String getCARD_NO() {
		return CARD_NO;
	}

	public void setCARD_NO(String cARD_NO) {
		CARD_NO = cARD_NO;
	}

	public String getCARD_NO2() {
		return CARD_NO2;
	}

	public void setCARD_NO2(String cARD_NO2) {
		CARD_NO2 = cARD_NO2;
	}

	public String getAutoFlg() {
		return autoFlg;
	}

	public void setAutoFlg(String mAutoFlg) {
		this.autoFlg = mAutoFlg;
	}

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String mProvinceId) {
		this.provinceId = mProvinceId;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String mProvinceName) {
		this.provinceName = mProvinceName;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String mCityId) {
		this.cityId = mCityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String mCityName) {
		this.cityName = mCityName;
	}

	public String getIsVip() {
		return isVip;
	}

	public void setIsVip(String isVip) {
		this.isVip = isVip;
	}

	public String getUserIntegral() {
		return userIntegral;
	}

	public void setUserIntegral(String userIntegral) {
		this.userIntegral = userIntegral;
	}

	public String getIsSign() {
		return isSign;
	}

	public void setIsSign(String isSign) {
		this.isSign = isSign;
	}

	
	public boolean isMessageFlg() {
		return messageFlg;
	}

	public void setMessageFlg(boolean messageFlg) {
		this.messageFlg = messageFlg;
	}

	public String getContinueLoginDate() {
		return continueLoginDate;
	}

	public void setContinueLoginDate(String mContinueLoginDate) {
		this.continueLoginDate = mContinueLoginDate;
	}

	public String getWithDrawFee() {
		return withDrawFee;
	}

	public void setWithDrawFee(String mWithDrawFee) {
		this.withDrawFee = mWithDrawFee;
	}

	public String getWithDrawExplain() {
		return withDrawExplain;
	}

	public void setWithDrawExplain(String mWithDrawExplain) {
		this.withDrawExplain = mWithDrawExplain;
	}

	public String getMAIL_ADDRESS() {
		return MAIL_ADDRESS;
	}

	public void setMAIL_ADDRESS(String MAIL_ADDRESS) {
		this.MAIL_ADDRESS = MAIL_ADDRESS;
	}

	public String getMAIL_FLG() {
		return MAIL_FLG;
	}

	public void setMAIL_FLG(String MAIL_FLG) {
		this.MAIL_FLG = MAIL_FLG;
	}

	public static Parcelable.Creator<MyAccountModel> CREATOR = new Creator<MyAccountModel>() {

		@Override
		public MyAccountModel[] newArray(int size) {
			return new MyAccountModel[size];
		}

		@Override
		public MyAccountModel createFromParcel(Parcel source) {
			MyAccountModel myaccount = (MyAccountModel) QuickSetParcelableUtil.read(source, MyAccountModel.class);

			return myaccount;
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		QuickSetParcelableUtil.write(dest, this);
	}

}
