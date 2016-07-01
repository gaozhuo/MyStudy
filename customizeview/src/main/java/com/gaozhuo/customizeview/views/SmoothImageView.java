package com.gaozhuo.customizeview.views;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
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
    private static final int STATE_TRANSFORM_NONE = 0;//没有动画
    private static final int STATE_TRANSFORM_IN = 1;//进入动画
    private static final int STATE_TRANSFORM_OUT = 2;//退出动画

    private Rect mSrcRect;
    private TransformParam mStartTransformParam;
    private TransformParam mEndTransformParam;
    private TransformParam mTransformParam;
    private boolean mIsInited;
    private int mState = STATE_TRANSFORM_NONE;
    private int mDrawableWidth;
    private int mDrawableHeight;
    private boolean mHasAnimation;
    private Matrix mMatrix = new Matrix();

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
        if (!mHasAnimation) {//没有动画
            super.onDraw(canvas);
            return;
        }

        Drawable drawable = getDrawable();
        if (drawable == null) {
            return;
        }


        if (!mIsInited) {
            mDrawableWidth = drawable.getIntrinsicWidth();
            mDrawableHeight = drawable.getIntrinsicHeight();
            if (mDrawableWidth <= 0 || mDrawableHeight <= 0) {
                return;
            }
            initTransFormParam();
            mIsInited = true;
        }

        int dx = (int) ((mTransformParam.rect.width() - mDrawableWidth * mTransformParam.scale) * 0.5f);
        int dy = (int) ((mTransformParam.rect.height() - mDrawableHeight * mTransformParam.scale) * 0.5f);
        mMatrix.reset();
        //mMatrix.setScale(mTransformParam.scale, mTransformParam.scale);
        mMatrix.postTranslate(dx, dy);

        canvas.save();
        canvas.translate(mTransformParam.rect.left, mTransformParam.rect.top);
        canvas.clipRect(0, 0, mTransformParam.rect.width(), mTransformParam.rect.height());

        //canvas.concat(mMatrix);
        canvas.translate(dx, dy);
        canvas.scale(mTransformParam.scale, mTransformParam.scale);
        Rect b = drawable.getBounds();
        Log.d("gaozhuo","b1=" + b.toString());
        drawable.draw(canvas);
        Log.d("gaozhuo","b2=" + b.toString());
        canvas.restore();

    }


    private void initTransFormParam() {
        if (mStartTransformParam == null) {
            calculateStartTransformParam();
        }
        if (mEndTransformParam == null) {
            calculateEndTransformParam();
        }

        if (mState == STATE_TRANSFORM_IN) {
            mTransformParam = new TransformParam(mStartTransformParam);
        } else if (mState == STATE_TRANSFORM_OUT) {
            mTransformParam = new TransformParam(mEndTransformParam);
        }

    }

    /**
     * 计算最终TransformParam
     */
    private void calculateEndTransformParam() {
        int drawableWidth = mDrawableWidth;
        int drawableHeight = mDrawableHeight;
        int imageViewWidth = getWidth();
        int imageViewHeight = getHeight();

        float endScale = 0;
        float scaleX = (float) imageViewWidth / drawableWidth;
        float scaleY = (float) imageViewHeight / drawableHeight;
        if (scaleX < scaleY) {
            endScale = scaleX;
        } else {
            endScale = scaleY;
        }

        //最终展视的宽高
        int finalWidth = (int) (drawableWidth * endScale);
        int finalHeight = (int) (drawableHeight * endScale);

        //图片展视在ImageView中间
        int top = getTop() + (imageViewHeight - finalHeight) / 2;
        int left = getLeft() + (imageViewWidth - finalWidth) / 2;

        Rect endRect = new Rect(left, top, left + finalWidth, top + finalHeight);

        mEndTransformParam = new TransformParam(endRect, endScale);
    }

    /**
     * 计算开始TransformParam
     */
    private void calculateStartTransformParam() {
        int drawableWidth = mDrawableWidth;
        int drawableHeight = mDrawableHeight;
        int imageViewWidth = mSrcRect.width();
        int imageViewHeight = mSrcRect.height();

        float startScale = 0;
        float scaleX = (float) imageViewWidth / drawableWidth;
        float scaleY = (float) imageViewHeight / drawableHeight;
        if (scaleX < scaleY) {
            startScale = scaleY;
        } else {
            startScale =  scaleX;
        }
        mStartTransformParam = new TransformParam(mSrcRect, startScale);
    }

    public void transformIn() {
        mHasAnimation = true;
        mState = STATE_TRANSFORM_IN;
    }


    public void startAnimation() {
        ValueAnimator animator = null;
        if (mState == STATE_TRANSFORM_IN) {
            PropertyValuesHolder scalePVH = PropertyValuesHolder.ofFloat("scale", mStartTransformParam.scale, mEndTransformParam.scale);
            PropertyValuesHolder leftPVH = PropertyValuesHolder.ofInt("left", mStartTransformParam.rect.left, mEndTransformParam.rect.left);
            PropertyValuesHolder topPVH = PropertyValuesHolder.ofInt("top", mStartTransformParam.rect.top, mEndTransformParam.rect.top);
            PropertyValuesHolder rightPVH = PropertyValuesHolder.ofInt("right", mStartTransformParam.rect.right, mEndTransformParam.rect.right);
            PropertyValuesHolder bottomPVH = PropertyValuesHolder.ofInt("bottom", mStartTransformParam.rect.bottom, mEndTransformParam.rect.bottom);
            animator = ObjectAnimator.ofPropertyValuesHolder(scalePVH, leftPVH, topPVH, rightPVH, bottomPVH);
        } else if (mState == STATE_TRANSFORM_OUT) {
            PropertyValuesHolder scalePVH = PropertyValuesHolder.ofFloat("scale", mEndTransformParam.scale, mStartTransformParam.scale);
            PropertyValuesHolder leftPVH = PropertyValuesHolder.ofInt("left", mEndTransformParam.rect.left, mStartTransformParam.rect.left);
            PropertyValuesHolder topPVH = PropertyValuesHolder.ofInt("top", mEndTransformParam.rect.top, mStartTransformParam.rect.top);
            PropertyValuesHolder rightPVH = PropertyValuesHolder.ofInt("right", mEndTransformParam.rect.right, mStartTransformParam.rect.right);
            PropertyValuesHolder bottomPVH = PropertyValuesHolder.ofInt("bottom", mEndTransformParam.rect.bottom, mStartTransformParam.rect.bottom);
            animator = ObjectAnimator.ofPropertyValuesHolder(scalePVH, leftPVH, topPVH, rightPVH, bottomPVH);
        } else {
            return;
        }

        animator.setDuration(3000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mTransformParam.scale = (float) animation.getAnimatedValue("scale");
                mTransformParam.rect.left = (int) animation.getAnimatedValue("left");
                mTransformParam.rect.top = (int) animation.getAnimatedValue("top");
                mTransformParam.rect.right = (int) animation.getAnimatedValue("right");
                mTransformParam.rect.bottom = (int) animation.getAnimatedValue("bottom");
                invalidate();
            }
        });
        animator.start();
    }


    class TransformParam {
        public Rect rect;
        public float scale;

        public TransformParam(Rect rect, float scale) {
            this.rect = rect;
            this.scale = scale;
        }

        public TransformParam(TransformParam param) {
            rect = new Rect(param.rect);
            scale = param.scale;
        }
    }
}
