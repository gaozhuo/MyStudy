package com.gaozhuo.customizeview.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
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

    protected EditDialog(Context context, Builder builder) {
        super(context, R.style.EditDialog);
        mTitle = builder.mTitle;
        mDesc = builder.mDesc;
        mCancelBtnText = builder.mCancelBtnText;
        mConfirmBtnText = builder.mConfirmBtnText;
        mConfirmOnClickListener = builder.mConfirmOnClickListener;
        mCancelOnClickListener = builder.mCancelOnClickListener;
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


        final Button confirm = (Button) findViewById(R.id.confirm);
        confirm.setText(mConfirmBtnText);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mConfirmOnClickListener != null) {
                    mConfirmOnClickListener.onClick(EditDialog.this, 0);
                }
            }
        });
        confirm.setEnabled(false);
        confirm.setAlpha(0.4f);

        Button cancel = (Button) findViewById(R.id.cancel);
        cancel.setText(mCancelBtnText);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (mCancelOnClickListener != null) {
                    mCancelOnClickListener.onClick(EditDialog.this, 1);
                }
            }
        });

        EditText editText = (EditText) findViewById(R.id.editText);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() >0){
                    confirm.setEnabled(true);
                    confirm.setAlpha(1.0f);
                }else {
                    confirm.setEnabled(false);
                    confirm.setAlpha(0.4f);
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
        private String mCancelBtnText = "取消";
        private String mConfirmBtnText = "提交";
        private OnClickListener mConfirmOnClickListener;
        private OnClickListener mCancelOnClickListener;

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

        public Builder setConfirmOnClickListener(OnClickListener listener) {
            mConfirmOnClickListener = listener;
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
