package com.yixing.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.yixing.utils.android.QuickSetParcelableUtil;

/**
 * @Author GL
 * @Description 城市
 * @Date 2014-8-12
 */
public class City extends BaseModel implements Parcelable {

	@SerializedName("value")
	private String id;

	@SerializedName("name")
	private String name;

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		QuickSetParcelableUtil.write(dest, this);
	}

	public static final Parcelable.Creator<City> CREATOR = new Creator<City>() {

		@Override
		public City createFromParcel(Parcel source) {
			City obj = (City) QuickSetParcelableUtil.read(source, City.class);
			return obj;
		}

		@Override
		public City[] newArray(int size) {
			return new City[size];
		}

	};

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
