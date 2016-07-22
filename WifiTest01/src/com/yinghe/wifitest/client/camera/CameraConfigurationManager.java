package com.yinghe.wifitest.client.camera;

import android.content.Context;
import android.graphics.Point;
import android.hardware.Camera;
import android.view.Display;
import android.view.WindowManager;

public class CameraConfigurationManager {
	private final Context context;
	private Point screenResolution;

	public CameraConfigurationManager(Context context) {
		this.context = context;
	}

	void initFromCameraParameters(Camera camera) {
		Camera.Parameters parameters = camera.getParameters();
		//		previewFormat = parameters.getPreviewFormat();
		//		previewFormatString = parameters.get("preview-format");
		//		Log.d(TAG, "Default preview format: " + previewFormat + '/' + previewFormatString);
		WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = manager.getDefaultDisplay();
		screenResolution = new Point(display.getWidth(), display.getHeight());
		//		Log.d(TAG, "Screen resolution: " + screenResolution);
		//		cameraResolution = getCameraResolution(parameters, screenResolution);
		//		Log.d(TAG, "Camera resolution: " + screenResolution);
	}

	public Point getScreenResolution() {
		WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = manager.getDefaultDisplay();
		screenResolution = new Point(display.getWidth(), display.getHeight());
		return screenResolution;
	}

}
