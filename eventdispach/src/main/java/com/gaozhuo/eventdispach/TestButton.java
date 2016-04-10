package com.gaozhuo.eventdispach;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;

public class TestButton extends Button {

    public TestButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.i("gaozhuo", "TestButton dispatchTouchEvent-- action=" + event.getAction());
        return super.dispatchTouchEvent(event);
        //super.dispatchTouchEvent(event);
        //return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i("gaozhuo", "TestButton onTouchEvent-- action=" + event.getAction());
        return super.onTouchEvent(event);

    }


}