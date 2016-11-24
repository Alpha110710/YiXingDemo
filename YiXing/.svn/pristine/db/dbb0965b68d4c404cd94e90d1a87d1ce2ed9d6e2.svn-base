package com.yixing.ui.account;

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

import com.yixing.R;
import com.yixing.ui.account.FragmentMyRedPackageUnused.CouponToCashSuccess;
import com.yixing.ui.base.BaseActivity;
import com.yixing.ui.widget.PagerSlidingTabStrip;

public class ActivityMyRedPackage extends BaseActivity implements OnClickListener {

	PagerSlidingTabStrip tabs;
	ViewPager pager;
	DisplayMetrics dm;

	FragmentMyRedPackageUnused unusedFrag;
	FragmentMyRedPackageUsed usedFrag;
	FragmentMyRedPackageOutData outDateFrag;
	String[] titles = { "未使用", "已使用", "已过期" };

	private TextView tv_title;
	private ImageView iv_back;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initView();
	}

	private void initView() {
		setContentView(R.layout.activity_my_red_packet);
		dm = getResources().getDisplayMetrics();
		pager = (ViewPager) findViewById(R.id.pager);
		tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);

		tv_title = (TextView) findViewById(R.id.tv_title);
		iv_back = (ImageView) findViewById(R.id.iv_back);

		tv_title.setText("我的红包");
		iv_back.setOnClickListener(this);

		pager.setAdapter(new MyAdapter(getSupportFragmentManager(), titles));
		tabs.setViewPager(pager);
		
		if (unusedFrag == null) {
			unusedFrag = new FragmentMyRedPackageUnused();
		}
		
		unusedFrag.setListener(new CouponToCashSuccess() {
			
			@Override
			public void onCouponToCashListener() {
				usedFrag.getMyRedPacket(true, true);
			}
		});
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
				if (unusedFrag == null) {
					unusedFrag = new FragmentMyRedPackageUnused();
				}
				return unusedFrag;
			case 1:
				if (usedFrag == null) {
					usedFrag = new FragmentMyRedPackageUsed();
				}
				return usedFrag;
			case 2:
				if (outDateFrag == null) {
					outDateFrag = new FragmentMyRedPackageOutData();
				}
				return outDateFrag;
			default:
				return null;
			}
		}
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