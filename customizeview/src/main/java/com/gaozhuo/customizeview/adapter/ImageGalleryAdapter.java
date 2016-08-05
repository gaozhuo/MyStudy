package com.gaozhuo.customizeview.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.gaozhuo.customizeview.R;

import java.util.List;

import uk.co.senab.photoview.PhotoView;


public class ImageGalleryAdapter extends PagerAdapter {

    private final List<String> mImageUrls;
    private final Context mContext;


    public ImageGalleryAdapter(Context context, List<String> imageItems) {
        mImageUrls = imageItems;
        mContext = context;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.gallery_image, null);
        final PhotoView photoView = (PhotoView) view.findViewById(R.id.iv_photo);

        Glide.with(mContext).load(mImageUrls.get(position)).into(photoView);

        container.addView(view, 0);

        return view;
    }

    @Override
    public int getCount() {
        return mImageUrls.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

}
