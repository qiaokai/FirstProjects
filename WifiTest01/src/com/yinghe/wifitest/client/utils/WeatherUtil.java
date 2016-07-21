package com.yinghe.wifitest.client.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.os.Handler;
import android.text.TextUtils;

import com.yinghe.wifitest.client.entity.LocationInfo;
import com.yinghe.wifitest.client.entity.MsgTag;
import com.yinghe.wifitest.client.entity.WeatherInfo;

public class WeatherUtil {
	private static final String SERVICES_HOST = "http://flash.weather.com.cn/wmaps/xml/";

	public static void upDateWeatherInfo(Activity activity, Handler handler) {
		String city = LocationInfo.Instance().getCity();
		String cityName = LanguageUtil.getPinyin(activity, city);

		if ("重庆".equals(city)) {
			cityName = "chongqing";
		}
		WeatherInfo.Instance().setLocation(city);
		WeatherInfo.Instance().setLocationName(cityName);
		getWeather(cityName, handler);
	}

	public static void getWeather(final String cityName, final Handler handler) {
		new Timer().schedule(new TimerTask() {

			@Override
			public void run() {
				InputStream inputStream=null;

				try {
					System.out.println(SERVICES_HOST + cityName + ".xml");
					URL url = new URL(SERVICES_HOST + cityName + ".xml");
					URLConnection urlConnection = url.openConnection();
					urlConnection.connect();
					inputStream = urlConnection.getInputStream();

					StringBuffer bufer = new StringBuffer();
					byte[] b = new byte[1024];
					for (int n; (n = inputStream.read(b)) != -1;) {
						bufer.append(new String(b, 0, n));
					}
					String result = bufer.substring(bufer.indexOf("<city") + 5, bufer.indexOf("/>"));

					String[] wearthTemp = result.replaceAll("\"", "").split(" ");
					int tem1 = -500;
					int tem2 = -500;
					boolean getWeatherFail = true;
					for (String temp : wearthTemp) {
						String[] info = temp.split("=");
						if ("state1".equals(info[0]) && !TextUtils.isEmpty(info[1])) {
							WeatherInfo.Instance().setImg_Weather(info[1]);
						}
						if ("tem1".equals(info[0]) && !TextUtils.isEmpty(info[1])) {
							WeatherInfo.Instance().setTem_low(info[1]);
							tem1 = Integer.parseInt(info[1]);
						}
						if ("tem2".equals(info[0]) && !TextUtils.isEmpty(info[1])) {
							WeatherInfo.Instance().setTem_low(info[1]);
							tem2 = Integer.parseInt(info[1]);
						}
						if ("temNow".equals(info[0]) && !TextUtils.isEmpty(info[1])) {
							WeatherInfo.Instance().setTem_Now(info[1] + "°");
							getWeatherFail = TextUtils.isEmpty(info[1]);
						}
						if ("stateDetailed".equals(info[0]) && !TextUtils.isEmpty(info[1])) {
							WeatherInfo.Instance().setWeather(info[1]);
						}
					}
					tem1 = tem1 > tem2 ? tem1 : tem2;
					WeatherInfo.Instance().setTem_High(tem1 + "°");
					if (handler != null) {
						if (getWeatherFail) {
							handler.sendEmptyMessage(MsgTag.Msg_GetWeatherFail);
						} else {
							handler.sendEmptyMessage(MsgTag.Msg_GetWeatherSuccess);
						}
					}
					inputStream.close();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						inputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}, 0);
	}
}
