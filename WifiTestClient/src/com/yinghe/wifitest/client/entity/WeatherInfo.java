package com.yinghe.wifitest.client.entity;

public class WeatherInfo {

	private static WeatherInfo Instance;

	private String location = "";
	private String locationName = "";
	private String weather = "";
	private String tem_Now = "";
	private String tem_low = "";
	private String tem_High = "";
	private String img_Weather = "";

	private WeatherInfo() {
	}

	public static WeatherInfo Instance() {
		if (Instance == null) {
			Instance = new WeatherInfo();
		}
		return Instance;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getWeather() {
		return weather;
	}

	public void setWeather(String weather) {
		this.weather = weather;
	}

	public String getTem_Now() {
		return tem_Now;
	}

	public void setTem_Now(String tem_Now) {
		this.tem_Now = tem_Now;
	}

	public String getTem_low() {
		return tem_low;
	}

	public void setTem_low(String tem_low) {
		this.tem_low = tem_low;
	}

	public String getTem_High() {
		return tem_High;
	}

	public void setTem_High(String tem_High) {
		this.tem_High = tem_High;
	}

	public String getImg_Weather() {
		return img_Weather;
	}

	public void setImg_Weather(String img_Weather) {
		this.img_Weather = img_Weather;
	}

}
