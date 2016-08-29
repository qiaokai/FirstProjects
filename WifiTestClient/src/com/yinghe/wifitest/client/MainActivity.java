package com.yinghe.wifitest.client;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.wifitest01.R;
import com.yinghe.wifitest.client.activity.SplashActivity;
import com.yinghe.wifitest.client.callback.YHLog;
import com.yinghe.wifitest.client.entity.ConstantEntity;

public class MainActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		parseServerIp();
		startActivity(new Intent(getApplicationContext(), SplashActivity.class));
		finish();

	}

	public void parseServerIp() {
		YHLog.i("start to parseEquipmentList. ");
		SharedPreferences preferences = this.getSharedPreferences("IFENG_SP", Context.MODE_PRIVATE);
		String serverIp = preferences.getString("serverIp", "");
		ConstantEntity.serverIp = serverIp;
	}

}