package com.yinghe.wifitest.client;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.wifitest01.R;
import com.example.wifitest01.R.id;
import com.yinghe.wifitest.client.activity.SplashActivity;
import com.yinghe.wifitest.client.entity.MsgTag;
import com.yinghe.wifitest.client.manager.CourseManager;
import com.yinghe.wifitest.client.utils.LocationUtil;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

	private TextView textView;
	private Button getId;
	private Button upDateWifi;
	private Button testWifi;
	private Button changeType_AP;
	private Button changeType_STA;
	private Button setWifi;
	private Button getV;
	private Button getA;
	private Button getServerIp;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		textView = (TextView) findViewById(R.id.text_ip);
		getId = (Button) findViewById(R.id.button_getId);
		upDateWifi = (Button) findViewById(R.id.Button_upDateWifi);
		testWifi = (Button) findViewById(R.id.Button_TestWifi);
		changeType_AP = (Button) findViewById(R.id.Button_changeType);
		changeType_STA = (Button) findViewById(R.id.Button_changeType_STA);
		setWifi = (Button) findViewById(R.id.Button_setWifi);
		getV = (Button) findViewById(R.id.Button_getV);
		getA = (Button) findViewById(R.id.Button_getA);
		getServerIp = (Button) findViewById(R.id.Button_setWJAP);

		textView.setOnClickListener(this);
		getId.setOnClickListener(this);
		upDateWifi.setOnClickListener(this);
		testWifi.setOnClickListener(this);
		changeType_AP.setOnClickListener(this);
		changeType_STA.setOnClickListener(this);
		setWifi.setOnClickListener(this);
		getV.setOnClickListener(this);
		getA.setOnClickListener(this);
		getServerIp.setOnClickListener(this);
	}

	String ip = "";
	int port = -1;
	String result = "";
	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			System.out.println("OK:" + msg.toString());
			if (msg.what == MsgTag.Msg_GetEquipmentId) {
				try {
					JSONObject temp = (JSONObject) msg.obj;
					ip = temp.getString("ip");
					port = temp.getInt("port");
					result = temp.getString("result");
				} catch (JSONException e) {
					e.printStackTrace();
				}
				// ip = msg.obj.toString().split("&&")[0];
				textView.setText("设备Id:" + result + " IP:" + ip + " port:" + port + "  ");
			} else if (msg.what == MsgTag.Msg_GetEquipmentVoltage && msg.arg1 == MsgTag.success) {
				textView.setText("电压:" + msg.obj.toString());
			} else if (msg.what == MsgTag.Msg_GetEquipmentElectric && msg.arg1 == MsgTag.success) {
				textView.setText("电流:" + msg.obj.toString());
			} else {
				textView.setText(msg.obj.toString());
			}
		}
	};

	@Override
	public void onClick(View v) {
		textView.setText("");
		switch (v.getId()) {
		case R.id.button_getId:
			CourseManager.getEquipmentId("255.255.255.255", 9000, 8080, handler);
			break;
		case R.id.Button_upDateWifi:
			CourseManager.upDateWifi(ip, port, 8080, handler);
			break;
		case R.id.Button_TestWifi:
			CourseManager.tetWifi("192.168.1.108", 8088, handler);
			break;
		case R.id.Button_changeType:
			CourseManager.changeType(ip, 8082, handler);
			break;
		case R.id.Button_changeType_STA:
			CourseManager.changeType_STA("255.255.255.255", 9000, handler);
			break;
		case R.id.Button_setWifi:
			CourseManager.setWifi("255.255.255.255", 9000, 8080, handler);
			break;
		case R.id.Button_getV:
			CourseManager.getVoltage(ip, 8082, handler);
			break;
		case R.id.Button_getA:
			CourseManager.getElectric(ip, 8082, handler);
			break;
		case R.id.Button_setWJAP:
			CourseManager.setWJAP("192.168.1.105", 8082, 8080, handler);
			break;
		default:
			break;
		}

	}
}