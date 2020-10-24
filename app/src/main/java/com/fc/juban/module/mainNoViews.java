package com.fc.juban.module;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import androidx.core.content.res.ResourcesCompat;
import com.fc.juban.R;
import com.fc.juban.tools.dp2px;

public class mainNoViews {
    private Typeface typeface1;
    private Typeface typeface2;
    private Typeface typeface3;
    private Context text;

    public mainNoViews(Context text){
        this.text = text;
        typeface1 = ResourcesCompat.getFont(text, R.font.cy_w01);
        typeface2 = ResourcesCompat.getFont(text, R.font.cy_w02);
        typeface3 = ResourcesCompat.getFont(text, R.font.cy_w03);
    }

    public LinearLayout cardTCB(String title, String content, String buttonText){
        return cardTCB(title, content, buttonText, null);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public LinearLayout cardTCB(String title, String content, String buttonText, Button.OnClickListener click){
        //设置根布局
        LinearLayout root = new LinearLayout(text);
        LinearLayout.LayoutParams rootParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        rootParams.setMargins(0, 0, 0, dp2px.dp(20));
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(dp2px.dp(20), dp2px.dp(20), dp2px.dp(20), dp2px.dp(20));
        root.setLayoutParams(rootParams);
        root.setBackground(text.getDrawable(R.drawable.shape_round_12dp_all_white));

        //设置标题布局
        TextView titleView = new TextView(text);
        titleView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        titleView.setText(title);
        titleView.setTypeface(typeface1);
        titleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        root.addView(titleView);

        //设置内容根布局
        LinearLayout contentRoot = new LinearLayout(text);
        contentRoot.setOrientation(LinearLayout.HORIZONTAL);
        contentRoot.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));

        //设置内容
        TextView contentView = new TextView(text);
        LinearLayout.LayoutParams contentParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
                1
        );
        contentView.setGravity(Gravity.CENTER | Gravity.LEFT);
        contentView.setTypeface(typeface2);
        contentView.setText(content);
        contentView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        contentView.setLayoutParams(contentParams);
        contentRoot.addView(contentView);

        //设置内容按钮
        Button buttonView = new Button(text);
        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        buttonParams.setMargins(dp2px.dp(6), dp2px.dp(6), dp2px.dp(6), dp2px.dp(6));
        buttonView.setText(buttonText);
        buttonView.setTypeface(typeface2);
        buttonView.setBackground(text.getDrawable(R.drawable.selector_round_12dp_all_gray));
        buttonView.setLayoutParams(buttonParams);
        buttonView.setOnClickListener(click);
        contentRoot.addView(buttonView);

        root.addView(contentRoot);

        return root;
    }
}
