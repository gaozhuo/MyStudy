package com.gaozhuo.eventdispach;


import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class TestLinearLayout extends LinearLayout {
    private int count = 0;

    public TestLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i("gaozhuo", "TestLinearLayout onInterceptTouchEvent-- action=" + ev.getAction());
        //return super.onInterceptTouchEvent(ev);

        if(ev.getActionMasked() == MotionEvent.ACTION_DOWN){
            return false;
        }else {
            return true;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        //Log.i("gaozhuo", "TestLinearLayout dispatchTouchEvent-- action=" + event.getAction());
        if(event.getActionMasked() != MotionEvent.ACTION_DOWN){
            event.setAction(MotionEvent.ACTION_CANCEL);
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i("gaozhuo", "TestLinearLayout onTouchEvent-- action=" + event.getAction());
        return super.onTouchEvent(event);
    }
}
