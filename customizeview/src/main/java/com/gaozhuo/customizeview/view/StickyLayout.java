package com.gaozhuo.customizeview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

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

    public StickyLayout(Context context) {
        this(context, null);
    }

    public StickyLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setOrientation(LinearLayout.VERTICAL);
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
        if(!mIsTopViewHidden || dy > 0 && isListViewTop()){
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("gaozhuo", "onTouchEvent");
        final int y = (int) event.getY();
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                int dy = y - mLastY;
                final int scrollY = getScrollY();

                /**
                 * 滑动的范围在0到 mTopViewHeight之间，已经滑动的距离问scrollY
                 * 所以还能滑动的距离为：0 -scrollY < -dy < mTopViewHeight - scrollY
                 */
                int deltaY = Math.min(Math.max(-scrollY, -dy), mTopViewHeight - scrollY);
                scrollBy(0, deltaY);

                if (mIsTopViewHidden && dy < 0) {//事件交给子控件处理
                    event.setAction(MotionEvent.ACTION_DOWN);
                    dispatchTouchEvent(event);
                }

                break;
            case MotionEvent.ACTION_UP:
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
    public void scrollBy(int x, int y) {
        super.scrollBy(x, y);
        mIsTopViewHidden = getScrollY() == mTopViewHeight;
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
}
