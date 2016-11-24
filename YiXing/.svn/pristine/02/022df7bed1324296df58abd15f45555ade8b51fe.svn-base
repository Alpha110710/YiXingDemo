package com.yixing.ui.account;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.yixing.ExtraConfig;
import com.yixing.MainActivity;
import com.yixing.R;
import com.yixing.YixingApp;
import com.yixing.biz.AccountBiz;
import com.yixing.biz.exception.BizFailure;
import com.yixing.biz.exception.ZYException;
import com.yixing.biz.task.BizDataAsyncTask;

import com.yixing.model.BaseModel;
import com.yixing.model.MyAccountModel;
import com.yixing.model.MyInvestHoldModel;
import com.yixing.model.WithdrawModel;
import com.yixing.ui.account.FragmentMyInvestHold.InvestListHoldAdapter;
import com.yixing.ui.base.BaseActivity;
import com.yixing.utils.java.AlertUtil;
import com.yixing.utils.java.StringUtil;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ActivityWithdrawDeposit extends BaseActivity implements OnClickListener {

	private TextView tv_title;
	private ImageView iv_back;

	private Button btn_withdraw_confirm;// 确认提现
	private Button btn_withdraw_verify;// 获取验证码
	private EditText et_withdraw_amount;// 输入金额
	private EditText et_withdraw_verify;// 输入验证码
	private TextView tv_withdraw_can_use;// 可使用金额
	private PullToRefreshListView pullToRefreshListView;

	private withdrawListAdapter adapter;
	private MyAccountModel mMyAccount;

	private int pageIndex = 0;
	private boolean isEnd = false;
	private TextView tv;

	private Timer timer;
	private TimerTask timerTask;
	private int count = 120;

	private Double useMoney;// 可用余额

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_withdraw_deposit);
		mMyAccount = getIntent().getParcelableExtra(ExtraConfig.IntentExtraKey.MY_ACCOUNT);

		initView();
		init();
	}

	private void initView() {
		tv_title = (TextView) findViewById(R.id.tv_title);
		iv_back = (ImageView) findViewById(R.id.iv_back);

		btn_withdraw_confirm = (Button) findViewById(R.id.btn_withdraw_confirm);
		btn_withdraw_verify = (Button) findViewById(R.id.btn_withdraw_verify);
		et_withdraw_amount = (EditText) findViewById(R.id.et_withdraw_amount);
		et_withdraw_verify = (EditText) findViewById(R.id.et_withdraw_verify);
		tv_withdraw_can_use = (TextView) findViewById(R.id.tv_withdraw_amount_can_use);
		pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.list_withdraw);

		btn_withdraw_confirm.setOnClickListener(this);
		btn_withdraw_verify.setOnClickListener(this);
		iv_back.setOnClickListener(this);
		tv_title.setText("提现");

	}

	private BizDataAsyncTask<String> getVerificationTask;

	/**
	 * 申请验证码
	 */
	private void getVertifyCodeForNewPhoneNum() {

		if (StringUtil.isEmpty(et_withdraw_amount.getText().toString().trim())) {
			AlertUtil.t(this, "请输入提现金额");
			return;
		}

		// SoftInputUtil.hideSoftKeyboard(ket_phoneNum.getEditText());
		getVerificationTask = new BizDataAsyncTask<String>(getWaitingView()) {

			@Override
			protected void onExecuteSucceeded(String result) {
				btn_withdraw_verify.setClickable(false);
				runTimerTask();
			}

			@Override
			protected String doExecute() throws ZYException, BizFailure {

				return AccountBiz.getVertifyCodeForBank(mMyAccount.getPhoneNumber().trim(), "5");
			}

			@Override
			protected void OnExecuteFailed(String error) {

				if (!StringUtil.isEmpty(error)) {

					AlertUtil.t(ActivityWithdrawDeposit.this, error);
				}
			}
		};

		getVerificationTask.execute();
	}

	private void init() {

		adapter = new withdrawListAdapter(this);
		pullToRefreshListView.getRefreshableView().setAdapter(adapter);

		// 刷新方法
		pullToRefreshListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {
			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

				getwithdrawRecord(false, true);

			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

				getwithdrawRecord(false, false);
			}

		});

		tv = new TextView(this);
		tv.setGravity(Gravity.CENTER);
		tv.setText("暂无数据");

		getwithdrawRecord(true, true);
	}

	/**
	 * 获取提现记录任务
	 */
	private BizDataAsyncTask<WithdrawModel> getwithdrawRecord;

	private void getwithdrawRecord(final boolean first, final boolean isPullDown) {

		getwithdrawRecord = new BizDataAsyncTask<WithdrawModel>() {

			@Override
			protected void onExecuteSucceeded(WithdrawModel result) {

				tv_withdraw_can_use.setText(result.getUSABLE_AMOUNT());
				useMoney = Double.parseDouble(result.getUSABLE_AMOUNT().replace(",", ""));

				if (result.getRETMODEL_LIST().size() < ExtraConfig.DEFAULT_PAGE_COUNT) {
					isEnd = true;
					if (isPullDown && result.getRETMODEL_LIST().size() == 0) {
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

				if (!isEnd) {
					pageIndex++;
				}

				for (int i = 0; i < result.getRETMODEL_LIST().size(); i++) {
					adapter.addItem(result.getRETMODEL_LIST().get(i));
				}
				adapter.notifyDataSetChanged();

				pullToRefreshListView.onRefreshComplete();

			}

			@Override
			protected WithdrawModel doExecute() throws ZYException, BizFailure {
				// TODO Auto-generated method stub
				if (isPullDown) {

					return AccountBiz.withdrawRecord("0", ExtraConfig.DEFAULT_PAGE_COUNT + "");
				} else {
					return AccountBiz.withdrawRecord(pageIndex + "", ExtraConfig.DEFAULT_PAGE_COUNT + "");
				}
			}

			@Override
			protected void OnExecuteFailed(String error) {
				// TODO Auto-generated method stub
				if (!StringUtil.isEmpty(error)) {
					AlertUtil.t(ActivityWithdrawDeposit.this, error);
				}

			}
		};

		if (first) {
			getwithdrawRecord.setWaitingView(getWaitingView());
		}

		getwithdrawRecord.execute();

	}

	private void invokeCharge() {

		if (StringUtil.isEmpty(et_withdraw_amount.getText().toString().trim())) {
			AlertUtil.t(this, "请输入提取金额");
			et_withdraw_amount.requestFocus();
			return;
		}

		if (StringUtil.isEmpty(et_withdraw_verify.getText().toString().trim())) {
			AlertUtil.t(this, "请输入短信验证码");
			et_withdraw_verify.requestFocus();
			return;
		}

		if (useMoney < Double.parseDouble(et_withdraw_amount.getText().toString().trim())) {
			AlertUtil.t(ActivityWithdrawDeposit.this, "提现金额大于可使用金额, 请重新输入");
			return;
		}

		AlertCheck();

	}

	/**
	 * 提现任务
	 */
	private BizDataAsyncTask<String> withdrawTask;

	private void dowithdraw() {

		withdrawTask = new BizDataAsyncTask<String>() {

			@Override
			protected void onExecuteSucceeded(String result) {
				if (result.trim().equals("1")) {
					showSuccessDialog();
				} else {
					showFailDialog();
				}

			}

			@Override
			protected String doExecute() throws ZYException, BizFailure {
				// TODO Auto-generated method stub
				String withdrawAmount = et_withdraw_amount.getText().toString().trim();
				String mobileCode = et_withdraw_verify.getText().toString().trim();
				String mobile = mMyAccount.getPhoneNumber().trim();

				return AccountBiz.withdraw(withdrawAmount, mobileCode, mobile);
			}

			@Override
			protected void OnExecuteFailed(String error) {

				if (!StringUtil.isEmpty(error)) {

					AlertUtil.t(ActivityWithdrawDeposit.this, error);
				}
				getwithdrawRecord(true, true);// 失败刷新充值记录
			}
		};

		withdrawTask.setWaitingView(getWaitingView());

		withdrawTask.execute();

	}

	private void showSuccessDialog() {

		View view = LayoutInflater.from(this).inflate(R.layout.dialog_withdraw_deposit_success, null);

		TextView tv_dialog_withdraw_success_Jump = (TextView) view.findViewById(R.id.tv_dialog_withdraw_success_Jump);// 立即投资
		ImageView iv_dialog_withdraw_success_back = (ImageView) view.findViewById(R.id.iv_dialog_withdraw_success_back);// 退出

		final Dialog dialog = new Dialog(this, R.style.My_Dialog);
		dialog.setContentView(view);
		dialog.setCancelable(false);

		iv_dialog_withdraw_success_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		tv_dialog_withdraw_success_Jump.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(ActivityWithdrawDeposit.this, MainActivity.class);
				YixingApp.goAccount = 1;
				startActivity(intent);

			}
		});

		dialog.show();

		dialog.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss(DialogInterface dialog) {
				getwithdrawRecord(true, true);// 成功刷新充值记录
				et_withdraw_amount.setText("");
				et_withdraw_verify.setText("");
			}
		});

	}

	private void showFailDialog() {

		View view = LayoutInflater.from(this).inflate(R.layout.dialog_withdraw_deposit_failure, null);

		TextView tv_dialog_withdraw_fail_jump = (TextView) view.findViewById(R.id.tv_dialog_withdraw_fail_jump);// 重新充值
		ImageView iv_dialog_withdraw_fail_back = (ImageView) view.findViewById(R.id.iv_dialog_withdraw_fail_back);// 退出

		final Dialog dialog = new Dialog(this, R.style.My_Dialog);
		dialog.setContentView(view);
		dialog.setCancelable(false);

		iv_dialog_withdraw_fail_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		tv_dialog_withdraw_fail_jump.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();// 继续提现
			}
		});

		dialog.show();

		dialog.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss(DialogInterface dialog) {
				getwithdrawRecord(true, true);// 成功刷新充值记录
			}
		});

	}

	private Handler mHandler = new Handler() {

		@Override
		public void dispatchMessage(Message msg) {
			// TODO Auto-generated method stub
			if (count >= 0) {
				btn_withdraw_verify.setText(count + "s");
				btn_withdraw_verify.setClickable(false);
				count--;
			} else {
				resetTimer();
			}
		}
	};

	private void resetTimer() {
		btn_withdraw_verify.setText(R.string.find_getverifycode);
		btn_withdraw_verify.setClickable(true);
		count = 120;
		if (timerTask != null) {
			timerTask.cancel();
		}
		if (timer != null) {
			timer.cancel();
		}
		timerTask = null;
		timer = null;
	}

	private void runTimerTask() {
		timer = new Timer();
		timerTask = new TimerTask() {

			@Override
			public void run() {
				mHandler.sendEmptyMessage(0);
			}
		};
		timer.schedule(timerTask, 1000, 1000);

	}

	class withdrawListAdapter extends BaseAdapter {

		private List<WithdrawModel.RETMODELLISTBean> list = new ArrayList<WithdrawModel.RETMODELLISTBean>();
		private Context context;

		public withdrawListAdapter(Context context) {

			this.context = context;
		}

		public void addItem(WithdrawModel.RETMODELLISTBean cellOptions) {
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
		public WithdrawModel.RETMODELLISTBean getItem(int position) {
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
			withdrawViewHolder viewHolder;

			if (convertView == null) {

				convertView = LayoutInflater.from(context).inflate(R.layout.item_listview_withdraw_deposit_record,
						parent, false);
				viewHolder = new withdrawViewHolder(convertView);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (withdrawViewHolder) convertView.getTag();
			}

			// list设置数据

			viewHolder.tv_item_withdraw_balance.setText(list.get(position).getACTUAL_AMOUNT());
			viewHolder.tv_item_withdraw_data.setText(list.get(position).getWITHDRAW_DATE());
			viewHolder.tv_item_withdraw_title.setText(list.get(position).getWITHDRAW_STATUS());
			viewHolder.tv_item_withdraw_deal_amount.setText(list.get(position).getWITHDRAW_AMOUNT());

			return convertView;
		}

		class withdrawViewHolder {

			TextView tv_item_withdraw_balance;// 到账余额
			TextView tv_item_withdraw_data;// 日期
			TextView tv_item_withdraw_deal_amount;// 提现金额
			TextView tv_item_withdraw_title;// 提现结果

			public withdrawViewHolder(View itemView) {

				tv_item_withdraw_balance = (TextView) itemView.findViewById(R.id.tv_item_withdraw_balance);
				tv_item_withdraw_data = (TextView) itemView.findViewById(R.id.tv_item_withdraw_data);
				tv_item_withdraw_deal_amount = (TextView) itemView.findViewById(R.id.tv_item_withdraw_deal_amount);
				tv_item_withdraw_title = (TextView) itemView.findViewById(R.id.tv_item_withdraw_title);

			}

		}

	}

	private void AlertCheck() {
		Dialog alertDialog = new AlertDialog.Builder(this).
		// 设置标题
				setTitle("提示").
				// 设置内容
				setMessage("您确定要提现吗?").
				// 设置按钮事件
				setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						dowithdraw();

					}
				}).setNegativeButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						arg0.cancel();
					}
				}).
				// 创建
				create();
		// 显示
		alertDialog.show();
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btn_withdraw_confirm:
			// 确认提现

			invokeCharge();
			break;
		case R.id.btn_withdraw_verify:
			// 获取验证码
			getVertifyCodeForNewPhoneNum();
			break;

		case R.id.iv_back:
			finish();
			break;

		default:
			break;
		}
	}

}
