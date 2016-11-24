package com.yixing.biz;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.yixing.biz.SoapProcessor.PropertyType;
import com.yixing.biz.exception.BizFailure;
import com.yixing.biz.exception.ZYException;
import com.yixing.model.BaseModel;
import com.yixing.model.GetMessageInfoModel;
import com.yixing.model.InviteInfoModel;
import com.yixing.model.MessageModelList;
import com.yixing.model.MyAccountModel;
import com.yixing.model.MyAddRateCouponGiveCheckModel;
import com.yixing.model.MyAddRateCouponOutDateModel;
import com.yixing.model.MyAddRateCouponPresentedModel;
import com.yixing.model.MyAddRateCouponUnusedModel;
import com.yixing.model.MyAddRateCouponUsedModel;
import com.yixing.model.MyInvestBidModel;
import com.yixing.model.MyInvestHoldModel;
import com.yixing.model.MyInvestPaymentModel;
import com.yixing.model.MyRedPacketOutDateModel;
import com.yixing.model.MyRedPacketUnusedModel;
import com.yixing.model.MyRedPacketUsedModel;
import com.yixing.model.PersonalInformationDetailModel;
import com.yixing.model.MyTransferModel;
import com.yixing.model.MyTransferedModel;
import com.yixing.model.MyTransferingModel;
import com.yixing.model.PersonalProfitLossModel;
import com.yixing.model.RechargeModel;
import com.yixing.model.ReturnMoneyDetailListModel;
import com.yixing.model.PersonalProfitLossItemModel;
import com.yixing.model.ReturnMoneyDetailModelOne;
import com.yixing.model.ReturnMoneyDetailModelTwo;
import com.yixing.model.ReturnMoneyPlanModel;
import com.yixing.model.SecurityCenterModel;
import com.yixing.model.TradingRecordModel;
import com.yixing.model.TransferAjaxEventModel;
import com.yixing.model.WithdrawModel;
import com.yixing.model.GetUserAccountOfBankModel;
import com.yixing.model.GetUserTransferInfoModel;
import com.yixing.model.InvestContractModel;

import cn.sharesdk.framework.authorize.f;

public class AccountBiz extends BaseBiz {

	/*
	 * 获取我的账户信息
	 */

	public static MyAccountModel getMyAccountPage() throws BizFailure, ZYException {
		SoapProcessor ksoap2 = new SoapProcessor("Service", "getUserAccount", true);

		JsonElement element = ksoap2.request();
		Gson gson = new GsonBuilder().create();
		return gson.fromJson(element, MyAccountModel.class);
	}

	/*
	 * 获取我的投资 持有中 投标中 已回款 列表
	 */

	public static List<BaseModel> getMyInvestList(String status, String firstIdx, String maxCount)
			throws BizFailure, ZYException {
		SoapProcessor ksoap2 = new SoapProcessor("Service", "myInvest", true);

		ksoap2.setProperty("status", status, PropertyType.TYPE_STRING);
		ksoap2.setProperty("firstIdx", firstIdx, PropertyType.TYPE_STRING);
		ksoap2.setProperty("maxCount", maxCount, PropertyType.TYPE_STRING);

		JsonElement element = ksoap2.request();
		Gson gson = new GsonBuilder().create();

		List<BaseModel> fs = null;

		if (status.equals("1")) {
			TypeToken<List<MyInvestHoldModel>> tt = new TypeToken<List<MyInvestHoldModel>>() {
			};
			fs = gson.fromJson(element, tt.getType());
		} else if (status.equals("6")) {
			TypeToken<List<MyInvestPaymentModel>> tt = new TypeToken<List<MyInvestPaymentModel>>() {
			};
			fs = gson.fromJson(element, tt.getType());
		} else if (status.equals("2")) {

			TypeToken<List<MyInvestBidModel>> tt = new TypeToken<List<MyInvestBidModel>>() {
			};
			fs = gson.fromJson(element, tt.getType());
		} else if (status.equals("3")) { // 可转让
			TypeToken<List<MyTransferModel>> tt = new TypeToken<List<MyTransferModel>>() {
			};
			fs = gson.fromJson(element, tt.getType());
		} else if (status.equals("4")) { // 转让中
			TypeToken<List<MyTransferingModel>> tt = new TypeToken<List<MyTransferingModel>>() {
			};
			fs = gson.fromJson(element, tt.getType());
		} else if (status.equals("5")) { // 已转让
			TypeToken<List<MyTransferedModel>> tt = new TypeToken<List<MyTransferedModel>>() {
			};
			fs = gson.fromJson(element, tt.getType());
		}

		List<BaseModel> bms = new ArrayList<BaseModel>();
		bms.addAll(fs);
		return bms;
	}

	/*
	 * 充值
	 */

	public static String recharge(String rechargeAmount, String mobileCode, String mobile)
			throws BizFailure, ZYException {
		SoapProcessor ksoap2 = new SoapProcessor("Service", "recharge", true);

		ksoap2.setProperty("rechargeAmount", rechargeAmount, PropertyType.TYPE_STRING);
		ksoap2.setProperty("mobileCode", mobileCode, PropertyType.TYPE_STRING);
		ksoap2.setProperty("mobile", mobile, PropertyType.TYPE_STRING);

		return ksoap2.request().getAsString();
	}

	/**
	 * 与银行数据同步
	 * 
	 * @return
	 * @throws BizFailure
	 * @throws ZYException
	 */
	public static GetUserAccountOfBankModel getUserAccountOfBank() throws BizFailure, ZYException {
		SoapProcessor ksoap2 = new SoapProcessor("Service", "getUserAccountOfBank", true);

		JsonElement element = ksoap2.request();
		Gson gson = new GsonBuilder().create();
		return gson.fromJson(element, GetUserAccountOfBankModel.class);
	}

	/**
	 * 获取银行验证码 4充值 5提现
	 */

	public static String getVertifyCodeForBank(String mobile, String smsType) throws BizFailure, ZYException {
		SoapProcessor ksoap2 = new SoapProcessor("Service", "getBankSmsInfo", false);

		ksoap2.setProperty("mobile", mobile, PropertyType.TYPE_STRING);
		ksoap2.setProperty("smsType", smsType, PropertyType.TYPE_STRING);

		return ksoap2.request().getAsString();
	}

	/*
	 * 获取充值记录信息
	 */

	public static RechargeModel rechargeRecord(String firstIdx, String maxCount) throws BizFailure, ZYException {
		SoapProcessor ksoap2 = new SoapProcessor("Service", "rechargeRecord", true);

		ksoap2.setProperty("firstIdx", firstIdx, PropertyType.TYPE_STRING);
		ksoap2.setProperty("maxCount", maxCount, PropertyType.TYPE_STRING);

		JsonElement element = ksoap2.request();
		Gson gson = new GsonBuilder().create();

		
		return gson.fromJson(element, RechargeModel.class);
	}

	/**
	 * 安全中心
	 * 
	 * @return
	 * @throws BizFailure
	 * @throws ZYException
	 */
	public static SecurityCenterModel userSafeCenterDetail() throws BizFailure, ZYException {
		SoapProcessor ksoap2 = new SoapProcessor("Service", "userSafeCenterDetail", true);

		JsonElement element = ksoap2.request();
		Gson gson = new GsonBuilder().create();

		SecurityCenterModel centerModel = gson.fromJson(element, SecurityCenterModel.class);
		return centerModel;
	}

	/**
	 * 交易记录
	 */
	public static List<TradingRecordModel> getTradingRecord(String firstIdx, String maxCount)
			throws BizFailure, ZYException {
		SoapProcessor ksoap2 = new SoapProcessor("Service", "getUsersAccountLogList", true);

		ksoap2.setProperty("firstIdx", firstIdx, PropertyType.TYPE_STRING);
		ksoap2.setProperty("maxCount", maxCount, PropertyType.TYPE_STRING);
		JsonElement element = ksoap2.request();
		Gson gson = new GsonBuilder().create();

		TypeToken<List<TradingRecordModel>> tt = new TypeToken<List<TradingRecordModel>>() {
		};
		List<TradingRecordModel> fs = gson.fromJson(element, tt.getType());
		return fs;
	}

	/**
	 * 获取回款计划列表
	 * 
	 * @param firstIdx
	 * @param maxCount
	 * @return
	 * @throws BizFailure
	 * @throws ZYException
	 */
	public static List<BaseModel> getReturnMoneyPlanList(String firstIdx, String maxCount)
			throws BizFailure, ZYException {
		SoapProcessor ksoap2 = new SoapProcessor("Service", "getRecoverPlanInfo", true);

		ksoap2.setProperty("firstIdx", firstIdx, PropertyType.TYPE_STRING);
		ksoap2.setProperty("maxCount", maxCount, PropertyType.TYPE_STRING);

		JsonElement element = ksoap2.request();
		Gson gson = new GsonBuilder().create();

		TypeToken<List<ReturnMoneyPlanModel>> tt = new TypeToken<List<ReturnMoneyPlanModel>>() {
		};
		List<ReturnMoneyPlanModel> fs = gson.fromJson(element, tt.getType());

		List<BaseModel> bms = new ArrayList<BaseModel>();
		bms.addAll(fs);
		return bms;
	}

	/*
	 * 提现
	 */

	public static String withdraw(String rechargeAmount, String mobileCode, String mobile)
			throws BizFailure, ZYException {
		SoapProcessor ksoap2 = new SoapProcessor("Service", "withDraw", true);

		ksoap2.setProperty("withDrawAmount", rechargeAmount, PropertyType.TYPE_STRING);
		ksoap2.setProperty("mobileCode", mobileCode, PropertyType.TYPE_STRING);
		ksoap2.setProperty("mobile", mobile, PropertyType.TYPE_STRING);

		return ksoap2.request().getAsString();
	}

	/*
	 * 获取提现记录信息
	 */

	public static WithdrawModel withdrawRecord(String firstIdx, String maxCount) throws BizFailure, ZYException {
		SoapProcessor ksoap2 = new SoapProcessor("Service", "withdrawRecord", true);

		ksoap2.setProperty("firstIdx", firstIdx, PropertyType.TYPE_STRING);
		ksoap2.setProperty("maxCount", maxCount, PropertyType.TYPE_STRING);

		JsonElement element = ksoap2.request();
		Gson gson = new GsonBuilder().create();

		return gson.fromJson(element, WithdrawModel.class);
	}

	/**
	 * 取得个人损益合计
	 */
	public static PersonalProfitLossModel getUserProfitAndLosses() throws BizFailure, ZYException {
		SoapProcessor ksoap2 = new SoapProcessor("Service", "userProfitAndLosses", true);
		JsonElement element = ksoap2.request();
		Gson gson = new GsonBuilder().create();
		return gson.fromJson(element, PersonalProfitLossModel.class);

	}

	/*
	 * 获取个人损益 列表
	 */

	public static List<PersonalProfitLossItemModel> getProfitAndLossList(String status, String firstIdx,
			String maxCount) throws BizFailure, ZYException {
		SoapProcessor ksoap2 = new SoapProcessor("Service", "userProfitAndLossesDetail", true);

		ksoap2.setProperty("firstIdx", firstIdx, PropertyType.TYPE_STRING);
		ksoap2.setProperty("maxCount", maxCount, PropertyType.TYPE_STRING);

		JsonElement element = ksoap2.request();
		Gson gson = new GsonBuilder().create();

		List<PersonalProfitLossItemModel> fs = null;

		TypeToken<List<PersonalProfitLossItemModel>> tt = new TypeToken<List<PersonalProfitLossItemModel>>() {
		};
		fs = gson.fromJson(element, tt.getType());

		return fs;
	}

	// 个人基本信息(头像进入)
	public static PersonalInformationDetailModel getPersonalInfoDetail() throws BizFailure, ZYException {
		SoapProcessor ksoap2 = new SoapProcessor("Service", "userInformationDetail", true);

		JsonElement element = ksoap2.request();
		Gson gson = new GsonBuilder().create();
		return gson.fromJson(element, PersonalInformationDetailModel.class);

	}

	/**
	 * 我的红包未使用
	 * 
	 * @param firstIdx
	 * @param maxCount
	 * @return
	 * @throws BizFailure
	 * @throws ZYException
	 */
	public static List<MyRedPacketUnusedModel> getMyRedCouponUnused(String firstIdx, String maxCount)
			throws BizFailure, ZYException {
		SoapProcessor ksoap2 = new SoapProcessor("Service", "getUnusedCoupon", true);

		ksoap2.setProperty("firstIdx", firstIdx, PropertyType.TYPE_STRING);
		ksoap2.setProperty("maxCount", maxCount, PropertyType.TYPE_STRING);

		JsonElement element = ksoap2.request();
		Gson gson = new GsonBuilder().create();

		TypeToken<List<MyRedPacketUnusedModel>> tt = new TypeToken<List<MyRedPacketUnusedModel>>() {
		};
		List<MyRedPacketUnusedModel> fs = gson.fromJson(element, tt.getType());

		return fs;
	}

	/**
	 * 我的红包已使用
	 * 
	 * @param firstIdx
	 * @param maxCount
	 * @return
	 * @throws BizFailure
	 * @throws ZYException
	 */
	public static List<MyRedPacketUsedModel> getMyRedCouponUsed(String firstIdx, String maxCount)
			throws BizFailure, ZYException {
		SoapProcessor ksoap2 = new SoapProcessor("Service", "getUsedCoupon", true);

		ksoap2.setProperty("firstIdx", firstIdx, PropertyType.TYPE_STRING);
		ksoap2.setProperty("maxCount", maxCount, PropertyType.TYPE_STRING);

		JsonElement element = ksoap2.request();
		Gson gson = new GsonBuilder().create();

		TypeToken<List<MyRedPacketUsedModel>> tt = new TypeToken<List<MyRedPacketUsedModel>>() {
		};
		List<MyRedPacketUsedModel> fs = gson.fromJson(element, tt.getType());

		return fs;
	}

	/**
	 * 我的红包已使用
	 * 
	 * @param firstIdx
	 * @param maxCount
	 * @return
	 * @throws BizFailure
	 * @throws ZYException
	 */
	public static List<MyRedPacketOutDateModel> getMyRedCouponOutDate(String firstIdx, String maxCount)
			throws BizFailure, ZYException {
		SoapProcessor ksoap2 = new SoapProcessor("Service", "getExpiredCoupon", true);

		ksoap2.setProperty("firstIdx", firstIdx, PropertyType.TYPE_STRING);
		ksoap2.setProperty("maxCount", maxCount, PropertyType.TYPE_STRING);

		JsonElement element = ksoap2.request();
		Gson gson = new GsonBuilder().create();

		TypeToken<List<MyRedPacketOutDateModel>> tt = new TypeToken<List<MyRedPacketOutDateModel>>() {
		};
		List<MyRedPacketOutDateModel> fs = gson.fromJson(element, tt.getType());

		return fs;
	}

	/**
	 * 我的红包兑现
	 * 
	 * @param firstIdx
	 * @param maxCount
	 * @return
	 * @throws BizFailure
	 * @throws ZYException
	 */
	public static String couponToCash(String redPacketTempletId, String redPacketLogId) throws BizFailure, ZYException {
		SoapProcessor ksoap2 = new SoapProcessor("Service", "couponToCash", true);

		ksoap2.setProperty("redPacketTempletId", redPacketTempletId, PropertyType.TYPE_STRING);
		ksoap2.setProperty("redPacketLogId", redPacketLogId, PropertyType.TYPE_STRING);

		return ksoap2.request().getAsString();
	}

	/**
	 * 我的红包兑换码兑换
	 * 
	 * @param firstIdx
	 * @param maxCount
	 * @return
	 * @throws BizFailure
	 * @throws ZYException
	 */
	public static String couponExchange(String exchangeCode) throws BizFailure, ZYException {
		SoapProcessor ksoap2 = new SoapProcessor("Service", "couponExchange", true);

		ksoap2.setProperty("exchangeCode", exchangeCode, PropertyType.TYPE_STRING);

		return ksoap2.request().getAsString();
	}

	/**
	 * 我的加息券未使用
	 * 
	 * @param firstIdx
	 * @param maxCount
	 * @return
	 * @throws BizFailure
	 * @throws ZYException
	 */
	public static List<MyAddRateCouponUnusedModel> getMyAddCouponUnused(String firstIdx, String maxCount)
			throws BizFailure, ZYException {
		SoapProcessor ksoap2 = new SoapProcessor("Service", "getUnusedInterestRate", true);

		ksoap2.setProperty("firstIdx", firstIdx, PropertyType.TYPE_STRING);
		ksoap2.setProperty("maxCount", maxCount, PropertyType.TYPE_STRING);

		JsonElement element = ksoap2.request();
		Gson gson = new GsonBuilder().create();

		TypeToken<List<MyAddRateCouponUnusedModel>> tt = new TypeToken<List<MyAddRateCouponUnusedModel>>() {
		};
		List<MyAddRateCouponUnusedModel> fs = gson.fromJson(element, tt.getType());

		return fs;
	}

	/**
	 * 我的加息券过期
	 * 
	 * @param firstIdx
	 * @param maxCount
	 * @return
	 * @throws BizFailure
	 * @throws ZYException
	 */
	public static List<MyAddRateCouponOutDateModel> getExpiredInterestRate(String firstIdx, String maxCount)
			throws BizFailure, ZYException {
		SoapProcessor ksoap2 = new SoapProcessor("Service", "getExpiredInterestRate", true);

		ksoap2.setProperty("firstIdx", firstIdx, PropertyType.TYPE_STRING);
		ksoap2.setProperty("maxCount", maxCount, PropertyType.TYPE_STRING);

		JsonElement element = ksoap2.request();
		Gson gson = new GsonBuilder().create();

		TypeToken<List<MyAddRateCouponOutDateModel>> tt = new TypeToken<List<MyAddRateCouponOutDateModel>>() {
		};
		List<MyAddRateCouponOutDateModel> fs = gson.fromJson(element, tt.getType());

		return fs;
	}

	/**
	 * 我的加息券已赠送
	 * 
	 * @param firstIdx
	 * @param maxCount
	 * @return
	 * @throws BizFailure
	 * @throws ZYException
	 */
	public static List<MyAddRateCouponPresentedModel> getLargessedInterestRate(String firstIdx, String maxCount)
			throws BizFailure, ZYException {
		SoapProcessor ksoap2 = new SoapProcessor("Service", "getLargessedInterestRate", true);

		ksoap2.setProperty("firstIdx", firstIdx, PropertyType.TYPE_STRING);
		ksoap2.setProperty("maxCount", maxCount, PropertyType.TYPE_STRING);

		JsonElement element = ksoap2.request();
		Gson gson = new GsonBuilder().create();

		TypeToken<List<MyAddRateCouponPresentedModel>> tt = new TypeToken<List<MyAddRateCouponPresentedModel>>() {
		};
		List<MyAddRateCouponPresentedModel> fs = gson.fromJson(element, tt.getType());

		return fs;
	}

	/**
	 * 我的加息券已使用
	 * 
	 * @param firstIdx
	 * @param maxCount
	 * @return
	 * @throws BizFailure
	 * @throws ZYException
	 */
	public static List<MyAddRateCouponUsedModel> getUsedInterestRate(String firstIdx, String maxCount)
			throws BizFailure, ZYException {
		SoapProcessor ksoap2 = new SoapProcessor("Service", "getUsedInterestRate", true);

		ksoap2.setProperty("firstIdx", firstIdx, PropertyType.TYPE_STRING);
		ksoap2.setProperty("maxCount", maxCount, PropertyType.TYPE_STRING);

		JsonElement element = ksoap2.request();
		Gson gson = new GsonBuilder().create();

		TypeToken<List<MyAddRateCouponUsedModel>> tt = new TypeToken<List<MyAddRateCouponUsedModel>>() {
		};
		List<MyAddRateCouponUsedModel> fs = gson.fromJson(element, tt.getType());

		return fs;
	}

	/**
	 * 我的加息券 验证受赠人信息
	 * 
	 * @param firstIdx
	 * @param maxCount
	 * @return
	 * @throws BizFailure
	 * @throws ZYException
	 */
	public static MyAddRateCouponGiveCheckModel checkDoneeInfo(String doneeMobile) throws BizFailure, ZYException {
		SoapProcessor ksoap2 = new SoapProcessor("Service", "checkDoneeInfo", true);

		ksoap2.setProperty("doneeMobile", doneeMobile, PropertyType.TYPE_STRING);

		JsonElement element = ksoap2.request();
		Gson gson = new GsonBuilder().create();

		return gson.fromJson(element, MyAddRateCouponGiveCheckModel.class);
	}

	/**
	 * 我的加息券 确认赠送
	 * 
	 * @param firstIdx
	 * @param maxCount
	 * @return
	 * @throws BizFailure
	 * @throws ZYException
	 */
	public static String interestRateTransfer(String doneeMobile, String rateCouponSendId)
			throws BizFailure, ZYException {
		SoapProcessor ksoap2 = new SoapProcessor("Service", "interestRateTransfer", true);

		ksoap2.setProperty("doneeMobile", doneeMobile, PropertyType.TYPE_STRING);
		ksoap2.setProperty("rateCouponSendId", rateCouponSendId, PropertyType.TYPE_STRING);

		return ksoap2.request().getAsString();
	}

	// interestRateExchange

	/**
	 * 我的加息券兑换码兑换
	 * 
	 * @param firstIdx
	 * @param maxCount
	 * @return
	 * @throws BizFailure
	 * @throws ZYException
	 */
	public static String interestRateExchange(String exchangeCode) throws BizFailure, ZYException {
		SoapProcessor ksoap2 = new SoapProcessor("Service", "interestRateExchange", true);

		ksoap2.setProperty("exchangeCode", exchangeCode, PropertyType.TYPE_STRING);

		return ksoap2.request().getAsString();
	}

	// 获取消息列表
	public static List<MessageModelList> getMessageList(String firstIdx, String maxCount)
			throws BizFailure, ZYException {
		SoapProcessor ksoap2 = new SoapProcessor("Service", "getMessageCenterList", true);

		ksoap2.setProperty("firstIdx", firstIdx, PropertyType.TYPE_STRING);
		ksoap2.setProperty("maxCount", maxCount, PropertyType.TYPE_STRING);

		JsonElement element = ksoap2.request();
		Gson gson = new GsonBuilder().create();

		TypeToken<List<MessageModelList>> tt = new TypeToken<List<MessageModelList>>() {
		};
		List<MessageModelList> fs = gson.fromJson(element, tt.getType());

		return fs;

	}

	/**
	 * 获取消息详细
	 * 
	 * @return
	 * @throws BizFailure
	 * @throws ZYException
	 */
	public static GetMessageInfoModel getMessageInfo(String messageId) throws BizFailure, ZYException {
		SoapProcessor ksoap2 = new SoapProcessor("Service", "getMessageInfo", false);

		ksoap2.setProperty("messageId", messageId, PropertyType.TYPE_STRING);

		JsonElement element = ksoap2.request();
		Gson gson = new GsonBuilder().create();
		return gson.fromJson(element, GetMessageInfoModel.class);

	}

	// 获取回款详情

	public static BaseModel getPaymentDetails(String tenderId, String tenderType) throws BizFailure, ZYException {
		SoapProcessor ksoap2 = new SoapProcessor("Service", "getPaymentDetails", true);

		ksoap2.setProperty("tenderId", tenderId, PropertyType.TYPE_STRING);
		ksoap2.setProperty("tenderType", tenderType, PropertyType.TYPE_STRING);

		JsonElement element = ksoap2.request();
		Gson gson = new GsonBuilder().create();
		BaseModel baseModel = null;

		if (tenderType.equals("1")) {// 非债权
			baseModel = new ReturnMoneyDetailModelOne();
			baseModel = gson.fromJson(element, ReturnMoneyDetailModelOne.class);
		} else {// 债权
			baseModel = new ReturnMoneyDetailModelTwo();
			baseModel = gson.fromJson(element, ReturnMoneyDetailModelTwo.class);
		}

		return baseModel;
	}

	/**
	 * 获取我的回款详情列表 (包括债权)
	 * 
	 * @param firstIdx
	 * @param maxCount
	 * @return
	 * @throws BizFailure
	 * @throws ZYException
	 */
	public static List<ReturnMoneyDetailListModel> getReturnMoneyDetailList(String tenderId, String tenderType,
			String firstIdx, String maxCount) throws BizFailure, ZYException {
		SoapProcessor ksoap2 = new SoapProcessor("Service", "getPaymentDetailList", true);

		ksoap2.setProperty("tenderId", tenderId, PropertyType.TYPE_STRING);
		ksoap2.setProperty("tenderType", tenderType, PropertyType.TYPE_STRING);
		ksoap2.setProperty("firstIdx", firstIdx, PropertyType.TYPE_STRING);
		ksoap2.setProperty("maxCount", maxCount, PropertyType.TYPE_STRING);

		JsonElement element = ksoap2.request();
		Gson gson = new GsonBuilder().create();

		TypeToken<List<ReturnMoneyDetailListModel>> tt = new TypeToken<List<ReturnMoneyDetailListModel>>() {
		};
		List<ReturnMoneyDetailListModel> fs = gson.fromJson(element, tt.getType());
		return fs;
	}

	/**
	 * 获取转让信息
	 * 
	 * @param oidTenderId
	 * @param tenderFrom
	 * @return
	 * @throws BizFailure
	 * @throws ZYException
	 */
	public static GetUserTransferInfoModel getUserTransferInfo(String oidTenderId, String tenderFrom)
			throws BizFailure, ZYException {
		SoapProcessor ksoap2 = new SoapProcessor("Service", "getUserTransferInfo", true);
		ksoap2.setProperty("oidTenderId", oidTenderId, PropertyType.TYPE_STRING);
		ksoap2.setProperty("tenderFrom", tenderFrom, PropertyType.TYPE_STRING);

		JsonElement element = ksoap2.request();
		Gson gson = new GsonBuilder().create();

		return gson.fromJson(element, GetUserTransferInfoModel.class);
	}

	/**
	 * 转让 计算接口
	 * 
	 * @param oidTenderId
	 * @param tenderFrom
	 * @return
	 * @throws BizFailure
	 * @throws ZYException
	 */
	public static TransferAjaxEventModel transferAjaxEvent(String oidTenderId, String tenderFrom, String transferAmount,
			String discountAmount) throws BizFailure, ZYException {
		SoapProcessor ksoap2 = new SoapProcessor("Service", "transferAjaxEvent", false);
		ksoap2.setProperty("oidTenderId", oidTenderId, PropertyType.TYPE_STRING);
		ksoap2.setProperty("tenderFrom", tenderFrom, PropertyType.TYPE_STRING);
		ksoap2.setProperty("transferAmount", transferAmount, PropertyType.TYPE_STRING);
		ksoap2.setProperty("discountAmount", discountAmount, PropertyType.TYPE_STRING);

		JsonElement element = ksoap2.request();
		Gson gson = new GsonBuilder().create();

		return gson.fromJson(element, TransferAjaxEventModel.class);
	}

	/**
	 * 确认转让
	 * 
	 * @param oidTenderId
	 * @param tenderFrom
	 * @return
	 * @throws BizFailure
	 * @throws ZYException
	 */
	public static String transferBtnClick(String oidTenderId, String tenderFrom, String transferAmount,
			String discountAmount) throws BizFailure, ZYException {
		SoapProcessor ksoap2 = new SoapProcessor("Service", "transferBtnClick", true);

		ksoap2.setProperty("oidTenderId", oidTenderId, PropertyType.TYPE_STRING);
		ksoap2.setProperty("tenderFrom", tenderFrom, PropertyType.TYPE_STRING);
		ksoap2.setProperty("transferAmount", transferAmount, PropertyType.TYPE_STRING);
		ksoap2.setProperty("discountAmount", discountAmount, PropertyType.TYPE_STRING);

		JsonElement element = ksoap2.request();

		return element.getAsString();
	}

	// updateUserSafeCenterDetail
	/**
	 * 更新安全中心
	 * 
	 * @param oidTenderId
	 * @param tenderFrom
	 * @return 1成功 0失败
	 * @throws BizFailure
	 * @throws ZYException
	 */
	public static String updateUserSafeCenterDetail(String province, String city, String address)
			throws BizFailure, ZYException {
		SoapProcessor ksoap2 = new SoapProcessor("Service", "updateUserSafeCenterDetail", true);

		ksoap2.setProperty("province", province, PropertyType.TYPE_STRING);
		ksoap2.setProperty("city", city, PropertyType.TYPE_STRING);
		ksoap2.setProperty("address", address, PropertyType.TYPE_STRING);

		JsonElement element = ksoap2.request();

		return element.getAsString();
	}
	
	//changeLoginPassword
	/**
	 * 修改登录密码
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 * @throws BizFailure
	 * @throws ZYException
	 */
	public static String changeLoginPassword(String oldPassword, String newPassword)
			throws BizFailure, ZYException {
		SoapProcessor ksoap2 = new SoapProcessor("Service", "changeLoginPassword", true);

		ksoap2.setProperty("oldPassword", oldPassword, PropertyType.TYPE_STRING);
		ksoap2.setProperty("newPassword", newPassword, PropertyType.TYPE_STRING);

		JsonElement element = ksoap2.request();

		return element.getAsString();
	}
	
	/**
	 * 修改支付密码
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 * @throws BizFailure
	 * @throws ZYException
	 */
	public static String changePayPassword(String oldPassword, String newPassword)
			throws BizFailure, ZYException {
		SoapProcessor ksoap2 = new SoapProcessor("Service", "changePayPassword", true);

		ksoap2.setProperty("oldPassword", oldPassword, PropertyType.TYPE_STRING);
		ksoap2.setProperty("newPassword", newPassword, PropertyType.TYPE_STRING);

		JsonElement element = ksoap2.request();

		return element.getAsString();
	}
	
	/**
	 * 查看投资合同
	 * @param tenderId
	 * @return
	 * @throws BizFailure
	 * @throws ZYException
	 */
	public static InvestContractModel showProductsInfo(String tenderId)
			throws BizFailure, ZYException {
		SoapProcessor ksoap2 = new SoapProcessor("Service", "showProductsInfo", false);

		ksoap2.setProperty("key", tenderId, PropertyType.TYPE_STRING);

		JsonElement element = ksoap2.request();
		Gson gson = new GsonBuilder().create();

		return gson.fromJson(element, InvestContractModel.class);
	}
	
	
	/**
	 * 重置支付密码  获取短信验证吗
	 */

	public static String getMobileCode(String mobile) throws BizFailure, ZYException {
		SoapProcessor ksoap2 = new SoapProcessor("Service", "getMobileCode", false);

		ksoap2.setProperty("mobile", mobile, PropertyType.TYPE_STRING);

		return ksoap2.request().getAsString();
	}
	
	
	
	/**
	 * 重置支付密码  确认提交验证码下一步
	 * @param mobile
	 * @return
	 * @throws BizFailure
	 * @throws ZYException
	 */
	public static String findPassword(String mobile, String verifyCode) throws BizFailure, ZYException {
		SoapProcessor ksoap2 = new SoapProcessor("Service", "findPassword", false);

		ksoap2.setProperty("mobile", mobile, PropertyType.TYPE_STRING);
		ksoap2.setProperty("verifyCode", verifyCode, PropertyType.TYPE_STRING);

		return ksoap2.request().getAsString();
	}
	
	//modifyPassword
	/**
	 * 重置登录密码  确认
	 * @param mobile
	 * @return
	 * @throws BizFailure
	 * @throws ZYException
	 */
	public static String modifyPassword(String mobile, String pwdType, String newPassword) throws BizFailure, ZYException {
		SoapProcessor ksoap2 = new SoapProcessor("Service", "modifyPassword", true);

		ksoap2.setProperty("mobile", mobile, PropertyType.TYPE_STRING);
		ksoap2.setProperty("pwdType", pwdType, PropertyType.TYPE_STRING);
		ksoap2.setProperty("newPassword", newPassword, PropertyType.TYPE_STRING);

		return ksoap2.request().getAsString();
	}

	// 邀请好友信息
		public static InviteInfoModel getInviteInfo() throws BizFailure, ZYException {
			SoapProcessor ksoap2 = new SoapProcessor("Service", "inviteInfo", true);

			JsonElement element = ksoap2.request();
			Gson gson = new GsonBuilder().create();
			return gson.fromJson(element, InviteInfoModel.class);

		}

	
	/*********************** 以上接口是调试完毕的 ***********************************************/

	
	/**
	 * 修改手机号码的获取验证码 又两个验证码 修改界面
	 */

	public static String getVertifyCodeForModifyPhone(String mobile) throws BizFailure, ZYException {
		SoapProcessor ksoap2 = new SoapProcessor("Service", "getVertifyCodeForModifyPhone", false);

		ksoap2.setProperty("newPhoneNumber", mobile, PropertyType.TYPE_STRING);

		return ksoap2.request().getAsString();
	}

	/**
	 * 修改手机号
	 */

	public static void modifyPhoneNumber(String newPhoneNumber, String verifyCode) throws BizFailure, ZYException {
		SoapProcessor ksoap2 = new SoapProcessor("Service", "modifyPhoneNumber", true);

		ksoap2.setProperty("newPhoneNumber", newPhoneNumber, PropertyType.TYPE_STRING);
		// ksoap2.setProperty("verifyCode", verifyCode,
		// PropertyType.TYPE_STRING);
		ksoap2.request();
		// return ksoap2.request().getAsString();
	}

	/***
	 * 用户基本信息修改
	 * @param birthday
	 * @param sex
	 * @param education
	 * @param qq
	 * @param marriage
	 * @param hangye
	 * @param income
	 * @return
	 * @throws BizFailure
	 * @throws ZYException
	 */
	public static String save( String birthday, String sex,  String education, String qq, String marriage, String hangye, String income)
			throws BizFailure, ZYException {
		SoapProcessor ksoap2 = new SoapProcessor("Service", "updateUserInformationDetail", true);

		ksoap2.setProperty("shouru", income, PropertyType.TYPE_STRING);
		ksoap2.setProperty("birthday", birthday, PropertyType.TYPE_STRING);
		ksoap2.setProperty("xueli", education, PropertyType.TYPE_STRING);
		ksoap2.setProperty("hangye", hangye, PropertyType.TYPE_STRING);
		ksoap2.setProperty("sex", sex, PropertyType.TYPE_STRING);
		ksoap2.setProperty("hunying", marriage, PropertyType.TYPE_STRING);
		ksoap2.setProperty("qq", qq, PropertyType.TYPE_STRING);

		JsonElement element = ksoap2.request();
		return element.getAsString();
	}
	
	
	public static String getUserPic(String fileName,String image)
			throws BizFailure, ZYException {
		SoapProcessor ksoap2 = new SoapProcessor("Service", "getUserPic", true);

		ksoap2.setProperty("filnaeme", fileName, PropertyType.TYPE_STRING);
		ksoap2.setProperty("image", image, PropertyType.TYPE_STRING);

		JsonElement element = ksoap2.request();
		return element.getAsString();
	}
}
