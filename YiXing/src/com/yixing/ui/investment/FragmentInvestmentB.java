package com.yixing.ui.investment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.yixing.ExtraConfig;
import com.yixing.R;
import com.yixing.YixingApp;
import com.yixing.biz.InvestmentBiz;
import com.yixing.biz.exception.BizFailure;
import com.yixing.biz.exception.ZYException;
import com.yixing.biz.task.BizDataAsyncTask;
import com.yixing.model.BaseModel;
import com.yixing.model.InvestmentModel;
import com.yixing.ui.base.BaseFragment;
import com.yixing.ui.investment.FragmentInvestmentA.Myadapter.ViewHolder;
import com.yixing.ui.widget.CircleProgressBar;
import com.yixing.utils.java.AlertUtil;

public class FragmentInvestmentB extends BaseFragment {
	private View view;
	PullToRefreshListView pullToRefreshListView;
	private ListView listView;
	private Myadapter adapter;
	TextView tv;
	private int pageIndex = 0;
	public int productType=0;//0全部,1e兴房贷，2公益贷，3信用贷，4政府银行保荐，5担保贷
	public int rateType=0;//1 5%一下，2 6%~10%,3 e 10%以上，0全部
		
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				adapter.editList();
				break;
			}
		};
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return view = inflater.inflate(R.layout.fragment_investment_item, null);
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
		initHoldingList(true, true,productType+"",rateType+"");
	}

	@Override
	public void onResume() {
		super.onResume();
		handler.removeMessages(1);//必须添加不然viewpager来回滑动的时候倒计时时间混乱
		handler.sendEmptyMessageDelayed(1, 1000);
		
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		handler.removeMessages(1);
	}


	private void initView() {
		adapter = new Myadapter();
		pullToRefreshListView = (PullToRefreshListView) view
				.findViewById(R.id.omvestment_pull);
		pullToRefreshListView.setMode(Mode.BOTH);
		pullToRefreshListView.getRefreshableView().setAdapter(adapter);
		pullToRefreshListView.getRefreshableView().setOnItemClickListener(
				new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						Intent intent=new Intent(getActivity(),ZQDetailActivity.class);
						intent.putExtra("transferId",adapter.getList().get(position-1).getZqId());
						intent.putExtra("productId",adapter.getList().get(position-1).getId());
						startActivity(intent);
					}
				});
		pullToRefreshListView
				.setOnRefreshListener(new OnRefreshListener2<ListView>() {

					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						initHoldingList(false, true,productType+"",rateType+"");

					}

					@Override
					public void onPullUpToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						initHoldingList(false, false,productType+"",rateType+"");
					}
				});

		tv = new TextView(getActivity());
		tv.setGravity(Gravity.CENTER);
		tv.setText("暂无数据");
	}

	private boolean end = false;
	private BizDataAsyncTask<List<BaseModel>> getHoldingList;

	// 访问接口获取数据
	public void initHoldingList(final boolean first, final boolean isPullDown,final String productType,final String rateType) {
		getHoldingList = new BizDataAsyncTask<List<BaseModel>>() {

			@Override
			protected List<BaseModel> doExecute() throws ZYException,
					BizFailure {
				if (isPullDown) {

					return InvestmentBiz.myInvestment("2", 0 + "",
							ExtraConfig.DEFAULT_PAGE_COUNT + "",productType,rateType);
				} else {
					if (end) {
						return new ArrayList<BaseModel>();
					} else {
						return InvestmentBiz.myInvestment("2", pageIndex + "",
								ExtraConfig.DEFAULT_PAGE_COUNT + "",productType,rateType);
					}
				}
			}

			@SuppressWarnings("deprecation")
			@Override
			protected void onExecuteSucceeded(List<BaseModel> result) {
				// TODO Auto-generated method stub

				if (result.size() < ExtraConfig.DEFAULT_PAGE_COUNT) {

					end = true;
					if (isPullDown && result.size() == 0) {
						pullToRefreshListView.setEmptyView(tv);
					}
					pullToRefreshListView.setPullLabel("没有更多数据",
							Mode.PULL_FROM_END);
					pullToRefreshListView.setReleaseLabel("没有更多数据",
							Mode.PULL_FROM_END);
					pullToRefreshListView.setRefreshingLabel("没有更多数据",
							Mode.PULL_FROM_END);
				} else {
					end = false;
					pullToRefreshListView.setPullLabel("上拉刷新",
							Mode.PULL_FROM_END);
					pullToRefreshListView.setReleaseLabel("放开以刷新",
							Mode.PULL_FROM_END);
					pullToRefreshListView.setRefreshingLabel("正在载入",
							Mode.PULL_FROM_END);
				}

				if (isPullDown) {
					pageIndex = 0;
					adapter.removeAll();
				}
				pageIndex++;

				for (int i = 0; i < result.size(); i++) {
					adapter.addItem((InvestmentModel) result.get(i));
				}
				adapter.notifyDataSetChanged();

				pullToRefreshListView.onRefreshComplete();

			}

			@Override
			protected void OnExecuteFailed(String error) {
				// TODO Auto-generated method stub
				pullToRefreshListView.onRefreshComplete();
			}

		};
		
		 if (first) { getHoldingList.setWaitingView(getWaitingView()); }
		 
		 getHoldingList.execute();

	}

	// 时间相减返回差值
	private String getData(String begin, String end) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String data = "00:00:00";
		try {
			Date d1 = df.parse(end);
			Date d2 = df.parse(begin);
			long diff = d1.getTime() - d2.getTime();// 这样得到的差值是微秒级别
			Date date = new Date(diff);
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			// 进行格式化
			data = sdf.format(date);
		} catch (Exception e) {
		}
		return data;
	}

	// 将String类型的时间加以秒
	private String getDateUp(String end) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
		Date date = null;
		try {
			date = sdf.parse(end);
		} catch (ParseException e) {
			e.printStackTrace();
			return end;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.setTimeInMillis(calendar.getTimeInMillis() - 1);
		// calendar.add (Calendar.SECOND, 1);
		return sdf.format(calendar.getTime());
	}

	class Myadapter extends BaseAdapter {
		private List<InvestmentModel> list = new ArrayList<InvestmentModel>();

		@Override
		public int getCount() {
			return list.size();
		}

		public List<InvestmentModel> getList(){
			return list;
		}
		// 添加数据
		public void addItem(InvestmentModel investmentModel) {
			list.add(investmentModel);
		}

		public void editList() {
				for(int i=0;i<list.size();i++){
					if("未发布".equals(list.get(i).getInvestment_Type())){//显示倒计时
						String begin=list.get(i).getInvestment_Time_Begin();
						if(list.get(i).getInvestment_Time_End().equals(begin))
							list.get(i).setInvestment_Time_End(begin);
							else 
								list.get(i).setInvestment_Time_End(getDateUp(list.get(i).getInvestment_Time_End()));
					}
				}
				notifyDataSetChanged();
		handler.sendEmptyMessageDelayed(1, 1000);
		}

		// 移除所有数据
		public void removeAll() {
			if (list != null && list.size() > 0) {
				for (int i = list.size() - 1; i >= 0; i--) {
					list.remove(i);
				}
			}
		}

		@Override
		public Object getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final ViewHolder viewHodler;
			if (convertView == null) {
				viewHodler = new ViewHolder();
				convertView = LayoutInflater.from(getActivity()).inflate(
						R.layout.item_listview_manage_money_plan_a, null);
				viewHodler.type=(ImageView) convertView.findViewById(R.id.product_type);
				viewHodler.title=(TextView) convertView.findViewById(R.id.fragmenta_title);
				viewHodler.investment_Rewards=(TextView) convertView.findViewById(R.id.tv_item_manage_money_award);
				viewHodler.earning_Rate=(TextView) convertView.findViewById(R.id.product_earning_Rate);
				viewHodler.product_Deadline=(TextView) convertView.findViewById(R.id.product_Deadline);
				viewHodler.product_Money=(TextView) convertView.findViewById(R.id.product_Money);
				viewHodler.investment_Schedule=(CircleProgressBar) convertView.findViewById(R.id.investment_Schedule);
				viewHodler.product_Money_Type=(TextView) convertView.findViewById(R.id.product_Money_dw);
				viewHodler.product_Deadline_Type=(TextView) convertView.findViewById(R.id.product_Deadline_dw);
				viewHodler.investment_Time=(TextView) convertView.findViewById(R.id.investment_time_back);
				viewHodler.investment_type=(LinearLayout) convertView.findViewById(R.id.investment_type);
				viewHodler.investmentType=(ImageView) convertView.findViewById(R.id.investment_image1);
				viewHodler.hktype=(TextView) convertView.findViewById(R.id.hktype);
				viewHodler.investmentjiangli=(TextView) convertView.findViewById(R.id.investment_jiangli);
				
				
				viewHodler.investment_money=(TextView) convertView.findViewById(R.id.investment_money);
				viewHodler.investment_deadline=(TextView) convertView.findViewById(R.id.investment_deadline);
				viewHodler.investment_income=(TextView) convertView.findViewById(R.id.investment_income);
				convertView.setTag(viewHodler);
			} else {
				viewHodler = (ViewHolder) convertView.getTag();
			}
			if("担保贷".equals(list.get(position).getType()))
				viewHodler.type.setImageResource(R.drawable.transfer_item4);
			else if("银行保荐".equals(list.get(position).getType()))
				viewHodler.type.setImageResource(R.drawable.transfer_item9);
			else if("e兴车贷".equals(list.get(position).getType()))
				viewHodler.type.setImageResource(R.drawable.transfer_item5);
			else if("e兴房贷".equals(list.get(position).getType()))
				viewHodler.type.setImageResource(R.drawable.transfer_item2);
			else if("助教贷".equals(list.get(position).getType()))
				viewHodler.type.setImageResource(R.drawable.transfer_item6);
			else if("助农贷".equals(list.get(position).getType()))
				viewHodler.type.setImageResource(R.drawable.transfer_item8);
			else if("创业贷".equals(list.get(position).getType()))
				viewHodler.type.setImageResource(R.drawable.transfer_item1);
			else if("信用贷".equals(list.get(position).getType()))
				viewHodler.type.setImageResource(R.drawable.transfer_item7);
			else if("公益贷".equals(list.get(position).getType()))
				viewHodler.type.setImageResource(R.drawable.transfer_item3);
			
			viewHodler.title.setText(list.get(position).getTitle());
			viewHodler.investment_Rewards.setVisibility(View.VISIBLE);
			viewHodler.investmentjiangli.setVisibility(View.VISIBLE);
			viewHodler.investment_Rewards.setText(list.get(position).getInvestment_Rewards()+"%");
			viewHodler.earning_Rate.setText(list.get(position).getEarning_Rate());
//			viewHodler.product_Deadline.setText(list.get(position).getProduct_Deadline());//接口返回带单位
			viewHodler.product_Money.setText(list.get(position).getProduct_Money());//接口返回带单位
			viewHodler.investment_Schedule.setText(list.get(position).getInvestment_Schedule()+"%");
			viewHodler.product_Money_Type.setText(list.get(position).getProduct_Money_type());
//			viewHodler.product_Deadline_Type.setText(list.get(position).getProduct_Deadline_Type());
			viewHodler.product_Deadline.setText(list.get(position).getRECOVER_DATE());
			viewHodler.product_Deadline_Type.setText(list.get(position).getRECOVER_DATE_CP());
			viewHodler.hktype.setText(list.get(position).getFINANCE_REPAY_TYPE());
			
			
			viewHodler.investment_money.setText("转让金额");
			viewHodler.investment_deadline.setText("剩余期限");
			viewHodler.investment_income.setText("原标年化");
			
				if("立即购买".equals(list.get(position).getInvestment_Type())){//显示投资进度
					viewHodler.investment_Schedule.setVisibility(View.VISIBLE);
					viewHodler.investment_type.setVisibility(View.GONE);
					viewHodler.investmentType.setVisibility(View.GONE);
					viewHodler.investment_Schedule.setProgress(Float.parseFloat(list.get(position).getInvestment_Schedule()));
					viewHodler.investment_Schedule.setTextColor(getResources().getColor(R.color.title_color_orange));
				}
				else {//根据状态值显示不同图片
					viewHodler.investment_Schedule.setVisibility(View.GONE);
					viewHodler.investment_type.setVisibility(View.GONE);
					viewHodler.investmentType.setVisibility(View.VISIBLE);
					if("转让失败".equals(list.get(position).getInvestment_Type()))
					viewHodler.investmentType.setImageResource(R.drawable.transfer_fail);
					else if("转让成功".equals(list.get(position).getInvestment_Type()))
						viewHodler.investmentType.setImageResource(R.drawable.transfer_success);
						
				}
			return convertView;
		}

		class ViewHolder {
			// * 产品类型（图标）
			ImageView  type;
			// * title
			TextView title;
			// * 投资奖励
			TextView investment_Rewards;
			// * 年华收益
			TextView  earning_Rate;
			// * 产品期限
			TextView	 product_Deadline;
			// * 产品期限单位
		   TextView product_Deadline_Type;
			// * 融资金额
			TextView product_Money;
			// * 融资金额单位
		    TextView product_Money_Type;
			// * 投资倒计时
			TextView  investment_Time;
			// *投资进度
			CircleProgressBar investment_Schedule;
			//*倒计时的布局是否显示
			LinearLayout investment_type;
			
		//*还款中，流标等图片显示的view
			ImageView investmentType;
			//*还款方式
			TextView hktype;
			//*还款方式
			TextView investmentjiangli;
			
			//*融资金额
			TextView investment_money;
			//*融资期限
			TextView investment_deadline;
			//*年化收益
			TextView investment_income;
			
			
		}
	}
	

}
