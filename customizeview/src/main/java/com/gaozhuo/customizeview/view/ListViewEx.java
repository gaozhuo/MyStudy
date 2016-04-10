package com.gaozhuo.customizeview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * Created by gaozhuo on 2016/3/19.
 */
public class ListViewEx extends ListView {
    public ListViewEx(Context context) {
        super(context);
    }

    public ListViewEx(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        Log.d("gaozhuo", "ListView dispatchTouchEvent");
//        switch (ev.getActionMasked()) {
//            case MotionEvent.ACTION_DOWN:
//                super.dispatchTouchEvent(ev);
//                return true;
//            case MotionEvent.ACTION_MOVE:
//                super.dispatchTouchEvent(ev);
//                return true;
//            case MotionEvent.ACTION_UP:
//                break;
//        }
//
//        boolean b = super.dispatchTouchEvent(ev);
//        Log.d("gaozhuo", "b=" + b);
//
//        return b;
//    }
//
    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        super.onTouchEvent(ev);

        return false;
    }
}
