package com.fc.juban.server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Message;
import androidx.annotation.Nullable;
import com.fc.juban.main_tree;

import java.text.SimpleDateFormat;
import java.util.Date;

public class autoSetBg extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel channel = new NotificationChannel("lock", "lock", NotificationManager.IMPORTANCE_LOW);
//            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//            if (manager == null)
//                return;
//            manager.createNotificationChannel(channel);
//            //此处的channelId必须和上面NotificationChannel设置的id一致
//            Notification notification = new NotificationCompat.Builder(this, "lock")
//                    .setAutoCancel(true)
//                    .setCategory(Notification.CATEGORY_SERVICE)
//                    .setOngoing(true)
//                    .setPriority(NotificationManager.IMPORTANCE_LOW)
//                    .build();
//            //注意 id不能为0
//            startForeground(107, notification);
//
//        }

        //开启一个线程，对数据进行处理
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Message msg;
                    int hour;
                    while (true) {
                        msg = main_tree.mine.obtainMessage();
                        hour = Integer.parseInt(new SimpleDateFormat("HH").format(
                                new Date(System.currentTimeMillis())));
                        switch ((int) (hour / 8)) {
                            case 2:
                                msg.what = 2;
                                break;
                            case 1:
                                msg.what = 1;
                                break;
                            default:
                                msg.what = 0;
                        }
                        //耗时操作：数据处理并保存，向Activity发送广播
                        main_tree.mine.sendMessage(msg);
                        Thread.sleep(60000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

}
