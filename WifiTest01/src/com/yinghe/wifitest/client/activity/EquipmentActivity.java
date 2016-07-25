package com.yinghe.wifitest.client.activity;

import com.example.wifitest01.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class EquipmentActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_equipment);

		TextView a = (TextView) findViewById(R.id.text_timer_setting);
		a.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
	}
}
