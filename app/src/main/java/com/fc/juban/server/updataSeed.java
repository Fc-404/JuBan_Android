package com.fc.juban.server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Message;
import androidx.annotation.Nullable;
import com.fc.juban.main_tree;

public class updataSeed extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(9999);
                        send();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void send(){
        Message msg = new Message();
        msg.what = 10;
        main_tree.mine.sendMessage(msg);
    }
}
