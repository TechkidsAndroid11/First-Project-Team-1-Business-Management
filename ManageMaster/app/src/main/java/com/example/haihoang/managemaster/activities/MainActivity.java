package com.example.haihoang.managemaster.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.haihoang.managemaster.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private TextView tvcurrentTime;
    Thread myThread = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_home);
        //cài UI
        setupUI();
    }

    private void setupUI() {
        tvcurrentTime = (TextView)findViewById(R.id.tvCurrentTime);
        myThread = new Thread(new TimeRunner());
        myThread.start();
    }

    private void getTime(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Date dt = new Date();
                DateFormat sb = new SimpleDateFormat("dd/MM/yyyy");
                String curTime = "Today: "+ sb.format(dt);
                tvcurrentTime.setText(curTime);
            }
        });
    }

    class TimeRunner implements Runnable{

        @Override
        public void run() {
            while(!Thread.currentThread().isInterrupted()){
                getTime();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }catch (Exception e){}
            }
        }
    }
}
