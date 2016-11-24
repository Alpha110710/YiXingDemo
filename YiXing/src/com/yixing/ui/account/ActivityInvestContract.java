package com.yixing.ui.account;

import com.yixing.ExtraConfig;
import com.yixing.R;
import com.yixing.biz.AccountBiz;
import com.yixing.biz.exception.BizFailure;
import com.yixing.biz.exception.ZYException;
import com.yixing.biz.task.BizDataAsyncTask;
import com.yixing.model.InvestContractModel;
import com.yixing.ui.base.BaseActivity;
import com.yixing.utils.java.AlertUtil;
import com.yixing.utils.java.StringUtil;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class ActivityInvestContract extends BaseActivity implements OnClickListener{
	
	private TextView tv_title;
	private ImageView iv_back;
	
	private String tenderId;
	private TextView tv_contract_one;
	private TextView tv_contract_two;
	private TextView tv_contract_three;
	private TextView tv_contract_four;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_invest_contract);
		tenderId = getIntent().getStringExtra(ExtraConfig.IntentExtraKey.RETURN_MONEY_DETAIL_ONE_ID);
		initView();
	}

	private void initView() {
		tv_title = (TextView) findViewById(R.id.tv_title);
		iv_back = (ImageView) findViewById(R.id.iv_back);
		tv_contract_one = (TextView) findViewById(R.id.tv_contract_one);
		tv_contract_two = (TextView) findViewById(R.id.tv_contract_two);
		tv_contract_three = (TextView) findViewById(R.id.tv_contract_three);
		tv_contract_four = (TextView) findViewById(R.id.tv_contract_four);

		iv_back.setOnClickListener(this);
		tv_title.setText("投资合同");
		
		showProductsInfo();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_back:
			finish();
			break;

		default:
			break;
		}
		
	}
	
	private BizDataAsyncTask<InvestContractModel> task;
	private void showProductsInfo() {
		task = new BizDataAsyncTask<InvestContractModel>() {
			
			@Override
			protected void onExecuteSucceeded(InvestContractModel result) {
				tv_contract_one.setText(Html.fromHtml(result.getTopContent()));
				tv_contract_two.setText(Html.fromHtml(result.getCenterContent()));
				tv_contract_three.setText(Html.fromHtml(result.getFootContent()));
				tv_contract_four.setText(Html.fromHtml(result.getBorrowUser2()));
				
			}
			
			@Override
			protected InvestContractModel doExecute() throws ZYException, BizFailure {
				return AccountBiz.showProductsInfo(tenderId);
			}
			
			@Override
			protected void OnExecuteFailed(String error) {
				if (!StringUtil.isEmpty(error)) {
					AlertUtil.t(ActivityInvestContract.this, error);
				}
			}
		};
		task.execute();;
	}

}
