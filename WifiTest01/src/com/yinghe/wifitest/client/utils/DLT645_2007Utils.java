package com.yinghe.wifitest.client.utils;

import java.util.ArrayList;

public class DLT645_2007Utils {

	private final static byte[] site = new byte[] { (byte) 0xAA, (byte) 0xAA, (byte) 0xAA, (byte) 0xAA, (byte) 0xAA, (byte) 0xAA };
	private final static byte[] orderTag = new byte[] { 0x11, 0x11, 0x11, 0x11 };
	private final static byte ctrl = 0x0c;

	public static byte[] getDLTOrder(String order) {
		byte[] data = DigitalUtils.getHexBytes(order);
		return DLT645_2007Utils.getDLTOrderByAsciiByte(site, ctrl, orderTag, data);
	}

	public static String getResultByDLTResponse(byte[] response) {
		ArrayList<Byte> temp = new ArrayList<Byte>();
		int count = -2;
		int begin = -1;
		for (int i = 0; i < response.length; i++) {
			if (response[i] == 0x68) {
				count += 1;
			}
			if (count == 0) {
				begin = i + 2;
				count = 1;
			}
			if (begin > 0 && i >= begin) {
				temp.add((byte) (response[i] - 0x33));
			}
		}
		byte[] input = new byte[temp.size()];
		for (int i = 0; i < input.length; i++) {
			input[i] = temp.get(i);
		}
		System.out.println(DigitalUtils.asciiByteToAsciiString(input));

		return "";
	}

	/**
	 * 
	 * @param site
	 *            地址域
	 * @param ctrl
	 *            控制码
	 * @param orderTag
	 *            命令标识符
	 * @param data
	 *            数据域
	 * @return
	 */
	private static byte[] getDLTOrderByAsciiByte(byte[] site, byte ctrl, byte[] orderTag, byte[] data) {
		ArrayList<Byte> temp = new ArrayList<Byte>();
		// 增加前导字节
		for (int i = 0; i < 4; i++) {
			temp.add((byte) 0xFE);
		}
		temp.add((byte) 0x68);// 增加帧起始符
		// 增加地址域
		for (int i = 0; i < site.length; i++) {
			temp.add(site[i]);
		}
		temp.add((byte) 0x68);// 增加帧起始符
		temp.add(ctrl);// 增加控制码
		temp.add((byte) (orderTag.length + data.length));// 增加数据与长度
		// 增加命令标识符
		for (int i = 0; i < orderTag.length; i++) {
			temp.add((byte) (orderTag[i] + 0x33));
		}
		// 增加数据域
		for (int i = 0; i < data.length; i++) {
			temp.add((byte) (data[i] + 0x33));
		}
		// 计算校验码
		byte checkTag = 0x00;
		boolean start = false;
		for (int i = 0; i < temp.size(); i++) {
			if (temp.get(i) == 0x68) {
				start = true;
			}
			if (start) {
				checkTag += temp.get(i);
			}
		}
		temp.add(checkTag);// 增加校验码
		temp.add((byte) 0x16);// 增加结束符

		// 转换数据类型
		byte[] result = new byte[temp.size()];
		for (int i = 0; i < result.length; i++) {
			result[i] = temp.get(i);
		}
		return result;

	}
}
