package com.yinghe.wifitest.services.utils;

import java.util.ArrayList;

public class DLT645_2007Utils {

	private final static byte[] site = new byte[] { (byte) 0xAA, (byte) 0xAA, (byte) 0xAA, (byte) 0xAA, (byte) 0xAA,
			(byte) 0xAA };
	private final static byte[] orderTag = new byte[] { 0x11, 0x11, 0x11, 0x11 };
	private final static byte ctrl = 0x0c;

	/**
	 * 获取DLT645—2007命令码
	 * 
	 * @param order
	 * @return
	 */
	public static byte[] getDltCode(String order) {
		byte[] data = DigitalUtils.getHexBytes(order);
		return DLT645_2007Utils.getDltCodeByHexByte(site, ctrl, orderTag, data);
	}

	/**
	 * 
	 * @param ctrl控制码
	 * @param order命令码
	 * @return
	 */
	public static byte[] getDltCode(byte ctrl, byte[] order) {
		return DLT645_2007Utils.getDltCodeByHexByte(site, ctrl, new byte[] {}, order);
	}

	/**
	 * 从DLT返回数据中解析出返回信息
	 * 
	 * @param message
	 * @return
	 */
	private static byte[] getDataFromDLTResponse(byte[] message) {
		int dataStart = 0;
		int start = -2;
		int dataLength = 0;
		for (int i = 0; i < message.length; i++) {
			if (message[i] == 0x68) {
				start += 1;
			}
			if (start == 0) {
				dataLength = message[i + 2];
				dataStart = i + 3;
				break;
			}
		}
		byte[] result = new byte[dataLength];
		for (int i = 0; i < message.length - 2 - dataStart; i++) {
			result[i] = (byte) (message[dataStart + i] - 0x33);
		}
		return result;
	}

	/**
	 * 获取DLT645-2007命令码
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
	private static byte[] getDltCodeByHexByte(byte[] site, byte ctrl, byte[] orderTag, byte[] data) {
		ArrayList<Byte> temp = new ArrayList<Byte>();
		// 增加前导字节
		for (int i = 0; i < 4; i++)
			temp.add((byte) 0xFE);
		// 增加帧起始符
		temp.add((byte) 0x68);
		// 增加地址域
		for (int i = 0; i < site.length; i++)
			temp.add(site[i]);
		// 增加帧起始符
		temp.add((byte) 0x68);
		// 增加控制码
		temp.add(ctrl);
		// 增加数据与长度
		temp.add((byte) (orderTag.length + data.length));
		// 增加命令标识符
		for (int i = 0; i < orderTag.length; i++) {
			temp.add((byte) (orderTag[i] + 0x33));
		}
		// 增加数据域
		for (int i = 0; i < data.length; i++)
			temp.add((byte) (data[i] + 0x33));
		// 计算校验码
		byte checkTag = 0x00;
		boolean start = false;
		for (int i = 0; i < temp.size(); i++) {
			if (temp.get(i) == 0x68)
				start = true;
			if (start)
				checkTag += temp.get(i);
		}
		// 增加校验码
		temp.add(checkTag);
		// 增加结束符
		temp.add((byte) 0x16);

		// 转换数据类型
		byte[] result = new byte[temp.size()];
		for (int i = 0; i < result.length; i++)
			result[i] = temp.get(i);

		return result;
	}

	/**
	 * 解析设备ID
	 * 
	 * @param message
	 * @param handler
	 */
	public static String parseEquipmentId(byte[] message) {
		String result = null;
		byte[] temp = getDataFromDLTResponse(message);
		result = DigitalUtils.getHexStringByBytes(temp);
		return result;
	}

	public static String parseEquipmentVoltage(byte[] message) {
		String result = null;
		try {
			byte[] temp = getDataFromDLTResponse(message);
			String temp1 = DigitalUtils.getHexStringByByte(temp[4]).trim();
			String temp2 = DigitalUtils.getHexStringByByte(temp[5]).trim();
			result = Integer.parseInt(temp2) * 10 + Integer.parseInt(temp1) / 10f + "V";
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String parseEquipmentElectric(byte[] message) {
		String electric = null;
		try {
			byte[] temp = getDataFromDLTResponse(message);
			String temp1 = DigitalUtils.getHexStringByByte(temp[4]).trim();
			String temp2 = DigitalUtils.getHexStringByByte(temp[5]).trim();
			String temp3 = DigitalUtils.getHexStringByByte(temp[6]).trim();
			String result = temp3 + temp2 + temp1;
			electric = Integer.parseInt(result) / 1000f + "A";
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return electric;
	}

	public static String parseEquipmentQuantity(byte[] message) {
		String quantity = null;
		try {
			byte[] temp = getDataFromDLTResponse(message);
			String temp1 = DigitalUtils.getHexStringByByte(temp[4]).trim();
			String temp2 = DigitalUtils.getHexStringByByte(temp[5]).trim();
			String temp3 = DigitalUtils.getHexStringByByte(temp[6]).trim();
			String temp4 = DigitalUtils.getHexStringByByte(temp[6]).trim();
			String result = temp4 + temp3 + temp2 + temp1;
			quantity = Integer.parseInt(result) / 100f + "KWH";
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return quantity;
	}

	public static byte[] getEquipmentIdCommand() {
		byte[] result = new byte[] { (byte) 0xFE, (byte) 0xFE, (byte) 0xFE, (byte) 0xFE, 0x68, (byte) 0xAA, (byte) 0xAA,
				(byte) 0xAA, (byte) 0xAA, (byte) 0xAA, (byte) 0xAA, 0x68, 0x13, 0x00, (byte) 0xDF, 0x16 };
		return result;
	}

	public static byte[] openEquipmentCommand() {
		byte[] result = new byte[] { (byte) 0xFE, (byte) 0xFE, (byte) 0xFE, (byte) 0xFE, 0x68, (byte) 0xAA, (byte) 0xAA,
				(byte) 0xAA, (byte) 0xAA, (byte) 0xAA, (byte) 0xAA, 0x68, 0x1C, 0x10, 0x37, 0x44, 0x44, 0x44, 0x34,
				(byte) 0x89, 0x67, 0x45, 0x4E, 0x33, 0x47, 0x77, 0x3B, 0x3A, 0x44, 0x3C, (byte) 0x98, 0x16 };
		return result;
	}

	public static byte[] closeEquipmentCommand() {
		byte[] result = new byte[] { (byte) 0xFE, (byte) 0xFE, (byte) 0xFE, (byte) 0xFE, 0x68, (byte) 0xAA, (byte) 0xAA,
				(byte) 0xAA, (byte) 0xAA, (byte) 0xAA, (byte) 0xAA, 0x68, 0x1C, 0x10, 0x37, 0x44, 0x44, 0x44, 0x34,
				(byte) 0x89, 0x67, 0x45, 0x4D, 0x33, 0x47, 0x77, 0x3B, 0x3A, 0x44, 0x3C, (byte) 0x97, 0x16 };
		return result;
	}

	public static byte[] getCurrentVoltageCommand() {
		byte ctrl = 0x11;
		byte[] order = new byte[] { 0x00, 0x01, 0x01, 0x02 };
		byte[] result = getDltCode(ctrl, order);
		return result;
	}

	public static byte[] getCurrentElectricCommand() {
		byte ctrl = 0x11;
		byte[] order = new byte[] { 0x00, 0x01, 0x02, 0x02 };
		byte[] result = getDltCode(ctrl, order);
		return result;
	}

	public static byte[] getCurrentQuantityCommand() {
		byte ctrl = 0x11;
		byte[] order = new byte[] { 0x00, 0x00, 0x00, 0x00 };
		byte[] result = getDltCode(ctrl, order);
		return result;
	}

}
