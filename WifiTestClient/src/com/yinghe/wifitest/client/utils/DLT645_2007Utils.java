package com.yinghe.wifitest.client.utils;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

public class DLT645_2007Utils {

	private final static byte[] site = new byte[] { (byte) 0xAA, (byte) 0xAA, (byte) 0xAA, (byte) 0xAA, (byte) 0xAA, (byte) 0xAA };
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
	 * 从DLT返回数据中解析出返回信息
	 * 
	 * @param data
	 * @return
	 */
	private static byte[] getDataFromDLTResponse(ArrayList<Byte> data) {
		int dataStart = 0;
		int start = -2;
		int dataLength = 0;
		for (int i = 0; i < data.size(); i++) {
			if (data.get(i) == 0x68) {
				start += 1;
			}
			if (start == 0) {
				dataLength = data.get(i + 2);
				dataStart = i + 3;
				break;
			}
		}
		byte[] result = new byte[dataLength];
		for (int i = 0; i < data.size() - 2 - dataStart; i++) {
			result[i] = (byte) (data.get(dataStart + i) - 0x33);
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
	 * @param response
	 * @param handler
	 */
	@SuppressWarnings("unchecked")
	public static String parseEquipmentId(JSONObject response) {
		String result = null;
		try {

			ArrayList<Byte> data = (ArrayList<Byte>) response.get("data");
			byte[] temp = getDataFromDLTResponse(data);
			result = DigitalUtils.getHexStringByBytes(temp);

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return result;
	}

	// @SuppressWarnings("unchecked")
	// public static void parseEquipmentVoltage(JSONObject response, Handler
	// handler) {
	// if (handler != null) {
	// String result = null;
	// Message message = new Message();
	// message.what = MsgTag.Msg_GetEquipmentVoltage;
	// try {
	// // String ip = response.getString("IP");
	// ArrayList<Byte> data = (ArrayList<Byte>) response.get("data");
	// byte[] temp = getDataFromDLTResponse(data);
	// String temp1 = DigitalUtils.getHexStringByByte(temp[4]).trim();
	// String temp2 = DigitalUtils.getHexStringByByte(temp[5]).trim();
	// result = Integer.parseInt(temp2) * 10 + Integer.parseInt(temp1) / 10f +
	// "V";
	// message.arg1 = MsgTag.success;
	// message.obj = result;
	// handler.sendMessage(message);
	// return;
	// } catch (JSONException e) {
	// e.printStackTrace();
	// }
	// message.arg1 = MsgTag.fail;
	// handler.sendMessage(message);
	// }
	// }

	// @SuppressWarnings("unchecked")
	// public static void parseEquipmentElectric(JSONObject response, Handler
	// handler) {
	// if (handler != null) {
	// String electric = null;
	// Message message = new Message();
	// message.what = MsgTag.Msg_GetEquipmentElectric;
	// try {
	// ArrayList<Byte> data = (ArrayList<Byte>) response.get("data");
	// byte[] temp = getDataFromDLTResponse(data);
	// String temp1 = DigitalUtils.getHexStringByByte(temp[4]).trim();
	// String temp2 = DigitalUtils.getHexStringByByte(temp[5]).trim();
	// String temp3 = DigitalUtils.getHexStringByByte(temp[6]).trim();
	// String result = temp3 + temp2 + temp1;
	// electric = Integer.parseInt(result) / 1000f + "A";
	//
	// message.arg1 = MsgTag.success;
	// message.obj = electric;
	// handler.sendMessage(message);
	// return;
	// } catch (JSONException e) {
	// e.printStackTrace();
	// }
	// message.arg1 = MsgTag.fail;
	// handler.sendMessage(message);
	// }
	// }
}
