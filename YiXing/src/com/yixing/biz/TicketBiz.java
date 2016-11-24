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
import com.yixing.model.InvestmentModel;
import com.yixing.model.TicketModel;

public class TicketBiz extends BaseBiz{
	/**
	 * 加息券
	 * @throws BizFailure
	 * @throws ZYException
	 */
	
	public static List<BaseModel> getTicket(String productId)
			throws BizFailure, ZYException {
		SoapProcessor ksoap2 = new SoapProcessor("Service", "getInterestRateListForTender", true);//命名空间,方法名，是否需要token

		//接口需要的参数
		ksoap2.setProperty("oidPlatformProductsId", productId,
				PropertyType.TYPE_STRING);
		
		JsonElement element = ksoap2.request();
		Gson gson = new GsonBuilder().create();
		List<BaseModel> fs = null;
			TypeToken<List<TicketModel>> tt = new TypeToken<List<TicketModel>>() {
			};
			fs = gson.fromJson(element, tt.getType());
		List<BaseModel> bms = new ArrayList<BaseModel>();
		bms.addAll(fs);
		return bms;
	}
	
}
