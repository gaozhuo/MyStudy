package com.gaozhuo.customizeview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.gaozhuo.customizeview.views.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private List<String> mData = new ArrayList<>();
    private MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
    }

    private void initData() {
        mData.add("简单圆");
        mData.add("浮窗");
        mData.add("HorizontalScrollViewEx");
        mData.add("FlowLayout");
        mData.add("Lock View");
        mData.add("StickyLayout");
        mData.add("LeftDrawerLayout");

    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        mAdapter = new MyAdapter(this, mData);
        mAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                Log.d("gaozhuo", "position=" + position);
                MainActivity.this.onItemClick(position);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    private void onItemClick(int position) {
        Intent intent = null;
        switch (position) {
            case 0:
                intent = new Intent(MainActivity.this, CircleActivity.class);
                startActivity(intent);
                break;
            case 1:
                intent = new Intent(MainActivity.this, FloatingWindowActivity.class);
                startActivity(intent);
                break;
            case 2:
                intent = new Intent(MainActivity.this, HorizontalScrollViewExActivity.class);
                startActivity(intent);
                break;
            case 3:
                intent = new Intent(MainActivity.this, FlowLayoutActivity.class);
                startActivity(intent);
                break;
            case 4:
                intent = new Intent(MainActivity.this, LockViewActivity.class);
                startActivity(intent);
                break;
            case 5:
                intent = new Intent(MainActivity.this, StickyLayoutActivity.class);
                startActivity(intent);
            case 6:
                intent = new Intent(MainActivity.this, LeftDrawerLayoutActivity.class);
                startActivity(intent);
                break;
        }
    }
}
