package com.gaozhuo.eventcollision;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

/**
 * Created by gaozhuo on 2016/3/19.
 */
public class HorizontalScrollViewEx extends HorizontalScrollView {
    private float mPreX;
    private float mPreY;

    public HorizontalScrollViewEx(Context context) {
        super(context);
    }

    public HorizontalScrollViewEx(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.d("gaozhuo", "HorizontalScrollView action=" + ev.getAction());
        return super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        switch (ev.getActionMasked()) {
//            case MotionEvent.ACTION_DOWN:
//                mPreX = ev.getX();
//                mPreY = ev.getY();
//               break;
//            case MotionEvent.ACTION_MOVE:
//                float x = ev.getX();
//                float y = ev.getY();
//                float offsetX = x - mPreX;
//                float offsetY = y - mPreY;
//                mPreX = x;
//                mPreY = y;
//                if(Math.abs(offsetX) >= Math.abs(10.0 * offsetY)){
//                    return super.onInterceptTouchEvent(ev);
//                }
//                super.onInterceptTouchEvent(ev);
//                return false;
//            case MotionEvent.ACTION_UP:
//                break;
//        }
        return super.onInterceptTouchEvent(ev);
    }
}
