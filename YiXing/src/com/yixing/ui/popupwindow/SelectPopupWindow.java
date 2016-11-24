package com.yixing.ui.popupwindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yixing.R;

/***
 * 性别 ，婚姻状况通用 popwindow
 * 
 */
public class SelectPopupWindow extends PopupWindow {
	private View mMenuView;
	private TextView btn_cancel, btn_man, btn_woman;// 取消,男,女
	private TextView btn_yh, btn_wh, btn_ly, btn_so;

	public SelectPopupWindow(final Activity context, boolean type,
			OnClickListener itemsOnClick) {
		super(context);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		if (type) {
			mMenuView = inflater.inflate(R.layout.activity_sex_select, null);// 性别选择
			btn_man = (TextView) mMenuView.findViewById(R.id.btn_man);
			btn_woman = (TextView) mMenuView.findViewById(R.id.btn_woman);
			btn_man.setOnClickListener(itemsOnClick);
			btn_woman.setOnClickListener(itemsOnClick);
		} else {
			mMenuView = inflater.inflate(R.layout.activity_marriage, null);// 婚姻状况选择
			btn_yh = (TextView) mMenuView.findViewById(R.id.btn_yh);
			btn_wh = (TextView) mMenuView.findViewById(R.id.btn_wh);
			btn_ly = (TextView) mMenuView.findViewById(R.id.btn_ly);
			btn_so = (TextView) mMenuView.findViewById(R.id.btn_so);
			btn_yh.setOnClickListener(itemsOnClick);
			btn_wh.setOnClickListener(itemsOnClick);
			btn_ly.setOnClickListener(itemsOnClick);
			btn_so.setOnClickListener(itemsOnClick);
		}
		btn_cancel = (TextView) mMenuView.findViewById(R.id.btn_cancel);
		// 取消按钮
		btn_cancel.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 销毁弹出框
				dismiss();
			}
		});
		// 设置SelectPicPopupWindow的View
		this.setContentView(mMenuView);
		// 设置SelectPicPopupWindow弹出窗体的宽
		this.setWidth(LayoutParams.FILL_PARENT);
		// 设置SelectPicPopupWindow弹出窗体的高
		this.setHeight(LayoutParams.WRAP_CONTENT);
		// 设置SelectPicPopupWindow弹出窗体可点击
		this.setFocusable(true);
		// 设置SelectPicPopupWindow弹出窗体动画效果
		this.setAnimationStyle(R.anim.bottom_dialog_enter);
		// 实例化一个ColorDrawable颜色为半透明
		ColorDrawable dw = new ColorDrawable(0xb0000000);
		// 设置SelectPicPopupWindow弹出窗体的背景
		this.setBackgroundDrawable(dw);
		// mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
		mMenuView.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {

				int height = mMenuView.findViewById(R.id.pop_layout)
						.getTop();
				int y = (int) event.getY();
				if (event.getAction() == MotionEvent.ACTION_UP) {
					if (y < height) {
						dismiss();
					}
				}
				return true;
			}
		});

	}
}
