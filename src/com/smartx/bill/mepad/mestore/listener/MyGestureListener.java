package com.smartx.bill.mepad.mestore.listener;

import android.content.Context;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class MyGestureListener extends SimpleOnGestureListener implements
		OnTouchListener {
	Context context;
	GestureDetector gDetector;

	public MyGestureListener() {
		super();
	}

	public MyGestureListener(Context context) {
		this(context, null);
	}

	public MyGestureListener(Context context, GestureDetector gDetector) {

		if (gDetector == null)
			gDetector = new GestureDetector(context, this);

		this.context = context;
		this.gDetector = gDetector;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {

		return super.onFling(e1, e2, velocityX, velocityY);
	}

	@Override
	public boolean onSingleTapConfirmed(MotionEvent e) {

		return super.onSingleTapConfirmed(e);
	}

	public boolean onTouch(View v, MotionEvent event) {

		// Within the MyGestureListener class you can now manage the
		// event.getAction() codes.

		// Note that we are now calling the gesture Detectors onTouchEvent. And
		// given we've set this class as the GestureDetectors listener
		// the onFling, onSingleTap etc methods will be executed.
		return gDetector.onTouchEvent(event);
	}

	public GestureDetector getDetector() {
		return gDetector;
	}
}
