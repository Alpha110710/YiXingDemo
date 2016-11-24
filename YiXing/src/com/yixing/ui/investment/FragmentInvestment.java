package com.yixing.ui.investment;

import com.yixing.R;
import com.yixing.YixingApp;
import com.yixing.ui.base.BaseFragment;
import com.yixing.ui.widget.PagerSlidingTabStrip;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FragmentInvestment extends BaseFragment implements OnClickListener {
	private PagerSlidingTabStrip tabs;
	private RelativeLayout relativeSelsect;// 筛选
	private String[] titles = { "理财计划A", "理财计划B" };
	private FragmentInvestmentA fragmenta;
	private FragmentInvestmentB fragmentb;
	// private FragmentInvestmentE fragmente;
	private ViewPager pager;
	private View view;
	private final int RequestCode = 1;// activityrequest状态码
	public int type = 1;// 1理财计划A，2理财计划B
	public int productType_A = 0;// 0全部,1e兴房贷，2公益贷，3信用贷，4政府银行保荐，5担保贷,6e兴车贷
	public int rateType_A = 0;// 1 5%一下，2 6%~10%,3 e 10%以上，0全部
	public int productType_B = 0;// 0全部,1e兴房贷，2公益贷，3信用贷，4政府银行保荐，5担保贷,6e兴车贷
	public int rateType_B = 0;// 1 5%一下，2 6%~10%,3 e 10%以上，0全部
	public boolean homeInfo = false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		return view = inflater.inflate(R.layout.fragment_investment, container, false);

	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
		setTitle();
	}

	private void setTitle() {
		TextView tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setText("投资专区");
		ImageView iv_back = (ImageView) findViewById(R.id.iv_back);
		iv_back.setVisibility(View.GONE);
	}

	@Override
	public void onResume() {
		super.onResume();
		if (YixingApp.canQueryFromOnResume == true) {
			if (fragmenta != null)

				fragmenta.initHoldingList(true, true, productType_A + "", rateType_A + "");
			if (fragmentb != null)
				fragmentb.initHoldingList(true, true, productType_B + "", rateType_B + "");
			/*
			 * if (fragmente != null) fragmente.initHoldingList(true,
			 * true,"",rateType+"");
			 */
			YixingApp.canQueryFromOnResume = false;

		}

		if (homeInfo == true) {
			if (type == 3) {
				/*
				 * if (fragmente == null) fragmente = new FragmentInvestmentE();
				 * pager.setCurrentItem(2);
				 */
			} else {
				if (fragmenta == null) {
					fragmenta = new FragmentInvestmentA();
					fragmenta.homeInfo = true;
					fragmenta.productType = productType_A;
				} else {
					fragmenta.homeInfo = true;
					fragmenta.productType = productType_A;
					fragmenta.initHoldingList(true, true, productType_A + "", rateType_A + "");
				}
				pager.setCurrentItem(0);
			}
			homeInfo = false;

		}
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
		if (hidden == false) {
			onResume();
		} else {
			onPause();
		}
	}

	private void initView() {
		relativeSelsect = (RelativeLayout) view.findViewById(R.id.relative_select);
		relativeSelsect.setOnClickListener(this);
		pager = (ViewPager) view.findViewById(R.id.investment_pager);
		tabs = (PagerSlidingTabStrip) view.findViewById(R.id.investment_tabs);
		pager.setAdapter(new MyAdapter(getChildFragmentManager(), titles));
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
				if (fragmenta == null) {
					fragmenta = new FragmentInvestmentA();
				}
				return fragmenta;
			case 1:
				if (fragmentb == null) {
					fragmentb = new FragmentInvestmentB();
				}
				return fragmentb;

			/*
			 * case 2: if (fragmente == null) { fragmente = new
			 * FragmentInvestmentE(); } return fragmente;
			 */

			default:
				return null;
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.relative_select:// 筛选
			Intent intent = new Intent(getActivity(), SelectActivity.class);
			intent.putExtra("type", pager.getCurrentItem() + 1);// 当前页
			if (pager.getCurrentItem() == 0) {
				intent.putExtra("productType", productType_A);// 产品类型
				intent.putExtra("rateType", rateType_A);// 投资利率
			} else {
				intent.putExtra("productType", productType_B);// 产品类型
				intent.putExtra("rateType", rateType_B);// 投资利率
			}
			startActivityForResult(intent, RequestCode);
		default:
			break;
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == getActivity().RESULT_OK && requestCode == RequestCode) {
			type = data.getIntExtra("type", -1);
			if (type == 1) {
				productType_A = data.getIntExtra("productType", -1);
				rateType_A = data.getIntExtra("rateType", -1);
				fragmenta.productType = productType_A;
				fragmenta.rateType = rateType_A;
				fragmenta.initHoldingList(true, true, productType_A + "", rateType_A + "");
			} else if (type == 2) {
				productType_B = data.getIntExtra("productType", -1);
				rateType_B = data.getIntExtra("rateType", -1);
				fragmentb.initHoldingList(true, true, productType_B + "", rateType_B + "");
				fragmentb.productType = productType_B;
				fragmentb.rateType = rateType_B;

			}
			/*
			 * else if(type==3){ fragmente.initHoldingList(true,
			 * true,"6",rateType+""); fragmente.rateType=rateType; }
			 */
		}
	}

}
