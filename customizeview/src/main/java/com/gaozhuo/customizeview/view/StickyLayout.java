package com.gaozhuo.customizeview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.OverScroller;

/**
 * 布局
 *
 * @author gaozhuo
 * @date 2016/5/15
 */
public class StickyLayout extends LinearLayout {
    private int mLastY;
    private View mTopView;
    private ListView mListView;
    private int mTopViewHeight;
    private boolean mIsTopViewHidden;
    private int mMinFlingVelocity;
    private int mMaxFlingVelocity;
    private int mTouchSlop;
    private OverScroller mScroller;
    private VelocityTracker mVelocityTracker;

    public StickyLayout(Context context) {
        this(context, null);
    }

    public StickyLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setOrientation(LinearLayout.VERTICAL);
        mMinFlingVelocity = ViewConfiguration.get(getContext()).getScaledMinimumFlingVelocity();
        mMaxFlingVelocity = ViewConfiguration.get(getContext()).getScaledMaximumFlingVelocity();
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        mScroller = new OverScroller(getContext());
        mVelocityTracker = VelocityTracker.obtain();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mVelocityTracker.recycle();
        mVelocityTracker = null;
        mScroller = null;
    }

    @Override
    public boolean dispatchTouchEvent(final MotionEvent ev) {
        Log.d("gaozhuo", "dispatchTouchEvent event=" + ev.getActionMasked());
        final int y = (int) ev.getY();
        int dy = y - mLastY;

        /**
         * ListView调用了getParent().requestDisallowInterceptTouchEvent(true)
         * 导致父控件无法拦截事件
         */
        if (mIsTopViewHidden && dy > 0 && isListViewTop()) {
            requestDisallowInterceptTouchEvent(false);
        }

        boolean result = super.dispatchTouchEvent(ev);

        mLastY = y;

        return result;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d("gaozhuo", "onInterceptTouchEvent");
        boolean intercepted = false;
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                intercepted = false;
                break;
            case MotionEvent.ACTION_MOVE:
                if (isParentNeedTouchEvent(ev)) {
                    intercepted = true;
                } else {
                    intercepted = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                intercepted = false;
                break;
        }

        return intercepted;
    }

    /**
     * 父控件是否需要事件
     *
     * @param ev
     * @return
     */
    private boolean isParentNeedTouchEvent(MotionEvent ev) {
        final int y = (int) ev.getY();
        int dy = y - mLastY;

        /**
         * 两种情况下父控件需要事件：1，topView没有完全隐藏时；2，topView完全隐藏并且
         * ListView已滑到第一个item的顶部且继续下滑时
         */
        if (!mIsTopViewHidden || dy > 0 && isListViewTop()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("gaozhuo", "onTouchEvent");
        mVelocityTracker.addMovement(event);
        final int y = (int) event.getY();
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                if(!mScroller.isFinished()){
                    mScroller.abortAnimation();
                }
                mLastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                int dy = y - mLastY;
                final int scrollY = getScrollY();

                scrollBy(0, -dy);

                if (mIsTopViewHidden && dy < 0) {//事件交给子控件处理
                    event.setAction(MotionEvent.ACTION_DOWN);
                    dispatchTouchEvent(event);
                }

                break;
            case MotionEvent.ACTION_UP:
                mVelocityTracker.computeCurrentVelocity(1000, mMaxFlingVelocity);
                int yVelocity = (int) mVelocityTracker.getYVelocity();
                if (Math.abs(yVelocity) > mMinFlingVelocity) {
                    mScroller.fling(0, getScrollY(), 0, -yVelocity, 0, 0, 0, mTopViewHeight);
                    invalidate();
                }
                break;
        }
        return true;
    }

    private boolean isListViewTop() {
        int position = mListView.getFirstVisiblePosition();
        if (position == 0) {
            View firstItem = mListView.getChildAt(position);
            if (firstItem != null && firstItem.getTop() == 0) {
                return true;
            }
        }
        return false;
    }


    @Override
    public void scrollTo(int x, int y) {
        /**
         * 滑动的范围在0到 mTopViewHeight之间
         */
        super.scrollTo(x, Math.min(Math.max(0, y), mTopViewHeight));

        final int scrollY = getScrollY();
        mIsTopViewHidden = scrollY == mTopViewHeight;
        //设置topView透明度
        float alpha = (mTopViewHeight - scrollY) / (float) mTopViewHeight;
        mTopView.setAlpha(alpha);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        ViewGroup.LayoutParams lp = (LayoutParams) mListView.getLayoutParams();
        lp.height = getMeasuredHeight();
        mListView.setLayoutParams(lp);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTopViewHeight = mTopView.getMeasuredHeight();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTopView = getChildAt(0);
        mListView = (ListView) getChildAt(1);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            Log.d("gaozhuo", "computeScroll=" + mScroller.getCurrY());
            scrollTo(0, mScroller.getCurrY());
            invalidate();
        }
    }
}
