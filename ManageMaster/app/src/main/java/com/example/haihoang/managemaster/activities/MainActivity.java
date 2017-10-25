package com.example.haihoang.managemaster.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.haihoang.managemaster.R;
import com.example.haihoang.managemaster.adapters.ListGroupAdapter;
import com.example.haihoang.managemaster.databases.DatabaseHandle;
import com.example.haihoang.managemaster.models.Group;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;



public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    FloatingActionButton btnAddEmployee;
    private TextView tvcurrentTime;
    private ListView lvListGroup;
    Thread myThread = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_home);
        //cài UI
        setupUI();

        btnAddEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddEmployeeActivity.class);
                startActivity(intent);
            }
        });
        setAdapter();

    }

    private void setupUI() {
        lvListGroup = (ListView) findViewById(R.id.lvListGroup);
        btnAddEmployee = (FloatingActionButton) findViewById(R.id.btnAdd);
        tvcurrentTime = (TextView)findViewById(R.id.tvCurrentTime);
        //myThread = new Thread(new TimeRunner());
        //myThread.start();
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
    private void setAdapter()
    {
        DatabaseHandle handle = DatabaseHandle.getInstance(this);
        ArrayList<String> listNameGroup = handle.getAllGroup();
        ArrayList<Group> listGroup=new ArrayList<Group>();
        for(int i=0;i<listNameGroup.size();i++)
        {
            String nameGroup = listNameGroup.get(i).toString();
            listGroup.add(new Group(nameGroup,handle.getCountEmployeeInGroup(nameGroup)));
        }
        ListGroupAdapter adapter = new ListGroupAdapter(this,R.layout.item_list_group,listGroup);
        lvListGroup.setAdapter(adapter);

    }
//    class TimeRunner implements Runnable{
//
//    }
//
//    private void setupUI() {
//
//        @Override
//        public void run() {
//            while(!Thread.currentThread().isInterrupted()){
//                getTime();
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }catch (Exception e){}
//            }
//        }
//
//    }
}
