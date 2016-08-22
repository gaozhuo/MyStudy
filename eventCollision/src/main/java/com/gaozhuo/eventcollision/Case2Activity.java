package com.gaozhuo.eventcollision;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class Case2Activity extends Activity {
    private LinearLayout mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case2);
        mLayout = (LinearLayout) findViewById(R.id.layout);
        Button button = new Button(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("gaozhuo","button click");
            }
        });
        button.setText("hahaha");
        ViewGroup.MarginLayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.topMargin = 200;
        Log.d("gaozhuo", "lp1=" + params);
        mLayout.addView(button, params);
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) button.getLayoutParams();
        Log.d("gaozhuo", "lp2=" + lp);

    }
}
