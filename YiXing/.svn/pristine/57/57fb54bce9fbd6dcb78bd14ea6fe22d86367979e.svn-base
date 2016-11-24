package com.yixing.ui.account;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
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
import com.yixing.biz.AccountBiz;
import com.yixing.biz.exception.BizFailure;
import com.yixing.biz.exception.ZYException;
import com.yixing.biz.task.BizDataAsyncTask;
import com.yixing.model.MyRedPacketUnusedModel;
import com.yixing.model.MyInvestHoldModel;
import com.yixing.model.MyRedPacketUnusedModel;
import com.yixing.model.MyRedPacketUnusedModel;
import com.yixing.ui.account.FragmentMyInvestHold.InvestListHoldAdapter.HoldListViewHolder;
import com.yixing.ui.base.BaseFragment;
import com.yixing.utils.java.AlertUtil;
import com.yixing.utils.java.StringUtil;

/**
 * 我的红包未使用
 * 
 * @author Ls
 *
 */
public class FragmentMyRedPackageUnused extends BaseFragment implements OnClickListener {

	private PullToRefreshListView pullToRefreshListView;
	private RedPacketUnusedListAdapter adapter;

	private TextView tv_red_unused_exchange;
	private EditText et_red_unused_exchange;

	private int pageIndex = 0;
	private boolean isEnd = false;
	private TextView tv;
	
	private CouponToCashSuccess listener;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_my_red_packet_unused, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		pullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.list_red_unused);
		tv_red_unused_exchange = (TextView) view.findViewById(R.id.tv_red_unused_exchange);
		et_red_unused_exchange = (EditText) view.findViewById(R.id.et_red_unused_exchange);

		tv_red_unused_exchange.setOnClickListener(this);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		init();

	}
	
	

	private void init() {
		adapter = new RedPacketUnusedListAdapter(getActivity());

		pullToRefreshListView.getRefreshableView().setAdapter(adapter);
		pullToRefreshListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				getMyRedPacket(false, true);
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				getMyRedPacket(false, false);
			}
		});

		tv = new TextView(getActivity());
		tv.setGravity(Gravity.CENTER);
		tv.setText("暂无数据");

		getMyRedPacket(true, true);
	}

	private BizDataAsyncTask<List<MyRedPacketUnusedModel>> task;

	private void getMyRedPacket(final boolean first, final boolean isPullDown) {

		task = new BizDataAsyncTask<List<MyRedPacketUnusedModel>>() {

			@Override
			protected void onExecuteSucceeded(List<MyRedPacketUnusedModel> result) {
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
					adapter.addItem(result.get(i));
				}
				adapter.notifyDataSetChanged();

				pullToRefreshListView.onRefreshComplete();

			}

			@Override
			protected List<MyRedPacketUnusedModel> doExecute() throws ZYException, BizFailure {
				// TODO Auto-generated method stub
				if (isPullDown) {

					return AccountBiz.getMyRedCouponUnused("0", ExtraConfig.DEFAULT_PAGE_COUNT + "");
				} else {
					if (isEnd) {
						return new ArrayList<MyRedPacketUnusedModel>();
					} else {
						return AccountBiz.getMyRedCouponUnused(pageIndex + "", ExtraConfig.DEFAULT_PAGE_COUNT + "");
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

	/**
	 * 兑现任务
	 */
	private BizDataAsyncTask<String> cashTask;

	private void couponToCash(final MyRedPacketUnusedModel model) {

		cashTask = new BizDataAsyncTask<String>() {

			@Override
			protected void onExecuteSucceeded(String result) {
				if (result.trim().equals("1")) {
					AlertUtil.t(getActivity(), "兑现成功");
				} else {
					AlertUtil.t(getActivity(), "兑现失败");
				}

				getMyRedPacket(true, true);
				listener.onCouponToCashListener();
			}

			@Override
			protected String doExecute() throws ZYException, BizFailure {

				return AccountBiz.couponToCash(model.getRED_PACKET_TEMPLET_ID(), model.getRED_PACKET_LOG_ID());
			}

			@Override
			protected void OnExecuteFailed(String error) {
				if (!StringUtil.isEmpty(error)) {
					AlertUtil.t(getActivity(), error);
				}
			}
		};
		cashTask.execute();
	}

	private BizDataAsyncTask<String> changeTask;

	private void couponExchange() {
		changeTask = new BizDataAsyncTask<String>() {

			@Override
			protected void onExecuteSucceeded(String result) {
				if (result.trim().equals("1")) {
					AlertUtil.t(getActivity(), "兑换成功");
				} else {
					AlertUtil.t(getActivity(), "兑换失败");
				}

				getMyRedPacket(true, true);
				listener.onCouponToCashListener();//刷新我的红包
			}

			@Override
			protected String doExecute() throws ZYException, BizFailure {

				return AccountBiz.couponExchange(et_red_unused_exchange.getText().toString().trim());
			}

			@Override
			protected void OnExecuteFailed(String error) {
				AlertUtil.t(getActivity(), error);
			}
		};

		changeTask.execute();
	}

	class RedPacketUnusedListAdapter extends BaseAdapter {

		private List<MyRedPacketUnusedModel> list = new ArrayList<MyRedPacketUnusedModel>();
		private Context context;

		public RedPacketUnusedListAdapter(Context context) {
			// TODO Auto-generated constructor stub
			this.context = context;
		}

		public void addItem(MyRedPacketUnusedModel cellOptions) {
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
		public MyRedPacketUnusedModel getItem(int position) {
			// TODO Auto-generated method stub
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			UnusedListViewHolder viewHolder;

			if (convertView == null) {

				convertView = LayoutInflater.from(context).inflate(R.layout.item_listview_my_red_packet_unused, parent,
						false);
				viewHolder = new UnusedListViewHolder(convertView);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (UnusedListViewHolder) convertView.getTag();
			}

			// list设置数据

			viewHolder.tv_item_red_unused_amount.setText(list.get(position).getOVERPLUS_AMOUNT().replace("元", ""));
			viewHolder.tv_item_red_unused_data.setText(list.get(position).getEND_DATE());

			// 兑现
			final int pos = position;
			viewHolder.tv_item_red_unused_cash.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					couponToCash(list.get(position));
				}
			});

			// 查看红包使用规则
			viewHolder.tv_item_red_unused_rule.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					Intent intent = new Intent(getActivity(), ActivityUseRedPacketRule.class);
					intent.putExtra(ExtraConfig.IntentExtraKey.RED_PACKET_RULE, list.get(position).getRULE());
					startActivity(intent);

				}
			});

			return convertView;
		}

		class UnusedListViewHolder {

			TextView tv_item_red_unused_data;// 有效期
			TextView tv_item_red_unused_amount;// 金额
			TextView tv_item_red_unused_cash;// 兑现
			TextView tv_item_red_unused_rule;// 使用规则

			public UnusedListViewHolder(View itemView) {
				tv_item_red_unused_data = (TextView) itemView.findViewById(R.id.tv_item_red_unused_data);
				tv_item_red_unused_amount = (TextView) itemView.findViewById(R.id.tv_item_red_unused_amount);
				tv_item_red_unused_cash = (TextView) itemView.findViewById(R.id.tv_item_red_unused_cash);
				tv_item_red_unused_rule = (TextView) itemView.findViewById(R.id.tv_item_red_unused_rule);

			}

		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_red_unused_exchange:
			// 兑换
			if (StringUtil.isEmpty(et_red_unused_exchange.getText().toString().trim())) {
				AlertUtil.t(getActivity(), "请输入兑换码");
				return;
			}
			
			couponExchange();

			break;

		default:
			break;
		}
	}
	
	
	
	public CouponToCashSuccess getListener() {
		return listener;
	}

	public void setListener(CouponToCashSuccess listener) {
		this.listener = listener;
	}

	/**
	 * 定义接口刷新兑现后刷新红包已使用
	 * @author Ls
	 *
	 */
	public interface CouponToCashSuccess{
		void onCouponToCashListener();
	};

}