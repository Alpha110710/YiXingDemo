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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.yixing.ExtraConfig;
import com.yixing.R;
import com.yixing.biz.InvestmentBiz;
import com.yixing.biz.exception.BizFailure;
import com.yixing.biz.exception.ZYException;
import com.yixing.biz.task.BizDataAsyncTask;
import com.yixing.model.BaseModel;
import com.yixing.model.InvestmentMoreModel;
import com.yixing.ui.base.BaseFragment;
import com.yixing.ui.widget.CircleProgressBar;
import com.yixing.utils.java.AlertUtil;

/***
 * 投资记录
 * 
 * @author Administrator
 * 
 */
public class FragmentRecords extends BaseFragment {
	private View view;
	PullToRefreshListView pullToRefreshListView;
	private ListView listView;
	private Myadapter adapter;
	TextView tv;
	private int pageIndex = 0;
	private String productId;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return view = inflater.inflate(R.layout.fragment_investment_record, null);
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		productId=getArguments().getString("productId");
		initView();
	}

	private void initView() {
		adapter = new Myadapter();
		pullToRefreshListView = (PullToRefreshListView) view
				.findViewById(R.id.omvestment_pull);
		pullToRefreshListView.setMode(Mode.BOTH);
		pullToRefreshListView.getRefreshableView().setAdapter(adapter);
		pullToRefreshListView
				.setOnRefreshListener(new OnRefreshListener2<ListView>() {

					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						initHoldingList(productId,false, true);

					}

					@Override
					public void onPullUpToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						initHoldingList(productId,false, false);
					}
				});

		tv = new TextView(getActivity());
		tv.setGravity(Gravity.CENTER);
		tv.setText("暂无数据");
		initHoldingList(productId,true, true);

	}

	private boolean end = false;
	private BizDataAsyncTask<List<BaseModel>> getHoldingList;

	// 访问接口获取数据
	private void initHoldingList(final String productId,final boolean first, final boolean isPullDown) {
		getHoldingList = new BizDataAsyncTask<List<BaseModel>>() {

			@Override
			protected List<BaseModel> doExecute() throws ZYException,
					BizFailure {
				if (isPullDown) {

					return InvestmentBiz.investmentMore(productId,0 + "",
							ExtraConfig.DEFAULT_PAGE_COUNT + "");
				} else {
					if (end) {
						return new ArrayList<BaseModel>();
					} else {
						return InvestmentBiz.investmentMore(productId,pageIndex + "",
								ExtraConfig.DEFAULT_PAGE_COUNT + "");
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
					adapter.addItem((InvestmentMoreModel) result.get(i));
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
		
		  if (first) { 
			  getHoldingList.setWaitingView(getWaitingView());
		  }
		 
		 getHoldingList.execute();

	}

	class Myadapter extends BaseAdapter {
		private List<InvestmentMoreModel> list = new ArrayList<InvestmentMoreModel>();

		@Override
		public int getCount() {
			return list.size();
		}

		// 添加数据
		public void addItem(InvestmentMoreModel investmentModel) {
			list.add(investmentModel);
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
						R.layout.item_listview_investment_topic_record, null);
				viewHodler.money=(TextView) convertView.findViewById(R.id.investment_money);
				viewHodler.phone=(TextView) convertView.findViewById(R.id.investment_phone);
				viewHodler.type=(TextView) convertView.findViewById(R.id.investment_type);
				viewHodler.time=(TextView) convertView.findViewById(R.id.investment_time);
				convertView.setTag(viewHodler);
			} else {
				viewHodler = (ViewHolder) convertView.getTag();
			}
			viewHodler.money.setText(list.get(position).getMoney());
			viewHodler.phone.setText(list.get(position).getPHONE());
			viewHodler.type.setText(list.get(position).getType());
			viewHodler.time.setText(list.get(position).getTime());
			return convertView;
		}

		class ViewHolder {
			// * 电话
			TextView phone;
			// * 金额
			TextView money;
			// * 类型
			TextView type;
			// * 时间
			TextView time;

		}
	}

}
