package com.gaozhuo.customizeview;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gaozhuo.commonlibrary.utils.DeviceUtils;
import com.gaozhuo.customizeview.views.HorizontalScrollViewEx;

import java.util.ArrayList;

public class HorizontalScrollViewExActivity extends AppCompatActivity {
    private HorizontalScrollViewEx mHorizontalScrollViewEx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontal_scroll_view_ex);
        initView();
    }

    private void initView() {
        mHorizontalScrollViewEx = (HorizontalScrollViewEx) findViewById(R.id.horizontalScroll);
        final int screenWidth = DeviceUtils.getScreenWidth(this);
        final int screenHeight = DeviceUtils.getScreenHeight(this);
        for (int i = 0; i < 3; i++) {
            ViewGroup layout = (ViewGroup) getLayoutInflater().inflate(R.layout.content_layout, mHorizontalScrollViewEx, false);
            layout.getLayoutParams().width = screenWidth;
            TextView textView = (TextView) layout.findViewById(R.id.title);
            textView.setText("page " + (i + 1));
            createList(layout);
            mHorizontalScrollViewEx.addView(layout);
        }
    }

    private void createList(ViewGroup layout) {
        ListView listView = (ListView) layout.findViewById(R.id.list);
        ArrayList<String> datas = new ArrayList<String>();
        for (int i = 0; i < 50; i++) {
            datas.add("name " + i);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, datas);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "click item", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
