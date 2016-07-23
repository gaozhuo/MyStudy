package com.gaozhuo.customizeview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.gaozhuo.customizeview.views.SwipeDeleteListView;

import java.util.ArrayList;
import java.util.List;

public class SwipeDeleteListViewActivity extends AppCompatActivity {
    private SwipeDeleteListView mListView;
    private List<String> mData = new ArrayList<>();
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_delete_list_view);

        initData();

        initView();


    }

    private void initData() {
        for (int i = 0; i < 5; i++) {
            mData.add("item " + i);
        }
    }

    private void initView() {
        mListView = (SwipeDeleteListView) findViewById(R.id.listView);
        mAdapter = new ArrayAdapter<String>(this, R.layout.swipe_delete_list_item, R.id.title, mData);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemRemovedListener(new SwipeDeleteListView.OnItemRemovedListener() {
            @Override
            public void onItemRemoved(int position) {
                mData.remove(position);
                mAdapter.notifyDataSetChanged();
            }
        });
    }
}
