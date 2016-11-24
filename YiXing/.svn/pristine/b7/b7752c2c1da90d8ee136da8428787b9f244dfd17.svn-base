package com.yixing.ui.account;

import java.util.ArrayList;
import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.yixing.ExtraConfig;
import com.yixing.R;
import com.yixing.biz.AccountBiz;
import com.yixing.biz.exception.BizFailure;
import com.yixing.biz.exception.ZYException;
import com.yixing.biz.task.BizDataAsyncTask;
import com.yixing.model.PersonalProfitLossItemModel;
import com.yixing.model.MyInvestHoldModel;
import com.yixing.model.PersonalProfitLossModel;
import com.yixing.model.PersonalProfitLossItemModel;
import com.yixing.ui.account.FragmentMyInvestHold.InvestListHoldAdapter;
import com.yixing.ui.account.FragmentMyInvestHold.InvestListHoldAdapter.HoldListViewHolder;
import com.yixing.ui.base.BaseActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ActivityPersonalProfitLoss extends BaseActivity implements OnClickListener {

	private TextView tv_title;
	private ImageView iv_back;

	private PullToRefreshListView pullToRefreshListView;
	private TextView tv_personal_profit_total;// 总计收益
	private TextView tv_personal_expenditure_total;// 总计支出

	private PersonalProfitLossAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_personal_profit_loss);

		initView();
		init();
		getData();
	}

	private void initView() {
		tv_title = (TextView) findViewById(R.id.tv_green_header_title);
		iv_back = (ImageView) findViewById(R.id.iv_back);

		pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.list_personal_profit_loss);
		tv_personal_expenditure_total = (TextView) findViewById(R.id.tv_personal_expenditure_total);
		tv_personal_profit_total = (TextView) findViewById(R.id.tv_personal_profit_total);

		iv_back.setOnClickListener(this);
		tv_title.setText("个人损益");

	}

	// 刷新需要继续修改

	private void init() {

		adapter = new PersonalProfitLossAdapter(this);
		pullToRefreshListView.getRefreshableView().setAdapter(adapter);

		// 刷新方法
		pullToRefreshListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {
			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub

				getListData(false, true);

			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				getListData(false, false);
			}

		});

		tv = new TextView(this);
		tv.setGravity(Gravity.CENTER);
		tv.setText("暂无数据");

		getListData(true, true);
	}

	private int pageIndex = 0;
	private boolean isEnd = false;
	private TextView tv;

	private BizDataAsyncTask<List<PersonalProfitLossItemModel>> task;

	private void getListData(final boolean first, final boolean isPullDown) {

		task = new BizDataAsyncTask<List<PersonalProfitLossItemModel>>() {

			@Override
			protected void onExecuteSucceeded(List<PersonalProfitLossItemModel> result) {
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
					adapter.addItem((PersonalProfitLossItemModel) result.get(i));
				}
				adapter.notifyDataSetChanged();

				pullToRefreshListView.onRefreshComplete();

			}

			@Override
			protected List<PersonalProfitLossItemModel> doExecute() throws ZYException, BizFailure {
				// TODO Auto-generated method stub
				if (isPullDown) {

					return AccountBiz.getProfitAndLossList("0", "0", ExtraConfig.DEFAULT_PAGE_COUNT + "");
				} else {
					if (isEnd) {
						return new ArrayList<PersonalProfitLossItemModel>();
					} else {
						return AccountBiz.getProfitAndLossList("0", pageIndex + "",
								ExtraConfig.DEFAULT_PAGE_COUNT + "");
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

	private BizDataAsyncTask<PersonalProfitLossModel> task2;

	private void getData() {
		task2 = new BizDataAsyncTask<PersonalProfitLossModel>() {

			@Override
			protected void onExecuteSucceeded(PersonalProfitLossModel result) {
				tv_personal_expenditure_total.setText(((PersonalProfitLossModel) result).getLossAmount());
				tv_personal_profit_total.setText(((PersonalProfitLossModel) result).getProfitAmount());
			}

			@Override
			protected PersonalProfitLossModel doExecute() throws ZYException, BizFailure {
				return AccountBiz.getUserProfitAndLosses();
			}

			@Override
			protected void OnExecuteFailed(String error) {

			}
		};

		task2.execute();
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

	class PersonalProfitLossAdapter extends BaseAdapter {

		private List<PersonalProfitLossItemModel> list = new ArrayList<PersonalProfitLossItemModel>();
		private Context context;

		public PersonalProfitLossAdapter(Context context) {
			// TODO Auto-generated constructor stub

			this.context = context;
		}

		public void addItem(PersonalProfitLossItemModel cellOptions) {
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
		public PersonalProfitLossItemModel getItem(int position) {
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
			HoldListViewHolder viewHolder;

			if (convertView == null) {

				convertView = LayoutInflater.from(context).inflate(R.layout.item_listview_personal_profit_loss, parent,
						false);
				viewHolder = new HoldListViewHolder(convertView);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (HoldListViewHolder) convertView.getTag();
			}

			// list设置数据

			if (list.get(position).getRevenueExpendType().equals("R")) {
				viewHolder.item_personal_profit_amount.setTextColor(getResources().getColor(R.color.font_red));
			} else {
				viewHolder.item_personal_profit_amount.setTextColor(getResources().getColor(R.color.font_blue));

			}

			viewHolder.item_personal_profit_award.setText(list.get(position).getFundType());
			viewHolder.item_personal_profit_amount.setText(list.get(position).getAmount());
			viewHolder.item_personal_profit_data.setText(list.get(position).getDate());

			return convertView;
		}

		class HoldListViewHolder {

			TextView item_personal_profit_award;// 投资奖励
			TextView item_personal_profit_amount;// 投资奖励金额
			TextView item_personal_profit_data;// 日期

			public HoldListViewHolder(View itemView) {
				item_personal_profit_award = (TextView) itemView.findViewById(R.id.item_personal_profit_award);
				item_personal_profit_amount = (TextView) itemView.findViewById(R.id.item_personal_profit_amount);
				item_personal_profit_data = (TextView) itemView.findViewById(R.id.item_personal_profit_data);

			}

		}

	}

}
