package com.yixing.ui.account;

import java.util.Timer;
import java.util.TimerTask;

import com.yixing.ExtraConfig;
import com.yixing.R;
import com.yixing.biz.AccountBiz;
import com.yixing.biz.exception.BizFailure;
import com.yixing.biz.exception.ZYException;
import com.yixing.biz.task.BizDataAsyncTask;
import com.yixing.model.MyAccountModel;
import com.yixing.storage.PreferenceCache;
import com.yixing.ui.base.BaseActivity;
import com.yixing.utils.java.AlertUtil;
import com.yixing.utils.java.StringUtil;

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
import android.widget.TextView;

public class ActivityPhoneNumChangeOne extends BaseActivity implements OnClickListener {

	private TextView tv_title;
	private ImageView iv_back;

	private EditText et_reset_num_one_old;// 原验证码
	private Button btn_reset_num_one_go_next;// 下一步
	private Button btn_reset_num_one_get_verify;// 获取验证码
	private TextView tv_reset_num_one_old_cant_use;// 原手机不可用

	private Timer timer;
	private TimerTask timerTask;
	private int count =120;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reset_tele_num_one);

		initView();

	}

	private void initView() {

		tv_title = (TextView) findViewById(R.id.tv_title);
		iv_back = (ImageView) findViewById(R.id.iv_back);
		et_reset_num_one_old = (EditText) findViewById(R.id.et_reset_num_one_old);
		btn_reset_num_one_get_verify = (Button) findViewById(R.id.btn_reset_num_one_get_verify);
		btn_reset_num_one_go_next = (Button) findViewById(R.id.btn_reset_num_one_go_next);
		tv_reset_num_one_old_cant_use = (TextView) findViewById(R.id.tv_reset_num_one_old_cant_use);

		iv_back.setOnClickListener(this);
		btn_reset_num_one_go_next.setOnClickListener(this);
		btn_reset_num_one_get_verify.setOnClickListener(this);
		tv_reset_num_one_old_cant_use.setOnClickListener(this);
		tv_title.setText("手机号修改");

	}

	private Handler mHandler = new Handler() {

		@Override
		public void dispatchMessage(Message msg) {
			// TODO Auto-generated method stub
			if (count >= 0) {
				btn_reset_num_one_get_verify.setText(count + "s");
				btn_reset_num_one_get_verify.setClickable(false);
				count--;
			} else {
				resetTimer();
			}
		}
	};

	private void resetTimer() {
		btn_reset_num_one_get_verify.setText(R.string.find_getverifycode);
		btn_reset_num_one_get_verify.setClickable(true);
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
	private void getVertifyCodeForNewPhoneNum() {

		if (StringUtil.isEmpty(et_reset_num_one_old.getEditableText().toString())) {// 新手机号码不能为空
			AlertUtil.t(this, R.string.msg_new_phone_num_empty);
			et_reset_num_one_old.requestFocus();
			return;
		}

		// SoftInputUtil.hideSoftKeyboard(ket_phoneNum.getEditText());
		getVerificationTask = new BizDataAsyncTask<String>(getWaitingView()) {

			@Override
			protected void onExecuteSucceeded(String result) {

				PreferenceCache.putVerificationCode(result);
				runTimerTask();

			}

			@Override
			protected String doExecute() throws ZYException, BizFailure {
				return AccountBiz.getVertifyCodeForModifyPhone(et_reset_num_one_old.getEditableText().toString());
			}

			@Override
			protected void OnExecuteFailed(String error) {
			}
		};

		getVerificationTask.execute();
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
		case R.id.btn_reset_num_one_go_next:
			// 下一步
			Intent intent = new Intent(this, ActivityPhoneNumChangeThree.class);
			startActivity(intent);

			break;
		case R.id.btn_reset_num_one_get_verify:
			// 获取手机验证码
			getVertifyCodeForNewPhoneNum();
			break;
		case R.id.tv_reset_num_one_old_cant_use:
			// 原手机不可用
			Intent intent1 = new Intent(this, ActivityPhoneNumChangeTwo.class);
			startActivity(intent1);
			break;

		default:
			break;
		}

	}

}