package com.yixing.model;

import com.google.gson.annotations.SerializedName;

public class MyRedPacketUnusedModel extends BaseModel{
	
	 /**
     * CASH_FLG : 0
     * OVERPLUS_AMOUNT : 50元
     * END_DATE : 无期限
     * RULE : <div style='background:#EAEAEA;height:100%;'><div style='color:#fc7905;font-size: 40px;text-align: left;margin-left: 10px;'>红包使用规则</div><div style='font-size: 25px;border: solid 2px #F3F3F3;border-radius: 10px;background:#FFFFFF;'> <p style='margin-left: 10px'>红包金额：50.00元</p> <p style='margin-left: 10px'>红包使用期限至：无期限</p><p style='margin-left: 10px'>红包是否可以兑现：不可以</p><p style='margin-left: 10px'>最低投资金额：0.00元</p><p style='margin-left: 10px'>此红包使用产品：测试001、满标（当日起息全）、日12次日提前、New追加产品002、月计息12、金二代月标-L1、理财产品A001、日123月1245、日123月12345、日12次日提前流转2、日1月3、即投即起息还款测试、新标测试、1019test01、月计息12345、日计息23、一个名字很长很长特别长的产品、New追加产品003、产品（当日起息全）、日13月35、金二代理财007、月计息1234、小李专享、平台-即投即起息、New追加产品004、用来提前还款的、平台融资产品A001、日计息12、一诺抵押、ccc、aaa、bbbbbb、日12次日提前流转、开发测试非流转标、次日366前满、资金流转产品A01、金二代测试产品-zx、日计息12顺延、8.17新产品001、月4次日提前、New追加产品001、产品名称0624、转让测试、</p><p style='margin-left: 10px'>红包仅可使用一次</p></div></div>
     * RED_PACKET_TEMPLET_ID : a4f9bda8b9a04ea38e4ff141affb74e9
     * RED_PACKET_LOG_ID : 4403
     */

		@SerializedName("CASH_FLG")
        private String CASH_FLG;
		
		@SerializedName("OVERPLUS_AMOUNT")
        private String OVERPLUS_AMOUNT;
		
		@SerializedName("END_DATE")
        private String END_DATE;
		
		@SerializedName("RULE")
        private String RULE;
		
		@SerializedName("RED_PACKET_TEMPLET_ID")
        private String RED_PACKET_TEMPLET_ID;
		
		@SerializedName("RED_PACKET_LOG_ID")
        private String RED_PACKET_LOG_ID;

        public String getCASH_FLG() {
            return CASH_FLG;
        }

        public void setCASH_FLG(String CASH_FLG) {
            this.CASH_FLG = CASH_FLG;
        }

        public String getOVERPLUS_AMOUNT() {
            return OVERPLUS_AMOUNT;
        }

        public void setOVERPLUS_AMOUNT(String OVERPLUS_AMOUNT) {
            this.OVERPLUS_AMOUNT = OVERPLUS_AMOUNT;
        }

        public String getEND_DATE() {
            return END_DATE;
        }

        public void setEND_DATE(String END_DATE) {
            this.END_DATE = END_DATE;
        }

        public String getRULE() {
            return RULE;
        }

        public void setRULE(String RULE) {
            this.RULE = RULE;
        }

        public String getRED_PACKET_TEMPLET_ID() {
            return RED_PACKET_TEMPLET_ID;
        }

        public void setRED_PACKET_TEMPLET_ID(String RED_PACKET_TEMPLET_ID) {
            this.RED_PACKET_TEMPLET_ID = RED_PACKET_TEMPLET_ID;
        }

        public String getRED_PACKET_LOG_ID() {
            return RED_PACKET_LOG_ID;
        }

        public void setRED_PACKET_LOG_ID(String RED_PACKET_LOG_ID) {
            this.RED_PACKET_LOG_ID = RED_PACKET_LOG_ID;
        }

}
