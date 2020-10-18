package com.fc.juban;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;

import java.net.MalformedURLException;
import java.net.URL;

public class about extends Activity {
    String updateUrl = "http://ja.wwgm.top/juban/download";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);

        //设置状态栏字体为黑色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            this.getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            );
    }

    public void close(View view){
        finish();
    }
    public void openUpdate(View view){
        Intent intent = new Intent();
        intent.setData(Uri.parse(updateUrl));//Url 就是你要打开的网址
        intent.setAction(Intent.ACTION_VIEW);
        this.startActivity(intent); //启动浏览器
    }
}
