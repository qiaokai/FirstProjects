package com.yinghe.wifitest.client.entity;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class SupportCityInfo extends ArrayList<String> {

	private static SupportCityInfo Instance;

	private SupportCityInfo() {
	}

	public static SupportCityInfo Instance() {
		if (Instance == null) {
			Instance = new SupportCityInfo();
		}
		return Instance;
	}
}
