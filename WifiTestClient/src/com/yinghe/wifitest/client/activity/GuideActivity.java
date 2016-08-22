package com.yinghe.wifitest.client.activity;

import java.util.ArrayList;
import java.util.List;

import com.example.wifitest01.R;
import com.yinghe.wifitest.client.adapter.ViewPagerAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class GuideActivity extends Activity implements View.OnClickListener {
	private static final String mCurrentPrefix = "guide_splash_portrait_";
	private static final String TAG_ENTRT_GAME = "enterGame";

	private List<View> views = new ArrayList<>();;
	private ImageView mIVSplashPic;
	private ViewPager viewPager;
	private ViewPagerAdapter adapter;
	private Button startBtn;
	// 底部的小点图片
	private ImageView[] dots;// 记录当前选中的位置
	private int currentIndex;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guide);

		startBtn = (Button) findViewById(R.id.btn_enterGame);
		startBtn.setTag(TAG_ENTRT_GAME);
		startBtn.setOnClickListener(this);

		for (int i = 0; i < 10; i++) {
			int id = getResources().getIdentifier(mCurrentPrefix + i, "drawable", getPackageName());
			if (id > 0) {
				mIVSplashPic = new ImageView(getApplicationContext());
				mIVSplashPic.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
				mIVSplashPic.setBackgroundResource(id);
				views.add(mIVSplashPic);
			}
		}
		viewPager = (ViewPager) findViewById(R.id.vp_guide);
		adapter = new ViewPagerAdapter(views);
		viewPager.setAdapter(adapter);
		viewPager.setOnPageChangeListener(new PageChangeListener());
		initDots();
	}

	private void initDots() {
		LinearLayout ll = (LinearLayout) findViewById(R.id.ll);
		dots = new ImageView[views.size()];
		for (int i = 0; i < views.size(); i++) {
			ImageView imageView = new ImageView(getApplicationContext());
			imageView.setImageResource(getResources().getIdentifier("dot_selector", "drawable", getPackageName()));
			imageView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			imageView.setPadding(10, 0, 10, 0);
			ll.addView(imageView);

			dots[i] = imageView;
			dots[i].setEnabled(false);// 都设为灰色
			dots[i].setOnClickListener((OnClickListener) this);
			dots[i].setTag(i);// 设置位置tag，方便取出与当前位置对应

		}

		currentIndex = 0;
		dots[currentIndex].setEnabled(true); // 设置为白色，即选中状态

	}

	private class PageChangeListener implements ViewPager.OnPageChangeListener {
		@Override
		public void onPageScrollStateChanged(int position) {
		}

		@Override
		public void onPageScrolled(int position, float arg1, int arg2) {
		}

		@Override
		public void onPageSelected(int position) {
			setCurDot(position);// 设置底部小点选中状态
		}

	}

	/**
	 * 设置当前指示点
	 * 
	 * @param position
	 */
	private void setCurDot(int position) {
		if (position < 0 || position > views.size() || currentIndex == position) {
			return;
		}
		dots[position].setEnabled(true);
		dots[currentIndex].setEnabled(false);
		currentIndex = position;
		if (position == views.size() - 1) {
			startBtn.setVisibility(View.VISIBLE);
		} else {
			startBtn.setVisibility(View.GONE);
		}
	}

	@Override
	public void onClick(View view) {
		if (TAG_ENTRT_GAME.equals(view.getTag())) {
			startActivity(new Intent(getApplicationContext(), MainFragmentActivity.class));
			finish();
		}

	}

}
