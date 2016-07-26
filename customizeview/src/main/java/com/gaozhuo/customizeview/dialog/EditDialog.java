package com.gaozhuo.customizeview.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.gaozhuo.customizeview.R;

/**
 * @author gzhuo
 * @date 2016/7/26
 */
public class EditDialog extends Dialog {
    private String mTitle;
    private String mDesc;
    private String mCancelBtnText;
    private String mConfirmBtnText;
    private OnClickListener mConfirmOnClickListener;
    private OnClickListener mCancelOnClickListener;
    private boolean mCancelBtnEnabled = true;
    private boolean mConfirmBtnEnabled = true;

    protected EditDialog(Context context, Builder builder) {
        super(context, R.style.EditDialog);
        mTitle = builder.mTitle;
        mDesc = builder.mDesc;
        mCancelBtnText = builder.mCancelBtnText;
        mConfirmBtnText = builder.mConfirmBtnText;
        mConfirmOnClickListener = builder.mConfirmOnClickListener;
        mCancelOnClickListener = builder.mCancelOnClickListener;
        mCancelBtnEnabled = builder.mCancelBtnEnabled;
        mConfirmBtnEnabled = builder.mConfirmBtnEnabled;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_edit);
        setDialogSize();
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(mTitle);
        TextView desc = (TextView) findViewById(R.id.desc);
        desc.setText(mDesc);


        Button confirm = (Button) findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mConfirmOnClickListener != null) {
                    mConfirmOnClickListener.onClick(EditDialog.this, 0);
                }
            }
        });

        Button cancel = (Button) findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (mCancelOnClickListener != null) {
                    mCancelOnClickListener.onClick(EditDialog.this, 1);
                }
            }
        });
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
        private String mCancelBtnText;
        private String mConfirmBtnText;
        private OnClickListener mConfirmOnClickListener;
        private OnClickListener mCancelOnClickListener;
        private boolean mCancelBtnEnabled = true;
        private boolean mConfirmBtnEnabled = true;

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

        public Builder setCancelOnClickListener(OnClickListener listener) {
            mCancelOnClickListener = listener;
            return this;
        }

        public Builder setCancelBtnEnabled(boolean enabled) {
            mCancelBtnEnabled = enabled;
            return this;
        }

        public Builder setConfirmBtnEnabled(boolean enabled) {
            mConfirmBtnEnabled = enabled;
            return this;
        }

        public Builder setConfirmBtnText(String confirmBtnText) {
            this.mConfirmBtnText = confirmBtnText;
            return this;
        }

        public void setCancelBtnText(String cancelBtnText) {
            this.mCancelBtnText = cancelBtnText;
        }

        public EditDialog create() {
            EditDialog dialog = new EditDialog(mContext, this);
            dialog.setCanceledOnTouchOutside(false);
            return dialog;
        }

        public EditDialog show() {
            EditDialog dialog = create();
            dialog.show();
            return dialog;
        }

    }
}
