package com.yixing.ui.account;

import java.util.Timer;
import java.util.TimerTask;

import com.yixing.ExtraConfig;
import com.yixing.R;
import com.yixing.YixingApp;
import com.yixing.biz.AccountBiz;
import com.yixing.biz.exception.BizFailure;
import com.yixing.biz.exception.ZYException;
import com.yixing.biz.task.BizDataAsyncTask;
import com.yixing.storage.PreferenceCache;
import com.yixing.ui.base.BaseActivity;
import com.yixing.utils.java.AlertUtil;
import com.yixing.utils.java.StringUtil;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class ActivityFindDealPwd extends BaseActivity implements OnClickListener {

	private TextView tv_title;
	private ImageView iv_back;

	private Button btn_find_deal_pwd_confirm;
	private Button btn_find_deal_pwd_verify;
	private TextView et_find_deal_pwd_tele_num;
	private EditText et_find_deal_pwd_verify;

	private boolean isHiddenOld = true;
	private boolean isHiddenNew = true;

	private Timer timer;
	private TimerTask timerTask;
	private int count = 120;

	private String teleNum;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_find_deal_password);
		teleNum = getIntent().getStringExtra(ExtraConfig.IntentExtraKey.ACCOUNT_TELE_NUM);
		initView();
	}

	private void initView() {

		tv_title = (TextView) findViewById(R.id.tv_title);
		iv_back = (ImageView) findViewById(R.id.iv_back);

		btn_find_deal_pwd_confirm = (Button) findViewById(R.id.btn_find_deal_pwd_confirm);
		btn_find_deal_pwd_verify = (Button) findViewById(R.id.btn_find_deal_pwd_verify);
		et_find_deal_pwd_tele_num = (TextView) findViewById(R.id.et_find_deal_pwd_tele_num);
		et_find_deal_pwd_verify = (EditText) findViewById(R.id.et_find_deal_pwd_verify);

		iv_back.setOnClickListener(this);
		btn_find_deal_pwd_confirm.setOnClickListener(this);
		btn_find_deal_pwd_verify.setOnClickListener(this);
		et_find_deal_pwd_tele_num.setText(teleNum);
		tv_title.setText("密码重置");

	}

	private Handler mHandler = new Handler() {

		@Override
		public void dispatchMessage(Message msg) {
			// TODO Auto-generated method stub
			if (count >= 0) {
				btn_find_deal_pwd_verify.setText(count + "s");
				btn_find_deal_pwd_verify.setClickable(false);
				count--;
			} else {
				resetTimer();
			}
		}
	};

	private void resetTimer() {
		btn_find_deal_pwd_verify.setText(R.string.find_getverifycode);
		btn_find_deal_pwd_verify.setClickable(true);
		count = 120;
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


		// SoftInputUtil.hideSoftKeyboard(ket_phoneNum.getEditText());
		getVerificationTask = new BizDataAsyncTask<String>(getWaitingView()) {

			@Override
			protected void onExecuteSucceeded(String result) {

				if (result.trim().equals("1")) {
					AlertUtil.t(ActivityFindDealPwd.this, "验证码发送成功");
				}
				runTimerTask();

			}

			@Override
			protected String doExecute() throws ZYException, BizFailure {
				return AccountBiz.getMobileCode(teleNum);
			}

			@Override
			protected void OnExecuteFailed(String error) {
				if (!StringUtil.isEmpty(error)) {
					AlertUtil.t(ActivityFindDealPwd.this, error);
				}
			}
		};

		getVerificationTask.execute();
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
		case R.id.btn_find_deal_pwd_confirm:
			// 确认
			// 本地校验验证码 跳转
			if (inVoke()) {
				findPassword();
			}

			break;
		case R.id.btn_find_deal_pwd_verify:
			// 获取验证码
			getVertifyCode();
			break;

		default:
			break;
		}
	}

	private BizDataAsyncTask<String> findPasswordTask;

	private void findPassword() {
		findPasswordTask = new BizDataAsyncTask<String>(getWaitingView()) {

			@Override
			protected void onExecuteSucceeded(String result) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(ActivityFindDealPwd.this, ActivityResetDealPwd.class);
				intent.putExtra("tele", teleNum);
				startActivity(intent);
			}

			@Override
			protected String doExecute() throws ZYException, BizFailure {
				// TODO Auto-generated method stub
				return AccountBiz.findPassword(teleNum, et_find_deal_pwd_verify.getText().toString().trim());
			}

			@Override
			protected void OnExecuteFailed(String error) {
				// TODO Auto-generated method stub
				if (!StringUtil.isEmpty(error)) {
					AlertUtil.t(ActivityFindDealPwd.this, error);
				}

			}
		};
		findPasswordTask.execute();
	}

	private boolean inVoke() {
		if (StringUtil.isEmpty(et_find_deal_pwd_tele_num.getText().toString())) {
			AlertUtil.t(this, "请输入注册手机号");
			return false;
		}

		if (StringUtil.isEmpty(et_find_deal_pwd_verify.getText().toString())) {
			AlertUtil.t(this, "请输入短信验证码");
			return false;
		}

		return true;
	}

}
