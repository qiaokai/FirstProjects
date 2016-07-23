package com.yinghe.wifitest.client.adapter;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wifitest01.R;
import com.yinghe.wifitest.client.utils.EquipmentUtil;

public class EquipmentListAdapter extends BaseAdapter {
	private static final String TAG_IS_OPENED = "isOpen";
	private static final String TAG_IS_CLOSED = "isClosed";
	private Activity mActivity;
	private ArrayList<String> data;

	public EquipmentListAdapter(Activity activity, ArrayList<String> cityListInfo) {
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
		final ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mActivity.getLayoutInflater().inflate(R.layout.item_listview_equipment, null);

			holder.img_state = (ImageView) convertView.findViewById(R.id.img_Equipment_state);
			holder.text_state = (TextView) convertView.findViewById(R.id.text_Equipment_state);

			holder.img_btn = (ImageView) convertView.findViewById(R.id.img_Equipment_ctrl);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.img_state.setEnabled(false);
		holder.text_state.setEnabled(false);
		// holder.text_state.setText("");
		// TextView textView = (TextView)
		// convertView.findViewById(R.id.textView1);
		final ImageView img_btn = (ImageView) convertView.findViewById(R.id.img_Equipment_ctrl);
		img_btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (TAG_IS_OPENED.equals(img_btn.getTag())) {
					if (EquipmentUtil.isClosed()) {
						showClosed(holder);
					}
				} else {
					if (EquipmentUtil.isOpened()) {
						showOpened(holder);
					}
				}

			}
		});
		// textView.setText(data.get(position));
		return convertView;
	}

	protected void showClosed(ViewHolder holder) {
		holder.img_btn.setTag(TAG_IS_CLOSED);
		holder.img_state.setEnabled(false);
		holder.text_state.setEnabled(false);
		holder.text_state.setText("关闭中");
		holder.img_btn.setImageResource(mActivity.getResources().getIdentifier("img_btn_is_closed", "drawable", mActivity.getPackageName()));

	}

	protected void showOpened(ViewHolder holder) {
		holder.img_btn.setTag(TAG_IS_OPENED);
		holder.img_state.setEnabled(true);
		holder.text_state.setEnabled(true);
		holder.text_state.setText("已开启");
		holder.img_btn.setImageResource(mActivity.getResources().getIdentifier("img_btn_is_opened", "drawable", mActivity.getPackageName()));

	}

	public final class ViewHolder {
		public ImageView img_state;
		public TextView text_state;
		public ImageView img_btn;
		public TextView text;

	}
}
