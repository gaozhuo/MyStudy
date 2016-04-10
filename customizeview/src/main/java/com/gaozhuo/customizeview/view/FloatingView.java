package com.gaozhuo.customizeview.view;

import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.gaozhuo.customizeview.R;

/**
 * Created by gaozhuo on 2016/3/27.
 */
public class FloatingView extends LinearLayout {
    private WindowManager.LayoutParams mLayoutParams;
    private WindowManager mWindowManager;
    private GestureDetectorCompat mGestureDetector;
    private float mRawX;
    private float mRawY;
    private float mTouchDownX;
    private float mTouchDownY;
    private boolean mIsShowing;
    private int mScreenWidth;
    private int mScreenHeight;

    public FloatingView(Context context) {
        this(context, null);
    }

    public FloatingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.floating_window, this);
        mWindowManager = (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        mWindowManager.getDefaultDisplay().getSize(point);
        mScreenWidth = point.x;
        mScreenHeight = point.y;
        Log.d("gaozhuo","width=" + mScreenWidth);
        Log.d("gaozhuo", "height=" + mScreenHeight);
        mLayoutParams = buildParams();
        mGestureDetector = new GestureDetectorCompat(context, new MyGestureListener());
    }

    public void show() {
        if (!mIsShowing) {
            mWindowManager.addView(this, mLayoutParams);
            mIsShowing = true;
        }
    }

    public void dismiss() {
        if (mIsShowing) {
            mWindowManager.removeView(this);
            mIsShowing = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        final float rawX = event.getRawX();
        final float rawY = event.getRawY();
        final float x = event.getX();
        final float y = event.getY();

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                mTouchDownX = x;
                mTouchDownY = y;
                mRawX = rawX;
                mRawY = rawY;
                break;
            case MotionEvent.ACTION_MOVE:
                mRawX = rawX;
                mRawY = rawY;
                updatePosition();
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onTouchEvent(event);
    }

    private void updatePosition() {
        mLayoutParams.x = (int) (mRawX - mTouchDownX);
        mLayoutParams.y = (int) (mRawY - getStatusBarHeight() - mTouchDownY);
        mWindowManager.updateViewLayout(this, mLayoutParams);
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        Log.d("gaozhuo", "statusbar height=" + result);
        return result;
    }

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            Log.d("gaozhuo", "onSingleTapConfirmed");
            dismiss();
            return super.onSingleTapConfirmed(e);
        }
    }

    private WindowManager.LayoutParams buildParams() {
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.type = WindowManager.LayoutParams.TYPE_PHONE;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        params.format = PixelFormat.TRANSLUCENT;
        params.x = 100;
        params.y = 100;
        params.gravity = Gravity.LEFT | Gravity.TOP;

        return params;
    }
}
