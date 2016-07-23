/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yinghe.wifitest.client.utils.zxing.camera;

import java.io.IOException;

import android.content.Context;
import android.graphics.Point;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;

public class CameraManager {

	private static final String TAG = CameraManager.class.getSimpleName();

	private final Context context;
	private final CameraConfigurationManager configManager;
	private Camera camera;
	private AutoFocusManager autoFocusManager;

	private boolean initialized;
	private boolean previewing;
	private int requestedCameraId = -1;
	private final PreviewCallback previewCallback;

	public CameraManager(Context context) {
		this.context = context;
		this.configManager = new CameraConfigurationManager(context);
		previewCallback = new PreviewCallback(configManager);
	}

	public synchronized void openDriver(SurfaceHolder holder) throws IOException {
		Camera theCamera = camera;
		if (theCamera == null) {

			if (requestedCameraId >= 0) {
				theCamera = OpenCameraInterface.open(requestedCameraId);
			} else {
				theCamera = OpenCameraInterface.open();
			}

			if (theCamera == null) {
				throw new IOException();
			}
			camera = theCamera;
		}
		theCamera.setPreviewDisplay(holder);

		if (!initialized) {
			initialized = true;
			configManager.initFromCameraParameters(theCamera);
		}

		Camera.Parameters parameters = theCamera.getParameters();
		String parametersFlattened = parameters == null ? null : parameters.flatten(); // Save
																						// these,
																						// temporarily
		try {
			configManager.setDesiredCameraParameters(theCamera, false);
		} catch (RuntimeException re) {
			// Reset:
			if (parametersFlattened != null) {
				parameters = theCamera.getParameters();
				parameters.unflatten(parametersFlattened);
				try {
					theCamera.setParameters(parameters);
					configManager.setDesiredCameraParameters(theCamera, true);
				} catch (RuntimeException re2) {
					Log.w(TAG, "Camera rejected even safe-mode parameters! No configuration");
				}
			}
		}

	}

	public synchronized boolean isOpen() {
		return camera != null;
	}

	public synchronized void closeDriver() {
		if (camera != null) {
			camera.release();
			camera = null;
		}
	}

	/**
	 * Asks the camera hardware to begin drawing preview frames to the screen.
	 */
	public synchronized void startPreview() {
		Camera theCamera = camera;
		if (theCamera != null && !previewing) {
			theCamera.startPreview();
			previewing = true;
			autoFocusManager = new AutoFocusManager(context, camera);
		}
	}

	/**
	 * Tells the camera to stop drawing preview frames.
	 */
	public synchronized void stopPreview() {
		if (autoFocusManager != null) {
			autoFocusManager.stop();
			autoFocusManager = null;
		}
		if (camera != null && previewing) {
			camera.stopPreview();
			previewCallback.setHandler(null, 0);
			previewing = false;
		}
	}

	public synchronized void requestPreviewFrame(Handler handler, int message) {
		Camera theCamera = camera;
		if (theCamera != null && previewing) {
			previewCallback.setHandler(handler, message);
			theCamera.setOneShotPreviewCallback(previewCallback);
		}
	}

	public synchronized void setManualCameraId(int cameraId) {
		requestedCameraId = cameraId;
	}

	/**
	 * 获取相机分辨率
	 * 
	 * @return
	 */
	public Point getCameraResolution() {
		return configManager.getCameraResolution();
	}

	public Size getPreviewSize() {
		if (null != camera) {
			return camera.getParameters().getPreviewSize();
		}
		return null;
	}
}
