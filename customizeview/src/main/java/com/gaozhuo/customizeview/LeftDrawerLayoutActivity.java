package com.gaozhuo.customizeview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.gaozhuo.customizeview.views.LeftDrawerLayout;

public class LeftDrawerLayoutActivity extends AppCompatActivity {
    private LeftDrawerLayout mLeftDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_left_drawer_layout);
        mLeftDrawerLayout = (LeftDrawerLayout) findViewById(R.id.leftDrawerLayout);
        findViewById(R.id.textView1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLeftDrawerLayout.close();
            }
        });
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLeftDrawerLayout.open();
            }
        });

    }
}
