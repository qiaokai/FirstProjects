package com.yinghe.wifitest.client.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.example.wifitest01.R;
import com.yinghe.wifitest.client.entity.MsgTag;
import com.yinghe.wifitest.client.manager.EquipmentManager;
import com.yinghe.wifitest.client.utils.WeatherUtil;

public class EquipmentSetActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_equipment);

		EquipmentManager.searchEquipment(handler);
		// EquipmentManager.buildTcpConnect(response);
	}

	static Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == MsgTag.Msg_GetPosition && msg.arg1 == MsgTag.success) {
				WeatherUtil.upDateWeatherInfo(handler);
			}
			if (msg.what == MsgTag.Msg_GetWeather && msg.arg1 == MsgTag.success) {
			}

		}

	};
}
