package com.yixing.ui.account;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.DialogInterface.OnDismissListener;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.yixing.dialog.AddRateCouponGivedDialog;
import com.yixing.model.MyAddRateCouponGiveCheckModel;
import com.yixing.model.MyAddRateCouponUnusedModel;
import com.yixing.model.MyInvestHoldModel;
import com.yixing.model.MyRedPacketUnusedModel;
import com.yixing.model.MyAddRateCouponUnusedModel;
import com.yixing.ui.account.FragmentMyInvestHold.InvestListHoldAdapter.HoldListViewHolder;
import com.yixing.ui.base.BaseFragment;
import com.yixing.utils.java.AlertUtil;
import com.yixing.utils.java.StringUtil;

/**
 * 我的红包过期
 * 
 * @author Ls
 *
 */
public class FragmentMyAddRateCouponUnused extends BaseFragment implements OnClickListener {

	private PullToRefreshListView pullToRefreshListView;
	private RedPacketOutDataListAdapter adapter;

	private TextView tv_add_rate_unused_exchange;// 兑现
	private EditText et_add_rate_unused_code;// 兑换码

	private int pageIndex = 0;
	private boolean isEnd = false;
	private TextView tv;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment_my_add_rate_coupon_unused, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		pullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.list_add_rate_unused);
		et_add_rate_unused_code = (EditText) view.findViewById(R.id.et_add_rate_unused_code);
		tv_add_rate_unused_exchange = (TextView) view.findViewById(R.id.tv_add_rate_unused_exchange);

		tv_add_rate_unused_exchange.setOnClickListener(this);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		init();
	}

	private void init() {
		adapter = new RedPacketOutDataListAdapter(getActivity());

		pullToRefreshListView.getRefreshableView().setAdapter(adapter);
		pullToRefreshListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				getMyAddRateCoupon(false, true);
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				getMyAddRateCoupon(false, false);
			}
		});

		tv = new TextView(getActivity());
		tv.setGravity(Gravity.CENTER);
		tv.setText("暂无数据");

		getMyAddRateCoupon(true, true);
	}

	// 获取未使用加息券任务
	private BizDataAsyncTask<List<MyAddRateCouponUnusedModel>> task;

	private void getMyAddRateCoupon(final boolean first, final boolean isPullDown) {

		task = new BizDataAsyncTask<List<MyAddRateCouponUnusedModel>>() {

			@Override
			protected void onExecuteSucceeded(List<MyAddRateCouponUnusedModel> result) {
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
					adapter.addItem((MyAddRateCouponUnusedModel) result.get(i));
				}
				adapter.notifyDataSetChanged();

				pullToRefreshListView.onRefreshComplete();

			}

			@Override
			protected List<MyAddRateCouponUnusedModel> doExecute() throws ZYException, BizFailure {
				// TODO Auto-generated method stub
				if (isPullDown) {

					return AccountBiz.getMyAddCouponUnused("0", ExtraConfig.DEFAULT_PAGE_COUNT + "");
				} else {
					if (isEnd) {
						return new ArrayList<MyAddRateCouponUnusedModel>();
					} else {
						return AccountBiz.getMyAddCouponUnused(pageIndex + "", ExtraConfig.DEFAULT_PAGE_COUNT + "");
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

	private BizDataAsyncTask<String> changeTask;

	private void interestRateExchange() {
		changeTask = new BizDataAsyncTask<String>() {

			@Override
			protected void onExecuteSucceeded(String result) {
				if (result.trim().equals("1")) {
					AlertUtil.t(getActivity(), "兑换成功");
				} else {
					AlertUtil.t(getActivity(), "兑换失败");
				}

				getMyAddRateCoupon(true, true);
			}

			@Override
			protected String doExecute() throws ZYException, BizFailure {

				return AccountBiz.interestRateExchange(et_add_rate_unused_code.getText().toString().trim());
			}

			@Override
			protected void OnExecuteFailed(String error) {

				if (!StringUtil.isEmpty(error)) {
					AlertUtil.t(getActivity(), error);
				}
			}
		};

		changeTask.execute();
	}

	class RedPacketOutDataListAdapter extends BaseAdapter {

		private List<MyAddRateCouponUnusedModel> list = new ArrayList<MyAddRateCouponUnusedModel>();
		private Context context;

		public RedPacketOutDataListAdapter(Context context) {
			// TODO Auto-generated constructor stub
			this.context = context;
		}

		public void addItem(MyAddRateCouponUnusedModel cellOptions) {
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
		public MyAddRateCouponUnusedModel getItem(int position) {
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
			OutDataListViewHolder viewHolder;

			if (convertView == null) {

				convertView = LayoutInflater.from(context).inflate(R.layout.item_listview_my_add_rate_coupon_unused,
						parent, false);
				viewHolder = new OutDataListViewHolder(convertView);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (OutDataListViewHolder) convertView.getTag();
			}

			// list设置数据

			viewHolder.tv_item_add_rate_data.setText(list.get(position).getEFFECTIVE_DATE());
			viewHolder.tv_item_add_rate_rate.setText(list.get(position).getRATE_COUPON_POSITION().replace("%", ""));
			if (list.get(position).getTRANSFER_ABLE_FLG().equals("1")) {
				//1可赠送 
				viewHolder.tv_item_add_rate_give.setVisibility(View.VISIBLE);
			} else {
				viewHolder.tv_item_add_rate_give.setVisibility(View.GONE);
			}

			viewHolder.tv_item_add_rate_rule.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// 加息券使用规则
					Intent intent = new Intent(getActivity(), ActivityUseAddRateCouponRule.class);
					intent.putExtra(ExtraConfig.IntentExtraKey.RED_PACKET_RULE, list.get(position).getRULE());
					startActivity(intent);
				}
			});

			viewHolder.tv_item_add_rate_give.setOnClickListener(new OnClickListener() {
				// 赠送
				@Override
				public void onClick(View v) {
					// 显示dialog
					getDialog(list.get(position).getRATE_COUPON_SEND_ID());
				}
			});

			return convertView;
		}

		class OutDataListViewHolder {

			TextView tv_item_add_rate_rate;// 利率
			TextView tv_item_add_rate_data;// 有效日期
			TextView tv_item_add_rate_rule;// 使用规则
			TextView tv_item_add_rate_give;// 赠送

			public OutDataListViewHolder(View itemView) {
				tv_item_add_rate_rate = (TextView) itemView.findViewById(R.id.tv_item_add_rate_unused_rate);
				tv_item_add_rate_data = (TextView) itemView.findViewById(R.id.tv_item_add_rate_unused_data);
				tv_item_add_rate_rule = (TextView) itemView.findViewById(R.id.tv_item_add_rate_unused_rule);
				tv_item_add_rate_give = (TextView) itemView.findViewById(R.id.tv_item_add_rate_unused_give);

			}

		}

	}

	private void getDialog(String id) {
		AddRateCouponGivedDialog dialog = new AddRateCouponGivedDialog(getActivity(), R.style.My_Dialog, id);
		dialog.show();
		dialog.setCancelable(false);
		dialog.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss(DialogInterface dialog) {
				getMyAddRateCoupon(true, true);
			}
		});

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_add_rate_unused_exchange:
			// 兑换
			if (StringUtil.isEmpty(et_add_rate_unused_code.getText().toString().trim())) {
				AlertUtil.t(getActivity(), "请输入兑换码");
				return;
			}
			interestRateExchange();

			break;

		default:
			break;
		}
	}

}