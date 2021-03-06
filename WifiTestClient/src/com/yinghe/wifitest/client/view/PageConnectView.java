package com.yinghe.wifitest.client.view;

import java.util.ArrayList;

import com.example.wifitest01.R;
import com.yinghe.wifitest.client.activity.CityChooseActivity;
import com.yinghe.wifitest.client.activity.EquipmentInfoActivity;
import com.yinghe.wifitest.client.activity.EquipmentSetActivity;
import com.yinghe.wifitest.client.activity.ScanActivity;
import com.yinghe.wifitest.client.adapter.EquipmentListAdapter;
import com.yinghe.wifitest.client.entity.EquipmentInfo;
import com.yinghe.wifitest.client.entity.EquipmentList;
import com.yinghe.wifitest.client.entity.LocationInfo;
import com.yinghe.wifitest.client.entity.MsgTag;
import com.yinghe.wifitest.client.entity.WeatherInfo;
import com.yinghe.wifitest.client.manager.EquipmentManager;
import com.yinghe.wifitest.client.utils.LocationUtil;
import com.yinghe.wifitest.client.utils.WeatherUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class PageConnectView {
	private static Activity mActivity;

	private static TextView text_City;
	private static TextView text_weather;
	private static TextView text_tem_low;
	private static TextView text_tem_high;
	private static TextView text_tem_now;

	private static ImageView img_location;
	private static ImageView img_AddEquipment;
	private static ImageView img_Weather;

	private static TextView text_EnterScenes;
	private static ListView list_Equipment;

	public static void init(View view, final Activity activity) {
		initWeatherInfo(activity, view);
		mActivity = activity;

		img_AddEquipment = (ImageView) view.findViewById(R.id.imageView_add_equipment);
		img_AddEquipment.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				addEquipment();
			}
		});

		EquipmentManager.parseEquipmentList(mActivity);
		list_Equipment = (ListView) view.findViewById(R.id.list_Equipment);
		ArrayList<EquipmentInfo> list = EquipmentList.Instance();
		EquipmentListAdapter adapter = new EquipmentListAdapter(mActivity, list);
		list_Equipment.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		list_Equipment.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(mActivity, EquipmentInfoActivity.class);
				intent.putExtra("posion", position);
				mActivity.startActivity(intent);

			}
		});

		text_EnterScenes = (TextView) view.findViewById(R.id.Text_enter_scenes);
		text_EnterScenes.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mActivity.startActivity(new Intent(mActivity, EquipmentSetActivity.class));
			}
		});

	}

	protected static void addEquipment() {
		mActivity.startActivityForResult(new Intent(mActivity, ScanActivity.class), MsgTag.Msg_GetScanInfoSuccess);

	}

	private static void initWeatherInfo(final Activity activity, View view) {
		text_City = (TextView) view.findViewById(R.id.text_city);
		img_location = (ImageView) view.findViewById(R.id.imageView_Location);
		text_weather = (TextView) view.findViewById(R.id.text_Weather);
		img_Weather = (ImageView) view.findViewById(R.id.imageView_weather);
		text_tem_low = (TextView) view.findViewById(R.id.text_tem_low);
		text_tem_high = (TextView) view.findViewById(R.id.text_tem_high);
		text_tem_now = (TextView) view.findViewById(R.id.text_temperature_current);

		if (TextUtils.isEmpty(LocationInfo.Instance().getCity())) {
			LocationUtil.getLocationCity(activity, handler);
		} else {
			WeatherUtil.upDateWeatherInfo(handler);
		}

		img_location.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				text_City.setText("获取中");
				LocationUtil.getLocationCity(activity, handler);
			}
		});

		text_City.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				activity.startActivityForResult(new Intent(activity, CityChooseActivity.class), MsgTag.Request_GetWeather);
			}
		});

	}

	public static void updateWeatherPanel() {
		text_City.setText(WeatherInfo.Instance().getLocation());
		text_weather.setText(WeatherInfo.Instance().getWeather());
		text_tem_low.setText(WeatherInfo.Instance().getTem_low());
		text_tem_high.setText(WeatherInfo.Instance().getTem_High());
		text_tem_now.setText(WeatherInfo.Instance().getTem_Now());

		String imgName = "ww" + WeatherInfo.Instance().getImg_Weather();
		img_Weather.setImageResource(mActivity.getResources().getIdentifier(imgName, "drawable", mActivity.getPackageName()));
	}

	static Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == MsgTag.Msg_GetPosition && msg.arg1 == MsgTag.success) {
				WeatherUtil.upDateWeatherInfo(handler);
			}
			if (msg.what == MsgTag.Msg_GetWeather && msg.arg1 == MsgTag.success) {
				updateWeatherPanel();
			}

		}

	};

	public static void updateEquipmentList() {
		// EquipmentListAdapter adapter = new EquipmentListAdapter(mActivity);
		// list_Equipment.setAdapter(adapter);
	}

}
