package com.gaozhuo.customizeview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by gaozhuo on 2016/4/6.
 */
public class FlowLayout extends ViewGroup {
    private int mChildCount;

    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int childCount = getChildCount();
        mChildCount = childCount;
        if (childCount == 0) {
            setMeasuredDimension(0, 0);
        }
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        int lineMaxHeight = 0;
        int lineWidth = 0;
        int measuredHeight = 0;
        int measuredWidth = 0;
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
            if (lineWidth + childWidth <= widthSpecSize) {
                lineWidth += childWidth;
                if (childHeight > lineMaxHeight) {
                    lineMaxHeight = childHeight;
                }
            } else {//换行
                if (lineWidth > measuredWidth) {
                    measuredWidth = lineWidth;
                }

                measuredHeight += lineMaxHeight;
                lineWidth = childWidth;
                lineMaxHeight = childHeight;
            }
        }

        if (lineWidth > measuredWidth) {//处理最后一行
            measuredWidth = lineWidth;
        }
        measuredHeight += lineMaxHeight;

//        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
//            setMeasuredDimension(measuredWidth, measuredHeight);
//        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
//            setMeasuredDimension(measuredWidth, heightSpecSize);
//        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
//            setMeasuredDimension(widthSpecSize, measuredHeight);
//        } else {
//            Log.d("gaozhuo", "match, match");
//            setMeasuredDimension(widthSpecSize, heightSpecSize);
//        }

        measuredWidth = Math.min(measuredWidth, widthSpecSize);
        measuredHeight = Math.min(measuredHeight, heightSpecSize);

        setMeasuredDimension(widthSpecMode == MeasureSpec.AT_MOST ? measuredWidth : widthSpecSize, heightSpecMode == MeasureSpec.AT_MOST ? measuredHeight : heightSpecSize);

    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = mChildCount;
        int widthSpecSize = getMeasuredWidth();

        int lineMaxHeight = 0;
        int lineWidth = 0;
        int measuredHeight = 0;
        int measuredWidth = 0;
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
            if (lineWidth + childWidth <= widthSpecSize) {
                lineWidth += childWidth;
                if (childHeight > lineMaxHeight) {
                    lineMaxHeight = childHeight;
                }
            } else {//换行
                if (lineWidth > measuredWidth) {
                    measuredWidth = lineWidth;
                }
                measuredHeight += lineMaxHeight;
                lineWidth = childWidth;
                lineMaxHeight = childHeight;
            }

            int left = lineWidth - childWidth + lp.leftMargin;
            int top = measuredHeight + lp.topMargin;
            int right = left + child.getMeasuredWidth();
            int bottom = top + child.getMeasuredHeight();
            child.layout(left, top, right, bottom);
        }

    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
}
