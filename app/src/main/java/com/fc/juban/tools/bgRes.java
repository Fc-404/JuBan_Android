package com.fc.juban.tools;

import com.fc.juban.R;

import java.util.ArrayList;
import java.util.List;

public class bgRes {
    /**
     * 背景代号
     */
    //TIME
    public static String TIME_SUN = "TIME_SUN";
    public static String TIME_SUM = "TIME_SUM";
    //IP
    public static String IP_DC = "多彩";
    public static String IP_JZY = "姜子牙";
    public static String IP_WHCS = "晚上城市";
    public static String IP_YRZX = "一人之下";
    public static String IP_NDMZ1 = "你的名字1";
    public static String IP_NDMZ2 = "你的名字2";
    public static String IP_NDMZ3 = "你的名字3";
    public static String IP_NDMZ4 = "你的名字4";

    public List<String> image_time_name = new ArrayList<String>();
    public List<String> image_ip_name = new ArrayList<String>();
    public List<Integer> image_time_id = new ArrayList<Integer>();
    public List<Integer> image_ip_id = new ArrayList<Integer>();

    public bgRes(){
        /**
         * TIME
         */
        image_time_name.add(TIME_SUN);
        image_time_id.add(R.mipmap.main_bg_time_sun_0);
        image_time_id.add(R.mipmap.main_bg_time_sun_1);
        image_time_id.add(R.mipmap.main_bg_time_sun_2);

        image_time_name.add(TIME_SUM);
        image_time_id.add(R.mipmap.main_bg_time_summer_0);
        image_time_id.add(R.mipmap.main_bg_time_summer_1);
        image_time_id.add(R.mipmap.main_bg_time_summer_2);

        /**
         * IP
         */
        image_ip_name.add(IP_DC);
        image_ip_id.add(R.mipmap.main_bg_ip_dc);
        image_ip_name.add(IP_JZY);
        image_ip_id.add(R.mipmap.main_bg_ip_jzy);
        image_ip_name.add(IP_WHCS);
        image_ip_id.add(R.mipmap.main_bg_ip_whcs);
        image_ip_name.add(IP_YRZX);
        image_ip_id.add(R.mipmap.main_bg_ip_yrzx);
        image_ip_name.add(IP_NDMZ1);
        image_ip_id.add(R.mipmap.main_bg_ip_ndmz_1);
        image_ip_name.add(IP_NDMZ2);
        image_ip_id.add(R.mipmap.main_bg_ip_ndmz_2);
        image_ip_name.add(IP_NDMZ3);
        image_ip_id.add(R.mipmap.main_bg_ip_ndmz_3);
        image_ip_name.add(IP_NDMZ4);
        image_ip_id.add(R.mipmap.main_bg_ip_ndmz_4);
    }
}
