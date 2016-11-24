package com.yixing.ui.account;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yixing.R;
import com.yixing.biz.AccountBiz;
import com.yixing.biz.exception.BizFailure;
import com.yixing.biz.exception.ZYException;
import com.yixing.biz.task.BizDataAsyncTask;
import com.yixing.model.PersonalInformationDetailModel;
import com.yixing.ui.base.BaseActivity;
import com.yixing.ui.popupwindow.BirthdayPopupWindow;
import com.yixing.ui.popupwindow.EducationPopupWindow;
import com.yixing.ui.popupwindow.SelectPicPopupWindow;
import com.yixing.ui.popupwindow.SelectPopupWindow;
import com.yixing.ui.widget.CircleImageView;
import com.yixing.utils.java.AlertUtil;
import com.yixing.utils.java.StringUtil;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 点击头像进入
 * 
 * @author Ls
 * 
 */
public class ActivityPersonalBasicInfo extends BaseActivity implements OnClickListener {

	private TextView tv_title;
	private ImageView iv_back;

	private RelativeLayout rl_personal_info_head_next;

	private LinearLayout rl_personal_birthday;
	private LinearLayout rl_personal_sex;
	private LinearLayout rl_personal_education;
	private LinearLayout rl_personal_qq;
	private LinearLayout rl_personal_matrimony_type;
	private LinearLayout rl_personal_industry;
	private LinearLayout rl_personal_monthly_income;

	private CircleImageView iv_personal_info_head_icon;
	private TextView tv_personal_info_real_name;
	private TextView tv_personal_info_id_num;
	private TextView tv_personal_info_tele_num;
	private TextView tv_personal_info_email_num;
	private TextView tv_personal_info_birth;
	private TextView tv_personal_info_sex;
	private TextView tv_personal_info_xueli;
	private TextView tv_personal_info_qq;
	private TextView tv_personal_info_hunyin;
	private TextView tv_personal_info_hangye;
	private TextView tv_personal_info_shouru;
	private TextView tv_personal_info_address;
	private Button btn_personal_info_confirm;

	// 头像有关
	SelectPicPopupWindow picPopupWindow;
	private static final int PHOTO_REQUEST_CAMERA = 11;// 拍照
	private static final int PHOTO_REQUEST_GALLERY = 12;// 从相册中选择
	private static final int PHOTO_REQUEST_CUT = 13;// 结果
	private static final int REQUEST_CODE_QQ = 4;// QQ
	private static final int REQUEST_CODE_HANGYE = 5;// 行业
	private static final int REQUEST_CODE_SHOURU = 6;// 收入
	/* 头像名称 */
	private static final String PHOTO_FILE_NAME = "head.jpg";
	private File tempFile;// 照相图片存储
	private Bitmap bitmap;// 剪裁后bitmap图片 上传图片将该bitmap上传

	// 生日
	BirthdayPopupWindow birthdayPopupWindow;
	// 性别 婚姻状况通用popwindow
	SelectPopupWindow selectPopupWindow;
	// 学历
	EducationPopupWindow educationPopupWindow;

	DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().showImageForEmptyUri(R.drawable.my_account5)
			.showImageOnFail(R.drawable.my_account5).cacheInMemory(true).cacheOnDisc(true).build();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_personal_basic_information);
		initView();

		initData();
	}

	private void initView() {

		tv_title = (TextView) findViewById(R.id.tv_title);
		iv_back = (ImageView) findViewById(R.id.iv_back);

		rl_personal_info_head_next = (RelativeLayout) findViewById(R.id.rl_personal_info_head_next);
		iv_personal_info_head_icon = (CircleImageView) findViewById(R.id.iv_personal_info_head_icon);
		rl_personal_birthday = (LinearLayout) findViewById(R.id.rl_personal_birthday);
		rl_personal_sex = (LinearLayout) findViewById(R.id.rl_personal_sex);
		rl_personal_education = (LinearLayout) findViewById(R.id.rl_personal_education);
		rl_personal_qq = (LinearLayout) findViewById(R.id.rl_personal_qq);
		rl_personal_matrimony_type = (LinearLayout) findViewById(R.id.rl_personal_matrimony_type);
		rl_personal_industry = (LinearLayout) findViewById(R.id.rl_personal_industry);
		rl_personal_monthly_income = (LinearLayout) findViewById(R.id.rl_personal_monthly_income);

		tv_personal_info_real_name = (TextView) findViewById(R.id.tv_personal_info_real_name);
		tv_personal_info_id_num = (TextView) findViewById(R.id.tv_personal_info_id_num);
		tv_personal_info_tele_num = (TextView) findViewById(R.id.tv_personal_info_tele_num);
		tv_personal_info_email_num = (TextView) findViewById(R.id.tv_personal_info_email_num);
		tv_personal_info_birth = (TextView) findViewById(R.id.tv_personal_info_birth);
		tv_personal_info_sex = (TextView) findViewById(R.id.tv_personal_info_sex);
		tv_personal_info_xueli = (TextView) findViewById(R.id.tv_personal_info_xueli);
		tv_personal_info_qq = (TextView) findViewById(R.id.tv_personal_info_qq);
		tv_personal_info_hunyin = (TextView) findViewById(R.id.tv_personal_info_hunyin);
		tv_personal_info_hangye = (TextView) findViewById(R.id.tv_personal_info_hangye);
		tv_personal_info_shouru = (TextView) findViewById(R.id.tv_personal_info_shouru);
		tv_personal_info_address = (TextView) findViewById(R.id.tv_personal_info_address);
		btn_personal_info_confirm = (Button) findViewById(R.id.btn_personal_info_confirm);

		iv_back.setOnClickListener(this);
		rl_personal_info_head_next.setOnClickListener(this);// 头像layout
		btn_personal_info_confirm.setOnClickListener(this);// 保存信息
		rl_personal_birthday.setOnClickListener(this);// 生日
		rl_personal_sex.setOnClickListener(this);// 性别
		rl_personal_education.setOnClickListener(this);// 学历
		rl_personal_qq.setOnClickListener(this);// qq
		rl_personal_matrimony_type.setOnClickListener(this);// 婚姻状况
		rl_personal_industry.setOnClickListener(this);// 所属行业
		rl_personal_monthly_income.setOnClickListener(this);// 月收入

		tv_title.setText("个人基本信息");

	}

	private void initData() {
		getPersonalInfo();
	}

	private BizDataAsyncTask<PersonalInformationDetailModel> task;

	private void getPersonalInfo() {
		task = new BizDataAsyncTask<PersonalInformationDetailModel>() {

			@Override
			protected void onExecuteSucceeded(PersonalInformationDetailModel result) {

				tv_personal_info_birth.setText(result.getBIRTHDAY());
				tv_personal_info_email_num.setText(result.getEMAIL());
				tv_personal_info_hangye.setText(result.getHANGYE());
				tv_personal_info_hunyin.setText(checkMarriageNum(result.getHUNYING()));
				if (!StringUtil.isEmpty(result.getID_CARD())) {
					tv_personal_info_id_num.setText(result.getID_CARD().substring(0, 1) + "******"
							+ result.getID_CARD().substring(result.getID_CARD().length() - 1));
				}
				
				tv_personal_info_qq.setText(result.getQQ());
				if (!StringUtil.isEmpty(result.getUSER_NAME())) {
					tv_personal_info_real_name.setText(result.getUSER_NAME().substring(0, 1) + "**");
				}
				
				tv_personal_info_sex.setText(checkSexNum(result.getSEX()));
				tv_personal_info_shouru.setText(result.getSHOURU());
				
				if (!StringUtil.isEmpty(result.getMOBILE())) {
					tv_personal_info_tele_num.setText(result.getMOBILE().substring(0, 3) + "******"
							+ result.getMOBILE().substring(result.getMOBILE().length() - 3));
				}
				
				tv_personal_info_xueli.setText(checkEducationNum(result.getXUELI()));
				ImageLoader.getInstance().displayImage(result.getHeadPic(), iv_personal_info_head_icon, defaultOptions);

			}

			@Override
			protected PersonalInformationDetailModel doExecute() throws ZYException, BizFailure {
				// TODO Auto-generated method stub
				return AccountBiz.getPersonalInfoDetail();
			}

			@Override
			protected void OnExecuteFailed(String error) {
				// TODO Auto-generated method stub

			}
		};

		task.execute();
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.iv_back:
			finish();
			break;
		case R.id.rl_personal_info_head_next:// 头像
			getSelectHead();
			break;
		case R.id.rl_personal_birthday:// 生日
			getSelectBirthday();
			break;
		case R.id.rl_personal_sex:// 性别
			getSelect(true);
			break;
		case R.id.rl_personal_education:// 学历
			getSelectEducation();
			break;
		case R.id.rl_personal_qq:// qq
			Intent intentqq = new Intent(ActivityPersonalBasicInfo.this, ActivityPersionalAmend.class);
			intentqq.putExtra("type", REQUEST_CODE_QQ);
			intentqq.putExtra("result", tv_personal_info_qq.getText().toString().trim());
			startActivityForResult(intentqq, REQUEST_CODE_QQ);
			break;
		case R.id.rl_personal_matrimony_type:// 婚姻状况
			getSelect(false);
			break;
		case R.id.rl_personal_industry:// 所属行业
			Intent intenthy = new Intent(ActivityPersonalBasicInfo.this, ActivityPersionalAmend.class);
			intenthy.putExtra("type", REQUEST_CODE_HANGYE);
			intenthy.putExtra("result", tv_personal_info_hangye.getText().toString().trim());
			startActivityForResult(intenthy, REQUEST_CODE_HANGYE);
			break;
		case R.id.rl_personal_monthly_income:// 月收入
			Intent intentsr = new Intent(ActivityPersonalBasicInfo.this, ActivityPersionalAmend.class);
			intentsr.putExtra("type", REQUEST_CODE_SHOURU);
			intentsr.putExtra("result", tv_personal_info_shouru.getText().toString().trim());
			startActivityForResult(intentsr, REQUEST_CODE_SHOURU);
			break;
		case R.id.btn_personal_info_confirm:// 保存信息
			String birthday = tv_personal_info_birth.getText().toString().trim();
			String sex = tv_personal_info_sex.getText().toString().trim();
			String education = tv_personal_info_xueli.getText().toString().trim();
			String qq = tv_personal_info_qq.getText().toString().trim();
			String marriage = tv_personal_info_hunyin.getText().toString().trim();
			String hangye = tv_personal_info_hangye.getText().toString().trim();
			String income = tv_personal_info_shouru.getText().toString().trim();
			save(birthday, checkSex(sex), checkEducation(education), qq, checkMarriage(marriage), hangye, income);// 上传文字等信息
			// 首先进行保存信息, 成功后再保存头像 避免两次
			break;
		default:
			break;
		}
	}

	/*
	 * 上传图片
	 */
	public String upload() {
		String photo = null;
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
			out.flush();
			out.close();
			byte[] buffer = out.toByteArray();
			byte[] encode = Base64.encode(buffer, Base64.DEFAULT);
			photo = new String(encode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return photo;
	}

	/*
	 * 头像
	 */
	private void getSelectHead() {
		picPopupWindow = new SelectPicPopupWindow(ActivityPersonalBasicInfo.this, new OnClickListener() {
			@Override
			public void onClick(View v) {
				picPopupWindow.dismiss();
				switch (v.getId()) {
				case R.id.btn_take_photo:// 照相
					camera();
					break;
				case R.id.btn_pick_photo:// 相册
					gallery();
					break;
				default:
					break;
				}
			}
		});
		picPopupWindow.showAtLocation(btn_personal_info_confirm, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); // 设置layout在PopupWindow中显示的位置
	}

	/*
	 * 生日
	 */
	private void getSelectBirthday() {
		birthdayPopupWindow = new BirthdayPopupWindow(ActivityPersonalBasicInfo.this, new OnClickListener() {

			@Override
			public void onClick(View v) {
				birthdayPopupWindow.dismiss();
				switch (v.getId()) {
				case R.id.birthday_submit:// 确定
					if (!"null".equals(birthdayPopupWindow.result) && birthdayPopupWindow.result != null)
						tv_personal_info_birth.setText(birthdayPopupWindow.result);
					else
						tv_personal_info_birth.setText(getData());
					break;

				default:
					break;
				}
			}
		});
		birthdayPopupWindow.showAtLocation(btn_personal_info_confirm, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); // 设置layout在PopupWindow中显示的位置
	}

	/*
	 * 学历
	 */
	private void getSelectEducation() {
		educationPopupWindow = new EducationPopupWindow(ActivityPersonalBasicInfo.this, new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				educationPopupWindow.dismiss();
				TextView text = (TextView) arg1.findViewById(R.id.item_education);
				tv_personal_info_xueli.setText(text.getText().toString());
			}
		});

		educationPopupWindow.showAtLocation(btn_personal_info_confirm, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0,
				0); // 设置layout在PopupWindow中显示的位置

	}

	/*
	 * 性别 婚姻状况选择
	 */
	private void getSelect(boolean type) {
		selectPopupWindow = new SelectPopupWindow(ActivityPersonalBasicInfo.this, type, new OnClickListener() {

			@Override
			public void onClick(View v) {
				selectPopupWindow.dismiss();
				switch (v.getId()) {
				case R.id.btn_man:// 男
					tv_personal_info_sex.setText("男");
					break;
				case R.id.btn_woman:// 女
					tv_personal_info_sex.setText("女");
					break;
				case R.id.btn_yh:// 已婚
					tv_personal_info_hunyin.setText("已婚");
					break;
				case R.id.btn_wh:// 未婚
					tv_personal_info_hunyin.setText("未婚");
					break;
				case R.id.btn_ly:// 离异
					tv_personal_info_hunyin.setText("离异");
					break;
				case R.id.btn_so:// 丧偶
					tv_personal_info_hunyin.setText("丧偶");
					break;

				default:
					break;
				}
			}
		});
		selectPopupWindow.showAtLocation(btn_personal_info_confirm, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); // 设置layout在PopupWindow中显示的位置
	}

	/*
	 * 获取当前时间
	 */
	public String getData() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd ");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		return formatter.format(curDate);
	}

	/*
	 * 从相册获取
	 */
	public void gallery() {
		// 激活系统图库，选择一张图片
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setType("image/*");
		startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
	}

	/*
	 * 从相机获取
	 */
	public void camera() {
		Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
		// 判断存储卡是否可以用，可用进行存储
		if (hasSdcard()) {
			intent.putExtra(MediaStore.EXTRA_OUTPUT,
					Uri.fromFile(new File(Environment.getExternalStorageDirectory(), PHOTO_FILE_NAME)));
		}
		startActivityForResult(intent, PHOTO_REQUEST_CAMERA);
	}

	/**
	 * 剪切图片
	 * 
	 * @function:
	 * @author:Jerry
	 * @date:2013-12-30
	 * @param uri
	 */
	private void crop(Uri uri) {
		// 裁剪图片意图
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		// 裁剪框的比例，1：1
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// 裁剪后输出图片的尺寸大小
		intent.putExtra("outputX", 97);
		intent.putExtra("outputY", 97);
		// 图片格式
		intent.putExtra("outputFormat", "JPEG");
		intent.putExtra("noFaceDetection", true);// 取消人脸识别
		intent.putExtra("return-data", true);// true:不返回uri，false：返回uri
		startActivityForResult(intent, PHOTO_REQUEST_CUT);
	}

	private boolean hasSdcard() {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == PHOTO_REQUEST_GALLERY) {
			if (data != null) {
				// 得到图片的全路径
				Uri uri = data.getData();
				crop(uri);// 剪裁图片
			}

		} else if (requestCode == PHOTO_REQUEST_CAMERA) {
			if (hasSdcard()) {
				tempFile = new File(Environment.getExternalStorageDirectory(), PHOTO_FILE_NAME);
				crop(Uri.fromFile(tempFile));// 剪裁
			} else {
				AlertUtil.t(ActivityPersonalBasicInfo.this, "未找到存储卡，无法存储照片！");
			}

		} else if (requestCode == PHOTO_REQUEST_CUT) {
			try {
				bitmap = data.getParcelableExtra("data");
				this.iv_personal_info_head_icon.setImageBitmap(bitmap);// 给头像显示图片
				boolean delete = tempFile.delete();

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		if (resultCode == RESULT_OK) {
			if (requestCode == REQUEST_CODE_QQ) {// qq返回
				tv_personal_info_qq.setText(data.getStringExtra("result"));
			} else if (requestCode == REQUEST_CODE_HANGYE) {// 行业
				tv_personal_info_hangye.setText(data.getStringExtra("result"));
			} else if (requestCode == REQUEST_CODE_SHOURU) {// 月收入
				tv_personal_info_shouru.setText(data.getStringExtra("result"));
			}
		}
	}

	private BizDataAsyncTask<String> save;

	/**
	 * 保存用户信息
	 */
	private void save(final String birthday, final String sex, final String education, final String qq,
			final String marriage, final String hangye, final String income) {
		save = new BizDataAsyncTask<String>() {

			@Override
			protected void onExecuteSucceeded(String result) {
				if ("1".equals(result)) {
					if (bitmap != null) {
						String photo = upload();
//						Log.i("aaa", photo);
						headUpload(PHOTO_FILE_NAME, photo);// 头像上传
					} else {
						AlertUtil.t(ActivityPersonalBasicInfo.this, "信息保存成功");
					}

				}
			}

			@Override
			protected String doExecute() throws ZYException, BizFailure {
				return AccountBiz.save(birthday, sex, education, qq, marriage, hangye, income);// 生日，性别，学历，qq,婚姻状况，行业，收入
			}

			@Override
			protected void OnExecuteFailed(String error) {
				if (!StringUtil.isEmpty(error)) {
					AlertUtil.t(ActivityPersonalBasicInfo.this, error);
				}
			}
		};
		
		save.execute();

	}

	private BizDataAsyncTask<String> getUserPic;

	private void headUpload(final String fileName, final String image) {
		getUserPic = new BizDataAsyncTask<String>() {

			@Override
			protected void onExecuteSucceeded(String result) {
				if ("1".equals(result)) {
					AlertUtil.t(ActivityPersonalBasicInfo.this, "信息保存成功");
				}
			}

			@Override
			protected String doExecute() throws ZYException, BizFailure {
				return AccountBiz.getUserPic(fileName, image);
			}

			@Override
			protected void OnExecuteFailed(String error) {
				if (!StringUtil.isEmpty(error)) {
					AlertUtil.t(ActivityPersonalBasicInfo.this, error);
				}
			}
		};

		getUserPic.execute();
	}

	/**
	 * 参数education
	 * 
	 * @param education
	 * @return
	 */
	private String checkEducationNum(String education) {
		if (education.equals("1")) {
			return "小学";
		} else if (education.equals("2")) {
			return "初中";
		} else if (education.equals("3")) {
			return "高中";
		} else if (education.equals("4")) {
			return "中专";
		} else if (education.equals("5")) {
			return "大专";
		} else if (education.equals("6")) {
			return "本科";
		} else if (education.equals("7")) {
			return "硕士";
		} else if (education.equals("8")) {
			return "博士";
		} else {
			return "";
		}
	}

	/**
	 * 参数sex
	 * 
	 * @param sex
	 * @return
	 */
	private String checkSexNum(String sex) {
		if (sex.equals("1")) {
			return "男";
		} else if (sex.equals("0")) {
			return "女";
		} else {
			return "";
		}
	}

	/**
	 * 参数婚姻状况
	 * 
	 * @param marriage
	 * @return
	 */
	private String checkMarriageNum(String marriage) {

		if (marriage.equals("1")) {
			return "未婚";
		} else if (marriage.equals("2")) {
			return "已婚";
		} else if (marriage.equals("3")) {
			return "离异";
		} else if (marriage.equals("4")) {
			return "丧偶";
		} else {
			return "";
		}
	}

	/**
	 * 参数education
	 * 
	 * @param education
	 * @return
	 */
	private String checkEducation(String education) {
		if (education.equals("小学")) {
			return "1";
		} else if (education.equals("初中")) {
			return "2";
		} else if (education.equals("高中")) {
			return "3";
		} else if (education.equals("中专")) {
			return "4";
		} else if (education.equals("大专")) {
			return "5";
		} else if (education.equals("本科")) {
			return "6";
		} else if (education.equals("硕士")) {
			return "7";
		} else if (education.equals("博士")) {
			return "8";
		} else {
			return "";
		}
	}

	/**
	 * 参数sex
	 * 
	 * @param sex
	 * @return
	 */
	private String checkSex(String sex) {
		if (sex.equals("男")) {
			return "1";
		} else if (sex.equals("女")) {
			return "0";
		} else {
			return "";
		}
	}

	/**
	 * 参数婚姻状况
	 * 
	 * @param marriage
	 * @return
	 */
	private String checkMarriage(String marriage) {

		if (marriage.equals("未婚")) {
			return "1";
		} else if (marriage.equals("已婚")) {
			return "2";
		} else if (marriage.equals("离异")) {
			return "3";
		} else if (marriage.equals("丧偶")) {
			return "4";
		} else {
			return "";
		}
	}

}
