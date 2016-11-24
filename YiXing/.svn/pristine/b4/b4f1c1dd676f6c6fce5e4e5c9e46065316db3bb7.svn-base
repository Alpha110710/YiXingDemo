package com.yixing.ui.account;

import com.yixing.ExtraConfig;
import com.yixing.R;
import com.yixing.biz.AccountBiz;
import com.yixing.biz.exception.BizFailure;
import com.yixing.biz.exception.ZYException;
import com.yixing.biz.task.BizDataAsyncTask;
import com.yixing.model.GetUserTransferInfoModel;
import com.yixing.model.TransferAjaxEventModel;
import com.yixing.ui.base.BaseActivity;
import com.yixing.utils.java.AlertUtil;
import com.yixing.utils.java.SoftInputUtil;
import com.yixing.utils.java.StringUtil;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ActivityTransfer extends BaseActivity implements OnClickListener {

	private TextView tv_title;
	private ImageView iv_back;

	private Button btn_transfer;// 确认转让
	private ImageView iv_transfer_flg;// 图片
	private EditText et_transfer_amount;// 转让金额
	private EditText et_transfer_discount_amont;// 折让金额
	private TextView tv_transfer_title;// 标题
	private TextView tv_transfer_can;// 可转让金额50000元
	private TextView tv_transfer_gong_yun_value;// 公允价值
	private TextView tv_transfer_discount_proporty;// 折让比例
	private TextView tv_transfer_counter_fee;// 转让手续费
	private TextView tv_transfer_amont_actual;// 实收转让金
	private TextView tv_transfer_explain1;// 转让说明1
	private TextView tv_transfer_explain2;// 转让说明2
	private TextView transfer_allmoney;// 全额转让
	private LinearLayout ll_transfer;// 全部保围的线性布局

	private String oidTenderId, tenderFrom, financeName;
	private String mTransferAmount = "", mDiscountsAmount = "";// 转让金额, 折让金额
	private GetUserTransferInfoModel mDebtTransferInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_transfer);
		oidTenderId = getIntent().getStringExtra(ExtraConfig.IntentExtraKey.MY_TRANSFER_OID_TENDER_ID);
		tenderFrom = getIntent().getStringExtra(ExtraConfig.IntentExtraKey.MY_TRANSFER_TENDER_FROM);
		financeName = getIntent().getStringExtra(ExtraConfig.IntentExtraKey.MY_TRANSFER_FINACE_NAME);

		initView();
	}

	private void initView() {
		tv_title = (TextView) findViewById(R.id.tv_title);
		iv_back = (ImageView) findViewById(R.id.iv_back);

		btn_transfer = (Button) findViewById(R.id.btn_transfer);
		iv_transfer_flg = (ImageView) findViewById(R.id.iv_transfer_flg);
		tv_transfer_title = (TextView) findViewById(R.id.tv_transfer_title);
		et_transfer_amount = (EditText) findViewById(R.id.et_transfer_amount);
		tv_transfer_can = (TextView) findViewById(R.id.tv_transfer_can);
		tv_transfer_gong_yun_value = (TextView) findViewById(R.id.tv_transfer_gong_yun_value);
		et_transfer_discount_amont = (EditText) findViewById(R.id.et_transfer_discount_amont);
		tv_transfer_discount_proporty = (TextView) findViewById(R.id.tv_transfer_discount_proporty);
		tv_transfer_counter_fee = (TextView) findViewById(R.id.tv_transfer_counter_fee);
		tv_transfer_amont_actual = (TextView) findViewById(R.id.tv_transfer_amont_actual);
		tv_transfer_explain1 = (TextView) findViewById(R.id.tv_transfer_explain1);
		tv_transfer_explain2 = (TextView) findViewById(R.id.tv_transfer_explain2);
		transfer_allmoney = (TextView) findViewById(R.id.transfer_allmoney);

		et_transfer_discount_amont.setText("0.00");

		iv_back.setOnClickListener(this);
		btn_transfer.setOnClickListener(this);
		transfer_allmoney.setOnClickListener(this);
		tv_title.setText("转让");

		et_transfer_amount.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
				} else {
					if (mTransferAmount.equals(et_transfer_amount.getText().toString())) {
					} else {
						mTransferAmount = et_transfer_amount.getText().toString();
						if (StringUtil.isEmpty(mTransferAmount)) {
							tv_transfer_discount_proporty.setText("--");
							tv_transfer_counter_fee.setText("--");
							tv_transfer_amont_actual.setText("--");
							tv_transfer_gong_yun_value.setText("--");
						}
						getDebtTransferData();

					}
					SoftInputUtil.hideSoftKeyboard(et_transfer_amount);
				}
			}

		});

		et_transfer_discount_amont.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {

				if (hasFocus) {

				} else {
					if (mDiscountsAmount.equals(et_transfer_discount_amont.getText().toString())) {

					} else {
						mDiscountsAmount = et_transfer_discount_amont.getText().toString();

						if (StringUtil.isEmpty(mDiscountsAmount)) {
							tv_transfer_discount_proporty.setText("--");
							tv_transfer_counter_fee.setText("--");
							tv_transfer_amont_actual.setText("--");
							tv_transfer_gong_yun_value.setText("--");

						}
						getDebtTransferData();

					}
					SoftInputUtil.hideSoftKeyboard(et_transfer_discount_amont);

				}

			}

		});

		ll_transfer = (LinearLayout) findViewById(R.id.ll_transfer);
		ll_transfer.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				ll_transfer.setFocusable(true);
				ll_transfer.setFocusableInTouchMode(true);
				ll_transfer.requestFocus();

				return false;
			}
		});

		getUserTransferInfo();

	}

	/**
	 * 计算折让比例/手续费任务
	 */
	private BizDataAsyncTask<TransferAjaxEventModel> taskEvent;

	private void getDebtTransferData() {
		taskEvent = new BizDataAsyncTask<TransferAjaxEventModel>() {

			@Override
			protected void onExecuteSucceeded(TransferAjaxEventModel result) {
				tv_transfer_discount_proporty.setText(result.getDISCOUNT_RATE() + "%");
				tv_transfer_counter_fee.setText(result.getFEE() + "元");
				tv_transfer_amont_actual.setText(result.getREAL_TRANSFER_VALUE() + "元");
				tv_transfer_gong_yun_value.setText(result.getFAIR_VALUE() + "元");// 公允价值
			}

			@Override
			protected TransferAjaxEventModel doExecute() throws ZYException, BizFailure {
				// TODO Auto-generated method stub
				if (mDiscountsAmount.equals("")) {
					mDiscountsAmount = "0.00";
				}
				return AccountBiz.transferAjaxEvent(oidTenderId, tenderFrom, mTransferAmount, mDiscountsAmount);
			}

			@Override
			protected void OnExecuteFailed(String error) {
				if (!StringUtil.isEmpty(error)) {
					AlertUtil.t(ActivityTransfer.this, error);
				}

			}
		};
		taskEvent.execute();
	}

	/**
	 * 获取转让信息
	 */
	private BizDataAsyncTask<GetUserTransferInfoModel> taskInfo;

	private void getUserTransferInfo() {
		taskInfo = new BizDataAsyncTask<GetUserTransferInfoModel>() {

			@Override
			protected void onExecuteSucceeded(GetUserTransferInfoModel result) {
				tv_transfer_gong_yun_value.setText(result.getFAIR_VALUE() + "元");
				tv_transfer_can.setText("可转让金额" + result.getENABLE_AMOUNT() + "元");
				tv_transfer_title.setText(result.getPRODUCTS_TITLE());
				tv_transfer_explain1
						.setText("1.债权转让需缴纳" + result.getFEE_RATE() + "%的手续费，即手续费=转让金额*" + result.getFEE_RATE() + "%");

				selectImg(financeName, iv_transfer_flg);
				mDebtTransferInfo = result;

			}

			@Override
			protected GetUserTransferInfoModel doExecute() throws ZYException, BizFailure {
				// TODO Auto-generated method stub
				return AccountBiz.getUserTransferInfo(oidTenderId, tenderFrom);
			}

			@Override
			protected void OnExecuteFailed(String error) {
				if (!StringUtil.isEmpty(error)) {
					AlertUtil.t(ActivityTransfer.this, error);
				}

			}
		};
		taskInfo.execute();
	}

	// 确认转让
	BizDataAsyncTask<String> confirmTransferTask;

	private void confirmTransfer() {
		confirmTransferTask = new BizDataAsyncTask<String>(getWaitingView()) {

			@Override
			protected void onExecuteSucceeded(String result) {
				// TODO Auto-generated method stub
				AlertUtil.t(ActivityTransfer.this, "转让成功");
				setResult(RESULT_OK);
				finish();
			}

			@Override
			protected String doExecute() throws ZYException, BizFailure {
				// TODO Auto-generated method stub
				return AccountBiz.transferBtnClick(oidTenderId, tenderFrom, mTransferAmount, mDiscountsAmount);
			}

			@Override
			protected void OnExecuteFailed(String error) {
				if (!StringUtil.isEmpty(error)) {
					AlertUtil.t(ActivityTransfer.this, error);
				}

			}
		};
		confirmTransferTask.execute();
	}

	// 设置图标
	private void selectImg(String fianceName, ImageView img) {
		if ("担保贷".equals(fianceName))
			img.setImageResource(R.drawable.transfer_item4);
		else if ("银行保荐".equals(fianceName))
			img.setImageResource(R.drawable.transfer_item9);
		else if ("e兴车贷".equals(fianceName))
			img.setImageResource(R.drawable.transfer_item5);
		else if ("e兴房贷".equals(fianceName))
			img.setImageResource(R.drawable.transfer_item2);
		else if ("助教贷".equals(fianceName))
			img.setImageResource(R.drawable.transfer_item6);
		else if ("助农贷".equals(fianceName))
			img.setImageResource(R.drawable.transfer_item8);
		else if ("创业贷".equals(fianceName))
			img.setImageResource(R.drawable.transfer_item1);
		else if ("信用贷".equals(fianceName))
			img.setImageResource(R.drawable.transfer_item7);
		else if ("公益贷".equals(fianceName))
			img.setImageResource(R.drawable.transfer_item3);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_back:
			finish();
			break;

		case R.id.btn_transfer:
			confirmTransfer();
			break;
		case R.id.transfer_allmoney:
			if (mDebtTransferInfo != null) {

				et_transfer_amount.setText(mDebtTransferInfo.getENABLE_AMOUNT());
			}
			break;

		default:
			break;
		}
	}

}
