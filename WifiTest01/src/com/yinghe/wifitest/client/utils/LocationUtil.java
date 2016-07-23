package com.yinghe.wifitest.client.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.provider.Settings;
import android.text.TextUtils;

import com.yinghe.wifitest.client.entity.LocationInfo;
import com.yinghe.wifitest.client.entity.MsgTag;
import com.yinghe.wifitest.client.entity.SupportCityInfo;

public class LocationUtil {
	private static String baseUrlStr = "http://maps.google.cn/maps/api/geocode/json?latlng=latitude,longitude&sensor=true&language=zh-CN";
	private static final String FILE_NAME_SUPPORT_CITY = "supportCity.txt";

	public static void initLocation(Activity activity, Handler handler) {
		getLocationCity(activity, handler);
		getSupportCity(activity);
	}

	public static void getLocationCity(Activity activity, final Handler handler) {
		if (getPositionSuccess(activity)) {
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					getLocationCity(handler);
				}
			}, 0);
		} else {
			System.out.println("获取坐标失败");
		}
	}

	private static void getLocationCity(Handler handler) {
		boolean succcess = false;
		try {
			String urlStr = baseUrlStr.replace("latitude", LocationInfo.Instance().getLatitude());
			urlStr = urlStr.replace("longitude", LocationInfo.Instance().getLongitude());
			URL url = new URL(urlStr);
			URLConnection urlConnection = url.openConnection();
			urlConnection.connect();
			InputStream inputStream = urlConnection.getInputStream();

			StringBuffer bufer = new StringBuffer();
			byte[] b = new byte[1024];
			for (int n; (n = inputStream.read(b)) != -1;) {
				bufer.append(new String(b, 0, n));
			}
			inputStream.close();

			JSONObject jsonObject = new JSONObject(bufer.toString());
			JSONArray address_components = (JSONArray) jsonObject.get("results");

			JSONObject address_component = address_components.getJSONObject(0);
			JSONArray locations = address_component.getJSONArray("address_components");

			String city = "";
			for (int i = 0; i < locations.length(); i++) {
				JSONObject info = locations.getJSONObject(i);
				JSONArray types = info.getJSONArray("types");
				for (int j = 0; j < types.length(); j++) {
					if ("locality".equals(types.get(j))) {
						city = info.getString("long_name");
						break;
					}
				}
			}
			if (!TextUtils.isEmpty(city)) {
				city = city.replaceAll("市", "");
				LocationInfo.Instance().setCity(city);
				succcess = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (handler != null && succcess) {
			handler.sendEmptyMessage(MsgTag.Msg_GetPositionSuccess);
		} else if (handler != null) {
			handler.sendEmptyMessage(MsgTag.Msg_GetPositionFail);
		}
	}

	@SuppressWarnings("deprecation")
	private static boolean getPositionSuccess(Activity activity) {
		LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		criteria.setCostAllowed(false); // 设置位置服务免费
		criteria.setAccuracy(Criteria.ACCURACY_COARSE); // 设置水平位置精度
		List<String> providers = locationManager.getAllProviders();
		String providerName = locationManager.getBestProvider(criteria, true);

		if ("gps".equals(providerName) && providers.contains(providerName)) {
			if (!Settings.Secure.isLocationProviderEnabled(activity.getContentResolver(), LocationManager.GPS_PROVIDER)) {
				Settings.Secure.setLocationProviderEnabled(activity.getContentResolver(), LocationManager.GPS_PROVIDER, true);
			}
		}
		if (providerName != null) {
			providers.remove(providerName);
			Location location = null;
			location = locationManager.getLastKnownLocation(providerName);
			while (location == null && providers.size() > 0) {
				providerName = providers.get(0);
				location = locationManager.getLastKnownLocation(providerName);
				providers.remove(0);
			}
			if (location != null) {
				double latitude = location.getLatitude();// 获取维度信息
				double longitude = location.getLongitude();// 获取经度信息

				LocationInfo.Instance().setLatitude(latitude);
				LocationInfo.Instance().setLongitude(longitude);
				return true;
			}
		}
		return false;
	}

	public static void getSupportCity(final Context context) {
		try {
			InputStream cityList = context.getAssets().open(FILE_NAME_SUPPORT_CITY);
			BufferedReader reader = new BufferedReader(new InputStreamReader(cityList));
			String tempcity;
			while ((tempcity = reader.readLine()) != null) {
				if (!TextUtils.isEmpty(tempcity)) {
					SupportCityInfo.Instance().add(tempcity);
				}
			}
			cityList.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
