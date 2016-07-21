package com.yinghe.wifitest.client.activity;

import java.util.ArrayList;
import java.util.List;

import com.example.wifitest01.R;
import com.example.wifitest01.R.color;
import com.yinghe.wifitest.client.adapter.ViewPagerAdapter;
import com.yinghe.wifitest.client.entity.MsgTag;
import com.yinghe.wifitest.client.utils.LocationUtil;
import com.yinghe.wifitest.client.view.PageConnectView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainFragmentActivity extends Activity implements View.OnClickListener {

	private static final String TAG_TITLE_CONNECT = "title_connect";
	private static final String TAG_TITLE_FIND = "title_find";
	private static final String TAG_TITLE_USER = "title_USER";

	private View connectView;
	private View findView;
	private View userView;

	private List<View> views = new ArrayList<>();;
	private ViewPagerAdapter adapter;

	private ViewPager viewPager;
	private ImageView imgTitleConnect;
	private ImageView imgTitleFind;
	private ImageView imgTitleUsert;

	private TextView textTitleConnect;
	private TextView textTitleFind;
	private TextView textTitleUser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fragment);

		connectView = getLayoutInflater().inflate(R.layout.fragment_connect, null);
		findView = getLayoutInflater().inflate(R.layout.activity_main2, null);
		userView = getLayoutInflater().inflate(R.layout.activity_main3, null);

		PageConnectView.init(connectView, this);

		initViewPage();
		initTitle();

	}

	@SuppressLint("InflateParams")
	private void initViewPage() {

		views.add(connectView);
		views.add(findView);
		views.add(userView);

		viewPager = (ViewPager) findViewById(R.id.vp_fra);
		adapter = new ViewPagerAdapter(views);
		viewPager.setAdapter(adapter);
		viewPager.setOnPageChangeListener(new PageChangeListener());
	}

	private void initTitle() {
		imgTitleConnect = (ImageView) findViewById(R.id.image_Title_connect);
		imgTitleFind = (ImageView) findViewById(R.id.image_Title_find);
		imgTitleUsert = (ImageView) findViewById(R.id.image_Title_user);
		textTitleConnect = (TextView) findViewById(R.id.text_Title_connect);
		textTitleFind = (TextView) findViewById(R.id.text_Title_find);
		textTitleUser = (TextView) findViewById(R.id.text_Title_user);

		imgTitleConnect.setTag(TAG_TITLE_CONNECT);
		imgTitleFind.setTag(TAG_TITLE_FIND);
		imgTitleUsert.setTag(TAG_TITLE_USER);
		textTitleConnect.setTag(TAG_TITLE_CONNECT);
		textTitleFind.setTag(TAG_TITLE_FIND);
		textTitleUser.setTag(TAG_TITLE_USER);

		imgTitleConnect.setOnClickListener(this);
		imgTitleFind.setOnClickListener(this);
		imgTitleUsert.setOnClickListener(this);
		textTitleConnect.setOnClickListener(this);
		textTitleFind.setOnClickListener(this);
		textTitleUser.setOnClickListener(this);

		restartTitle();
		imgTitleConnect.setEnabled(false);
		textTitleConnect.setTextColor(getResources().getColor(color.lightcoral));
	}

	private void restartTitle() {
		imgTitleConnect.setEnabled(true);
		imgTitleFind.setEnabled(true);
		imgTitleUsert.setEnabled(true);
		textTitleConnect.setTextColor(color.white);
		textTitleFind.setTextColor(color.white);
		textTitleUser.setTextColor(color.white);
	}

	private class PageChangeListener implements ViewPager.OnPageChangeListener {
		@Override
		public void onPageScrollStateChanged(int position) {
		}

		@Override
		public void onPageScrolled(int position, float arg1, int arg2) {
		}

		@Override
		public void onPageSelected(int position) {
			setTitleState(position);
		}

	}

	public void setTitleState(int position) {
		restartTitle();
		switch (position) {
		case 0:
			imgTitleConnect.setEnabled(false);
			textTitleConnect.setTextColor(getResources().getColor(color.lightcoral));
			break;
		case 1:
			imgTitleFind.setEnabled(false);
			textTitleFind.setTextColor(getResources().getColor(color.lightcoral));
			break;
		case 2:
			imgTitleUsert.setEnabled(false);
			textTitleUser.setTextColor(getResources().getColor(color.lightcoral));
			break;
		default:
			break;
		}

	}

	@Override
	public void onClick(View view) {
		switch (view.getTag().toString()) {
		case TAG_TITLE_CONNECT:
			viewPager.setCurrentItem(0);
			break;
		case TAG_TITLE_FIND:
			viewPager.setCurrentItem(1);
			break;
		case TAG_TITLE_USER:
			viewPager.setCurrentItem(2);
			break;
		default:
			break;
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == MsgTag.Request_GetWeather && resultCode == MsgTag.Result_GetWeatherSuccess) {
			System.out.println("000");
			PageConnectView.updateWeatherPanel();
		}
	}
}
