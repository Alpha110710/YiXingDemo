package com.yixing.ui.addressselector;

import java.util.List;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yixing.R;
import com.yixing.biz.HomeBiz;
import com.yixing.biz.exception.BizFailure;
import com.yixing.biz.exception.ZYException;
import com.yixing.biz.task.BizDataAsyncTask;
import com.yixing.model.BaseModel;
import com.yixing.model.City;
import com.yixing.model.County;
import com.yixing.model.Province;

public class AddressSelector implements AdapterView.OnItemClickListener {
	private static final int INDEX_TAB_PROVINCE = 0;
	private static final int INDEX_TAB_CITY = 1;
	private static final int INDEX_TAB_COUNTY = 2;
	private static final int INDEX_TAB_STREET = 3;

	private static final int INDEX_INVALID = -1;

	private static final int WHAT_PROVINCES_SELECTED = 0;
	private static final int WHAT_CITIES_SELECTED = 1;
	private static final int WHAT_COUNTIES_SELECTED = 2;

	@SuppressWarnings("unchecked")
	private Handler handler = new Handler(new Handler.Callback() {
		@Override
		public boolean handleMessage(Message msg) {
			switch (msg.what) {
			case WHAT_PROVINCES_SELECTED:
				provinces = (List<Province>) msg.obj;
				provinceAdapter.notifyDataSetChanged();
				listView.setAdapter(provinceAdapter);

				break;

			case WHAT_CITIES_SELECTED:
				cities = (List<City>) msg.obj;
				cityAdapter.notifyDataSetChanged();
				if (Lists.notEmpty(cities)) {
					// 以次级内容更新列表
					listView.setAdapter(cityAdapter);
					// 更新索引为次级
					tabIndex = INDEX_TAB_CITY;
				} else {
					// 次级无内容，回调
					callbackInternal();
				}

				break;

			case WHAT_COUNTIES_SELECTED:
				counties = (List<County>) msg.obj;
				countyAdapter.notifyDataSetChanged();
				if (Lists.notEmpty(counties)) {
					listView.setAdapter(countyAdapter);
					tabIndex = INDEX_TAB_COUNTY;
				} else {
					callbackInternal();
				}

				break;
			}

			updateTabsVisibility();
			updateProgressVisibility();
			updateIndicator();

			return true;
		}
	});

	private final Context context;
	private final LayoutInflater inflater;
	private OnAddressSelectedListener listener;

	private View view;

	private View indicator;

	private TextView textViewProvince;
	private TextView textViewCity;
	private TextView textViewCounty;

	private ProgressBar progressBar;

	private ListView listView;
	private ProvinceAdapter provinceAdapter;
	private CityAdapter cityAdapter;
	private CountyAdapter countyAdapter;

	private List<Province> provinces;
	private List<City> cities;
	private List<County> counties;

	private int provinceIndex = INDEX_INVALID;
	private int cityIndex = INDEX_INVALID;
	private int countyIndex = INDEX_INVALID;
	private int streetIndex = INDEX_INVALID;

	private int tabIndex = INDEX_TAB_PROVINCE;

	public AddressSelector(Context context) {
		this.context = context;
		inflater = LayoutInflater.from(context);

		// FlowManager.init(new
		// FlowConfig.Builder(context.getApplicationContext()).build());
		// database = openCityDB();

		// AssetsDatabaseManager.initManager(context);
		// 获取管理对象，因为数据库需要通过管理对象才能够获取
		// AssetsDatabaseManager mg = AssetsDatabaseManager.getManager();
		// 通过管理对象获取数据库
		// SQLiteDatabase db = mg.getDatabase(Database.NAME);
		// database = new Database(db);
		initViews();
		initAdapters();
		initProvince();
	}

	private void initAdapters() {
		provinceAdapter = new ProvinceAdapter();
		cityAdapter = new CityAdapter();
		countyAdapter = new CountyAdapter();
	}

	private void initProvince() {
		retrieveProvinces();
	}

	private void updateTabsVisibility() {
		textViewProvince.setVisibility(Lists.notEmpty(provinces) ? View.VISIBLE
				: View.GONE);
		textViewCity.setVisibility(Lists.notEmpty(cities) ? View.VISIBLE
				: View.GONE);
		textViewCounty.setVisibility(Lists.notEmpty(counties) ? View.VISIBLE
				: View.GONE);

		textViewProvince.setEnabled(tabIndex != INDEX_TAB_PROVINCE);
		textViewCity.setEnabled(tabIndex != INDEX_TAB_CITY);
		textViewCounty.setEnabled(tabIndex != INDEX_TAB_COUNTY);
	}

	private void initViews() {
		view = inflater.inflate(R.layout.address_selector, null);

		this.progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

		this.listView = (ListView) view.findViewById(R.id.listView);
		this.indicator = view.findViewById(R.id.indicator);

		this.textViewProvince = (TextView) view
				.findViewById(R.id.textViewProvince);
		this.textViewCity = (TextView) view.findViewById(R.id.textViewCity);
		this.textViewCounty = (TextView) view.findViewById(R.id.textViewCounty);

		this.textViewProvince
				.setOnClickListener(new OnProvinceTabClickListener());
		this.textViewCity.setOnClickListener(new OnCityTabClickListener());
		this.textViewCounty.setOnClickListener(new onCountyTabClickListener());

		this.listView.setOnItemClickListener(this);

		updateIndicator();
	}

	private void updateIndicator() {
		view.post(new Runnable() {
			@SuppressLint("NewApi")
			@Override
			public void run() {
				switch (tabIndex) {
				case INDEX_TAB_PROVINCE:
					buildIndicatorAnimatorTowards(textViewProvince).start();
					break;
				case INDEX_TAB_CITY:
					buildIndicatorAnimatorTowards(textViewCity).start();
					break;
				case INDEX_TAB_COUNTY:
					buildIndicatorAnimatorTowards(textViewCounty).start();
					break;
				case INDEX_TAB_STREET:
					break;
				}
			}
		});
	}

	@SuppressLint("NewApi")
	private AnimatorSet buildIndicatorAnimatorTowards(TextView tab) {
		ObjectAnimator xAnimator = ObjectAnimator.ofFloat(indicator, "X",
				indicator.getX(), tab.getX());

		final ViewGroup.LayoutParams params = indicator.getLayoutParams();
		ValueAnimator widthAnimator = ValueAnimator.ofInt(params.width,
				tab.getMeasuredWidth());
		widthAnimator
				.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
					@Override
					public void onAnimationUpdate(ValueAnimator animation) {
						params.width = ((Integer) animation.getAnimatedValue())
								.intValue();
						indicator.setLayoutParams(params);
					}
				});

		AnimatorSet set = new AnimatorSet();
		set.setInterpolator(new FastOutSlowInInterpolator());
		set.playTogether(xAnimator, widthAnimator);

		return set;
	}

	class OnProvinceTabClickListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			tabIndex = INDEX_TAB_PROVINCE;
			listView.setAdapter(provinceAdapter);

			if (provinceIndex != INDEX_INVALID) {
				listView.setSelection(provinceIndex);
			}

			updateTabsVisibility();
			updateIndicator();
		}
	}

	class OnCityTabClickListener implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			tabIndex = INDEX_TAB_CITY;
			listView.setAdapter(cityAdapter);

			if (cityIndex != INDEX_INVALID) {
				listView.setSelection(cityIndex);
			}

			updateTabsVisibility();
			updateIndicator();
		}
	}

	class onCountyTabClickListener implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			tabIndex = INDEX_TAB_COUNTY;
			listView.setAdapter(countyAdapter);

			if (countyIndex != INDEX_INVALID) {
				listView.setSelection(countyIndex);
			}

			updateTabsVisibility();
			updateIndicator();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		switch (tabIndex) {
		case INDEX_TAB_PROVINCE:
			Province province = provinceAdapter.getItem(position);

			// 更新当前级别及子级标签文本
			textViewProvince.setText(province.getName());
			textViewCity.setText("请选择");
			textViewCounty.setText("请选择");

			// 更新选中效果
			provinceAdapter.notifyDataSetChanged();

			retrieveCitiesWith(Integer.parseInt(province.getId()));

			// 更新子级数据
			cities = null;
			counties = null;
			cityAdapter.notifyDataSetChanged();
			countyAdapter.notifyDataSetChanged();

			// 更新选中数据
			this.provinceIndex = position;
			this.cityIndex = INDEX_INVALID;
			this.countyIndex = INDEX_INVALID;
			this.streetIndex = INDEX_INVALID;

			break;

		case INDEX_TAB_CITY:
			City city = cityAdapter.getItem(position);

			textViewCity.setText(city.getName());
			textViewCounty.setText("请选择");

			retrieveCountiesWith(Integer.parseInt(city.getId()));

			counties = null;
			countyAdapter.notifyDataSetChanged();

			this.cityIndex = position;
			this.countyIndex = INDEX_INVALID;
			this.streetIndex = INDEX_INVALID;

			cityAdapter.notifyDataSetChanged();

			break;

		case INDEX_TAB_COUNTY:
			County county = countyAdapter.getItem(position);

			textViewCounty.setText(county.getName());

			// retrieveStreetsWith(county.id);

			this.countyIndex = position;
			this.streetIndex = INDEX_INVALID;

			countyAdapter.notifyDataSetChanged();
			callbackInternal();
			break;

		// case INDEX_TAB_STREET:
		// Street street = streetAdapter.getItem(position);
		//
		// textViewStreet.setText(street.name);
		//
		// streetAdapter.notifyDataSetChanged();
		//
		// this.streetIndex = position;
		//
		// callbackInternal();
		// break;
		}

		updateTabsVisibility();
		updateIndicator();
	}

	public View getView() {
		return view;
	}

	private void callbackInternal() {
		if (listener != null) {
			Province province = provinces == null
					|| provinceIndex == INDEX_INVALID ? null : provinces
					.get(provinceIndex);
			City city = cities == null || cityIndex == INDEX_INVALID ? null
					: cities.get(cityIndex);
			County county = counties == null || countyIndex == INDEX_INVALID ? null
					: counties.get(countyIndex);

			listener.onAddressSelected(province, city, county);
		}
	}

	private void updateProgressVisibility() {
		ListAdapter adapter = listView.getAdapter();
		int itemCount = adapter.getCount();
		progressBar.setVisibility(itemCount > 0 ? View.GONE : View.VISIBLE);
	}

	private BizDataAsyncTask<List<Province>> getProvinceList;

	private void retrieveProvinces() {
		progressBar.setVisibility(View.VISIBLE);

		getProvinceList = new BizDataAsyncTask<List<Province>>() {

			@Override
			protected void onExecuteSucceeded(List<Province> result) {
				// TODO Auto-generated method stub

				handler.sendMessage(Message.obtain(handler,
						WHAT_PROVINCES_SELECTED, result));
			}

			@Override
			protected List<Province> doExecute() throws ZYException, BizFailure {
				// TODO Auto-generated method stub
				return HomeBiz.getProvinceList("0");
			}

			@Override
			protected void OnExecuteFailed(String error) {
				// TODO Auto-generated method stub
				progressBar.setVisibility(View.GONE);
			}
		};
		getProvinceList.execute();

		/*
		 * addressProvider.provideProvinces(database,new
		 * AddressProvider.AddressReceiver<Province>() {
		 * 
		 * @Override public void send(List<Province> data) {
		 * handler.sendMessage(Message.obtain(handler, WHAT_PROVINCES_SELECTED,
		 * data)); } });
		 */
	}

	private BizDataAsyncTask<List<City>> getCityList;

	private void retrieveCitiesWith(final int provinceId) {
		progressBar.setVisibility(View.VISIBLE);
		getCityList = new BizDataAsyncTask<List<City>>() {

			@Override
			protected void onExecuteSucceeded(List<City> result) {
				// TODO Auto-generated method stub
				handler.sendMessage(Message.obtain(handler,
						WHAT_CITIES_SELECTED, result));

			}

			@Override
			protected void OnExecuteFailed(String error) {
				// TODO Auto-generated method stub
				progressBar.setVisibility(View.GONE);
			}

			@Override
			protected List<City> doExecute() throws ZYException, BizFailure {
				// TODO Auto-generated method stub
				return HomeBiz.getCityList(String.valueOf(provinceId),"0");
			}

		};

		getCityList.execute();
		/*
		 * addressProvider.provideCitiesWith(database,provinceId, new
		 * AddressProvider.AddressReceiver<City>() {
		 * 
		 * @Override public void send(List<City> data) {
		 * handler.sendMessage(Message.obtain(handler, WHAT_CITIES_SELECTED,
		 * data)); } });
		 */
	}

	private BizDataAsyncTask<List<County>> getCountyList;

	private void retrieveCountiesWith(final int cityId) {
		progressBar.setVisibility(View.VISIBLE);

		getCountyList = new BizDataAsyncTask<List<County>>() {

			@Override
			protected void onExecuteSucceeded(List<County> result) {
				// TODO Auto-generated method stub
				handler.sendMessage(Message.obtain(handler,
						WHAT_COUNTIES_SELECTED, result));

			}

			@Override
			protected void OnExecuteFailed(String error) {
				// TODO Auto-generated method stub
				progressBar.setVisibility(View.GONE);
			}

			@Override
			protected List<County> doExecute() throws ZYException, BizFailure {
				// TODO Auto-generated method stub
				return HomeBiz.getCountyList(String.valueOf(cityId));
			}

		};

		getCountyList.execute();
		/*
		 * addressProvider.provideCountiesWith(database,cityId, new
		 * AddressProvider.AddressReceiver<County>() {
		 * 
		 * @Override public void send(List<County> data) {
		 * handler.sendMessage(Message.obtain(handler, WHAT_COUNTIES_SELECTED,
		 * data)); } });
		 */}

	class ProvinceAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return provinces == null ? 0 : provinces.size();
		}

		@Override
		public Province getItem(int position) {
			return provinces.get(position);
		}

		@Override
		public long getItemId(int position) {
			return Long.parseLong(getItem(position).getId());
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Holder holder;

			if (convertView == null) {
				convertView = LayoutInflater.from(parent.getContext()).inflate(
						R.layout.item_area, parent, false);

				holder = new Holder();
				holder.textView = (TextView) convertView
						.findViewById(R.id.textView);
				holder.imageViewCheckMark = (ImageView) convertView
						.findViewById(R.id.imageViewCheckMark);

				convertView.setTag(holder);
			} else {
				holder = (Holder) convertView.getTag();
			}

			Province item = getItem(position);
			holder.textView.setText(item.getName());

			boolean checked = provinceIndex != INDEX_INVALID
					&& provinces.get(provinceIndex).getId() == item.getId();
			holder.textView.setEnabled(!checked);
			holder.imageViewCheckMark.setVisibility(checked ? View.VISIBLE
					: View.GONE);

			return convertView;
		}

		class Holder {
			TextView textView;
			ImageView imageViewCheckMark;
		}
	}

	class CityAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return cities == null ? 0 : cities.size();
		}

		@Override
		public City getItem(int position) {
			return cities.get(position);
		}

		@Override
		public long getItemId(int position) {
			return Long.parseLong(getItem(position).getId());
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Holder holder;

			if (convertView == null) {
				convertView = LayoutInflater.from(parent.getContext()).inflate(
						R.layout.item_area, parent, false);

				holder = new Holder();
				holder.textView = (TextView) convertView
						.findViewById(R.id.textView);
				holder.imageViewCheckMark = (ImageView) convertView
						.findViewById(R.id.imageViewCheckMark);

				convertView.setTag(holder);
			} else {
				holder = (Holder) convertView.getTag();
			}

			City item = getItem(position);
			holder.textView.setText(item.getName());

			boolean checked = cityIndex != INDEX_INVALID
					&& cities.get(cityIndex).getId() == item.getId();
			holder.textView.setEnabled(!checked);
			holder.imageViewCheckMark.setVisibility(checked ? View.VISIBLE
					: View.GONE);

			return convertView;
		}

		class Holder {
			TextView textView;
			ImageView imageViewCheckMark;
		}
	}

	class CountyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return counties == null ? 0 : counties.size();
		}

		@Override
		public County getItem(int position) {
			return counties.get(position);
		}

		@Override
		public long getItemId(int position) {
			return Long.parseLong(getItem(position).getId());
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Holder holder;

			if (convertView == null) {
				convertView = LayoutInflater.from(parent.getContext()).inflate(
						R.layout.item_area, parent, false);

				holder = new Holder();
				holder.textView = (TextView) convertView
						.findViewById(R.id.textView);
				holder.imageViewCheckMark = (ImageView) convertView
						.findViewById(R.id.imageViewCheckMark);

				convertView.setTag(holder);
			} else {
				holder = (Holder) convertView.getTag();
			}

			County item = getItem(position);
			holder.textView.setText(item.getName());

			boolean checked = countyIndex != INDEX_INVALID
					&& counties.get(countyIndex).getId() == item.getId();
			holder.textView.setEnabled(!checked);
			holder.imageViewCheckMark.setVisibility(checked ? View.VISIBLE
					: View.GONE);

			return convertView;
		}

		class Holder {
			TextView textView;
			ImageView imageViewCheckMark;
		}
	}

	public OnAddressSelectedListener getOnAddressSelectedListener() {
		return listener;
	}

	public void setOnAddressSelectedListener(OnAddressSelectedListener listener) {
		this.listener = listener;
	}

}
