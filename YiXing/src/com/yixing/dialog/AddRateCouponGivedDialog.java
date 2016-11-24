package com.yixing.dialog;

import com.yixing.R;
import com.yixing.biz.AccountBiz;
import com.yixing.biz.exception.BizFailure;
import com.yixing.biz.exception.ZYException;
import com.yixing.biz.task.BizDataAsyncTask;
import com.yixing.model.MyAddRateCouponGiveCheckModel;
import com.yixing.utils.java.AlertUtil;
import com.yixing.utils.java.StringUtil;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AddRateCouponGivedDialog extends Dialog implements android.view.View.OnClickListener, TextWatcher {

	private TextView tv_dialog_tishi;
	private TextView tv_dialog_gived_name;
	private Button btn_dialog_confirm;
	private EditText et_dialog_tele_num;
	private TextView tv_dialog_gived_tele_num;
	private Button btn_gived_confirm;
	private ImageView iv_dialog_back;
	private LinearLayout ll_dialog_down;

	private String id, mobile;
	private Context context;

	public AddRateCouponGivedDialog(Context context, int theme, String id) {
		super(context, theme);
		this.context = context;
		this.id = id;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_give_add_rate_coupon);

		initView();

	}

	private void initView() {
		et_dialog_tele_num = (EditText) findViewById(R.id.et_dialog_tele_num);// 受赠人手机号
		tv_dialog_tishi = (TextView) findViewById(R.id.tv_dialog_tishi);// 提示信息受赠人不存在
		btn_dialog_confirm = (Button) findViewById(R.id.btn_dialog_confirm);// 确认
		tv_dialog_gived_name = (TextView) findViewById(R.id.tv_dialog_gived_name);// 受赠人姓名
		tv_dialog_gived_tele_num = (TextView) findViewById(R.id.tv_dialog_gived_tele_num);// 受赠人手机号码
		btn_gived_confirm = (Button) findViewById(R.id.btn_gived_confirm);// 确认赠送
		iv_dialog_back = (ImageView) findViewById(R.id.iv_dialog_back);// 退出
		ll_dialog_down = (LinearLayout) findViewById(R.id.ll_dialog_down);// 退出

		btn_dialog_confirm.setClickable(false);
		btn_dialog_confirm.setEnabled(false);// 设置不可点击

		iv_dialog_back.setOnClickListener(this);
		btn_gived_confirm.setOnClickListener(this);
		btn_dialog_confirm.setOnClickListener(this);

		et_dialog_tele_num.addTextChangedListener(this);
	}

	/**
	 * 验证赠送好友任务
	 */
	private BizDataAsyncTask<MyAddRateCouponGiveCheckModel> checkTask;

	private void getGivedMsg() {

		checkTask = new BizDataAsyncTask<MyAddRateCouponGiveCheckModel>() {
			@Override
			protected void onExecuteSucceeded(MyAddRateCouponGiveCheckModel result) {
				tv_dialog_gived_name.setText("受赠人姓名 : " + result.getUSER_NAME());
				tv_dialog_gived_tele_num.setText("受赠人手机号码 : " + result.getMOBILE());
				// tv_dialog_tishi.setVisibility(View.INVISIBLE);
				ll_dialog_down.setVisibility(View.VISIBLE);

				mobile = result.getMOBILE();

			}

			@Override
			protected MyAddRateCouponGiveCheckModel doExecute() throws ZYException, BizFailure {
				// TODO Auto-generated method stub
				return AccountBiz.checkDoneeInfo(et_dialog_tele_num.getText().toString().trim());
			}

			@Override
			protected void OnExecuteFailed(String error) {
				// TODO Auto-generated method stub
				// tv_dialog_tishi.setVisibility(View.VISIBLE);
				// tv_dialog_tishi.setText(error);
				if (!StringUtil.isEmpty(error)) {
					AlertUtil.t(context, error);
				}
				ll_dialog_down.setVisibility(View.GONE);
			}
		};
		checkTask.execute();
	}

	/**
	 * 确认赠送任务
	 */
	private BizDataAsyncTask<String> confirmTask;

	private void interestRateTransfer() {
		confirmTask = new BizDataAsyncTask<String>() {

			@Override
			protected void onExecuteSucceeded(String result) {
				AlertUtil.t(context, "赠送成功");
				dismiss();
			}

			@Override
			protected String doExecute() throws ZYException, BizFailure {

				return AccountBiz.interestRateTransfer(mobile, id);
			}

			@Override
			protected void OnExecuteFailed(String error) {
				AlertUtil.t(context, error);
			}
		};

		confirmTask.execute();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_dialog_back:
			dismiss();
			break;

		case R.id.btn_dialog_confirm:
			// 赠送
			getGivedMsg();
			break;

		case R.id.btn_gived_confirm:
			// 确认赠送
			interestRateTransfer();
			break;

		default:
			break;
		}
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterTextChanged(Editable s) {
		// editText监听改变 button颜色
		if (StringUtil.isEmpty(s.toString().trim())) {
			// 设置按钮不可点击
			btn_dialog_confirm.setClickable(false);
			btn_dialog_confirm.setEnabled(false);
		} else {
			// 设置按钮可点击
			btn_dialog_confirm.setClickable(true);
			btn_dialog_confirm.setEnabled(true);
		}

	}

}
