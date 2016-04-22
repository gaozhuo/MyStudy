package com.gaozhuo.customizeview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.gaozhuo.customizeview.R;

/**
 * 自定义九宫格锁
 *
 * @author gaozhuo
 * @date 2016/4/20
 */
public class LockView extends ViewGroup {
    private static final String TAG = LockView.class.getSimpleName();
    private Context mContext;
    private Paint mPaint;
    private int mLineColor;
    private int mLineWidth;
    private int mNodeSize;
    private Drawable mNormalNodeDrawable;
    private Drawable mActivatedNodeDrawable;

    public LockView(Context context) {
        this(context, null);
    }

    public LockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LockView);
        mLineColor = a.getColor(R.styleable.LockView_lock_lineColor, Color.BLUE);
        mLineWidth = a.getDimensionPixelSize(R.styleable.LockView_lock_lineWidth, 12);
        mNodeSize = a.getDimensionPixelSize(R.styleable.LockView_lock_nodeSize, 48);
        mNormalNodeDrawable = a.getDrawable(R.styleable.LockView_lock_normalNodeDrawable);
        mActivatedNodeDrawable = a.getDrawable(R.styleable.LockView_lock_activatedNodeDrawable);
        init();
        a.recycle();

    }

    private void init() {
        mPaint = new Paint();
        mPaint.setStrokeWidth(mLineWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(mLineColor);
        mPaint.setAntiAlias(true);
        setWillNotDraw(false);

        for (int i = 0; i < 9; i++) {
            NodeView node = new NodeView(mContext, i + 1);
            node.setBackgroundDrawable(mNormalNodeDrawable);
            addView(node);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    private static class NodeView extends View {
        public static final int STATUS_NORMAL = 0;
        public static final int STATUS_ACTIVATED = 1;
        public static final int STATUS_ERROR = 2;

        private int mNum;
        private int mStatus;

        public NodeView(Context context, int num) {
            super(context);
            mStatus = STATUS_NORMAL;
            mNum = num;
        }

        public int getNum() {
            return mNum;
        }

        public void setNum(int mum) {
            this.mNum = mum;
        }

        public int getStatus() {
            return mStatus;
        }

        public void setStatus(int status) {
            this.mStatus = status;
        }
    }
}
