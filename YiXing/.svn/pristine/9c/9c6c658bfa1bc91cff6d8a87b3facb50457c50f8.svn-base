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
import com.yixing.model.AlllMoneyModel;
import com.yixing.model.BaseModel;
import com.yixing.model.HomePageModel;
import com.yixing.model.InvestmentControlModel;
import com.yixing.model.InvestmentDetaiTwolModel;
import com.yixing.model.InvestmentDetailModel;
import com.yixing.model.InvestmentMessageModel;
import com.yixing.model.InvestmentModel;
import com.yixing.model.InvestmentMoreModel;
import com.yixing.model.InvestmentZQModel;
import com.yixing.model.RedPacketModel;
import com.yixing.model.ZQDetailModel;
import com.yixing.model.ZQIncomeModel;
import com.yixing.model.ZQItemModel;

public class InvestmentBiz extends BaseBiz {

	/***
	 * 
	 * @param status
	 *            (1投资理财A,2投资理财B，3E兴车贷)
	 * @param firstIdx
	 *            （获取第几页的数据）
	 * @param maxCount
	 *            每一页获取的最大item数量 type 理财a 理财b e兴车贷 productType
	 *            0全部,1e兴房贷，2公益贷，3信用贷，4政府银行保荐，5担保贷 rateType //1 5%一下，2 6%~10%,3
	 *            e 10%以上，0全部
	 * @return
	 * @throws BizFailure
	 * @throws ZYException
	 */
	public static List<BaseModel> myInvestment(String status, String firstIdx,
			String maxCount, String productType, String rateType)
			throws BizFailure, ZYException {
		SoapProcessor ksoap2 = new SoapProcessor("Service",
				"getMyInvestmentInfo", false);// 命名空间,方法名，是否需要token

		// 接口需要的参数
		ksoap2.setProperty("tabFlg", status, PropertyType.TYPE_STRING);
		if (rateType.equals("1"))
			ksoap2.setProperty("productRate", "Min5", PropertyType.TYPE_STRING);
		else if (rateType.equals("2"))
			ksoap2.setProperty("productRate", "6-10", PropertyType.TYPE_STRING);
		else if (rateType.equals("3"))
			ksoap2.setProperty("productRate", "Max11", PropertyType.TYPE_STRING);
		else if (rateType.equals("0"))
			ksoap2.setProperty("productRate", "", PropertyType.TYPE_STRING);

		ksoap2.setProperty("firstIdx", firstIdx, PropertyType.TYPE_STRING);
		ksoap2.setProperty("maxCount", maxCount, PropertyType.TYPE_STRING);
		ksoap2.setProperty("productTypeFlg", productType,
				PropertyType.TYPE_STRING);
		JsonElement element = ksoap2.request();
		Gson gson = new GsonBuilder().create();
		List<BaseModel> fs = null;
		if (status.equals("1")) {
			TypeToken<List<InvestmentModel>> tt = new TypeToken<List<InvestmentModel>>() {
			};
			fs = gson.fromJson(element, tt.getType());
		} else if (status.equals("2")) {
			TypeToken<List<InvestmentModel>> tt = new TypeToken<List<InvestmentModel>>() {
			};
			fs = gson.fromJson(element, tt.getType());
		} else if (status.equals("3")) {
			TypeToken<List<InvestmentModel>> tt = new TypeToken<List<InvestmentModel>>() {
			};
			fs = gson.fromJson(element, tt.getType());
		}
		List<BaseModel> bms = new ArrayList<BaseModel>();
		bms.addAll(fs);
		return bms;
	}

	/***
	 * 
	 * @param productId
	 *            投资专区标 详细 理财A e兴车贷
	 * @return
	 * @throws BizFailure
	 * @throws ZYException
	 */
	public static InvestmentDetailModel getData(String productId)
			throws BizFailure, ZYException {
		SoapProcessor ksoap2 = new SoapProcessor("Service", "getTenderInfo",
				false);
		// 接口需要的参数
		ksoap2.setProperty("borrowId", productId, PropertyType.TYPE_STRING);

		JsonElement element = ksoap2.request();
		Gson gson = new GsonBuilder().create();
		return gson.fromJson(element, InvestmentDetailModel.class);
	}

	/***
	 * 
	 * 投资专区里面的更多
	 * 
	 * @param firstIdx
	 *            （获取第几页的数据）
	 * @param maxCount
	 *            每一页获取的最大item数量
	 * @return
	 * @throws BizFailure
	 * @throws ZYException
	 */
	public static List<BaseModel> investmentMore(String productId,String firstIdx,
			String maxCount) throws BizFailure, ZYException {
		SoapProcessor ksoap2 = new SoapProcessor("Service", "getInvestors", false);// 命名空间,方法名，是否需要token
		// 接口需要的参数
		ksoap2.setProperty("borrowId", productId, PropertyType.TYPE_STRING);
		ksoap2.setProperty("firstIdx", firstIdx, PropertyType.TYPE_STRING);
		ksoap2.setProperty("maxCount", maxCount, PropertyType.TYPE_STRING);

		JsonElement element = ksoap2.request();
		Gson gson = new GsonBuilder().create();
		List<BaseModel> fs = null;
		TypeToken<List<InvestmentMoreModel>> tt = new TypeToken<List<InvestmentMoreModel>>() {
		};
		fs = gson.fromJson(element, tt.getType());
		List<BaseModel> bms = new ArrayList<BaseModel>();
		bms.addAll(fs);
		return bms;
	}

	/***
	 * 
	 * @param productId
	 *            风控措施
	 * @return
	 * @throws BizFailure
	 * @throws ZYException
	 */
	public static InvestmentControlModel getControlData(String productId)
			throws BizFailure, ZYException {
		SoapProcessor ksoap2 = new SoapProcessor("Service",
				"getMaterialDescription", false);
		// 接口需要的参数
		ksoap2.setProperty("productsId", productId, PropertyType.TYPE_STRING);
		JsonElement element = ksoap2.request();
		Gson gson = new GsonBuilder().create();
		return gson.fromJson(element, InvestmentControlModel.class);
	}

	/***
	 * 
	 * @param productId
	 *            项目信息
	 * @return
	 * @throws BizFailure
	 * @throws ZYException
	 */
	public static InvestmentMessageModel getMessageData(String productId)
			throws BizFailure, ZYException {
		SoapProcessor ksoap2 = new SoapProcessor("Service",
				"getInvestmentInfo", false);
		// 接口需要的参数
		ksoap2.setProperty("productsId", productId, PropertyType.TYPE_STRING);

		JsonElement element = ksoap2.request();
		Gson gson = new GsonBuilder().create();
		return gson.fromJson(element, InvestmentMessageModel.class);
	}

	/***
	 * 
	 * @param productId
	 *            InvestmentDetailTwoActivity页面） 获取页面对应的值
	 * @return
	 * @throws BizFailure
	 * @throws ZYException
	 */
	public static InvestmentDetaiTwolModel getDataTwo(String productId)
			throws BizFailure, ZYException {
		SoapProcessor ksoap2 = new SoapProcessor("Service",
				"getTenderInfoBorrow", true);// 需要token
		// 接口需要的参数
		ksoap2.setProperty("borrowId", productId, PropertyType.TYPE_STRING);

		JsonElement element = ksoap2.request();
		Gson gson = new GsonBuilder().create();
		return gson.fromJson(element, InvestmentDetaiTwolModel.class);
	}

	/***
	 * 
	 * @param productId
	 *            （InvestmentDetailTwoActivity页面） 获取收益
	 * @return
	 * @throws BizFailure
	 * @throws ZYException
	 */
	public static String getInCome(String productId, String money,String ticket)
			throws BizFailure, ZYException {
		SoapProcessor ksoap2 = new SoapProcessor("Service",
				"getTenderBorrowMoney", true);// 需要token
		// 接口需要的参数
		ksoap2.setProperty("borrowId", productId, PropertyType.TYPE_STRING);
		ksoap2.setProperty("tenderMoney", money, PropertyType.TYPE_STRING);
		//TODO  带修改  加息全字段
		ksoap2.setProperty("rateAmountTo", ticket, PropertyType.TYPE_STRING);

		return ksoap2.request().getAsString();
	}
	
	/***
	 * 
	 * @param productId
	 *            （InvestmentDetailTwoActivity页面）权额投资
	 * @return
	 * @throws BizFailure
	 * @throws ZYException
	 */
	public static AlllMoneyModel getAllMoney(String procdutId,String tickeId)
			throws BizFailure, ZYException {
		SoapProcessor ksoap2 = new SoapProcessor("Service",
				"allTender", true);// 需要token
		// 接口需要的参数
		ksoap2.setProperty("productsId", procdutId, PropertyType.TYPE_STRING);
		ksoap2.setProperty("rateAmountTo", tickeId, PropertyType.TYPE_STRING);

		JsonElement element = ksoap2.request();
		Gson gson = new GsonBuilder().create();
		return gson.fromJson(element, AlllMoneyModel.class);
	}

	/***
	 * 
	 * @param productId
	 *            立即投资（InvestmentDetailTwoActivity页面）
	 * @return redPacketAmount：红包金额 redPackIdStr：红包ID password交易密码
	 *         rateAmountTo：加息券金额 lblRateCouponSendId：加息券ID directionalPwd：定向密码
	 *         productId：标ID
	 * @throws BizFailure
	 * @throws ZYException
	 */
	
	
	public static String promptlyInvestment(String redId, String redMoney,
			String ticketId, String ticketMoney, String money, String password,
			String dxPassword, String productId,String verifyCode) throws BizFailure, ZYException {
		SoapProcessor ksoap2 = new SoapProcessor("Service","bidding", true);// 需要token
		// 接口需要的参数
		ksoap2.setProperty("tenderAccount", money, PropertyType.TYPE_STRING);
		ksoap2.setProperty("payPassword", password, PropertyType.TYPE_STRING);
		ksoap2.setProperty("verifyCode", verifyCode, PropertyType.TYPE_STRING);
		ksoap2.setProperty("redPacketAmount", redMoney,PropertyType.TYPE_STRING);
		ksoap2.setProperty("redPackIdStr", redId, PropertyType.TYPE_STRING);
		ksoap2.setProperty("rateAmountTo", ticketMoney,PropertyType.TYPE_STRING);
		ksoap2.setProperty("lblRateCouponSendId", ticketId,PropertyType.TYPE_STRING);
		ksoap2.setProperty("directionalPwd", dxPassword,PropertyType.TYPE_STRING);
		ksoap2.setProperty("productId", productId, PropertyType.TYPE_STRING);
		return ksoap2.request().getAsString();
	}

	/***
	 * 
	 * 获取投资理财B详细页面数据
	 * 
	 * @return
	 * @throws BizFailure
	 * @throws ZYException
	 */
	public static BaseModel getDataList(String transferId,String productId) throws BizFailure,
			ZYException {
		SoapProcessor ksoap2 = new SoapProcessor("Service", "getTransferInfo", false);// 命名空间,方法名，是否需要token
		// 接口需要的参数
		ksoap2.setProperty("transferId", transferId, PropertyType.TYPE_STRING);
		ksoap2.setProperty("oidPlatformProductsId", productId, PropertyType.TYPE_STRING);
		
		JsonElement element = ksoap2.request();
		Gson gson = new GsonBuilder().create();
		return gson.fromJson(element, ZQDetailModel.class);
	}

	/***
	 * 
	 * @param productId
	 *            理财投资b立即购买页面） 获取页面对应的值
	 * @return
	 * @throws BizFailure
	 * @throws ZYException
	 */
	public static InvestmentZQModel getZQData(String transferId)
			throws BizFailure, ZYException {
		SoapProcessor ksoap2 = new SoapProcessor("Service",
				"getTransferBorrow", true);// 需要token
		// 接口需要的参数
		ksoap2.setProperty("borrowId", transferId, PropertyType.TYPE_STRING);

		JsonElement element = ksoap2.request();
		Gson gson = new GsonBuilder().create();
		return gson.fromJson(element, InvestmentZQModel.class);
	}

	/***
	 * 
	 * @return
	 * @throws BizFailure
	 * @throws ZYException
	 */
	public static List<BaseModel> getRedPacket(String productId)
			throws BizFailure, ZYException {
		SoapProcessor ksoap2 = new SoapProcessor("Service",
				"getCouponListForTender", true);

		// 接口需要的参数
		ksoap2.setProperty("oidPlatformProductsId", productId,
				PropertyType.TYPE_STRING);
		JsonElement element = ksoap2.request();
		Gson gson = new GsonBuilder().create();
		List<BaseModel> fs = null;
		TypeToken<List<RedPacketModel>> tt = new TypeToken<List<RedPacketModel>>() {
		};
		fs = gson.fromJson(element, tt.getType());
		List<BaseModel> bms = new ArrayList<BaseModel>();
		bms.addAll(fs);
		return bms;
	}
	/**
	 * 立即投资（获取短信验证码）
	 */

	public static String getVertifyCodeForModifyPhone(String mobile) throws BizFailure, ZYException {
		SoapProcessor ksoap2 = new SoapProcessor("Service", "getBankSmsInfo", false);

		ksoap2.setProperty("mobile", mobile, PropertyType.TYPE_STRING);
		ksoap2.setProperty("smsType", "2", PropertyType.TYPE_STRING);

		return ksoap2.request().getAsString();
	}
	/***
	 * 
	 *            
	 * @return 立即投资前用于check判断
	 * @throws BizFailure
	 * @throws ZYException
	 */
	public static String realNameAuth()
			throws BizFailure, ZYException {
		SoapProcessor ksoap2 = new SoapProcessor("Service",
				"realNameAuth", true);// 需要token

		return ksoap2.request().getAsString();
	}
	/***
	 * 
	 * 理财b获取投资人list
	 * 
	 * @param firstIdx
	 *            （获取第几页的数据）
	 * @param maxCount
	 *            每一页获取的最大item数量
	 * @return
	 * @throws BizFailure
	 * @throws ZYException
	 */
	public static List<BaseModel> getZQDataList(String productId,String firstIdx,
			String maxCount) throws BizFailure, ZYException {
		SoapProcessor ksoap2 = new SoapProcessor("Service", "getTransferInvestors", false);// 命名空间,方法名，是否需要token
		// 接口需要的参数
		ksoap2.setProperty("transferId", productId, PropertyType.TYPE_STRING);
		ksoap2.setProperty("firstIdx", firstIdx, PropertyType.TYPE_STRING);
		ksoap2.setProperty("maxCount", maxCount, PropertyType.TYPE_STRING);
		
		JsonElement element = ksoap2.request();
		Gson gson = new GsonBuilder().create();
		List<BaseModel> fs = null;
		TypeToken<List<ZQItemModel>> tt = new TypeToken<List<ZQItemModel>>() {
		};
		fs = gson.fromJson(element, tt.getType());
		List<BaseModel> bms = new ArrayList<BaseModel>();
		bms.addAll(fs);
		return bms;
	}
	
	/***
	 * 
	 * @param productId
	 *            立即投资（ZQBUYActivity页面）
	 * @return money  金额 password 交易密码 productId 债权标的id verifyCode 验证码
	 *        
	 *         productId：标ID
	 * @throws BizFailure
	 * @throws ZYException
	 */
	
	
	public static String investmentZQ(String money, String password,
		 String productId,String verifyCode) throws BizFailure, ZYException {
		SoapProcessor ksoap2 = new SoapProcessor("Service","biddingForTransfer", true);// 需要token
		// 接口需要的参数
		ksoap2.setProperty("transferId", productId, PropertyType.TYPE_STRING);
		ksoap2.setProperty("payPassword", password, PropertyType.TYPE_STRING);
		ksoap2.setProperty("transferAmount", money, PropertyType.TYPE_STRING);
		ksoap2.setProperty("smsInfo", verifyCode, PropertyType.TYPE_STRING);
		return ksoap2.request().getAsString();
	}
	
	/***
	 * 
	 * @param productId
	 *            （ZQBUYActivity页面） 获取收益
	 * @return
	 * @throws BizFailure
	 * @throws ZYException
	 */
	public static ZQIncomeModel getZQInCome(String productId, String money)
			throws BizFailure, ZYException {
		SoapProcessor ksoap2 = new SoapProcessor("Service",
				"getInterestTotalForTransfer", true);// 需要token
		// 接口需要的参数
		ksoap2.setProperty("transferId", productId, PropertyType.TYPE_STRING);
		ksoap2.setProperty("transferAmount", money, PropertyType.TYPE_STRING);

		JsonElement element = ksoap2.request();
		Gson gson = new GsonBuilder().create();
		return gson.fromJson(element, ZQIncomeModel.class);
	}
	/***
	 * 
	 * @param productId
	 *            （ZQBUYActivity页面）权额投资
	 * @return
	 * @throws BizFailure
	 * @throws ZYException
	 */
	public static ZQIncomeModel getZQAllMoney(String procdutId)
			throws BizFailure, ZYException {
		SoapProcessor ksoap2 = new SoapProcessor("Service",
				"allTenderForTransfer", true);// 需要token
		// 接口需要的参数
		ksoap2.setProperty("transferId", procdutId, PropertyType.TYPE_STRING);

		JsonElement element = ksoap2.request();
		Gson gson = new GsonBuilder().create();
		return gson.fromJson(element, ZQIncomeModel.class);
	}


}












