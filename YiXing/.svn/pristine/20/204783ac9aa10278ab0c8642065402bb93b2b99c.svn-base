package com.yixing.ui.investment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.yixing.R;
import com.yixing.biz.InvestmentBiz;
import com.yixing.biz.exception.BizFailure;
import com.yixing.biz.exception.ZYException;
import com.yixing.biz.task.BizDataAsyncTask;
import com.yixing.model.InvestmentMessageModel;
import com.yixing.model.QualificationModel;
import com.yixing.photo.ShowBigImage;
import com.yixing.ui.base.BaseFragment;

public class FragmentProductMessage extends BaseFragment {
	View view;
	private TextView userName;
	private TextView marital_status;
	private TextView sex;
	private TextView month_income;
	private TextView age;
	private TextView hous_conditions;
	private TextView culture;
	private TextView car;
	private TextView borrow_use;
	private TextView payment_source;
	private TextView describe;
	private TextView audit_opinion;
	private GridView  gridViewBottom;
	MyadapterBottom adapterBottom;
	InvestmentMessageModel investmentMessageModel;
	private ImageView item1,item2,item3,item4,item5;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return view = inflater.inflate(R.layout.fragment_investment_message,
				null);
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
		getInvestmentDetail(true,getArguments().getString("productId"));
  
	}

	private void initView() {
		adapterBottom=new MyadapterBottom();
		item1=(ImageView) view.findViewById(R.id.item1);
		item2=(ImageView) view.findViewById(R.id.item2);
		item3=(ImageView) view.findViewById(R.id.item3);
		item4=(ImageView) view.findViewById(R.id.item4);
		item5=(ImageView) view.findViewById(R.id.item5);
		userName = (TextView) view.findViewById(R.id.uername);
		marital_status = (TextView) view.findViewById(R.id.marital_status);
		sex = (TextView) view.findViewById(R.id.sex);
		month_income = (TextView) view.findViewById(R.id.month_income);
		age = (TextView) view.findViewById(R.id.age);
		hous_conditions = (TextView) view.findViewById(R.id.hous_conditions);
		culture = (TextView) view.findViewById(R.id.culture);
		car = (TextView) view.findViewById(R.id.car);
		borrow_use = (TextView) view.findViewById(R.id.borrow_use);
		payment_source = (TextView) view.findViewById(R.id.payment_source);
		describe = (TextView) view.findViewById(R.id.describe);
		audit_opinion = (TextView) view.findViewById(R.id.audit_opinion);
		gridViewBottom = (GridView) view.findViewById(R.id.gridview);
		gridViewBottom.setAdapter(adapterBottom);
	}
	private BizDataAsyncTask<InvestmentMessageModel> getInvestmentDetail;
	/**
	 * 获取数据
	 */
	private void getInvestmentDetail(final boolean isFirst,final String productId) {
		getInvestmentDetail = new BizDataAsyncTask<InvestmentMessageModel>() {

			@Override
			protected void onExecuteSucceeded(InvestmentMessageModel result) {
				 investmentMessageModel=result;
				for(int i=0;i<investmentMessageModel.getQualification_proof().size();i++){//资质认证
					adapterBottom.addItem(investmentMessageModel.getQualification_proof().get(i));
				}
				adapterBottom.notifyDataSetChanged();
				getData();
			}


			@Override
			protected InvestmentMessageModel doExecute() throws ZYException, BizFailure {
				return InvestmentBiz.getMessageData(productId);
			}

			@Override
			protected void OnExecuteFailed(String error) {
			}
		};
		if (isFirst) {
			getInvestmentDetail.setWaitingView(getWaitingView());
		}

		getInvestmentDetail.execute();
	}
	private void getData() {
		userName .setText(investmentMessageModel.getUserName());
		marital_status.setText(investmentMessageModel.getMaritalStatus());
		sex.setText(investmentMessageModel.getSex());
		month_income .setText(investmentMessageModel.getMonth_income());
		age .setText(investmentMessageModel.getAge());
		hous_conditions .setText(investmentMessageModel.getHousConditions());
		culture.setText(investmentMessageModel.getCulture());
		car.setText(investmentMessageModel.getCar());
		borrow_use.setText(investmentMessageModel.getBorrow_use());
		payment_source.setText(investmentMessageModel.getPayment_source());
		describe .setText(investmentMessageModel.getDescribe());
		audit_opinion.setText(investmentMessageModel.getAudit_opinion());
		
		//资质的显示隐藏
		if("1".equals(investmentMessageModel.getVERIFY_BUSINESS()))
			item1.setImageResource(R.drawable.yyzz_ok);
		if("1".equals(investmentMessageModel.getVERIFY_ORGANIZATION()))
			item2.setImageResource(R.drawable.jgdm_ok);
		if("1".equals(investmentMessageModel.getVERIFY_LICENCE()))
			item3.setImageResource(R.drawable.xkz_ok);
		if("1".equals(investmentMessageModel.getVERIFY_ENTERPRISE()))
			item4.setImageResource(R.drawable.qyls_ok);
		if("1".equals(investmentMessageModel.getVERIFY_TAX()))
			item5.setImageResource(R.drawable.swdj_ok);
		gridViewBottom.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				  Intent intent=new Intent(getActivity(),ShowBigImage.class);
			        intent.putStringArrayListExtra("images",adapterBottom.getList());
			        intent.putExtra("currentItem",arg2);
			        startActivity(intent);
			}
		});
	}
	
	
	/**
	 * 资质证照
	 * @author Administrator
	 *
	 */
	class MyadapterBottom extends BaseAdapter {
		public List<QualificationModel> list = new ArrayList<QualificationModel>();

		@Override
		public int getCount() {
			return list.size();
		}

		// 添加数据
		public void addItem(QualificationModel url) {
			list.add(url);
		}
		public ArrayList<String> getList(){
		 ArrayList<String> 	imgStringArray=new ArrayList<String>();
			if (list != null && list.size() > 0) {
				for (int i = 0; i<list.size(); i++) {
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
			ImageLoader.getInstance().displayImage(list.get(position).getUrl(), viewHodler.img);
			return convertView;
		}

		class ViewHolder {
			ImageView img;
		}
	}
	
}


















