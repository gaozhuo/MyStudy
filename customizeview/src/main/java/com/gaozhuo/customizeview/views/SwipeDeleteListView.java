package com.gaozhuo.customizeview.views;

import android.content.Context;
import android.support.v4.view.VelocityTrackerCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.AccelerateInterpolator;
import android.widget.ListView;
import android.widget.OverScroller;

import com.gaozhuo.commonlibrary.utils.DeviceUtils;


/**
 * 带滑动删除的ListView
 *
 * @author gaozhuo
 * @date 2016/7/23
 */
public class SwipeDeleteListView extends ListView {
    private static final float MIN_FLING_VELOCITY = 2400;
    private View mItemView;//滑动的item
    private int mPosition;//滑动的item的位置
    private int mLastX;
    private int mLastY;
    private int mTouchSlop;
    private int mScreenWidth;
    private OverScroller mScroller;
    private OnItemRemovedListener mOnItemRemovedListener;
    private VelocityTracker mVelocityTracker;

    public interface OnItemRemovedListener {
        void onItemRemoved(int position);
    }

    public SwipeDeleteListView(Context context) {
        super(context);
        init();
    }

    public SwipeDeleteListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SwipeDeleteListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        mScreenWidth = DeviceUtils.getScreenWidth(getContext());
        mScroller = new OverScroller(getContext());
        mVelocityTracker = VelocityTracker.obtain();
    }

    public void setOnItemRemovedListener(OnItemRemovedListener listener) {
        mOnItemRemovedListener = listener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        mVelocityTracker.addMovement(ev);
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = x;
                mLastY = y;
                getCurrentItem(ev);
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = x - mLastX;
                int dy = y - mLastY;
                if (canSwipe(dx, dy)) {
                    mItemView.scrollBy(-dx, 0);
                    mLastX = x;
                    mLastY = y;
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (positionValid() && mItemView != null) {
                    mVelocityTracker.computeCurrentVelocity(1000);
                    float xVelocity = mVelocityTracker.getXVelocity();
                    if (xVelocity > MIN_FLING_VELOCITY) {
                        scrollRight();
                    } else if (xVelocity < -MIN_FLING_VELOCITY) {
                        scrollLeft();
                    } else {
                        scrollByDistance();
                    }
                }

                break;
        }
        return super.onTouchEvent(ev);
    }

    private void scrollByDistance() {
        int scrollX = mItemView.getScrollX();
        if (scrollX > mScreenWidth / 3) {
            scrollLeft();
        } else if (scrollX < -mScreenWidth / 3) {
            scrollRight();
        } else {
            scrollOrigin();
        }
    }

    /**
     * 滚出右边界
     */
    private void scrollRight() {
        int scrollX = mItemView.getScrollX();
        int delta = -mScreenWidth - scrollX;
        mScroller.startScroll(scrollX, 0, delta, 0, -delta);
    }

    /**
     * 滚出左边界
     */
    private void scrollLeft() {
        int scrollX = mItemView.getScrollX();
        int delta = mScreenWidth - scrollX;
        mScroller.startScroll(scrollX, 0, delta, 0, delta);
    }

    /**
     * 回到原点
     */
    private void scrollOrigin() {
        int scrollX = mItemView.getScrollX();
        mScroller.startScroll(scrollX, 0, -scrollX, 0, Math.abs(scrollX));
    }


    private boolean positionValid() {
        return mPosition != ListView.INVALID_POSITION;
    }

    private boolean canSwipe(int dx, int dy) {
        //mItemView != null 且横向滑动时才可以滑动item
        if (positionValid() && mItemView != null && Math.abs(dx) > mTouchSlop && Math.abs(dy) < mTouchSlop) {
            return true;
        }
        return false;
    }

    private void getCurrentItem(MotionEvent ev) {
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        int position = pointToPosition(x, y);
        if (position == ListView.INVALID_POSITION) {
            return;
        }
        mPosition = position;
        mItemView = getChildAt(position - getFirstVisiblePosition());
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            mItemView.scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();

            //滑动结束且没有回到原点才调用onItemRemoved方法
            if (mScroller.isFinished() && mItemView != null && mItemView.getScrollX() != 0 && positionValid()) {
                mOnItemRemovedListener.onItemRemoved(mPosition);
                mItemView.scrollTo(0, 0);
                mItemView = null;
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mVelocityTracker.recycle();
    }
}
