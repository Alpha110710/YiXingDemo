package com.yixing.ui.init;



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
import com.yixing.utils.java.Util;

import android.content.Intent;
import android.os.Bundle;
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

public class ActivityResetLoginPwd extends BaseActivity implements OnClickListener {

	private TextView tv_title;
	private ImageView iv_back;

	private EditText et_reset_login_pwd_new;
	private Button btn_reset_login_pwd_confirm;
	private CheckBox cb_reset_login_pwd_new;

	private boolean isHidden = true;
	
	String phoneNum = "" ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reset_deal_password);
		phoneNum = getIntent().getStringExtra("PHONE_NUM") ;
		initView();
	}

	private void initView() {

		tv_title = (TextView) findViewById(R.id.tv_title);
		iv_back = (ImageView) findViewById(R.id.iv_back);
		btn_reset_login_pwd_confirm = (Button) findViewById(R.id.btn_reset_deal_pwd_confirm);
		cb_reset_login_pwd_new = (CheckBox) findViewById(R.id.cb_reset_deal_pwd_new);
		et_reset_login_pwd_new = (EditText) findViewById(R.id.et_reset_deal_pwd_new);

		iv_back.setOnClickListener(this);
		cb_reset_login_pwd_new.setOnClickListener(this);
		btn_reset_login_pwd_confirm.setOnClickListener(this);

		tv_title.setText("密码重置");

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_back:
			finish();
			break;

		case R.id.cb_reset_deal_pwd_new:
			// 新密码可见
			setHidenNew(et_reset_login_pwd_new);
			break;
		case R.id.btn_reset_deal_pwd_confirm:
			// 确认修改
			resetLoginPwd();
			
			break;

		default:
			break;
		}
	}

	private BizDataAsyncTask<Void> task;

	private void resetLoginPwd() {

		if (StringUtil.isEmpty(et_reset_login_pwd_new.getEditableText().toString())) {//
			AlertUtil.t(this, R.string.msg_new_pay_pwd_empty);
			et_reset_login_pwd_new.requestFocus();
			return;
		}
		if (et_reset_login_pwd_new.length() < 6 || et_reset_login_pwd_new.length() > 20) { // 密码长度检查
			AlertUtil.t(this, R.string.msg_password_length_check);
			et_reset_login_pwd_new.requestFocus();
			return ;
		}
		if (!Util.checkPwd(et_reset_login_pwd_new.getEditableText().toString())) {
			AlertUtil.t(this, R.string.msg_pwd_pattern_check);
			et_reset_login_pwd_new.requestFocus();
			return ;
		}

		task = new BizDataAsyncTask<Void>(getWaitingView()) {

			@Override
			protected void onExecuteSucceeded(Void result) {
				AlertUtil.t(ActivityResetLoginPwd.this, "密码修改成功");

				// PreferenceCache.putVerificationCode(result);

				PreferenceCache.putToken("");
				PreferenceCache.putIfSkipLogin(false);
				// WajrApp.canQueryFromOnResume = false;
				YixingApp.globalIndex = 0;
				// Intent it = new Intent(ActivitydealPwdChange.this,
				// Activitydeal.class);
				// it.putExtra(ExtraConfig.IntentExtraKey.deal_FROM_MAIN, true);
				// startActivity(it);
				// Util.showdeal(ActivitydealPwdChange.this);
				finish();
			}

			@Override
			protected Void doExecute() throws ZYException, BizFailure {
				AccountBiz.modifyPassword(phoneNum,"0",
						et_reset_login_pwd_new.getEditableText().toString());
				return null;
			}

			@Override
			protected void OnExecuteFailed(String error) {
				if(!StringUtil.isEmpty(error)){
					AlertUtil.t(ActivityResetLoginPwd.this, error);
				}

			}

		};

		task.execute();

	}

	/**
	 * 设置新密码可见
	 * 
	 * @param editText
	 */
	private void setHidenNew(EditText editText) {
		if (isHidden) {
			// 设置EditText文本为可见的
			editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
		} else {
			// 设置EditText文本为隐藏的
			editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
		}
		isHidden = !isHidden;
		editText.postInvalidate();
		// 切换后将EditText光标置于末尾
		CharSequence charSequence = editText.getText();
		if (charSequence instanceof Spannable) {
			Spannable spanText = (Spannable) charSequence;
			Selection.setSelection(spanText, charSequence.length());
		}
	}

}
