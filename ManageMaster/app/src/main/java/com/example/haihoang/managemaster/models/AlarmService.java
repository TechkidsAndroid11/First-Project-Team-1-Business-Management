package com.example.haihoang.managemaster.models;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.haihoang.managemaster.R;

import java.util.Date;


/**
 * Created by Trần_Tân on 25/10/2017.
 */

public class AlarmService extends Service {
    private static final String TAG = "AlarmService";
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Date date = new Date();
        Log.d(TAG, "onStartCommand: "+date.getDate());
        if(date.getDate() == 1){
            Notification noti = new Notification.Builder(this).setContentTitle("New month!!")
                    .setContentText("Tổng kết lương thôi bạn ơi!!")
                    .setSmallIcon(R.drawable.ic_attach_money_wthite_24dp).build();
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            manager.notify(0,noti);
        }
        return super.onStartCommand(intent, flags, startId);
    }
}
