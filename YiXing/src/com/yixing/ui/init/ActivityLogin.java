package com.yixing.ui.init;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yixing.ExtraConfig;
import com.yixing.R;
import com.yixing.YixingApp;
import com.yixing.YixingConfig;
import com.yixing.biz.HomeBiz;
import com.yixing.biz.exception.BizFailure;
import com.yixing.biz.exception.ZYException;
import com.yixing.biz.task.BizDataAsyncTask;
import com.yixing.model.LoginModel;
import com.yixing.model.RedomVerifyCode;
import com.yixing.storage.PreferenceCache;
import com.yixing.ui.base.BaseActivity;
import com.yixing.utils.java.AlertUtil;
import com.yixing.utils.java.Base64;
import com.yixing.utils.java.StringUtil;
import com.yixing.utils.java.Util;

public class ActivityLogin extends BaseActivity implements OnClickListener {

	private EditText edtPhoneNum;
	private EditText edtLoginPwd;
	private CheckBox ckHideShow;
	private LinearLayout llHideShow;
	private EditText edtVerifyCodeLogin;
	private ImageView ivGetRedomVerifyCode;
	private Button btnLogin;
	private CheckBox ckRemberAccount;
	private TextView tvForgetPwd;
	private TextView tvToRegist;
	private ImageView ivClose;

	private String verifyCodeContent = "";
	private Bitmap bit;

	private int toLogin = 0;
	private boolean tokenOverDue;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		toLogin = getIntent().getIntExtra("TO_LOGIN", 0);
		tokenOverDue = getIntent().getBooleanExtra(ExtraConfig.IntentExtraKey.LOGIN_FROM_MAIN, false);
		init();
	}

	private void init() {

		edtPhoneNum = (EditText) findViewById(R.id.edt_phone_num);
		edtLoginPwd = (EditText) findViewById(R.id.edt_login_pwd);
		ckHideShow = (CheckBox) findViewById(R.id.ck_hide_show);
		llHideShow = (LinearLayout) findViewById(R.id.ll_hide_show_login);
		llHideShow.setOnClickListener(this);
		edtVerifyCodeLogin = (EditText) findViewById(R.id.edt_verify_code);
		ivGetRedomVerifyCode = (ImageView) findViewById(R.id.iv_get_redom_verify_code);
		ivGetRedomVerifyCode.setOnClickListener(this);
		ivClose = (ImageView) findViewById(R.id.iv_close);
		ivClose.setOnClickListener(this);
		btnLogin = (Button) findViewById(R.id.btn_login);
		btnLogin.setOnClickListener(this);
		ckRemberAccount = (CheckBox) findViewById(R.id.cb_rember_account);
		ckRemberAccount.setOnClickListener(this);
		tvForgetPwd = (TextView) findViewById(R.id.tv_forget_pwd);
		tvForgetPwd.setOnClickListener(this);
		tvToRegist = (TextView) findViewById(R.id.tv_to_regist);
		tvToRegist.setOnClickListener(this);

		edtPhoneNum.setText(PreferenceCache.getUsername());
		//getCaptchaImage();

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		case R.id.btn_login:

			if (checkLogin()) {
				doLogin();
			}

			break;

		case R.id.tv_forget_pwd:

			startActivity(new Intent(this, ActivityFindLoginPwd.class));

			break;
		case R.id.tv_to_regist:
			if (toLogin == 1) {
				finish();
			} else {

				Intent itToRegist = new Intent(this, ActivityRegist.class);
				itToRegist.putExtra("TO_REGIST", 1);
				startActivity(itToRegist);
			}
			break;
		case R.id.iv_get_redom_verify_code:
			getCaptchaImage();

			break;

		case R.id.ll_hide_show_login:

			if (ckHideShow.isChecked()) {

				edtLoginPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

				edtLoginPwd.setSelection(edtLoginPwd.getEditableText().toString().length());
				// ckHideShow.setBackgroundResource(R.drawable.login_eye_true);

			} else {

				edtLoginPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());

				edtLoginPwd.setSelection(edtLoginPwd.getEditableText().toString().length());
				// ckHideShow.setBackgroundResource(R.drawable.login_eye_false);
			}
			ckHideShow.setChecked(!ckHideShow.isChecked());

			break;
		/*
		 * case R.id.cb_rember_account:
		 * 
		 * if (ckRemberAccount.isChecked()) {
		 * 
		 * ckRemberAccount .setBackgroundResource(R.drawable.login_check_true);
		 * 
		 * } else { ckRemberAccount
		 * .setBackgroundResource(R.drawable.login_check_false); } break;
		 */
		case R.id.iv_close:

			if (tokenOverDue) {
				YixingApp.globalIndex = 0;
				Util.gotoMain(ActivityLogin.this);
			} else {

				finish();
			}

			break;

		}

	}

	@Override
	public void onBackPressed() {

		if (tokenOverDue) {
			YixingApp.globalIndex = 0;
			Util.gotoMain(ActivityLogin.this);
		} else {

			finish();
		}

	}

	private boolean checkLogin() {

		if (StringUtil.isEmpty(edtPhoneNum.getEditableText().toString().trim())) {
			AlertUtil.t(this, "请输入手机号");
			edtPhoneNum.requestFocus();
			return false;
		}
		if (StringUtil.isEmpty(edtLoginPwd.getEditableText().toString().trim())) {
			AlertUtil.t(this, "请输入登录密码");
			edtLoginPwd.requestFocus();
			return false;
		}

	/*	if (edtLoginPwd.length() < 6 || edtLoginPwd.length() > 20) { // 密码长度检查
			AlertUtil.t(this, R.string.msg_password_length_check);
			edtLoginPwd.requestFocus();
			return false;
		}
		if (!Util.checkPwd(edtLoginPwd.getEditableText().toString())) {
			AlertUtil.t(this, R.string.msg_pwd_pattern_check);
			edtLoginPwd.requestFocus();
			return false;
		}

		if (StringUtil.isEmpty(edtVerifyCodeLogin.getEditableText().toString().trim())) {
			AlertUtil.t(this, "请输入校验码");
			edtVerifyCodeLogin.requestFocus();
			return false;
		}
		if (!verifyCodeContent.equals(edtVerifyCodeLogin.getEditableText().toString().trim())) {
			AlertUtil.t(this, "请输入正确的校验码");
			edtVerifyCodeLogin.requestFocus();
			return false;
		}*/

		return true;
	}

	BizDataAsyncTask<LoginModel> loginTask;

	private void doLogin() {
		loginTask = new BizDataAsyncTask<LoginModel>(getWaitingView()) {

			@Override
			protected void onExecuteSucceeded(LoginModel result) {
				PreferenceCache.putToken(result.getUserToken()); // 持久化缓存token
				PreferenceCache.putBankOpenFlag(true);
				/*
				 * PreferenceCache.putIfSkipLogin(true); // 跳过登录环节
				 * PreferenceCache.putAutoLogin(ckRemberAccount.isChecked());//
				 * 记录是否自动登录
				 */
				if (ckRemberAccount.isChecked()) {
					PreferenceCache.putUsername(edtPhoneNum.getEditableText().toString());
				} else {
					PreferenceCache.putUsername("");
				}

				/*
				 * if (PreferenceCache.isAutoLogin()) {
				 * PreferenceCache.putPhoneNum(edtPhoneNum.getEditableText()
				 * .toString()); }
				 */
				YixingApp.globalIndex = 3;
				Util.gotoMain(ActivityLogin.this);
				/*
				 * if(loginFromMain){ Util.gotoMain(ActivityLogin.this); }else {
				 * finish(); }
				 */
			}

			@Override
			protected LoginModel doExecute() throws ZYException, BizFailure {

				return HomeBiz.login(edtPhoneNum.getEditableText().toString().trim(),
						edtLoginPwd.getEditableText().toString().trim(), "2", "1.0.0");

			}

			@Override
			protected void OnExecuteFailed(String error) {
				// TODO Auto-generated method stub
				getCaptchaImage();
				if(!StringUtil.isEmpty(error)){
					AlertUtil.t(ActivityLogin.this, error);
				}
			}

		};

		loginTask.execute();
	}

	private BizDataAsyncTask<RedomVerifyCode> getCaptchaImageTask;

	/**
	 * 申请验证码
	 */
	private void getCaptchaImage() {
		getCaptchaImageTask = new BizDataAsyncTask<RedomVerifyCode>() {

			@Override
			protected void onExecuteSucceeded(RedomVerifyCode result) {

				try {
					verifyCodeContent = result.getCode();
					byte[] srtbyte = Base64.decode(result.getByteContent());
					bit = getBitmapFromByte(srtbyte);
					ivGetRedomVerifyCode.setImageBitmap(bit);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			protected RedomVerifyCode doExecute() throws ZYException, BizFailure {
				return HomeBiz.getCaptchaImage();
			}

			@Override
			protected void OnExecuteFailed(String error) {
				// TODO Auto-generated method stub
				if (!StringUtil.isEmpty(error)) {
					Toast.makeText(YixingApp.getAppContext(), error, Toast.LENGTH_LONG).show();
				}
			}

		};

		getCaptchaImageTask.execute();
	}

	public Bitmap getBitmapFromByte(byte[] temp) {
		if (temp != null) {
			Bitmap bitmap = BitmapFactory.decodeByteArray(temp, 0, temp.length);
			return bitmap;
		} else {
			return null;
		}
	}

}