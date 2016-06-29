package com.gaozhuo.customizeview.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

/**
 * @author gaozhuo
 * @date 2016/5/15
 */
public class SmoothImageView extends ImageView {
    private static final String TAG = SmoothImageView.class.getSimpleName();
    private Rect mSrcRect;
    private TransformParam mStartTransformParam;
    private TransformParam mEndTransformParam;
    private TransformParam mTransformParam;

    private boolean mIsAnimationStarting;

    public SmoothImageView(Context context) {
        super(context);
    }

    public SmoothImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SmoothImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setSrcRect(Rect rect) {
        mSrcRect = rect;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (!mIsAnimationStarting) {//没有动画
            super.onDraw(canvas);
            return;
        }

        Drawable drawable = getDrawable();
        Log.d(TAG, "drawable=" + drawable);
        if (drawable == null) {
            return;
        }
        if (!(drawable instanceof BitmapDrawable)) {
            return;
        }

        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        if (bitmap == null) {
            return;
        }

        if(mStartTransformParam == null){
            calculateStartTransformParam(bitmap);
        }

        if(mEndTransformParam == null){
            calculateEndTransformParam(bitmap);
        }

    }

    /**
     * 计算最终TransformParam
     *
     * @param bitmap
     */
    private void calculateEndTransformParam(Bitmap bitmap) {
        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();
        int imageViewWidth = getWidth();
        int imageViewHeight = getHeight();

        float endScale = 0;
        if (imageViewWidth / bitmapWidth < imageViewHeight / bitmapHeight) {
            endScale = imageViewWidth / bitmapWidth;
        } else {
            endScale = imageViewHeight / bitmapHeight;
        }

        //最终展视的宽高
        int finalWidth = (int) (bitmapWidth * endScale);
        int finalHeight = (int) (bitmapHeight * endScale);

        //图片展视在ImageView中间
        int top = getTop() + (imageViewHeight - finalHeight) / 2;
        int left = getLeft() + (imageViewWidth - finalWidth) / 2;

        Rect endRect = new Rect(left, top, left + finalWidth, top + finalHeight);

        mEndTransformParam = new TransformParam(endRect, endScale);
    }

    /**
     * 计算开始TransformParam
     *
     * @param bitmap
     */
    private void calculateStartTransformParam(Bitmap bitmap) {
        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();
        int imageViewWidth = mSrcRect.width();
        int imageViewHeight = mSrcRect.height();

        float startScale = 0;
        if (imageViewWidth / bitmapWidth < imageViewHeight / bitmapHeight) {
            startScale = imageViewHeight / bitmapHeight;
        } else {
            startScale = imageViewWidth / bitmapWidth;
        }
        mStartTransformParam = new TransformParam(mSrcRect, startScale);
    }


    public void startAnimationIn() {
        mIsAnimationStarting = true;
    }

    public void startAnimationOut() {
        mIsAnimationStarting = true;

    }

    class TransformParam {
        public Rect rect;
        public float scale;

        public TransformParam(Rect rect, float scale) {
            this.rect = rect;
            this.scale = scale;
        }
    }
}
