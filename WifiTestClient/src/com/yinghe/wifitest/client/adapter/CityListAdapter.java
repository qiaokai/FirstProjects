package com.yinghe.wifitest.client.adapter;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.wifitest01.R;

public class CityListAdapter extends BaseAdapter {
	private Activity mActivity;
	private ArrayList<String> data;

	public CityListAdapter(Activity activity, ArrayList<String> cityListInfo) {
		mActivity = activity;
		data = cityListInfo;
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
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
			convertView = mActivity.getLayoutInflater().inflate(R.layout.item_listview_citylist, null);
			holder.text = (TextView) convertView.findViewById(R.id.item_citylist_text);
			convertView.setTag(holder);// 绑定ViewHolder对象
		} else {
			holder = (ViewHolder) convertView.getTag();// 取出ViewHolder对象
		}
		holder.text.setText(data.get(position));
		return convertView;
	}

	public final class ViewHolder {
		public TextView text;

	}

}
