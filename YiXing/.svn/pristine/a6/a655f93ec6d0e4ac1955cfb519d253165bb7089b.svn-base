package com.yixing.ui.account;

import com.yixing.ExtraConfig;
import com.yixing.R;
import com.yixing.biz.AccountBiz;
import com.yixing.biz.exception.BizFailure;
import com.yixing.biz.exception.ZYException;
import com.yixing.biz.task.BizDataAsyncTask;
import com.yixing.model.City;
import com.yixing.model.County;
import com.yixing.model.Province;
import com.yixing.model.SecurityCenterModel;
import com.yixing.ui.addressselector.BottomDialogB;
import com.yixing.ui.addressselector.OnAddressSelectedListener;
import com.yixing.ui.base.BaseActivity;
import com.yixing.utils.java.AlertUtil;
import com.yixing.utils.java.SoftInputUtil;
import com.yixing.utils.java.StringUtil;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ActivityAddressChange extends BaseActivity implements OnClickListener, OnAddressSelectedListener {

	private TextView tv_title;
	private ImageView iv_back;
	
	private Button btn_address_change_confirm;// 提交修改地址
	private LinearLayout ll_address_change;// 省 市
	private TextView tv_address_change;// 省 市
	private EditText et_address_change_detail;// 详细地址

	private BottomDialogB dialog;

	private Province mProvince;
	private City mCity;
	private SecurityCenterModel model;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_address_change);
		model = getIntent().getParcelableExtra(ExtraConfig.IntentExtraKey.SECURITY_CENTER_ADDRESS_CHANGE);

		initView();
	}

	private void initView() {
		tv_title = (TextView) findViewById(R.id.tv_title);
		iv_back = (ImageView) findViewById(R.id.iv_back);
		
		btn_address_change_confirm = (Button) findViewById(R.id.btn_address_change_confirm);
		ll_address_change = (LinearLayout) findViewById(R.id.ll_address_change);
		tv_address_change = (TextView) findViewById(R.id.tv_address_change);
		et_address_change_detail = (EditText) findViewById(R.id.et_address_change_detail);

		
		iv_back.setOnClickListener(this);
		ll_address_change.setOnClickListener(this);
		btn_address_change_confirm.setOnClickListener(this);
		tv_address_change.setText(model.getPROVINCE() + model.getCITY());
		et_address_change_detail.setText(model.getADDRESS());
		et_address_change_detail.setSelection(model.getADDRESS().length());
		tv_title.setText("收货地址");
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.ll_address_change:
			// 显示省市
			dialog = new BottomDialogB(this);
			dialog.setOnAddressSelectedListener(this);
			dialog.show();
			break;
		case R.id.btn_address_change_confirm:
			// 确认修改
			updateUserSafeCenterDetail();
			SoftInputUtil.hideSoftKeyboard(et_address_change_detail);
			break;
		case R.id.iv_back:
			finish();
			break;

		default:
			break;
		}
	}

	@Override
	public void onAddressSelected(Province province, City city, County county) {
		// TODO Auto-generated method stub
		mProvince = province;
		mCity = city;

		String s = (province == null ? "" : province.getName()) + (city == null ? "" : city.getName())
				+ (county == null ? "" : county.getName());

		tv_address_change.setText(s);

		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
		}

	}

	/**
	 * 修改收货地址任务
	 */
	private BizDataAsyncTask<String> upDateTask;

	private void updateUserSafeCenterDetail() {
		upDateTask = new BizDataAsyncTask<String>(getWaitingView()) {

			@Override
			protected void onExecuteSucceeded(String result) {
				// TODO Auto-generated method stub
				if (result.equals("1")) {
					AlertUtil.t(ActivityAddressChange.this, "收货地址修改成功");
					setResult(RESULT_OK);
					finish();
				} else {
					AlertUtil.t(ActivityAddressChange.this, "收货地址修改失败");
				}

			}

			@Override
			protected String doExecute() throws ZYException, BizFailure {
				if (mProvince == null || mCity == null) {
					return AccountBiz.updateUserSafeCenterDetail(model.getPROVINCE_ID(), model.getCITY_ID(),
							et_address_change_detail.getText().toString().trim());
				} else {
					return AccountBiz.updateUserSafeCenterDetail(mProvince.getId(), mCity.getId(),
							et_address_change_detail.getText().toString().trim());
				}

			}

			@Override
			protected void OnExecuteFailed(String error) {
				if (!StringUtil.isEmpty(error)) {
					AlertUtil.t(ActivityAddressChange.this, error);
				}

			}
		};
		upDateTask.execute();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
		}
	}

}
