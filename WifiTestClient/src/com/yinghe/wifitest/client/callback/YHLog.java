package com.yinghe.wifitest.client.callback;

import java.util.Timer;
import java.util.TimerTask;

import android.util.Log;

public class YHLog {
	public static final String TAG = "YingHe";

	public static void i(final String input) {
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				Log.i("YingHe", input);
			}
		}, 0);
	}

	public static void d(final String input) {
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				Log.d("YingHe", input);
			}
		}, 0);
	}

	// public static void w(String input) {
	// Log.w("YingHe", input);
	// }
	//
	// public static void e(String input) {
	// Log.e("YingHe", input);
	// }

}
