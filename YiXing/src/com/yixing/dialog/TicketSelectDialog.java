package com.yixing.dialog;

import java.util.ArrayList;
import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.yixing.ExtraConfig;
import com.yixing.R;
import com.yixing.biz.InvestmentBiz;
import com.yixing.biz.RedPacketBiz;
import com.yixing.biz.TicketBiz;
import com.yixing.biz.exception.BizFailure;
import com.yixing.biz.exception.ZYException;
import com.yixing.biz.task.BizDataAsyncTask;
import com.yixing.model.BaseModel;
import com.yixing.model.InvestmentDetailModel;
import com.yixing.model.InvestmentModel;
import com.yixing.model.RedPacketModel;
import com.yixing.model.TicketModel;
import com.yixing.ui.widget.CircleProgressBar;
import com.yixing.utils.android.DialogInterface;
import com.yixing.utils.java.AlertUtil;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/***
 * 加息券进入该页面
 * @author Administrator
 *
 */
public class TicketSelectDialog  extends Dialog implements android.view.View.OnClickListener{
	private ImageButton close;//关闭
	private Button selectRed;//使用加息券
	private ListView dialog_list;
	private MyAdapter adapter;
	private Context context;
	private String money;
	private String productId;//标的id
	DialogInterface dialogInterface;
	private List<TicketModel> list=new ArrayList<TicketModel>();
	public TicketSelectDialog(Context context, int theme,String money,String productId,DialogInterface dialogInterface) {
		super(context, theme);
		this.context=context;
		this.money=money;
		this.productId=productId;
		this.dialogInterface=dialogInterface;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_use_red_packet);
		initView();
		initHoldingList();
	}

	/**
	 * 获取数据
	 */
	private BizDataAsyncTask<List<BaseModel>> getTicket;

	// 访问接口获取数据
	private void initHoldingList() {
		getTicket = new BizDataAsyncTask<List<BaseModel>>() {
			@Override
			protected List<BaseModel> doExecute() throws ZYException,
					BizFailure {
						return TicketBiz.getTicket(productId);//投资金额
				}

			@SuppressWarnings("deprecation")
			@Override
			protected void onExecuteSucceeded(List<BaseModel> result) {

				for (int i = 0; i < result.size(); i++) {
					adapter.addItem((TicketModel) result.get(i));
				}
				adapter.notifyDataSetChanged();
			}

			@Override
			protected void OnExecuteFailed(String error) {
				// TODO Auto-generated method stub
			}

		};
		/*
		 * if (first) { getTicket.setWaitingView(getWaitingView()); }
		 */
		getTicket.execute();
		
	}
	private void initView() {
		adapter = new MyAdapter();
		close=(ImageButton) findViewById(R.id.close);
		close.setOnClickListener(this);
		selectRed=(Button) findViewById(R.id.select_red);
		selectRed.setText("使用加息券");
		selectRed.setOnClickListener(this);
		dialog_list=(ListView) findViewById(R.id.dialog_list);
		dialog_list.setAdapter(adapter);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.close://关闭
			dismiss();
			break;
		case R.id.select_red://使用加息券
			String position=adapter.getNum();
			if(!"".equals(position)){
				dialogInterface.selectTicket(true, list.get(Integer.parseInt(position)));
				dismiss();
			}else{
				AlertUtil.t(context, "您没有选中任何加息券");
			}
		default:
			break;
		}
	}
	
	class MyAdapter extends BaseAdapter{
		private String num="";
		@Override
		public int getCount() {
			return list.size();
		}
		//添加数据
		public void addItem(TicketModel model){
			list.add(model);
		}
		public String getNum(){
				
			return num;
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
		public View getView(final int position, View convertView, ViewGroup parent) {
			final ViewHolder viewHodler;
			if (convertView == null) {
				viewHodler = new ViewHolder();
				convertView = LayoutInflater.from(context).inflate(
						R.layout.item_listview_dialog, null);
				viewHodler.endTime=(TextView) convertView.findViewById(R.id.dialog_end_time);
				viewHodler.useCondition=(TextView) convertView.findViewById(R.id.dialog_use_condition);
				viewHodler.money=(TextView) convertView.findViewById(R.id.dialog_money);
				viewHodler.checkBox= (CheckBox) convertView.findViewById(R.id.dialog_checkbox);
				viewHodler.layout_dialog=(LinearLayout) convertView.findViewById(R.id.layout_dialog);
				viewHodler.dialog_use=(TextView) convertView.findViewById(R.id.dialog_use);
				viewHodler.dialog_je=(TextView) convertView.findViewById(R.id.dialog_je);
				viewHodler.money_type=(TextView) convertView.findViewById(R.id.money_type);
				convertView.setTag(viewHodler);
			} else {
				viewHodler = (ViewHolder) convertView.getTag();
			}
			if(!"".equals(num)){
				if((position+"").equals(num))
					viewHodler.checkBox.setChecked(true);
				else
					viewHodler.checkBox.setChecked(false);
			}else{
				viewHodler.checkBox.setChecked(false);
			}
			viewHodler.dialog_use.setText("最低金额:");
			viewHodler.dialog_je.setText("加息券:");
			viewHodler.money_type.setVisibility(View.GONE);
			viewHodler.money.setText(list.get(position).getRate()+"%");
			viewHodler.endTime.setText(list.get(position).getEnd_Time());
			viewHodler.useCondition.setText("投资满"+list.get(position).getInvestMoney()+"元可以使用");
			
			if(Integer.parseInt(money)<Integer.parseInt(list.get(position).getInvestMoney())){
				viewHodler.layout_dialog.setBackgroundColor(Color.parseColor("#f0f0f0"));
			}else{
				viewHodler.layout_dialog.setBackgroundColor(Color.parseColor("#ffffff"));
			}
			
			
			viewHodler.layout_dialog.setOnClickListener(new android.view.View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(Integer.parseInt(money)>=Integer.parseInt(list.get(position).getInvestMoney())){
					if("".equals(num)){
						num=position+"";
					}else
					{
						if((position+"").equals(num))
							num="";
						else
							num=position+"";
					}
					adapter.notifyDataSetChanged();
				 }else{
					 AlertUtil.t(context, "当前加息券不可用，请重新选择");
				 }
					 
				}
			});
			
			
			return convertView;
		}
		class ViewHolder {
			// * 是否选中加息券
			CheckBox checkBox;
			//* 有效期
			TextView endTime;
			TextView dialog_use;//使用条件（前面的文字）
			//*使用条件
			TextView useCondition;
			TextView dialog_je;//金额前面文字
			//*金额
			TextView money;
			TextView money_type;//隐藏红包的单位元
			LinearLayout layout_dialog;
		}
	}
	

}









