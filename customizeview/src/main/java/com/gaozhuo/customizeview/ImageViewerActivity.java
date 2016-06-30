package com.gaozhuo.customizeview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.gaozhuo.commonlibrary.utils.DeviceUtils;
import com.gaozhuo.customizeview.views.SmoothImageView;

public class ImageViewerActivity extends AppCompatActivity {
    private static final String TAG = ImageViewerActivity.class.getSimpleName();
    private SmoothImageView mImageView;
    private String mImageUrl;
    private Rect mSrcRect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);

        initVariables();

        initView();

    }

    private void initVariables() {
        mImageUrl = getIntent().getStringExtra("imageUrl");
        mSrcRect = getIntent().getParcelableExtra("srcRect");
        mSrcRect.offset(0, -DeviceUtils.getStatusBarHeight(this));
    }

    private void initView() {
        mImageView = (SmoothImageView) findViewById(R.id.imageView);
        mImageView.setSrcRect(mSrcRect);
        mImageView.transformIn();
        Log.d(TAG, "imageUrl=" + mImageUrl);
        Glide.with(this).load(mImageUrl).into(mImageView);
    }

    public static void openUI(Context context, String imageUrl, ImageView srcImageView) {
        Intent intent = new Intent(context, ImageViewerActivity.class);
        intent.putExtra("imageUrl", imageUrl);
        if (srcImageView != null) {
            int[] location = new int[2];
            srcImageView.getLocationOnScreen(location);
            Rect srcRect = new Rect(location[0], location[1], location[0] + srcImageView.getWidth(), location[1] + srcImageView.getHeight());
            intent.putExtra("srcRect", srcRect);
        }
        context.startActivity(intent);
    }
}
