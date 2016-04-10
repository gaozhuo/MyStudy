package com.gaozhuo.eventdispach;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * Created by gaozhuo on 2016/3/13.
 */
public class TestImageView extends ImageView {

    public TestImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.i("gaozhuo", "dispatchTouchEvent-- action=" + event.getAction());
        return super.dispatchTouchEvent(event);
        //super.dispatchTouchEvent(event);
        //return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i("gaozhuo", "onTouchEvent-- action=" + event.getAction());
        return super.onTouchEvent(event);
        //return false;
        //super.onTouchEvent(event);
        //return true;
    }
}
