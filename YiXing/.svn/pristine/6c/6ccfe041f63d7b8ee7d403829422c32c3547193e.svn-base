package com.yixing.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yixing.MainActivity;
import com.yixing.R;
import com.yixing.YixingApp;

/***
 * 红包进入该页面
 * 
 * @author Administrator
 *
 */
public class UserCheckDialog extends Dialog  {
	private TextView dialog_Return;//返回
	private TextView dialog_title;//标题
	private boolean type;//判断是投资进入该页面还是注册进入该界面(true立即投资进入)
	private Context context ;
	public UserCheckDialog(Context context, int theme,boolean type) {
		super(context, theme);
		this.type=type;
		this.context = context ;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_register_success);
		initView();
		
	}

	private void initView() {
		dialog_Return=(TextView) findViewById(R.id.dialog_return);
		dialog_title=(TextView) findViewById(R.id.dialog_title);
		if(type){
			dialog_Return.setText("知道了");
			dialog_title.setText("温馨提示");
		}
		dialog_Return.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				if(!type){
					YixingApp.globalIndex = 0;
					Intent intent = new Intent(context ,MainActivity.class);
					context.startActivity(intent);  
				}
				dismiss();
			}
		});
	}



}
