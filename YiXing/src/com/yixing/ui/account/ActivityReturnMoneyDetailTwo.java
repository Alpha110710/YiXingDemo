package com.yixing.ui.account;

import java.util.ArrayList;
import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.yixing.ExtraConfig;
import com.yixing.R;
import com.yixing.ExtraConfig.IntentExtraKey;
import com.yixing.biz.AccountBiz;
import com.yixing.biz.exception.BizFailure;
import com.yixing.biz.exception.ZYException;
import com.yixing.biz.task.BizDataAsyncTask;
import com.yixing.model.BaseModel;
import com.yixing.model.ReturnMoneyDetailListModel;
import com.yixing.model.ReturnMoneyDetailModelTwo;
import com.yixing.ui.base.BaseActivity;
import com.yixing.ui.init.ActivityWebView;
import com.yixing.utils.java.AlertUtil;
import com.yixing.utils.java.StringUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class ActivityReturnMoneyDetailTwo extends BaseActivity implements OnClickListener {

	private ImageView headerImg;
	private TextView headerTv;

	private PullToRefreshListView pullToRefreshListView;
	private ReturnMoneyDetailAdapter adapter;

	private TextView tv_return_money_two_data;// 剩余期限
	private TextView tv_return_money_two_deal;// 成交价格
	private TextView tv_return_money_two_deal_day;// 交易时间
	private TextView tv_return_money_two_debt;// 债权金额
	private TextView tv_return_money_two_debt_company;// 债权金额 单位
	private TextView tv_return_money_two_fair;// 公允价值
	private TextView tv_return_money_two_num;// 债权编号
	private TextView tv_return_money_two_pact;// 查看投资合同
	private TextView tv_return_money_two_style;// 还款方式
	private TextView tv_return_money_two_title_detail;// 标题
	private TextView tv_return_money_two_year;// 原标年化
	private TextView tv_return_money_two_geyue;// 个月/天

	private int pageIndex = 0;
	private boolean isEnd = false;
	private TextView tv;

	private String tenderId, tenderType;
	private String transferId = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_return_money_detail_two);

		tenderId = getIntent().getStringExtra(ExtraConfig.IntentExtraKey.RETURN_MONEY_DETAIL_TWO_ID);
		tenderType = getIntent().getStringExtra(ExtraConfig.IntentExtraKey.RETURN_MONEY_DETAIL_TWO_TYPR);

		initView();

		init();

	}

	private void initView() {

		headerImg = (ImageView) findViewById(R.id.iv_back);
		headerTv = (TextView) findViewById(R.id.tv_title);

		tv_return_money_two_data = (TextView) findViewById(R.id.tv_return_money_two_data);
		tv_return_money_two_deal = (TextView) findViewById(R.id.tv_return_money_two_deal);
		tv_return_money_two_deal_day = (TextView) findViewById(R.id.tv_return_money_two_deal_day);
		tv_return_money_two_debt = (TextView) findViewById(R.id.tv_return_money_two_debt);
		tv_return_money_two_debt_company = (TextView) findViewById(R.id.tv_return_money_two_debt_company);
		tv_return_money_two_fair = (TextView) findViewById(R.id.tv_return_money_two_fair);
		tv_return_money_two_num = (TextView) findViewById(R.id.tv_return_money_two_num);
		tv_return_money_two_pact = (TextView) findViewById(R.id.tv_return_money_two_pact);
		tv_return_money_two_style = (TextView) findViewById(R.id.tv_return_money_two_style);
		tv_return_money_two_title_detail = (TextView) findViewById(R.id.tv_return_money_two_title_detail);
		tv_return_money_two_year = (TextView) findViewById(R.id.tv_return_money_two_year);
		tv_return_money_two_geyue = (TextView) findViewById(R.id.tv_return_money_two_geyue);

		pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.list_return_money_detail_two);

		tv_return_money_two_pact.setOnClickListener(this);
		headerImg.setOnClickListener(this);
		headerTv.setText("回款详情");

	}

	private void init() {

		adapter = new ReturnMoneyDetailAdapter(this);
		pullToRefreshListView.getRefreshableView().setAdapter(adapter);

		// 刷新方法
		pullToRefreshListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {
			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				getReturnMoneyDetailList(false, true);
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				getReturnMoneyDetailList(false, false);
			}

		});

		getReturnMoneyDetailList(true, true);
		getPaymentDetails();
	}

	// 获取回款详情债权(上半部分)任务
	private BizDataAsyncTask<BaseModel> getDetailTask;

	private void getPaymentDetails() {
		getDetailTask = new BizDataAsyncTask<BaseModel>() {

			@Override
			protected void onExecuteSucceeded(BaseModel result) {
				ReturnMoneyDetailModelTwo two = (ReturnMoneyDetailModelTwo) result;
				tv_return_money_two_data
						.setText(two.getFINANCE_PERIOD_FORMAT().replace("个月", "").replace("天", "").replace("个季度", ""));// 剩余期限

				if (two.getFINANCE_PERIOD_FORMAT().contains("天")) {
					tv_return_money_two_geyue.setText("天");
				} else if (two.getFINANCE_PERIOD_FORMAT().contains("个月")) {
					tv_return_money_two_geyue.setText("个月");
				} else {
					tv_return_money_two_geyue.setText("个季度");
				}

				tv_return_money_two_deal.setText(two.getACTUAL_AMOUNT());
				tv_return_money_two_deal_day.setText(two.getSUCCESS_DATE());
				tv_return_money_two_debt.setText(two.getTENDER_AMOUNT_TRANS());
				tv_return_money_two_debt_company.setText(two.getTENDER_AMOUNT_TRANS_UNIT());
				tv_return_money_two_fair.setText(two.getFAIR_VALUE());
				tv_return_money_two_num.setText("债权编号 : " + two.getTRANSFER_CONTRACT_ID());
				tv_return_money_two_style.setText(two.getREPAY_TYPE());
				tv_return_money_two_title_detail.setText(two.getPRODUCTS_TITLE());
				tv_return_money_two_year.setText(two.getRATE());
				transferId = two.getOID_TRANSFER_ID();

			}

			@Override
			protected BaseModel doExecute() throws ZYException, BizFailure {
				return AccountBiz.getPaymentDetails(tenderId, tenderType);
			}

			@Override
			protected void OnExecuteFailed(String error) {
				if (!StringUtil.isEmpty(error)) {
					AlertUtil.t(ActivityReturnMoneyDetailTwo.this, error);
				}
			}
		};

		getDetailTask.execute();
	}

	/**
	 * 获取回款详情信息列表
	 */
	private BizDataAsyncTask<List<ReturnMoneyDetailListModel>> task;

	private void getReturnMoneyDetailList(final boolean first, final boolean isPullDown) {

		task = new BizDataAsyncTask<List<ReturnMoneyDetailListModel>>() {

			@Override
			protected void onExecuteSucceeded(List<ReturnMoneyDetailListModel> result) {
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
			protected List<ReturnMoneyDetailListModel> doExecute() throws ZYException, BizFailure {
				// TODO Auto-generated method stub
				if (isPullDown) {

					return AccountBiz.getReturnMoneyDetailList(tenderId, tenderType, "0",
							ExtraConfig.DEFAULT_PAGE_COUNT + "");
				} else {
					if (isEnd) {
						return new ArrayList<ReturnMoneyDetailListModel>();
					} else {
						return AccountBiz.getReturnMoneyDetailList(tenderId, tenderType, pageIndex + "",
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

	class ReturnMoneyDetailAdapter extends BaseAdapter {

		private List<ReturnMoneyDetailListModel> list = new ArrayList<ReturnMoneyDetailListModel>();
		private Context context;

		public ReturnMoneyDetailAdapter(Context context) {
			// TODO Auto-generated constructor stub

			this.context = context;
		}

		public void addItem(ReturnMoneyDetailListModel cellOptions) {
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
		public ReturnMoneyDetailListModel getItem(int position) {
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
			ReturnMoneyDetailListViewHolder viewHolder;

			if (convertView == null) {

				convertView = LayoutInflater.from(context).inflate(R.layout.item_listview_return_money_detail, parent,
						false);
				viewHolder = new ReturnMoneyDetailListViewHolder(convertView);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ReturnMoneyDetailListViewHolder) convertView.getTag();
			}

			// list设置数据
			if (list.get(position).getType().equals("1")) {
				// 逾期
				viewHolder.iv_detail_type.setImageResource(R.drawable.return_money_detail_four);
				viewHolder.ll_hide.setVisibility(View.VISIBLE);
				viewHolder.tv_detail_data.setText(list.get(position).getData());
				viewHolder.tv_detail_interest.setText(list.get(position).getInterest());
				viewHolder.tv_detail_money.setText(list.get(position).getMoney());
				viewHolder.tv_detail_out_day.setText(list.get(position).getOut_data());
				viewHolder.tv_detail_out_interest.setText(list.get(position).getOut_interest());
				viewHolder.tv_detail_out_interest_punishment.setText(list.get(position).getOut_interest_punishment());

			} else {
				// 未逾期
				if (list.get(position).getRECOVER_FLG().equals("1")) {
					// 已回款
					viewHolder.iv_detail_type.setImageResource(R.drawable.return_money_detail_three);
					viewHolder.ll_hide.setVisibility(View.GONE);
					viewHolder.tv_detail_data.setText(list.get(position).getData());
					viewHolder.tv_detail_interest.setText(list.get(position).getInterest());
					viewHolder.tv_detail_money.setText(list.get(position).getMoney());
				} else {
					// 未回款
					viewHolder.iv_detail_type.setImageResource(R.drawable.return_money_detail_five);
					viewHolder.ll_hide.setVisibility(View.GONE);
					viewHolder.tv_detail_data.setText(list.get(position).getData());
					viewHolder.tv_detail_interest.setText(list.get(position).getInterest());
					viewHolder.tv_detail_money.setText(list.get(position).getMoney());
				}

			}

			return convertView;
		}

		class ReturnMoneyDetailListViewHolder {

			ImageView iv_detail_type;// 已回款/逾期图片
			TextView tv_detail_data;// 日期
			TextView tv_detail_interest;// 利息
			TextView tv_detail_money;// 本金
			TextView tv_detail_out_day;// 逾期天数
			TextView tv_detail_out_interest;// 逾期利息
			TextView tv_detail_out_interest_punishment;// 逾期罚息
			LinearLayout ll_hide;// 不是逾期会隐藏

			public ReturnMoneyDetailListViewHolder(View itemView) {

				iv_detail_type = (ImageView) itemView.findViewById(R.id.iv_item_return_money_detail_type);
				tv_detail_data = (TextView) itemView.findViewById(R.id.tv_item_return_money_detail_data);
				tv_detail_interest = (TextView) itemView.findViewById(R.id.tv_item_return_money_detail_interest);
				tv_detail_money = (TextView) itemView.findViewById(R.id.tv_item_return_money_detail_money);
				tv_detail_out_day = (TextView) itemView.findViewById(R.id.tv_item_return_money_detail_out_day);
				tv_detail_out_interest = (TextView) itemView
						.findViewById(R.id.tv_item_return_money_detail_out_interest);
				tv_detail_out_interest_punishment = (TextView) itemView
						.findViewById(R.id.tv_item_return_money_detail_out_interest_punishment);
				ll_hide = (LinearLayout) itemView.findViewById(R.id.ll_hide);

			}

		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		case R.id.iv_back:
			// 返回
			finish();
			break;
		case R.id.tv_return_money_two_pact:
			// 查看合同
			Intent intent = new Intent(this, ActivityWebView.class);
			intent.putExtra("LINKURL", tenderId);
			intent.putExtra(IntentExtraKey.WEB_VIEW_FROM, 4);
			if (!StringUtil.isEmpty(transferId)) {
				intent.putExtra("transferId", transferId);
				startActivity(intent);
			}
			break;

		default:
			break;
		}

	}

}
