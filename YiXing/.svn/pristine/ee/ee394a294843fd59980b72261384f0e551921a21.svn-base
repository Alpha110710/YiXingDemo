package com.yixing.ui.investment;

import org.w3c.dom.Text;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yixing.ExtraConfig;
import com.yixing.R;
import com.yixing.biz.AccountBiz;
import com.yixing.biz.InvestmentBiz;
import com.yixing.biz.exception.BizFailure;
import com.yixing.biz.exception.ZYException;
import com.yixing.biz.task.BizDataAsyncTask;
import com.yixing.dialog.PromptlyInvestmentDialog;
import com.yixing.model.AlllMoneyModel;
import com.yixing.model.InvestmentDetaiTwolModel;
import com.yixing.model.InvestmentZQModel;
import com.yixing.model.MyAccountModel;
import com.yixing.model.ZQIncomeModel;
import com.yixing.ui.account.ActivityFindDealPwd;
import com.yixing.ui.account.ActivityRecharge;
import com.yixing.ui.base.BaseActivity;
import com.yixing.utils.java.AlertUtil;
import com.yixing.utils.java.StringUtil;

public class ZQBUYActivity extends BaseActivity implements OnClickListener {
	/**
	 * 账户余额
	 */
	private TextView user_Money;
	/**
	 * 折让金额
	 */
	private TextView buy_Money;
	/**
	 * 实际应付金额
	 */

	private LinearLayout layout;
	private TextView Money;
	private TextView get_sms_code;// 获取验证码按钮
	private TextView forget_password;// 忘记交易密码按钮
	private TextView my_income;// 预计收益
	private EditText edit_password;// 交易密码
	private LinearLayout layoutdxpassword;// 定向密码显示隐藏
	private EditText edit_dxpassword;// 定向密码
	private EditText edit_money;// 输入的投资金额
	private EditText edit_sms_code;// 短信验证码
	private TextView pay;// 充值
	private TextView all_Investment;// 全投
	private Button submit;// 立即投资
	int i = 120;// 设置重新获取验证码的时间为60秒
	private String transferId;// 债权id
	private InvestmentZQModel model;
	private String mEditValue = "";

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				i--;
				if (i == 0) {
					get_sms_code.setClickable(true);
					get_sms_code.setText("获取短信验证码");
					i = 60;
				} else {
					get_sms_code.setText("重新获取" + i);
					handler.sendEmptyMessageDelayed(1, 1000);
				}
				break;

			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zq_detail_buy);
		setTitle("购买债权");
		initView();
		transferId = getIntent().getStringExtra("transferId");
	}

	@Override
	protected void onResume() {
		super.onResume();
		getZQDetail(true, transferId);
	}

	private void initView() {
		layoutdxpassword = (LinearLayout) findViewById(R.id.dxpassword_layout);
		user_Money = (TextView) findViewById(R.id.use_money);
		buy_Money = (TextView) findViewById(R.id.buy_money);
		edit_money = (EditText) findViewById(R.id.edit_money);
		// 判断edittext得到焦点还是失去焦点状态
		edit_money.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (!hasFocus) {// 失去焦点
					// 获取收益（接口）
					if (!mEditValue.equals((edit_money.getEditableText()
							.toString()))) {

						mEditValue = edit_money.getEditableText().toString();
						if (StringUtil.isEmpty(edit_money.getText().toString()
								.trim())) {
							my_income.setText("--元");
							buy_Money.setText("折让金额--元");
							Money.setText("实际应付--元");
						} else {
							getInComeData(transferId, edit_money.getText()
									.toString().trim());
						}
					}
				}
			}
		});
		layout = (LinearLayout) findViewById(R.id.layout);
		layout.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				layout.setFocusable(true);
				layout.setFocusableInTouchMode(true);
				layout.requestFocus();

				return false;
			}
		});

		Money = (TextView) findViewById(R.id.money);
		get_sms_code = (TextView) findViewById(R.id.zq_forget_sms_password);
		forget_password = (TextView) findViewById(R.id.zq_forget_trade_password);
		edit_dxpassword = (EditText) findViewById(R.id.dx_password);
		my_income = (TextView) findViewById(R.id.income);
		edit_password = (EditText) findViewById(R.id.edit_password);
		edit_sms_code = (EditText) findViewById(R.id.edit_sms_code);
		pay = (TextView) findViewById(R.id.pay_money);
		all_Investment = (TextView) findViewById(R.id.investment_allmoney);
		submit = (Button) findViewById(R.id.submit);
		submit.setOnClickListener(this);
		pay.setOnClickListener(this);
		all_Investment.setOnClickListener(this);
		forget_password.setOnClickListener(this);
		get_sms_code.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.submit:// 立即购买
			getSubmitCheck();
			break;
		case R.id.pay_money:// 充值
			if (model != null) {
				MyAccountModel accountModel = new MyAccountModel();
				accountModel.setBalance(model.getMoney());
				accountModel.setPhoneNumber(model.getPhone());
				Intent intentAccount = new Intent(ZQBUYActivity.this,
						ActivityRecharge.class);
				intentAccount.putExtra(ExtraConfig.IntentExtraKey.MY_ACCOUNT,
						accountModel);
				startActivity(intentAccount);
			}
			// AlertUtil.t(ZQBUYActivity.this, "充值");
			break;
		case R.id.investment_allmoney:// 全投
			getAllMoney(transferId);
			break;
		case R.id.zq_forget_sms_password:// 获取验证码
			if (model != null)
				getVertifyCodeForModifyPhone();
			break;
		case R.id.zq_forget_trade_password:// 忘记交易密码
			if (model != null) {
				Intent intent = new Intent(ZQBUYActivity.this,
						ActivityFindDealPwd.class);
				intent.putExtra(ExtraConfig.IntentExtraKey.ACCOUNT_TELE_NUM,
						model.getPhone());
				startActivity(intent);
			}
			// AlertUtil.t(ZQBUYActivity.this, "忘记交易密码");
			break;

		default:
			break;
		}
	}

	/***
	 * 判断输入框的输入是否有为null
	 */
	private void getSubmitCheck() {
		String code = edit_sms_code.getText().toString().trim();
		// check
		if (!"".equals(edit_money.getText().toString().trim())
				&& !"".equals(edit_password.getText().toString().trim())
				&& !"".equals(code)) {
			promptlyInvestment(edit_money.getText().toString().trim(),
					edit_password.getText().toString().trim(), transferId,
					edit_sms_code.getText().toString());

		} else {
			AlertUtil.t(ZQBUYActivity.this, "请填写完整信息后投资");
		}
	}

	private BizDataAsyncTask<InvestmentZQModel> getInvestmentZQDetail;

	/**
	 * 获取数据
	 */
	private void getZQDetail(final boolean isFirst, final String transferId) {
		getInvestmentZQDetail = new BizDataAsyncTask<InvestmentZQModel>() {

			@Override
			protected void onExecuteSucceeded(InvestmentZQModel result) {
				model = result;
				// 给控件赋值
				user_Money.setText(model.getMoney() + "元");
				/*
				 * if ("1".equals(model.getPassword()))
				 * layoutdxpassword.setVisibility(View.VISIBLE);
				 */
				if ("0".equals(model.getExtension())) {
					pay.setVisibility(View.VISIBLE);
				}
			}

			@Override
			protected InvestmentZQModel doExecute() throws ZYException,
					BizFailure {
				return InvestmentBiz.getZQData(transferId);// 传递债权的id
			}

			@Override
			protected void OnExecuteFailed(String error) {
			}
		};
		if (isFirst) {
			getInvestmentZQDetail.setWaitingView(getWaitingView());
		}
		getInvestmentZQDetail.execute();
	}

	/**
	 * 获取短信验证码接口
	 */
	private BizDataAsyncTask<String> getVertifyCodeForModifyPhone;

	private void getVertifyCodeForModifyPhone() {
		getVertifyCodeForModifyPhone = new BizDataAsyncTask<String>() {

			@Override
			protected void onExecuteSucceeded(String result) {
				AlertUtil.t(ZQBUYActivity.this, "短信验证码发送成功，请注意查收");
				handler.sendEmptyMessage(1);
				get_sms_code.setClickable(false);
			}

			@Override
			protected String doExecute() throws ZYException, BizFailure {
				return InvestmentBiz.getVertifyCodeForModifyPhone(model
						.getPhone());// 传递电话号码
			}

			@Override
			protected void OnExecuteFailed(String error) {

			}
		};

		getVertifyCodeForModifyPhone.execute();
	}

	/**
	 * 立即投资
	 */
	private BizDataAsyncTask<String> promptlyInvestment;

	private void promptlyInvestment(final String money, final String password,
			final String productId, final String verifyCode) {
		promptlyInvestment = new BizDataAsyncTask<String>() {

			@Override
			protected void onExecuteSucceeded(String result) {
				// 投资成功
				PromptlyInvestmentDialog dialog = new PromptlyInvestmentDialog(
						ZQBUYActivity.this, R.style.My_Dialog, true);
				dialog.show();
			}

			@Override
			protected String doExecute() throws ZYException, BizFailure {
				return InvestmentBiz.investmentZQ(money, password, productId,
						verifyCode);
			}

			@Override
			protected void OnExecuteFailed(String error) {
				// 投资失败
				if (error.equals("投资失败！")) {
					PromptlyInvestmentDialog dialog = new PromptlyInvestmentDialog(
							ZQBUYActivity.this, R.style.My_Dialog, false);
					dialog.show();
				} else {
					AlertUtil.t(ZQBUYActivity.this, error);
				}
			}
		};

		promptlyInvestment.execute();
	}

	/**
	 * 获取收益接口
	 */
	private BizDataAsyncTask<ZQIncomeModel> getInCome;

	private void getInComeData(final String transferId, final String money) {
		getInCome = new BizDataAsyncTask<ZQIncomeModel>() {

			@Override
			protected void onExecuteSucceeded(ZQIncomeModel result) {
				my_income.setText(result.getINTEREST_TOTAL() + "元");
				buy_Money.setText("折让金额" + result.getDISCOUNT() + "元");
				Money.setText("实际应付" + result.getACTUAL_ACCOUNT() + "元");
			}

			@Override
			protected ZQIncomeModel doExecute() throws ZYException, BizFailure {
				return InvestmentBiz.getZQInCome(transferId, money);
			}

			@Override
			protected void OnExecuteFailed(String error) {
				AlertUtil.t(ZQBUYActivity.this, error);

			}
		};
		// if (isFirst) {
		// getInCome.setWaitingView(getWaitingView());
		// }

		getInCome.execute();
	}

	/***
	 * 全投资接口
	 */
	private BizDataAsyncTask<ZQIncomeModel> getAllMoney;

	private void getAllMoney(final String transferId) {
		getAllMoney = new BizDataAsyncTask<ZQIncomeModel>(getWaitingView()) {

			@Override
			protected void onExecuteSucceeded(ZQIncomeModel result) {
				edit_money.setText(result.getALL_AMOUNT());
				mEditValue = result.getALL_AMOUNT();
				my_income.setText(result.getINTEREST_TOTAL() + "元");
				buy_Money.setText("折让金额" + result.getDISCOUNT() + "元");
				Money.setText("实际应付" + result.getACTUAL_ACCOUNT() + "元");
			}

			@Override
			protected ZQIncomeModel doExecute() throws ZYException, BizFailure {
				return InvestmentBiz.getZQAllMoney(transferId);
			}

			@Override
			protected void OnExecuteFailed(String error) {

			}
		};

		getAllMoney.execute();
	}

}
