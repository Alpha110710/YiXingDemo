package com.yixing.ui.loan;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.yixing.MainActivity;
import com.yixing.R;
import com.yixing.YixingApp;
import com.yixing.biz.HomeBiz;
import com.yixing.biz.exception.BizFailure;
import com.yixing.biz.exception.ZYException;
import com.yixing.biz.task.BizDataAsyncTask;
import com.yixing.dialog.LoanSuccessDialog;
import com.yixing.model.City;
import com.yixing.model.LoginModel;
import com.yixing.model.Province;
import com.yixing.model.RedomVerifyCode;
import com.yixing.ui.account.ActivityRecharge;
import com.yixing.ui.base.BaseFragment;
import com.yixing.utils.java.AlertUtil;
import com.yixing.utils.java.Base64;
import com.yixing.utils.java.StringUtil;

public class FragmentLoan extends BaseFragment implements OnClickListener {

	private TextView tvLoanProcess;
	private EditText edtExceptLoanAmount;
	private EditText edtExceptLoanRate;
	private EditText edtExceptLoanReason;
	private EditText edtExceptLoanPurpose;
	private TextView tvSelectPro;
	private TextView tvSelectCity;
	private EditText edtRecommender;
	private EditText edtVerifyCode;
	private ImageView ivGetVerifyCode;
	private ImageView iv_get_verify_code_loan_refresh;
	private Button btnSubmitLoan;

	private String verifyCodeContent = "";
	private Bitmap bit;

	private PopupWindow popupWindow;
	private ArrayAdapter<String> adapter;

	private String provinceId = "1", cityId = "37";

	private GoHome goHome;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment_loan, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		init();
	}

	private void init() {

		tvLoanProcess = (TextView) findViewById(R.id.tv_loan_process);
		tvLoanProcess.setOnClickListener(this);
		edtExceptLoanAmount = (EditText) findViewById(R.id.edt_except_loan_amount);
		edtExceptLoanRate = (EditText) findViewById(R.id.edt_except_loan_rate);
		edtExceptLoanReason = (EditText) findViewById(R.id.edt_except_loan_reason);
		edtExceptLoanPurpose = (EditText) findViewById(R.id.edt_except_loan_purpose);
		tvSelectPro = (TextView) findViewById(R.id.tv_select_province);
		tvSelectCity = (TextView) findViewById(R.id.tv_select_city);
		edtRecommender = (EditText) findViewById(R.id.edt_recommender);
		edtVerifyCode = (EditText) findViewById(R.id.edt_verify_code_loan);
		ivGetVerifyCode = (ImageView) findViewById(R.id.iv_get_verify_code_loan);
		iv_get_verify_code_loan_refresh = (ImageView) findViewById(R.id.iv_get_verify_code_loan_refresh);
		tvSelectPro.setText("北京市");
		tvSelectCity.setText("东城区");

		// ivGetVerifyCode.setOnClickListener(this);
		btnSubmitLoan = (Button) findViewById(R.id.btn_submit_loan);
		btnSubmitLoan.setOnClickListener(this);
		tvSelectCity.setOnClickListener(this);
		tvSelectPro.setOnClickListener(this);
		iv_get_verify_code_loan_refresh.setOnClickListener(this);

		getCaptchaImage();// 显示验证码
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (YixingApp.globalIndex == 2) {
			setNull();
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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		case R.id.tv_loan_process:

			Intent itLoanProcess = new Intent(getActivity(), ActivityLoanProcess.class);
			startActivity(itLoanProcess);

			break;

		case R.id.iv_get_verify_code_loan_refresh:
			// 刷新验证码
			getCaptchaImage();
			break;

		case R.id.btn_submit_loan:
			// 提交借贷
			if (submitCheck()) {
				submitLoan();
			}

			break;

		case R.id.tv_select_city:
			retrieveCitiesWith(provinceId);
			break;

		case R.id.tv_select_province:
			retrieveProvinces();
			break;

		}
	}

	private boolean submitCheck() {

		if (StringUtil.isEmpty(edtExceptLoanAmount.getEditableText().toString().trim())) {
			AlertUtil.t(getActivity(), "请输入期望借款金额");
			edtExceptLoanAmount.requestFocus();
			return false;
		}
		if (StringUtil.isEmpty(edtExceptLoanRate.getEditableText().toString().trim())) {
			AlertUtil.t(getActivity(), "请输入期望借款年利率");
			edtExceptLoanRate.requestFocus();
			return false;
		}

		if (StringUtil.isEmpty(edtExceptLoanReason.getEditableText().toString().trim())) {
			AlertUtil.t(getActivity(), "请输入借款理由");
			edtExceptLoanReason.requestFocus();
			return false;
		}
		if (StringUtil.isEmpty(edtExceptLoanPurpose.getEditableText().toString().trim())) {
			AlertUtil.t(getActivity(), "请输入借款用途");
			edtExceptLoanPurpose.requestFocus();
			return false;
		}
		if (StringUtil.isEmpty(edtRecommender.getEditableText().toString().trim())) {
			AlertUtil.t(getActivity(), "请输入正确的手机号");
			edtRecommender.requestFocus();
			return false;
		}
		if (StringUtil.isEmpty(edtVerifyCode.getEditableText().toString().trim())) {
			AlertUtil.t(getActivity(), "请输入图片验证码");
			edtVerifyCode.requestFocus();
			return false;
		}

		if (cityId.equals("")) {
			AlertUtil.t(getActivity(), "请选择城市");
			return false;
		}

		if (Float.parseFloat(edtExceptLoanRate.getText().toString()) < 8f
				|| Float.parseFloat(edtExceptLoanRate.getText().toString()) > 24f) {
			AlertUtil.t(getActivity(), "借款利率  请输入8~24之间数值");
			edtExceptLoanRate.requestFocus();
			return false;
		}

		if (!verifyCodeContent.equals(edtVerifyCode.getText().toString().trim())) {
			AlertUtil.t(getActivity(), "请输入正确的图片验证码");
			getCaptchaImage();
			edtVerifyCode.requestFocus();
			return false;
		}
		return true;

	}

	BizDataAsyncTask<String> submitLoanTask;

	private void submitLoan() {
		submitLoanTask = new BizDataAsyncTask<String>(getWaitingView()) {

			@Override
			protected void onExecuteSucceeded(String result) {
				showSuccessDialog(result);
			}

			@Override
			protected String doExecute() throws ZYException, BizFailure {

				return HomeBiz.financeApplication(edtExceptLoanAmount.getText().toString(),
						edtExceptLoanRate.getText().toString(), edtExceptLoanReason.getText().toString(),
						edtExceptLoanPurpose.getText().toString(), provinceId, cityId,
						edtRecommender.getText().toString());

			}

			@Override
			protected void OnExecuteFailed(String error) {
				// TODO Auto-generated method stub
				if (!StringUtil.isEmpty(error)) {
					AlertUtil.t(getActivity(), error);
				}
			}

		};

		submitLoanTask.execute();
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
					ivGetVerifyCode.setImageBitmap(bit);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			protected RedomVerifyCode doExecute() throws ZYException, BizFailure {
				return HomeBiz.getCaptchaImage();
			}

			@Override
			protected void OnExecuteFailed(String error) {
				// TODO Auto-generated method stub
				if (!StringUtil.isEmpty(error)) {
					Toast.makeText(YixingApp.getAppContext(), error, Toast.LENGTH_LONG).show();
				}
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

	private BizDataAsyncTask<List<Province>> getProvinceList;

	private void retrieveProvinces() {

		getProvinceList = new BizDataAsyncTask<List<Province>>() {

			@Override
			protected void onExecuteSucceeded(List<Province> result) {
				// TODO Auto-generated method stub

				initProPopup(tvSelectPro, result);
			}

			@Override
			protected List<Province> doExecute() throws ZYException, BizFailure {
				// TODO Auto-generated method stub
				return HomeBiz.getProvinceList("1");
			}

			@Override
			protected void OnExecuteFailed(String error) {
				// TODO Auto-generated method stub
			}
		};
		getProvinceList.execute();

	}

	private BizDataAsyncTask<List<City>> getCityList;

	private void retrieveCitiesWith(final String provinceId) {
		getCityList = new BizDataAsyncTask<List<City>>() {

			@Override
			protected void onExecuteSucceeded(List<City> result) {

				initCityPopup(tvSelectCity, result);
			}

			@Override
			protected void OnExecuteFailed(String error) {
				// TODO Auto-generated method stub
			}

			@Override
			protected List<City> doExecute() throws ZYException, BizFailure {
				// TODO Auto-generated method stub
				return HomeBiz.getCityList(provinceId, "1");
			}

		};

		getCityList.execute();
	}

	/**
	 * 省份popup
	 * 
	 * @param textView
	 * @param provinces
	 */
	private void initProPopup(final TextView textView, final List<Province> provinces) {

		final List<String> names = new ArrayList<String>();
		for (Province province : provinces) {
			names.add(province.getName());
		}

		adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, names);

		View view = LayoutInflater.from(getActivity()).inflate(R.layout.popup_loan, null);

		popupWindow = new PopupWindow(view, textView.getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT, true);

		ListView lvLoan = (ListView) view.findViewById(R.id.lv_popup_loan);
		lvLoan.setAdapter(adapter);

		// 设置popupWindow点击任意地址不可见需设置这个方法

		popupWindow.setBackgroundDrawable(getResources().getDrawable(R.color.white));
		popupWindow.setOutsideTouchable(true);

		// 设置监听事件
		lvLoan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

				if (!tvSelectPro.getText().equals(names.get(i))) {
					textView.setText(names.get(i));
					tvSelectCity.setText("");
					cityId = "";
				}

				provinceId = provinces.get(i).getId();
				popupWindow.dismiss();

			}
		});

		popupWindow.showAsDropDown(textView);

	}

	/**
	 * 城市popup
	 * 
	 * @param textView
	 * @param cities
	 */
	private void initCityPopup(final TextView textView, final List<City> cities) {

		final List<String> names = new ArrayList<String>();
		for (City city : cities) {
			names.add(city.getName());
		}

		adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, names);

		View view = LayoutInflater.from(getActivity()).inflate(R.layout.popup_loan, null);

		popupWindow = new PopupWindow(view, textView.getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT, true);

		ListView lvLoan = (ListView) view.findViewById(R.id.lv_popup_loan);
		lvLoan.setAdapter(adapter);

		// 设置popupWindow点击任意地址不可见需设置这个方法

		popupWindow.setBackgroundDrawable(getResources().getDrawable(R.color.white));
		popupWindow.setOutsideTouchable(true);

		// 设置监听事件
		lvLoan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				textView.setText(names.get(i));
				cityId = cities.get(i).getId();
				popupWindow.dismiss();

			}
		});

		popupWindow.showAsDropDown(textView);

	}

	// 显示成功的dialog
	private void showSuccessDialog(String num) {
		LoanSuccessDialog dialog = new LoanSuccessDialog(getActivity(), R.style.My_Dialog, num);
		dialog.show();
		dialog.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss(DialogInterface dialog) {

				goHome.goHomeListener();
				setNull();
			}
		});
	}

	/**
	 * 将所有填写内容置空
	 */
	private void setNull() {
		edtExceptLoanAmount.setText("");
		edtExceptLoanPurpose.setText("");
		edtExceptLoanRate.setText("");
		edtExceptLoanReason.setText("");
		edtRecommender.setText("");
		edtVerifyCode.setText("");
		provinceId = "1";
		cityId = "37";
		tvSelectPro.setText("北京市");
		tvSelectCity.setText("东城区");
	}

	public void setGoHome(GoHome goHome) {
		this.goHome = goHome;
	}

	public interface GoHome {
		void goHomeListener();
	}

}
