package com.yinghe.wifitest.client.utils;

public class DigitalUtils {

	/**
	 * 字符串转ASCII码字符串
	 * 
	 * @param input
	 * @return
	 */
	public static String StringToAsciiString(String input) {
		String result = "";
		int max = input.length();
		for (int i = 0; i < max; i++) {
			char c = input.charAt(i);
			String b = Integer.toHexString(c) + " ";
			result = result + b;
		}
		return result;
	}

	public static String AsciiStringToString(String input) {
		String result = "";
		String temp[] = input.trim().split(" ");
		for (int i = 0; i < temp.length; i++) {
			int c = Integer.parseInt(temp[i], 16);
			char b = (char) c;
			result = result + b;
		}
		return result;
	}
}
