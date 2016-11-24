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
import com.yixing.model.RedPacketModel;

public class RedPacketBiz extends BaseBiz{
	
	
	/***
	 * 
	 * @param status (1已使用，2未使用，3已过期)
	 * @param money (正常获取借口不需要传递这个参数，在投资获取红包时需要传递)
	 * @return
	 * @throws BizFailure
	 * @throws ZYException
	 */
	public static List<BaseModel> getRedPacket(String status,String money)
			throws BizFailure, ZYException {
		SoapProcessor ksoap2 = new SoapProcessor("Service", "getCouponListForTender", true);

		//接口需要的参数
		ksoap2.setProperty("status", status, PropertyType.TYPE_STRING);
		if(!"".equals(money))
			ksoap2.setProperty("money", money, PropertyType.TYPE_STRING);
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
	
}
