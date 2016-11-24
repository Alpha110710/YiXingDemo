package com.yixing.ui.init;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yixing.ExtraConfig.IntentExtraKey;
import com.yixing.R;
import com.yixing.YixingApp;
import com.yixing.biz.HomeBiz;
import com.yixing.biz.exception.BizFailure;
import com.yixing.biz.exception.ZYException;
import com.yixing.biz.task.BizDataAsyncTask;
import com.yixing.dialog.UserCheckDialog;
import com.yixing.model.City;
import com.yixing.model.County;
import com.yixing.model.LoginModel;
import com.yixing.model.Province;
import com.yixing.model.RedomVerifyCode;
import com.yixing.storage.PreferenceCache;
import com.yixing.ui.addressselector.BottomDialog;
import com.yixing.ui.addressselector.OnAddressSelectedListener;
import com.yixing.ui.base.BaseActivity;
import com.yixing.ui.investment.InvestmentDetailActivity;
import com.yixing.utils.java.AlertUtil;
import com.yixing.utils.java.Base64;
import com.yixing.utils.java.StringUtil;
import com.yixing.utils.java.Util;

public class ActivityRegist extends BaseActivity implements OnClickListener , OnAddressSelectedListener {

	private EditText edtPhoneNumR;
	private EditText edtLoginPwdR;
	private LinearLayout llHideShowR ;
	private CheckBox ckHideShowR;
	private EditText edtRandomCodeR;
	private ImageView ivRandomCodeR;
	private ImageView ivGetRandomCodeR;
	private Button btnGetRegistSms;
	private EditText edtVerifyCodeR;
	private TextView tvAddressSelect;
	private EditText edtRecommender;
	private Button btnRegist;
	
	private CheckBox ckUserProtocal;
	private TextView tvUserProtocal;
	private TextView tvToLogin;
	private ImageView ivClose;

	private BottomDialog dialog;
	private boolean check_agree = true;
	private String verifyCodeContent = "";
	private Bitmap bit;
	
	private Province mProvince ;
	private City mCity;
	private County mCounty;
	
	
	private Timer timer;
	private TimerTask timerTask;
	private int count = 120;
	private int toRegist = 0 ;
	
	
	private Handler mHandler = new Handler() {

		@Override
		public void dispatchMessage(Message msg) {
			// TODO Auto-generated method stub
			if (count >= 0) {
				btnGetRegistSms.setText(count + "s");
				btnGetRegistSms.setClickable(false);
				count--;
			} else {
				resetTimer();
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		toRegist = getIntent().getIntExtra("TO_REGIST", 0) ;
		init();

	}

	private void init() {

		edtPhoneNumR = (EditText) findViewById(R.id.edt_phone_num_regist);
		edtLoginPwdR = (EditText) findViewById(R.id.edt_login_pwd_regist);
		llHideShowR = (LinearLayout) findViewById(R.id.ll_hide_show_regist);
		ckHideShowR = (CheckBox) findViewById(R.id.cb_hide_show_regist);
		llHideShowR.setOnClickListener(this);
		edtRandomCodeR = (EditText) findViewById(R.id.edt_verify_code_regist);
		ivRandomCodeR = (ImageView) findViewById(R.id.iv_verify_code_regist);
		ivGetRandomCodeR = (ImageView) findViewById(R.id.iv_get_verify_code_regist);
		ivGetRandomCodeR.setOnClickListener(this);
		btnGetRegistSms = (Button) findViewById(R.id.btn_get_register_sms);
		btnGetRegistSms.setOnClickListener(this);
		edtVerifyCodeR = (EditText) findViewById(R.id.edt_input_verify_code_regist);
		tvAddressSelect = (TextView) findViewById(R.id.tv_address_selector);
		tvAddressSelect.setOnClickListener(this);
		edtRecommender = (EditText) findViewById(R.id.edt_recommender);
		btnRegist = (Button) findViewById(R.id.btn_regist);
		btnRegist.setOnClickListener(this);
		ckUserProtocal = (CheckBox) findViewById(R.id.ck_user_protocal);
		ckUserProtocal.setOnClickListener(this);
		tvUserProtocal = (TextView) findViewById(R.id.tv_user_protocal);
		tvUserProtocal.setOnClickListener(this);
		tvToLogin = (TextView) findViewById(R.id.tv_to_login);
		tvToLogin.setOnClickListener(this);
		ivClose = (ImageView) findViewById(R.id.iv_close);
		ivClose.setOnClickListener(this);
		
		getCaptchaImage();

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		case R.id.iv_get_verify_code_regist:

			getCaptchaImage();
			break;

		case R.id.ll_hide_show_regist:
			
			

			if (ckHideShowR.isChecked()) {

				edtLoginPwdR
						.setTransformationMethod(HideReturnsTransformationMethod
								.getInstance());

				edtLoginPwdR.setSelection(edtLoginPwdR.getEditableText()
						.toString().length());
				//ckHideShowR.setBackgroundResource(R.drawable.login_eye_true);

			} else {

				edtLoginPwdR
						.setTransformationMethod(PasswordTransformationMethod
								.getInstance());

				edtLoginPwdR.setSelection(edtLoginPwdR.getEditableText()
						.toString().length());
				//ckHideShowR.setBackgroundResource(R.drawable.login_eye_false);
			}
			
			ckHideShowR.setChecked(! ckHideShowR.isChecked());
			
			break;

		case R.id.btn_get_register_sms:
			
			if(StringUtil.isEmpty(edtPhoneNumR.getEditableText().toString().trim())){
				
				AlertUtil.t(this, "请输入手机号");
				edtPhoneNumR.requestFocus();
			}else{
				
				getRegMobileCode();
			}
			
			
			break;
		case R.id.btn_regist:

			if (checkRegist()) {
				doRegist();
			}
			break;
	/*	case R.id.ck_user_protocal:

			if (check_agree) {
				Drawable img_off;
				Resources res = getResources();
				img_off = res.getDrawable(R.drawable.login_check_true);
				// 调用setCompoundDrawables时，必须调用Drawable.setBounds()方法,否则图片不显示
				img_off.setBounds(0, 0, img_off.getMinimumWidth(),
						img_off.getMinimumHeight());
				ckUserProtocal.setCompoundDrawables(img_off, null, null, null); // 设置左图标
				ckUserProtocal.setCompoundDrawablePadding(5);
				check_agree = false;
			} else {
				Drawable img_on;
				Resources res = getResources();
				img_on = res.getDrawable(R.drawable.login_check_false);
				// 调用setCompoundDrawables时，必须调用Drawable.setBounds()方法,否则图片不显示
				img_on.setBounds(0, 0, img_on.getMinimumWidth(),
						img_on.getMinimumHeight());
				ckUserProtocal.setCompoundDrawables(img_on, null, null, null); // 设置左图标
				ckUserProtocal.setCompoundDrawablePadding(5);

				check_agree = true;
			}

			break;*/
		case R.id.tv_user_protocal:

			Intent itUserPro = new Intent(this, ActivityWebView.class);

			itUserPro.putExtra(IntentExtraKey.WEB_VIEW_FROM, 1);

			startActivity(itUserPro);

			break;
		case R.id.tv_to_login:

			if(toRegist ==1){
				finish();
			}else{
			Intent itToLogin = new Intent(this, ActivityLogin.class) ;
			itToLogin.putExtra("TO_LOGIN", 1) ;
			startActivity(itToLogin);
			}
		case R.id.iv_close:
			finish();
			break;
			
		case R.id.tv_address_selector:
			
			 dialog = new BottomDialog(ActivityRegist.this);
             dialog.setOnAddressSelectedListener(ActivityRegist.this);
             dialog.show();
			break ;
		}

	}
	
	
	private void resetTimer() {
		btnGetRegistSms.setText(R.string.find_getverifycode);
		btnGetRegistSms.setClickable(true);
		count = 60;
		timerTask.cancel();
		timer.cancel();
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


	private boolean checkRegist() {

		if (StringUtil
				.isEmpty(edtPhoneNumR.getEditableText().toString().trim())) {
			AlertUtil.t(this, "请输入手机号");
			edtPhoneNumR.requestFocus();
			return false;
		}
		if (StringUtil
				.isEmpty(edtLoginPwdR.getEditableText().toString().trim())) {
			AlertUtil.t(this, "请输入登录密码");
			edtLoginPwdR.requestFocus();
			return false;
		}

		if (edtLoginPwdR.length() < 6 || edtLoginPwdR.length() > 20) { // 密码长度检查
			AlertUtil.t(this, R.string.msg_password_length_check);
			edtLoginPwdR.requestFocus();
			return false;
		}
		if (!Util.checkPwd(edtLoginPwdR.getEditableText().toString())) {
			AlertUtil.t(this, R.string.msg_pwd_pattern_check);
			edtLoginPwdR.requestFocus();
			return false;
		}

		if (StringUtil.isEmpty(edtRandomCodeR.getEditableText().toString()
				.trim())) {
			AlertUtil.t(this, "请输入校验码");
			edtRandomCodeR.requestFocus();
			return false;
		}
		if (!verifyCodeContent.equals(edtRandomCodeR.getEditableText()
				.toString().trim())) {
			AlertUtil.t(this, "请输入正确的校验码");
			edtRandomCodeR.requestFocus();
			return false;
		}
		if (StringUtil.isEmpty(edtVerifyCodeR.getEditableText().toString()
				.trim())) {
			AlertUtil.t(this, "请输入短信验证码");
			edtVerifyCodeR.requestFocus();
			return false;
		}
		if (StringUtil.isEmpty(tvAddressSelect.getText().toString().trim())) {
			AlertUtil.t(this, "请选择省，市，区");
			//edtVerifyCodeR.requestFocus();
			return false;
		}
		
		
		if(!ckUserProtocal.isChecked()){
			AlertUtil.t(this, "请确认同意用户协议");
			return false ;
		}

		return true;
	}

	

	private BizDataAsyncTask<String> getRegMobileCodeTask;
	
	private void getRegMobileCode(){
		
		getRegMobileCodeTask = new BizDataAsyncTask<String>() {

			@Override
			protected String doExecute() throws ZYException, BizFailure {
				// TODO Auto-generated method stub
				return	HomeBiz.getRegMobileCode(edtPhoneNumR.getEditableText().toString().trim());
			}

			@Override
			protected void onExecuteSucceeded(String result) {
				// TODO Auto-generated method stub
				runTimerTask();
				edtPhoneNumR.setClickable(false);
				edtPhoneNumR.setFocusable(false);
			}

			@Override
			protected void OnExecuteFailed(String error) {
				// TODO Auto-generated method stub
				if (!StringUtil.isEmpty(error)) {
					Toast.makeText(YixingApp.getAppContext(), error,
							Toast.LENGTH_LONG).show();
				}
			}
			
		};
		
		getRegMobileCodeTask.execute();
	}
	
	
	
	private BizDataAsyncTask<RedomVerifyCode> getCaptchaImageTask;

	/**
	 * 申请验证码
	 */
	private void getCaptchaImage() {
		getCaptchaImageTask = new BizDataAsyncTask<RedomVerifyCode>() {

			@Override
			protected void onExecuteSucceeded(RedomVerifyCode result) {

				try {
					verifyCodeContent = result.getCode();
					byte[] srtbyte = Base64.decode(result.getByteContent());
					bit = getBitmapFromByte(srtbyte);
					ivRandomCodeR.setImageBitmap(bit);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			protected RedomVerifyCode doExecute() throws ZYException,
					BizFailure {
				return HomeBiz.getCaptchaImage();
			}

			@Override
			protected void OnExecuteFailed(String error) {
				// TODO Auto-generated method stub

			}

		};

		getCaptchaImageTask.execute();
	}

	public Bitmap getBitmapFromByte(byte[] temp) {
		if (temp != null) {
			Bitmap bitmap = BitmapFactory.decodeByteArray(temp, 0, temp.length);
			return bitmap;
		} else {
			return null;
		}
	}

	BizDataAsyncTask<String> registTask;

	private void doRegist() {
		registTask = new BizDataAsyncTask<String>() {

			@Override
			protected void onExecuteSucceeded(String result) {
				PreferenceCache.putUsername(edtPhoneNumR.getEditableText()
						.toString());

				PreferenceCache.putToken(result); 
				
				UserCheckDialog dialog = new UserCheckDialog(
						ActivityRegist.this, R.style.My_Dialog,false);
				dialog.setCancelable(false);
				dialog.show();
				doLogin();

			}

			@Override
			protected String doExecute() throws ZYException, BizFailure {

				return HomeBiz.register(edtPhoneNumR.getEditableText()
						.toString().trim(), edtLoginPwdR.getEditableText()
						.toString().trim(), edtVerifyCodeR.getEditableText()
						.toString().trim(), edtRecommender.getEditableText()
						.toString().trim(), "2", mProvince.getId(), mCity.getId(), mCounty.getId()); // 终端 "2"安卓
																// "3"ios,当前版本：0
			}

			@Override
			protected void OnExecuteFailed(String error) {
				// TODO Auto-generated method stub
				if (!StringUtil.isEmpty(error)) {
					Toast.makeText(YixingApp.getAppContext(), error,
							Toast.LENGTH_LONG).show();
				}

			}

		};

		registTask.execute();
	}

	BizDataAsyncTask<LoginModel> loginTask;

	private void doLogin() {
		loginTask = new BizDataAsyncTask<LoginModel>(getWaitingView()) {

			@Override
			protected void onExecuteSucceeded(LoginModel result) {
				PreferenceCache.putToken(result.getUserToken()); // 持久化缓存token
				// PreferenceCache.putIfSkipLogin(true);
				PreferenceCache.putUsername(edtPhoneNumR.getEditableText()
						.toString());
			/*	YixingApp.globalIndex = 0;
				Util.gotoMain(ActivityRegist.this);
				finish();*/
			}

			@Override
			protected LoginModel doExecute() throws ZYException, BizFailure {

				return HomeBiz.login(edtPhoneNumR.getEditableText().toString()
						.trim(), edtLoginPwdR.getEditableText().toString()
						.trim(), "2", "1.0.0");
			}

			@Override
			protected void OnExecuteFailed(String error) {
				// TODO Auto-generated method stub
				if (!StringUtil.isEmpty(error)) {
					Toast.makeText(YixingApp.getAppContext(), error,
							Toast.LENGTH_LONG).show();
				}

			}
		};

		loginTask.execute();
	}
	
	
	   public void onAddressSelected(Province province, City city, County county) {
		   
		   mProvince = province ;
		   mCity = city ;
		   mCounty = county;
	        String s =
	                (province == null ? "" : province.getName()) +
	                        (city == null ? "" : city.getName()) +
	                        (county == null ? "" : county.getName());

	        tvAddressSelect.setText(s);
	      //  Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
	    }

	    @Override
	    protected void onDestroy() {
	        super.onDestroy();
	        if (dialog != null && dialog.isShowing()) {
	            dialog.dismiss();
	        }
	    }

}