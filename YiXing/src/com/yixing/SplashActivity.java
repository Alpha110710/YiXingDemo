package com.yixing;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import com.yixing.storage.PreferenceCache;
import com.yixing.ui.account.ActivityMyRedPackage;
import com.yixing.ui.account.ActivityReturnMoneyPlan;

/**
 * 欢迎页
 * 
 * @author lenovo
 * 
 */
public class SplashActivity extends Activity {

	private boolean finished = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.splash);

		// final boolean guidePage = PreferenceCache.getGuidePage();
		// final String version = PreferenceCache.getVersion();

		/**
		 * 当设置为不自动登录 并且 程序异常退出（如 360 杀死应用） 进入程序先清空token
		 */
		if ( !PreferenceCache.getToken().equals("")) {
			PreferenceCache.putToken("");
		}

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				if (finished) {
					return;
				}

				/*
				 * if (true) { startActivity(new Intent(SplashActivity.this,
				 * ActivityGuide.class)); } else { // 判断版本 if
				 * (!version.equals(getVersion1())) { startActivity(new
				 * Intent(SplashActivity.this, ActivityGuide.class)); } else {
				 * startActivity(new Intent(SplashActivity.this,
				 * MainActivity.class)); }
				 * 
				 * }
				 */
				/*
				 * startActivity(new Intent(SplashActivity.this,
				 * MainActivity.class));
				 */
				startActivity(new Intent(SplashActivity.this, MainActivity.class));
				finish();

			}
		}, 2000);

		/*
		 * if(!PreferenceCache.ifAutoLogin()){
		 * PreferenceCache.putIfSkipLogin(false); }
		 */
	}

	@Override
	public void onBackPressed() {
		finished = true;
		super.onBackPressed();
	}

	/*
	 * public String getVersion1() { String version1 = ""; try { PackageManager
	 * manager = this.getPackageManager(); PackageInfo info =
	 * manager.getPackageInfo(this.getPackageName(), 0); version1 =
	 * info.versionName; } catch (Exception e) { e.printStackTrace(); } return
	 * version1; }
	 */

	public String getVersion1() {
		String version1 = "";
		PackageManager manager = this.getPackageManager();
		PackageInfo info;
		try {
			info = manager.getPackageInfo(getPackageName(), 0);
			version1 = info.versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return version1;

	}
}
