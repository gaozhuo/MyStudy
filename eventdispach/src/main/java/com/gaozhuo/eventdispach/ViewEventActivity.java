package com.gaozhuo.eventdispach;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.lang.annotation.Retention;

public class ViewEventActivity extends Activity implements View.OnTouchListener, View.OnClickListener {
    private LinearLayout mLayout;
    private Button mButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view_event);

        mLayout = (LinearLayout) this.findViewById(R.id.mylayout);
        //mLayout.setEnabled(false);
        //mLayout.setClickable(true);
        mButton = (Button) this.findViewById(R.id.my_btn);
        //mButton.setEnabled(false);
        //mButton.setClickable(false);



       // mLayout.setOnTouchListener(this);
        //mButton.setOnTouchListener(this);

        //mLayout.setOnClickListener(this);
        //mButton.setOnClickListener(this);

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //Log.i("gaozhuo", "OnTouchListener--onTouch-- action=" + event.getAction() + " --" + v);
        return false;
    }

    @Override
    public void onClick(View v) {
        //Log.i("gaozhuo", "OnClickListener--onClick--" + v);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //Log.i("gaozhuo", "MainActivity--dispatchTouchEvent--action=" + ev.getAction());
        boolean b = super.dispatchTouchEvent(ev);
        Log.d("gaozhuo", "MainActivity dispatchTouchEvent b = " + b);

        return b;
    }

    @Override
    public void onUserInteraction() {
        //Log.i("gaozhuo", "MainActivity--onUserInteraction");
        super.onUserInteraction();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i("gaozhuo", "MainActivity--onTouchEvent--action="+event.getAction());
        return super.onTouchEvent(event);
    }

}