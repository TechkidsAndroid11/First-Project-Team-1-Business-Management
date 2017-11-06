package com.example.haihoang.managemaster.models;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.example.haihoang.managemaster.R;
import com.example.haihoang.managemaster.activities.SummaryActivity;
import com.example.haihoang.managemaster.databases.DatabaseHandle;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * Created by Trần_Tân on 25/10/2017.
 */

public class AlarmService extends Service {
    private static final String TAG = "AlarmService";
    Date date;
    String tmp;
    String myDate;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        SharedPreferences sharedPreferences = getSharedPreferences("my_month",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String myMonth = sharedPreferences.getString("month","");
        String currtime = new SimpleDateFormat("MM").format(new Date());
        if(myMonth.equals("")){
            editor.putString("month",currtime);
            myMonth = currtime;
            editor.commit();
        }
        Log.d(TAG, "onStartCommand: "+myMonth);
        if(checkLastDayOfMonth()){
            Intent intent1 = new Intent(this, SummaryActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this,(int)System.currentTimeMillis(),intent1,0);

            Notification noti = new Notification.Builder(this).setContentTitle("Cuối tháng rồi!!")
                    .setContentText("Hãy tổng kết lương!!")
                    .setSmallIcon(R.drawable.ic_attach_money_wthite_24dp)
                    .setContentIntent(pendingIntent)
                    .build();
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            noti.flags = Notification.FLAG_AUTO_CANCEL;
            manager.notify(0,noti);
        }
        if (checkFistDayOfMonth()){
            Log.d(TAG, "onStartCommand: reset");
            DatabaseHandle handle = DatabaseHandle.getInstance(this);
            ArrayList<EmployeeModel> model = handle.getAllEmployee();
            for(int i=0;i<model.size();i++)
            {
                handle.updatePreviousSalaryAndTotalSalary(model.get(i));
            }

        }
        checkDate();
        return super.onStartCommand(intent, flags, startId);
    }
    public boolean checkLastDayOfMonth(){
        date = new Date();
        tmp = new SimpleDateFormat("dd/MM/yyyy").format(date);
        Log.d(TAG, "checkLastDayOfMonth: "+tmp+" "+date.getHours()+":"+date.getMinutes()+":"+date.getSeconds());
        String[] ntn=tmp.split("/");
        int ngay = Integer.parseInt(ntn[0]);
        int thang = Integer.parseInt(ntn[1]);
        int nam = Integer.parseInt(ntn[2]);
        if(date.getHours()!=14||date.getMinutes()!=0){
            return false;
        }
        if(thang==1||thang==3||thang==5||thang==7||thang==8
                ||thang==10||thang==12){
            if(ngay==31) return true;
        }
        if(thang==4||thang==6||thang==9||thang==11){
            if(ngay==30) return true;
        }
        if(thang==2){
            if(checkNamNhuan(nam)){
                if(ngay==29) return true;
            }
            else if(ngay==28) return true;
        }
        return false;
    }
    public boolean checkNamNhuan(int year){
        if (((year % 4 == 0) && (year % 100!= 0)) || (year%400 == 0)) return true;
        return false;
    }
    public boolean checkFistDayOfMonth(){
        date = new Date();
        tmp = new SimpleDateFormat("dd/MM/yyyy").format(date);
        Log.d(TAG, "checkLastDayOfMonth: "+tmp+" "+date.getHours()+":"+date.getMinutes()+":"+date.getSeconds());
        String[] ntn=tmp.split("/");
        int ngay = Integer.parseInt(ntn[0]);
        int thang = Integer.parseInt(ntn[1]);
        int nam = Integer.parseInt(ntn[2]);
        if(date.getHours()!=23||date.getMinutes()!=59){
            return false;
        }
        if(thang==1||thang==3||thang==5||thang==7||thang==8
                ||thang==10||thang==12){
            if(ngay==31) return true;
        }
        if(thang==4||thang==6||thang==9||thang==11){
            if(ngay==30) return true;
        }
        if(thang==2){
            if(checkNamNhuan(nam)){
                if(ngay==29) return true;
            }
            else if(ngay==28) return true;
        }
        return false;
    }

    private void checkDate() {
        SharedPreferences sharedPreferences = getSharedPreferences("my_date",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        myDate = sharedPreferences.getString("date","");
        String currtime = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        if(myDate.equals("")){
            editor.putString("date",currtime);
            myDate = currtime;
            editor.commit();
        }else{
            if(getDay(myDate)!= getDay(currtime)){
                editor.clear();
                editor.putString("date",currtime);
                editor.commit();
                //Reset Absent
                DatabaseHandle handle = DatabaseHandle.getInstance(this);
                handle.resetStatusAllAbsent();
                Toast.makeText(this,"Manage Master - Sang ngày mới!! Reset Vắng mặt.",Toast.LENGTH_LONG).show();
            }
        }
    }
    public int getDay(String s){
        String []aday = s.split("/");
        return Integer.parseInt(aday[0]);
    }

}
