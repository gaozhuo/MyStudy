package com.gaozhuo.customizeview.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.gaozhuo.customizeview.R;

/**
 * Created by gaozhuo on 2016/3/23.
 */
public class SimpleCircle extends View {
    private int mColor = Color.BLUE;
    private Paint mPaint = new Paint();
    private Button mButton;

    public SimpleCircle(Context context) {
        this(context, null);
    }

    public SimpleCircle(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SimpleCircle);
        mColor = a.getColor(R.styleable.SimpleCircle_circle_color, Color.BLUE);
        a.recycle();
        init();
    }

    private void init() {
        mPaint.setColor(mColor);
        mButton = new ButtonEx(getContext());
        mButton.setEnabled(true);
        mButton.setClickable(true);
        mButton.setPressed(true);
        mButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("gaozhuo", "button click");
                //Toast.makeText(getContext(), "click", Toast.LENGTH_SHORT).show();
            }
        });
        mButton.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d("gaozhuo", "button onTouch");
                return false;
            }
        });
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int paddingTop = getPaddingTop();
        int paddingLeft = getPaddingLeft();
        int paddingBottom = getPaddingBottom();
        int paddingRight = getPaddingRight();
        int width = getWidth() - paddingLeft - paddingRight;
        int height = getHeight() - paddingTop - paddingBottom;
        int radius = Math.min(width, height) >> 1;
        canvas.drawCircle((width >> 1) + paddingLeft, (height >> 1) + paddingTop, radius, mPaint);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mButton.layout(200, 200, 600, 400);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        mButton.setText("hahahah");
        mButton.draw(canvas);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.d("gaozhuo", "dispatchTouchEvent event=" + event.getActionMasked());
        return mButton.dispatchTouchEvent(event);
        //return super.dispatchTouchEvent(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(300, 300);
        } else if (widthMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(300, heightSize);
        } else if (heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSize, 300);
        } else {
            setMeasuredDimension(widthSize, heightSize);
        }
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
