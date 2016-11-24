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
import com.yixing.model.TradingRecordModel;
import com.yixing.ui.base.BaseActivity;
import com.yixing.ui.base.BaseFragment;

/**
 * 
 * @author Ls 交易记录
 *
 */
public class ActivityTradingRecord extends BaseActivity implements OnClickListener {

	private PullToRefreshListView pullToRefreshListView;
	private ListAdapter adapter;

	private int pageIndex = 0;
	private boolean isEnd = false;
	private TextView tv;

	private TextView tv_title;
	private ImageView iv_back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trading_record);

		initView();
		init();
	}

	private void initView() {
		pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.list_trading_record);

		tv_title = (TextView) findViewById(R.id.tv_title);
		iv_back = (ImageView) findViewById(R.id.iv_back);

		iv_back.setOnClickListener(this);
		tv_title.setText("交易记录");
	}

	// 刷新需要继续修改

	private void init() {

		adapter = new ListAdapter(this);
		pullToRefreshListView.getRefreshableView().setAdapter(adapter);

		// 刷新方法
		pullToRefreshListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {
			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

				getMyInvestHold(false, true);

			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

				getMyInvestHold(false, false);
			}

		});

		tv = new TextView(this);
		tv.setGravity(Gravity.CENTER);
		tv.setText("暂无数据");

		getMyInvestHold(true, true);
	}

	private BizDataAsyncTask<List<TradingRecordModel>> getMyInvestListTask;

	private void getMyInvestHold(final boolean first, final boolean isPullDown) {

		getMyInvestListTask = new BizDataAsyncTask<List<TradingRecordModel>>() {

			@Override
			protected void onExecuteSucceeded(List<TradingRecordModel> result) {
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
					adapter.addItem((TradingRecordModel) result.get(i));
				}
				adapter.notifyDataSetChanged();

				pullToRefreshListView.onRefreshComplete();

			}

			@Override
			protected List<TradingRecordModel> doExecute() throws ZYException, BizFailure {
				// TODO Auto-generated method stub
				if (isPullDown) {

					return AccountBiz.getTradingRecord("0", ExtraConfig.DEFAULT_PAGE_COUNT + "");
				} else {
					if (isEnd) {
						return new ArrayList<TradingRecordModel>();
					} else {
						return AccountBiz.getTradingRecord(pageIndex + "", ExtraConfig.DEFAULT_PAGE_COUNT + "");
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

	class ListAdapter extends BaseAdapter {

		private List<TradingRecordModel> list = new ArrayList<TradingRecordModel>();
		private Context context;

		public ListAdapter(Context context) {

			this.context = context;
		}

		public void addItem(TradingRecordModel cellOptions) {
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
		public TradingRecordModel getItem(int position) {
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
			RecordListViewHolder viewHolder;

			if (convertView == null) {

				convertView = LayoutInflater.from(context).inflate(R.layout.item_listview_trading_record, parent,
						false);
				viewHolder = new RecordListViewHolder(convertView);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (RecordListViewHolder) convertView.getTag();
			}

			// TextView tv_item_trading_balance;// 账户余额
			// TextView tv_item_trading_data;// 日期
			// TextView tv_item_trading_deal_amount;// 交易金额
			// TextView tv_item_trading_title;// 标题

			// list设置数据

			if (list.get(position).getRECHARGE_AMOUNT().contains("+")) {
				viewHolder.tv_item_trading_deal_amount.setTextColor(getResources().getColor(R.color.font_red));
			} else {
				viewHolder.tv_item_trading_deal_amount.setTextColor(getResources().getColor(R.color.text_blue));

			}

			viewHolder.tv_item_trading_balance.setText(list.get(position).getFEE_AMOUNT());
			viewHolder.tv_item_trading_data.setText(list.get(position).getINS_DATE());
			viewHolder.tv_item_trading_deal_amount.setText(list.get(position).getRECHARGE_AMOUNT());
			viewHolder.tv_item_trading_title.setText(list.get(position).getRECHARGE_TYPE());

			return convertView;
		}

		class RecordListViewHolder {

			TextView tv_item_trading_balance;// 账户余额
			TextView tv_item_trading_data;// 日期
			TextView tv_item_trading_deal_amount;// 交易金额
			TextView tv_item_trading_title;// 标题

			public RecordListViewHolder(View itemView) {

				tv_item_trading_balance = (TextView) itemView.findViewById(R.id.tv_item_trading_balance);
				tv_item_trading_data = (TextView) itemView.findViewById(R.id.tv_item_trading_data);
				tv_item_trading_deal_amount = (TextView) itemView.findViewById(R.id.tv_item_trading_deal_amount);
				tv_item_trading_title = (TextView) itemView.findViewById(R.id.tv_item_trading_title);

			}

		}

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (YixingApp.globalIndex == 1) {
			// if (((ActivityMyInvest)
			// this.this).canInvestmentOnResume) {
			// initInvestmentList(true, true, true);

			// ((ActivityMyInvest) this.this).canInvestmentOnResume =
			// false;
			// }
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
