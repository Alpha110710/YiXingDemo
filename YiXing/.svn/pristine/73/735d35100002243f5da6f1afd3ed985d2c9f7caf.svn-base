package com.yixing.ui.investment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.yixing.MainActivity;
import com.yixing.R;
import com.yixing.YixingApp;
import com.yixing.YixingConfig;
import com.yixing.ExtraConfig.IntentExtraKey;
import com.yixing.biz.InvestmentBiz;
import com.yixing.biz.exception.BizFailure;
import com.yixing.biz.exception.ZYException;
import com.yixing.biz.task.BizDataAsyncTask;
import com.yixing.dialog.UserCheckDialog;
import com.yixing.model.InvestmentDetailModel;
import com.yixing.storage.PreferenceCache;
import com.yixing.ui.base.BaseActivity;
import com.yixing.ui.init.ActivityLogin;
import com.yixing.ui.init.ActivityWebView;
import com.yixing.utils.java.AlertUtil;
import com.yixing.utils.java.StringUtil;

/***
 * 理财计划A e兴车贷
 * 
 * @author Administrator
 * 
 */

public class InvestmentDetailActivity extends BaseActivity implements
		OnClickListener, OnTouchListener {
	private LinearLayout more;// 更多
	private GestureDetector mGestureDetector;
	private InvestmentDetailModel investmentDetailModel;
	private TextView investmentNumber;// 产品编号
	private ImageView investmentType;// 产品类型(担保贷、e兴车贷)
	private TextView investmentTitle;// 产品标题
	private TextView investmentRewards;// * 投资奖励
	private TextView earningRate;// * 年华收益
	private TextView productDeadline;// * 产品期限
	private TextView productDeadlineType;// * 产品期限单位
	private TextView productMoney;// * 融资金额
	private TextView productMoneyType;// * 融资金额单位
	private TextView productRemainMoney; // 可投金额
	private TextView productRemainMoneyType; // 可投金额单位
	private TextView modeRepayment; // 还款方式
	private TextView productText;// 协议范本
	private Button investmentSubmit;// 立即投资
	private String productId;// 标的id
	private LinearLayout investment_center;
	private LinearLayout investment_touch;// 要滑动的控件

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_investment_detail_one);
		initView();
		setTitle("投资详情");// 给title赋值
		productId = getIntent().getStringExtra("productId");
		getInvestmentDetail(true, productId);

	}

	private void initView() {
		investment_center = (LinearLayout) findViewById(R.id.investment_center);
		investment_touch = (LinearLayout) findViewById(R.id.investment_touch);
		// mGestureDetector = new GestureDetector(this,new MyGestureListener());
		investmentNumber = (TextView) findViewById(R.id.investment_number);
		investmentType = (ImageView) findViewById(R.id.investment_type);
		investmentTitle = (TextView) findViewById(R.id.investment_title);
		investmentRewards = (TextView) findViewById(R.id.investmentRewards);
		earningRate = (TextView) findViewById(R.id.earningRate);
		productDeadline = (TextView) findViewById(R.id.productDeadline);
		productDeadlineType = (TextView) findViewById(R.id.productDeadlineType);
		productMoney = (TextView) findViewById(R.id.productMoney);
		productMoneyType = (TextView) findViewById(R.id.productMoneyType);
		productRemainMoney = (TextView) findViewById(R.id.product_Remain_Money);
		productRemainMoneyType = (TextView) findViewById(R.id.product_Remain_Money_Type);
		modeRepayment = (TextView) findViewById(R.id.modeRepayment);
		productText = (TextView) findViewById(R.id.investment_text);
		productText.setOnClickListener(this);
		investmentSubmit = (Button) findViewById(R.id.investment_submit);
		investmentSubmit.setOnClickListener(this);
		
	}

	/*
	 * @Override public boolean onTouchEvent(MotionEvent event) { if
	 * (mGestureDetector.onTouchEvent(event)) return true; else return false; }
	 */
	/*
	 * class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
	 * 
	 * private int minVelocity = 0; private int verticalMinDistance = -150;
	 * 
	 * public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
	 * float velocityY) {
	 * 
	 * if (e2.getY() - e1.getY() < verticalMinDistance && Math.abs(velocityX) >
	 * minVelocity) { Intent intent=new
	 * Intent(InvestmentDetailActivity.this,InvestmentDetailMoreActivity.class);
	 * intent.putExtra("productsId", productId); startActivity(intent);
	 * overridePendingTransition(R.anim.push_bottom_in, R.anim.push_bottom_out);
	 * }
	 * 
	 * return false; }
	 * 
	 * }
	 */

	private BizDataAsyncTask<InvestmentDetailModel> getInvestmentDetail;

	/**
	 * 获取数据
	 */
	private void getInvestmentDetail(boolean isFirst, final String productId) {
		getInvestmentDetail = new BizDataAsyncTask<InvestmentDetailModel>() {

			@Override
			protected void onExecuteSucceeded(InvestmentDetailModel result) {
				investmentDetailModel = result;
				getData();
			}

			@Override
			protected InvestmentDetailModel doExecute() throws ZYException,
					BizFailure {
				return InvestmentBiz.getData(productId);
			}

			@Override
			protected void OnExecuteFailed(String error) {
			}
		};
		
		 if (isFirst) { 
			 getInvestmentDetail.setWaitingView(getWaitingView());
		  }
		 

		getInvestmentDetail.execute();
	}

	/**
	 * 借口获取的数据进行绑定
	 */
	@SuppressLint("NewApi") private void getData() {
		// 根据类型显示不同的图片
		if("担保贷".equals(investmentDetailModel.getType()))
			investmentType.setImageResource(R.drawable.transfer_item4);
		else if("银行保荐".equals(investmentDetailModel.getType()))
			investmentType.setImageResource(R.drawable.transfer_item9);
		else if("e兴车贷".equals(investmentDetailModel.getType()))
			investmentType.setImageResource(R.drawable.transfer_item5);
		else if("e兴房贷".equals(investmentDetailModel.getType()))
			investmentType.setImageResource(R.drawable.transfer_item2);
		else if("助教贷".equals(investmentDetailModel.getType()))
			investmentType.setImageResource(R.drawable.transfer_item6);
		else if("助农贷".equals(investmentDetailModel.getType()))
			investmentType.setImageResource(R.drawable.transfer_item8);
		else if("创业贷".equals(investmentDetailModel.getType()))
			investmentType.setImageResource(R.drawable.transfer_item1);
		else if("信用贷".equals(investmentDetailModel.getType()))
			investmentType.setImageResource(R.drawable.transfer_item7);
		else if("公益贷".equals(investmentDetailModel.getType()))
			investmentType.setImageResource(R.drawable.transfer_item3);
 
		if(!"立即投资".equals(investmentDetailModel.getFlag()))//图标变灰色不可点击
		{
			investmentSubmit.setBackground(getResources().getDrawable(R.drawable.shape_grey_large_round));
			investmentSubmit.setClickable(false);
		}
		investmentNumber.setText("项目编号："+investmentDetailModel.getProductsCode());
		investmentSubmit.setText(investmentDetailModel.getFlag());
		investment_touch.setOnTouchListener(this);
		
		investmentTitle.setText(investmentDetailModel.getTitle());
		earningRate.setText(investmentDetailModel.getEarning_Rate());
		productDeadline.setText(investmentDetailModel.getProduct_Deadline());
		productDeadlineType.setText(investmentDetailModel
				.getProduct_Deadline_Type());
		/*if (investmentDetailModel.getProduct_Money().indexOf(".") != -1)
			productMoney.setText(investmentDetailModel.getProduct_Money()
					.subSequence(
							0,
							investmentDetailModel.getProduct_Money().indexOf(
									".")));
		else*/
			productMoney.setText(investmentDetailModel.getProduct_Money());
		productMoneyType.setText(investmentDetailModel.getProduct_Money_type());
		/*if (investmentDetailModel.getProduct_Remain_Money().indexOf(".") != -1)
			productRemainMoney.setText(investmentDetailModel
					.getProduct_Remain_Money().subSequence(
							0,
							investmentDetailModel.getProduct_Remain_Money()
									.indexOf(".")));
		else*/
			productRemainMoney.setText(investmentDetailModel
					.getProduct_Remain_Money());
		productRemainMoneyType.setText(investmentDetailModel
				.getProduct_Remain_Money_Type());
		modeRepayment.setText(investmentDetailModel.getModeRepayment());
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.investment_text:// 协议范本
			
			Intent webView = new Intent(InvestmentDetailActivity.this,
					ActivityWebView.class);
			if("e兴车贷".equals(investmentDetailModel.getType())){
				webView.putExtra(IntentExtraKey.WEB_VIEW_FROM, 7);
			}else{
				webView.putExtra(IntentExtraKey.WEB_VIEW_FROM, 2);
			}
			
			startActivity(webView);
			break;
		case R.id.investment_submit:// 立即投资
			// 走接口判断用户是否可以进行投资
			if (StringUtil.isEmpty(PreferenceCache.getToken())) {
				Intent itToRegist = new Intent(InvestmentDetailActivity.this,
						ActivityLogin.class);
				startActivity(itToRegist);
			} else {
				getCheck();
			}

			break;
		default:
			break;
		}
	}

	public int lastY;
	public int top, bottom;

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		int ea = event.getAction();
		final int screenHeight = investment_center.getHeight();
		switch (v.getId()) {
		case R.id.investment_touch:
			switch (ea) {
			case MotionEvent.ACTION_DOWN:
				top = v.getTop();
				bottom = v.getBottom();
				// 获取触摸事件触摸位置的原始y坐标
				lastY = (int) event.getRawY();
				break;
			case MotionEvent.ACTION_MOVE:
				int dy = (int) event.getRawY() - lastY;
				int b = bottom + dy;
				int t = top + dy;
				// 下面判断移动是否超出屏幕
				if (t < 0) {
					t = 0;
					b = t + v.getHeight();
				}
				if (b > screenHeight) {
					b = screenHeight;
					t = b - v.getHeight();
				}
				v.layout(v.getLeft(), t, v.getRight(), b);
				v.postInvalidate();
				break;
			case MotionEvent.ACTION_UP:
				v.layout(v.getLeft(), top, v.getRight(), bottom);
				Intent intent = new Intent(InvestmentDetailActivity.this,
						InvestmentDetailMoreActivity.class);
				intent.putExtra("productsId", productId);
				intent.putExtra("flag", investmentDetailModel.getFlag());
				startActivity(intent);
				overridePendingTransition(R.anim.push_bottom_in,
						R.anim.push_bottom_out);
				break;
			}
		}
		return true;
	}

	private BizDataAsyncTask<String> getCheck;

	/**
	 * check
	 */
	private void getCheck() {
		getCheck = new BizDataAsyncTask<String>() {

			@Override
			protected void onExecuteSucceeded(String result) {
				if ("0".equals(result)) {
					Intent intent = new Intent(InvestmentDetailActivity.this,
							InvestmentDetailTwoActivity.class);
					intent.putExtra("productId", productId);// 标的唯一id
					startActivity(intent);
				} else if ("1".equals(result)) {
					UserCheckDialog dialog = new UserCheckDialog(
							InvestmentDetailActivity.this, R.style.My_Dialog,true);
					dialog.show();

				} else if ("2".equals(result)) {
					AlertUtil.t(InvestmentDetailActivity.this, "没有实名认证");
				}

			}

			@Override
			protected String doExecute() throws ZYException, BizFailure {
				return InvestmentBiz.realNameAuth();
			}

			@Override
			protected void OnExecuteFailed(String error) {
			}
		};

		getCheck.execute();
	}

}
