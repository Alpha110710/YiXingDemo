package com.yixing;


public class ExtraConfig {

	public static final int DEFAULT_PAGE_COUNT = 20;

	public static class BaseReceiverAction {
		public static final String ACTION_PREFIX = YixingConfig.ACTION_BASE_PREFIX;

		/**
		 * token过期
		 */
		public static final String ACTION_TOKEN_EXPIRE = ACTION_PREFIX
				+ "token.expire";
		
	}

	public static class IntentExtraKey {
		public static final String REQUEST_LOGIN_FOR_RESULT = "login_for_result";
		public static final String RETURN_MONEY_DETAIL_ONE_ID = "return_money_detail_one_id";
		public static final String RETURN_MONEY_DETAIL_ONE_TYPR = "return_money_detail_one_type";
		public static final String RETURN_MONEY_DETAIL_TWO_ID = "return_money_detail_two_id";
		public static final String RETURN_MONEY_DETAIL_TWO_TYPR = "return_money_detail_two_type";
		public static final String RETURN_MONEY_DETAIL_TWO_DATE = "return_money_detail_two_date";
		public static final String RETURN_MONEY_DETAIL_TWO_CONTRACT_ID = "return_money_detail_two_date_contract_id";
		public static final String MY_ACCOUNT = "my_account";
		public static final String LOGIN_FROM_MAIN = "login_from_main";
		public static final String WEB_VIEW_FROM = "web_view_from";
		public static final String RED_PACKET_RULE = "red_packet_rule";
		public static final String ACCOUNT_MSG = "account_msg";
		public static final String MY_TRANSFER_OID_TENDER_ID = "my_transfer_oid_tender_id";
		public static final String MY_TRANSFER_TENDER_FROM = "my_transfer_tender_from";
		public static final String MY_TRANSFER_FINACE_NAME = "my_transfer_finace_name";
		public static final String SECURITY_CENTER_ADDRESS_CHANGE = "security_center_address_change";
		public static final String MY_TRANSFER_DETAIL_BEAN = "my_transfer_detail_bean";
		public static final String MY_TRANSFER_DETAIL_ID = "my_transfer_detail_ID";
		public static final String ACCOUNT_TELE_NUM = "account_tele_num";
		
	}

	public static class RequestCode {
		public static final int REQUEST_CODE_FOR_LOGIN = 0x01;
		public static final int REQUEST_CODE_FOR_MESSAGE_INFO = 0x02;
		public static final int REQUEST_CODE_FOR_MY_TRANSFER = 0x03;
		public static final int REQUEST_CODE_FOR_MY_DEAL_PWD_CHANGE = 0x04;
		public static final int REQUEST_CODE_FOR_MY_ADDRESS_CHANGE = 0x05;
		public static final int REQUEST_CODE_FOR_MESSAGE = 0x06;
		

	}
}
