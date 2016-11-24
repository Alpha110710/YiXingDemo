package com.yixing.ui.loan;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import com.yixing.R;
import com.yixing.ui.base.BaseActivity;

public class ActivityLoanProcess extends BaseActivity implements OnClickListener {

	private GridView gvLoanProcess;
	private int[] pic_ids = { R.drawable.borrow_flow_one, R.drawable.borrow_flow_two, R.drawable.borrow_flow_three,
			R.drawable.borrow_flow_four, R.drawable.borrow_flow_five, R.drawable.borrow_flow_six };
	private ImageView iv_back;
	private TextView tv_title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loan_process);
		init();
	}

	private void init() {

		setTitle("借款流程");
		gvLoanProcess = (GridView) findViewById(R.id.gv_loan_process);
		iv_back = (ImageView) findViewById(R.id.iv_back);
		tv_title = (TextView) findViewById(R.id.tv_title);

		gvLoanProcess.setAdapter(new GVAdapter(ActivityLoanProcess.this));
		tv_title.setText("借款流程");
		iv_back.setOnClickListener(this);
	}

	class GVAdapter extends BaseAdapter {
		private Context mContext;

		// Constructor
		public GVAdapter(Context c) {
			mContext = c;
		}

		public int getCount() {
			return pic_ids.length;
		}

		public Object getItem(int position) {
			return null;
		}

		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup arg2) {
			// TODO Auto-generated method stub
			ImageView imageView;
			if (convertView == null) {
				imageView = new ImageView(mContext);
				imageView.setLayoutParams(
						new GridView.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
				imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//				imageView.setPadding(0, 10, 0, 13);

			} else {
				imageView = (ImageView) convertView;
			}

			imageView.setImageResource(pic_ids[position]);
			return imageView;
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_back:
			finish();
			break;

		default:
			break;
		}
	}

}