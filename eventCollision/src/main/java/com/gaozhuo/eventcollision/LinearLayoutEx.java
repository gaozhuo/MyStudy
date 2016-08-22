package com.gaozhuo.eventcollision;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Created by gaozhuo on 2016/8/22.
 */
public class LinearLayoutEx extends LinearLayout {
    private Button mButton;

    public LinearLayoutEx(Context context) {
        super(context);
        init();
    }

    public LinearLayoutEx(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LinearLayoutEx(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mButton = new Button(getContext());
        mButton.setText("hahaha");
        mButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("gaozhuo", "mButton click");
            }
        });

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        mButton.layout(200,400,600,800);
    }

//    @Override
//    protected void dispatchDraw(Canvas canvas) {
//        super.dispatchDraw(canvas);
//        mButton.draw(canvas);
//    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //return super.dispatchTouchEvent(ev);
        getChildAt(0).dispatchTouchEvent(ev);
        return true;
    }
}
