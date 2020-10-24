package com.fc.juban;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import com.fc.juban.module.RoundImageView;
import com.fc.juban.tools.*;

import java.util.ArrayList;
import java.util.List;

public class set_bg extends Activity {
    bgRes bg;
    Typeface typeface;
    //Intent
    Intent intent;
    //Image
    List<LinearLayout> image_time = new ArrayList<LinearLayout>();
    List<LinearLayout> image_ip = new ArrayList<LinearLayout>();

    //控件
    private TextView titleTheme;
    private LinearLayout themeView;
    private SeekBar alpha;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_bg);

        //设置状态栏字体为黑色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            this.getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            );

        typeface = ResourcesCompat.getFont(this, R.font.cy_w03);
        //获取控件
        titleTheme = (TextView) findViewById(R.id.set_bg_title_theme);
        themeView = (LinearLayout) findViewById(R.id.set_bg_theme_view);
        alpha = (SeekBar) findViewById(R.id.set_bg_theme_alpha);

        //通过intent设置当前主题
        intent = getIntent();
        titleTheme.setText(intent.getStringExtra("bgCode"));
        alpha.setProgress((int)(intent.getFloatExtra("bgAlpha", 1) * 100));
        setResult(20, intent);

        bg = new bgRes();
        draw();
        imgOnClick();

        if (bg.image_time_name.indexOf(intent.getStringExtra("bgCode")) >= 0){
            image_time.get(
                    bg.image_time_name.indexOf(intent.getStringExtra("bgCode"))
            ).callOnClick();
        } else if (bg.image_ip_name.indexOf(intent.getStringExtra("bgCode")) >= 0){
            image_ip.get(
                    bg.image_ip_name.indexOf(intent.getStringExtra("bgCode"))
            ).callOnClick();
        }

        //seekbar控件值改变事件
        alpha.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                intent.putExtra("bgAlpha", (float) progress / 100);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                return;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                return;
            }
        });

        //点击主题返回
        titleTheme.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void draw(){
        int indexView = 0;
        //TIME
        themeView.addView(newTextView("TIME"), indexView++);
        for (int i = 0; i < bg.image_time_name.size(); i++){
            LinearLayout group = newLinear();
            themeView.addView(group, indexView++);
            image_time.add(group);

            RoundImageView left = new RoundImageView(this);
            RoundImageView centre = new RoundImageView(this);
            RoundImageView right = new RoundImageView(this);
            left.setLayoutParams(new LinearLayout.LayoutParams(
                    (int)((new getScreenSize(this).getAppSize().x - 80) * 0.3),
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            centre.setLayoutParams(new LinearLayout.LayoutParams(
                    (int)((new getScreenSize(this).getAppSize().x - 80) * 0.4),
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            right.setLayoutParams(new LinearLayout.LayoutParams(
                    (int)((new getScreenSize(this).getAppSize().x - 80) * 0.3),
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            left.setRound(99);
            right.setRound(99);
            left.setScaleType(ImageView.ScaleType.CENTER_CROP);
            centre.setScaleType(ImageView.ScaleType.CENTER_CROP);
            right.setScaleType(ImageView.ScaleType.CENTER_CROP);
            left.setLocation(new boolean[]{true, false, false, true});
            right.setLocation(new boolean[]{false, true, true, false});
            left.setImageResource(bg.image_time_id.get(i * 3));
            centre.setImageResource(bg.image_time_id.get(i * 3 + 1));
            right.setImageResource(bg.image_time_id.get(i * 3 + 2));
            group.addView(left);
            group.addView(centre);
            group.addView(right);
        }

        //IP
        themeView.addView(newTextView("IP"), indexView++);
        for (int i = 0; i < bg.image_ip_name.size(); i++){
            LinearLayout group = newLinear();
            themeView.addView(group, indexView++);
            image_ip.add(group);

            RoundImageView img = new RoundImageView(this);
            img.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            img.setRound(99);
            img.setScaleType(ImageView.ScaleType.CENTER_CROP);
            img.setLocation(new boolean[]{true, true, true, true});
            img.setImageResource(bg.image_ip_id.get(i));
            group.addView(img);
        }
    }

    private TextView newTextView(String group){
        TextView groupT = new TextView(this);
        groupT.setTypeface(typeface);
        groupT.setPadding(0, 88, 0, 44);
        groupT.setTextSize(20);
        groupT.setText(group);
        return groupT;
    }
    private LinearLayout newLinear(){
        LinearLayout time_group = new LinearLayout(this);
        time_group.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 400);
        layoutParams.setMargins(0, 44, 0, 0);
        time_group.setLayoutParams(layoutParams);
        time_group.setPadding(0, 0, 0, 20);
        return time_group;
    }

    //图片点击事件
    private void imgOnClick(){
        //TIME
        int n = 0;
        for (int i = 0; i < image_time.size(); i++){
            final LinearLayout te = image_time.get(i);
            te.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgClickClose();
                    te.setBackground(getDrawable(R.drawable.shape_round_stroke_99px_all));
                    titleTheme.setText(bg.image_time_name.get(image_time.indexOf(te)));
                    intent.putExtra("bgCode", bg.image_time_name.get(image_time.indexOf(te)));
                    intent.putExtra("bgType", "TIME");
                }
            });
        }

        //IP
        for (int i = 0; i < image_ip.size(); i++){
            final LinearLayout te = image_ip.get(i);
            te.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgClickClose();
                    te.setBackground(getDrawable(R.drawable.shape_round_stroke_99px_all));
                    titleTheme.setText(bg.image_ip_name.get(image_ip.indexOf(te)));
                    intent.putExtra("bgCode", bg.image_ip_name.get(image_ip.indexOf(te)));
                    intent.putExtra("bgType", "IP");
                }
            });
        }
    }
    private void imgClickClose(){
        for (int i = 0; i < image_time.size(); i++)
            image_time.get(i).setBackground(getDrawable(R.color.colorT));
        for (int i = 0; i < image_ip.size(); i++)
            image_ip.get(i).setBackground(getDrawable(R.color.colorT));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        intent = null;
        bg = null;
        System.gc();
    }
}
