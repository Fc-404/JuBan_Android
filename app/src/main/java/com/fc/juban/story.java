package com.fc.juban;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.fc.juban.tools.getScreenSize;

import java.io.File;

public class story extends Activity {
    public static Activity story;
    //读写权限定义
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.MOUNT_UNMOUNT_FILESYSTEMS",
            "android.permission.READ_PHONE_STATE"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.story);
        this.story = this;

        //设置状态栏字体为黑色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            this.getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            );

        //welcome activity生命结束
        welcome.welcome.finish();
        welcome.closeJump();//需要关闭定时跳转，防止二次跳转

        //根据屏幕大小设置text高度；
        ScrollView story_text = (ScrollView) findViewById(R.id.story_text);
        //story_text.setHeight(point.y);  此方法不生效，使用下面方法；
        story_text.getLayoutParams().height = new getScreenSize(story.this).getAppSize().y - 320;

        //绑定start按钮事件
        startOnClick((Button) findViewById(R.id.storyStart));

        //判断是否加载过故事页
        if (!isLoad()) {
            this.finish();
            startActivity(new Intent().setClass(story.this, login_up.class));
        }

    }

    //检测配置文件；是否加载过此activity
    private boolean isLoad(){
        //String rootDir = Environment.getExternalStorageDirectory().getAbsolutePath();

        SharedPreferences data = getSharedPreferences("isLoad", MODE_PRIVATE);
        SharedPreferences.Editor mdData = data.edit();

        if (data.getInt("isLoad", 0) == 0){
            mdData.putInt("isLoad", 1);
            mdData.commit();
        } else if (data.getInt("isLoad", 0) == 1)
            return false;

        return true;
    }

    //点击start事件
    private boolean startOnClick(Button btn){
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //如果有权限进入软件
                if(ContextCompat.checkSelfPermission(story.this,"android.permission.READ_EXTERNAL_STORAGE")
                        == PackageManager.PERMISSION_GRANTED)
                    startActivity(new Intent().setClass(story.this, login_up.class));
                else {
                    //否则
                    final AlertDialog.Builder accept_IO = new AlertDialog.Builder(story.this);
                    accept_IO.setTitle("注意");
                    accept_IO.setMessage("为了软件能正常运行，请同意访问文件的权限！");
                    accept_IO.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startEnt();
                        }
                    });
                    accept_IO.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startEnt();
                        }
                    });
                    accept_IO.show();
                }
            }
        });

        return true;
    }

    public void startEnt(){
        Toast.makeText(story.this, "请同意！", Toast.LENGTH_SHORT).show();
        //读写权限申请
        ActivityCompat.requestPermissions(story.this, PERMISSIONS_STORAGE, 1);
        //startActivity(new Intent().setClass(story.this, login_up.class));
    }

}
