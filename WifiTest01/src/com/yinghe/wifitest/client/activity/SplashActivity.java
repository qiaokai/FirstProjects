package com.yinghe.wifitest.client.activity;

import com.example.wifitest01.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("whtopentimes",
				Context.MODE_PRIVATE);
		boolean firstopen = sharedPreferences.getBoolean("firstopen", true);
		if (firstopen) {
			Editor editor = sharedPreferences.edit();// 获取编辑器
			editor.putBoolean("firstopen", false);
			editor.commit();// 提交修改
			startActivity(new Intent(getApplicationContext(), GuideActivity.class));
			finish();
		} else {
			setContentView(R.layout.activity_splash);
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					startActivity(new Intent(getApplicationContext(), MainFragmentActivity.class));
					finish();
				}
			}, 1000);
		}

	}
}
