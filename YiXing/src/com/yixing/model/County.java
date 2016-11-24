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
public class County extends BaseModel implements Parcelable {

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

	public static final Parcelable.Creator<County> CREATOR = new Creator<County>() {

		@Override
		public County createFromParcel(Parcel source) {
			County obj = (County) QuickSetParcelableUtil.read(source, County.class);
			return obj;
		}

		@Override
		public County[] newArray(int size) {
			return new County[size];
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
