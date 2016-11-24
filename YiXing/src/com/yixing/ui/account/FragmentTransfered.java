package com.yixing.ui.account;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yixing.ExtraConfig;
import com.yixing.R;
import com.yixing.YixingApp;
import com.yixing.biz.AccountBiz;
import com.yixing.biz.exception.BizFailure;
import com.yixing.biz.exception.ZYException;
import com.yixing.biz.task.BizDataAsyncTask;
import com.yixing.model.BaseModel;
import com.yixing.model.MyTransferedModel;
import com.yixing.ui.base.BaseFragment;

/**
 * 
 * @author Ls 我的转让 已转让
 *
 */
public class FragmentTransfered extends BaseFragment {

	private PullToRefreshListView pullToRefreshListView;
	private TransferedListAdapter adapter;

	private int pageIndex = 0;
	private boolean isEnd = false;
	private TextView tv;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment_my_transfered, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		init();

	}


	// 刷新需要继续修改

	private void init() {
		pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.list_my_transfered);

		adapter = new TransferedListAdapter(getActivity());
		pullToRefreshListView.getRefreshableView().setAdapter(adapter);

		// 刷新方法
		pullToRefreshListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {
			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub

				getMyTransferedModel(false, true);

			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				getMyTransferedModel(false, false);
			}

		});

		tv = new TextView(getActivity());
		tv.setGravity(Gravity.CENTER);
		tv.setText("暂无数据");

		getMyTransferedModel(true, true);
	}

	private BizDataAsyncTask<List<BaseModel>> task;

	private void getMyTransferedModel(final boolean first, final boolean isPullDown) {

		task = new BizDataAsyncTask<List<BaseModel>>() {

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
					adapter.addItem((MyTransferedModel) result.get(i));
				}
				adapter.notifyDataSetChanged();

				pullToRefreshListView.onRefreshComplete();

			}

			@Override
			protected List<BaseModel> doExecute() throws ZYException, BizFailure {
				// TODO Auto-generated method stub
				if (isPullDown) {

					return AccountBiz.getMyInvestList("5", "0", ExtraConfig.DEFAULT_PAGE_COUNT + "");
				} else {
					if (isEnd) {
						return new ArrayList<BaseModel>();
					} else {
						return AccountBiz.getMyInvestList("5", pageIndex + "", ExtraConfig.DEFAULT_PAGE_COUNT + "");
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
			task.setWaitingView(getWaitingView());
		}

		task.execute();

	}

	class TransferedListAdapter extends BaseAdapter {

		private List<MyTransferedModel> list = new ArrayList<MyTransferedModel>();
		private Context context;

		public TransferedListAdapter(Context context) {
			// TODO Auto-generated constructor stub

			this.context = context;
		}

		public void addItem(MyTransferedModel cellOptions) {
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
		public MyTransferedModel getItem(int position) {
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
			TransferedListViewHolder viewHolder;

			if (convertView == null) {

				convertView = LayoutInflater.from(context).inflate(R.layout.item_listview_my_transfered, parent, false);
				viewHolder = new TransferedListViewHolder(convertView);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (TransferedListViewHolder) convertView.getTag();
			}

			 /**
		     * TRANSFER_CONTRACT_ID : EXJR1608250002Z
		     * PRODUCTS_TITLE : 银行保荐-投资理财计划0818-ZQZR001
		     * FINANCE_INTEREST_RATE : 9.00
		     * FINANCE_PERIOD_FORMAT : 3个月
		     * TRANSFER_CAPITAL_FORMAT : 5000.00/5000.00
		     * TRANSFER_CAPITAL_YES : 5000.00
		     * REMAIN_TIME : 已到期
		     * TENDER_AMOUNT : 6000.00
		     * TRANSFER_CAPITAL : 5000.00
		     * TRANSFER_AMOUNT : 4997.33*/
			
			// list设置数据
			viewHolder.tv_item_transfered_id.setText(list.get(position).getTRANSFER_CONTRACT_ID());
			viewHolder.tv_item_transfered_deal_amount.setText(list.get(position).getTRANSFER_AMOUNT());
			viewHolder.tv_item_transfered_debt_amount.setText(list.get(position).getTENDER_AMOUNT());
			viewHolder.tv_item_transfered_out_amount.setText(list.get(position).getTRANSFER_CAPITAL());
			viewHolder.tv_item_transfered_title.setText(list.get(position).getPRODUCTS_TITLE());

			// 转让详情
			final int pos = position;
			viewHolder.tv_item_transfered_transfer_detail.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					// 点击跳转到回款详情 需要有一个标记 确认跳转到回款详情--债权
					Intent intent = new Intent(getActivity(), ActivityTransferedDetail.class);
					intent.putExtra(ExtraConfig.IntentExtraKey.MY_TRANSFER_DETAIL_BEAN, list.get(pos).getTRANSFER_DETAIL());
					intent.putExtra(ExtraConfig.IntentExtraKey.MY_TRANSFER_DETAIL_ID, list.get(pos).getTRANSFER_CONTRACT_ID());
					startActivity(intent);

				}
			});

			return convertView;
		}

		class TransferedListViewHolder {

			TextView tv_item_transfered_id;// 转让编号
			TextView tv_item_transfered_deal_amount;// 成交金额
			TextView tv_item_transfered_debt_amount;// 债权金额
			TextView tv_item_transfered_out_amount;// 转出金额
			TextView tv_item_transfered_title;// 标题
			TextView tv_item_transfered_transfer_detail;// 转让详情

			public TransferedListViewHolder(View itemView) {
				tv_item_transfered_id = (TextView) itemView.findViewById(R.id.tv_item_transfered_id);
				tv_item_transfered_deal_amount = (TextView) itemView.findViewById(R.id.tv_item_transfered_deal_amount);
				tv_item_transfered_debt_amount = (TextView) itemView.findViewById(R.id.tv_item_transfered_debt_amount);
				tv_item_transfered_out_amount = (TextView) itemView.findViewById(R.id.tv_item_transfered_out_amount);
				tv_item_transfered_title = (TextView) itemView.findViewById(R.id.tv_item_transfered_title);
				tv_item_transfered_transfer_detail = (TextView) itemView
						.findViewById(R.id.tv_item_transfered_transfer_detail);

			}

		}

	}


}
