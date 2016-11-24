package com.yixing.ui.more;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yixing.ExtraConfig;
import com.yixing.ExtraConfig.IntentExtraKey;
import com.yixing.ExtraConfig.RequestCode;
import com.yixing.R;
import com.yixing.biz.MoreBiz;
import com.yixing.biz.exception.BizFailure;
import com.yixing.biz.exception.ZYException;
import com.yixing.biz.task.BizDataAsyncTask;
import com.yixing.model.NewNoticeModel;
import com.yixing.ui.base.BaseFragment;
import com.yixing.ui.init.ActivityWebView;

public class FragmentNewsNotice extends BaseFragment {

	private PullToRefreshListView pullToRefreshListView;
	private NewNoticeAdapter adapter;

	private int pageIndex = 0;
	private boolean isEnd = false;
	private TextView tv;

	private int clickPosition;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment_about_us_new_notice,
				container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		init();
	}

	// 刷新需要继续修改

	private void init() {
		pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.ptrlv_fragment_new_notice);

		adapter = new NewNoticeAdapter(getActivity());
		pullToRefreshListView.getRefreshableView().setAdapter(adapter);

		// 刷新方法
		pullToRefreshListView
				.setOnRefreshListener(new OnRefreshListener2<ListView>() {
					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						// TODO Auto-generated method stub

						getNewNotice(false, true);

					}

					@Override
					public void onPullUpToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						// TODO Auto-generated method stub
						getNewNotice(false, false);
					}

				});

		tv = new TextView(getActivity());
		tv.setGravity(Gravity.CENTER);
		tv.setText("暂无数据");

		getNewNotice(true, true);
	}

	private BizDataAsyncTask<List<NewNoticeModel>> getNewNoticetask;

	private void getNewNotice(final boolean first, final boolean isPullDown) {

		getNewNoticetask = new BizDataAsyncTask<List<NewNoticeModel>>() {

			@Override
			protected void onExecuteSucceeded(List<NewNoticeModel> result) {
				// TODO Auto-generated method stub

				if (result.size() < ExtraConfig.DEFAULT_PAGE_COUNT) {
					isEnd = true;
					if (isPullDown && result.size() == 0) {
						pullToRefreshListView.setEmptyView(tv);
					}

					pullToRefreshListView.setPullLabel("没有更多数据",
							Mode.PULL_FROM_END);
					pullToRefreshListView.setReleaseLabel("没有更多数据",
							Mode.PULL_FROM_END);
					pullToRefreshListView.setRefreshingLabel("没有更多数据",
							Mode.PULL_FROM_END);
				} else {
					isEnd = false;
					pullToRefreshListView.setPullLabel("上拉刷新",
							Mode.PULL_FROM_END);
					pullToRefreshListView.setReleaseLabel("放开以刷新",
							Mode.PULL_FROM_END);
					pullToRefreshListView.setRefreshingLabel("正在载入",
							Mode.PULL_FROM_END);
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
			protected List<NewNoticeModel> doExecute() throws ZYException,
					BizFailure {
				// TODO Auto-generated method stub
				if (isPullDown) {

					return MoreBiz.getNewNoticeList(0,
							ExtraConfig.DEFAULT_PAGE_COUNT);
				} else {
					if (isEnd) {
						return new ArrayList<NewNoticeModel>();
					} else {
						return MoreBiz.getNewNoticeList(pageIndex,
								ExtraConfig.DEFAULT_PAGE_COUNT);
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
			getNewNoticetask.setWaitingView(getWaitingView());
		}

		getNewNoticetask.execute();

	}

	class NewNoticeAdapter extends BaseAdapter {

		private List<NewNoticeModel> list = new ArrayList<NewNoticeModel>();
		private Context context;

		public NewNoticeAdapter(Context context) {
			// TODO Auto-generated constructor stub

			this.context = context;
		}

		public void addItem(NewNoticeModel cellOptions) {
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
		public NewNoticeModel getItem(int position) {
			// TODO Auto-generated method stub
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			HoldListViewHolder viewHolder;

			if (convertView == null) {

				convertView = LayoutInflater.from(context).inflate(
						R.layout.item_listview_new_notice, parent, false);
				viewHolder = new HoldListViewHolder(convertView);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (HoldListViewHolder) convertView.getTag();
			}

			/*
			 * // 设置数据 if ("1".equals(list.get(position).getOpenFlg())) {
			 * viewHolder.iv_point.setVisibility(View.INVISIBLE);
			 * //holder.name.setTypeface(null, Typeface.NORMAL); } else {
			 * viewHolder.iv_point.setVisibility(View.VISIBLE);
			 * //holder.name.setTypeface(null, Typeface.BOLD); }
			 */

			viewHolder.tv_news_title.setText(list.get(position).getTitle());
			viewHolder.tv_news_date.setText(list.get(position).getDate());

			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// MessageItem messageItem = (MessageItem)
					// v.getTag(TAG_DATA_SOURCE);
					
					  NewNoticeModel messageItem = list.get(position);
					  /* clickPosition = position;
					  Intent i = new
					  Intent(getActivity(), ActivityMessageDetail.class);
					  i.putExtra("messageId", messageItem.getId());
					  if
					  (messageItem.getMailStatus().contains("未读")) {
					  i.putExtra("status", messageItem.getMailStatus()); }
					  startActivityForResult(i,
					  RequestCode.REQUEST_CODE_FOR_MESSAGE_INFO);*/
					  
					  
					  Intent itNoticeDetail = new Intent(getActivity(), ActivityWebView.class);
					  itNoticeDetail.putExtra(IntentExtraKey.WEB_VIEW_FROM, 6);
					  itNoticeDetail.putExtra("LINKURL", messageItem.getId());
					  startActivity(itNoticeDetail);
					 
				}
			});
			return convertView;
		}

		class HoldListViewHolder {

			ImageView iv_point;
			TextView tv_news_title;// 剩余天数
			TextView tv_news_date;// 当前持有

			public HoldListViewHolder(View itemView) {
				iv_point = (ImageView) itemView.findViewById(R.id.iv_point);
				tv_news_title = (TextView) itemView
						.findViewById(R.id.tv_news_title);
				tv_news_date = (TextView) itemView
						.findViewById(R.id.tv_news_time);

			}

		}

	}

}
