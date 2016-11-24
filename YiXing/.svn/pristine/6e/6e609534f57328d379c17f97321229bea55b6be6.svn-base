package com.yixing.ui.investment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Telephony.Sms.Conversations;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yixing.ExtraConfig;
import com.yixing.R;
import com.yixing.biz.InvestmentBiz;
import com.yixing.biz.exception.BizFailure;
import com.yixing.biz.exception.ZYException;
import com.yixing.biz.task.BizDataAsyncTask;
import com.yixing.model.BaseModel;
import com.yixing.model.ControlItem;
import com.yixing.model.InvestmentControlModel;
import com.yixing.model.InvestmentDetailModel;
import com.yixing.model.InvestmentModel;
import com.yixing.model.InvestmentMoreModel;
import com.yixing.photo.ShowBigImage;
import com.yixing.ui.base.BaseFragment;
import com.yixing.ui.investment.FragmentRecords.Myadapter.ViewHolder;
import com.yixing.ui.widget.CircleProgressBar;
import com.yixing.utils.java.AlertUtil;

public class FragmentControl extends BaseFragment {
	private View view;
	private GridView gridView;
	private TextView guarantor;
	private TextView guarantorDescription;
	private Myadapter adapter;
	private InvestmentControlModel controlModel;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return view = inflater.inflate(R.layout.fragment_investment_control,
				null);
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
		getInvestmentDetail(true, getArguments().getString("productId"));
	}

	private void initView() {
		adapter = new Myadapter();
		guarantor = (TextView) view.findViewById(R.id.guarantor);
		guarantorDescription = (TextView) view
				.findViewById(R.id.guarantor_description);
		gridView = (GridView) view.findViewById(R.id.gridview);
		// gridView.setAdapter(adapter);

	}

	private BizDataAsyncTask<InvestmentControlModel> getInvestmentControl;

	/**
	 * 获取数据 标的唯一id
	 */
	private void getInvestmentDetail(final boolean isFirst,
			final String productId) {
		getInvestmentControl = new BizDataAsyncTask<InvestmentControlModel>() {

			@Override
			protected void onExecuteSucceeded(InvestmentControlModel result) {
				controlModel = result;

				for (int i = 0; i < controlModel.getControlItem().size(); i++) {
					adapter.addItem(controlModel.getControlItem().get(i));
				}

				getData();

			}

			@Override
			protected InvestmentControlModel doExecute() throws ZYException,
					BizFailure {
				return InvestmentBiz.getControlData(productId);
			}

			@Override
			protected void OnExecuteFailed(String error) {
			}
		};
		if (isFirst) {
			getInvestmentControl.setWaitingView(getWaitingView());
		}

		getInvestmentControl.execute();
	}

	/**
	 * 给控件赋值
	 * 
	 * @param controlModel
	 */
	private void getData() {
		guarantor.setText(controlModel.getGuarantor());
		guarantorDescription.setText("\u3000\u3000\u3000\u3000\u3000"
				+ controlModel.getGuarantor_Description());
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(getActivity(), ShowBigImage.class);
				intent.putStringArrayListExtra("images", adapter.getList());
				intent.putExtra("currentItem", arg2);
				startActivity(intent);
			}
		});
	}

	class Myadapter extends BaseAdapter {
		private List<ControlItem> list = new ArrayList<ControlItem>();

		@Override
		public int getCount() {
			return list.size();
		}

		// 添加数据
		public void addItem(ControlItem url) {
			list.add(url);
		}

		public ArrayList<String> getList() {
			ArrayList<String> imgStringArray = new ArrayList<String>();
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					imgStringArray.add(list.get(i).getUrl());
				}
			}
			return imgStringArray;
		}

		// 移除所有数据
		public void removeAll() {
			if (list != null && list.size() > 0) {
				for (int i = list.size() - 1; i >= 0; i--) {
					list.remove(i);
				}
			}
		}

		@Override
		public Object getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final ViewHolder viewHodler;
			if (convertView == null) {
				viewHodler = new ViewHolder();
				convertView = LayoutInflater.from(getActivity()).inflate(
						R.layout.list_item_control, null);
				viewHodler.img = (ImageView) convertView
						.findViewById(R.id.control_image);
				convertView.setTag(viewHodler);
			} else {
				viewHodler = (ViewHolder) convertView.getTag();
			}
			ImageLoader.getInstance().displayImage(list.get(position).getUrl(),
					viewHodler.img);
			return convertView;
		}

		class ViewHolder {
			ImageView img;
		}
	}
}
