package com.yixing.model;

import com.google.gson.annotations.SerializedName;
import com.yixing.utils.android.QuickSetParcelableUtil;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class MyInvestHoldModel extends BaseModel {

    /**
     * OID_TENDER_ID : f2c1f11f0bf749d3ab091223ecefdcd0
     * TRANSFER_CONTRACT_ID :
     * OID_PLATFORM_PRODUCTS_ID : 349a14065d3b46d1bd1130822af8aac3
     * OID_USER_ID : 03b3c03c010c4ba98733242455348795
     * TENDER_AMOUNT : 10000.0
     * RECOVER_AMOUNT_TOTAL : 10550.0
     * RECOVER_AMOUNT_TOTAL_YES : 91.67
     * RECOVER_AMOUNT_TOTAL_WAIT : 10458.33
     * RECOVER_AMOUNT_INTEREST : 550.00
     * RECOVER_AMOUNT_COUPON : 0.0
     * OVERDUE_INTEREST : 0.0
     * OVERDUE_FORFEIT : 0.0
     * INS_DATE : 2016-08-25 17:30:00.0
     * FINANCE_INTEREST_RATE : 11.0
     * INTEREST_RATE_TYPE : 1
     * FINANCE_PERIOD : 6
     * FINANCE_FULL_FLG : 1
     * PRODUCTS_TITLE : e兴车贷-THK车贷082501期
     * RECOVER_DATE : 2017-02-25
     * TENDER_FROM : 1
     * OID_TRANSFER_ID :
     * FINANCE_PRODUCTS_NAME : e兴车贷
     * PLATFORM_PRODUCTS_ID : e兴车贷-THK车贷08...
     * FINANCE_PERIOD_FORMAT : 6个月
     * TENDER_AMOUNT_FORMAT : 10,000.00
     * RECOVER_AMOUNT_TOTAL_WAIT_FORMAT : 10,458.33
     * RECOVER_AMOUNT_TOTAL_YES_FORMAT : 91.67
     * INS_DATE_FORMAT : 2016-08-25
     * RECOVER_DATE_FORMAT : 2017-02-25
     * STATUS : 满标复审
     */
		@SerializedName("OID_TENDER_ID")
        private String OID_TENDER_ID;
		
		@SerializedName("TRANSFER_CONTRACT_ID")
        private String TRANSFER_CONTRACT_ID;
		
		@SerializedName("OID_PLATFORM_PRODUCTS_ID")
        private String OID_PLATFORM_PRODUCTS_ID;
		
		@SerializedName("OID_USER_ID")
        private String OID_USER_ID;
		
		@SerializedName("TENDER_AMOUNT")
        private double TENDER_AMOUNT;
		
		@SerializedName("RECOVER_AMOUNT_TOTAL")
        private double RECOVER_AMOUNT_TOTAL;
		
		@SerializedName("RECOVER_AMOUNT_TOTAL_YES")
        private double RECOVER_AMOUNT_TOTAL_YES;
		
		@SerializedName("RECOVER_AMOUNT_TOTAL_WAIT")
        private double RECOVER_AMOUNT_TOTAL_WAIT;
		
		@SerializedName("RECOVER_AMOUNT_INTEREST")
        private String RECOVER_AMOUNT_INTEREST;
		
		@SerializedName("RECOVER_AMOUNT_COUPON")
        private double RECOVER_AMOUNT_COUPON;
		
		@SerializedName("OVERDUE_INTEREST")
        private double OVERDUE_INTEREST;
		
		@SerializedName("OVERDUE_FORFEIT")
        private double OVERDUE_FORFEIT;
		
		@SerializedName("INS_DATE")
        private String INS_DATE;
		
		@SerializedName("FINANCE_INTEREST_RATE")
        private double FINANCE_INTEREST_RATE;
		
		@SerializedName("INTEREST_RATE_TYPE")
        private String INTEREST_RATE_TYPE;
		
		@SerializedName("FINANCE_PERIOD")
        private int FINANCE_PERIOD;
		
		@SerializedName("FINANCE_FULL_FLG")
        private String FINANCE_FULL_FLG;
		
		@SerializedName("PRODUCTS_TITLE")
        private String PRODUCTS_TITLE;
		
		@SerializedName("RECOVER_DATE")
        private String RECOVER_DATE;
		
		@SerializedName("TENDER_FROM")
        private String TENDER_FROM;
		
		@SerializedName("OID_TRANSFER_ID")
        private String OID_TRANSFER_ID;
		
		@SerializedName("FINANCE_PRODUCTS_NAME")
        private String FINANCE_PRODUCTS_NAME;
		
		@SerializedName("PLATFORM_PRODUCTS_ID")
        private String PLATFORM_PRODUCTS_ID;
		
		@SerializedName("FINANCE_PERIOD_FORMAT")
        private String FINANCE_PERIOD_FORMAT;
		
		@SerializedName("TENDER_AMOUNT_FORMAT")
        private String TENDER_AMOUNT_FORMAT;
		
		@SerializedName("RECOVER_AMOUNT_TOTAL_WAIT_FORMAT")
        private String RECOVER_AMOUNT_TOTAL_WAIT_FORMAT;
		
		@SerializedName("RECOVER_AMOUNT_TOTAL_YES_FORMAT")
        private String RECOVER_AMOUNT_TOTAL_YES_FORMAT;
		
		@SerializedName("INS_DATE_FORMAT")
        private String INS_DATE_FORMAT;
		
		@SerializedName("RECOVER_DATE_FORMAT")
        private String RECOVER_DATE_FORMAT;
		
		@SerializedName("STATUS")
        private String STATUS;

        public String getOID_TENDER_ID() {
            return OID_TENDER_ID;
        }

        public void setOID_TENDER_ID(String OID_TENDER_ID) {
            this.OID_TENDER_ID = OID_TENDER_ID;
        }

        public String getTRANSFER_CONTRACT_ID() {
            return TRANSFER_CONTRACT_ID;
        }

        public void setTRANSFER_CONTRACT_ID(String TRANSFER_CONTRACT_ID) {
            this.TRANSFER_CONTRACT_ID = TRANSFER_CONTRACT_ID;
        }

        public String getOID_PLATFORM_PRODUCTS_ID() {
            return OID_PLATFORM_PRODUCTS_ID;
        }

        public void setOID_PLATFORM_PRODUCTS_ID(String OID_PLATFORM_PRODUCTS_ID) {
            this.OID_PLATFORM_PRODUCTS_ID = OID_PLATFORM_PRODUCTS_ID;
        }

        public String getOID_USER_ID() {
            return OID_USER_ID;
        }

        public void setOID_USER_ID(String OID_USER_ID) {
            this.OID_USER_ID = OID_USER_ID;
        }

        public double getTENDER_AMOUNT() {
            return TENDER_AMOUNT;
        }

        public void setTENDER_AMOUNT(double TENDER_AMOUNT) {
            this.TENDER_AMOUNT = TENDER_AMOUNT;
        }

        public double getRECOVER_AMOUNT_TOTAL() {
            return RECOVER_AMOUNT_TOTAL;
        }

        public void setRECOVER_AMOUNT_TOTAL(double RECOVER_AMOUNT_TOTAL) {
            this.RECOVER_AMOUNT_TOTAL = RECOVER_AMOUNT_TOTAL;
        }

        public double getRECOVER_AMOUNT_TOTAL_YES() {
            return RECOVER_AMOUNT_TOTAL_YES;
        }

        public void setRECOVER_AMOUNT_TOTAL_YES(double RECOVER_AMOUNT_TOTAL_YES) {
            this.RECOVER_AMOUNT_TOTAL_YES = RECOVER_AMOUNT_TOTAL_YES;
        }

        public double getRECOVER_AMOUNT_TOTAL_WAIT() {
            return RECOVER_AMOUNT_TOTAL_WAIT;
        }

        public void setRECOVER_AMOUNT_TOTAL_WAIT(double RECOVER_AMOUNT_TOTAL_WAIT) {
            this.RECOVER_AMOUNT_TOTAL_WAIT = RECOVER_AMOUNT_TOTAL_WAIT;
        }

        public String getRECOVER_AMOUNT_INTEREST() {
            return RECOVER_AMOUNT_INTEREST;
        }

        public void setRECOVER_AMOUNT_INTEREST(String RECOVER_AMOUNT_INTEREST) {
            this.RECOVER_AMOUNT_INTEREST = RECOVER_AMOUNT_INTEREST;
        }

        public double getRECOVER_AMOUNT_COUPON() {
            return RECOVER_AMOUNT_COUPON;
        }

        public void setRECOVER_AMOUNT_COUPON(double RECOVER_AMOUNT_COUPON) {
            this.RECOVER_AMOUNT_COUPON = RECOVER_AMOUNT_COUPON;
        }

        public double getOVERDUE_INTEREST() {
            return OVERDUE_INTEREST;
        }

        public void setOVERDUE_INTEREST(double OVERDUE_INTEREST) {
            this.OVERDUE_INTEREST = OVERDUE_INTEREST;
        }

        public double getOVERDUE_FORFEIT() {
            return OVERDUE_FORFEIT;
        }

        public void setOVERDUE_FORFEIT(double OVERDUE_FORFEIT) {
            this.OVERDUE_FORFEIT = OVERDUE_FORFEIT;
        }

        public String getINS_DATE() {
            return INS_DATE;
        }

        public void setINS_DATE(String INS_DATE) {
            this.INS_DATE = INS_DATE;
        }

        public double getFINANCE_INTEREST_RATE() {
            return FINANCE_INTEREST_RATE;
        }

        public void setFINANCE_INTEREST_RATE(double FINANCE_INTEREST_RATE) {
            this.FINANCE_INTEREST_RATE = FINANCE_INTEREST_RATE;
        }

        public String getINTEREST_RATE_TYPE() {
            return INTEREST_RATE_TYPE;
        }

        public void setINTEREST_RATE_TYPE(String INTEREST_RATE_TYPE) {
            this.INTEREST_RATE_TYPE = INTEREST_RATE_TYPE;
        }

        public int getFINANCE_PERIOD() {
            return FINANCE_PERIOD;
        }

        public void setFINANCE_PERIOD(int FINANCE_PERIOD) {
            this.FINANCE_PERIOD = FINANCE_PERIOD;
        }

        public String getFINANCE_FULL_FLG() {
            return FINANCE_FULL_FLG;
        }

        public void setFINANCE_FULL_FLG(String FINANCE_FULL_FLG) {
            this.FINANCE_FULL_FLG = FINANCE_FULL_FLG;
        }

        public String getPRODUCTS_TITLE() {
            return PRODUCTS_TITLE;
        }

        public void setPRODUCTS_TITLE(String PRODUCTS_TITLE) {
            this.PRODUCTS_TITLE = PRODUCTS_TITLE;
        }

        public String getRECOVER_DATE() {
            return RECOVER_DATE;
        }

        public void setRECOVER_DATE(String RECOVER_DATE) {
            this.RECOVER_DATE = RECOVER_DATE;
        }

        public String getTENDER_FROM() {
            return TENDER_FROM;
        }

        public void setTENDER_FROM(String TENDER_FROM) {
            this.TENDER_FROM = TENDER_FROM;
        }

        public String getOID_TRANSFER_ID() {
            return OID_TRANSFER_ID;
        }

        public void setOID_TRANSFER_ID(String OID_TRANSFER_ID) {
            this.OID_TRANSFER_ID = OID_TRANSFER_ID;
        }

        public String getFINANCE_PRODUCTS_NAME() {
            return FINANCE_PRODUCTS_NAME;
        }

        public void setFINANCE_PRODUCTS_NAME(String FINANCE_PRODUCTS_NAME) {
            this.FINANCE_PRODUCTS_NAME = FINANCE_PRODUCTS_NAME;
        }

        public String getPLATFORM_PRODUCTS_ID() {
            return PLATFORM_PRODUCTS_ID;
        }

        public void setPLATFORM_PRODUCTS_ID(String PLATFORM_PRODUCTS_ID) {
            this.PLATFORM_PRODUCTS_ID = PLATFORM_PRODUCTS_ID;
        }

        public String getFINANCE_PERIOD_FORMAT() {
            return FINANCE_PERIOD_FORMAT;
        }

        public void setFINANCE_PERIOD_FORMAT(String FINANCE_PERIOD_FORMAT) {
            this.FINANCE_PERIOD_FORMAT = FINANCE_PERIOD_FORMAT;
        }

        public String getTENDER_AMOUNT_FORMAT() {
            return TENDER_AMOUNT_FORMAT;
        }

        public void setTENDER_AMOUNT_FORMAT(String TENDER_AMOUNT_FORMAT) {
            this.TENDER_AMOUNT_FORMAT = TENDER_AMOUNT_FORMAT;
        }

        public String getRECOVER_AMOUNT_TOTAL_WAIT_FORMAT() {
            return RECOVER_AMOUNT_TOTAL_WAIT_FORMAT;
        }

        public void setRECOVER_AMOUNT_TOTAL_WAIT_FORMAT(String RECOVER_AMOUNT_TOTAL_WAIT_FORMAT) {
            this.RECOVER_AMOUNT_TOTAL_WAIT_FORMAT = RECOVER_AMOUNT_TOTAL_WAIT_FORMAT;
        }

        public String getRECOVER_AMOUNT_TOTAL_YES_FORMAT() {
            return RECOVER_AMOUNT_TOTAL_YES_FORMAT;
        }

        public void setRECOVER_AMOUNT_TOTAL_YES_FORMAT(String RECOVER_AMOUNT_TOTAL_YES_FORMAT) {
            this.RECOVER_AMOUNT_TOTAL_YES_FORMAT = RECOVER_AMOUNT_TOTAL_YES_FORMAT;
        }

        public String getINS_DATE_FORMAT() {
            return INS_DATE_FORMAT;
        }

        public void setINS_DATE_FORMAT(String INS_DATE_FORMAT) {
            this.INS_DATE_FORMAT = INS_DATE_FORMAT;
        }

        public String getRECOVER_DATE_FORMAT() {
            return RECOVER_DATE_FORMAT;
        }

        public void setRECOVER_DATE_FORMAT(String RECOVER_DATE_FORMAT) {
            this.RECOVER_DATE_FORMAT = RECOVER_DATE_FORMAT;
        }

        public String getSTATUS() {
            return STATUS;
        }

        public void setSTATUS(String STATUS) {
            this.STATUS = STATUS;
        }
}
