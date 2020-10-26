package com.fc.juban;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.annotation.Nullable;
import com.fc.juban.module.RoundImageView;
import com.fc.juban.tools.*;

public class login_up extends Activity {
    public static Activity login_up;
    private boolean isInOrUp = false;//初始化为登录界面
    private AlertDialog.Builder stringErr;
    //定义activity里的组件，以便给其他函数使用
    private FrameLayout logup;
    private LinearLayout login;
    private LinearLayout logup_div;
    private ImageView login_bg;
    private Button logup_bt;
    private Button login_bt;
    private EditText logup_user;
    private EditText logup_pwd;
    private EditText login_user;
    private EditText login_pwd;
    //应用配置获取变量
    SharedPreferences data;
    SharedPreferences.Editor mdData;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_up);
        this.login_up = this;
        story.story.finish();

        //设置状态栏字体为黑色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            this.getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            );

        //获取应用配置
        data = getSharedPreferences("user", MODE_PRIVATE);
        mdData = data.edit();
        //判断是否登录
        if (isLogup()){
            startActivity(new Intent().setClass(login_up.this, main_tree.class));
        }

        //初始化activity里的组件
        logup = (FrameLayout) findViewById(R.id.logup);
        login = (LinearLayout) findViewById(R.id.login);
        logup_div = (LinearLayout) findViewById(R.id.logup_div);
        login_bg = (ImageView) findViewById(R.id.login_bg);
        logup_bt = (Button) findViewById(R.id.logup_bt);
        login_bt = (Button) findViewById(R.id.login_bt);
        logup_user = (EditText) findViewById(R.id.logup_user);
        logup_pwd = (EditText) findViewById(R.id.logup_pwd);
        login_user = (EditText) findViewById(R.id.login_user);
        login_pwd = (EditText) findViewById(R.id.login_pwd);

        //提前设置注册背景上移
        login_bg.animate().translationY((int) (-new getScreenSize(login_up.this).getAppSize().y * 0.8));

        //设置框架高度
        logup.getLayoutParams().height = (int) (new getScreenSize(login_up.this).getAppSize().y * 0.8);
        login.getLayoutParams().height = (int) (new getScreenSize(login_up.this).getAppSize().y * 0.8);
        logup_div.getLayoutParams().height = logup.getLayoutParams().height - 360;

        //创建登录界面圆角视图
        RoundImageView logupBg = new RoundImageView(this);
        logupBg.setScaleType(ImageView.ScaleType.CENTER_CROP);
        logupBg.setImageResource(R.mipmap.story_bg);
        logupBg.setRound(99);
        logupBg.setLocation(new boolean[]{false, false, true, true});
        logup.addView(logupBg, 0);

        //设置stringErr提示框
        stringErr = new AlertDialog.Builder(this);
        stringErr.setCancelable(false);
        stringErr.setTitle("您输入的格式有错误");
        stringErr.setMessage("用户名可以使用：\n中文、大小写英文、数字、下划线、点、加号；\n1到16位（中文最多支持8位）；\n\n" +
                "密码可以使用：\n大小写英文、数字、下划线、点、加号；\n6到16位。");
        stringErr.setPositiveButton("知道了", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });

    }

    //判断是否登录
    private boolean isLogup(){
        if (data.getString("user", "") == "")
            return false;
        else if (data.getString("user", "") != "")
            return true;
        return false;
    }

    //注册or登录按钮点击事件绑定
    public void onLogin(View view) {
        if (isInOrUp == true) {
            onLoginEnt();
            return;
        }
        isInOrUp = true;

        logup.animate().translationY((int) (-logup.getHeight() * 0.8));
        login_bg.animate().translationY(0);
    }
    public void onLogup(View view) {
        if (isInOrUp == false) {
            onLogupEnt();
            return;
        }
        isInOrUp = false;

        logup.animate().translationY(0);
        login_bg.animate().translationY((int) (-new getScreenSize(login_up.this).getAppSize().y * 0.8));
    }

    //登录或注册再次点击事件
    private void onLogupEnt() {
        String user = logup_user.getText().toString();
        String pwd = logup_pwd.getText().toString();
        if (checkStr.checkUserPwd(user, pwd) == false){
            stringErr.show();
        } else {
            //创建LoadProgressDialog
            final LoadProgressDialog logup_ing_ui=new LoadProgressDialog(login_up.this,"登录中……");
            //显示
            logup_ing_ui.show();

            final toApi logup_new = new toApi(toApi.api_logup(logup_user.getText().toString(),
                    logup_pwd.getText().toString()));
            logup_new.setCheck(new check() {
                @Override
                public void okBefore() {
                    if (logup_new.getResult().getKeyValue("return").equals("false")){
                        stringErr.setTitle("登录失败");
                        stringErr.setMessage("你是不是忘了密码呀\n快请我喝奶茶\n然后我帮你回忆0_0");
                        stringErr.show();
                    } else if (logup_new.getResult().getKeyValue("return").equals("true")){
                        mdData.putString("user", logup_user.getText().toString());
                        mdData.putString("pwd", logup_pwd.getText().toString());
                        //
                        stringErr.setTitle("登录成功");
                        stringErr.setMessage("快开始你的浪漫慢慢慢...之旅吧^_^\n漫长用在喜欢你上，就没那么讨厌啦\n嘻嘻");
                        stringErr.setPositiveButton("开始", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent().setClass(login_up.this, main_tree.class));
                            }
                        });
                        stringErr.show();
                        mdData.apply();
                    } else {
                        stringErr.setTitle("未知错误");
                        stringErr.setMessage("哪有什么未知\n只是我找不到而已*_*");
                        stringErr.show();
                    }
                    logup_ing_ui.dismiss();
                }

                @Override
                public void missBefore() {
                    stringErr.setTitle("网络错误");
                    stringErr.setMessage("有可能是我没钱运营了\n也有可能是你网络出错了\n不不不，客户永远不会错！￥_￥");
                    stringErr.show();
                    logup_ing_ui.dismiss();
                }
            });
            logup_new.get();
        }
    }
    private void onLoginEnt() {
        String user = login_user.getText().toString();
        String pwd = login_pwd.getText().toString();
        if (checkStr.checkUserPwd(user, pwd) == false){
            stringErr.show();
        } else {
            //创建LoadProgressDialog
            final LoadProgressDialog login_ing_ui=new LoadProgressDialog(login_up.this,"注册中……");
            //显示
            login_ing_ui.show();

            final toApi login_new = new toApi(toApi.api_Login(login_user.getText().toString(),
                    login_pwd.getText().toString()));
            login_new.setCheck(new check() {
                @Override
                public void okBefore() {
                    if (login_new.getResult().getKeyValue("return").equals("false")){
                        stringErr.setTitle("注册失败");
                        stringErr.setMessage("你的用户名不够特殊呦\n换个用户名试试0_0");
                        stringErr.show();
                    } else if (login_new.getResult().getKeyValue("return").equals("true")){
                        stringErr.setTitle("注册成功");
                        stringErr.setMessage("请牢记用户名和密码\n忘记之后将无法找回\n除非请我喝一杯奶茶^_^");
                        stringErr.show();
                        logup_user.setText(login_user.getText().toString());
                        logup_bt.callOnClick();
                    } else {
                        stringErr.setTitle("未知错误");
                        stringErr.setMessage("哪有什么未知\n只是我找不到而已*_*");
                        stringErr.show();
                    }
                    login_ing_ui.dismiss();
                }

                @Override
                public void missBefore() {
                    stringErr.setTitle("网络错误");
                    stringErr.setMessage("有可能是我没钱运营了\n也有可能是你网络出错了\n不不不，客户永远不会错！￥_￥");
                    stringErr.show();
                    login_ing_ui.dismiss();
                }
            });
            login_new.get();
        }
    }

}
