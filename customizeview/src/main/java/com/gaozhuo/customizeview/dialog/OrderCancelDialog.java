package com.gaozhuo.customizeview.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.gaozhuo.customizeview.R;

/**
 *
 *
 * @author gzhuo
 * @date 2016/7/22
 */
public class OrderCancelDialog extends Dialog {

    public OrderCancelDialog(Context context) {
        super(context);
    }

    public OrderCancelDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_order_cancel);
    }

    public static class Builder{

        public Builder(Context context) {

        }
    }
}
