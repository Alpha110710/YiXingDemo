package com.yixing.ui.account;

import com.yixing.ExtraConfig;
import com.yixing.R;
import com.yixing.biz.AccountBiz;
import com.yixing.biz.exception.BizFailure;
import com.yixing.biz.exception.ZYException;
import com.yixing.biz.task.BizDataAsyncTask;
import com.yixing.model.City;
import com.yixing.model.County;
import com.yixing.model.MyAccountModel;
import com.yixing.model.Province;
import com.yixing.model.SecurityCenterModel;
import com.yixing.ui.addressselector.BottomDialog;
import com.yixing.ui.addressselector.BottomDialogB;
import com.yixing.ui.addressselector.OnAddressSelectedListener;
import com.yixing.ui.base.BaseActivity;
import com.yixing.ui.init.ActivityRegist;
import com.yixing.utils.java.AlertUtil;
import com.yixing.utils.java.StringUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView;

public class ActivitySecurityCenter extends BaseActivity implements OnClickListener{

	private TextView tv_title;
	private ImageView iv_back;

	private TextView tv_security_center_phone_num;// 手机号码
	private TextView tv_security_center_address;// 所在地址
//	private EditText et_security_center_address_detail;// 详细地址
	private TextView tv_security_center_email;// 电子账户
	private TextView tv_security_center_id_card;// 证件信息
	private TextView tv_security_center_login_pwd;// 登录密码
	private TextView tv_security_center_payment_pwd;// 支付密码

	private SecurityCenterModel model;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_security_center);

		initView();

		init();

	}

	private void initView() {

		tv_title = (TextView) findViewById(R.id.tv_title);
		iv_back = (ImageView) findViewById(R.id.iv_back);

		tv_security_center_phone_num = (TextView) findViewById(R.id.tv_security_center_phone_num);
		tv_security_center_address = (TextView) findViewById(R.id.tv_security_center_address);
//		et_security_center_address_detail = (EditText) findViewById(R.id.et_security_center_address_detail);
		tv_security_center_email = (TextView) findViewById(R.id.tv_security_center_email);
		tv_security_center_id_card = (TextView) findViewById(R.id.tv_security_center_id_card);
		tv_security_center_login_pwd = (TextView) findViewById(R.id.tv_security_center_login_pwd);
		tv_security_center_payment_pwd = (TextView) findViewById(R.id.tv_security_center_payment_pwd);

		iv_back.setOnClickListener(this);
//		tv_security_center_phone_num.setOnClickListener(this);//手机号码不可修改
		tv_security_center_address.setOnClickListener(this);
		tv_security_center_login_pwd.setOnClickListener(this);
		tv_security_center_payment_pwd.setOnClickListener(this);

		tv_title.setText("安全中心");

	}

	private void init() {
		getUserSafeCenterDetail();
	}

	private BizDataAsyncTask<SecurityCenterModel> task;

	private void getUserSafeCenterDetail() {
		task = new BizDataAsyncTask<SecurityCenterModel>(getWaitingView()) {

			@Override
			protected void onExecuteSucceeded(SecurityCenterModel result) {

				/**
				 * EXTENSION : 已开通 ID_CARD : 210502198410212416 MOBILE :
				 * 13889405860 PROVINCE : 山东省 CITY : 莱芜市 AREA : 莱城区 ADDRESS :
				 * USER_PWD : ef209c9343ca8c715265781876657b18 USER_PAY_PWD :
				 * b3a2083f5814d4c81406c558552169e8
				 */
				model = result;
				
				if (!StringUtil.isEmpty(result.getMOBILE())) {
					tv_security_center_phone_num.setText(result.getMOBILE().substring(0, 3) + "******"
							+ result.getMOBILE().substring(result.getMOBILE().length() - 3));
				}
				
				if (!StringUtil.isEmpty(result.getID_CARD())) {
					tv_security_center_id_card.setText(result.getID_CARD().substring(0, 1) + "******"
							+ result.getID_CARD().substring(result.getID_CARD().length() - 1));
				}
				
				tv_security_center_email.setText(result.getEXTENSION());
				tv_security_center_address.setText(result.getPROVINCE() + result.getCITY() + result.getADDRESS());
//				et_security_center_address_detail.setText(result.getADDRESS());

			}

			@Override
			protected SecurityCenterModel doExecute() throws ZYException, BizFailure {

				return AccountBiz.userSafeCenterDetail();
			}

			@Override
			protected void OnExecuteFailed(String error) {

			}
		};

		task.execute();
	}

	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_back:
			finish();
			break;

		case R.id.tv_security_center_phone_num:
			// 修改手机号码
			Intent intent = new Intent(this, ActivityPhoneNumChangeOne.class);
			startActivity(intent);
			break;
		case R.id.tv_security_center_address:
			//跳转修改收货地址
			if (model == null) {
				return;
			}
			Intent itAddress = new Intent(this, ActivityAddressChange.class);
			itAddress.putExtra(ExtraConfig.IntentExtraKey.SECURITY_CENTER_ADDRESS_CHANGE, model);
			startActivityForResult(itAddress, ExtraConfig.RequestCode.REQUEST_CODE_FOR_MY_ADDRESS_CHANGE);
			break;
		case R.id.tv_security_center_login_pwd:
			// 修改登录密码
			Intent intent2 = new Intent();
			intent2.setClass(this, ActivityLoginPwdChange.class);
			startActivity(intent2);
			break;

		case R.id.tv_security_center_payment_pwd:
			if (model == null) {
				return;
			}
			// 修改支付密码
			Intent intent3 = new Intent();
			intent3.setClass(this, ActivityDealPwdChange.class);
			intent3.putExtra(ExtraConfig.IntentExtraKey.ACCOUNT_TELE_NUM, model.getMOBILE());
			startActivityForResult(intent3, ExtraConfig.RequestCode.REQUEST_CODE_FOR_MY_DEAL_PWD_CHANGE);
			break;

		default:
			break;
		}

	}

	
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		// TODO Auto-generated method stub
		super.onActivityResult(arg0, arg1, arg2);
		if (arg0 == ExtraConfig.RequestCode.REQUEST_CODE_FOR_MY_DEAL_PWD_CHANGE) {//修改交易密码
			getUserSafeCenterDetail();
		}
		if (arg0 == ExtraConfig.RequestCode.REQUEST_CODE_FOR_MY_ADDRESS_CHANGE) {//修改地址
			getUserSafeCenterDetail();
			
		}
	}

	

	

}