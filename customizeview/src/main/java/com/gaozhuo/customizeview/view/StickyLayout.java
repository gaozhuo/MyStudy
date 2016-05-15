package com.gaozhuo.customizeview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * 布局
 *
 * @author gaozhuo
 * @date 2016/5/15
 */
public class StickyLayout extends LinearLayout {
    private int mLastY;

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
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int y = (int) ev.getY();
        boolean intercepted = false;
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                intercepted = false;
                break;
            case MotionEvent.ACTION_MOVE:
                if (needTouchEvent()) {
                    intercepted = true;
                } else {
                    intercepted = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                intercepted = false;
                break;
        }
        mLastY = y;
        return intercepted;
    }

    private boolean needTouchEvent() {
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int y = (int) event.getY();
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d("gaozhuo", "scrollY=" + getScrollY());
                View headerView = getChildAt(0);
                //Log.d("gaozhuo", "bottom=" + headerView.getTranslationY());
               // Log.d("gaozhuo", "height=" + headerView.getHeight());

                int dy = y - mLastY;
                if(getScrollY() >= 0){

                    //scrollBy(0, -dy);
                }

                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        mLastY = y;

        return true;
    }
}
