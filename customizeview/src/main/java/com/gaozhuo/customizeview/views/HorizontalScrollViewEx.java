package com.gaozhuo.customizeview.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

import com.gaozhuo.commonlibrary.utils.DeviceUtils;

/**
 * Created by gaozhuo on 2016/4/4.
 */
public class HorizontalScrollViewEx extends ViewGroup {
    private static final String TAG = HorizontalScrollViewEx.class.getSimpleName();
    private int mLastX;
    private int mLastY;
    private Scroller mScroller;
    private VelocityTracker mVelocityTracker;
    private int mCurrentChildIndex = 0;//指示当前显示的是哪一个view
    private int mScreenWidth;

    public HorizontalScrollViewEx(Context context) {
        this(context, null);
    }

    public HorizontalScrollViewEx(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        //setClickable(true);
        mScroller = new Scroller(getContext());
        mVelocityTracker = VelocityTracker.obtain();
        mScreenWidth = DeviceUtils.getScreenWidth(getContext());
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mScroller = null;
        mVelocityTracker.recycle();
        mVelocityTracker = null;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        final int count = getChildCount();
        if (count == 0) {
            setMeasuredDimension(0, 0);
            return;
        }

        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        final int width = mScreenWidth;
        final int height = getChildAt(0).getMeasuredHeight();

        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(width, height);
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(width, heightSpecSize);
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSpecSize, height);
        } else {
            setMeasuredDimension(widthSpecSize, heightSpecSize);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int leftOffset = 0;
        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            final int width = child.getMeasuredWidth();
            final int height = child.getMeasuredHeight();
            child.layout(leftOffset, 0, leftOffset + width, height);
            leftOffset += width;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent action=" + event.getActionMasked());
        mVelocityTracker.addMovement(event);
        int x = (int) event.getX();
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = x - mLastX;
                scrollBy(-dx, 0);
                break;
            case MotionEvent.ACTION_UP:
                mVelocityTracker.computeCurrentVelocity(1000);
                float xVelocity = mVelocityTracker.getXVelocity();
                if (Math.abs(xVelocity) > 50) {
                    if (xVelocity > 0) {//从左向右滑动，显示上一个View
                        mCurrentChildIndex = mCurrentChildIndex - 1;
                    } else {//从右向左滑动，显示下一个View
                        mCurrentChildIndex = mCurrentChildIndex + 1;
                    }

                } else {
                    float offsetX = (float) getScrollX() / mScreenWidth;
                    mCurrentChildIndex = Math.round(offsetX);//四舍五入
                }
                // 0 <= mCurrentChildIndex <= getChildCount() - 1
                mCurrentChildIndex = Math.max(0, Math.min(getChildCount() - 1, mCurrentChildIndex));

                int deltaX = mCurrentChildIndex * mScreenWidth - getScrollX();
                smoothScrollBy(deltaX, 0);
                mVelocityTracker.clear();
                break;

        }
        mLastX = x;
        /**
         * 这里必须返回true，因为HorizontalScrollViewEx里面的子view如果是disclickable的
         * 那么子view的onTouchEvent讲返回false，所以HorizontalScrollViewEx的onTouchEvent
         * 方法得到执行，如果返回super.onTouchEvent(event)的话，即调用view的onTouchEvent
         * view默认是disclickable的，所以会view的onTouchEvent会返回false，也即HorizontalScrollViewEx
         * 的onTouchEvent返回false，导致收不到后续事件
         */
        return true;
    }

    private void smoothScrollBy(int dx, int dy) {
        if (mScroller != null) {
            mScroller.startScroll(getScrollX(), getScrollY(), dx, dy, 500);
            invalidate();
        }
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercepted = false;
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                intercepted = false;
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastX;
                int deltaY = y - mLastY;
                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    intercepted = true;
                } else {
                    intercepted = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                intercepted = false;
                break;

        }
        mLastX = x;
        mLastY = y;
        Log.d(TAG, "onInterceptTouchEvent intercepted=" + intercepted);
        return intercepted;
    }
}
