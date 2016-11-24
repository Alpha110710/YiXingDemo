package com.yixing.biz;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.yixing.biz.SoapProcessor.PropertyType;
import com.yixing.biz.exception.BizFailure;
import com.yixing.biz.exception.ZYException;
import com.yixing.model.NewNoticeModel;

public class MoreBiz extends BaseBiz {

	//获取最新公告
	public static List<NewNoticeModel> getNewNoticeList(int pageIndex,
			int pageCount) throws BizFailure, ZYException {
		SoapProcessor ksoap2 = new SoapProcessor("Service",
				"getNoticeList", false);

		ksoap2.setProperty("firstIdx", pageIndex + "", PropertyType.TYPE_STRING);
		ksoap2.setProperty("maxCount", pageCount+"", PropertyType.TYPE_STRING);

		JsonElement element = ksoap2.request();

		Gson gson = new GsonBuilder().create();

		TypeToken<List<NewNoticeModel>> tt = new TypeToken<List<NewNoticeModel>>() {
		};

		List<NewNoticeModel> messageItems = gson.fromJson(element, tt.getType());

		return messageItems;

	}
	
	
	
	
}