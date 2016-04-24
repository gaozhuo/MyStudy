package com.gaozhuo.customizeview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import com.gaozhuo.customizeview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义九宫格锁
 *
 * @author gaozhuo
 * @date 2016/4/20
 */
public class LockView extends ViewGroup {
    private static final String TAG = LockView.class.getSimpleName();
    private static final int EXTENT = 5;
    private Context mContext;
    private Paint mPaint;
    private int mLineColor;
    private int mLineWidth;
    private int mNodeSize;
    private Drawable mNormalNodeDrawable;
    private Drawable mActivatedNodeDrawable;
    private int mActivatedAnimRes;
    private int mVibrateTime;
    private boolean mEnableVibrate;
    private List<NodeView> mLinkedNodes = new ArrayList<NodeView>();//已经连线的node
    private int mX;
    private int mY;
    private Vibrator mVibrator;

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
        mActivatedAnimRes = a.getResourceId(R.styleable.LockView_lock_activatedAnimRes, 0);
        mEnableVibrate = a.getBoolean(R.styleable.LockView_lock_enableVibrate, false);
        mVibrateTime = a.getInt(R.styleable.LockView_lock_vibrateTime, 20);
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

        mVibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);

        for (int i = 0; i < 9; i++) {
            NodeView node = new NodeView(mContext, i + 1);
            addView(node);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        int size = widthSpecSize < heightSpecSize ? widthSpecSize : heightSpecSize;
        setMeasuredDimension(size, size);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        int gridSize = getMeasuredWidth() / 3;//9等分后每个方格的大小
        int offset = (gridSize - mNodeSize) / 2;
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            int left = (i % 3) * gridSize + offset;
            int top = (i / 3) * gridSize + offset;
            int right = left + mNodeSize;
            int bottom = top + mNodeSize;
            child.layout(left, top, right, bottom);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mX = (int) event.getX();
        mY = (int) event.getY();
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                NodeView currentNode = getSelectedNode(mX, mY);
                if (currentNode != null && !mLinkedNodes.contains(currentNode)) {
                    insertMiddleNodeIfNeed(currentNode);
                    mLinkedNodes.add(currentNode);
                    currentNode.setStatus(NodeView.STATUS_ACTIVATED);
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                getPassword();
                for (NodeView nodeView : mLinkedNodes) {
                    nodeView.setStatus(NodeView.STATUS_NORMAL);
                }
                mLinkedNodes.clear();
                invalidate();
                break;
        }
        return true;
    }

    /**
     * 如果当前节点和mLinkedNodes的最后一个节点之间有中间节点，则插入中间节点
     *
     * @param currentNode 当前节点
     */
    private void insertMiddleNodeIfNeed(NodeView currentNode) {
        int size = mLinkedNodes.size();
        if(size < 1){
            return;
        }
        NodeView lastNode = mLinkedNodes.get(size -1);
        if (lastNode != null) {
            int currentNum = currentNode.getNum();
            int lastNum = lastNode.getNum();
            //如果最后一个node的num较大则交换位置
            if (lastNum > currentNum) {
                int temp = lastNum;
                lastNum = currentNum;
                currentNum = temp;
            }

            int index = -1;
            if (lastNum == 1 && currentNum == 3 || lastNum == 4 && currentNum == 6 || lastNum == 7 && currentNum == 9) {//水平连线情况
                index = lastNum;
            } else if (lastNum == 1 && currentNum == 7 || lastNum == 2 && currentNum == 8 || lastNum == 3 && currentNum == 9){//竖直连线情况
                index = (lastNum - 1) + 3;
            }else if(lastNum == 1 && currentNum == 9 || lastNum == 3 && currentNum == 7){//对角连线情况
               index = 4;
            }
            if(index != -1){
                NodeView middleNode  = (NodeView) getChildAt(index);
                middleNode.setStatus(NodeView.STATUS_ACTIVATED);
                mLinkedNodes.add(middleNode);
            }
        }
    }

    private void getPassword() {
        if (mLinkedNodes.size() > 0) {
            StringBuilder sb = new StringBuilder();
            for (NodeView node : mLinkedNodes) {
                sb.append(node.getNum() + "");
            }
            if (mOnPasswordListener != null) {
                mOnPasswordListener.password(sb.toString());
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int size = mLinkedNodes.size();
        if (size == 0) {
            return;
        }
        if (size >= 2) {
            for (int i = 0; i < size - 1; i++) {
                NodeView node1 = mLinkedNodes.get(i);
                NodeView node2 = mLinkedNodes.get(i + 1);
                linkTwoNode(canvas, node1, node2);
            }

        }
        //连接最后一个点和手指
        NodeView lastNode = mLinkedNodes.get(size - 1);
        linkToFinger(canvas, lastNode);

    }

    private void linkToFinger(Canvas canvas, NodeView lastNode) {
        canvas.drawLine(lastNode.getCenterX(), lastNode.getCenterY(), mX, mY, mPaint);
    }

    private void linkTwoNode(Canvas canvas, NodeView node1, NodeView node2) {
        canvas.drawLine(node1.getCenterX(), node1.getCenterY(), node2.getCenterX(), node2.getCenterY(), mPaint);
    }

    /**
     * 获取选中的node
     *
     * @param x
     * @param y
     * @return
     */
    private NodeView getSelectedNode(int x, int y) {
        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            NodeView nodeView = (NodeView) getChildAt(i);
            int distance = (int) Math.sqrt((x - nodeView.getCenterX()) * (x - nodeView.getCenterX())
                    + (y - nodeView.getCenterY()) * (y - nodeView.getCenterY()));
            if (distance < mNodeSize / 2 + EXTENT) {
                return nodeView;
            }
        }

        return null;
    }

    private OnPasswordListener mOnPasswordListener;

    public void setOnPasswordListener(OnPasswordListener listener) {
        mOnPasswordListener = listener;
    }


    public interface OnPasswordListener {
        void password(String password);
    }

    private class NodeView extends View {
        public static final int STATUS_NORMAL = 0;
        public static final int STATUS_ACTIVATED = 1;
        public static final int STATUS_ERROR = 2;

        private int mNum;
        private int mStatus;

        public NodeView(Context context, int num) {
            super(context);
            mStatus = STATUS_NORMAL;
            mNum = num;
            setBackgroundDrawable(mNormalNodeDrawable);
        }

        public int getCenterX() {
            return (getLeft() + getRight()) / 2;
        }

        public int getCenterY() {
            return (getTop() + getBottom()) / 2;
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
            if (mStatus != status) {
                mStatus = status;
                if (status == STATUS_ACTIVATED) {
                    setBackgroundDrawable(mActivatedNodeDrawable);
                    if (mActivatedAnimRes != 0) {
                        startAnimation(AnimationUtils.loadAnimation(getContext(), mActivatedAnimRes));
                    }
                    if (mEnableVibrate) {
                        mVibrator.vibrate(mVibrateTime);
                    }
                } else if (status == STATUS_NORMAL) {
                    setBackgroundDrawable(mNormalNodeDrawable);
                    clearAnimation();
                }
            }
        }
    }
}
