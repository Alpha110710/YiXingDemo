package com.yixing.ui.account;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yixing.ExtraConfig;
import com.yixing.R;
import com.yixing.YixingApp;
import com.yixing.biz.AccountBiz;
import com.yixing.biz.exception.BizFailure;
import com.yixing.biz.exception.ZYException;
import com.yixing.biz.task.BizDataAsyncTask;
import com.yixing.dialog.UserCheckDialog;
import com.yixing.model.BannerItemModel;
import com.yixing.model.GetUserAccountOfBankModel;
import com.yixing.model.MyAccountModel;
import com.yixing.storage.PreferenceCache;
import com.yixing.ui.base.BaseFragment;
import com.yixing.ui.investment.InvestmentDetailActivity;
import com.yixing.ui.widget.CircleImageView;
import com.yixing.ui.widget.PromptOkCancel;
import com.yixing.utils.java.AlertUtil;
import com.yixing.utils.java.StringUtil;
import com.yixing.utils.java.Util;

public class FragmentAccount extends BaseFragment implements OnClickListener {

	private ImageView ivAccountMessage;// 消息图标
	private CircleImageView iv_account_header_icon;// 头像
	private TextView tv_account_back;// 退出
	private TextView tv_account_bank_card_opened;// 显示银行账户已开通
	private TextView tv_account_balance;// 账户余额
	private TextView tv_account_freezing_amount;// 冻结账户金额
	private TextView tv_account_receive_amount;// 待收金额
	private TextView tv_account_withdrawals;// 提现
	private TextView tv_account_recharge;// 充值
	private TextView tv_account_bank_sync;// 与银行数据同步
	private TextView tv_account_phone_num;// 手机号码

	private MyAccountModel mMyAccount;
	private PullToRefreshScrollView pullToRefreshScrollView;

	DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().showImageForEmptyUri(R.drawable.my_account5)
			.showImageOnFail(R.drawable.my_account5).cacheInMemory(true).cacheOnDisc(true).build();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_my_account, container, false);
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		ivAccountMessage = (ImageView) view.findViewById(R.id.iv_account_message);
		tv_account_back = (TextView) view.findViewById(R.id.tv_account_back);
		tv_account_bank_card_opened = (TextView) view.findViewById(R.id.tv_account_bank_card_opened);
		tv_account_balance = (TextView) view.findViewById(R.id.tv_account_balance);
		iv_account_header_icon = (CircleImageView) view.findViewById(R.id.iv_account_header_icon);
		tv_account_freezing_amount = (TextView) view.findViewById(R.id.tv_account_freezing_amount);
		tv_account_receive_amount = (TextView) view.findViewById(R.id.tv_account_receive_amount);
		tv_account_withdrawals = (TextView) view.findViewById(R.id.tv_account_withdrawals);
		tv_account_recharge = (TextView) view.findViewById(R.id.tv_account_recharge);
		tv_account_bank_sync = (TextView) view.findViewById(R.id.tv_account_bank_sync);
		tv_account_phone_num = (TextView) view.findViewById(R.id.tv_account_phone_num);

		pullToRefreshScrollView = (PullToRefreshScrollView) view.findViewById(R.id.sv_account);

		tv_account_back.setOnClickListener(this);
		iv_account_header_icon.setOnClickListener(this);
		tv_account_withdrawals.setOnClickListener(this);
		tv_account_recharge.setOnClickListener(this);
		tv_account_bank_sync.setOnClickListener(this);
		ivAccountMessage.setOnClickListener(this);

		view.findViewById(R.id.ll_account_my_investment).setOnClickListener(this);
		view.findViewById(R.id.ll_account_my_return_plan).setOnClickListener(this);
		view.findViewById(R.id.ll_account_my_transfer).setOnClickListener(this);
		view.findViewById(R.id.ll_account_my_profit_loss).setOnClickListener(this);
		view.findViewById(R.id.ll_account_transaction_record).setOnClickListener(this);
		view.findViewById(R.id.ll_account_bank).setOnClickListener(this);
		view.findViewById(R.id.ll_account_security_center).setOnClickListener(this);
		view.findViewById(R.id.ll_account_red_packet).setOnClickListener(this);
		view.findViewById(R.id.ll_account_add_rate_coupon).setOnClickListener(this);
		view.findViewById(R.id.ll_account_invitation).setOnClickListener(this);

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (YixingApp.globalIndex == 3) {
			init();
		}
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		// TODO Auto-generated method stub
		super.onHiddenChanged(hidden);
		if (hidden) {
			onPause();
		} else {
			onResume();
		}
	}

	private void init() {

		getAccountPageData(true);

		pullToRefreshScrollView.setMode(Mode.PULL_FROM_START);

		// 上拉监听函数
		pullToRefreshScrollView.setOnRefreshListener(new OnRefreshListener<ScrollView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {

				if (PullToRefreshBase.Mode.PULL_FROM_START == refreshView.getCurrentMode()) {
					getAccountPageData(false);
				}

			}
		});

	}

	BizDataAsyncTask<MyAccountModel> getMyAccountTask;

	/**
	 * @param isFirst
	 *            判断是否是第一次登录
	 */
	private void getAccountPageData(boolean isFirst) {
		getMyAccountTask = new BizDataAsyncTask<MyAccountModel>() {

			@Override
			protected void onExecuteSucceeded(MyAccountModel result) {

				mMyAccount = result;

				// 设置数据

				// 判断银行账户开通 0外卡 1浙商 ""未开通
				if (result.getBankOpenFlg().trim().equals("0")) {

					tv_account_bank_card_opened.setVisibility(View.VISIBLE);
					tv_account_recharge.setVisibility(View.VISIBLE);
					tv_account_withdrawals.setVisibility(View.VISIBLE);
					tv_account_bank_sync.setVisibility(View.VISIBLE);
					tv_account_bank_card_opened.setText("存管e户  " + result.getCARD_NO2());
					tv_account_bank_card_opened.setCompoundDrawables(null, null, null, null);
				} else if (result.getBankOpenFlg().trim().equals("1")) {

					tv_account_bank_card_opened.setVisibility(View.VISIBLE);
					tv_account_recharge.setVisibility(View.GONE);
					tv_account_withdrawals.setVisibility(View.GONE);
					tv_account_bank_sync.setVisibility(View.VISIBLE);
					tv_account_bank_card_opened.setText("存管账户 " + result.getCARD_NO2());
					Drawable drawable = getActivity().getResources().getDrawable(R.drawable.my_account3);
					drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
					tv_account_bank_card_opened.setCompoundDrawables(drawable, null, null, null);

				} else {

					tv_account_bank_card_opened.setVisibility(View.INVISIBLE);
					tv_account_recharge.setVisibility(View.GONE);
					tv_account_withdrawals.setVisibility(View.GONE);
					tv_account_bank_sync.setVisibility(View.INVISIBLE);
					// 判断是否开通银行存管账户 登陆后未开通 弹出提示框
					if (PreferenceCache.getBankOpenFlag()) {
						UserCheckDialog dialog = new UserCheckDialog(getActivity(), R.style.My_Dialog, true);
						dialog.show();
						PreferenceCache.putBankOpenFlag(false);
					}
				}

				if (result.isMessageFlg()) {
					ivAccountMessage.setImageResource(R.drawable.my_account2);// 有消息
				} else {
					ivAccountMessage.setImageResource(R.drawable.my_account1);// 无消息
				}

				//设置手机号码
				if (!StringUtil.isEmpty(result.getPhoneNumber())) {
					tv_account_phone_num.setText(result.getPhoneNumber().substring(0, 3) + "******"
							+ result.getPhoneNumber().substring(result.getPhoneNumber().length() - 3));
				}
				tv_account_balance.setText(result.getBalance());// 显示余额
				tv_account_freezing_amount.setText(result.getFrozeAmount());// 冻结金额
				tv_account_receive_amount.setText(result.getAwait());// 待收金额

				ImageLoader.getInstance().displayImage(mMyAccount.getHeadPic(), iv_account_header_icon, defaultOptions);

				// 数据end
				pullToRefreshScrollView.onRefreshComplete();

			}

			@Override
			protected MyAccountModel doExecute() throws ZYException, BizFailure {
				return AccountBiz.getMyAccountPage();
			}

			@Override
			protected void OnExecuteFailed(String error) {
				// TODO Auto-generated method stub
				pullToRefreshScrollView.onRefreshComplete();
			}
		};

		if (isFirst) {
			getMyAccountTask.setWaitingView(getWaitingView());
		}

		getMyAccountTask.execute();
	}

	/**
	 * 与银行同步任务
	 */
	private BizDataAsyncTask<GetUserAccountOfBankModel> getUserAccountOfBankTask;

	private void getUserAccountOfBank() {
		getUserAccountOfBankTask = new BizDataAsyncTask<GetUserAccountOfBankModel>() {

			@Override
			protected void onExecuteSucceeded(GetUserAccountOfBankModel result) {
				getAccountPageData(true);
				if (result.getREMOVE_UR().equals("2")) {
					AlertUtil.t(getActivity(), "与银行账户同步成功");
				}
			}

			@Override
			protected GetUserAccountOfBankModel doExecute() throws ZYException, BizFailure {

				return AccountBiz.getUserAccountOfBank();
			}

			@Override
			protected void OnExecuteFailed(String error) {
				if (!StringUtil.isEmpty(error)) {
					AlertUtil.t(getActivity(), error);
				}
			}
		};

		getUserAccountOfBankTask.execute();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_account_message:
			// 消息查看
			Intent itMsg = new Intent(getActivity(), ActivityMessageCenter.class);
			startActivity(itMsg);

			break;
		case R.id.tv_account_bank_sync:
			// 与银行账户同步
			getUserAccountOfBank();
			break;

		case R.id.tv_account_withdrawals:

			// 提现
			Intent itOne = new Intent();
			itOne.setClass(FragmentAccount.this.getActivity(), ActivityWithdrawDeposit.class);
			itOne.putExtra(ExtraConfig.IntentExtraKey.MY_ACCOUNT, mMyAccount);
			startActivity(itOne);

			break;

		// 充值
		case R.id.tv_account_recharge:

			// showDialog();
			if (mMyAccount == null) {
				return;
			}
			Intent itRecharge = new Intent();
			itRecharge.setClass(FragmentAccount.this.getActivity(), ActivityRecharge.class);
			itRecharge.putExtra(ExtraConfig.IntentExtraKey.MY_ACCOUNT, mMyAccount);
			startActivity(itRecharge);

			break;

		// 我的投资
		case R.id.ll_account_my_investment:

			if (mMyAccount == null) {
				return;
			}
			Intent itMyInvest = new Intent();
			itMyInvest.setClass(FragmentAccount.this.getActivity(), ActivityMyInvest.class);
			startActivity(itMyInvest);

			break;
		// 回款计划
		case R.id.ll_account_my_return_plan:

			if (mMyAccount == null) {
				return;
			}
			Intent intent = new Intent();
			intent.setClass(FragmentAccount.this.getActivity(), ActivityReturnMoneyPlan.class);
			startActivity(intent);

			break;

		// 我的转让
		case R.id.ll_account_my_transfer:
			if (mMyAccount == null) {
				return;
			}
			Intent itMyTransfer = new Intent();
			itMyTransfer.setClass(FragmentAccount.this.getActivity(), ActivityMyTransfer.class);
			startActivity(itMyTransfer);
			break;
		// 个人损益
		case R.id.ll_account_my_profit_loss:
			if (mMyAccount == null) {
				return;
			}
			Intent itProfit = new Intent();
			itProfit.setClass(FragmentAccount.this.getActivity(), ActivityPersonalProfitLoss.class);
			startActivity(itProfit);
			break;
		// 交易记录
		case R.id.ll_account_transaction_record:
			if (mMyAccount == null) {
				return;
			}
			Intent itRecord = new Intent();
			itRecord.setClass(FragmentAccount.this.getActivity(), ActivityTradingRecord.class);
			startActivity(itRecord);
			break;
		// 银行卡
		case R.id.ll_account_bank:
			Intent itBankCard = new Intent();
			if (mMyAccount == null) {
				return;
			}

			if (!"1".equals(mMyAccount.getVERIFY_FLG())) {
				AlertUtil.t(getActivity(), "请先到PC端进行实名认证");
				return;
			}

			if ("".equals(mMyAccount.getBankOpenFlg().trim())) {
				AlertUtil.t(getActivity(), "请先到PC端绑定银行卡");
				return;
			}

			itBankCard.setClass(FragmentAccount.this.getActivity(), ActivityBankCard.class);
			itBankCard.putExtra(ExtraConfig.IntentExtraKey.MY_ACCOUNT, mMyAccount);

			startActivity(itBankCard);

			break;

		// 安全中心
		case R.id.ll_account_security_center:

			if (mMyAccount == null) {
				return;
			}
			Intent itSecurityCenter = new Intent();
			// ((MainActivity)
			// FragmentAccount.this.getActivity()).canMyAccountOnResume = true;
			itSecurityCenter.setClass(FragmentAccount.this.getActivity(), ActivitySecurityCenter.class);
			startActivity(itSecurityCenter);
			break;
		// 我的红包
		case R.id.ll_account_red_packet:
			Intent itRedPacket = new Intent();
			itRedPacket.setClass(FragmentAccount.this.getActivity(), ActivityMyRedPackage.class);
			itRedPacket.putExtra(ExtraConfig.IntentExtraKey.MY_ACCOUNT, mMyAccount);
			startActivity(itRedPacket);
			break;

		// 我的加息券
		case R.id.ll_account_add_rate_coupon:
			Intent itAddCoupon = new Intent();
			itAddCoupon.setClass(FragmentAccount.this.getActivity(), ActivityMyAddRateCoupon.class);
			itAddCoupon.putExtra(ExtraConfig.IntentExtraKey.MY_ACCOUNT, mMyAccount);
			startActivity(itAddCoupon);

			break;
		// 我的邀请
		case R.id.ll_account_invitation:
			Intent itInvitation = new Intent();
			itInvitation.setClass(FragmentAccount.this.getActivity(), ActivityInviteFriend.class);
			startActivity(itInvitation);
			break;

		// 头像
		case R.id.iv_account_header_icon:
			if (mMyAccount == null) {
				return;
			}
			Intent itHeader = new Intent();
			itHeader.setClass(FragmentAccount.this.getActivity(), ActivityPersonalBasicInfo.class);
			startActivity(itHeader);
			break;

		// 退出登录
		case R.id.tv_account_back:

			PromptOkCancel dialog = new PromptOkCancel(getActivity()) {

				@Override
				protected void onOk() {
					// TODO Auto-generated method stub
					PreferenceCache.putToken("");
					// PreferenceCache.putIfSkipLogin(false);
					// startActivity(new Intent(BaseHeaderBarActivity.this,
					// ActivityLogin.class));
					Util.showLogin(getActivity());
				}
			};
			dialog.show("确认", "是否退出账户?");

			break;
		default:

			break;
		}

	}

}
