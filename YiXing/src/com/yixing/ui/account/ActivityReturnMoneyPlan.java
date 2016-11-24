package com.yixing.ui.account;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yixing.ExtraConfig;
import com.yixing.R;
import com.yixing.biz.AccountBiz;
import com.yixing.biz.exception.BizFailure;
import com.yixing.biz.exception.ZYException;
import com.yixing.biz.task.BizDataAsyncTask;
import com.yixing.model.BaseModel;
import com.yixing.model.MyInvestHoldModel;
import com.yixing.model.MyInvestPaymentModel;
import com.yixing.model.ReturnMoneyPlanModel;
import com.yixing.ui.base.BaseActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ActivityReturnMoneyPlan extends BaseActivity implements OnClickListener {

	private TextView headerTitle;
	private ImageView headerBack;
	private PullToRefreshListView pullToRefreshListView;
	private ReturnMoneyPlanListAdapter adapter;

	private TextView textView;
	private int pageIndex;
	private boolean isEnd = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_return_money_plan);
		initView();

		init();
	}

	private void initView() {
		headerTitle = (TextView) findViewById(R.id.tv_title);
		headerBack = (ImageView) findViewById(R.id.iv_back);
		pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.list_return_money_plan);

		headerTitle.setText("回款计划");
		headerBack.setOnClickListener(this);

	}

	// private String productsTitle;//标题
	// private String financeInterestRate;//待回利息
	// private String financePeriod;//待回本金
	// private String interestRateType;//债权-非债权
	// private String recoverDate;//日期
	// private String currentPeriod;//期数

	private void init() {

		adapter = new ReturnMoneyPlanListAdapter(this);

		pullToRefreshListView.getRefreshableView().setAdapter(adapter);

		pullToRefreshListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {
			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub

				getReturnMoneyPlanList(false, true);

			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				getReturnMoneyPlanList(false, false);
			}

		});

		textView = new TextView(this);
		textView.setGravity(Gravity.CENTER);
		textView.setText("暂无数据");
		getReturnMoneyPlanList(true, true);

	}

	BizDataAsyncTask<List<BaseModel>> returnMoneyPlanTask;

	private void getReturnMoneyPlanList(boolean isFirst, final boolean isPullDown) {

		returnMoneyPlanTask = new BizDataAsyncTask<List<BaseModel>>() {

			@Override
			protected void onExecuteSucceeded(List<BaseModel> result) {
				// TODO Auto-generated method stub

				if (result.size() < ExtraConfig.DEFAULT_PAGE_COUNT) {
					isEnd = true;
					if (isPullDown && result.size() == 0) {
						pullToRefreshListView.setEmptyView(textView);
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
					adapter.addItem((ReturnMoneyPlanModel) result.get(i));
				}
				adapter.notifyDataSetChanged();

				pullToRefreshListView.onRefreshComplete();

			}

			@Override
			protected List<BaseModel> doExecute() throws ZYException, BizFailure {
				// TODO Auto-generated method stub

				if (isPullDown) {

					return AccountBiz.getReturnMoneyPlanList("", ExtraConfig.DEFAULT_PAGE_COUNT + "");
				} else {
					if (isEnd) {
						return new ArrayList<BaseModel>();
					} else {
						return AccountBiz.getReturnMoneyPlanList(pageIndex + "", ExtraConfig.DEFAULT_PAGE_COUNT + "");
					}
				}
			}

			@Override
			protected void OnExecuteFailed(String error) {
				// TODO Auto-generated method stub
				pullToRefreshListView.onRefreshComplete();
			}
		};

		if (isFirst) {
			returnMoneyPlanTask.setWaitingView(getWaitingView());
		}

		returnMoneyPlanTask.execute();

	}

	class ReturnMoneyPlanListAdapter extends BaseAdapter {

		private List<ReturnMoneyPlanModel> list = new ArrayList<ReturnMoneyPlanModel>();
		private Context context;

		public ReturnMoneyPlanListAdapter(Context context) {
			// TODO Auto-generated constructor stub

			this.context = context;
		}

		public void addItem(ReturnMoneyPlanModel cellOptions) {
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
		public ReturnMoneyPlanModel getItem(int position) {
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
			ReturnMoneyPlanListViewHolder viewHolder;

			if (convertView == null) {

				convertView = LayoutInflater.from(context).inflate(R.layout.item_listview_return_money_plan, parent,
						false);
				viewHolder = new ReturnMoneyPlanListViewHolder(convertView);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ReturnMoneyPlanListViewHolder) convertView.getTag();
			}

			viewHolder.tv_item_plan_benjin.setText(list.get(position).getFinancePeriod());
			viewHolder.tv_item_plan_data.setText(list.get(position).getRecoverDate());
			viewHolder.tv_item_plan_lixi.setText(list.get(position).getFinanceInterestRate());
			viewHolder.tv_item_plan_periods.setText(list.get(position).getCurrentPeriod());
			viewHolder.tv_item_plan_title.setText(list.get(position).getProductsTitle());

			// 回款详情
			final int pos = position;
			viewHolder.tv_item_plan_detail.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// 1普通标 2债权

					if (list.get(pos).getInterestRateType().equals("1")) {

						Intent intent = new Intent(ActivityReturnMoneyPlan.this, ActivityReturnMoneyDetailOne.class);
						intent.putExtra(ExtraConfig.IntentExtraKey.RETURN_MONEY_DETAIL_ONE_ID,
								list.get(pos).getOID_TENDER_ID());
						intent.putExtra(ExtraConfig.IntentExtraKey.RETURN_MONEY_DETAIL_ONE_TYPR,
								list.get(pos).getInterestRateType());

						startActivity(intent);
					} else {
						Intent intent = new Intent(ActivityReturnMoneyPlan.this, ActivityReturnMoneyDetailTwo.class);
						intent.putExtra(ExtraConfig.IntentExtraKey.RETURN_MONEY_DETAIL_TWO_ID,
								list.get(pos).getOID_TENDER_ID());
						intent.putExtra(ExtraConfig.IntentExtraKey.RETURN_MONEY_DETAIL_TWO_TYPR,
								list.get(pos).getInterestRateType());

						startActivity(intent);
					}

				}
			});

			return convertView;
		}

		class ReturnMoneyPlanListViewHolder {

			TextView tv_item_plan_benjin;// 待回本金
			TextView tv_item_plan_data;// 日期
			TextView tv_item_plan_detail;// 回款详情
			TextView tv_item_plan_lixi;// 待回利息
			TextView tv_item_plan_periods;// 期数
			TextView tv_item_plan_title;// 标题

			public ReturnMoneyPlanListViewHolder(View itemView) {
				tv_item_plan_benjin = (TextView) itemView.findViewById(R.id.tv_item_plan_benjin);
				tv_item_plan_data = (TextView) itemView.findViewById(R.id.tv_item_plan_data);
				tv_item_plan_detail = (TextView) itemView.findViewById(R.id.tv_item_plan_detail);
				tv_item_plan_lixi = (TextView) itemView.findViewById(R.id.tv_item_plan_lixi);
				tv_item_plan_periods = (TextView) itemView.findViewById(R.id.tv_item_plan_periods);
				tv_item_plan_title = (TextView) itemView.findViewById(R.id.tv_item_plan_title);

			}

		}

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.iv_back:
			finish();
			break;
		}
	}

}
