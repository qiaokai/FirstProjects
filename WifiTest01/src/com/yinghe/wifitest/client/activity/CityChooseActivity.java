package com.yinghe.wifitest.client.activity;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wifitest01.R;
import com.yinghe.wifitest.client.adapter.CityListAdapter;
import com.yinghe.wifitest.client.adapter.HotCityAdapter;
import com.yinghe.wifitest.client.entity.HotCityInfo;
import com.yinghe.wifitest.client.entity.LocationInfo;
import com.yinghe.wifitest.client.entity.MsgTag;
import com.yinghe.wifitest.client.entity.SupportCityInfo;
import com.yinghe.wifitest.client.utils.WeatherUtil;

public class CityChooseActivity extends Activity implements OnClickListener {
	private Activity mActivity = this;
	private static final String TAG_INPUT = "input";
	private static final String TAG_SEARCH = "search";
	private static final String TAG_BACK = "back";

	private TextView margin_top;
	private TextView margin_center;
	private EditText input_Location;
	private ImageView imageBtn;

	private GridView grid_HotCity;
	private RelativeLayout panel_HotCity;

	private ListView list_CityList;

	private ArrayList<String> cityListInfo = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_citychoose);

		margin_top = (TextView) findViewById(R.id.margin_cityChoose_top);
		margin_center = (TextView) findViewById(R.id.margin_cityChoose_center);
		panel_HotCity = (RelativeLayout) findViewById(R.id.panel_hotCity);

		initInputPanel();
		initHotCityPanel();
		initCityListPanel();

	}

	private void initInputPanel() {
		input_Location = (EditText) findViewById(R.id.editText_Location);
		imageBtn = (ImageView) findViewById(R.id.image_search);
		input_Location.setTag(TAG_INPUT);
		imageBtn.setTag(TAG_SEARCH);
		input_Location.setOnClickListener(this);
		imageBtn.setOnClickListener(this);

		input_Location.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				upDateCityList(String.valueOf(s));
				if (s.length() > 0) {
					hidePanel();
				} else {
					showPanel();
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});
	}

	private void upDateCityList(String str) {
		cityListInfo.clear();
		for (String temp : SupportCityInfo.Instance()) {
			if (temp.indexOf(str) > -1) {
				cityListInfo.add(temp);
			}
		}
		CityListAdapter adapter = new CityListAdapter(mActivity, cityListInfo);
		list_CityList.setAdapter(adapter);
	}

	/*
	 * 初始化热门城市面板
	 */
	private void initHotCityPanel() {
		grid_HotCity = (GridView) findViewById(R.id.gridView_hotCity);
		final HotCityAdapter adapter = new HotCityAdapter(this);
		grid_HotCity.setAdapter(adapter);
		grid_HotCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				updateWeather(HotCityInfo.Instance().get(position));
			}
		});
	}

	private void initCityListPanel() {
		list_CityList = (ListView) findViewById(R.id.list_CityList);
		CityListAdapter adapter = new CityListAdapter(mActivity, cityListInfo);
		list_CityList.setAdapter(adapter);

		list_CityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				updateWeather(cityListInfo.get(position));
			}
		});
	}

	@Override
	public void onClick(View view) {
		switch (view.getTag().toString()) {
		case TAG_INPUT:
			view.setTag(TAG_BACK);
			hidePanel();
			break;
		case TAG_BACK:
			view.setTag(TAG_INPUT);
			showPanel();
			break;
		case TAG_SEARCH:
			String city = input_Location.getText().toString();
			if (TextUtils.isEmpty(city)) {
				Toast.makeText(mActivity, "请输入要查询的城市", Toast.LENGTH_SHORT).show();
			} else {
				updateWeather(city);
			}
			break;
		default:
			break;
		}

	}

	private void hidePanel() {
		margin_top.setVisibility(View.GONE);
		margin_center.setVisibility(View.GONE);
		panel_HotCity.setVisibility(View.GONE);
	}

	private void showPanel() {
		margin_top.setVisibility(View.VISIBLE);
		margin_center.setVisibility(View.VISIBLE);
		panel_HotCity.setVisibility(View.VISIBLE);
	}

	private void updateWeather(String city) {
		LocationInfo.Instance().setCity(city);
		WeatherUtil.upDateWeatherInfo(mActivity, handler);
	};

	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == MsgTag.Msg_GetWeatherSuccess) {
				setResult(MsgTag.Result_GetWeatherSuccess);
			}
			if (msg.what == MsgTag.Msg_GetWeatherFail) {
				Toast.makeText(mActivity, "获取当前地区天气失败", Toast.LENGTH_SHORT).show();
			}
			finish();
		}
	};

}
