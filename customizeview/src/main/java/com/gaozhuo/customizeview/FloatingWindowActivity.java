package com.gaozhuo.customizeview;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.gaozhuo.commonlibrary.utils.DeviceUtils;
import com.gaozhuo.customizeview.view.FloatingView;

public class FloatingWindowActivity extends AppCompatActivity {
    private FloatingView mFloatingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floating_window);
        mFloatingView = new FloatingView(FloatingWindowActivity.this);
        findViewById(R.id.show).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //mFloatingView.show();
                Log.d("gaozhuo", "model=" + DeviceUtils.getModel());

            }
        });

        findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFloatingView.dismiss();

            }
        });
    }


}
