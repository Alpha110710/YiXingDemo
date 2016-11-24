package com.yixing.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yixing.MainActivity;
import com.yixing.R;
import com.yixing.YixingApp;

/***
 * 立即投资dialog
 * @author Administrator
 *
 */
public class PromptlyInvestmentDialog  extends Dialog{
	boolean type;
	Context context;
	TextView back_all;//返回投资列表页
	ImageView close;
	public PromptlyInvestmentDialog(Context context, int theme,boolean type) {
		super(context, theme);
		this.context=context;
		this.type=type;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(type==true){
			setContentView(R.layout.dialog_investment_success);
		}else{
				setContentView(R.layout.dialog_investment_failure);
				close=(ImageView) findViewById(R.id.close);
				
				close.setOnClickListener(new android.view.View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						dismiss();		
						
					}
				});
		}
		initView();
	}

	private void initView() {
		back_all=(TextView) findViewById(R.id.back_all);
		back_all.setOnClickListener(new android.view.View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(type==true){
					YixingApp.globalIndex=1;
					context.startActivity(new Intent(context,MainActivity.class));
				dismiss();
				}
				else{
					dismiss();
				}
					
				
			}
		});

	}
	
	

}









