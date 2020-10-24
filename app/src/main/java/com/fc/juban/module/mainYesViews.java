package com.fc.juban.module;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.core.content.res.ResourcesCompat;
import com.fc.juban.R;
import com.fc.juban.main_tree;
import com.fc.juban.tools.*;

public class mainYesViews {
    private Typeface typeface1;
    private Typeface typeface2;
    private Typeface typeface3;
    private Context text;

    public mainYesViews(Context text){
        this.text = text;
        typeface1 = ResourcesCompat.getFont(text, R.font.cy_w01);
        typeface2 = ResourcesCompat.getFont(text, R.font.cy_w02);
        typeface3 = ResourcesCompat.getFont(text, R.font.cy_w03);
    }

    public ScrollView moodOptionView(){
        final moodRes moodSrc = new moodRes();

        ScrollView rootDiv = new ScrollView(text);
        LinearLayout.LayoutParams rootDivParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        rootDiv.setLayoutParams(rootDivParams);
        //网格布局
        GridLayout root = new GridLayout(text);
        LinearLayout.LayoutParams rootParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        root.setLayoutParams(rootParams);
        root.setColumnCount(4);
        root.setOrientation(GridLayout.HORIZONTAL);
        //root.setBackground(text.getDrawable(R.mipmap.main_bg_ip_jzy));

        for (int i = 0; i < moodSrc.size(); i++){
            //cell 布局
            LinearLayout cell = new LinearLayout(text);
            GridLayout.LayoutParams cellParams = new GridLayout.LayoutParams(
                    GridLayout.spec(GridLayout.UNDEFINED, 1f),
                    GridLayout.spec(GridLayout. UNDEFINED, 1f));
            LinearLayout.LayoutParams cellParams1 = new LinearLayout.LayoutParams(
                    dp2px.dp(80),
                    dp2px.dp(80)
            );
            cellParams1.gravity = Gravity.CENTER;
            cell.setLayoutParams(cellParams1);
            cell.setPadding(dp2px.dp(10), dp2px.dp(10), dp2px.dp(10), dp2px.dp(10));
            cell.setOrientation(LinearLayout.VERTICAL);
            //mood
            ImageView mood = new ImageView(text);
            LinearLayout.LayoutParams moodParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            moodParams.gravity = Gravity.CENTER;
            moodParams.height = dp2px.dp(30);
            moodParams.weight = dp2px.dp(30);
            mood.setLayoutParams(moodParams);
            mood.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            mood.setImageResource(moodSrc.getIndex(i));
            //word
            TextView word = new TextView(text);
            LinearLayout.LayoutParams wordParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            wordParams.weight = LinearLayout.LayoutParams.MATCH_PARENT;
            wordParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
            word.setLayoutParams(wordParams);
            word.setTextSize(12);
            word.setTypeface(typeface2);
            word.setGravity(Gravity.CENTER);
            word.setText(moodSrc.getName(i));

            final int  n = i;
            cell.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(text, moodSrc.getName(n), Toast.LENGTH_LONG).show();
                    main_tree.Q_s(v);
                    Message moodMsg = new Message();
                    moodMsg.what = moodSrc.getIndex(n);
                    main_tree.moodSet.sendMessage(moodMsg);
                }
            });
            cell.addView(mood);
            cell.addView(word);
            root.addView(cell);
        }
        rootDiv.addView(root);

        return rootDiv;
    }

    public LinearLayout leaveView(final AlertDialog.Builder mine, final SharedPreferences userData){
        LinearLayout root = new LinearLayout(text);
        root.setLayoutParams(new ViewGroup.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(dp2px.dp(20), dp2px.dp(20), dp2px.dp(20), dp2px.dp(20));
        root.setClipToPadding(false);
        root.setClickable(false);

        final EditText wordView = new EditText(text);
        LinearLayout.LayoutParams wordParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                dp2px.dp(120)
        );
        wordParams.setMargins(0, 0, 0, dp2px.dp(20));
        wordView.setPadding(dp2px.dp(12), dp2px.dp(12), dp2px.dp(12), dp2px.dp(12));
        wordView.setLayoutParams(wordParams);
        wordView.setTypeface(typeface1);
        wordView.setBackground(text.getDrawable(R.drawable.shape_round_12dp_all_white));
        wordView.setTranslationZ(12);

        Button ack = new Button(text);
        LinearLayout.LayoutParams ackParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        ackParams.setMargins(0, 0, 0, dp2px.dp(20));
        ack.setLayoutParams(ackParams);
        ack.setTypeface(typeface2);
        ack.setText("确认");
        ack.setTextSize(20);
        ack.setBackground(text.getDrawable(R.drawable.selector_round_12dp_all_gray));
        ack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkStr.checkLeave(wordView.getText().toString())){
                    Toast.makeText(text, "文字中出现非常规字符\u3000请重试", Toast.LENGTH_LONG).show();
                    return;
                }

                final toApi leaveUp = new toApi();
                leaveUp.set(toApi.api_addSeedInfo(
                        userData.getString("seed", ""),
                        userData.getString("user", ""),
                        "leave", wordView.getText().toString())).get();
                leaveUp.setCheck(new check() {
                    @Override
                    public void okBefore() {
                        if ("true".equals(leaveUp.getResult().getKeyValue("return")))
                            Toast.makeText(text, "留言成功", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(text, "你的留言丢失在茫茫人海中了", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void missBefore() {
                        Toast.makeText(text, "网线可能有脾气", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        root.addView(wordView);
        root.addView(ack);

        return root;
    }
}
