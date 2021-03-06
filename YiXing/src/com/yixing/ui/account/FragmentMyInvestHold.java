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
import com.yixing.model.MyInvestHoldModel;
import com.yixing.ui.base.BaseFragment;

/**
 * 
 * @author Ls 我的投资 持有中
 *
 */
public class FragmentMyInvestHold extends BaseFragment {

	private PullToRefreshListView pullToRefreshListView;
	private InvestListHoldAdapter adapter;

	private int pageIndex = 0;
	private boolean isEnd = false;
	private TextView tv;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_my_investment_hold, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		init();

	}

	// 刷新需要继续修改

	private void init() {
		pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.list_my_invest_hold);

		adapter = new InvestListHoldAdapter(getActivity());
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

		tv = new TextView(getActivity());
		tv.setGravity(Gravity.CENTER);
		tv.setText("暂无数据");

		getMyInvestHold(true, true);
	}

	private BizDataAsyncTask<List<BaseModel>> getMyInvestListTask;

	private void getMyInvestHold(final boolean first, final boolean isPullDown) {

		getMyInvestListTask = new BizDataAsyncTask<List<BaseModel>>() {

			@Override
			protected void onExecuteSucceeded(List<BaseModel> result) {

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
					adapter.addItem((MyInvestHoldModel) result.get(i));
				}
				adapter.notifyDataSetChanged();

				pullToRefreshListView.onRefreshComplete();

			}

			@Override
			protected List<BaseModel> doExecute() throws ZYException, BizFailure {

				if (isPullDown) {

					return AccountBiz.getMyInvestList("1", "0", ExtraConfig.DEFAULT_PAGE_COUNT + "");
				} else {
					if (isEnd) {
						return new ArrayList<BaseModel>();
					} else {
						return AccountBiz.getMyInvestList("1", pageIndex + "", ExtraConfig.DEFAULT_PAGE_COUNT + "");
					}
				}
			}

			@Override
			protected void OnExecuteFailed(String error) {

				pullToRefreshListView.onRefreshComplete();
			}
		};

		if (first) {
			getMyInvestListTask.setWaitingView(getWaitingView());
		}

		getMyInvestListTask.execute();

	}

	class InvestListHoldAdapter extends BaseAdapter {

		private List<MyInvestHoldModel> list = new ArrayList<MyInvestHoldModel>();
		private Context context;

		public InvestListHoldAdapter(Context context) {

			this.context = context;
		}

		public void addItem(MyInvestHoldModel cellOptions) {
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

			return list == null ? 0 : list.size();
		}

		@Override
		public MyInvestHoldModel getItem(int position) {

			return list.get(position);
		}

		@Override
		public long getItemId(int position) {

			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			HoldListViewHolder viewHolder;

			if (convertView == null) {

				convertView = LayoutInflater.from(context).inflate(R.layout.item_listview_my_investment_hold, parent,
						false);
				viewHolder = new HoldListViewHolder(convertView);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (HoldListViewHolder) convertView.getTag();
			}

			// list设置数据
			if (list.get(position).getTENDER_FROM().trim().equals("2")) {
				viewHolder.iv_item_hold.setImageResource(R.drawable.transfer);
			} else {
				if ("担保贷".equals(list.get(position).getFINANCE_PRODUCTS_NAME()))
					viewHolder.iv_item_hold.setImageResource(R.drawable.transfer_item4);
				else if ("银行保荐".equals(list.get(position).getFINANCE_PRODUCTS_NAME()))
					viewHolder.iv_item_hold.setImageResource(R.drawable.transfer_item9);
				else if ("e兴车贷".equals(list.get(position).getFINANCE_PRODUCTS_NAME()))
					viewHolder.iv_item_hold.setImageResource(R.drawable.transfer_item5);
				else if ("e兴房贷".equals(list.get(position).getFINANCE_PRODUCTS_NAME()))
					viewHolder.iv_item_hold.setImageResource(R.drawable.transfer_item2);
				else if ("助教贷".equals(list.get(position).getFINANCE_PRODUCTS_NAME()))
					viewHolder.iv_item_hold.setImageResource(R.drawable.transfer_item6);
				else if ("助农贷".equals(list.get(position).getFINANCE_PRODUCTS_NAME()))
					viewHolder.iv_item_hold.setImageResource(R.drawable.transfer_item8);
				else if ("创业贷".equals(list.get(position).getFINANCE_PRODUCTS_NAME()))
					viewHolder.iv_item_hold.setImageResource(R.drawable.transfer_item1);
				else if ("信用贷".equals(list.get(position).getFINANCE_PRODUCTS_NAME()))
					viewHolder.iv_item_hold.setImageResource(R.drawable.transfer_item7);
				else if ("公益贷".equals(list.get(position).getFINANCE_PRODUCTS_NAME()))
					viewHolder.iv_item_hold.setImageResource(R.drawable.transfer_item3);

			}

			if (list.get(position).getTENDER_FROM().trim().equals("1")) {
				// 非债权
				viewHolder.tv_item_hold_title.setText(list.get(position).getPRODUCTS_TITLE());
			} else {
				// 债权
				viewHolder.tv_item_hold_title.setText(list.get(position).getTRANSFER_CONTRACT_ID());
			}

			viewHolder.tv_item_hold_await.setText(list.get(position).getRECOVER_AMOUNT_TOTAL_WAIT_FORMAT());
			viewHolder.tv_item_hold_financing.setText(list.get(position).getTENDER_AMOUNT_FORMAT());
			viewHolder.tv_item_hold_received.setText(list.get(position).getRECOVER_AMOUNT_TOTAL_YES_FORMAT());

			// 回款详情
			final int pos = position;
			viewHolder.tv_item_hold_detail.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					// 点击跳转到回款详情 需要有一个标记 确认跳转到回款详情--债权
					if (list.get(position).getTENDER_FROM().trim().equals("1")) {
						// 非债权
						Intent intent = new Intent(getActivity(), ActivityReturnMoneyDetailOne.class);
						intent.putExtra(ExtraConfig.IntentExtraKey.RETURN_MONEY_DETAIL_ONE_ID,
								list.get(pos).getOID_TENDER_ID());
						intent.putExtra(ExtraConfig.IntentExtraKey.RETURN_MONEY_DETAIL_ONE_TYPR,
								list.get(pos).getTENDER_FROM());
					
						startActivity(intent);
					} else {
						// 债权
						Intent intent = new Intent(getActivity(), ActivityReturnMoneyDetailTwo.class);
						intent.putExtra(ExtraConfig.IntentExtraKey.RETURN_MONEY_DETAIL_TWO_ID,
								list.get(pos).getOID_TENDER_ID());
						intent.putExtra(ExtraConfig.IntentExtraKey.RETURN_MONEY_DETAIL_TWO_TYPR,
								list.get(pos).getTENDER_FROM());
						startActivity(intent);

					}

				}
			});

			return convertView;
		}

		class HoldListViewHolder {

			ImageView iv_item_hold;// 贷款小图片类型
			TextView tv_item_hold_title;// 标题
			TextView tv_item_hold_financing;// 融资金额
			TextView tv_item_hold_received;// 已收
			TextView tv_item_hold_await;// 待收
			TextView tv_item_hold_detail;// 回款详情

			public HoldListViewHolder(View itemView) {
				iv_item_hold = (ImageView) itemView.findViewById(R.id.iv_item_hold);
				tv_item_hold_await = (TextView) itemView.findViewById(R.id.tv_item_hold_await);
				tv_item_hold_financing = (TextView) itemView.findViewById(R.id.tv_item_hold_financing);
				tv_item_hold_received = (TextView) itemView.findViewById(R.id.tv_item_hold_received);
				tv_item_hold_title = (TextView) itemView.findViewById(R.id.tv_item_hold_title);
				tv_item_hold_detail = (TextView) itemView.findViewById(R.id.tv_item_hold_detail);

			}

		}

	}

}
