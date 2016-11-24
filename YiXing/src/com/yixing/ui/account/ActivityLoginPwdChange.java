package com.yixing.ui.account;

import com.yixing.ExtraConfig;
import com.yixing.R;
import com.yixing.YixingApp;
import com.yixing.biz.AccountBiz;
import com.yixing.biz.exception.BizFailure;
import com.yixing.biz.exception.ZYException;
import com.yixing.biz.task.BizDataAsyncTask;
import com.yixing.storage.PreferenceCache;
import com.yixing.ui.base.BaseActivity;
import com.yixing.ui.init.ActivityLogin;
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
import android.widget.LinearLayout;
import android.widget.TextView;

public class ActivityLoginPwdChange extends BaseActivity implements OnClickListener {

	private TextView tv_title;
	private ImageView iv_back;

	private EditText et_login_pwd_change_old;
	private EditText et_login_pwd_change_new;
	private Button btn_login_pwd_change_confirm;
	private CheckBox cb_login_pwd_change_new;
	private CheckBox cb_login_pwd_change_old;
	private LinearLayout ll_login_pwd_change_old;
	private LinearLayout ll_login_pwd_change_new;

	private boolean isHiddenOld = true;
	private boolean isHiddenNew = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_pwd_change);
		initView();
	}

	private void initView() {

		tv_title = (TextView) findViewById(R.id.tv_title);
		iv_back = (ImageView) findViewById(R.id.iv_back);
		btn_login_pwd_change_confirm = (Button) findViewById(R.id.btn_login_pwd_change_confirm);
		et_login_pwd_change_new = (EditText) findViewById(R.id.et_login_pwd_change_new);
		et_login_pwd_change_old = (EditText) findViewById(R.id.et_login_pwd_change_old);
		cb_login_pwd_change_new = (CheckBox) findViewById(R.id.cb_login_pwd_change_new);
		cb_login_pwd_change_old = (CheckBox) findViewById(R.id.cb_login_pwd_change_old);
		ll_login_pwd_change_old = (LinearLayout) findViewById(R.id.ll_login_pwd_change_old);
		ll_login_pwd_change_new = (LinearLayout) findViewById(R.id.ll_login_pwd_change_new);

		iv_back.setOnClickListener(this);
		btn_login_pwd_change_confirm.setOnClickListener(this);
		// cb_login_pwd_change_new.setOnClickListener(this);
		// cb_login_pwd_change_old.setOnClickListener(this);
		ll_login_pwd_change_old.setOnClickListener(this);
		ll_login_pwd_change_new.setOnClickListener(this);
		tv_title.setText("登录密码修改");

	}

	private BizDataAsyncTask<String> changeTask;

	private void changeLoginPassword() {
		changeTask = new BizDataAsyncTask<String>(getWaitingView()) {

			@Override
			protected void onExecuteSucceeded(String result) {
				AlertUtil.t(ActivityLoginPwdChange.this, "密码已修改，请用新密码登录");

				PreferenceCache.putToken("");
				//PreferenceCache.putIfSkipLogin(false);

				//YixingApp.canQueryFromOnResume = false;
				//YixingApp.globalIndex = 0;

				Intent it = new Intent(ActivityLoginPwdChange.this, ActivityLogin.class);
				it.putExtra(ExtraConfig.IntentExtraKey.LOGIN_FROM_MAIN, true);
				startActivity(it);

				finish();

			}

			@Override
			protected String doExecute() throws ZYException, BizFailure {
				// TODO Auto-generated method stub
				String oldPassword = et_login_pwd_change_old.getText().toString().trim();
				String newPassword = et_login_pwd_change_new.getText().toString().trim();
				return AccountBiz.changeLoginPassword(oldPassword, newPassword);
			}

			@Override
			protected void OnExecuteFailed(String error) {
				if (!StringUtil.isEmpty(error)) {
					AlertUtil.t(ActivityLoginPwdChange.this, error);
				}

			}
		};

		changeTask.execute();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_back:
			finish();
			break;

		case R.id.btn_login_pwd_change_confirm:
			// 确认修改
			if (checkLogin()) {
				changeLoginPassword();
			}
			break;

		case R.id.ll_login_pwd_change_old:
			// 旧密码可见
			setHidenOld(et_login_pwd_change_old, cb_login_pwd_change_old);
			break;
		case R.id.ll_login_pwd_change_new:
			// 新密码可见
			setHidenNew(et_login_pwd_change_new, cb_login_pwd_change_new);
			break;

		default:
			break;
		}
	}

	private boolean checkLogin() {

		if (StringUtil.isEmpty(et_login_pwd_change_old.getEditableText().toString().trim())) {
			AlertUtil.t(this, "请输入原密码");
			et_login_pwd_change_old.requestFocus();
			return false;
		}
		if (StringUtil.isEmpty(et_login_pwd_change_new.getEditableText().toString().trim())) {
			AlertUtil.t(this, "请输入新密码");
			et_login_pwd_change_new.requestFocus();
			return false;
		}

		if (et_login_pwd_change_new.length() < 6 || et_login_pwd_change_new.length() > 20) { // 密码长度检查
			AlertUtil.t(this, R.string.msg_password_length_check);
			et_login_pwd_change_new.requestFocus();
			return false;
		}
		if (!Util.checkPwd(et_login_pwd_change_new.getEditableText().toString())) {
			AlertUtil.t(this, R.string.msg_pwd_pattern_check);
			et_login_pwd_change_new.requestFocus();
			return false;
		}

		return true;
	}

	/**
	 * 设置原密码可见
	 * 
	 * @param editText
	 */
	private void setHidenOld(EditText editText, CheckBox checkBox) {
		if (isHiddenOld) {
			// 设置EditText文本为可见的
			editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
			checkBox.setChecked(false);
		} else {
			// 设置EditText文本为隐藏的
			editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
			checkBox.setChecked(true);
		}
		isHiddenOld = !isHiddenOld;
		editText.postInvalidate();
		// 切换后将EditText光标置于末尾
		CharSequence charSequence = editText.getText();
		if (charSequence instanceof Spannable) {
			Spannable spanText = (Spannable) charSequence;
			Selection.setSelection(spanText, charSequence.length());
		}
	}

	/**
	 * 设置新密码可见
	 * 
	 * @param editText
	 */
	private void setHidenNew(EditText editText, CheckBox checkBox) {
		if (isHiddenNew) {
			// 设置EditText文本为可见的
			editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
			checkBox.setChecked(false);
		} else {
			// 设置EditText文本为隐藏的
			editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
			checkBox.setChecked(true);
		}
		isHiddenNew = !isHiddenNew;
		editText.postInvalidate();
		// 切换后将EditText光标置于末尾
		CharSequence charSequence = editText.getText();
		if (charSequence instanceof Spannable) {
			Spannable spanText = (Spannable) charSequence;
			Selection.setSelection(spanText, charSequence.length());
		}
	}

}