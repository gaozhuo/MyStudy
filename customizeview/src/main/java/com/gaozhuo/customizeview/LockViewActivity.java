package com.gaozhuo.customizeview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.gaozhuo.customizeview.view.LockView;

public class LockViewActivity extends AppCompatActivity {
    private LockView mLockView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_view);
        mLockView = (LockView) findViewById(R.id.lockView);
        mLockView.setOnPasswordListener(new LockView.OnPasswordListener() {
            @Override
            public void password(String password) {
                Toast.makeText(LockViewActivity.this, password, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
