package com.yixing.model;

import com.google.gson.annotations.SerializedName;
import com.yixing.utils.android.QuickSetParcelableUtil;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class SecurityCenterModel extends BaseModel implements Parcelable{

	 /**
     * EXTENSION : 已开通
     * ID_CARD : 210502198410212416
     * MOBILE : 13889405860
     * PROVINCE : 山东省
     * CITY : 莱芜市
     * AREA : 莱城区
     * ADDRESS :
     * USER_PWD : ef209c9343ca8c715265781876657b18
     * USER_PAY_PWD : b3a2083f5814d4c81406c558552169e8
     */
	
	@SerializedName("EXTENSION")
	private String EXTENSION;//电子账户已开通

	@SerializedName("ID_CARD")
	private String ID_CARD;

	@SerializedName("MOBILE")
	private String MOBILE;

	@SerializedName("PROVINCE")
	private String PROVINCE;

	@SerializedName("CITY")
	private String CITY;

	@SerializedName("AREA")
	private String AREA;

	@SerializedName("ADDRESS")
	private String ADDRESS;

	@SerializedName("USER_PWD")
	private String USER_PWD;

	@SerializedName("USER_PAY_PWD")
	private String USER_PAY_PWD;
	
	@SerializedName("PROVINCE_ID")
	private String PROVINCE_ID;
	
	@SerializedName("CITY_ID")
	private String CITY_ID;
	
	
	
	

	public String getPROVINCE_ID() {
		return PROVINCE_ID;
	}

	public void setPROVINCE_ID(String pROVINCE_ID) {
		PROVINCE_ID = pROVINCE_ID;
	}

	public String getCITY_ID() {
		return CITY_ID;
	}

	public void setCITY_ID(String cITY_ID) {
		CITY_ID = cITY_ID;
	}

	public String getEXTENSION() {
		return EXTENSION;
	}

	public void setEXTENSION(String EXTENSION) {
		this.EXTENSION = EXTENSION;
	}

	public String getID_CARD() {
		return ID_CARD;
	}

	public void setID_CARD(String ID_CARD) {
		this.ID_CARD = ID_CARD;
	}

	public String getMOBILE() {
		return MOBILE;
	}

	public void setMOBILE(String MOBILE) {
		this.MOBILE = MOBILE;
	}

	public String getPROVINCE() {
		return PROVINCE;
	}

	public void setPROVINCE(String PROVINCE) {
		this.PROVINCE = PROVINCE;
	}

	public String getCITY() {
		return CITY;
	}

	public void setCITY(String CITY) {
		this.CITY = CITY;
	}

	public String getAREA() {
		return AREA;
	}

	public void setAREA(String AREA) {
		this.AREA = AREA;
	}

	public String getADDRESS() {
		return ADDRESS;
	}

	public void setADDRESS(String ADDRESS) {
		this.ADDRESS = ADDRESS;
	}

	public String getUSER_PWD() {
		return USER_PWD;
	}

	public void setUSER_PWD(String USER_PWD) {
		this.USER_PWD = USER_PWD;
	}

	public String getUSER_PAY_PWD() {
		return USER_PAY_PWD;
	}

	public void setUSER_PAY_PWD(String USER_PAY_PWD) {
		this.USER_PAY_PWD = USER_PAY_PWD;
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
	
	
	
	public static Parcelable.Creator<SecurityCenterModel> getCREATOR() {
		return CREATOR;
	}

	public static void setCREATOR(Parcelable.Creator<SecurityCenterModel> cREATOR) {
		CREATOR = cREATOR;
	}



	public static Parcelable.Creator<SecurityCenterModel> CREATOR = new Creator<SecurityCenterModel>() {

		@Override
		public SecurityCenterModel[] newArray(int size) {
			return new SecurityCenterModel[size];
		}

		@Override
		public SecurityCenterModel createFromParcel(Parcel source) {
			SecurityCenterModel model = (SecurityCenterModel) QuickSetParcelableUtil.read(source, SecurityCenterModel.class);

			return model;
		}
	};
}
