package com.gaozhuo.customizeview.views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import uk.co.senab.photoview.PhotoView;

/**
 * @author gaozhuo
 * @date 2016/5/15
 */
public class SmoothImageView extends PhotoView {
    private static final String TAG = SmoothImageView.class.getSimpleName();
    public static final int STATE_TRANSFORM_NONE = 0;//没有动画
    public static final int STATE_TRANSFORM_IN = 1;//进入动画
    public static final int STATE_TRANSFORM_OUT = 2;//退出动画

    private Rect mSrcRect;
    private TransformParam mStartTransformParam;
    private TransformParam mEndTransformParam;
    private TransformParam mTransformParam;
    private boolean mIsInited;
    private int mState = STATE_TRANSFORM_NONE;
    private int mDrawableWidth;
    private int mDrawableHeight;
    private OnTransformListener mOnTransformListener;
    private Paint mPaint = new Paint();

    public SmoothImageView(Context context) {
        this(context, null);
    }

    public SmoothImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SmoothImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init2();
    }

    private void init2() {
        mPaint.setStyle(Paint.Style.FILL);
        //background_material_dark
        mPaint.setColor(0xff303030);
    }

    public void setSrcRect(Rect rect) {
        mSrcRect = rect;
    }

    public void setOnTransformListener(OnTransformListener listener) {
        mOnTransformListener = listener;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d("gaozhuo", "onDraw");
        if (mState == STATE_TRANSFORM_NONE) {//没有动画
            mPaint.setAlpha(255);
            canvas.drawPaint(mPaint);
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
            startAnimation();
        }

        mPaint.setAlpha(mTransformParam.alpha);
        canvas.drawPaint(mPaint);

        int dx = (int) ((mTransformParam.rect.width() - mDrawableWidth * mTransformParam.scale) * 0.5f);
        int dy = (int) ((mTransformParam.rect.height() - mDrawableHeight * mTransformParam.scale) * 0.5f);

        canvas.save();
        canvas.translate(mTransformParam.rect.left, mTransformParam.rect.top);
        canvas.clipRect(0, 0, mTransformParam.rect.width(), mTransformParam.rect.height());

        canvas.translate(dx, dy);

        canvas.scale(mTransformParam.scale, mTransformParam.scale);
        drawable.draw(canvas);
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

        mEndTransformParam = new TransformParam(endRect, endScale, 255);
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
            startScale = scaleX;
        }
        mStartTransformParam = new TransformParam(mSrcRect, startScale, 0);
    }

    public void transformIn() {
        mState = STATE_TRANSFORM_IN;
        mIsInited = false;
    }

    public void transformOut() {
        mState = STATE_TRANSFORM_OUT;
        mIsInited = false;
        invalidate();
    }


    public void startAnimation() {
        ValueAnimator animator = null;
        if (mState == STATE_TRANSFORM_IN) {
            PropertyValuesHolder scalePVH = PropertyValuesHolder.ofFloat("scale", mStartTransformParam.scale, mEndTransformParam.scale);
            PropertyValuesHolder leftPVH = PropertyValuesHolder.ofInt("left", mStartTransformParam.rect.left, mEndTransformParam.rect.left);
            PropertyValuesHolder topPVH = PropertyValuesHolder.ofInt("top", mStartTransformParam.rect.top, mEndTransformParam.rect.top);
            PropertyValuesHolder rightPVH = PropertyValuesHolder.ofInt("right", mStartTransformParam.rect.right, mEndTransformParam.rect.right);
            PropertyValuesHolder bottomPVH = PropertyValuesHolder.ofInt("bottom", mStartTransformParam.rect.bottom, mEndTransformParam.rect.bottom);
            PropertyValuesHolder alphaPVH = PropertyValuesHolder.ofInt("alpha", 0, 255);
            animator = ObjectAnimator.ofPropertyValuesHolder(scalePVH, leftPVH, topPVH, rightPVH, bottomPVH, alphaPVH);
        } else if (mState == STATE_TRANSFORM_OUT) {
            PropertyValuesHolder scalePVH = PropertyValuesHolder.ofFloat("scale", mEndTransformParam.scale, mStartTransformParam.scale);
            PropertyValuesHolder leftPVH = PropertyValuesHolder.ofInt("left", mEndTransformParam.rect.left, mStartTransformParam.rect.left);
            PropertyValuesHolder topPVH = PropertyValuesHolder.ofInt("top", mEndTransformParam.rect.top, mStartTransformParam.rect.top);
            PropertyValuesHolder rightPVH = PropertyValuesHolder.ofInt("right", mEndTransformParam.rect.right, mStartTransformParam.rect.right);
            PropertyValuesHolder bottomPVH = PropertyValuesHolder.ofInt("bottom", mEndTransformParam.rect.bottom, mStartTransformParam.rect.bottom);
            PropertyValuesHolder alphaPVH = PropertyValuesHolder.ofInt("alpha", 255, 0);
            animator = ObjectAnimator.ofPropertyValuesHolder(scalePVH, leftPVH, topPVH, rightPVH, bottomPVH, alphaPVH);
        } else {
            return;
        }

        animator.setDuration(300);
        //animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mTransformParam.scale = (float) animation.getAnimatedValue("scale");
                mTransformParam.rect.left = (int) animation.getAnimatedValue("left");
                mTransformParam.rect.top = (int) animation.getAnimatedValue("top");
                mTransformParam.rect.right = (int) animation.getAnimatedValue("right");
                mTransformParam.rect.bottom = (int) animation.getAnimatedValue("bottom");
                mTransformParam.alpha = (int) animation.getAnimatedValue("alpha");
                invalidate();
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if(mState == STATE_TRANSFORM_IN){
                    mState = STATE_TRANSFORM_NONE;
                }
                if (mOnTransformListener != null) {
                    mOnTransformListener.onTransformComplete(mState);
                }
            }
        });
        animator.start();
    }


    private class TransformParam {
        public Rect rect;
        public float scale;
        public int alpha;

        public TransformParam(Rect rect, float scale, int alpha) {
            this.rect = rect;
            this.scale = scale;
            this.alpha = alpha;
        }

        public TransformParam(TransformParam param) {
            rect = new Rect(param.rect);
            scale = param.scale;
            alpha = param.alpha;

        }
    }

    public interface OnTransformListener {
        void onTransformComplete(int state);
    }
}
