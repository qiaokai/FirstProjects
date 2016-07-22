package com.yinghe.wifitest.client.camera;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Camera;
import android.util.Log;

public class CameraManager {
	private final Context context;
	private static CameraManager cameraManager = null;
	private final CameraConfigurationManager configManager;
	private Camera camera;

	//	private AutoFocusManager autoFocusManager;
	private final PreviewCallback previewCallback;

	private Rect framingRect;
	private static final int MIN_FRAME_WIDTH = 240;
	private static final int MIN_FRAME_HEIGHT = 240;
	private static final int MAX_FRAME_WIDTH = 400;
	private static final int MAX_FRAME_HEIGHT = 400;

	public static CameraManager Instance() {
		if (cameraManager != null) {
			return cameraManager;
		} else {
			Log.w("wifitest", "please init CameraManager at first.");
		}
		return null;
	}

	private CameraManager(Context context) {
		this.context = context;
		configManager = new CameraConfigurationManager(context);
		previewCallback = new PreviewCallback(configManager);
	}

	public static void init(Context context) {
		if (cameraManager == null) {
			cameraManager = new CameraManager(context);
		} else {
			Log.w("wifitest", "please used  CameraManager.Instance().");
		}

	}

	public Rect getFramingRect() {
		Point screenResolution = configManager.getScreenResolution();
		if (framingRect == null) {
//			if (camera == null) {
//				return null;
//			}
			int width = screenResolution.x * 3 / 4;
			if (width < MIN_FRAME_WIDTH) {
				width = MIN_FRAME_WIDTH;
			} else if (width > MAX_FRAME_WIDTH) {
				width = MAX_FRAME_WIDTH;
			}
			int height = screenResolution.y * 3 / 4;
			if (height < MIN_FRAME_HEIGHT) {
				height = MIN_FRAME_HEIGHT;
			} else if (height > MAX_FRAME_HEIGHT) {
				height = MAX_FRAME_HEIGHT;
			}
			int leftOffset = (screenResolution.x - width) / 2;
			int topOffset = (screenResolution.y - height) / 3;
			framingRect = new Rect(leftOffset, topOffset, leftOffset + width, topOffset + height);
		}
		return framingRect;
	}

}
