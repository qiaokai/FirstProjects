package com.yinghe.wifitest.client.view;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wifitest01.R;
import com.yinghe.wifitest.client.entity.ConstantEntity;

public class UserSettingView {
	private static Activity mActivity;

	public static void init(View view, Activity activity) {
		mActivity = activity;
		final EditText serverIp = (EditText) view.findViewById(R.id.edit_serverIp);
		serverIp.setText(ConstantEntity.serverIp);

		Button save = (Button) view.findViewById(R.id.button_saveServerIp);
		save.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!TextUtils.isEmpty(serverIp.getText().toString())) {
					saveServerIp(serverIp.getText().toString());
					ConstantEntity.serverIp = serverIp.getText().toString();
					Toast.makeText(mActivity, "保存成功", Toast.LENGTH_LONG).show();
				}
			}
		});
	}

	public static void saveServerIp(String serverIp) {
		SharedPreferences preferences = mActivity.getSharedPreferences("IFENG_SP", Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putString("serverIp", serverIp);
		editor.commit();

	}
}
