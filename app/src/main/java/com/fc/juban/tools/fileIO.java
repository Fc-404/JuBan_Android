package com.fc.juban.tools;

import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class fileIO {
    //判断是否有权限
    public static boolean check(){
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
            return true;
        return false;
    }

    public static boolean savaToSD(String filename, String fileContent){
        return savaToSD(filename, fileContent, false);
    }
    public static boolean savaToSD(String filename, String fileContent, boolean mode) {
        //如果手机已插入sd卡,且app具有读写sd卡的权限
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            try {
                filename = Environment.getExternalStorageDirectory().getCanonicalPath() + "/" + filename;
                //这里就不要用openFileOutput了,那个是往手机内存中写数据的
                FileOutputStream output = new FileOutputStream(filename, mode);
                output.write(fileContent.getBytes());
                //将String字符串以字节流的形式写入到输出流中
                output.close();
                //关闭输出流
                return true;
            } catch (Exception m) {
                m.printStackTrace();
                return false;
            }
        }
        return false;
    }

    //读取SD卡中文件的方法
    //定义读取文件的方法:
    public static String readFromSD(String filename) {
        StringBuilder sb = new StringBuilder("");
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            try {
                if (!createToSD(filename))
                    return "";
                filename = Environment.getExternalStorageDirectory().getCanonicalPath() + "/" + filename;
                //打开文件输入流
                FileInputStream input = new FileInputStream(filename);
                byte[] temp = new byte[1024];

                int len = 0;
                //读取文件内容:
                while ((len = input.read(temp)) > 0) {
                    sb.append(new String(temp, 0, len));
                }
                //关闭输入流
                input.close();
                return sb.toString();
            } catch (Exception m){
                m.printStackTrace();
                return "";
            }
        }
        return "";
    }

    //创建文件
    public static boolean createToSD(String filename){
        String rootDir;
        try {
             rootDir = Environment.getExternalStorageDirectory().getCanonicalPath();
        } catch (Exception m){
            m.printStackTrace();
            return false;
        }

        int dirIndex = 0;
        dirIndex = filename.indexOf("/", -1);
        File file = new File(rootDir + "/" + filename.substring(0, dirIndex));
        if (!file.exists())
            if (!file.mkdirs())
                return false;

        file = new File(rootDir + "/" + filename);
        if (!file.exists())
            try {
                file.createNewFile();
            } catch (Exception m){
                m.printStackTrace();
                return false;
            }
        return true;
    }

    public static boolean deleteToSD(String filename){
        String rootDir;
        try {
            rootDir = Environment.getExternalStorageDirectory().getCanonicalPath();
        } catch (Exception m){
            m.printStackTrace();
            return false;
        }

        int dirIndex = 0;
        File file = new File(rootDir + "/" + filename);
        try{
            file.delete();
            return true;
        } catch (Exception m){
            m.printStackTrace();
            return false;
        }
    }

}
