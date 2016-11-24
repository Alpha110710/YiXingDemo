package com.yixing.ui.account;

import com.yixing.ExtraConfig;
import com.yixing.R;
import com.yixing.biz.AccountBiz;
import com.yixing.biz.exception.BizFailure;
import com.yixing.biz.exception.ZYException;
import com.yixing.biz.task.BizDataAsyncTask;
import com.yixing.model.GetMessageInfoModel;
import com.yixing.ui.base.BaseActivity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class ActivityMessageWatch extends BaseActivity implements OnClickListener {

	private TextView tv_msg_detail_content;
	private TextView tv_msg_detail_title;

	private String id;

	private TextView tv_title;
	private ImageView iv_back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message_view);
		id = getIntent().getStringExtra(ExtraConfig.IntentExtraKey.ACCOUNT_MSG);

		initView();
	}

	private void initView() {
		tv_msg_detail_content = (TextView) findViewById(R.id.tv_msg_detail_content);
		tv_msg_detail_title = (TextView) findViewById(R.id.tv_msg_detail_title);
		tv_title = (TextView) findViewById(R.id.tv_title);
		iv_back = (ImageView) findViewById(R.id.iv_back);

		tv_title.setText("消息中心");
		iv_back.setOnClickListener(this);

		getMessageInfoModel();
	}

	/**
	 * 获取消息详细信息
	 */
	private BizDataAsyncTask<GetMessageInfoModel> task;

	private void getMessageInfoModel() {
		task = new BizDataAsyncTask<GetMessageInfoModel>() {

			@Override
			protected void onExecuteSucceeded(GetMessageInfoModel result) {

				tv_msg_detail_title.setText(result.getTITLE());
				tv_msg_detail_content.setText(result.getMSG_CONTENT());
			}

			@Override
			protected GetMessageInfoModel doExecute() throws ZYException, BizFailure {
				// TODO Auto-generated method stub
				return AccountBiz.getMessageInfo(id);
			}

			@Override
			protected void OnExecuteFailed(String error) {
				// TODO Auto-generated method stub

			}
		};
		task.execute();
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.iv_back:
			setResult(RESULT_OK);
			finish();
			break;

		default:
			break;
		}
	}

}
