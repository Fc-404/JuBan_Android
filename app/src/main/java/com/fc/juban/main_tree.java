package com.fc.juban;

import android.app.Activity;
import android.content.*;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import com.fc.juban.tools.*;
import com.fc.juban.server.autoSetBg;

import java.util.ArrayList;
import java.util.List;

public class main_tree extends Activity {
    SharedPreferences data;
    SharedPreferences.Editor mdData;
    private String bgCode;
    /**
     * 定义activity组件
     */
    //Layout
    private ImageView mainTreeBg;
    private LinearLayout mainTreeHead;
    private LinearLayout mainTreeHeadInfo;
    private LinearLayout mainTreeHeadInfoMain;
    private LinearLayout mainTreeHeadInfoContent;
    private LinearLayout mainTreeHeadMore;
    private LinearLayout mainTreeContent;
    private LinearLayout mainTreeEnd;
    private LinearLayout mainTreeEndLogContent;
    //Control
    private Button mainTreeHeadInfoMainShow;
    private Button mainTreeHeadMoreMain;
    private Button mainTreeHeadMoreBg;
    private Button mainTreeHeadMoreHelp;
    private Button mainTreeHeadMoreAbout;
    private Button mainTreeEndLogButton;
    //private Button mainTreeEndLogButtonHr;

    //
    public static Activity main_tree;
    //屏幕尺寸
    public Point screen;
    //日志是否展开
    private boolean isLogShow = false;
    //Info是否展开
    private boolean isInfoShow = true;
    //更多是否展开
    private boolean isMoreShow = true;
    //
    static int TimeNow = 0;
    public static Handler mine;

    /**
     * 未知bug变量区
     */
    private boolean bug_isSetBg = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_tree);
        login_up.login_up.finish();
        main_tree = this;

        //设置状态栏字体为黑色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            this.getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            );
        //startActivity(new Intent(this, set_bg.class));

        data = getSharedPreferences("bg", MODE_PRIVATE);
        mdData = data.edit();

        /**
         * 获取组件
         */
        mainTreeBg = (ImageView) findViewById(R.id.main_tree_bg);
        //head
        mainTreeHead = (LinearLayout) findViewById(R.id.main_tree_head);
        mainTreeHeadInfo = (LinearLayout) findViewById(R.id.main_tree_head_info);
        mainTreeHeadInfoMain = (LinearLayout) findViewById(R.id.main_tree_head_info_main);
        mainTreeHeadInfoMainShow = (Button) findViewById(R.id.main_tree_head_info_main_show);
        mainTreeHeadInfoContent = (LinearLayout) findViewById(R.id.main_tree_head_info_content);

        mainTreeHeadMore = (LinearLayout) findViewById(R.id.main_tree_head_more);
        mainTreeHeadMoreMain = (Button) findViewById(R.id.main_tree_head_more_main);
        mainTreeHeadMoreBg = (Button) findViewById(R.id.main_tree_head_more_bg);
        mainTreeHeadMoreHelp = (Button) findViewById(R.id.main_tree_head_more_help);
        mainTreeHeadMoreAbout = (Button) findViewById(R.id.main_tree_head_more_about);
        //content
        mainTreeContent = (LinearLayout) findViewById(R.id.main_tree_content);
        //end
        mainTreeEnd = (LinearLayout) findViewById(R.id.main_tree_end);
        mainTreeEndLogContent = (LinearLayout) findViewById(R.id.main_tree_end_log_content);
        //Control
        mainTreeEndLogButton = (Button) findViewById(R.id.main_tree_end_log_button);
        //mainTreeEndLogButtonHr = (Button) findViewById(R.id.main_tree_end_log_button_hr);

        /**
         * 设置组件属性
         */
        screen = new getScreenSize(main_tree.this).getAppSize();
        //head
        mainTreeHeadInfo.getLayoutParams().width = (int) (screen.x * 0.7);
        mainTreeHeadMore.getLayoutParams().width = (int) (screen.x * 0.3);
        mainTreeHeadInfoMain.getLayoutParams().height = (int) (mainTreeHead.getLayoutParams().height);
        mainTreeHeadMoreMain.setHeight(mainTreeHead.getLayoutParams().height);
        mainTreeHeadInfoMainShow.callOnClick();
        mainTreeHeadMoreMain.callOnClick();
        //content
        mainTreeContent.getLayoutParams().height = (int) (screen.y * 0.85);
        //end
        mainTreeEnd.getLayoutParams().height = (int) (screen.y - mainTreeHead.getLayoutParams().height - 20);
        mainTreeEndLogButton.setWidth((int) (screen.x * 0.8));
        //mainTreeEndLogButtonHr.setWidth((int)(screen.x * 0.8));

        //让其他服务呼叫本服务
        callMine();
        //设置背景
        setBg();
        //主逻辑
        mainLogic();
    }

    //主逻辑
    private void mainLogic(){
        nativeIni test = new nativeIni(this);

    }

    //背景设置
    private void setBg() {
        //背景初始化
        bgRes bg_res = new bgRes();

        if (data.getString("bgCode", "").equals("")) {
            mdData.putString("bgCode", bgRes.TIME_SUN);
            mdData.putString("bgType", "TIME");
            mdData.commit();
            this.bgCode = bgRes.TIME_SUN;
        } else {
            this.bgCode = data.getString("bgCode", "");
        }

        int bg;
        if (data.getString("bgType", "TIME").equals("TIME")) {
            bg = bg_res.image_time_id.get(
                    bg_res.image_time_name.indexOf(data.getString("bgCode", bgRes.TIME_SUN))
                    * 3 + TimeNow
            );

//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                startForegroundService(new Intent(main_tree.this, autoSetBg.class));
//            } else {
//                startService(new Intent(main_tree.this, autoSetBg.class));
//            }
            try {
                startService(new Intent(main_tree.this, autoSetBg.class));
            } catch(Exception m){}

        } else {
            bg = bg_res.image_ip_id.get(
                    bg_res.image_ip_name.indexOf(data.getString("bgCode", bgRes.IP_JZY))
            );
            stopService(new Intent(main_tree.this, autoSetBg.class));
        }

        mainTreeBg.setImageResource(bg);
    }

    //判断其他的是否展开
    private void otherClose(String mine) {
        switch (mine) {
            case "info":
                if (isMoreShow == true) mainTreeHeadMoreMain.callOnClick();
                if (isLogShow == true) mainTreeEndLogButton.callOnClick();
                break;
            case "more":
                if (isInfoShow == true) mainTreeHeadInfoMainShow.callOnClick();
                if (isLogShow == true) mainTreeEndLogButton.callOnClick();
                break;
            case "log":
                if (isMoreShow == true) mainTreeHeadMoreMain.callOnClick();
                if (isInfoShow == true) mainTreeHeadInfoMainShow.callOnClick();
                break;
            default:
                return;
        }
    }

    //Info button
    public void onInfo(View view) {
        if (isInfoShow == false) {
            otherClose("info");
            mainTreeHeadInfo.animate().translationY(0);
            isInfoShow = true;
        } else {
            mainTreeHeadInfo.animate().translationY(-mainTreeHeadInfoContent.getLayoutParams().height);
            isInfoShow = false;
        }
    }

    //日志按钮
    public void showLog(View view) {
        if (isLogShow == false) {
            otherClose("log");
            mainTreeEnd.animate().translationY(-mainTreeContent.getLayoutParams().height + 20);
            mainTreeEndLogButton.setText("收起");
            isLogShow = true;
        } else {
            mainTreeEnd.animate().translationY(0);
            mainTreeEndLogButton.setText("日志");
            isLogShow = false;
        }
    }

    //更多按钮
    public void onMore(View view) {
        if (isMoreShow == false) {
            otherClose("more");
            mainTreeHeadMore.animate().translationY(0);
            mainTreeHeadMoreMain.setText("收起");
            isMoreShow = true;
        } else {
            mainTreeHeadMore.animate().translationY(-
                    (mainTreeHeadMore.getLayoutParams().height - mainTreeHead.getLayoutParams().height)
            );
            mainTreeHeadMoreMain.setText("更多");
            isMoreShow = false;
        }
    }

    //关于按钮
    public void onAbout(View view) {
        startActivity(new Intent(main_tree.this, about.class));
    }

    //帮助按钮
    public void onHelp(View view) {
        finish();
    }

    //背景按钮
    public void onBg(View view) {
        if (bug_isSetBg == false) {
            Intent intent = new Intent(main_tree.this, set_bg.class);
            intent.putExtra("bgCode", this.bgCode);
            startActivityForResult(intent, 20);
            bug_isSetBg = true;
        }
    }

    //activity 点击事件穿透
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //
        List<Button> buttons = new ArrayList<Button>();
        buttons.add(mainTreeHeadMoreMain);
        buttons.add(mainTreeHeadMoreBg);
        buttons.add(mainTreeHeadMoreHelp);
        buttons.add(mainTreeHeadMoreAbout);
        buttons.add(mainTreeHeadInfoMainShow);
        //
        int[] xy = new int[2];
        int[] xy_end = new int[2];
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).getLocationOnScreen(xy);
            xy_end[0] = buttons.get(i).getWidth() + xy[0];
            xy_end[1] = buttons.get(i).getHeight() + xy[1];

            if (event.getX() >= xy[0] && event.getX() <= xy_end[0]
                    && event.getY() >= xy[1] && event.getY() <= xy_end[1]) {
                if (isMoreShow == false && xy[0] >= mainTreeHead.getLayoutParams().height)
                    return false;
                buttons.get(i).callOnClick();
                return false;
            }
        }

        return super.onTouchEvent(event);
    }

    //set_bg 返回事件
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != 20 || data == null) {
            return;
        }
        bug_isSetBg = false;

        if (data.getStringExtra("bgType").equals("TIME")) {
            this.mdData.putString("bgCode", data.getStringExtra("bgCode"));
            this.mdData.putString("bgType", data.getStringExtra("bgType"));
        }
        if (data.getStringExtra("bgType").equals("IP")) {
            this.mdData.putString("bgCode", data.getStringExtra("bgCode"));
            this.mdData.putString("bgType", data.getStringExtra("bgType"));
        }
        this.mdData.commit();
        setBg();

        return;
    }

    private void callMine(){
        mine = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 0 || msg.what == 1 || msg.what == 2){
                    TimeNow = msg.what;
                    setBg();
                }
            }
        };
    }

}
