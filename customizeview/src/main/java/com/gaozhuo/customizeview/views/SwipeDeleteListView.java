package com.gaozhuo.customizeview.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ListView;

/**
 * 带滑动删除的ListView
 *
 * @author gaozhuo
 * @date 2016/5/15
 */
public class SwipeDeleteListView extends ListView {
    private View mItemView;//滑动的item
    private int mLastX;
    private int mLastY;
    private int mTouchSlop;

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
        Log.d("gaozhuo", "mTouchSlop" + mTouchSlop);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = x;
                mLastY = y;

                getCurrentItem(ev);

                break;
            case MotionEvent.ACTION_MOVE:
                int dx = x- mLastX;
                int dy = y - mLastY;

                Log.d("gaozhuo", "dx=" +dx);
                Log.d("gaozhuo", "dy=" +dy);


                if(isSwipe(dx,dy)){
                    Log.d("gaozhuo", "isSwipe");
                    mItemView.scrollBy(-dx, 0);

                    mLastX = x;
                    mLastY = y;
                    return true;

                }


                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        mLastX = x;
        mLastY = y;
        return super.onTouchEvent(ev);
    }

    private boolean isSwipe(int dx, int dy) {
        //mItemView != null 且横向滑动时才可以滑动item
        if(mItemView != null && Math.abs(dx) > mTouchSlop && Math.abs(dy) < mTouchSlop){
            return true;
        }
        return false;
    }

    private void getCurrentItem(MotionEvent ev) {
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        int position = pointToPosition(x, y);
        if(position == ListView.INVALID_POSITION){
            return;
        }
        mItemView = getChildAt(position -  getFirstVisiblePosition());
    }
}
