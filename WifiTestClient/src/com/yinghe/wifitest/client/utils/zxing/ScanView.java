package com.yinghe.wifitest.client.utils.zxing;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class ScanView extends View {
	private Paint paint;
	private static final long ANIMATION_DELAY = 10L;// 刷新界面的时间

	private int ScreenRate;// 四个绿色边角对应的长度
	private static final int CORNER_WIDTH = 10;// 四个绿色边角对应的宽度
	private static final int MIDDLE_LINE_WIDTH = 6;// 扫描框中的中间线的宽度
	private static final int MIDDLE_LINE_PADDING = 5;// 扫描框中的中间线的与扫描框左右的间隙
	private static final int SPEEN_DISTANCE = 5;// 中间那条线每次刷新移动的距离
	private static float density;// 手机的屏幕密度
	private int slideTop;// 中间滑动线的最顶端位置

	private boolean isFirst = true;

	public ScanView(Context context, AttributeSet attrs) {
		super(context, attrs);
		density = context.getResources().getDisplayMetrics().density;
		ScreenRate = (int) (20 * density);// 将像素转换成dp
		paint = new Paint();
	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		// super.onDraw(canvas);
		Rect frame = canvas.getClipBounds();
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
		if (isFirst) {
			slideTop = frame.top;
			isFirst = false;
		}
		slideTop += SPEEN_DISTANCE;
		if (slideTop >= frame.bottom) {
			slideTop = frame.top;
		}
		canvas.drawRect(frame.left + MIDDLE_LINE_PADDING, slideTop - MIDDLE_LINE_WIDTH / 2, frame.right - MIDDLE_LINE_PADDING, slideTop + MIDDLE_LINE_WIDTH / 2, paint);

		// 只刷新扫描框的内容，其他地方不刷新
		postInvalidateDelayed(ANIMATION_DELAY, frame.left, frame.top, frame.right, frame.bottom);
	}
}
