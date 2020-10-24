package com.fc.juban.tools;

import com.fc.juban.R;

import java.util.ArrayList;
import java.util.List;

public class moodRes {
    final public static String KAIXIN = "开心";
    final public static String CAIMI = "财迷";
    final public static String DAXIAO = "大笑";
    final public static String FOXI = "佛系";
    final public static String GULI = "鼓励";
    final public static String HAIPA = "害怕";
    final public static String HUACHI = "花痴";
    final public static String HUAIHUAI = "坏坏";
    final public static String KAIHUAIDAXIAO = "开怀大笑";
    final public static String KEAI = "可爱";
    final public static String PAOMEIYAN = "抛媚眼";
    final public static String SHANGXIN = "伤心";
    final public static String SHENGQI = "生气";
    final public static String SHIWANG = "失望";
    final public static String TANSHUI = "贪睡";
    final public static String XIANGWEN = "香吻";
    final public static String XINSHANG = "欣赏";
    final public static String XUANYUN = "眩晕";
    final public static String YIBENZHENGJING = "一本正经";
    final public static String ZHENJIN = "震惊";

    List<String> mood = new ArrayList<String>();
    List<Integer> moodR = new ArrayList<Integer>();

    public moodRes(){
        add();
    }
    private void add(){
        mood.add(KAIXIN);
        moodR.add(R.mipmap.kaixin);
        mood.add(CAIMI);
        moodR.add(R.mipmap.caimi);
        mood.add(DAXIAO);
        moodR.add(R.mipmap.daxiao);
        mood.add(FOXI);
        moodR.add(R.mipmap.foxi);
        mood.add(GULI);
        moodR.add(R.mipmap.guli);
        mood.add(HAIPA);
        moodR.add(R.mipmap.haipa);
        mood.add(HUACHI);
        moodR.add(R.mipmap.huachi);
        mood.add(HUAIHUAI);
        moodR.add(R.mipmap.huaihuai);
        mood.add(KAIHUAIDAXIAO);
        moodR.add(R.mipmap.kaihuaidaxiao);
        mood.add(KEAI);
        moodR.add(R.mipmap.keai);
        mood.add(PAOMEIYAN);
        moodR.add(R.mipmap.paomeiyan);
        mood.add(SHANGXIN);
        moodR.add(R.mipmap.shangxin);
        mood.add(SHENGQI);
        moodR.add(R.mipmap.shengqi);
        mood.add(SHIWANG);
        moodR.add(R.mipmap.shiwang);
        mood.add(TANSHUI);
        moodR.add(R.mipmap.tanshui);
        mood.add(XIANGWEN);
        moodR.add(R.mipmap.xiangwen);
        mood.add(XINSHANG);
        moodR.add(R.mipmap.xinshang);
        mood.add(XUANYUN);
        moodR.add(R.mipmap.xuanyun);
        mood.add(YIBENZHENGJING);
        moodR.add(R.mipmap.yibenzhengjing);
        mood.add(ZHENJIN);
        moodR.add(R.mipmap.zhenjing);
    }

    public int get(String name){
        int i = mood.indexOf(name) == -1 ? 0 : mood.indexOf(name);
        return moodR.get(i);
    }

    public int getIndex(int index){
        return moodR.get(index);
    }

    public String getName(int index){
        return mood.get(index);
    }

    public String R2name(int res){
        int index = moodR.indexOf(res) == -1 ? 0 : moodR.indexOf(res);
        return mood.get(index);
    }

    public int size(){
        return mood.size();
    }
}
