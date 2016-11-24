package com.yixing.ui.home;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yixing.MainActivity;
import com.yixing.R;
import com.yixing.ExtraConfig.IntentExtraKey;
import com.yixing.biz.HomeBiz;
import com.yixing.biz.exception.BizFailure;
import com.yixing.biz.exception.ZYException;
import com.yixing.biz.task.BizDataAsyncTask;
import com.yixing.model.BannerItemModel;
import com.yixing.model.HomePageModel;
import com.yixing.model.HomeRecommendModel;
import com.yixing.model.NewsAdvertisingModel;
import com.yixing.storage.PreferenceCache;
import com.yixing.ui.base.BaseFragment;
import com.yixing.ui.init.ActivityLogin;
import com.yixing.ui.init.ActivityRegist;
import com.yixing.ui.init.ActivityWebView;
import com.yixing.ui.investment.InvestmentDetailActivity;
import com.yixing.ui.widget.AutoImageView;
import com.yixing.ui.widget.AutoTextView;
import com.yixing.ui.widget.CircleProgressBar;
import com.yixing.utils.java.StringUtil;

public class FragmentHome extends BaseFragment implements View.OnClickListener,
		OnPageChangeListener {

	private ViewPager viewPager;
	private LinearLayout tipsLayout;
	/**
	 * 装点点的ImageView数组
	 */
	private ImageView[] tips;

	/**
	 * 装ImageView数组
	 */
	private ImageView[] mImageViews;
	private List<BannerItemModel> imgs;

	private PullToRefreshScrollView pullToRefreshScrollView;

	private AtomicInteger what = new AtomicInteger(0);
	private boolean isContinue = false, isRuning = true;
	private Thread thread;

	private TextView tvBorrowTitle; // 标题
	private TextView tvBorrowRate; // 利率
	private TextView tvBorrowAvailableAmount; // 可投金额
	private TextView tvBorrowPeriod; // 期限
	private TextView tvRepayStyle; // 期限
	private CircleProgressBar cpb_progress; // 进度
	private Button btnInvestNow; // 立即投资
	private TextView tvHomeRegist; // 注册登录
	private TextView tvHomeLogin; // 注册登录
	private LinearLayout llHomeRegistLogin;
	private FrameLayout flHomeRecomm;

	private AutoImageView ivNoticeImg;
	private int[] imageTypes = { R.drawable.notice_type_system,
			R.drawable.notice_type_huodong, R.drawable.notice_type_tongzhi,
			R.drawable.notice_type_new };
	private AutoTextView autoNoticeTv;

	private HomePageModel mhomedata;

	private final Handler viewHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			viewPager.setCurrentItem(msg.what);
			super.handleMessage(msg);
		}

	};

	private boolean isTvRunning = false ;
	private static int sCount = 0;
	private final Handler handler = new Handler();
	private Runnable runnable = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			// 在此处添加执行的代码
			
			sCount++;
			if (sCount >= Integer.MAX_VALUE) {
				sCount = mhomedata.getNEWS_INFO().size();
			}
			NewsAdvertisingModel mNewsModel = mhomedata.getNEWS_INFO().get(
					sCount % (mhomedata.getNEWS_INFO().size()));
			//autoNoticeTv.setImgType(Integer.parseInt(mNewsModel.getNewsImg()));
			ivNoticeImg.setImageResource(imageTypes[Integer.parseInt(mNewsModel.getNewsImg())-1]);
			autoNoticeTv.setText(mNewsModel.getTitle());
		/*	ImageLoader.getInstance().displayImage(mNewsModel.getNewsImg(),
					ivNoticeImg);*/
			//autoNoticeTv.next();
			if (mhomedata.getNEWS_INFO().size() > 1) {
				handler.postDelayed(this, 10000);// 50是延时时长
			}

		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment_home, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		init();
	}

	private void init() {

		tipsLayout = (LinearLayout) findViewById(R.id.viewGroup);

		findViewById(R.id.ll_loan_car).setOnClickListener(this);
		findViewById(R.id.ll_loan_house).setOnClickListener(this);
		findViewById(R.id.ll_loan_public).setOnClickListener(this);
		//findViewById(R.id.ll_loan_credit).setOnClickListener(this);
		findViewById(R.id.ll_loan_bank_recommend).setOnClickListener(this);
		findViewById(R.id.ll_loan_guarantee).setOnClickListener(this);

		flHomeRecomm = (FrameLayout) findViewById(R.id.fl_recommend);
		cpb_progress = (CircleProgressBar) findViewById(R.id.cpb_progress);
		cpb_progress.setOnClickListener(this);
		tvBorrowTitle = (TextView) findViewById(R.id.tv_home_borrow_title);
		tvBorrowRate = (TextView) findViewById(R.id.tv_home_borrow_rate);
		tvBorrowAvailableAmount = (TextView) findViewById(R.id.tv_home_borrow_available_amount);
		tvBorrowPeriod = (TextView) findViewById(R.id.tv_home_borrow_period);

		btnInvestNow = (Button) findViewById(R.id.btn_home_invest_now);
		btnInvestNow.setOnClickListener(this);
		llHomeRegistLogin = (LinearLayout) findViewById(R.id.ll_home_regist_login);
		tvHomeRegist = (TextView) findViewById(R.id.tv_home_regist);
		tvHomeLogin = (TextView) findViewById(R.id.tv_home_login);
		tvHomeRegist.setOnClickListener(this);
		tvHomeLogin.setOnClickListener(this);
		tvRepayStyle = (TextView) findViewById(R.id.tv_repay_style);

		ivNoticeImg = (AutoImageView) findViewById(R.id.iv_notice_img);
		autoNoticeTv = (AutoTextView) findViewById(R.id.autotv_notice);
		autoNoticeTv.setOnClickListener(this);

		pullToRefreshScrollView = (PullToRefreshScrollView) findViewById(R.id.sv_fragment_first_pull_refresh_scrollview);

		pullToRefreshScrollView.setMode(Mode.PULL_FROM_START);

		// 上拉监听函数
		pullToRefreshScrollView
				.setOnRefreshListener(new OnRefreshListener<ScrollView>() {

					@Override
					public void onRefresh(
							PullToRefreshBase<ScrollView> refreshView) {

						if (PullToRefreshBase.Mode.PULL_FROM_START == refreshView
								.getCurrentMode()) {
							getHomePageData(false);
						}

					}
				});

		viewPager = (ViewPager) findViewById(R.id.viewPager); // 设置监听，主要是设置点点的背景
		viewPager.setOnPageChangeListener(this);

		viewPager.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
				case MotionEvent.ACTION_MOVE:
					isContinue = false;
					break;
				case MotionEvent.ACTION_UP:
					isContinue = true;
					break;
				default:
					isContinue = true;
					break;
				}
				return false;
			}
		});

		thread = new Thread(new Runnable() {

			@Override
			public void run() {
				while (isRuning) {
					if (isContinue) {
						viewHandler.sendEmptyMessage(what.get());
						whatOption();
					}
				}
			}

		});

		thread.start();

		getHomePageData(true);

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (StringUtil.isEmpty(PreferenceCache.getToken())) {
			llHomeRegistLogin.setVisibility(View.VISIBLE);
		} else {
			llHomeRegistLogin.setVisibility(View.GONE);
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {

		case R.id.ll_loan_car:

			((MainActivity) getActivity()).setInvestType(1, 6, 0);

			break;
		case R.id.ll_loan_house:

			((MainActivity) getActivity()).setInvestType(1, 1, 0);

			break;
		case R.id.ll_loan_public:
			((MainActivity) getActivity()).setInvestType(1, 2, 0);

			break;
		/*case R.id.ll_loan_credit:
			((MainActivity) getActivity()).setInvestType(1, 3, 0);

			break;*/
		case R.id.ll_loan_bank_recommend:
			((MainActivity) getActivity()).setInvestType(1, 4, 0);

			break;
		case R.id.ll_loan_guarantee:
			((MainActivity) getActivity()).setInvestType(1, 3, 0);   // 5 担保贷   3 信用贷

			break;

		case R.id.btn_home_invest_now:

			if (!"立即投资".equals(mhomedata.getBULLETED_LIST().get(0)
					.getTenderConRecommend())) {
				return;
			}else{
				
				Intent intent=new Intent(getActivity(),InvestmentDetailActivity.class);
				intent.putExtra("productId",mhomedata.getBULLETED_LIST().get(0).getId());
				startActivity(intent);
			}

			break;
			
			
		case R.id.cpb_progress :
			
			Intent intent=new Intent(getActivity(),InvestmentDetailActivity.class);
			intent.putExtra("productId",mhomedata.getBULLETED_LIST().get(0).getId());
			startActivity(intent);
			break;
			

		case R.id.tv_home_regist:
			
			/*Intent itToRegist = new Intent(getActivity(), ActivityRegist.class);
			itToRegist.putExtra("TO_REGIST", "1");

			startActivity(itToRegist);*/
			
			startActivity(new Intent(getActivity(), ActivityRegist.class));

			break;
		case R.id.tv_home_login:
			
			/*Intent itToLogin = new Intent(getActivity(), ActivityLogin.class);
			itToLogin.putExtra("TO_LOGIN", "1");
			startActivity(itToLogin);*/
			startActivity(new Intent(getActivity(), ActivityLogin.class));

			break;
			
			
		case R.id.autotv_notice:
			String noticeId = mhomedata.getNEWS_INFO().get(
					sCount % (mhomedata.getNEWS_INFO().size())).getId();
			  Intent itNoticeDetail = new Intent(getActivity(), ActivityWebView.class);
			  itNoticeDetail.putExtra(IntentExtraKey.WEB_VIEW_FROM, 6);
			  itNoticeDetail.putExtra("LINKURL", noticeId);
			  startActivity(itNoticeDetail);

		default:

			break;

		}

	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub

		what.getAndSet(arg0);
		setTipsBackground(arg0);

	}

	private void setViewPagerImgs() {
		tipsLayout.removeAllViews();
		// 将点点加入到ViewGroup中
		tips = new ImageView[imgs.size()];
		for (int i = 0; i < tips.length; i++) {
			ImageView imageView = new ImageView(getActivity());
			imageView.setLayoutParams(new LayoutParams(15, 15));
			tips[i] = imageView;
			if (i == 0) {
				tips[i].setBackgroundResource(R.drawable.point_sel);
			} else {
				tips[i].setBackgroundResource(R.drawable.point_normal);
			}

			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
					new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT));
			layoutParams.leftMargin = 6;
			layoutParams.rightMargin = 6;
			tipsLayout.addView(imageView, layoutParams);
		}

		// 将图片装载到数组中
		mImageViews = new ImageView[imgs.size()];
		for (int i = 0; i < mImageViews.length; i++) {

			final int j = i;
			ImageView imageView = new ImageView(getActivity());
			imageView.setScaleType(ScaleType.FIT_XY);
			mImageViews[i] = imageView;
			ImageLoader.getInstance().displayImage(
					((BannerItemModel) imgs.get(i)).getFileUrl(), imageView); // ,
																				// options);//
																				// 显示图片

			imageView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					String linkUrl = imgs.get(j).getLinkUrl();
					// LogUtil.e("1111111111");
					if (StringUtil.isEmpty(linkUrl)) {
						return;
					}
					/*
					 * Intent itlinkUrl = new Intent(getActivity(),
					 * ActivityWebView.class); itlinkUrl.putExtra("LINKURL",
					 * linkUrl);
					 * itlinkUrl.putExtra(IntentExtraKey.WEB_VIEW_FROM, 5);
					 * startActivity(itlinkUrl);
					 */

				}
			});

		}

		// 设置Adapter
		viewPager.setAdapter(new viewPagerAdapter());
		// 设置ViewPager的默认项。
		viewPager.setCurrentItem(0);

	}

	public class viewPagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mImageViews != null ? mImageViews.length : 0;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			((ViewPager) container).removeView(mImageViews[position]);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO Auto-generated method stub

			((ViewPager) container).addView(mImageViews[position]);

			return mImageViews[position];
		}

	}

	/**
	 * 设置选中的tip的背景
	 * 
	 * @param selectItems
	 */
	private void setTipsBackground(int selectItems) {
		for (int i = 0; i < tips.length; i++) {
			if (i == selectItems) {
				tips[i].setBackgroundResource(R.drawable.point_sel);
			} else {
				tips[i].setBackgroundResource(R.drawable.point_normal);
			}
		}
	}

	private void whatOption() {
		what.incrementAndGet();
		if (what.get() > mImageViews.length - 1) {
			what.getAndAdd(-mImageViews.length);
		}
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private BizDataAsyncTask<HomePageModel> getHomePageTask;

	/**
	 * 首页数据
	 */
	private void getHomePageData(boolean isFirst) {
		getHomePageTask = new BizDataAsyncTask<HomePageModel>() {

			@Override
			protected void onExecuteSucceeded(HomePageModel result) {
				mhomedata = result;
				// 添加banner 数据 begin
				imgs = result.getADVERTISING_LIST();
				if (imgs != null) {
					setViewPagerImgs();
					what = new AtomicInteger(0);
					isContinue = true;
				}
				// 添加banner 数据 end
				updateView();
				pullToRefreshScrollView.onRefreshComplete();

			}

			@Override
			protected HomePageModel doExecute() throws ZYException, BizFailure {
				return HomeBiz.getHomePage();
			}

			@Override
			protected void OnExecuteFailed(String error) {
				// TODO Auto-generated method stub
				pullToRefreshScrollView.onRefreshComplete();
			}
		};

		if (isFirst) {
			getHomePageTask.setWaitingView(getWaitingView());
		}

		getHomePageTask.execute();
	}

	private void updateView() {

		if(mhomedata.getBULLETED_LIST().size() >= 1){
		HomeRecommendModel recommendBid = mhomedata.getBULLETED_LIST().get(0);
		if (recommendBid != null) {
			flHomeRecomm.setVisibility(View.VISIBLE);
			btnInvestNow.setVisibility(View.VISIBLE);
			cpb_progress.setProgress(Float.parseFloat(recommendBid
					.getFinanceAmountScale()));
			tvBorrowTitle.setText(recommendBid.getProductsTitle());
			tvBorrowRate.setText(recommendBid.getFinanceRate());
			tvBorrowAvailableAmount
					.setText(recommendBid.getFinanceAmountWait());
			tvBorrowPeriod.setText(recommendBid.getFinancePeriod()
					+ recommendBid.getInterestRateType());
			btnInvestNow.setText(recommendBid.getTenderConRecommend());
			if (!"立即投资".equals(recommendBid.getTenderConRecommend())) {
				btnInvestNow
						.setBackgroundResource(R.drawable.shape_grey_large_round);
			} else {
				btnInvestNow
						.setBackgroundResource(R.drawable.shape_orange_large_round);
			}
			tvRepayStyle.setText(recommendBid.getFinanceRepayType());
		}
		}

		sCount = mhomedata.getNEWS_INFO().size();

		if (sCount >= 1) {
			
			ivNoticeImg.setVisibility(View.VISIBLE);
			autoNoticeTv.setVisibility(View.VISIBLE);
			autoNoticeTv.setText(mhomedata.getNEWS_INFO().get(0).getTitle());
			//autoNoticeTv.setImgType(Integer.parseInt(mhomedata.getNEWS_INFO().get(0).getNewsImg()));
			ivNoticeImg.setImageResource(imageTypes[Integer.parseInt(mhomedata.getNEWS_INFO().get(0).getNewsImg())-1]);
			// 启动计时器
			if(!isTvRunning){
			handler.postDelayed(runnable, 10000);
			isTvRunning = true ;
			}
		}
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (getHomePageTask != null) {
			getHomePageTask.cancel(true);
		}

		if (thread != null) {
			isRuning = false;
			thread.interrupt();
		}
	}
}
