package com.yixing.ui.investment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.yixing.R;
import com.yixing.biz.InvestmentBiz;
import com.yixing.biz.exception.BizFailure;
import com.yixing.biz.exception.ZYException;
import com.yixing.biz.task.BizDataAsyncTask;
import com.yixing.dialog.UserCheckDialog;
import com.yixing.storage.PreferenceCache;
import com.yixing.ui.base.BaseActivity;
import com.yixing.ui.init.ActivityLogin;
import com.yixing.ui.widget.PagerSlidingTabStrip;
import com.yixing.utils.java.AlertUtil;
import com.yixing.utils.java.StringUtil;

public class InvestmentDetailMoreActivity extends BaseActivity{
	private String[] titles = { "项目信息", "风控措施", "投标记录" };
	private ViewPager pager;
	private Button submit;
	private PagerSlidingTabStrip tabs;
	private FragmentProductMessage produceMessage;//项目信息
	private FragmentControl  control ;//风控措施
	private FragmentRecords records;//投标记录
	private String productsId;//标的唯一id
	private String flag;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_investment_detail_more);
		setTitle("投资专区");
		productsId=getIntent().getStringExtra("productsId");
		flag=getIntent().getStringExtra("flag");
		initView();
	}
	@SuppressLint("NewApi") private void initView() {
		submit=(Button) findViewById(R.id.submit);
		if(!"立即投资".equals(flag)){
			submit.setBackground(getResources().getDrawable(R.drawable.shape_grey_large_round));
			submit.setClickable(false);
		}
		submit.setText(flag);
		if(submit.isClickable()){
			submit.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// 走接口判断用户是否可以进行投资
					if (StringUtil.isEmpty(PreferenceCache.getToken())) {
						Intent itToRegist = new Intent(InvestmentDetailMoreActivity.this,
								ActivityLogin.class);
						startActivity(itToRegist);
					} else {
						getCheck();
					}
				}
			});
		}
		pager = (ViewPager)findViewById(R.id.investment_pager);
		tabs = (PagerSlidingTabStrip) findViewById(R.id.investment_tabs);
		pager.setAdapter(new MyAdapter(getSupportFragmentManager(), titles));
		tabs.setViewPager(pager);		
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
					Intent intent=new Intent(InvestmentDetailMoreActivity.this,InvestmentDetailTwoActivity.class);
					intent.putExtra("productId", productsId);//标的唯一id
					startActivity(intent);
				} else if ("1".equals(result)) {
					UserCheckDialog dialog = new UserCheckDialog(
							InvestmentDetailMoreActivity.this, R.style.My_Dialog,true);
					dialog.show();

				} else if ("2".equals(result)) {
					AlertUtil.t(InvestmentDetailMoreActivity.this, "没有实名认证");
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
	
	public class MyAdapter extends FragmentPagerAdapter {
		String[] _titles;

		public MyAdapter(FragmentManager fm, String[] titles) {
			super(fm);
			_titles = titles;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return _titles[position];
		}

		@Override
		public int getCount() {
			return _titles.length;
		}

		@Override
		public Fragment getItem(int position) {
			switch (position) {
			case 0:
				if (produceMessage == null) {
					produceMessage = new FragmentProductMessage();
					Bundle bundle = new Bundle();  
					bundle.putString("productId",productsId);  
					produceMessage.setArguments(bundle);  
				}
				return produceMessage;
			case 1:
				if (control == null) {
					control = new FragmentControl();
					Bundle bundle = new Bundle();  
					bundle.putString("productId",productsId);  
					control.setArguments(bundle);  
				}
				return control;
			case 2:
				if (records == null) {
					records = new FragmentRecords();
					Bundle bundle = new Bundle();  
					bundle.putString("productId",productsId);  
					records.setArguments(bundle);  
				}
				return records;
			default:
				return null;
			}
		}
	}

}
