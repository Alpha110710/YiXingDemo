package com.yixing.ui.account;

import com.yixing.ExtraConfig;
import com.yixing.R;
import com.yixing.ui.base.BaseActivity;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class ActivityUseAddRateCouponRule extends BaseActivity implements OnClickListener {

	private TextView red_packet_rule;
	private String url;

	private TextView tv_title;
	private ImageView iv_back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_use_red_packet_rule);
		url = getIntent().getStringExtra(ExtraConfig.IntentExtraKey.RED_PACKET_RULE);
		
		initView();

	}

	private void initView() {

		tv_title = (TextView) findViewById(R.id.tv_title);
		iv_back = (ImageView) findViewById(R.id.iv_back);
		red_packet_rule = (TextView) findViewById(R.id.red_packet_rule);

		red_packet_rule.setText(Html.fromHtml(url));
		iv_back.setOnClickListener(this);
		tv_title.setText("加息券使用规则");
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
