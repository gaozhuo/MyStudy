package com.gaozhuo.customizeview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.gaozhuo.customizeview.dialog.OrderCancelDialog;

public class DialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderCancelDialog.Builder builder = new OrderCancelDialog.Builder(DialogActivity.this);
                builder.setTitle("取消订单")
                        .setDesc("请选择您取消订单的原因")
                        .setBtn1Text("直接取消订单")
                        .setBtn1Desc("该订单已超过48小时未处理，你可以直接取消订单")
                        .setBtn2Text("联系助手处理")
                        .setBtn2Desc("告知助手取消原因，助手会尽快帮你处理");
                builder.setBtn1Enabled(false);
                OrderCancelDialog dialog = builder.create();
                dialog.show();
            }
        });
    }
}
