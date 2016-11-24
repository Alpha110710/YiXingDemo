package com.yixing.ui.account;

import com.yixing.R;
import com.yixing.ui.base.BaseActivity;
import com.yixing.ui.widget.PagerSlidingTabStrip;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class ActivityMyInvest extends BaseActivity implements OnClickListener {
	private PagerSlidingTabStrip tabs;
	private ViewPager pager;

	private FragmentMyInvestBid myInvestBid;
	private FragmentMyInvestHold myInvestHold;
	private FragmentMyInvestPayment myInvestPayment;
	private String[] titles = { "持有中", "投标中", "已回款" };

	private TextView tv_title;
	private ImageView iv_back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initView();
	}

	private void initView() {
		setContentView(R.layout.activity_my_investment);
		pager = (ViewPager) findViewById(R.id.pager_my_invest);
		tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs_my_invest);
		pager.setAdapter(new MyAdapter(getSupportFragmentManager(), titles));
		tabs.setViewPager(pager);

		tv_title = (TextView) findViewById(R.id.tv_title);
		iv_back = (ImageView) findViewById(R.id.iv_back);

		tv_title.setText("我的投资");
		iv_back.setOnClickListener(this);

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
				if (myInvestHold == null) {
					myInvestHold = new FragmentMyInvestHold();
				}
				return myInvestHold;
			case 1:
				if (myInvestBid == null) {
					myInvestBid = new FragmentMyInvestBid();
				}
				return myInvestBid;
			case 2:
				if (myInvestPayment == null) {
					myInvestPayment = new FragmentMyInvestPayment();
				}
				return myInvestPayment;
			default:
				return null;
			}
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.iv_back:
			//返回
			finish();
			break;
		default:
			break;

		}

	}

}
