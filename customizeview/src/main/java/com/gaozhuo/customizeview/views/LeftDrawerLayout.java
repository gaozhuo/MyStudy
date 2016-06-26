package com.gaozhuo.customizeview.views;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * 自定义侧滑菜单
 *
 * @author gaozhuo
 * @date 2016/5/15
 */
public class LeftDrawerLayout extends ViewGroup {
    private static final int MIN_FLING_VELOCITY = 400; // dips per second
    private float mLeftMenuRatio = 0.75f;
    private View mLeftMenuView;
    private View mContentView;
    private ViewDragHelper mViewDrawHelper;

    public LeftDrawerLayout(Context context) {
        this(context, null);
    }

    public LeftDrawerLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LeftDrawerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        final float density = getResources().getDisplayMetrics().density;
        final float minVel = MIN_FLING_VELOCITY * density;

        mViewDrawHelper = ViewDragHelper.create(this, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return child == mLeftMenuView;
            }

            @Override
            public void onEdgeDragStarted(int edgeFlags, int pointerId) {
                super.onEdgeDragStarted(edgeFlags, pointerId);
                Log.d("gaozhuo", "onEdgeDragStarted");
                mViewDrawHelper.captureChildView(mLeftMenuView, pointerId);
            }

            @Override
            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                super.onViewPositionChanged(changedView, left, top, dx, dy);
                Log.d("gaozhuo", "onViewPositionChanged left=" + left);
                invalidate();
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                super.onViewReleased(releasedChild, xvel, yvel);
                MarginLayoutParams lp = (MarginLayoutParams) mLeftMenuView.getLayoutParams();
                int widthUsed = getPaddingLeft() + lp.leftMargin;

                float offset = (releasedChild.getWidth() + releasedChild.getLeft() - widthUsed) * 1.0f / releasedChild.getWidth();
                Log.d("gaozhuo", "offset=" + offset);
                int finalLeft = (xvel > 0 || xvel == 0 && offset > 0.5f) ? widthUsed : (widthUsed - mLeftMenuView.getWidth());
                int finalTop = getPaddingTop() + lp.topMargin;

                mViewDrawHelper.settleCapturedViewAt(finalLeft, finalTop);
                invalidate();
            }

            @Override
            public int getViewHorizontalDragRange(View child) {
                return mLeftMenuView.getWidth();
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                MarginLayoutParams lp = (MarginLayoutParams) mLeftMenuView.getLayoutParams();
                int widthUsed = getPaddingLeft() + lp.leftMargin;
                return Math.min(Math.max(-child.getWidth() + widthUsed, left), widthUsed);
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                MarginLayoutParams lp = (MarginLayoutParams) mLeftMenuView.getLayoutParams();
                return getPaddingTop() + lp.topMargin;
            }
        });
        mViewDrawHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
        mViewDrawHelper.setMinVelocity(minVel);
    }

    public void close() {
        MarginLayoutParams lp = (MarginLayoutParams) mLeftMenuView.getLayoutParams();
        int finalLeft = getPaddingLeft() + lp.leftMargin - mLeftMenuView.getWidth();
        int finalTop = getPaddingTop() + lp.topMargin;
        mViewDrawHelper.smoothSlideViewTo(mLeftMenuView, finalLeft, finalTop);
        invalidate();
    }

    public void open(){
        MarginLayoutParams lp = (MarginLayoutParams) mLeftMenuView.getLayoutParams();
        int finalLeft = getPaddingLeft() + lp.leftMargin;
        int finalTop = getPaddingTop() + lp.topMargin;
        mViewDrawHelper.smoothSlideViewTo(mLeftMenuView, finalLeft, finalTop);
        invalidate();

    }

    @Override
    public void computeScroll() {
        if (mViewDrawHelper.continueSettling(true)) {
            invalidate();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mViewDrawHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDrawHelper.processTouchEvent(event);
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        final int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        measureChildWithMargins(mContentView, widthMeasureSpec, 0, heightMeasureSpec, 0);

        int widthUsed = (int) (widthSize * (1 - mLeftMenuRatio));
        measureChildWithMargins(mLeftMenuView, widthMeasureSpec, widthUsed, heightMeasureSpec, 0);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        MarginLayoutParams lp = (MarginLayoutParams) mContentView.getLayoutParams();
        int left = getPaddingLeft() + lp.leftMargin;
        int top = getPaddingTop() + lp.topMargin;
        mContentView.layout(left, top, left + mContentView.getMeasuredWidth(), top + mContentView.getMeasuredHeight());

        lp = (MarginLayoutParams) mLeftMenuView.getLayoutParams();
        left = getPaddingLeft() + lp.leftMargin - mLeftMenuView.getMeasuredWidth();
        top = getPaddingTop() + lp.topMargin;
        mLeftMenuView.layout(left, top, left + mLeftMenuView.getMeasuredWidth(), top + mLeftMenuView.getMeasuredHeight());
        Log.d("gaozhuo", "top=" + mLeftMenuView.getTop());
        Log.d("gaozhuo", "left=" + mLeftMenuView.getLeft());
        Log.d("gaozhuo", "bottom=" + mLeftMenuView.getBottom());
        Log.d("gaozhuo", "right=" + mLeftMenuView.getRight());
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mContentView = getChildAt(0);
        mLeftMenuView = getChildAt(1);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
}
