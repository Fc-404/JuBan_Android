package com.fc.juban;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.*;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import com.fc.juban.server.updataSeed;
import com.fc.juban.tools.*;
import com.fc.juban.server.autoSetBg;
import com.fc.juban.module.*;

import java.util.ArrayList;
import java.util.List;

public class main_tree extends Activity {
    SharedPreferences bgData;
    SharedPreferences.Editor mdBgData;
    SharedPreferences userData;
    SharedPreferences.Editor mdUserData;
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
    private LinearLayout mainTreeContentNoHall;

    private LinearLayout mainTreeEnd;
    private LinearLayout mainTreeEndLogContent;
    private LinearLayout mainTreeEndLogContentDiary;
    private RelativeLayout mainTreeLayoutAlpha;
    //Control
    private Button mainTreeHeadInfoMainShow;
    private Button mainTreeHeadMoreMain;
    private Button mainTreeHeadMoreBg;
    private Button mainTreeHeadMoreHelp;
    private Button mainTreeHeadMoreAbout;
    private Button mainTreeEndLogButton;
    private TextView lowerWorld;
    //private Button mainTreeEndLogButtonHr;
    private TextView mainTreeHeadInfoContentWo;
    private TextView mainTreeHeadInfoContentTa;
    private TextView mainTreeHeadInfoContentBirthday;
    private TextView mainTreeHeadInfoContentLove;
    private TextView mainTreeHeadInfoContentLevel;
    private TextView mainTreeHeadInfoMainLove;
    private TextView mainTreeHeadInfoMainWo;
    private TextView mainTreeHeadInfoMainTa;
    private TextView mainTreeHeadInfoMainLevel;
    private ImageView mainTreeContentYesTree;
    private ImageView mainTreeContentYesBack;

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
    //动态壁纸事件
    static int TimeNow = 0;
    //接收外部消息
    public static Handler mine;
    public static Handler moodSet;
    //Head点击事件时间戳
    private long headTs = 0;
    //登录验证次数
    private int logupCount = 3;
    //用户名
    private String userID;

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

        //获取背景配置
        bgData = getSharedPreferences("bg", MODE_PRIVATE);
        mdBgData = bgData.edit();
        userData = getSharedPreferences("user", MODE_PRIVATE);
        mdUserData = userData.edit();
        this.userID = userData.getString("user", "");

        /**
         * 获取组件
         */
        mainTreeBg = (ImageView) findViewById(R.id.main_tree_bg);
        mainTreeLayoutAlpha = (RelativeLayout) findViewById(R.id.main_tree_layout_alpha);
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
        mainTreeHeadInfoContentWo = (TextView) findViewById(R.id.main_tree_head_info_content_wo);
        mainTreeHeadInfoContentTa = (TextView) findViewById(R.id.main_tree_head_info_content_ta);
        mainTreeHeadInfoContentBirthday = (TextView) findViewById(R.id.main_tree_head_info_content_birthday);
        mainTreeHeadInfoContentLove = (TextView) findViewById(R.id.main_tree_head_info_content_love);
        mainTreeHeadInfoContentLevel = (TextView) findViewById(R.id.main_tree_head_info_content_level);
        mainTreeHeadInfoMainWo = (TextView) findViewById(R.id.main_tree_head_info_main_wo);
        mainTreeHeadInfoMainTa = (TextView) findViewById(R.id.main_tree_head_info_main_ta);
        mainTreeHeadInfoMainLove = (TextView) findViewById(R.id.main_tree_head_info_main_love);
        mainTreeHeadInfoMainLevel = (TextView) findViewById(R.id.main_tree_head_info_main_level);
        //content
        mainTreeContent = (LinearLayout) findViewById(R.id.main_tree_content);
        mainTreeContentNoHall = (LinearLayout) findViewById(R.id.main_tree_content_no_hall);
        mainTreeContentYesTree = (ImageView) findViewById(R.id.main_tree_content_yes_tree);
        mainTreeContentYesBack = (ImageView) findViewById(R.id.main_tree_content_yes_back);
        lowerWorld = (TextView) findViewById(R.id.lowerWorld);
        //end
        mainTreeEnd = (LinearLayout) findViewById(R.id.main_tree_end);
        mainTreeEndLogContent = (LinearLayout) findViewById(R.id.main_tree_end_log_content);
        mainTreeEndLogContentDiary = (LinearLayout) findViewById(R.id.main_tree_end_log_content_diary);
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
    private void mainLogic() {
        /**
         * miss刷新
         * 逻辑刷新
         */
        //用于延时执行逻辑刷新
        final Handler m = new Handler();
        final Runnable g0 = new Runnable() {
            @Override
            public void run() {
                mainLogic();
            }
        };
        final Runnable g1 = new Runnable() {
            @Override
            public void run() {
                getMissView(userData.getString("user", ""));
                m.postDelayed(this, 3000);
            }
        };

        final String userName = userData.getString("user", "");
        mainTreeHeadInfoContentWo.setText(userName == "" ? "Wo" : userName);
        mainTreeHeadInfoMainWo.setText(userName == "" ? "Wo" : userName);

        /**
         * 登录逻辑
         */
        final toApi logup = new toApi();
        logup.set(toApi.api_logup(userName, userData.getString("pwd", ""))).get();
        logup.setCheck(new check() {
            @Override
            public void okBefore() {
                if ("true".equals(logup.getResult().getKeyValue("return"))) {
                    //登录成功
                    //Toast.makeText(main_tree, "登录成功", Toast.LENGTH_SHORT).show();
                    logupCount = 3;
                } else {
                    if (logupCount > 0) {
                        logupCount--;
                        Runnable logupAgain = new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(main_tree, "小伴正在尝试重新登录", Toast.LENGTH_LONG).show();
                                mainLogic();
                            }
                        };
                        m.postDelayed(logupAgain, 5000);
                        return;
                    }


                    AlertDialog.Builder returnLogup = new AlertDialog.Builder(main_tree);
                    returnLogup.setTitle("验证失败");
                    returnLogup.setMessage("你的账号验证失败，请重新登录，如有问题请联系管理员\n记得带杯奶茶哦");
                    returnLogup.setPositiveButton("好的呢", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mdUserData.putString("user", null);
                            mdUserData.putString("pwd", null);
                            mdUserData.commit();
                            while (true) {
                                try {
                                    stopService(new Intent(main_tree, updataSeed.class));
                                    break;
                                } catch (Exception m) {
                                    m.printStackTrace();
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                            fileIO.deleteToSD("Juban/diary.ini");
                            fileIO.deleteToSD("Juban/ini.ini");
                            startActivity(new Intent(main_tree, login_up.class));
                            finish();
                        }
                    });
                    returnLogup.show();
                }
            }

            @Override
            public void missBefore() {
                Toast.makeText(main_tree, "同学，你断网了，也可能我没钱了", Toast.LENGTH_LONG).show();
            }
        });

        /**
         * 查seed表
         */
        final boolean[] isSeed = {false};
        final toApi apiFindSeed = new toApi();
        apiFindSeed.set(toApi.api_findSeed(userName)).get();
        apiFindSeed.setCheck(new check() {
            @Override
            public void okBefore() {
                m.removeCallbacks(g0);
                if ("false".equals(apiFindSeed.getResult().getKeyValue("return"))) {
                    /**
                     * seed失败
                     */
                    //设置yes不可见
                    findViewById(R.id.main_tree_content_include_yes).setVisibility(View.GONE);
                    //设置刷新按钮
                    findViewById(R.id.main_tree_content_no_updata).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(main_tree, "刷新了一下下", Toast.LENGTH_LONG).show();
                            mainLogic();
                        }
                    });
                    //获取自己的need
                    getNeedView(userName);
                    //获取全部need，用于展示大厅
                    getAllNeedView();
                    //3秒获取自己的miss表
                    m.postDelayed(g1, 0);
                } else {
                    /**
                     * seed成功
                     */
                    m.removeCallbacks(g1);
                    mdUserData.putString("seed", apiFindSeed.getResult().getKeyValue("seedId")).commit();
                    getSeedInfo();
                    try {
                        startService(new Intent(main_tree, updataSeed.class));
                    } catch (Exception m) {
                        m.printStackTrace();
                    }
                    /**
                     * lower world
                     */
                    final toApi lowerWorldObj = new toApi();
                    lowerWorldObj.set(toApi.lowerWorld).get();
                    lowerWorldObj.setCheck(new check() {
                        @Override
                        public void okBefore() {
                            if (!"".equals(lowerWorldObj.getResult().getKeyValue("lower")))
                                lowerWorld.setText(lowerWorldObj.getResult().getKeyValue("lower"));
                        }

                        @Override
                        public void missBefore() {

                        }
                    });
                }
            }

            @Override
            public void missBefore() {
                Toast.makeText(main_tree, "你怕我顺着网线找你吗\u3000没网啦", Toast.LENGTH_LONG).show();
//                莫名其妙的延迟功能，有空研究
//                //延时函数
//                new Timer().schedule(new TimerTask() {
//                    @Override
//                    public void run() {
//                        //运行在UI线程上
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                mainLogic();
//                            }
//                        });
//                    }
//                }, 10000);

                if (!"".equals(userData.getString("seed", "")))
                    romanceRoadView();
                m.postDelayed(g0, 10000);
            }
        });

    }

    //获取seedINFO
    private void getSeedInfo() {
        if (userData.getString("seed", "") == ""){
            mainLogic();
            return;
        }
        /**
         * seed成功
         */
        //获取seed信息
        final toApi getSeedInfo = new toApi();
        getSeedInfo.set(toApi.api_getSeedInfo(userData.getString("seed", ""))).get();
        final check getSeedInfoResult = new check() {

            @Override
            public void okBefore() {
                if ("false".equals(getSeedInfo.getResult().getKeyValue("return"))) {
                    Toast.makeText(main_tree, "服务器脑子瓦特了", Toast.LENGTH_LONG);
                    mainLogic();
                    return;
                }
                /**
                 * 把信息保存在本地
                 */
                final twoArray infoResult = getSeedInfo.getResult();
                final nativeIni seedIni = new nativeIni(main_tree);
                //DIARY group
                List<String> strKey = infoResult.getRootList("DIARY");
                int indexDiary = seedIni.getDiary().rootLength();
                for (int i = indexDiary; i < strKey.size(); i++) {
                    String key = strKey.get(i);
                    String value = infoResult.getKeyValue(key, "DIARY");
                    seedIni.setDiary(key, value);
                }
                //INFO group
                strKey = infoResult.getRootList("INFO");
                for (int i = 0; i < strKey.size(); i++) {
                    String key = strKey.get(i);
                    String value = infoResult.getKeyValue(key, "INFO");
                    seedIni.setIni(key, value, "INFO");
                }
                //CD group
                strKey = infoResult.getRootList("CD");
                for (int i = 0; i < strKey.size(); i++) {
                    String key = strKey.get(i);
                    String value = infoResult.getKeyValue(key, "CD");
                    seedIni.setIni(key, value, "CD");
                }

                //绘制本地视图
                romanceRoadView();
                //Toast.makeText(main_tree, "getSeedOk", Toast.LENGTH_LONG).show();
            }

            @Override
            public void missBefore() {
                Toast.makeText(main_tree, "动动小手，检查下网络", Toast.LENGTH_LONG);
            }
        };
        getSeedInfo.setCheck(getSeedInfoResult);
    }

    /**
     * 本地ini渲染界面
     */
    private void romanceRoadView() {
        findViewById(R.id.main_tree_content_include_no).setVisibility(View.GONE);
        findViewById(R.id.main_tree_content_include_yes).setVisibility(View.VISIBLE);

        final String myName = userData.getString("user", "");
        final twoArray iniObj = new nativeIni(this).getIni();
        final twoArray diaryObj = new nativeIni(this).getIni();
        if (myName.equals(iniObj.getKeyValue("A", "INFO"))) {
            mainTreeHeadInfoContentTa.setText(iniObj.getKeyValue("B", "INFO"));
            mainTreeHeadInfoMainTa.setText(iniObj.getKeyValue("B", "INFO"));
        } else {
            mainTreeHeadInfoContentTa.setText(iniObj.getKeyValue("A", "INFO"));
            mainTreeHeadInfoMainTa.setText(iniObj.getKeyValue("A", "INFO"));
        }

        mainTreeHeadInfoContentBirthday.setText(iniObj.getKeyValue("birthday", "INFO"));
        mainTreeHeadInfoContentLove.setText(iniObj.getKeyValue("love", "INFO"));
        mainTreeHeadInfoMainLove.setText(iniObj.getKeyValue("love", "INFO"));

        //int level = (int) Math.floor(Math.pow((Integer.parseInt(iniObj.getKeyValue("love", "INFO")) / 10.0), 1.0/2));
        int level = leaveCalc(Integer.parseInt(iniObj.getKeyValue("love", "INFO")));
        mainTreeHeadInfoMainLevel.setText(level + "");
        mainTreeHeadInfoContentLevel.setText(level + "");

        final moodRes moods = new moodRes();
        final ImageView moodImg = (ImageView) findViewById(R.id.main_tree_content_mood);
        final ImageView moodTaImg = (ImageView) findViewById(R.id.main_tree_content_moodTa);
        moodImg.setImageResource(moods.get(iniObj.getKeyValue(userID + "mood", "INFO")));
        moodTaImg.setImageResource(moods.get(iniObj.getKeyValue(
                (iniObj.getKeyValue("A", "INFO").equals(userID) ?
                        iniObj.getKeyValue("B", "INFO") :
                        iniObj.getKeyValue("A", "INFO"))
                        + "mood", "INFO")));

        diaryViewDraw();

        //根据leave设置tree
        if (level < 10){
            mainTreeContentYesTree.setImageResource(R.mipmap.zhongzi);
            mainTreeContentYesBack.setImageResource(R.mipmap.xiaotudi);
        } else if (level < 20){
            mainTreeContentYesTree.setImageResource(R.mipmap.xiaoyoumiao);
        } else if (level < 30) {
            mainTreeContentYesTree.setImageResource(R.mipmap.dayoumiao);
        } else if (level < 40) {
            mainTreeContentYesTree.setImageResource(R.mipmap.xiaoshumiao);
        } else if (level < 50) {
            mainTreeContentYesTree.setImageResource(R.mipmap.dashumiao);
        } else if (level < 60) {
            mainTreeContentYesTree.setImageResource(R.mipmap.xiaochengshu);
            mainTreeContentYesBack.setImageResource(R.mipmap.caodi);
        } else {
            mainTreeContentYesTree.setImageResource(R.mipmap.dachengshupng);
            mainTreeContentYesBack.setImageResource(R.mipmap.yinghuacaodi);
        }
    }
    private int leaveCalc(int love){
        int sum = 0;
        for (int i = 0; ; i++){
            sum += 10 * i * i;
            if (sum > love)
                return i - 1;
        }
    }

    /**
     * 界面yes 事件绑定
     */
    public void mood(final View m) {
        Q(m);
        //配置文件
        nativeIni ini = new nativeIni(main_tree);
        //表情资源文件
        final moodRes moods = new moodRes();

        /**
         * 表情选择视图
         */
        final AlertDialog.Builder show = new AlertDialog.Builder(main_tree);
        show.setTitle("选择心情");
        show.setIcon(moods.get(ini.getIni().getKeyValue(userID + "mood", "INFO")));
        show.setView(new mainYesViews(main_tree).moodOptionView());
        show.show();

        moodSet = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                final toApi moodUp = new toApi();
                moodUp.set(toApi.api_mdSeedInfo(userData.getString("seed", ""), userID, "mood", moods.R2name(msg.what))).get();
                moodUp.setCheck(new check() {
                    @Override
                    public void okBefore() {
                        if ("true".equals(moodUp.getResult().getKeyValue("return"))) {
                            Toast.makeText(main_tree, "设置心情成功", Toast.LENGTH_LONG).show();
                            getSeedInfo();
                        } else
                            Toast.makeText(main_tree, "你的心情丢在网线上了，请重试", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void missBefore() {
                        Toast.makeText(main_tree, "没有互联网传递你的心情", Toast.LENGTH_LONG).show();
                    }
                });
            }
        };
    }

    public void pendant(final View m) {
        Q(m);
        Toast.makeText(main_tree, "待开发，敬请期待*_*\u3000怕是得等到天荒地老", Toast.LENGTH_LONG).show();
    }

    public void leave(final View m) {
        Q(m);

        if (Integer.parseInt(mainTreeHeadInfoMainLevel.getText().toString()) < 10){
            Toast.makeText(main_tree, "等级不够\u3000暂不可用", Toast.LENGTH_LONG).show();
            return;
        }

        final AlertDialog.Builder leaveView = new AlertDialog.Builder(main_tree);
        final AlertDialog[] leaveClose = new AlertDialog[1];
        final EditText leaveWord = new EditText(main_tree);
        LinearLayout leaveDiv = new mainYesViews(main_tree).leaveView(leaveWord,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!checkStr.checkLeave(leaveWord.getText().toString())) {
                            Toast.makeText(main_tree, "文字中出现非常规字符\u3000请重试", Toast.LENGTH_LONG).show();
                            return;
                        }

                        final toApi leaveUp = new toApi();
                        leaveUp.set(toApi.api_addSeedInfo(
                                userData.getString("seed", ""),
                                userData.getString("user", ""),
                                "leave", leaveWord.getText().toString())).get();
                        leaveUp.setCheck(new check() {
                            @Override
                            public void okBefore() {
                                if ("true".equals(leaveUp.getResult().getKeyValue("return"))) {
                                    Toast.makeText(main_tree, "留言成功", Toast.LENGTH_LONG).show();
                                    addLove(2);
                                    leaveClose[0].dismiss();
                                } else
                                    Toast.makeText(main_tree, "你的留言丢失在茫茫人海中了", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void missBefore() {
                                Toast.makeText(main_tree, "网线可能有脾气", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });

        leaveView.setTitle(" ");
        leaveView.setIcon(R.mipmap.leave);
        leaveClose[0] = leaveView.setView(leaveDiv).show();
    }

    public void watering(final View m) {
        Q(m);
        mainTreeContentYesTree.callOnClick();

        nativeIni iniInfo = new nativeIni(main_tree);
        long lastTime = Long.parseLong(iniInfo.getIni().getKeyValue("watering", "CD"));
        long timeInterval = System.currentTimeMillis() - lastTime;
        if (timeInterval < 1800000){
            int hour = (int) ((1800000 - timeInterval) / 60000) / 60;
            int minute = (int) ((1800000 - timeInterval) / 60000) % 60;
            Toast.makeText(main_tree, "请\u3000" + hour + "h:" + minute + "min\u3000后再浇水", Toast.LENGTH_LONG).show();
            return;
        }

        final toApi watering = new toApi();
        watering.set(toApi.api_mdSeedInfo(userData.getString("seed", ""), userID, "watering",
                Long.toString(System.currentTimeMillis()) )).get();
        watering.setCheck(new check() {
            @Override
            public void okBefore() {
                if ("true".equals(watering.getResult().getKeyValue("return"))){
                    addLove(2, new runCode() {
                        @Override
                        public void run() {
                            Toast.makeText(main_tree, "成功浇水，加2成长值", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void missBefore() {
                Toast.makeText(main_tree, "你断网了", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void manure(final View m) {
        Q(m);
        mainTreeContentYesTree.callOnClick();

        nativeIni iniInfo = new nativeIni(main_tree);
        long lastTime = Long.parseLong(iniInfo.getIni().getKeyValue("manure", "CD"));
        long timeInterval = System.currentTimeMillis() - lastTime;
        if (timeInterval < 3600000){
            int hour = (int) ((3600000 - timeInterval) / 60000) / 60;
            int minute = (int) ((3600000 - timeInterval) / 60000) % 60;
            Toast.makeText(main_tree, "请\u3000" + hour + "h:" + minute + "min\u3000后再施肥", Toast.LENGTH_LONG).show();
            return;
        }

        final toApi watering = new toApi();
        watering.set(toApi.api_mdSeedInfo(userData.getString("seed", ""), userID, "manure",
                Long.toString(System.currentTimeMillis()) )).get();
        watering.setCheck(new check() {
            @Override
            public void okBefore() {
                if ("true".equals(watering.getResult().getKeyValue("return"))){
                    addLove(4, new runCode() {
                        @Override
                        public void run() {
                            Toast.makeText(main_tree, "成功施肥，加4成长值", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void missBefore() {
                Toast.makeText(main_tree, "你断网了", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void debug(final View m) {
        Q(m);
        mainTreeContentYesTree.callOnClick();

        nativeIni iniInfo = new nativeIni(main_tree);
        long lastTime = Long.parseLong(iniInfo.getIni().getKeyValue("debug", "CD"));
        long timeInterval = System.currentTimeMillis() - lastTime;
        if (timeInterval < 5400000){
            int hour = (int) ((5400000 - timeInterval) / 60000) / 60;
            int minute = (int) ((5400000 - timeInterval) / 60000) % 60;
            Toast.makeText(main_tree, "请\u3000" + hour + "h:" + minute + "min\u3000后再杀虫", Toast.LENGTH_LONG).show();
            return;
        }

        final toApi watering = new toApi();
        watering.set(toApi.api_mdSeedInfo(userData.getString("seed", ""), userID, "debug",
                Long.toString(System.currentTimeMillis()) )).get();
        watering.setCheck(new check() {
            @Override
            public void okBefore() {
                if ("true".equals(watering.getResult().getKeyValue("return"))){
                    addLove(6, new runCode() {
                        @Override
                        public void run() {
                            Toast.makeText(main_tree, "杀虫成功，加6成长值", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void missBefore() {
                Toast.makeText(main_tree, "你断网了", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void weeding(final View m) {
        Q(m);
        mainTreeContentYesTree.callOnClick();

        nativeIni iniInfo = new nativeIni(main_tree);
        long lastTime = Long.parseLong(iniInfo.getIni().getKeyValue("weeding", "CD"));
        long timeInterval = System.currentTimeMillis() - lastTime;
        if (timeInterval < 7200000){
            int hour = (int) ((7200000 - timeInterval) / 60000) / 60;
            int minute = (int) ((7200000 - timeInterval) / 60000) % 60;
            Toast.makeText(main_tree, "请\u3000" + hour + "h:" + minute + "min\u3000后再除草", Toast.LENGTH_LONG).show();
            return;
        }

        final toApi watering = new toApi();
        watering.set(toApi.api_mdSeedInfo(userData.getString("seed", ""), userID, "weeding",
                Long.toString(System.currentTimeMillis()) )).get();
        watering.setCheck(new check() {
            @Override
            public void okBefore() {
                if ("true".equals(watering.getResult().getKeyValue("return"))){
                    addLove(8, new runCode() {
                        @Override
                        public void run() {
                            Toast.makeText(main_tree, "除草成功，加8成长值", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void missBefore() {
                Toast.makeText(main_tree, "你断网了", Toast.LENGTH_LONG).show();
            }
        });
    }

    //加经验
    public void addLove(int love){
        addLove(love, null);
    }
    public void addLove(int love, final runCode code) {
        final toApi addLove = new toApi();
        addLove.set(toApi.api_mdSeedInfo(userData.getString("seed", ""), userID, "love", Integer.toString(love))).get();
        addLove.setCheck(new check() {
            @Override
            public void okBefore() {
                if (!"true".equals(addLove.getResult().getKeyValue("return")))
                    Toast.makeText(main_tree, "love消失了", Toast.LENGTH_SHORT).show();
                if (!(code == null)) code.run();
            }

            @Override
            public void missBefore() {
                Toast.makeText(main_tree, "love会消失吗", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void Q(final View m) {
        m.animate().scaleY(0.8f).setDuration(100).withEndAction(new Runnable() {
            @Override
            public void run() {
                m.animate().scaleY(1);
            }
        });
    }
    public static void Q_s(final View m) {
        m.animate().scaleY(0.8f).setDuration(100).withEndAction(new Runnable() {
            @Override
            public void run() {
                m.animate().scaleY(1);
            }
        });
    }
    public void Q1(final View m){
        m.animate().scaleX(1.2f).setDuration(55).withEndAction(new Runnable() {
            @Override
            public void run() {
                m.animate().scaleX(0.8f).setDuration(105).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        m.animate().scaleX(1.18f).setDuration(65).withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                m.animate().scaleX(0.88f).setDuration(76).withEndAction(new Runnable() {
                                    @Override
                                    public void run() {
                                        m.animate().scaleX(1.13f).setDuration(86).withEndAction(new Runnable() {
                                            @Override
                                            public void run() {
                                                m.animate().scaleX(1f).setDuration(100);
                                            }
                                        });;;
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });
    }

    //diary view draw
    private void diaryViewDraw() {
        mainTreeEndLogContentDiary.removeAllViews();

        twoArray diary = new nativeIni(main_tree).getDiary();
        List<String> diaryId = diary.getRootList();
        int diaryLength = diary.rootLength();

        for (int i = (diaryLength - 100 < 0 ? 0 : diaryLength - 100); i < diaryLength; i++) {
            try {
                String time = diaryId.get(i);
                String Ymd = time.substring(0, 10);
                String his = time.substring(11);
                String content = diary.getKeyValue(time);
                String name = content.substring(0, content.indexOf("###"));
                content = content.substring(name.length() + 3);
                String type = content.substring(0, content.indexOf("###"));
                content = content.substring(type.length() + 3);
                String value = content;

                if ("leave".equals(type))
                    mainTreeEndLogContentDiary.addView(new diaryViews(main_tree).leaveView(name, value, Ymd + "\n" + his), 0);
                else
                    mainTreeEndLogContentDiary.addView(new diaryViews(main_tree).logsView(name, value, Ymd + ":" + his), 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * toApi函数，界面no
     */
    private void getNeedView(final String userName) {
        /**
         * 查need表
         */
        final toApi apiFindNeed = new toApi();
        apiFindNeed.set(toApi.api_findNeed(userName)).get();
        apiFindNeed.setCheck(new check() {
            @Override
            public void okBefore() {
                final TextView selfNeed = (TextView) findViewById(R.id.main_tree_content_no_selfneed);
                if ("false".equals(apiFindNeed.getResult().getKeyValue("return"))) {
                    selfNeed.setText(randPhrase.mineSign() + "\n编辑之后会发送到大厅");
                } else if ("true".equals(apiFindNeed.getResult().getKeyValue("return"))) {
                    selfNeed.setText(apiFindNeed.getResult().getKeyValue("sign"));
                }
                /**
                 * 编辑need
                 */
                Button selfEdit = (Button) findViewById(R.id.main_tree_content_no_selfedit);
                selfEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final EditText inputServer = new EditText(main_tree.this);
                        inputServer.setText(selfNeed.getText());
                        AlertDialog.Builder builder = new AlertDialog.Builder(main_tree.this);
                        builder.setTitle("编辑合种邀请").setView(inputServer)
                                .setNegativeButton("取消", null);
                        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (!checkStr.checkNeed(inputServer.getText().toString())) {
                                    Toast.makeText(main_tree, "只能编辑1-21位[中英文、数字、下划线、点、加号]", Toast.LENGTH_LONG).show();
                                    return;
                                }
                                final toApi mdNeed = new toApi();
                                mdNeed.set(toApi.api_mdNeed(userName, inputServer.getText().toString())).get();
                                mdNeed.setCheck(new check() {
                                    @Override
                                    public void okBefore() {
                                        if ("true".equals(mdNeed.getResult().getKeyValue("return")))
                                            Toast.makeText(main_tree, "修改成功", Toast.LENGTH_LONG).show();
                                        else
                                            Toast.makeText(main_tree, "修改失败", Toast.LENGTH_LONG).show();
                                        getNeedView(userName);
                                        getAllNeedView();
                                    }

                                    @Override
                                    public void missBefore() {
                                        Toast.makeText(main_tree, "网络错误", Toast.LENGTH_LONG);
                                    }
                                });
                            }
                        });
                        builder.show();
                    }
                });
            }

            @Override
            public void missBefore() {
                Toast.makeText(main_tree, "need miss", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getAllNeedView() {
        /**
         * 获取need表
         */
        final toApi apiFindNeedAll = new toApi();
        apiFindNeedAll.set(toApi.api_findNeed("all")).get();
        apiFindNeedAll.setCheck(new check() {
            @Override
            public void okBefore() {
                if ("true".equals(apiFindNeedAll.getResult().getKeyValue("return"))) {
                    //移除所有元素
                    mainTreeContentNoHall.removeAllViews();

                    final twoArrayR needResultR = apiFindNeedAll.getResultR();
                    for (int i = 0; i < needResultR.groupLength("need"); i++) {
                        final String name = needResultR.getKeyValue("id", "need", i);
                        final LinearLayout needView = new mainNoViews(main_tree).cardTCB(
                                name,
                                needResultR.getKeyValue("sign", "need", i),
                                "喜欢Ta",
                                //按钮事件
                                new Button.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        //判断是否点击了自己
                                        if (userData.getString("user", "").equals(name)) {
                                            Toast.makeText(main_tree, "不可以喜欢自己哟", Toast.LENGTH_LONG).show();
                                            return;
                                        }
                                        //点击其他时的提示
                                        final AlertDialog.Builder missAck = new AlertDialog.Builder(main_tree);
                                        missAck.setTitle("谨慎");
                                        missAck.setMessage("你确定给Ta发送合种邀请吗？");
                                        missAck.setNegativeButton("取消", null);
                                        //确定后的事件
                                        missAck.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                //逻辑判断是否重复
                                                final toApi missIs = new toApi();
                                                //如果我点击了miss，会在服务端查看我的miss是否是我，是则不能继续miss，否则置空本地miss
                                                missIs.set(toApi.api_findMiss(userData.getString("missTa", ""))).get();
                                                missIs.setCheck(new check() {
                                                    @Override
                                                    public void okBefore() {
                                                        if ("true".equals(missIs.getResult().getKeyValue("return")) &&
                                                                userData.getString("user", "").equals(
                                                                        missIs.getResult().getKeyValue("missId"))) {
                                                            Toast.makeText(main_tree, "你已经发送了一个喜欢", Toast.LENGTH_LONG).show();
                                                            return;
                                                        } else {
                                                            mdUserData.putString("missTa", "");
                                                            mdUserData.commit();
                                                            //检查我想miss的存不存在miss表，如果存在就退出
                                                            final toApi missFind = new toApi();
                                                            missFind.set(toApi.api_findMiss(name)).get();
                                                            missFind.setCheck(new check() {
                                                                @Override
                                                                public void okBefore() {
                                                                    if ("true".equals(missFind.getResult().getKeyValue("return"))) {
                                                                        Toast.makeText(main_tree, "你来迟了，Ta已名花有主了", Toast.LENGTH_LONG).show();
                                                                        return;
                                                                    }
                                                                    final toApi missI = new toApi();
                                                                    missI.set(toApi.api_findMiss(userData.getString("user", ""))).get();
                                                                    missI.setCheck(new check() {
                                                                        @Override
                                                                        public void okBefore() {
                                                                            if ("true".equals(missI.getResult().getKeyValue("return"))) {
                                                                                Toast.makeText(main_tree, "有人想和你合种请先拒绝或接受", Toast.LENGTH_LONG).show();
                                                                                return;
                                                                            }
                                                                            final toApi findSeed = new toApi();
                                                                            findSeed.set(toApi.api_findSeed(userID)).get();
                                                                            findSeed.setCheck(new check() {
                                                                                @Override
                                                                                public void okBefore() {
                                                                                    if ("false".equals(findSeed.getResult().getKeyValue("return"))) {
                                                                                        //否则就建立miss关系
                                                                                        final toApi missObj = new toApi();
                                                                                        missObj.set(toApi.api_mdMiss(name, userData.getString("user", ""))).get();
                                                                                        missObj.setCheck(new check() {
                                                                                            @Override
                                                                                            public void okBefore() {
                                                                                                if ("true".equals(missObj.getResult().getKeyValue("return"))) {
                                                                                                    Toast.makeText(main_tree, "已发送请求，请耐心等待", Toast.LENGTH_LONG).show();
                                                                                                    mdUserData.putString("missTa", name);
                                                                                                    mdUserData.commit();
                                                                                                } else
                                                                                                    Toast.makeText(main_tree, "发送失败", Toast.LENGTH_LONG).show();
                                                                                            }

                                                                                            @Override
                                                                                            public void missBefore() {
                                                                                                Toast.makeText(main_tree, "网络错误", Toast.LENGTH_LONG).show();
                                                                                            }
                                                                                        });
                                                                                    } else {
                                                                                        mainLogic();
                                                                                    }
                                                                                }

                                                                                @Override
                                                                                public void missBefore() {

                                                                                }
                                                                            });
                                                                        }

                                                                        @Override
                                                                        public void missBefore() {
                                                                            Toast.makeText(main_tree, "网络错误", Toast.LENGTH_LONG).show();
                                                                        }
                                                                    });

                                                                }

                                                                @Override
                                                                public void missBefore() {
                                                                    Toast.makeText(main_tree, "网络错误", Toast.LENGTH_LONG).show();
                                                                }
                                                            });
                                                        }
                                                    }

                                                    @Override
                                                    public void missBefore() {
                                                        Toast.makeText(main_tree, "网络错误", Toast.LENGTH_LONG).show();
                                                    }
                                                });

                                            }
                                        });
                                        missAck.show();
                                    }
                                }
                        );
                        mainTreeContentNoHall.addView(needView, 0);
                    }
                }
            }

            @Override
            public void missBefore() {
                Toast.makeText(main_tree, "获取大厅信息失败", Toast.LENGTH_LONG).show();
            }
        });
        /**
         * 上方代码及其糟糕
         * 尽管需要多重嵌套
         * 也应该把实参实例
         * 多重嵌套避免匿名
         * 下方代码嵌套更多
         * 使用实例对象实参
         * 大幅提高代码阅读
         */
    }

    private void getMissView(final String userName) {
        /**
         * 获取miss信息
         */
        final toApi apiFindMiss = new toApi();
        apiFindMiss.set(toApi.api_findMiss(userName)).get();
        apiFindMiss.setCheck(new check() {
            @Override
            public void okBefore() {
                if ("true".equals(apiFindMiss.getResult().getKeyValue("return"))) {
                    final LinearLayout missView = (LinearLayout) findViewById(R.id.main_tree_content_no_miss);
                    TextView missText = (TextView) findViewById(R.id.main_tree_content_no_miss_text);
                    missText.setText(apiFindMiss.getResult().getKeyValue("missId") + "\u3000想和你开启浪漫之旅");
                    missView.setVisibility(View.VISIBLE);
                    Button missAccept = (Button) findViewById(R.id.main_tree_content_no_miss_accept);
                    Button missRefuse = (Button) findViewById(R.id.main_tree_content_no_miss_refuse);
                    //接受
                    missAccept.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //接受miss请求
                            final String Ta = apiFindMiss.getResult().getKeyValue("missId");
                            final String seedStr = getMD5.get(userName + Ta);
                            final toApi apiDelMissA = new toApi();
                            final toApi apiDelMissB = new toApi();
                            final toApi apiDelNeedA = new toApi();
                            final toApi apiDelNeedB = new toApi();
                            final toApi apiMdSeedA = new toApi();
                            final toApi apiMdSeedB = new toApi();
                            final toApi apiCreateSeed = new toApi();


                            final check createSeed = new check() {
                                @Override
                                public void okBefore() {
                                    /**
                                     * create seed ok
                                     */
                                    if (!"false".equals(apiCreateSeed.getResult().getKeyValue("return"))) {
                                        //重新执行主逻辑
                                        mainLogic();
                                    } else {
                                        Toast.makeText(main_tree, "Error create seed", Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void missBefore() {
                                    Toast.makeText(main_tree, "NetworkError create seed", Toast.LENGTH_LONG).show();
                                }
                            };
                            final check mdSeedB = new check() {
                                @Override
                                public void okBefore() {
                                    /**
                                     * modification seed B ok
                                     */
                                    if (!"false".equals(apiMdSeedB.getResult().getKeyValue("return"))) {
                                        apiCreateSeed.set(toApi.api_createSeedInfo(seedStr, userName, Ta)).get();
                                        apiCreateSeed.setCheck(createSeed);
                                    } else {
                                        Toast.makeText(main_tree, "Error modification seed B", Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void missBefore() {
                                    Toast.makeText(main_tree, "NetworkError md seed B", Toast.LENGTH_LONG).show();
                                }
                            };
                            final check mdSeedA = new check() {
                                @Override
                                public void okBefore() {
                                    /**
                                     * modification seed A ok
                                     */
                                    if (!"false".equals(apiMdSeedA.getResult().getKeyValue("return"))) {
                                        apiMdSeedB.set(toApi.api_mdSeed(Ta, seedStr)).get();
                                        apiMdSeedB.setCheck(mdSeedB);
                                    } else {
                                        Toast.makeText(main_tree, "Error modification seed A", Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void missBefore() {
                                    Toast.makeText(main_tree, "NetworkError md seed A", Toast.LENGTH_LONG).show();
                                }
                            };
                            final check DelNeedB = new check() {
                                @Override
                                public void okBefore() {
                                    /**
                                     * del need B ok
                                     */
                                    if (!"false".equals(apiDelNeedB.getResult().getKeyValue("return"))) {
                                        apiMdSeedA.set(toApi.api_mdSeed(userName, seedStr)).get();
                                        apiMdSeedA.setCheck(mdSeedA);
                                    } else {
                                        Toast.makeText(main_tree, "Error del need B", Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void missBefore() {
                                    Toast.makeText(main_tree, "NetworkError del need B", Toast.LENGTH_LONG).show();
                                }
                            };
                            final check DelNeedA = new check() {
                                @Override
                                public void okBefore() {
                                    /**
                                     * del need A ok
                                     */
                                    if (!"false".equals(apiDelNeedA.getResult().getKeyValue("return"))) {
                                        apiDelNeedB.set(toApi.api_delNeed(userName)).get();
                                        apiDelNeedB.setCheck(DelNeedB);
                                    } else {
                                        Toast.makeText(main_tree, "Error del need A", Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void missBefore() {
                                    Toast.makeText(main_tree, "NetworkError del need A", Toast.LENGTH_LONG).show();
                                }
                            };
                            final check DelMissB = new check() {
                                @Override
                                public void okBefore() {
                                    /**
                                     * del need A ok
                                     */
                                    if (!"false".equals(apiDelMissB.getResult().getKeyValue("return"))) {
                                        apiDelNeedB.set(toApi.api_delNeed(Ta)).get();
                                        apiDelNeedB.setCheck(DelNeedA);
                                    } else {
                                        Toast.makeText(main_tree, "Error del need B", Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void missBefore() {
                                    Toast.makeText(main_tree, "NetworkError del need B", Toast.LENGTH_LONG).show();
                                }
                            };
                            final check DelMissA = new check() {
                                @Override
                                public void okBefore() {
                                    /**
                                     * del miss A ok
                                     */
                                    if (!"false".equals(apiDelMissA.getResult().getKeyValue("return"))) {
                                        apiDelMissB.set(toApi.api_delMiss(Ta)).get();
                                        apiDelMissB.setCheck(DelMissB);
                                    } else {
                                        Toast.makeText(main_tree, "Error del miss A", Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void missBefore() {
                                    Toast.makeText(main_tree, "NetworkError del miss A", Toast.LENGTH_LONG).show();
                                }
                            };

                            apiDelMissA.set(toApi.api_delMiss(userName)).get();
                            apiDelMissA.setCheck(DelMissA);
                        }
                    });
                    //拒绝
                    missRefuse.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //拒绝miss请求
                            final toApi apiDelMiss = new toApi();
                            apiDelMiss.set(toApi.api_delMiss(userName)).get();
                            apiDelMiss.setCheck(new check() {
                                @Override
                                public void okBefore() {
                                    if (!"false".equals(apiDelMiss.getResult().getKeyValue("return")))
                                        missView.setVisibility(View.GONE);
                                    else
                                        Toast.makeText(main_tree, "拒绝失败，请稍后重试", Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public void missBefore() {
                                    Toast.makeText(main_tree, "网络连接失败", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    });
                }
            }

            @Override
            public void missBefore() {
                Toast.makeText(main_tree, "获取Miss信息失败", Toast.LENGTH_LONG).show();
            }
        });
    }

    //背景设置
    private void setBg() {
        //背景初始化
        bgRes bg_res = new bgRes();

        if (bgData.getString("bgCode", "").equals("")) {
            mdBgData.putString("bgCode", bgRes.TIME_SUN);
            mdBgData.putString("bgType", "TIME");
            mdBgData.putFloat("bgAlpha", 1);
            mdBgData.commit();
            this.bgCode = bgRes.TIME_SUN;
        } else {
            this.bgCode = bgData.getString("bgCode", "");
        }

        int bg;
        if (bgData.getString("bgType", "TIME").equals("TIME")) {
            bg = bg_res.image_time_id.get(
                    bg_res.image_time_name.indexOf(bgData.getString("bgCode", bgRes.TIME_SUN))
                            * 3 + TimeNow
            );

//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                startForegroundService(new Intent(main_tree.this, autoSetBg.class));
//            } else {
//                startService(new Intent(main_tree.this, autoSetBg.class));
//            }
            try {
                startService(new Intent(main_tree.this, autoSetBg.class));
            } catch (Exception m) {
            }

        } else {
            bg = bg_res.image_ip_id.get(
                    bg_res.image_ip_name.indexOf(bgData.getString("bgCode", bgRes.IP_JZY))
            );
            stopService(new Intent(main_tree.this, autoSetBg.class));
        }

        mainTreeLayoutAlpha.setAlpha(bgData.getFloat("bgAlpha", 1));
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
        startActivity(new Intent(main_tree.this, help.class));
    }

    //背景按钮
    public void onBg(View view) {
        if (bug_isSetBg == false) {
            Intent intent = new Intent(main_tree.this, set_bg.class);
            intent.putExtra("bgCode", this.bgCode);
            intent.putExtra("bgAlpha", bgData.getFloat("bgAlpha", 1));
            startActivityForResult(intent, 20);
            bug_isSetBg = true;
        }
    }

    //activity 点击事件穿透
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (headTs + 200 > System.currentTimeMillis()) {
            headTs = System.currentTimeMillis();
            return true;
        }
        headTs = System.currentTimeMillis();
        //
        List<View> buttons = new ArrayList<View>();
        buttons.add(mainTreeHeadMoreMain);
        buttons.add(mainTreeHeadMoreBg);
        buttons.add(mainTreeHeadMoreHelp);
        buttons.add(mainTreeHeadMoreAbout);
        buttons.add(mainTreeHeadInfoMainShow);
        buttons.add(mainTreeHeadInfoContent);
        //
        int[] xy = new int[2];
        int[] xy_end = new int[2];
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).getLocationOnScreen(xy);
            xy_end[0] = buttons.get(i).getWidth() + xy[0];
            xy_end[1] = buttons.get(i).getHeight() + xy[1];

            if (event.getX() >= xy[0] && event.getX() <= xy_end[0]
                    && event.getY() >= xy[1] && event.getY() <= xy_end[1]) {
//                if (isMoreShow == false && xy[1] >= mainTreeHead.getLayoutParams().height)
//                    return false;
                buttons.get(i).callOnClick();
                return true;
            }
        }

        if (isMoreShow) mainTreeHeadMoreMain.callOnClick();
        if (isInfoShow) mainTreeHeadInfoMainShow.callOnClick();

        //return false;
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //事件流，抬起手时才能判定，不然down传不到其他按钮；
        if (onTouchEvent(ev) && ev.getAction() == MotionEvent.ACTION_DOWN)
            return false;

        //失败，程序运行太快；
//        if (isInfoShow == true || isMoreShow == true)
//            return false;
        return super.dispatchTouchEvent(ev);
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
            this.mdBgData.putString("bgCode", data.getStringExtra("bgCode"));
            this.mdBgData.putString("bgType", data.getStringExtra("bgType"));
            this.mdBgData.putFloat("bgAlpha", data.getFloatExtra("bgAlpha", 1));
        }
        if (data.getStringExtra("bgType").equals("IP")) {
            this.mdBgData.putString("bgCode", data.getStringExtra("bgCode"));
            this.mdBgData.putString("bgType", data.getStringExtra("bgType"));
            this.mdBgData.putFloat("bgAlpha", data.getFloatExtra("bgAlpha", 1));
        }
        this.mdBgData.commit();
        setBg();

        return;
    }

    private void callMine() {
        mine = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 0 || msg.what == 1 || msg.what == 2) {
                    TimeNow = msg.what;
                    setBg();
                    return;
                }
                if (msg.what == 10) {
                    getSeedInfo();
                    return;
                }
                if (msg.what == 20) {
                    mainLogic();
                    return;
                }
            }
        };
    }

}
