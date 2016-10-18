package com.gaozhuo.customizeview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.gaozhuo.customizeview.adapter.GalleryAdapter;
import com.gaozhuo.customizeview.views.GridDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class GalleryActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private List<String> mData = new ArrayList<>();
    private GalleryAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        initData();
        initView();
    }

    private void initData() {
        for (int i = 0; i < 50; i++) {
            mData.add(BitmapSampleUtil.getImageUrl());
        }
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new GridDividerItemDecoration(2, 3));
        mAdapter = new GalleryAdapter(this, mData);
        mAdapter.setOnItemClickListener(new GalleryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
                ImageViewerActivity.openUI(GalleryActivity.this, mData.get(position), imageView);
//                Intent intent = new Intent(GalleryActivity.this, MultiImageViewerActivity.class);
//                intent.putExtra("position", position);
//                intent.putStringArrayListExtra("imageUrls", (ArrayList<String>) mData);
//                startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }

}
