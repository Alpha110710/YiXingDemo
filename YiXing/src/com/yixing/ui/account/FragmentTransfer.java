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
import com.yixing.model.MyTransferModel;
import com.yixing.ui.base.BaseFragment;

/**
 * 
 * @author Ls 我的转让 可转让
 *
 */
public class FragmentTransfer extends BaseFragment {

	private PullToRefreshListView pullToRefreshListView;
	private TransferListAdapter adapter;

	private int pageIndex = 0;
	private boolean isEnd = false;
	private TextView tv;

	private TransferListener listener;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment_my_transfer_can, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		init();

	}

	private void init() {
		pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.list_my_transfer);

		adapter = new TransferListAdapter(getActivity());
		pullToRefreshListView.getRefreshableView().setAdapter(adapter);

		// 刷新方法
		pullToRefreshListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {
			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub

				getMyTransfer(false, true);

			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				getMyTransfer(false, false);
			}

		});

		tv = new TextView(getActivity());
		tv.setGravity(Gravity.CENTER);
		tv.setText("暂无数据");

		getMyTransfer(true, true);
	}

	private BizDataAsyncTask<List<BaseModel>> task;

	private void getMyTransfer(final boolean first, final boolean isPullDown) {

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
					adapter.addItem((MyTransferModel) result.get(i));
				}
				adapter.notifyDataSetChanged();

				pullToRefreshListView.onRefreshComplete();

			}

			@Override
			protected List<BaseModel> doExecute() throws ZYException, BizFailure {
				// TODO Auto-generated method stub
				if (isPullDown) {

					return AccountBiz.getMyInvestList("3", "", ExtraConfig.DEFAULT_PAGE_COUNT + "");
				} else {
					if (isEnd) {
						return new ArrayList<BaseModel>();
					} else {
						return AccountBiz.getMyInvestList("3", pageIndex + "", ExtraConfig.DEFAULT_PAGE_COUNT + "");
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

	class TransferListAdapter extends BaseAdapter {

		private List<MyTransferModel> list = new ArrayList<MyTransferModel>();
		private Context context;

		public TransferListAdapter(Context context) {
			this.context = context;
		}

		public void addItem(MyTransferModel cellOptions) {
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
		public MyTransferModel getItem(int position) {
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
			TransferListViewHolder viewHolder;

			if (convertView == null) {

				convertView = LayoutInflater.from(context).inflate(R.layout.item_listview_my_transfer_can, parent,
						false);
				viewHolder = new TransferListViewHolder(convertView);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (TransferListViewHolder) convertView.getTag();
			}

			// 设置数据
			viewHolder.tv_item_transfer_day.setText(list.get(position).getREMAIN_DAYS());
			viewHolder.tv_item_transfer_have.setText(String.valueOf(list.get(position).getTENDER_AMOUNT()));
			viewHolder.tv_item_transfer_title.setText(list.get(position).getPRODUCTS_TITLE());
			viewHolder.tv_item_transfer_year.setText(String.valueOf(list.get(position).getFINANCE_INTEREST_RATE()));

			// 回款详情
			final int pos = position;
			viewHolder.tv_item_transfer_zhuanrang.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// 点击跳转到回款详情 需要有一个标记 确认跳转到回款详情--债权
					Intent intent = new Intent(getActivity(), ActivityTransfer.class);
					intent.putExtra(ExtraConfig.IntentExtraKey.MY_TRANSFER_OID_TENDER_ID,
							list.get(pos).getOID_TENDER_ID());
					intent.putExtra(ExtraConfig.IntentExtraKey.MY_TRANSFER_TENDER_FROM, list.get(pos).getTENDER_FROM());
					intent.putExtra(ExtraConfig.IntentExtraKey.MY_TRANSFER_FINACE_NAME,
							list.get(pos).getFINANCE_PRODUCTS_NAME());
					startActivityForResult(intent, ExtraConfig.RequestCode.REQUEST_CODE_FOR_MY_TRANSFER);

				}
			});

			return convertView;
		}

		class TransferListViewHolder {

			TextView tv_item_transfer_day;// 剩余天数
			TextView tv_item_transfer_have;// 当前持有
			TextView tv_item_transfer_title;// 标题
			TextView tv_item_transfer_year;// 原标年化
			TextView tv_item_transfer_zhuanrang;// 转让按钮

			public TransferListViewHolder(View itemView) {
				tv_item_transfer_day = (TextView) itemView.findViewById(R.id.tv_item_transfer_day);
				tv_item_transfer_have = (TextView) itemView.findViewById(R.id.tv_item_transfer_have);
				tv_item_transfer_title = (TextView) itemView.findViewById(R.id.tv_item_transfer_title);
				tv_item_transfer_year = (TextView) itemView.findViewById(R.id.tv_item_transfer_year);
				tv_item_transfer_zhuanrang = (TextView) itemView.findViewById(R.id.tv_item_transfer_zhuanrang);

			}

		}

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == ExtraConfig.RequestCode.REQUEST_CODE_FOR_MY_TRANSFER) {
			// 回调刷新
			getMyTransfer(true, true);
			listener.onTransferListener();
		}
	}

	public void setListener(TransferListener listener) {
		this.listener = listener;
	}

	public interface TransferListener {
		void onTransferListener();
	}

}
