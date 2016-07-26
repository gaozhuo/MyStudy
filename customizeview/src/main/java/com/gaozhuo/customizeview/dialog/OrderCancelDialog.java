package com.gaozhuo.customizeview.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.gaozhuo.customizeview.R;

/**
 * @author gzhuo
 * @date 2016/7/22
 */
public class OrderCancelDialog extends Dialog {
    private String mTitle;
    private String mDesc;
    private String mBtn1Text;
    private String mBtn1Desc;
    private String mBtn2Text;
    private String mBtn2Desc;

    private OnClickListener mBtn1OnClickListener;
    private OnClickListener mBtn2OnClickListener;
    private OnClickListener mCancelOnClickListener;
    private boolean mBtn1Enabled = true;
    private boolean mBtn2Enabled = true;

    protected OrderCancelDialog(Context context, Builder builder) {
        super(context, R.style.OrderCancelDialog);
        mTitle = builder.mTitle;
        mDesc = builder.mDesc;
        mBtn1Text = builder.mBtn1Text;
        mBtn1Desc = builder.mBtn1Desc;
        mBtn2Text = builder.mBtn2Text;
        mBtn2Desc = builder.mBtn2Desc;
        mBtn1OnClickListener = builder.mBtn1OnClickListener;
        mBtn2OnClickListener = builder.mBtn2OnClickListener;
        mCancelOnClickListener = builder.mCancelOnClickListener;
        mBtn1Enabled = builder.mBtn1Enabled;
        mBtn2Enabled = builder.mBtn2Enabled;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_order_cancel);
        setDialogSize();
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(mTitle);
        TextView desc = (TextView) findViewById(R.id.desc);
        desc.setText(mDesc);
        TextView btn1Text = (TextView) findViewById(R.id.text1);
        btn1Text.setText(mBtn1Text);
        TextView btn1Desc = (TextView) findViewById(R.id.desc1);
        btn1Desc.setText(mBtn1Desc);
        TextView btn2Text = (TextView) findViewById(R.id.text2);
        btn2Text.setText(mBtn2Text);
        TextView btn2Desc = (TextView) findViewById(R.id.desc2);
        btn2Desc.setText(mBtn2Desc);

        View btn1 = findViewById(R.id.layout1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBtn1OnClickListener != null) {
                    mBtn1OnClickListener.onClick(OrderCancelDialog.this, 0);
                }
            }
        });

        View btn2 = findViewById(R.id.layout2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBtn2OnClickListener != null) {
                    mBtn2OnClickListener.onClick(OrderCancelDialog.this, 1);
                }
            }
        });

        View cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (mCancelOnClickListener != null) {
                    mCancelOnClickListener.onClick(OrderCancelDialog.this, 2);
                }
            }
        });


        if (!mBtn1Enabled) {
            btn1.setEnabled(false);
            btn1.setAlpha(0.4f);
        }

        if (!mBtn2Enabled) {
            btn2.setEnabled(false);
            btn2.setAlpha(0.4f);
        }

    }

    private void setDialogSize() {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);
        Window window = getWindow();
        window.setLayout((int) (dm.widthPixels * 0.85), WindowManager.LayoutParams.WRAP_CONTENT);
    }

    public static class Builder {
        private Context mContext;
        private String mTitle;
        private String mDesc;
        private String mBtn1Text;
        private String mBtn1Desc;
        private String mBtn2Text;
        private String mBtn2Desc;
        private OnClickListener mBtn1OnClickListener;
        private OnClickListener mBtn2OnClickListener;
        private OnClickListener mCancelOnClickListener;
        private boolean mBtn1Enabled = true;
        private boolean mBtn2Enabled = true;

        public Builder(Context context) {
            mContext = context;
        }

        public Builder setTitle(String title) {
            this.mTitle = title;
            return this;
        }

        public Builder setDesc(String desc) {
            this.mDesc = desc;
            return this;
        }

        public Builder setBtn1Text(String btn1Text) {
            this.mBtn1Text = btn1Text;
            return this;
        }

        public Builder setBtn1Desc(String btn1Desc) {
            this.mBtn1Desc = btn1Desc;
            return this;
        }

        public Builder setBtn2Text(String btn2Text) {
            this.mBtn2Text = btn2Text;
            return this;
        }

        public Builder setBtn2Desc(String btn2Desc) {
            this.mBtn2Desc = btn2Desc;
            return this;
        }

        public Builder setBtn1OnClickListener(OnClickListener listener) {
            mBtn1OnClickListener = listener;
            return this;
        }

        public Builder setBtn2OnClickListener(OnClickListener listener) {
            mBtn2OnClickListener = listener;
            return this;
        }

        public Builder setCancelOnClickListener(OnClickListener listener) {
            mCancelOnClickListener = listener;
            return this;
        }

        public Builder setBtn1Enabled(boolean enabled) {
            mBtn1Enabled = enabled;
            return this;
        }

        public Builder setBtn2Enabled(boolean enabled) {
            mBtn2Enabled = enabled;
            return this;
        }

        public OrderCancelDialog create() {
            OrderCancelDialog dialog = new OrderCancelDialog(mContext, this);
            dialog.setCanceledOnTouchOutside(false);
            return dialog;
        }

        public OrderCancelDialog show() {
            OrderCancelDialog dialog = create();
            dialog.show();
            return dialog;
        }

    }
}
