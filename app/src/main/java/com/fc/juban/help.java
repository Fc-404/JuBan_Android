package com.fc.juban;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.Nullable;
import com.fc.juban.tools.check;
import com.fc.juban.tools.fileIO;
import com.fc.juban.tools.nativeIni;
import com.fc.juban.tools.toApi;

public class help extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);

        //设置状态栏字体为黑色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            this.getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            );
    }

    public void helpClose(View view) {
        finish();
    }

    public void outLogup(View view) {
        SharedPreferences userData = getSharedPreferences("user", MODE_PRIVATE);
        SharedPreferences.Editor mdUserData = userData.edit();
        mdUserData.putString("user", "");
        mdUserData.putString("pwd", "");
        mdUserData.putString("seed", "");
        mdUserData.commit();
        main_tree.main_tree.finish();
        startActivity(new Intent(this, login_up.class));
        this.finish();
    }

    public void dieLove(View view) {
        SharedPreferences userData = getSharedPreferences("user", MODE_PRIVATE);
        SharedPreferences.Editor mdUserData = userData.edit();

        if (userData.getString("seed", "").equals(""))
            return;

        final toApi delSeed = new toApi();
        delSeed.set(toApi.api_delSeed(userData.getString("user", ""))).get();
        delSeed.setCheck(new check() {
            @Override
            public void okBefore() {
            }

            @Override
            public void missBefore() {
            }
        });
        nativeIni ini = new nativeIni(help.this);
        final toApi delSeedTa = new toApi();
        String ta = userData.getString("user", "") == ini.getIni().getKeyValue("A", "INFO") ?
                ini.getIni().getKeyValue("B", "INFO") : ini.getIni().getKeyValue("A", "INFO");
        delSeedTa.set(toApi.api_delSeed(ta)).get();
        delSeedTa.setCheck(new check() {
            @Override
            public void okBefore() {
            }

            @Override
            public void missBefore() {

            }
        });

        mdUserData.putString("seed", "");
        mdUserData.commit();
        fileIO.deleteToSD("JuBan/ini.ini");
        fileIO.deleteToSD("JuBan/diary.ini");
        Toast.makeText(help.this, "再见", Toast.LENGTH_LONG).show();
        Message msg = new Message();
        msg.what = 20;
        main_tree.mine.sendMessage(msg);
    }
}
