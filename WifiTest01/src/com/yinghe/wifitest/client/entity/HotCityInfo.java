package com.yinghe.wifitest.client.entity;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class HotCityInfo extends ArrayList<String> {

	private static HotCityInfo Instance;

	private HotCityInfo() {
	}

	public static HotCityInfo Instance() {
		if (Instance == null) {
			Instance = new HotCityInfo();
			Instance.add("北京");
			Instance.add("天津");
			Instance.add("上海");
			Instance.add("重庆");
			Instance.add("沈阳");
			Instance.add("大连");
			Instance.add("长春");
			Instance.add("哈尔滨");
			Instance.add("郑州");
			Instance.add("武汉");
			Instance.add("长沙");
			Instance.add("广州");
			Instance.add("深圳");
			Instance.add("南京");
			Instance.add("重庆");
		}
		return Instance;
	}
}
