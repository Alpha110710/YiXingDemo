package com.yixing.ui.more;

import com.yixing.R;
import com.yixing.ui.base.BaseFragment;
import com.yixing.ui.investment.FragmentInvestmentA;
import com.yixing.ui.investment.FragmentInvestmentB;
import com.yixing.ui.investment.FragmentInvestmentE;
import com.yixing.ui.investment.FragmentInvestment.MyAdapter;
import com.yixing.ui.widget.PagerSlidingTabStrip;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FragmentMore extends BaseFragment{
	
	private PagerSlidingTabStrip tabs;
	private String[] titles = { "平台简介", "最新公告", "联系我们" };
	private ViewPager pager;
	
	private FragmentContactUs  fragmentContactUs ;
	private FragmentNewsNotice  fragmentNewsNotice ;
	private FragmentPlatIntro  fragmentPlatIntro;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment_more, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		initView();
		setTitle();
	}
	
	private void setTitle() {
		TextView tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setText("关于我们");
		ImageView iv_back = (ImageView) findViewById(R.id.iv_back);
		iv_back.setVisibility(View.GONE);
	}
	
	private void initView() {
		pager = (ViewPager) findViewById(R.id.vp_more);
		tabs = (PagerSlidingTabStrip) findViewById(R.id.more_tab);
		pager.setAdapter(new MyAdapter(getFragmentManager(), titles));
		tabs.setViewPager(pager);
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
				if (fragmentPlatIntro == null) {
					fragmentPlatIntro = new FragmentPlatIntro();
				}
				return fragmentPlatIntro;
				
			case 1:
				if (fragmentNewsNotice == null) {
					fragmentNewsNotice = new FragmentNewsNotice();
				}
				return fragmentNewsNotice;
			case 2:
				if (fragmentContactUs == null) {
					fragmentContactUs = new FragmentContactUs();
				}
				return fragmentContactUs;
			default:
				return null;
			}
		}
	}


}
