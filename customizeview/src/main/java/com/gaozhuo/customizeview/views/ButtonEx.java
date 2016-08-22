package com.gaozhuo.customizeview.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;

/**
 * Created by gaozhuo on 2016/8/21.
 */
public class ButtonEx extends Button {
    public ButtonEx(Context context) {
        super(context);
    }

    public ButtonEx(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ButtonEx(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("gaozhuo", "ButtonEx onTouchEvent action=" + event.getActionMasked());
        boolean b = super.onTouchEvent(event);
        Log.d("gaozhuo", "b=" + b);

        return b;
    }
}
