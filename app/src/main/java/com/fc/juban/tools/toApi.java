package com.fc.juban.tools;

import android.os.Message;
import android.os.Handler;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class toApi {
    //软件所需API定义
    final static String prefix = "http://ja.wwgm.top/juban/api/";
    final static String suffix = ".php";
    final public static String addSeedInfo = prefix + "addSeedInfo" + suffix;
    final public static String createSeedInfo = prefix + "createSeedlnfo" + suffix;
    final public static String delMiss = prefix + "delMiss" + suffix;
    final public static String delNeed = prefix + "delNeed" + suffix;
    final public static String delSeed = prefix + "delSeed" + suffix;
    final public static String findMiss = prefix + "findMiss" + suffix;
    final public static String findNeed = prefix + "findNeed" + suffix;
    final public static String findSeed = prefix + "findSeed" + suffix;
    final public static String getSeedInfo = prefix + "getSeedlnfo" + suffix;
    final public static String login = prefix + "login" + suffix;
    final public static String logup = prefix + "logup" + suffix;
    final public static String mdMiss = prefix + "mdMiss" + suffix;
    final public static String mdNeed = prefix + "mdNeed" + suffix;
    final public static String mdSeed = prefix + "mdSeed" + suffix;
    final public static String mdSeedInfo = prefix + "mdSeedlnfo" + suffix;
    final public static String lowerWorld = prefix + "lowerWorld.html";

    //私钥
    private static String keyStr = "fc";

    private int resultCode = -1;
    private URL apiUrl;
    private check m = null;
    private BufferedReader resultTemp;
    private StringBuffer resultTemp_Temp = new StringBuffer("");
    private String result;

    Message mineMes = Message.obtain();
    Handler mine;

    //构造函数
    public toApi(){
        this.apiUrl = null;
    }
    public toApi(String apiUrl) {
        try {
            this.apiUrl = new URL(apiUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            this.apiUrl = null;
        }
    }
    //设置api
    public toApi set(String str){
        try {
            this.apiUrl = new URL(str);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            this.apiUrl = null;
        }
        return this;
    }

    //获取
    public toApi get() {
        if (apiUrl == null) {
            missBefore();
            return this;
        }

        mine = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 1 && m != null)
                    okBefore();
                else if (msg.what == 0)
                    missBefore();
                this.removeCallbacksAndMessages(null);
            }
        };

        /**
         * 创建Http对象
         * 设置请求方式
         * 连接
         * 获取响应码
         */
        new Thread() {
            @Override
            public void run() {
                super.run();
                HttpURLConnection apiObj = null;
                mineMes = mine.obtainMessage();
                mineMes.what = 0;
                try {
                    apiObj = (HttpURLConnection) apiUrl.openConnection();
                    apiObj.setRequestMethod("GET");
                    apiObj.setRequestProperty("Charsert", "utf-8");
                    apiObj.setConnectTimeout(9000);
                    apiObj.setReadTimeout(9000);
                    apiObj.connect();
                    resultCode = apiObj.getResponseCode();
                } catch (IOException e) {
                    e.printStackTrace();
                    mine.sendMessage(mineMes);
                    return;
                }

                //连接成功后
                if (resultCode == HttpURLConnection.HTTP_OK) {
                    //获取结果集
                    try {
                        resultTemp = new BufferedReader(new InputStreamReader(apiObj.getInputStream(), "UTF-8"));
                        String inputLine = null;
                        while ((inputLine = resultTemp.readLine()) != null)
                            resultTemp_Temp.append(inputLine);
                    } catch (IOException e) {
                        e.printStackTrace();
                        mine.sendMessage(mineMes);
                        return;
                    }
                    result = resultTemp_Temp.toString();
                }
                mineMes.what = 1;
                mine.sendMessage(mineMes);
            }
        }.start();
        return this;
    }

    //获取结果
    public twoArray getResult() {
        twoArray m = new xml2ini(result).getResult();
        return m;
    }
    public twoArrayR getResultR() {
        twoArrayR m = new xml2iniR(result).getResult();
        return m;
    }

    //成功后执行
    private void okBefore() {
        m.okBefore();
        mine.removeCallbacksAndMessages(null);
    }
    //失败后执行
    private void missBefore() {
        m.missBefore();
        mine.removeCallbacksAndMessages(null);
    }
    //设置成功和失败后执行代码
    public void setCheck(check event) {
        this.m = event;
    }

    //获取设置时变秘钥
    public void setKey(String key){
        keyStr = key;
    }
    public static String getKey(){
        return getMD5.get(keyStr + new SimpleDateFormat("mm").format(new Date()));
    }

    //统一api接口
    //addSeedInfo
    public static String api_addSeedInfo(String seedId, String id, String type, String value){
        String str = toApi.addSeedInfo +
                "?seedId=" + seedId +
                "&id=" + id +
                "&type=" + type +
                "&value=" + value +
                "&key=" + toApi.getKey();
        return str;
    }
    //createSeedInfo
    public static String api_createSeedInfo(String seedId, String A, String B) {
        String str = toApi.createSeedInfo +
                "?seedId=" + seedId +
                "&A=" + A +
                "&B=" + B +
                "&key=" + toApi.getKey();
        return str;
    }
    //delMiss
    public static String api_delMiss(String id){
        String str = toApi.delMiss +
                "?id=" + id +
                "&key=" + toApi.getKey();
        return str;
    }
    //delNeed
    public static String api_delNeed(String id) {
        String str = toApi.delNeed +
                "?id=" + id +
                "&key=" + toApi.getKey();
        return str;
    }
    //delSeed
    public static String api_delSeed(String id) {
        String str = toApi.delSeed +
                "?id=" + id +
                "&key=" + toApi.getKey();
        return str;
    }
    //findMiss
    public static String api_findMiss(String id) {
        String str = toApi.findMiss +
                "?id=" + id +
                "&key=" + toApi.getKey();
        return str;
    }
    //findNeed
    public static String api_findNeed(String id) {
        String str = toApi.findNeed +
                "?id=" + id +
                "&key=" + toApi.getKey();
        return str;
    }
    //findSeed
    public static String api_findSeed(String id) {
        String str = toApi.findSeed +
                "?id=" + id +
                "&key=" + toApi.getKey();
        return str;
    }
    //getSeedInfo
    public static String api_getSeedInfo(String seedId) {
        String str = toApi.getSeedInfo +
                "?seedId=" + seedId +
                "&key=" + toApi.getKey();
        return str;
    }
    //login
    public static String api_Login(String id, String pw){
        String str = toApi.login +
                "?id=" + id +
                "&pw=" + getMD5.get(pw) +
                "&key=" + toApi.getKey();
        return str;
    }
    //logup
    public static String api_logup(String id, String pw){
        String str = toApi.logup +
                "?id=" + id +
                "&pw=" + getMD5.get(pw) +
                "&key=" + toApi.getKey();
        return str;
    }
    //mdMiss
    public static String api_mdMiss(String id, String myId){
        String str = toApi.mdMiss +
                "?id=" + id +
                "&myId=" + myId +
                "&key=" + toApi.getKey();
        return str;
    }
    //mdNeed
    public static String api_mdNeed(String id, String sign){
        String str = toApi.mdNeed +
                "?id=" + id +
                "&sign=" + sign +
                "&key=" + toApi.getKey();
        return str;
    }
    //mdSeed
    public static String api_mdSeed(String id, String seedId){
        String str = toApi.mdSeed +
                "?id=" + id +
                "&seedId=" + seedId +
                "&key=" + toApi.getKey();
        return str;
    }
    //mdSeedInfo
    public static String api_mdSeedInfo(String seedId,String id, String type, String value) {
        String str = toApi.mdSeedInfo +
                "?seedId=" + seedId +
                "&id=" + id +
                "&type=" + type +
                "&value=" + value +
                "&key=" + toApi.getKey();
        return str;
    }
}
