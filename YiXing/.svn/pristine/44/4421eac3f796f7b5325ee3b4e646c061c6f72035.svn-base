package com.yixing.ui.account;

import com.yixing.ExtraConfig;
import com.yixing.R;
import com.yixing.model.MyAccountModel;
import com.yixing.ui.account.ActivityMyInvest.MyAdapter;
import com.yixing.ui.base.BaseActivity;
import com.yixing.ui.widget.PagerSlidingTabStrip;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class ActivityBankCard extends BaseActivity implements OnClickListener {

	private TextView tv_title;
	private ImageView iv_back;
	
	private TextView tv_bank_card_num;
	private TextView tv_bank_card_name;
	
	private MyAccountModel mMyAccount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bank_card);
		mMyAccount = getIntent().getParcelableExtra(ExtraConfig.IntentExtraKey.MY_ACCOUNT);
		initView();
	}

	private void initView() {

		tv_title = (TextView) findViewById(R.id.tv_title);
		iv_back = (ImageView) findViewById(R.id.iv_back);
		
		tv_bank_card_name = (TextView) findViewById(R.id.tv_bank_card_name);
		tv_bank_card_num = (TextView) findViewById(R.id.tv_bank_card_num);

		tv_title.setText("银行卡");
		iv_back.setOnClickListener(this);
		tv_bank_card_num.setText(mMyAccount.getCARD_NO2());
		tv_bank_card_name.setText(mMyAccount.getUSER_NAME());
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

}
