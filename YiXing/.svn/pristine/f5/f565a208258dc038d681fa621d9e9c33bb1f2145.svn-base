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

public class ActivityDealPwdChange extends BaseActivity implements OnClickListener {

	private TextView tv_title;
	private ImageView iv_back;
	private TextView tv_right;

	private EditText et_deal_pwd_change_old;
	private EditText et_deal_pwd_change_new;
	private Button btn_deal_pwd_change_confirm;
	private CheckBox cb_deal_pwd_change_new;
	private CheckBox cb_deal_pwd_change_old;
	private LinearLayout ll_deal_pwd_change_new;
	private LinearLayout ll_deal_pwd_change_old;

	private boolean isHiddenOld = true;
	private boolean isHiddenNew = true;
	
	private String teleNum;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_deal_pwd_change);
		teleNum = getIntent().getStringExtra(ExtraConfig.IntentExtraKey.ACCOUNT_TELE_NUM);
		initView();
	}

	private void initView() {

		tv_title = (TextView) findViewById(R.id.tv_title);
		iv_back = (ImageView) findViewById(R.id.iv_back);
		tv_right = (TextView) findViewById(R.id.tv_right);
		btn_deal_pwd_change_confirm = (Button) findViewById(R.id.btn_deal_pwd_change_confirm);
		et_deal_pwd_change_new = (EditText) findViewById(R.id.et_deal_pwd_change_new);
		et_deal_pwd_change_old = (EditText) findViewById(R.id.et_deal_pwd_change_old);
		cb_deal_pwd_change_new = (CheckBox) findViewById(R.id.cb_deal_pwd_change_new);
		cb_deal_pwd_change_old = (CheckBox) findViewById(R.id.cb_deal_pwd_change_old);
		ll_deal_pwd_change_new = (LinearLayout) findViewById(R.id.ll_deal_pwd_change_new);
		ll_deal_pwd_change_old = (LinearLayout) findViewById(R.id.ll_deal_pwd_change_old);

		iv_back.setOnClickListener(this);
		tv_right.setOnClickListener(this);
		btn_deal_pwd_change_confirm.setOnClickListener(this);
//		cb_deal_pwd_change_new.setOnClickListener(this);
//		cb_deal_pwd_change_old.setOnClickListener(this);
		ll_deal_pwd_change_old.setOnClickListener(this);
		ll_deal_pwd_change_new.setOnClickListener(this);
		tv_title.setText("投资密码修改");
		tv_right.setVisibility(View.VISIBLE);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_back:
			finish();
			break;

		case R.id.ll_deal_pwd_change_old:
			// 新密码可见
			setHidenOld(et_deal_pwd_change_old, cb_deal_pwd_change_old);
			break;
		case R.id.ll_deal_pwd_change_new:
			// 新密码可见
			setHidenNew(et_deal_pwd_change_new, cb_deal_pwd_change_new);
			break;

		case R.id.btn_deal_pwd_change_confirm:
			// 确认修改
			if (checkLogin()) {
				changedealPwd();
			}
			break;
		case R.id.tv_right:
			// 密码重置
			Intent intent = new Intent();
			intent.setClass(this, ActivityFindDealPwd.class);
			intent.putExtra(ExtraConfig.IntentExtraKey.ACCOUNT_TELE_NUM, teleNum);
			startActivity(intent);
			break;

		default:
			break;
		}
	}

	private BizDataAsyncTask<String> task;

	private void changedealPwd() {

		task = new BizDataAsyncTask<String>(getWaitingView()) {

			@Override
			protected void onExecuteSucceeded(String result) {
				AlertUtil.t(ActivityDealPwdChange.this, "投资密码修改成功");
				setResult(RESULT_OK);
				finish();
			}

			@Override
			protected String doExecute() throws ZYException, BizFailure {

				return AccountBiz.changePayPassword(et_deal_pwd_change_old.getText().toString().trim(),
						et_deal_pwd_change_new.getText().toString().trim());
			}

			@Override
			protected void OnExecuteFailed(String error) {
				if (!StringUtil.isEmpty(error)) {
					AlertUtil.t(ActivityDealPwdChange.this, error);
				}
			}

		};

		task.execute();

	}

	private boolean checkLogin() {

		if (StringUtil.isEmpty(et_deal_pwd_change_old.getEditableText().toString().trim())) {
			AlertUtil.t(this, "请输入原密码");
			et_deal_pwd_change_old.requestFocus();
			return false;
		}
		if (StringUtil.isEmpty(et_deal_pwd_change_new.getEditableText().toString().trim())) {
			AlertUtil.t(this, "请输入新密码");
			et_deal_pwd_change_new.requestFocus();
			return false;
		}

		if (et_deal_pwd_change_new.length() < 6 || et_deal_pwd_change_new.length() > 20) { // 密码长度检查
			AlertUtil.t(this, R.string.msg_password_length_check);
			et_deal_pwd_change_new.requestFocus();
			return false;
		}
		if (!Util.checkPwd(et_deal_pwd_change_new.getEditableText().toString())) {
			AlertUtil.t(this, R.string.msg_pwd_pattern_check);
			et_deal_pwd_change_new.requestFocus();
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
