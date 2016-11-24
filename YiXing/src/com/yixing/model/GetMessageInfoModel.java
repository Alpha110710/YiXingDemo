package com.yixing.model;

import com.google.gson.annotations.SerializedName;

public class GetMessageInfoModel extends BaseModel{
	 /**
     * TITLE : 登录获得积分
     * MSG_CONTENT : 用户登录获得积分【2】
     */
		@SerializedName("TITLE")
        private String TITLE;
		
		@SerializedName("MSG_CONTENT")
        private String MSG_CONTENT;

        public String getTITLE() {
            return TITLE;
        }

        public void setTITLE(String TITLE) {
            this.TITLE = TITLE;
        }

        public String getMSG_CONTENT() {
            return MSG_CONTENT;
        }

        public void setMSG_CONTENT(String MSG_CONTENT) {
            this.MSG_CONTENT = MSG_CONTENT;
        }

}
