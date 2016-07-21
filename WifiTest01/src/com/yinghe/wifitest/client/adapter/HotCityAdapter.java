package com.yinghe.wifitest.client.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.wifitest01.R;
import com.yinghe.wifitest.client.entity.HotCityInfo;

public class HotCityAdapter extends BaseAdapter {
	private Activity mActivity;
	private int currentItem = -1;

	public int getCurrentItem() {
		return currentItem;
	}

	public void setCurrentItem(int currentItem) {
		this.currentItem = currentItem;
	}

	public HotCityAdapter(Activity activity) {
		mActivity = activity;
	}

	@Override
	public int getCount() {
		return HotCityInfo.Instance().size();
	}

	@Override
	public Object getItem(int position) {
		return HotCityInfo.Instance().get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint({ "ViewHolder", "NewApi", "InflateParams" })
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mActivity.getLayoutInflater().inflate(R.layout.item_gridview_hotcity, null);
			holder.text = (TextView) convertView.findViewById(R.id.text_item_grid_hotCity);
			convertView.setTag(holder);// 绑定ViewHolder对象
		} else {
			holder = (ViewHolder) convertView.getTag();// 取出ViewHolder对象
		}
		holder.text.setText(HotCityInfo.Instance().get(position));
		return convertView;
	}

	public final class ViewHolder {
		public TextView text;
	}

}
