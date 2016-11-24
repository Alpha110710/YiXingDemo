package com.yixing.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.yixing.R;
import com.yixing.ui.base.BaseActivity;
import com.yixing.utils.java.AlertUtil;
import com.yixing.utils.java.StringUtil;

/***
 * 个人信息填写修改信息页
 * 
 * @author Administrator
 * 
 */
public class ActivityPersionalAmend extends BaseActivity {
	private int type;
	private String result;
	private EditText edit;
	private Button submit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_persional_amend);
		type = getIntent().getIntExtra("type", 0);
		result = getIntent().getStringExtra("result");
		initView();
	}

	private void initView() {
		edit = (EditText) findViewById(R.id.edit_content);
		submit = (Button) findViewById(R.id.submit);
		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				
				if(StringUtil.isEmpty(edit.getEditableText().toString())){
					AlertUtil.t(ActivityPersionalAmend.this, "请输入设置内容");
					return ;
				}

				Intent intent = new Intent();
				intent.putExtra("result", edit.getText().toString().trim());
				setResult(RESULT_OK, intent);
				finish();
			}
		});
		if (type == 4) {
			setTitle("设置QQ");
			edit.setInputType(InputType.TYPE_CLASS_NUMBER);

			InputFilter[] filters = { new InputFilter.LengthFilter(10) };
			edit.setFilters(filters);

		} else if (type == 5) {
			setTitle("设置行业");
		} else if (type == 6) {
			setTitle("设置月收入");
			edit.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
		}
		if (!"".equals(result)) {
			edit.setText(result);
		} else {
			// edit.setHint("请输入要修改的内容");
		}
	}

}
