package com.yinghe.wifitest.client.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import com.example.wifitest01.R;
import com.yinghe.wifitest.client.entity.MsgTag;
import com.yinghe.wifitest.client.manager.EquipmentManager;
import com.yinghe.wifitest.client.utils.WeatherUtil;

public class EquipmentSetActivity extends Activity {

	TextView searchEquipment;
	TextView checkTCPConnect;
	TextView buildTCPConnect;
	TextView testTCPConnect;
	TextView waitConnectSuccess;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_equipment_setting);

		searchEquipment = (TextView) findViewById(R.id.text_searchEquipment);
		checkTCPConnect = (TextView) findViewById(R.id.text_checkTCPConnect);
		buildTCPConnect = (TextView) findViewById(R.id.text_buildTCPConnect);
		testTCPConnect = (TextView) findViewById(R.id.text_testTCPConnect);

		waitConnectSuccess = (TextView) findViewById(R.id.text_waitConnectSuccess);
		EquipmentManager.searchEquipment(handler);
		searchEquipment.setSelected(true);
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case MsgTag.GetEquipmentIp:
				if (msg.arg1 == MsgTag.success) {
					checkTCPConnect.setSelected(true);
					EquipmentManager.checkTCPConnect(handler);
				} else {
					Toast.makeText(getApplicationContext(), "GetEquipmentIp fail", Toast.LENGTH_SHORT).show();
				}

				break;
			case MsgTag.buildTCPConnect:
				if (msg.arg1 == MsgTag.success) {
					testTCPConnect.setSelected(true);
					ArrayList<String> IpList = (ArrayList<String>) msg.obj;
//					EquipmentManager.checkTCPConnect(IpList, handler);
				} else {
					Toast.makeText(getApplicationContext(), "buildTCPConnect fail", Toast.LENGTH_SHORT).show();
				}

				break;
			// case MsgTag.:
			// if (msg.arg1 == MsgTag.success) {
			// testTCPConnect.setSelected(true);
			// ArrayList<String> IpList = (ArrayList<String>) msg.obj;
			// EquipmentManager.testTCPConnect(IpList, handler);
			// } else {
			// Toast.makeText(getApplicationContext(), "buildTCPConnect fail",
			// Toast.LENGTH_SHORT).show();
			// }
			//
			// break;

			default:
				System.out.println(msg.obj.toString());
				Toast.makeText(getApplicationContext(), msg.obj.toString(), Toast.LENGTH_SHORT).show();
				break;
			}

		}

	};
}
