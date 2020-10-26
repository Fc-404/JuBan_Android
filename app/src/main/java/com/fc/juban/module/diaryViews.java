package com.fc.juban.module;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.content.res.ResourcesCompat;
import com.fc.juban.R;
import com.fc.juban.main_tree;
import com.fc.juban.tools.dp2px;

import java.util.zip.GZIPInputStream;

public class diaryViews {
    private Typeface typeface1;
    private Typeface typeface2;
    private Typeface typeface3;
    private Context text;

    public diaryViews(Context text){
        this.text = text;
        typeface1 = ResourcesCompat.getFont(text, R.font.cy_w01);
        typeface2 = ResourcesCompat.getFont(text, R.font.cy_w02);
        typeface3 = ResourcesCompat.getFont(text, R.font.cy_w03);
    }

    public LinearLayout logsView(String sign, String content, String time){
        LinearLayout root = new LinearLayout(text);
        LinearLayout.LayoutParams rootParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        rootParams.setMargins(0, dp2px.dp(6), 0 , dp2px.dp(6));
        root.setLayoutParams(rootParams);
        root.setOrientation(LinearLayout.HORIZONTAL);

        TextView head = new TextView(text);
        LinearLayout.LayoutParams headParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        head.setLayoutParams(headParams);
        head.setText(sign + ":\u3000");
        head.setTypeface(typeface3);
        head.setTextSize(12);

        TextView middle = new TextView(text);
        LinearLayout.LayoutParams middleParams = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        middleParams.weight = 1;
        middle.setLayoutParams(middleParams);
        middle.setTypeface(typeface2);
        middle.setTextSize(16);
        middle.setText(content);

        TextView end = new TextView(text);
        LinearLayout.LayoutParams endParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        end.setLayoutParams(endParams);
        end.setTextSize(10);
        end.setTypeface(typeface1);
        end.setText(time);

        root.addView(head);
        root.addView(middle);
        root.addView(end);

        return root;
    }

    public LinearLayout leaveView(String title, String content, String time){
        LinearLayout root = new LinearLayout(text);
        LinearLayout.LayoutParams rootParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        rootParams.setMargins(dp2px.dp(20), dp2px.dp(6), dp2px.dp(20), dp2px.dp(6));
        root.setLayoutParams(rootParams);
        root.setElevation(dp2px.dp(12));
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(dp2px.dp(20), dp2px.dp(20), dp2px.dp(20), dp2px.dp(20));
        root.setBackground(text.getDrawable(R.drawable.shape_round_12dp_all_white));

        TextView head = new TextView(text);
        LinearLayout.LayoutParams headParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        head.setLayoutParams(headParams);
        head.setTextSize(20);
        head.setTypeface(typeface3);
        head.setText(title);

        LinearLayout mainBody = new LinearLayout(text);
        LinearLayout.LayoutParams mainBodyParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        mainBodyParams.setMargins(0, dp2px.dp(12), 0, 0);
        mainBody.setLayoutParams(mainBodyParams);
        mainBody.setOrientation(LinearLayout.HORIZONTAL);

        TextView bodyText = new TextView(text);
        LinearLayout.LayoutParams bodyTextParams = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        bodyTextParams.weight = 1;
        bodyText.setLayoutParams(bodyTextParams);
        bodyText.setTypeface(typeface2);
        bodyText.setTextSize(16);
        bodyText.setText(content);

        TextView bodyTime = new TextView(text);
        LinearLayout.LayoutParams bodyTimeParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        bodyTimeParams.gravity = Gravity.BOTTOM;
        bodyTime.setLayoutParams(bodyTimeParams);
        bodyTime.setTypeface(typeface1);
        bodyTime.setGravity(Gravity.RIGHT);
        bodyTime.setTextSize(10);
        bodyTime.setText(time);

        mainBody.addView(bodyText);
        mainBody.addView(bodyTime);
        root.addView(head);
        root.addView(mainBody);

        return root;
    }

}
