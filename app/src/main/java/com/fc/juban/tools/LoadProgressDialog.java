package com.fc.juban.tools;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;
import com.fc.juban.R;

public class LoadProgressDialog extends Dialog {
    private String message;
    private boolean canCancel;
    private TextView textView;
    private Context context;

    public LoadProgressDialog(Context context, String message) {
        this(context, message, true);
    }

    public LoadProgressDialog(Context context, String message, boolean canCancel) {
        super(context, R.style.LoadProgressDialog);
        this.context = context;
        this.message = message;
        this.canCancel = canCancel;
    }

    public void setMessage(String message) {
        this.message = message;
        handler.sendEmptyMessage(0);
    }


    @SuppressLint("HandlerLeak")
    private
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                textView.setText(message);
            }
        }
    };

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_loadprogress);
        textView = findViewById(R.id.tv_message);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        textView.setText(message);
    }
}
