package com.yinghe.wifitest.client;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.example.wifitest01.R;
import com.yinghe.wifitest.client.activity.SplashActivity;
import com.yinghe.wifitest.client.adapter.EquipmentListAdapter;
import com.yinghe.wifitest.client.entity.HotCityInfo;
import com.yinghe.wifitest.client.utils.LocationUtil;

public class MainActivity extends Activity {

	private Activity activity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_main);
		this.activity = this;
		LocationUtil.initLocation(activity, null);

//		ListView listView = (ListView) findViewById(R.id.myListView1);
//		EquipmentListAdapter adapter = new EquipmentListAdapter(activity, HotCityInfo.Instance());
//		listView.setAdapter(adapter);

		startActivity(new Intent(getApplicationContext(), SplashActivity.class));
		// finish();
		// BaiduLotion.initLockPst(getApplicationContext(), null);

	}
}