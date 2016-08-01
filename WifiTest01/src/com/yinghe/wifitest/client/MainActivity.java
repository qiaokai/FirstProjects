package com.yinghe.wifitest.client;

import com.example.wifitest01.R;
import com.yinghe.wifitest.client.manager.CourseManager;

import android.annotation.SuppressLint;
import android.app.Activity;
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
	private Button changeType;
	private Button setWifi;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		textView = (TextView) findViewById(R.id.text_ip);
		getId = (Button) findViewById(R.id.button_getId);
		upDateWifi = (Button) findViewById(R.id.Button_upDateWifi);
		testWifi = (Button) findViewById(R.id.Button_TestWifi);
		changeType = (Button) findViewById(R.id.Button_changeType);
		setWifi = (Button) findViewById(R.id.Button_setWifi);

		textView.setOnClickListener(this);
		getId.setOnClickListener(this);
		upDateWifi.setOnClickListener(this);
		testWifi.setOnClickListener(this);
		changeType.setOnClickListener(this);
		setWifi.setOnClickListener(this);
	}

	String ip = "192.168.1.105";
	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			String[] temp = (String[]) msg.obj;
			textView.setText(temp[0] + ":" + temp[1]);

			// textView.setText(DigitalUtils.StringToAsciiString(temp));
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
			CourseManager.upDateWifi(ip, 9000, 8082, handler);
			break;
		case R.id.Button_TestWifi:
			CourseManager.tetWifi(ip, 8082, handler);
			break;
		case R.id.Button_changeType:
			CourseManager.changeType("255.255.255.255", 9000, 8080, handler);
			break;
		case R.id.Button_setWifi:
			CourseManager.setWifi("255.255.255.255", 9000, 8080, handler);
			break;
		default:
			break;
		}

	}
}