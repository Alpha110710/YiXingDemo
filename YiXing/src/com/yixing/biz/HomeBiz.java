package com.yixing.biz;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.yixing.YixingConfig;
import com.yixing.biz.SoapProcessor.PropertyType;
import com.yixing.biz.exception.BizFailure;
import com.yixing.biz.exception.ZYException;
import com.yixing.model.City;
import com.yixing.model.County;
import com.yixing.model.HomePageModel;
import com.yixing.model.LoginModel;
import com.yixing.model.Province;
import com.yixing.model.RedomVerifyCode;
import com.yixing.model.VersionDescription;
import com.yixing.utils.android.HttpUtil;
import com.yixing.utils.android.LogUtil;

public class HomeBiz extends BaseBiz {

	/**
	 * 登录
	 * 
	 * @param userName
	 * @param password
	 * @return
	 * @throws BizFailure
	 * @throws ZYException
	 */
	public static LoginModel login(String userName, String password,
			String terminalType, String version) // 终端 "2" 安卓  "3"ios , 当前版本 ： 0
			throws BizFailure, ZYException {
		SoapProcessor ksoap = new SoapProcessor("Service", "checkUserLogin",
				false);

		ksoap.setProperty("userName", userName, PropertyType.TYPE_STRING);
		ksoap.setProperty("password", password, PropertyType.TYPE_STRING);
		ksoap.setProperty("terminalType", terminalType,
				PropertyType.TYPE_STRING);
		// ksoap.setProperty("version", version, PropertyType.TYPE_STRING);

		JsonElement element = ksoap.request();
		Gson gson = new GsonBuilder().create();
		return gson.fromJson(element, LoginModel.class);

	}

	/**
	 * 注册
	 * 
	 * @param phoneNum
	 * @param verifyCode
	 * @param password
	 * @param invitor
	 * @return
	 * @throws BizFailure
	 * @throws ZYException
	 */

	public static String register(String userName, String password,
			String mobileCode, String inviteId, String terminalType,
			String province, String city, String area) throws BizFailure,
			ZYException {
		SoapProcessor ksoap2 = new SoapProcessor("Service", "regist", false);

		ksoap2.setProperty("userName", userName, PropertyType.TYPE_STRING);
		ksoap2.setProperty("password", password, PropertyType.TYPE_STRING);
		ksoap2.setProperty("mobileCode", mobileCode, PropertyType.TYPE_STRING);
		ksoap2.setProperty("inviteId", inviteId, PropertyType.TYPE_STRING);
		ksoap2.setProperty("terminalType", terminalType,
				PropertyType.TYPE_STRING); // 终端 2" 安卓 "3" ios ,
		ksoap2.setProperty("province", province, PropertyType.TYPE_STRING);
		ksoap2.setProperty("city", city, PropertyType.TYPE_STRING); // 当前版本 ： 0
		ksoap2.setProperty("area", area, PropertyType.TYPE_STRING); // 当前版本 ： 0

		return ksoap2.request().getAsString();
	}

	/**
	 * 找回登录密码
	 * 
	 * @param mobile
	 * @param verifyCode
	 * @param pwdType
	 * @return
	 * @throws BizFailure
	 * @throws ZYException
	 */

	public static String findPassword(String mobile, String verifyCode)
			throws BizFailure, ZYException {
		SoapProcessor ksoap2 = new SoapProcessor("Service", "findPassword",
				false);

		ksoap2.setProperty("mobile", mobile, PropertyType.TYPE_STRING);
		ksoap2.setProperty("verifyCode", verifyCode, PropertyType.TYPE_STRING);
		return ksoap2.request().getAsString();
	}

	/*
	 * 获取首页数据信息
	 */

	public static HomePageModel getHomePage() throws BizFailure, ZYException {
		SoapProcessor ksoap2 = new SoapProcessor("Service", "getHomePage",
				false);

		JsonElement element = ksoap2.request();
		Gson gson = new GsonBuilder().create();
		return gson.fromJson(element, HomePageModel.class);
	}

	/**
	 * 获取随机验证码
	 * 
	 * @return
	 * @throws BizFailure
	 * @throws ZYException
	 */
	public static RedomVerifyCode getCaptchaImage() throws BizFailure,
			ZYException {
		SoapProcessor ksoap2 = new SoapProcessor("Service", "getVerifyCode",
				false);

		JsonElement element = ksoap2.request();
		Gson gson = new GsonBuilder().create();
		return gson.fromJson(element, RedomVerifyCode.class);
	}

	/**
	 * 注册 获取短信验证码
	 * 
	 * @param mobile
	 * @return
	 * @throws BizFailure
	 * @throws ZYException
	 */
	public static String getRegMobileCode(String mobile) throws BizFailure,
			ZYException {
		SoapProcessor ksoap2 = new SoapProcessor("Service", "getRegMobileCode",
				false);
		ksoap2.setProperty("mobile", mobile, PropertyType.TYPE_STRING);
		return ksoap2.request().getAsString();
	}

	/**
	 * 找回密码 获取验证码
	 * 
	 * @param mobile
	 * @throws BizFailure
	 * @throws ZYException
	 */
	public static String getMobileCode(String mobile) throws BizFailure,
			ZYException {
		SoapProcessor ksoap2 = new SoapProcessor("Service", "getMobileCode",
				false);
		ksoap2.setProperty("mobile", mobile, PropertyType.TYPE_STRING);
		return ksoap2.request().getAsString();
	}

	/**
	 * @Description 获取省份列表
	 * @return 0 注册 1 安全中心
	 * @throws BizFailure
	 * @throws ZYException
	 */
	public static List<Province> getProvinceList(String searchType)
			throws BizFailure, ZYException {
		SoapProcessor ksoap2 = new SoapProcessor("Service", "getProvincesList",
				false);
		ksoap2.setProperty("searchType", searchType, PropertyType.TYPE_STRING);
		JsonElement element = ksoap2.request();
		Gson gson = new GsonBuilder().create();
		TypeToken<List<Province>> tt = new TypeToken<List<Province>>() {
		};
		return gson.fromJson(element, tt.getType());
	}

	/**
	 * @Description 获取城市列表
	 * @param provinceId
	 * @return
	 * @throws BizFailure
	 * @throws ZYException
	 */
	public static List<City> getCityList(String provinceId, String searchType)
			throws BizFailure, ZYException {
		SoapProcessor ksoap2 = new SoapProcessor("Service", "getCitiesList",
				false);
		ksoap2.setProperty("provinceId", provinceId, PropertyType.TYPE_STRING);
		ksoap2.setProperty("searchType", searchType, PropertyType.TYPE_STRING);
		JsonElement element = ksoap2.request();
		Gson gson = new GsonBuilder().create();
		TypeToken<List<City>> tt = new TypeToken<List<City>>() {
		};
		return gson.fromJson(element, tt.getType());
	}

	/**
	 * 获取区列表
	 * 
	 * @param cityId
	 * @return
	 * @throws BizFailure
	 * @throws ZYException
	 */
	public static List<County> getCountyList(String cityId) throws BizFailure,
			ZYException {
		SoapProcessor ksoap2 = new SoapProcessor("Service", "getAreasList",
				false);
		ksoap2.setProperty("cityId", cityId, PropertyType.TYPE_STRING);
		JsonElement element = ksoap2.request();
		Gson gson = new GsonBuilder().create();
		TypeToken<List<County>> tt = new TypeToken<List<County>>() {
		};
		return gson.fromJson(element, tt.getType());
	}

	public static String financeApplication(String financingAmountExpect,
			String financingRateExpect, String financingReason,
			String financingUse, String province,
			String city, String introducerMobile)
			throws BizFailure, ZYException {
		SoapProcessor ksoap2 = new SoapProcessor("Service",
				"financeApplication", true);
		ksoap2.setProperty("financingAmountExpect", financingAmountExpect,
				PropertyType.TYPE_STRING);
		ksoap2.setProperty("financingRateExpect", financingRateExpect,
				PropertyType.TYPE_STRING);
		ksoap2.setProperty("financingReason", financingReason,
				PropertyType.TYPE_STRING);
		ksoap2.setProperty("financingUse", financingUse,
				PropertyType.TYPE_STRING);
		ksoap2.setProperty("province", province, PropertyType.TYPE_STRING);
		ksoap2.setProperty("city", city, PropertyType.TYPE_STRING);
		ksoap2.setProperty("introducerMobile", introducerMobile,
				PropertyType.TYPE_STRING);
		JsonElement element = ksoap2.request();
		
		return element.getAsString();
	
	}
	
	
	/**
	 * 检测新版本
	 * 
	 * @throws ZYException
	 */
	public static VersionDescription detectNewVersion() throws ZYException {
		try {
			String result = HttpUtil
					.postRespString(YixingConfig.VERSION_DETECTION_URL);
			LogUtil.e(result);
			Gson gson = new Gson(); 
			return gson.fromJson(result, VersionDescription.class);
		} catch (Exception e) {
			throw new ZYException();
		}
	}

}
