package com.fc.juban.tools;

import android.content.Context;
import android.os.Build;
import android.widget.Toast;
import androidx.annotation.RequiresApi;

public class nativeIni {
    Context context;
    String iniPath, logPath;
    twoArray iniPutIn;
    twoArray logPutIn;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public nativeIni(Context context){
        this.context = context;

        iniPath = "JuBan/ini.ini";
        logPath = "JuBan/diary.ini";

        if (!fileIO.check()) {
            Toast.makeText(context, "读写配置文件失败，请检查软件是否有文件读写权限。", Toast.LENGTH_LONG).show();
            return;
        }
        String iniStr = fileIO.readFromSD(iniPath);
        String logStr = fileIO.readFromSD(logPath);
        iniPutIn = new string2ini(iniStr).getResult();
        logPutIn = new string2ini(logStr).getResult();
    }

    public twoArray getIni(){
        return iniPutIn;
    }
    public twoArray getDiary(){
        return logPutIn;
    }

    public boolean setIni(String key, String value, String group){
        iniPutIn.setKeyValue(key, value, group);

        String iniOutStr = "";
        for (int i = 0; i < iniPutIn.groupLength(); i++){
            iniOutStr += "[" + iniPutIn.getGroupList().get(i) + "]\n";
            for (int j = 0; j < iniPutIn.rootLength(iniPutIn.getGroupList().get(i)); j++)
                iniOutStr += iniPutIn.getRootList(iniPutIn.getGroupList().get(i)).get(j) + "=" +
                        iniPutIn.getKeyValue(
                                iniPutIn.getRootList(iniPutIn.getGroupList().get(i)).get(j),
                                iniPutIn.getGroupList().get(i)) + "\n";
        }
        fileIO.savaToSD(iniPath, iniOutStr);
        return true;
    }
    public boolean setDiary(String key, String value){
        if (fileIO.savaToSD(logPath, key + "=" + value + "\n", true))
            return true;
        return false;
    }
}
