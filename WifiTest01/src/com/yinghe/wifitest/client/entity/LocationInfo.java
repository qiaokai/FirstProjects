package com.yinghe.wifitest.client.entity;

public class LocationInfo {

	private static LocationInfo Instance;

	private String province = "";
	private String city = "";
	private String district = "";
	private String latitude;
	private String longitude;

	private LocationInfo() {
	}

	public static LocationInfo Instance() {
		if (Instance == null) {
			Instance = new LocationInfo();
		}
		return Instance;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = String.valueOf(latitude);
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = String.valueOf(longitude);
	}

}
