package com.yixing.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.yixing.R;
import com.yixing.ui.widget.WaitingView;

public class BaseFragment extends Fragment {
	public View findViewById(int resId){
		return getView().findViewById(resId);
	}
	
    public WaitingView getWaitingView(){
    	BaseActivity activity = (BaseActivity) getActivity();
    	return activity.getWaitingView();
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    }

    
}