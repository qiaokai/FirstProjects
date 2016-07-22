package com.yinghe.wifitest.client.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.example.wifitest01.R;
import com.yinghe.wifitest.client.camera.CameraManager;

public final class ScanView extends View {

	private static final long ANIMATION_DELAY = 10L;// 刷新界面的时间

	private int ScreenRate;// 四个绿色边角对应的长度
	private static final int CORNER_WIDTH = 10;// 四个绿色边角对应的宽度
	private static final int MIDDLE_LINE_WIDTH = 6;// 扫描框中的中间线的宽度
	private static final int MIDDLE_LINE_PADDING = 5;// 扫描框中的中间线的与扫描框左右的间隙
	private static final int SPEEN_DISTANCE = 5;// 中间那条线每次刷新移动的距离
	private static float density;// 手机的屏幕密度
	private int slideTop;// 中间滑动线的最顶端位置

	private final Paint paint;
	private Bitmap resultBitmap;
	private int maskColor;
	private int resultColor;
	private int frameColor;
	private int laserColor;
	private int resultPointColor;

	public ScanView(Context context, AttributeSet attrs) {
		super(context, attrs);
		density = context.getResources().getDisplayMetrics().density;
		ScreenRate = (int) (20 * density);// 将像素转换成dp
		paint = new Paint();
		Resources resources = getResources();
		maskColor = resources.getColor(R.color.scanview_mask);
		resultColor = resources.getColor(R.color.scanview_result);
	}

	@Override
	public void onDraw(Canvas canvas) {
		Rect frame = CameraManager.Instance().getFramingRect();
		if (frame == null) {
			System.out.println("OKOKOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
			return;
		}
		//获取屏幕的宽和高
		int width = canvas.getWidth();
		int height = canvas.getHeight();
		System.out.println(frame);
		System.out.println("width:" + width + "  height:" + height);
		paint.setColor(resultBitmap != null ? resultColor : maskColor);

		//画出扫描框外面的阴影部分，共四个部分，扫描框的上面到屏幕上面，扫描框的下面到屏幕下面
		//扫描框的左边面到屏幕左边，扫描框的右边到屏幕右边
		canvas.drawRect(0, 0, width, frame.top, paint);
		canvas.drawRect(0, frame.top, frame.left, frame.bottom + 1, paint);
		canvas.drawRect(frame.right + 1, frame.top, width, frame.bottom + 1, paint);
		canvas.drawRect(0, frame.bottom + 1, width, height, paint);

		// 画扫描框边上的角，总共8个部分
		paint.setColor(Color.GREEN);
		canvas.drawRect(frame.left, frame.top, frame.left + ScreenRate, frame.top + CORNER_WIDTH, paint);
		canvas.drawRect(frame.left, frame.top, frame.left + CORNER_WIDTH, frame.top + ScreenRate, paint);
		canvas.drawRect(frame.right - ScreenRate, frame.top, frame.right, frame.top + CORNER_WIDTH, paint);
		canvas.drawRect(frame.right - CORNER_WIDTH, frame.top, frame.right, frame.top + ScreenRate, paint);
		canvas.drawRect(frame.left, frame.bottom - CORNER_WIDTH, frame.left + ScreenRate, frame.bottom, paint);
		canvas.drawRect(frame.left, frame.bottom - ScreenRate, frame.left + CORNER_WIDTH, frame.bottom, paint);
		canvas.drawRect(frame.right - ScreenRate, frame.bottom - CORNER_WIDTH, frame.right, frame.bottom, paint);
		canvas.drawRect(frame.right - CORNER_WIDTH, frame.bottom - ScreenRate, frame.right, frame.bottom, paint);

		// 绘制中间的线,每次刷新界面，中间的线往下移动SPEEN_DISTANCE
		slideTop += SPEEN_DISTANCE;
		if (slideTop >= frame.bottom) {
			slideTop = frame.top;
		}
		canvas.drawRect(frame.left + MIDDLE_LINE_PADDING, slideTop - MIDDLE_LINE_WIDTH / 2,
			frame.right - MIDDLE_LINE_PADDING, slideTop + MIDDLE_LINE_WIDTH / 2, paint);

		// 只刷新扫描框的内容，其他地方不刷新
		postInvalidateDelayed(ANIMATION_DELAY, frame.left, frame.top, frame.right, frame.bottom);
	}

}
