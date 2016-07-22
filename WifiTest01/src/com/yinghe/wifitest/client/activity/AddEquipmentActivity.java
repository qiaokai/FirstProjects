package com.yinghe.wifitest.client.activity;

import com.example.wifitest01.R;
import com.yinghe.wifitest.client.camera.CameraManager;
import com.yinghe.wifitest.client.view.ScanView;

import android.app.Activity;
import android.os.Bundle;

public class AddEquipmentActivity extends Activity {
//	private ScanView scanView;
	private boolean isHasSurface = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_equipment);

//		scanView = (ScanView) findViewById(R.id.scanView);

		CameraManager.init(getApplication());
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (isHasSurface) {
//			initCamera(scanView.getHolder());
		}

	}
}
