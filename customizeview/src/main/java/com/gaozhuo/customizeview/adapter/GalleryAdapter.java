package com.gaozhuo.customizeview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gaozhuo.customizeview.R;

import java.util.List;

/**
 *
 *
 * @author gaozhuo
 * @date 2016/5/15
 */
public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder> {
    private Context mContext;
    private List<String> mData;
    private LayoutInflater mInflater;
    private OnItemClickListener mOnItemClickListener;

    public GalleryAdapter(Context context, List<String> data) {
        mContext = context;
        mData = data;
        mInflater = LayoutInflater.from(context);
    }


    @Override
    public GalleryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recycler_view_item_gallery, parent, false);
        return new GalleryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GalleryViewHolder holder, int position) {
        String url = mData.get(position);
        Log.d("gaozhuo", "imageUrl=" + url);
        Glide.with(mContext).load(url).into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class GalleryViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;

        public GalleryViewHolder(final View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.imageView);
            if (mOnItemClickListener != null) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickListener.onItemClick(v, getLayoutPosition());
                    }
                });
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }
}
