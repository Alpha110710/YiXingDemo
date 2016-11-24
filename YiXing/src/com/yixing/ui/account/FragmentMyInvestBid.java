package com.yixing.ui.account;

import android.content.Context;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.yixing.ExtraConfig;
import com.yixing.R;
import com.yixing.YixingApp;
import com.yixing.biz.AccountBiz;
import com.yixing.biz.exception.BizFailure;
import com.yixing.biz.exception.ZYException;
import com.yixing.biz.task.BizDataAsyncTask;
import com.yixing.model.BaseModel;
import com.yixing.model.MyInvestBidModel;
import com.yixing.model.MyInvestBidModel;
import com.yixing.ui.account.FragmentMyInvestBid.InvestListBidAdapter.BidListViewHolder;
import com.yixing.ui.base.BaseFragment;

public class FragmentMyInvestBid extends BaseFragment {

	private PullToRefreshListView pullToRefreshListView;
	private InvestListBidAdapter adapter;

	private int pageIndex = 0;
	private boolean isEnd = false;
	private TextView tv;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment_my_investment_bid, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		init();
	}

	// 刷新需要继续修改

	private void init() {
		pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.list_my_invest_bid);

		adapter = new InvestListBidAdapter(getActivity());
		pullToRefreshListView.getRefreshableView().setAdapter(adapter);

		// 刷新方法
		pullToRefreshListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {
			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub

				getMyInvestBid(false, true);

			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				getMyInvestBid(false, false);
			}

		});

		tv = new TextView(getActivity());
		tv.setGravity(Gravity.CENTER);
		tv.setText("暂无数据");

		getMyInvestBid(true, true);
	}

	private BizDataAsyncTask<List<BaseModel>> getMyInvestListTask;

	private void getMyInvestBid(final boolean first, final boolean isPullDown) {

		getMyInvestListTask = new BizDataAsyncTask<List<BaseModel>>() {

			@Override
			protected void onExecuteSucceeded(List<BaseModel> result) {
				// TODO Auto-generated method stub

				if (result.size() < ExtraConfig.DEFAULT_PAGE_COUNT) {
					isEnd = true;
					if (isPullDown && result.size() == 0) {
						pullToRefreshListView.setEmptyView(tv);
					}

					pullToRefreshListView.setPullLabel("没有更多数据", Mode.PULL_FROM_END);
					pullToRefreshListView.setReleaseLabel("没有更多数据", Mode.PULL_FROM_END);
					pullToRefreshListView.setRefreshingLabel("没有更多数据", Mode.PULL_FROM_END);
				} else {
					isEnd = false;
					pullToRefreshListView.setPullLabel("上拉刷新", Mode.PULL_FROM_END);
					pullToRefreshListView.setReleaseLabel("放开以刷新", Mode.PULL_FROM_END);
					pullToRefreshListView.setRefreshingLabel("正在载入", Mode.PULL_FROM_END);
				}

				if (isPullDown) {
					pageIndex = 0;
					adapter.removeAll();
				}
				pageIndex++;

				for (int i = 0; i < result.size(); i++) {
					adapter.addItem((MyInvestBidModel) result.get(i));
				}
				adapter.notifyDataSetChanged();

				pullToRefreshListView.onRefreshComplete();

			}

			@Override
			protected List<BaseModel> doExecute() throws ZYException, BizFailure {
				// TODO Auto-generated method stub
				if (isPullDown) {

					return AccountBiz.getMyInvestList("2", "0", ExtraConfig.DEFAULT_PAGE_COUNT + "");
				} else {
					if (isEnd) {
						return new ArrayList<BaseModel>();
					} else {
						return AccountBiz.getMyInvestList("2", pageIndex + "", ExtraConfig.DEFAULT_PAGE_COUNT + "");
					}
				}
			}

			@Override
			protected void OnExecuteFailed(String error) {
				// TODO Auto-generated method stub
				pullToRefreshListView.onRefreshComplete();
			}
		};

		if (first) {
			getMyInvestListTask.setWaitingView(getWaitingView());
		}

		getMyInvestListTask.execute();

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (YixingApp.globalIndex == 1) {
			// if (((ActivityMyInvest)
			// this.getActivity()).canInvestmentOnResume) {
			// initInvestmentList(true, true, true);

			// ((ActivityMyInvest) this.getActivity()).canInvestmentOnResume =
			// false;
			// }
		}
	}

	class InvestListBidAdapter extends BaseAdapter {

		private List<MyInvestBidModel> list = new ArrayList<MyInvestBidModel>();
		private Context context;

		public InvestListBidAdapter(Context context) {
			// TODO Auto-generated constructor stub

			this.context = context;
		}

		public void addItem(MyInvestBidModel cellOptions) {
			list.add(cellOptions);
		}

		public void removeAll() {
			if (list != null && list.size() > 0) {
				for (int i = list.size() - 1; i >= 0; i--) {
					list.remove(i);
				}
			}
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list == null ? 0 : list.size();
		}

		@Override
		public MyInvestBidModel getItem(int position) {
			// TODO Auto-generated method stub
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			BidListViewHolder viewHolder;

			if (convertView == null) {

				convertView = LayoutInflater.from(context).inflate(R.layout.item_listview_my_investment_bid, parent,
						false);
				viewHolder = new BidListViewHolder(convertView);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (BidListViewHolder) convertView.getTag();
			}

			// list设置数据

			if (!list.get(position).getTypeFlg().equals("")) {
				viewHolder.iv_item_bid.setImageResource(R.drawable.transfer);

			} else {
				if ("担保贷".equals(list.get(position).getLoanFlg()))
					viewHolder.iv_item_bid.setImageResource(R.drawable.transfer_item4);
				else if ("银行保荐".equals(list.get(position).getLoanFlg()))
					viewHolder.iv_item_bid.setImageResource(R.drawable.transfer_item9);
				else if ("e兴车贷".equals(list.get(position).getLoanFlg()))
					viewHolder.iv_item_bid.setImageResource(R.drawable.transfer_item5);
				else if ("e兴房贷".equals(list.get(position).getLoanFlg()))
					viewHolder.iv_item_bid.setImageResource(R.drawable.transfer_item2);
				else if ("助教贷".equals(list.get(position).getLoanFlg()))
					viewHolder.iv_item_bid.setImageResource(R.drawable.transfer_item6);
				else if ("助农贷".equals(list.get(position).getLoanFlg()))
					viewHolder.iv_item_bid.setImageResource(R.drawable.transfer_item8);
				else if ("创业贷".equals(list.get(position).getLoanFlg()))
					viewHolder.iv_item_bid.setImageResource(R.drawable.transfer_item1);
				else if ("信用贷".equals(list.get(position).getLoanFlg()))
					viewHolder.iv_item_bid.setImageResource(R.drawable.transfer_item7);

			}

			if (list.get(position).getInvestData().contains("个月")) {
				viewHolder.tv_item_bid_data.setText(list.get(position).getInvestData().replace("个月", ""));
				viewHolder.tv_item_bid_geyue.setText("个月");
			} else if (list.get(position).getInvestData().contains("天")) {
				viewHolder.tv_item_bid_data.setText(list.get(position).getInvestData().replace("天", ""));
				viewHolder.tv_item_bid_geyue.setText("天");
			} else {
				viewHolder.tv_item_bid_data.setText(list.get(position).getInvestData().replace("个季度", ""));
				viewHolder.tv_item_bid_geyue.setText("个季度");
			}
			if (list.get(position).getTENDER_FROM().equals("1")) {
				//非债权
				viewHolder.tv_item_bid_title.setText(list.get(position).getTitle());
			}else {
				//债权
				viewHolder.tv_item_bid_title.setText(list.get(position).getTRANSFER_CONTRACT_ID());
			}
			viewHolder.tv_item_bid_expect_profit.setText(list.get(position).getExpectedProfit());
			viewHolder.tv_item_bid_finaning.setText(list.get(position).getFinancingAmount());
			viewHolder.tv_item_bid_year_rate.setText(list.get(position).getYearRate());
			viewHolder.tv_item_bid_state.setText(list.get(position).getState());

			return convertView;
		}

		class BidListViewHolder {

			ImageView iv_item_bid;// flg小图片
			TextView tv_item_bid_data;// 投资期限
			TextView tv_item_bid_expect_profit;// 期望收益
			TextView tv_item_bid_finaning;// 融资金额
			TextView tv_item_bid_title;// 标题
			TextView tv_item_bid_year_rate;// 年化利率
			TextView tv_item_bid_state;// 满标复审
			TextView tv_item_bid_geyue;// 个月 单位

			public BidListViewHolder(View itemView) {

				iv_item_bid = (ImageView) itemView.findViewById(R.id.iv_item_bid);
				tv_item_bid_data = (TextView) itemView.findViewById(R.id.tv_item_bid_data);
				tv_item_bid_expect_profit = (TextView) itemView.findViewById(R.id.tv_item_bid_expect_profit);
				tv_item_bid_finaning = (TextView) itemView.findViewById(R.id.tv_item_bid_finaning);
				tv_item_bid_title = (TextView) itemView.findViewById(R.id.tv_item_bid_title);
				tv_item_bid_year_rate = (TextView) itemView.findViewById(R.id.tv_item_bid_year_rate);
				tv_item_bid_state = (TextView) itemView.findViewById(R.id.tv_item_bid_state);
				tv_item_bid_geyue = (TextView) itemView.findViewById(R.id.tv_item_bid_geyue);

			}

		}

	}

}