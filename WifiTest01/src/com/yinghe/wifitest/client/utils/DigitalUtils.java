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

	/**
	 * 字符串转ASCII码数组
	 * 
	 * @param input
	 * @return
	 */
	public static byte[] StringToAsciiBytes(String input) {
		int length = input.trim().length();
		byte[] result = new byte[length + 2];
		for (int i = 0; i < length; i++) {
			char c = input.charAt(i);
			result[i] = (byte) c;
		}
		result[length] = 0x0D;
		result[length + 1] = 0x0A;
		return result;
	}

	public static byte[] AsciiBytesTo645Bytes(byte[] input) {
		byte[] reStart = new byte[] { (byte) 0xfe, (byte) 0xfe, (byte) 0xfe, (byte) 0xfe };// 前导字节
		byte start = 0x68;// 帧起始符 68H
		byte[] site = new byte[] { (byte) 0xaa, (byte) 0xaa, (byte) 0xaa, (byte) 0xaa, (byte) 0xaa, (byte) 0xaa };// 地址域
		byte start2 = 0x68;// 帧起始符 68H
		byte ctrl = 0x0c;// 控制码
		byte size = (byte) (input.length + 4);// 数据长度
		byte[] data = new byte[input.length + 4]; // 数据域
		for (int i = 0; i < 4; i++) {
			data[i] = 0x44;
		}
		for (int i = 0; i < input.length; i++) {
			data[i + 4] = (byte) (input[i] + 0x33);
		}
		byte check = start;// 校验码
		for (int i = 0; i < site.length; i++) {
			check += site[i];
		}
		check += start2;
		check += size;
		check += ctrl;
		for (int i = 0; i < data.length; i++) {
			check += data[i];
		}

		byte[] temp = new byte[100];
		int count = 0;
		for (int i = 0; i < reStart.length; i++) {
			temp[count] = reStart[i];
			count += 1;
		}
		temp[count] = start;
		count += 1;
		for (int i = 0; i < site.length; i++) {
			temp[count] = site[i];
			count += 1;
		}
		temp[count] = start2;
		count += 1;
		temp[count] = ctrl;
		count += 1;
		temp[count] = size;
		count += 1;
		for (int i = 0; i < data.length; i++) {
			temp[count] = data[i];
			count += 1;
		}
		temp[count] = check;
		temp[count + 1] = 0x16;
		byte[] result = new byte[count + 2];
		for (int i = 0; i < result.length; i++) {
			result[i] = temp[i];
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
