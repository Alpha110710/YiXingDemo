package com.yixing.ui.account;

import java.util.Timer;
import java.util.TimerTask;

import com.yixing.R;
import com.yixing.biz.AccountBiz;
import com.yixing.biz.exception.BizFailure;
import com.yixing.biz.exception.ZYException;
import com.yixing.biz.task.BizDataAsyncTask;
import com.yixing.storage.PreferenceCache;
import com.yixing.ui.base.BaseActivity;
import com.yixing.utils.java.AlertUtil;
import com.yixing.utils.java.StringUtil;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class ActivityPhoneNumChangeThree extends BaseActivity implements OnClickListener {

	private TextView tv_title;
	private ImageView iv_back;

	private EditText et_reset_num_three_new;// 新手机号码
	private EditText et_reset_num_three_verify;// 验证码
	private Button btn_reset_num_three_confirm;// 确认修改
	private Button btn_reset_num_three_verify;// 获取验证码

	private Timer timer;
	private TimerTask timerTask;
	private int count = 120;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reset_tele_num_three);

		initView();
	}

	private Handler mHandler = new Handler() {

		@Override
		public void dispatchMessage(Message msg) {
			// TODO Auto-generated method stub
			if (count >= 0) {
				btn_reset_num_three_verify.setText(count + "s");
				btn_reset_num_three_verify.setClickable(false);
				count--;
			} else {
				resetTimer();
			}
		}
	};

	private void resetTimer() {
		btn_reset_num_three_verify.setText(R.string.find_getverifycode);
		btn_reset_num_three_verify.setClickable(true);
		count = 60;
		timerTask.cancel();
		timer.cancel();
		timerTask = null;
		timer = null;
	}

	private void initView() {
		tv_title = (TextView) findViewById(R.id.tv_title);
		iv_back = (ImageView) findViewById(R.id.iv_back);

		btn_reset_num_three_confirm = (Button) findViewById(R.id.btn_reset_num_three_confirm);
		btn_reset_num_three_verify = (Button) findViewById(R.id.btn_reset_num_three_verify);
		et_reset_num_three_new = (EditText) findViewById(R.id.et_reset_num_three_new);
		et_reset_num_three_verify = (EditText) findViewById(R.id.et_reset_num_three_verify);

		iv_back.setOnClickListener(this);
		btn_reset_num_three_verify.setOnClickListener(this);
		btn_reset_num_three_confirm.setOnClickListener(this);

		tv_title.setText("手机号修改");
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
	private void getVertifyCodeForNewPhoneNum() {

		if (StringUtil.isEmpty(et_reset_num_three_new.getEditableText().toString())) {// 新手机号码不能为空
			AlertUtil.t(this, R.string.msg_new_phone_num_empty);
			et_reset_num_three_new.requestFocus();
			return;
		}

		// SoftInputUtil.hideSoftKeyboard(ket_phoneNum.getEditText());
		getVerificationTask = new BizDataAsyncTask<String>(getWaitingView()) {

			@Override
			protected void onExecuteSucceeded(String result) {
				// AlertUtil.t(RegisterActivity.this, result);

				PreferenceCache.putVerificationCode(result);
				runTimerTask();

			}

			@Override
			protected String doExecute() throws ZYException, BizFailure {
				return AccountBiz.getVertifyCodeForModifyPhone(et_reset_num_three_new.getEditableText().toString());
			}

			@Override
			protected void OnExecuteFailed(String error) {
				// TODO Auto-generated method stub

			}
		};

		getVerificationTask.execute();
	}

	private BizDataAsyncTask<Void> changePhoneNumTask;

	/**
	 * 修改手机号码
	 */
	private void changePhoneNum() {

		if (StringUtil.isEmpty(et_reset_num_three_new.getEditableText().toString())) {//
			AlertUtil.t(this, R.string.msg_new_phone_num_empty);
			et_reset_num_three_new.requestFocus();
			return;
		}
		if (StringUtil.isEmpty(et_reset_num_three_verify.getEditableText().toString())) {//
			AlertUtil.t(this, R.string.msg_verify_code_empty);
			et_reset_num_three_new.requestFocus();
			return;
		}

		changePhoneNumTask = new BizDataAsyncTask<Void>(getWaitingView()) {

			@Override
			protected void onExecuteSucceeded(Void result) {
				AlertUtil.t(ActivityPhoneNumChangeThree.this, "修改手机号码成功，请重新登录");

				// PreferenceCache.putVerificationCode(result);

				PreferenceCache.putToken("");
				PreferenceCache.putIfSkipLogin(false);
				// WajrApp.canQueryFromOnResume = false;
				// WajrApp.globalIndex = 0;
				// Intent it = new Intent(ActivityPhoneNumberChangeNew.this,
				// ActivityLogin.class);
				// it.putExtra(ExtraConfig.IntentExtraKey.LOGIN_FROM_MAIN,
				// true);
				// startActivity(it);
				// // Util.showLogin(ActivityLoginPwdChange.this);
				// ActivityPhoneNumberChangeNew.this.finish();
			}

			@Override
			protected Void doExecute() throws ZYException, BizFailure {
				AccountBiz.modifyPhoneNumber(et_reset_num_three_new.getEditableText().toString(),
						et_reset_num_three_verify.getEditableText().toString());
				return null;
			}

			@Override
			protected void OnExecuteFailed(String error) {
				// TODO Auto-generated method stub

			}
		};

		changePhoneNumTask.execute();

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
//		resetTimer();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_back:
			finish();
			break;
		case R.id.btn_reset_num_three_confirm:
			// 确认修改
			changePhoneNum();
			break;
		case R.id.btn_reset_num_three_verify:
			// 获取验证码
			getVertifyCodeForNewPhoneNum();
			break;

		default:
			break;
		}
	}

}
