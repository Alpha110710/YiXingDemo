package com.yixing;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.loveplusplus.demo.image.R;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class YixingApp extends Application {

	private static final String CACHE_ROOT_DIR = YixingConfig.EXT_STORAGE_ROOT + File.separator + YixingConfig.CACHE_ROOT_NAME;  
	public static final String CACHE_PIC_ROOT_DIR = CACHE_ROOT_DIR + File.separator + YixingConfig.CACHE_PIC_ROOT_NAME;  
	public static final String CACHE_ROOT_CACHE_DIR = CACHE_ROOT_DIR + File.separator + YixingConfig.CACHE_ROOT_CACHE_NAME;

	private static Context mAppContext;
	public static int globalIndex = 0;
	public static boolean canQueryFromOnResume = false;
	private List<Activity> list=new ArrayList<Activity>();
	
	public static int goAccount = 0;
	public static int goInvest = 0;
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		mAppContext = getApplicationContext();
		
		buildCacheDir();
		
		initImageLoader(mAppContext);
	}
	public static Context getAppContext() {
		return mAppContext;
	}
	
	public static void initImageLoader(Context context) {
		// This configuration tuning is custom. You can tune every option, you
		// may tune some of them,
		// or you can create default configuration by
		// ImageLoaderConfiguration.createDefault(this);
		// method.
		DisplayImageOptions defaultOptions = new DisplayImageOptions
				.Builder()
				.showImageForEmptyUri(R.drawable.empty_photo) 
				.showImageOnFail(R.drawable.empty_photo) 
				.cacheInMemory(true)
				.cacheOnDisc(true)
				.build();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration
				.Builder(context)
				.defaultDisplayImageOptions(defaultOptions)
				.discCacheSize(50 * 1024 * 1024)//
				.discCacheFileCount(100)//缓存一百张图片
				.writeDebugLogs()
				.build();
		ImageLoader.getInstance().init(config);		
	}
	
	public static void buildCacheDir(){
		File rootDir = new File(CACHE_ROOT_DIR);
		if(!rootDir.exists()){
			rootDir.mkdir();
		}
		
		File cacheDir = new File(CACHE_ROOT_CACHE_DIR);
		if(!cacheDir.exists()){
			cacheDir.mkdir();
		}
		
		File picRootDir = new File(CACHE_PIC_ROOT_DIR);
		if(!picRootDir.exists()){
			picRootDir.mkdir();
		}
	}
}
