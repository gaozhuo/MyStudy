package com.gaozhuo.customizeview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class StickyLayoutActivity extends AppCompatActivity {
    private ListView mListView;
    private List<String> mData = new ArrayList<>();
    private BaseAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticky_layout);
        initData();
        initView();
    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.listView);
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mData);
        mListView.setAdapter(mAdapter);
    }

    private void initData(){
        for(int i = 0; i< 50; i++){
            mData.add("item " + i);
        }
    }
}
