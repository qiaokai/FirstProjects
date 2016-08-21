package com.yinghe.wifitest.client.adapter;

import java.util.ArrayList;

import com.example.wifitest01.R;
import com.yinghe.wifitest.client.entity.EquipmentInfo;
import com.yinghe.wifitest.client.entity.MsgTag;
import com.yinghe.wifitest.client.manager.EquipmentManager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class EquipmentListAdapter extends BaseAdapter {
	private static final String TAG_IS_OPENED = "isOpen";
	private static final String TAG_IS_CLOSED = "isClosed";
	private static Activity mActivity;
	private static ViewHolder holder;
	ArrayList<EquipmentInfo> list;

	public EquipmentListAdapter(Activity activity, ArrayList<EquipmentInfo> list) {
		mActivity = activity;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint({ "ViewHolder", "NewApi", "InflateParams", "CutPasteId" })
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
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

		final String IP = list.get(position).getIP();
		if (list.get(position).IsOpened()) {
			showOpened(holder);
			EquipmentManager.getEquipmentInfoByMina(IP, MsgTag.openEquipment, handler);
		} else {
			EquipmentManager.getEquipmentInfoByMina(IP, MsgTag.closeEquipment, handler);
		}
		final ImageView img_btn = (ImageView) convertView.findViewById(R.id.img_Equipment_ctrl);
		img_btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (TAG_IS_OPENED.equals(img_btn.getTag())) {
					EquipmentManager.getEquipmentInfoByMina(IP, MsgTag.closeEquipment, handler);
				} else {
					EquipmentManager.getEquipmentInfoByMina(IP, MsgTag.openEquipment, handler);
				}

			}
		});
		return convertView;
	}

	protected static void showClosed(ViewHolder holder) {
		holder.img_btn.setTag(TAG_IS_CLOSED);
		holder.img_state.setEnabled(false);
		holder.text_state.setEnabled(false);
		holder.text_state.setText("关闭中");
		holder.img_btn.setImageResource(mActivity.getResources().getIdentifier("img_btn_is_closed", "drawable", mActivity.getPackageName()));

	}

	protected static void showOpened(ViewHolder holder) {
		holder.img_btn.setTag(TAG_IS_OPENED);
		holder.img_state.setEnabled(true);
		holder.text_state.setEnabled(true);
		holder.text_state.setText("已开启");
		holder.img_btn.setImageResource(mActivity.getResources().getIdentifier("img_btn_is_opened", "drawable", mActivity.getPackageName()));

	}

	static Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == MsgTag.openEquipment && msg.arg1 == MsgTag.success) {
				showOpened(holder);
			} else if (msg.what == MsgTag.closeEquipment && msg.arg1 == MsgTag.success) {
				showClosed(holder);
			}
		}
	};

	public final class ViewHolder {
		public ImageView img_state;
		public TextView text_state;
		public ImageView img_btn;
		public TextView text;

	}
}
