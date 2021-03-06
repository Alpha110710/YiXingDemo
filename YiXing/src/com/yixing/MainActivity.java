package com.yixing;

import java.io.File;
import java.io.IOException;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.format.Time;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.yixing.biz.HomeBiz;
import com.yixing.biz.exception.ZYException;
import com.yixing.biz.task.EasyLocalTask;
import com.yixing.model.VersionDescription;
import com.yixing.storage.PreferenceCache;
import com.yixing.ui.account.FragmentAccount;
import com.yixing.ui.base.BaseActivity;
import com.yixing.ui.base.BaseFragment;
import com.yixing.ui.home.FragmentHome;
import com.yixing.ui.init.ActivityLogin;
import com.yixing.ui.investment.FragmentInvestment;
import com.yixing.ui.loan.FragmentLoan;
import com.yixing.ui.loan.FragmentLoan.GoHome;
import com.yixing.ui.more.FragmentMore;
import com.yixing.ui.widget.PromptOkCancel;
import com.yixing.utils.android.DeviceUtil;
import com.yixing.utils.android.HttpUtil;
import com.yixing.utils.android.LogUtil;
import com.yixing.utils.java.StringUtil;

public class MainActivity extends BaseActivity implements OnClickListener {

	private RadioGroup radioGroupHome;
	private RadioButton radioHome, radioInvestment, radioLoan, radioAccount, radioMore;

	private FragmentHome homeFragment;
	private FragmentInvestment investmentFragment;
	private FragmentLoan loanFragment;
	private FragmentAccount accountFragment;
	private FragmentMore moreFragment;

	private int progress;
	private static final int DOWNLOADING = 1; // 表示正在下载
	private static final int DOWNLOADED = 2; // 下载完毕
	private static final int DOWNLOAD_FAILED = 3; // 下载失败
	private boolean cancelFlag = false;
	private String mUrl = "";
	private NotificationManager notificationManager;
	private Notification notification;
	RemoteViews view = null;

	private int type = 1;// 用于标识当前的底部item位置（初始化为首页） 首页1 ，投资专区2，借款专区3，我的账户4，关于我们5

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.actiivty_main);

		init();
		detectUpgrade();

	}

	private void init() {

		radioGroupHome = (RadioGroup) findViewById(R.id.radio_group);
		radioHome = (RadioButton) findViewById(R.id.radiobutton_home);
		radioInvestment = (RadioButton) findViewById(R.id.radiobutton_investment);
		radioLoan = (RadioButton) findViewById(R.id.radiobutton_loan);
		radioAccount = (RadioButton) findViewById(R.id.radiobutton_account);
		radioMore = (RadioButton) findViewById(R.id.radiobutton_more);
		radioHome.setOnClickListener(this);
		radioInvestment.setOnClickListener(this);
		radioLoan.setOnClickListener(this);
		radioAccount.setOnClickListener(this);
		radioMore.setOnClickListener(this);

		homeFragment = new FragmentHome();

		addFragment(homeFragment, "fhome");
		showFragment(homeFragment);

	}

	@Override
	protected void onResume() {
		super.onResume();
		if (StringUtil.isEmpty(PreferenceCache.getToken())) {// 未登录
			if (type == 1)
				radioHome.setChecked(true);
			else if (type == 2)
				radioInvestment.setChecked(true);
			else if (type == 3)
				radioLoan.setChecked(true);
			else if (type == 4)
				radioAccount.setChecked(true);
			else if (type == 5)
				radioMore.setChecked(true);
		} else {// 已经登陆
			int id = radioGroupHome.getCheckedRadioButtonId();
			if (id == R.id.radiobutton_loan) {// 借款
				type = 3;
				invokeLoanFragment();
				showFragment(loanFragment);// 借款专区

			} else if (id == R.id.radiobutton_account) {// 我的账户
				type = 4;
				invokeAccountFragment();
				showFragment(accountFragment);// 我的账户
			}

		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.radiobutton_home:
			showFragment(homeFragment);// 首页
			YixingApp.globalIndex = 0;
			type = 1;
			break;
		case R.id.radiobutton_investment:
			YixingApp.globalIndex = 1;
			type = 2;
			invokeInvestFragment();
			showFragment(investmentFragment);// 投资专区
			break;
		case R.id.radiobutton_loan:

			if (StringUtil.isEmpty(PreferenceCache.getToken())) {
				Intent itToRegist = new Intent(MainActivity.this, ActivityLogin.class);
				startActivity(itToRegist);
				return;
			}
			YixingApp.globalIndex = 2;
			type = 3;
			invokeLoanFragment();
			showFragment(loanFragment);// 借款专区
			break;
		case R.id.radiobutton_account:
			if (StringUtil.isEmpty(PreferenceCache.getToken())) {

				Intent itToRegist = new Intent(MainActivity.this, ActivityLogin.class);
				startActivity(itToRegist);
				return;
			}
			type = 4;
			YixingApp.globalIndex = 3;
			invokeAccountFragment();
			showFragment(accountFragment);// 我的账户
			break;
		case R.id.radiobutton_more:
			YixingApp.globalIndex = 4;
			type = 5;
			invokeMoreFragment();
			showFragment(moreFragment);// 关于我们
			break;
		}

	}

	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
		setIntent(intent);

		if (YixingApp.globalIndex == 0) {
			showFragment(homeFragment);
			radioHome.setChecked(true);
			type = 1;
		} else if (YixingApp.globalIndex == 1) {
			showFragment(investmentFragment);
			radioGroupHome.check(radioInvestment.getId());
			refreshFragment();
		} else if (YixingApp.globalIndex == 2) {
			showFragment(loanFragment);
		} else if (YixingApp.globalIndex == 3) {
			invokeAccountFragment();
			showFragment(accountFragment);
			radioAccount.setChecked(true);
			type = 4;
		} else if (YixingApp.globalIndex == 4) {
			showFragment(moreFragment);
		}

		if (YixingApp.goAccount == 1) {
			invokeAccountFragment();
			showFragment(accountFragment);
			type = 4;
			radioGroupHome.check(radioAccount.getId());
			YixingApp.globalIndex = 3;
			YixingApp.goAccount = 0;
		}

		if (YixingApp.goInvest == 1) {
			invokeInvestFragment();
			showFragment(investmentFragment);
			type = 2;
			radioGroupHome.check(radioInvestment.getId());
			YixingApp.globalIndex = 1;
			YixingApp.goInvest = 0;
		}

	}

	private void refreshFragment() {

		BaseFragment baseFragment = (BaseFragment) this.getSupportFragmentManager().findFragmentByTag("finvest");
		if (baseFragment != null) {
			YixingApp.canQueryFromOnResume = true;
			baseFragment.onResume();
		}

	}

	private void addFragment(Fragment fragment, String tag) {
		getSupportFragmentManager().beginTransaction().add(R.id.fl_radio, fragment, tag).commit();
	}

	/**
	 * 显示fragment
	 * 
	 * @param fragment
	 */

	private void showFragment(Fragment fragment) {
		FragmentManager manager = getSupportFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();

		if (homeFragment != null) {
			transaction.hide(homeFragment);
		}

		if (investmentFragment != null) {
			transaction.hide(investmentFragment);
		}

		if (loanFragment != null) {
			transaction.hide(loanFragment);
		}

		if (accountFragment != null) {
			transaction.hide(accountFragment);
		}

		if (moreFragment != null) {
			transaction.hide(moreFragment);
		}
		transaction.show(fragment).commit();
		
	}

	// 跳转到投资专区的对应筛选条件

	public void setInvestType(int tabOne, int tabTwo, int tabThree) {

		if (investmentFragment == null) {
			investmentFragment = new FragmentInvestment();
			addFragment(investmentFragment, "finvest");

		}
		showFragment(investmentFragment);
		investmentFragment.homeInfo = true;
		investmentFragment.type = tabOne;
		investmentFragment.productType_A = tabTwo;
		investmentFragment.rateType_A = tabThree;
		type = 2;
		radioInvestment.setChecked(true);// 底部导航栏的显示

	}

	long lastBackKeyDownTime = 0;

	@Override
	public void onBackPressed() {
		if (System.currentTimeMillis() - lastBackKeyDownTime > 2000) { // 两秒钟内双击返回键关闭主界面
			Toast.makeText(this, R.string.double_tap_to_exit, Toast.LENGTH_SHORT).show();
			lastBackKeyDownTime = System.currentTimeMillis();

		} else {
			android.os.Process.killProcess(android.os.Process.myPid());
			super.onBackPressed();
		}
	}

	//解决重影问题 
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		if (savedInstanceState.getInt("position") == 0) {
			invokeHomeFragment();
			showFragment(homeFragment);
			radioGroupHome.check(radioHome.getId());
			type = 1;
			YixingApp.globalIndex = 0;
		} else if (savedInstanceState.getInt("position") == 1) {
			invokeInvestFragment();
			showFragment(investmentFragment);
			radioGroupHome.check(radioInvestment.getId());
			type = 2;
			YixingApp.globalIndex = 1;
		} else if (savedInstanceState.getInt("position") == 2) {
			invokeLoanFragment();
			showFragment(loanFragment);
			radioGroupHome.check(radioLoan.getId());
			type = 3;
			YixingApp.globalIndex = 2;
		} else if (savedInstanceState.getInt("position") == 3) {
			invokeAccountFragment();
			showFragment(accountFragment);
			radioGroupHome.check(radioAccount.getId());
			type = 4;
			YixingApp.globalIndex = 3;
		} else {
			invokeMoreFragment();
			showFragment(moreFragment);
			radioGroupHome.check(radioMore.getId());
			type = 5;
			YixingApp.globalIndex = 4;
		}
		
		super.onRestoreInstanceState(savedInstanceState);

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
//		super.onSaveInstanceState(outState);
		outState.putInt("position", YixingApp.globalIndex);
	}

	private void invokeHomeFragment() {

		if (homeFragment == null) {
			homeFragment = new FragmentHome();
			addFragment(homeFragment, "fhome");
		}
	}	

	private void invokeInvestFragment() {

		if (investmentFragment == null) {
			investmentFragment = new FragmentInvestment();
			addFragment(investmentFragment, "finvest");
		}
	}

	private void invokeLoanFragment() {

		if (loanFragment == null) {
			loanFragment = new FragmentLoan();
			// 申请借贷成功后返回 首页的监听
			loanFragment.setGoHome(new GoHome() {
				@Override
				public void goHomeListener() {
					radioHome.setChecked(true);
					type = 1;
					showFragment(homeFragment);
					YixingApp.globalIndex = 0;
				}
			});
			addFragment(loanFragment, "floan");
		}
	}

	private void invokeAccountFragment() {

		if (accountFragment == null) {
			accountFragment = new FragmentAccount();
			addFragment(accountFragment, "faccount");
		}
	}

	private void invokeMoreFragment() {

		if (moreFragment == null) {
			moreFragment = new FragmentMore();
			addFragment(moreFragment, "fmore");
		}
	}

	private void detectUpgrade() {
		new EasyLocalTask<Void, VersionDescription>() {

			@Override
			protected VersionDescription doInBackground(Void... params) {

				try {
					return HomeBiz.detectNewVersion();

				} catch (ZYException e) {
					return null;
				}

			}

			@Override
			protected void onPostExecute(VersionDescription result) {
				super.onPostExecute(result);

				if (result != null) {
					if (!DeviceUtil.getVesionName(MainActivity.this).equals(result.getVersionName())) {
						final VersionDescription vd = result;
						mUrl = vd.getDownloadLink();
						new PromptOkCancel(MainActivity.this) {

							@Override
							protected void onOk() {
								LogUtil.e(vd.getDownloadLink());
								if (StringUtil.isEmpty(vd.getDownloadLink())) {
									return;
								}
								downloadApk(vd.getDownloadLink());
								// downloadAPK();
							}
						}.show(getString(R.string.new_version_detected), result.getVersionDescription(),
								R.string.download_background, R.string.remind_me_later, result.getAndroidForceUpdate());// 如果给传递值就强制下载
					}
				}
			}
		}.execute();
	}

	private void downloadApk(final String url) {
		new EasyLocalTask<Void, File>() {

			@Override
			protected File doInBackground(Void... params) {
				File file = new File(YixingApp.CACHE_ROOT_CACHE_DIR + File.separator + YixingConfig.APK_NAME);
				try {
					LogUtil.e(url);
					notification();

					HttpUtil.downloadFile(url, file, new HttpUtil.IDownloadCallback() {

						int i = 0;

						@Override
						public void onProgress(long currentSize, long totalSize) {

							progress = (int) (((float) currentSize / totalSize) * 100);

							if ((int) (progress / 10) > i) {
								i = (int) (progress / 10);
								// 更改进度条
								notification.contentView.setProgressBar(R.id.progress, (int) (totalSize / 1024 / 1000),
										(int) (currentSize / 1024 / 1000), false);
								// 发送消息
								notificationManager.notify(101, notification);
							}
						}
					});
					// HttpUtil.downloadFile(url, file);
				} catch (IOException e) {
					file = null;
				}

				return file;
			}

			@Override
			protected void onPostExecute(File result) {
				super.onPostExecute(result);
				if (result != null) {
					notificationManager.cancel(101);// notification关闭不显示
					installApk(result);
				}
			}
		}.execute();
	}

	/**
	 * @Description 安装APK
	 */
	public void installApk(File file) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");

		startActivity(intent);
	}

	private void notification() {
		notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		notification = new Notification(R.drawable.ic_launcher, "下载新版本", System.currentTimeMillis());
		/*
		 * notification = new Notification(); notification.icon =
		 * R.drawable.ic_launcher; notification.tickerText = "下载新版本";
		 */

		if (view == null) {
			view = new RemoteViews(getPackageName(), R.layout.notification);
			notification.contentView = view;
			notification.contentView.setProgressBar(R.id.progress, 100, 0, false);
		}
		PendingIntent contentIntent = PendingIntent.getActivity(this, R.string.app_name, new Intent(),
				PendingIntent.FLAG_UPDATE_CURRENT);
		notification.contentIntent = contentIntent;
		notification.flags |= Notification.FLAG_ONGOING_EVENT;// 滑动或者clear都不会清空
		// 获取系统当前时间
		Time t = new Time();
		t.setToNow(); // 取得系统时间。
		int hour = t.hour;
		int minute = t.minute;
		if (hour >= 12) {
			if (hour == 12) {
				notification.contentView.setTextViewText(R.id.time, "下午" + hour + ":" + minute);
			}
			notification.contentView.setTextViewText(R.id.time, "下午" + (hour - 12) + ":" + minute);
		} else {
			notification.contentView.setTextViewText(R.id.time, "上午" + hour + ":" + minute);
		}
		notificationManager.notify(101, notification);
	}

}
