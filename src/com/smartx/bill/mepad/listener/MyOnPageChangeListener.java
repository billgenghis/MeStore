package com.smartx.bill.mepad.listener;

import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

/**
 * 页卡切换监听
 */
public class MyOnPageChangeListener implements OnPageChangeListener {
	private int offset = 0;// 动画图片偏移量
	private int currIndex = 0;// 当前页卡编号
	private int bmpW;// 动画图片宽度
	private ImageView cursor = null;
	private int one = offset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量
	private int two = one * 2;// 页卡1 -> 页卡3 偏移量
	private int three = one * 3;// 页卡1 -> 页卡4 偏移量

	
	public MyOnPageChangeListener(ImageView cursor){
		this.cursor = cursor; 
	}
	@Override
	public void onPageSelected(int arg0) {
		Animation animation = null;
		switch (arg0) {
		case 0:
			if (currIndex == 1) {
				animation = new TranslateAnimation(one, 0, 0, 0);
			} else if (currIndex == 2) {
				animation = new TranslateAnimation(two, 0, 0, 0);
			} else if (currIndex == 3) {
				animation = new TranslateAnimation(three, 0, 0, 0);
			}
			break;
		case 1:
			if (currIndex == 0) {
				animation = new TranslateAnimation(offset, one, 0, 0);
			} else if (currIndex == 2) {
				animation = new TranslateAnimation(two, one, 0, 0);
			} else if (currIndex == 3) {
				animation = new TranslateAnimation(three, one, 0, 0);
			}
			break;
		case 2:
			if (currIndex == 0) {
				animation = new TranslateAnimation(offset, two, 0, 0);
			} else if (currIndex == 1) {
				animation = new TranslateAnimation(one, two, 0, 0);
			} else if (currIndex == 3) {
				animation = new TranslateAnimation(three, two, 0, 0);
			}
			break;
		case 3:
			if (currIndex == 0) {
				animation = new TranslateAnimation(offset, three, 0, 0);
			} else if (currIndex == 1) {
				animation = new TranslateAnimation(one, three, 0, 0);
			} else if (currIndex == 2) {
				animation = new TranslateAnimation(two, three, 0, 0);
			}
			break;
		}
		currIndex = arg0;
		animation.setFillAfter(true);// True:图片停在动画结束位置
		animation.setDuration(300);
		cursor.startAnimation(animation);
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}
}


