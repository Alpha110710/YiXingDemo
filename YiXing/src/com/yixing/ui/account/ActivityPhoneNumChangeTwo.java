package com.yixing.ui.account;

import com.yixing.R;
import com.yixing.ui.base.BaseActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class ActivityPhoneNumChangeTwo extends BaseActivity implements OnClickListener {

	private TextView tv_title;
	private ImageView iv_back;
	private Button btn_reset_num_two_go_next;
	private EditText et_reset_num_two_id_num;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reset_tele_num_two);

		initView();
	}

	private void initView() {
		tv_title = (TextView) findViewById(R.id.tv_title);
		iv_back = (ImageView) findViewById(R.id.iv_back);
		btn_reset_num_two_go_next = (Button) findViewById(R.id.btn_reset_num_two_go_next);
		et_reset_num_two_id_num = (EditText) findViewById(R.id.et_reset_num_two_id_num);

		btn_reset_num_two_go_next.setOnClickListener(this);
		et_reset_num_two_id_num.setOnClickListener(this);
		iv_back.setOnClickListener(this);
		tv_title.setText("手机号修改");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_back:
			finish();
			break;
		case R.id.et_reset_num_two_id_num:
			// 身份证号
			break;
		case R.id.btn_reset_num_two_go_next:
			// 下一步
			Intent intent = new Intent();
			intent.setClass(this, ActivityPhoneNumChangeThree.class);
			startActivity(intent);
			break;

		default:
			break;
		}
	}

}
