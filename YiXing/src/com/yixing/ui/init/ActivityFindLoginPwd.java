package com.yixing.ui.init;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yixing.R;
import com.yixing.biz.HomeBiz;
import com.yixing.biz.exception.BizFailure;
import com.yixing.biz.exception.ZYException;
import com.yixing.biz.task.BizDataAsyncTask;
import com.yixing.storage.PreferenceCache;
import com.yixing.ui.base.BaseActivity;
import com.yixing.utils.java.AlertUtil;
import com.yixing.utils.java.StringUtil;

public class ActivityFindLoginPwd extends BaseActivity implements
		OnClickListener {

	private TextView tv_title;
	private ImageView iv_back;

	private EditText et_find_login_pwd_tele_num;
	private EditText et_find_login_pwd_verify;
	private Button btn_get_sms_code;
	private Button btn_next_find_login_pwd;

	private Timer timer;
	private TimerTask timerTask;
	private int count = 120;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_find_login_password);
		initView();
	}

	private void initView() {

		tv_title = (TextView) findViewById(R.id.tv_title);
		iv_back = (ImageView) findViewById(R.id.iv_back);

		et_find_login_pwd_tele_num = (EditText) findViewById(R.id.edt_phone_num_find_login_pwd);
		et_find_login_pwd_verify = (EditText) findViewById(R.id.edt_sms_code_find_login_pwd);
		btn_get_sms_code = (Button) findViewById(R.id.acquire_msmcode);
		btn_next_find_login_pwd = (Button) findViewById(R.id.btn_next_find_login_pwd);
		btn_get_sms_code.setOnClickListener(this);
		btn_next_find_login_pwd.setOnClickListener(this);
		
		iv_back.setOnClickListener(this);
		tv_title.setText("找回密码");

	}

	private Handler mHandler = new Handler() {

		@Override
		public void dispatchMessage(Message msg) {
			// TODO Auto-generated method stub
			if (count >= 0) {
				btn_get_sms_code.setText(count + "s");
				btn_get_sms_code.setClickable(false);
				count--;
			} else {
				resetTimer();
			}
		}
	};

	private void resetTimer() {
		btn_get_sms_code.setText(R.string.find_getverifycode);
		//btn_get_sms_code.setClickable(true);
		count = 60;
		timerTask.cancel();
		timer.cancel();
		timerTask = null;
		timer = null;
	}

	private void runTimerTask() {
		timer = new Timer();
		timerTask = new TimerTask() {

			@Override
			public void run() {
				mHandler.sendEmptyMessage(0);
			}
		};
		timer.schedule(timerTask, 1000, 1000);

	}

	private BizDataAsyncTask<String> getVerificationTask;

	/**
	 * 申请验证码
	 */
	private void getVertifyCode() {

		if (StringUtil.isEmpty(et_find_login_pwd_tele_num.getEditableText()
				.toString())) {// 新手机号码不能为空
			AlertUtil.t(this, R.string.msg_phone_number);
			et_find_login_pwd_tele_num.requestFocus();
			return;
		}

		// SoftInputUtil.hideSoftKeyboard(ket_phoneNum.getEditText());
		getVerificationTask = new BizDataAsyncTask<String>(getWaitingView()) {

			@Override
			protected void onExecuteSucceeded(String result) {
				et_find_login_pwd_tele_num.setClickable(false);
				et_find_login_pwd_tele_num.setFocusable(false);
				PreferenceCache.putVerificationCode(result);
				runTimerTask();

			}

			@Override
			protected String doExecute() throws ZYException, BizFailure {
				return HomeBiz.getMobileCode(et_find_login_pwd_tele_num
						.getEditableText().toString().trim());
			}

			@Override
			protected void OnExecuteFailed(String error) {
				if(!StringUtil.isEmpty(error)){
					AlertUtil.t(ActivityFindLoginPwd.this, error);
				}
			}
		};

		getVerificationTask.execute();
	}
	private BizDataAsyncTask<String> findPassWordTask;
	
	/**
	 * 校验短信验证码
	 */
	private void findPassWord() {
		

		findPassWordTask = new BizDataAsyncTask<String>(getWaitingView()) {
			
			@Override
			protected void onExecuteSucceeded(String result) {
			
				Intent intent = new Intent(ActivityFindLoginPwd.this ,ActivityResetLoginPwd.class);
				intent.putExtra("PHONE_NUM", et_find_login_pwd_tele_num.getEditableText().toString());
				startActivity(intent);
				finish();
			}
			
			@Override
			protected String doExecute() throws ZYException, BizFailure {
				return HomeBiz.findPassword(et_find_login_pwd_tele_num.getEditableText().toString(), et_find_login_pwd_verify.getEditableText().toString());
			}
			
			@Override
			protected void OnExecuteFailed(String error) {
				if(!StringUtil.isEmpty(error)){
					AlertUtil.t(ActivityFindLoginPwd.this, error);
				}
			}
		};
		
		findPassWordTask.execute();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		// resetTimer();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_back:
			finish();
			break;
		case R.id.btn_next_find_login_pwd:
			// 确认
			// 本地校验验证码 跳转
			
			if(StringUtil.isEmpty(et_find_login_pwd_tele_num.getEditableText().toString().trim())){
				AlertUtil.t(this, R.string.msg_phone_number);
				et_find_login_pwd_tele_num.requestFocus();
				return;
			}
			if(StringUtil.isEmpty(et_find_login_pwd_verify.getEditableText().toString().trim())){
				AlertUtil.t(this, R.string.msg_verify_code_empty);
				et_find_login_pwd_verify.requestFocus();
				return;
			}
			
			
		/*	if (PreferenceCache.getVerificationCode().equals(
					et_find_login_pwd_verify.getEditableText().toString()
							.trim())) {

				Intent intent = new Intent();
				intent.setClass(this, ActivityResetLoginPwd.class);
				startActivity(intent);
			} else {
				AlertUtil.t(this, "验证码输入错误");
			}*/
			
			findPassWord();
			
			break;
		case R.id.acquire_msmcode:
			// 获取验证码
			getVertifyCode();
			break;

		default:
			break;
		}
	}

}
