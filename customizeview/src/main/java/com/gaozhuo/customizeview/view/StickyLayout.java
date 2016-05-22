package com.gaozhuo.customizeview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
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
    private View mHeaderView;
    private ListView mListView;
    private boolean mIntercepted;

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
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = y;
                mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(AbsListView view, int scrollState) {

                    }

                    @Override
                    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                        //Log.d("gaozhuo", "position=" + mListView.getChildAt(0).getTop());
                        if (firstVisibleItem == 0) {
                            int top = mListView.getChildAt(firstVisibleItem).getTop();
                            if (top == 0) {
                                mIntercepted = true;
                            } else {
                                mIntercepted = false;
                            }

                        } else {
                            mIntercepted = false;
                        }

                    }
                });


                mListView.setOnTouchListener(new OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        Log.d("gaozhuo", "listview onTouch event=" + event.getActionMasked());
                        return false;
                    }
                });
                break;
            case MotionEvent.ACTION_MOVE:
                if (getScrollY() == mHeaderView.getHeight()) {

                }
                break;
        }

        int dy = y - mLastY;
        if(getScrollY() == mHeaderView.getHeight() && dy  >0 && mIntercepted){
            requestDisallowInterceptTouchEvent(false);
        }

        return super.dispatchTouchEvent(ev);
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
                if (needTouchEvent(ev)) {
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

    private boolean needTouchEvent(MotionEvent ev) {
        final int y = (int) ev.getY();
        int dy = y - mLastY;
        if (getScrollY() == mHeaderView.getHeight() && dy < 0) {
            Log.d("gaozhuo", "hahaha");
            return false;
        }

        Log.d("gaozhuo", "mIntercepted=" + mIntercepted);

        if(getScrollY() == mHeaderView.getHeight() && dy  >0 && mIntercepted){
            return true;
        }
        return true;
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
                 * 滑动的范围在0到headerView.getHeight()之间
                 */
                if (dy < 0) {//向上滑动
                    int deltaY = Math.min(-dy, mHeaderView.getHeight() - scrollY);
                    scrollBy(0, deltaY);
                } else if (dy > 0) {//向下滑动
                    int deltaY = Math.min(dy, scrollY);
                    scrollBy(0, -deltaY);
                }

                if (scrollY == mHeaderView.getHeight() && dy < 0) {
                    Log.d("gaozhuo", "hahaha222");
                    event.setAction(MotionEvent.ACTION_DOWN);
                    dispatchTouchEvent(event);
                }

                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        mLastY = y;
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        ViewGroup.LayoutParams lp = (LayoutParams) mListView.getLayoutParams();
        lp.height = getMeasuredHeight();
        mListView.setLayoutParams(lp);
        Log.d("gaozhuo", "lp.height=" + lp.height);

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mHeaderView = getChildAt(0);
        mListView = (ListView) getChildAt(1);
    }
}
