package com.yixing.model;

import com.google.gson.annotations.SerializedName;

public class MessageModelList extends BaseModel{
	
	/**
     * ID : 83032
     * SENDER : system
     * RECEIVER : 03b3c03c010c4ba98733242455348795
     * SUBJECT : 冻结投资金额
     * CONTENTS : 您对融资产品e兴房贷-投资理财计划0830的预投成功，账户冻结投资金额。金额：100.00元
     * REPLY_FLG : 0
     * OPEN_FLG : 0
     * DEL_FLG : 0
     * INS_DATE : 2016-08-31 11:23:07.0
     * UPD_DATE : 2016-08-31 11:23:07.0
     * GROUP_NAME : 平台组
     * UD_USER_NAME : null
     * OD_USER_NAME : null
     * MAIL_STATUS : 未读
     * TITLE : 冻结投资金额
     */

	@SerializedName("ID")
    private String ID;
	
	@SerializedName("SENDER")
    private String SENDER;
	
	@SerializedName("RECEIVER")
    private String RECEIVER;
	
	@SerializedName("SUBJECT")
    private String SUBJECT;
	
	@SerializedName("CONTENTS")
    private String CONTENTS;
	
	@SerializedName("REPLY_FLG")
    private String REPLY_FLG;
	
	@SerializedName("OPEN_FLG")
    private String OPEN_FLG;
	
	@SerializedName("DEL_FLG")
    private String DEL_FLG;
	
	@SerializedName("INS_DATE")
    private String INS_DATE;
	
	@SerializedName("UPD_DATE")
    private String UPD_DATE;
	
	@SerializedName("GROUP_NAME")
    private String GROUP_NAME;
	
	@SerializedName("UD_USER_NAME")
    private Object UD_USER_NAME;
	
	@SerializedName("OD_USER_NAME")
    private Object OD_USER_NAME;
	
	@SerializedName("MAIL_STATUS")
    private String MAIL_STATUS;
	
	@SerializedName("TITLE")
    private String TITLE;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getSENDER() {
        return SENDER;
    }

    public void setSENDER(String SENDER) {
        this.SENDER = SENDER;
    }

    public String getRECEIVER() {
        return RECEIVER;
    }

    public void setRECEIVER(String RECEIVER) {
        this.RECEIVER = RECEIVER;
    }

    public String getSUBJECT() {
        return SUBJECT;
    }

    public void setSUBJECT(String SUBJECT) {
        this.SUBJECT = SUBJECT;
    }

    public String getCONTENTS() {
        return CONTENTS;
    }

    public void setCONTENTS(String CONTENTS) {
        this.CONTENTS = CONTENTS;
    }

    public String getREPLY_FLG() {
        return REPLY_FLG;
    }

    public void setREPLY_FLG(String REPLY_FLG) {
        this.REPLY_FLG = REPLY_FLG;
    }

    public String getOPEN_FLG() {
        return OPEN_FLG;
    }

    public void setOPEN_FLG(String OPEN_FLG) {
        this.OPEN_FLG = OPEN_FLG;
    }

    public String getDEL_FLG() {
        return DEL_FLG;
    }

    public void setDEL_FLG(String DEL_FLG) {
        this.DEL_FLG = DEL_FLG;
    }

    public String getINS_DATE() {
        return INS_DATE;
    }

    public void setINS_DATE(String INS_DATE) {
        this.INS_DATE = INS_DATE;
    }

    public String getUPD_DATE() {
        return UPD_DATE;
    }

    public void setUPD_DATE(String UPD_DATE) {
        this.UPD_DATE = UPD_DATE;
    }

    public String getGROUP_NAME() {
        return GROUP_NAME;
    }

    public void setGROUP_NAME(String GROUP_NAME) {
        this.GROUP_NAME = GROUP_NAME;
    }

    public Object getUD_USER_NAME() {
        return UD_USER_NAME;
    }

    public void setUD_USER_NAME(Object UD_USER_NAME) {
        this.UD_USER_NAME = UD_USER_NAME;
    }

    public Object getOD_USER_NAME() {
        return OD_USER_NAME;
    }

    public void setOD_USER_NAME(Object OD_USER_NAME) {
        this.OD_USER_NAME = OD_USER_NAME;
    }

    public String getMAIL_STATUS() {
        return MAIL_STATUS;
    }

    public void setMAIL_STATUS(String MAIL_STATUS) {
        this.MAIL_STATUS = MAIL_STATUS;
    }

    public String getTITLE() {
        return TITLE;
    }

    public void setTITLE(String TITLE) {
        this.TITLE = TITLE;
    }
	

}
