package com.yixing.dialog;

import com.yixing.MainActivity;
import com.yixing.R;
import com.yixing.YixingApp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LoanSuccessDialog extends Dialog implements android.view.View.OnClickListener {

	private Context context;
	private String num;

	private TextView tv_dialog_loan_success_num;
	private TextView tv_dialog_loan_success_go_main;

	public LoanSuccessDialog(Context context, int theme, String num) {
		super(context, theme);
		this.context = context;
		this.num = num;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_loan_success);

		initView();
	}

	private void initView() {

		setCancelable(false);// 设置点击外部不可消失

		tv_dialog_loan_success_num = (TextView) findViewById(R.id.tv_dialog_loan_success_num);// 借款编号
		tv_dialog_loan_success_go_main = (TextView) findViewById(R.id.tv_dialog_loan_success_go_main);// 返回首页

		tv_dialog_loan_success_num.setText(num);
		tv_dialog_loan_success_go_main.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_dialog_loan_success_go_main:
			dismiss();
			break;

		default:
			break;
		}
	}
}
