package com.fc.juban;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.Timer;
import java.util.TimerTask;

public class welcome extends AppCompatActivity {
    private static Timer countTimeIndex;
    private ConstraintLayout main;
    private Intent storyUI = new Intent();
    public static Activity welcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        welcome = this;

        //设置状态栏字体为黑色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            this.getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            );

        storyUI.setClass(this, story.class);

        main = (ConstraintLayout) findViewById(R.id.main);
        //点击activity跳转下一个窗口
        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(storyUI);
            }
        });

        //时隔3s跳转到下一个窗口
        jump(3000);
    }

    //自动跳转
    private void jump(int times){
        countTimeIndex = new Timer();
        countTimeIndex.schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(storyUI);
            }
        }, times);
    }
    //关闭自动跳转，提供给其他类；
    public static void closeJump(){
        countTimeIndex.cancel();
    }
}
