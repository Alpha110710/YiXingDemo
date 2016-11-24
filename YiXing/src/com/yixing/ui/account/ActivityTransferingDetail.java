package com.yixing.ui.account;

import com.yixing.ExtraConfig;
import com.yixing.R;
import com.yixing.model.MyTransferingModel.TRANSFERDETAILBean;
import com.yixing.ui.base.BaseActivity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class ActivityTransferingDetail extends BaseActivity implements OnClickListener{
	
	private TextView tv_title;
	private ImageView iv_back;
	private TRANSFERDETAILBean detailBean;
	private String transfer_id_num;
	
	private TextView transfer_amount;//金额
	private TextView transfer_capital;//转让金额
	private TextView transfer_fair;//公允价值
	private TextView transfer_transferAmount;//成交价格
	private TextView transfer_scale;//折让比例
	private TextView transfer_scale_Amount;//折让金额
	private TextView transfer_managefee;//服务费
	private TextView transfer_status;//状态
	private TextView transfer_time;//服务费
	private TextView transfer_id;//债权编号
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_transfer_detail);
		detailBean=(TRANSFERDETAILBean) getIntent().getParcelableExtra(ExtraConfig.IntentExtraKey.MY_TRANSFER_DETAIL_BEAN);
		transfer_id_num=getIntent().getStringExtra(ExtraConfig.IntentExtraKey.MY_TRANSFER_DETAIL_ID);
		initView();
		getData();
	}
	
	 /**
     * DETAIL_TENDER_AMOUNT : 6000.00
     * DETAIL_TRANSFER_CAPITAL : 5000.00
     * DETAIL_FAIR_VALUE : 5007.33
     * DETAIL_TRANSFER_AMOUNT : 4997.33
     * DETAIL_DISCOUNT_SCALE : 0.20
     * DETAIL_DISCOUNT_AMOUNT : 10.00
     * DETAIL_TRANSFER_TIME : 2016-08-25 11:06:46
     * DETAIL_TRANSFER_MANAGE_FEE : 100.00
     * DETAIL_TRANSFER_STATUS : 转让成功
     */

	private void getData() {
		if(detailBean!=null)
		transfer_amount.setText(detailBean.getDETAIL_TENDER_AMOUNT() + "元");
		transfer_capital.setText(detailBean.getDETAIL_TRANSFER_CAPITAL());
		transfer_fair.setText(detailBean.getDETAIL_FAIR_VALUE());
		transfer_transferAmount.setText(detailBean.getDETAIL_TRANSFER_AMOUNT());
		transfer_scale.setText(detailBean.getDETAIL_DISCOUNT_SCALE()+"%");
		transfer_scale_Amount.setText(detailBean.getDETAIL_DISCOUNT_AMOUNT()+"元");
		transfer_managefee.setText(detailBean.getDETAIL_TRANSFER_MANAGE_FEE()+"元");
		transfer_status.setText(detailBean.getDETAIL_TRANSFER_STATUS());
		transfer_time.setText(detailBean.getDETAIL_TRANSFER_TIME());
		transfer_id.setText("债权编号" + transfer_id_num);
	}

	private void initView() {
		tv_title = (TextView) findViewById(R.id.tv_title);
		iv_back = (ImageView) findViewById(R.id.iv_back);
		
		iv_back.setOnClickListener(this);
		tv_title.setText("转让详情");
		
		transfer_amount=(TextView) findViewById(R.id.transfer_amount);
		transfer_capital=(TextView) findViewById(R.id.transfer_capital);
		transfer_fair=(TextView) findViewById(R.id.transfer_fair);
		transfer_transferAmount=(TextView) findViewById(R.id.transfer_transferAmount);
		transfer_scale=(TextView) findViewById(R.id.transfer_scale);
		transfer_scale_Amount=(TextView) findViewById(R.id.transfer_scale_Amount);
		transfer_managefee=(TextView) findViewById(R.id.transfer_managefee);
		transfer_status=(TextView) findViewById(R.id.transfer_status);
		transfer_time=(TextView) findViewById(R.id.transfer_time);
		transfer_id=(TextView) findViewById(R.id.transfer_id);
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
