package com.example.haihoang.managemaster.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.haihoang.managemaster.R;
import com.example.haihoang.managemaster.adapters.ListGroupAdapter;
import com.example.haihoang.managemaster.databases.DatabaseHandle;
import com.example.haihoang.managemaster.models.EmployeeModel;
import com.example.haihoang.managemaster.models.Group;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;



public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    FloatingActionButton btnAddEmployee;
    private boolean checkClick=true;
    private TextView tvcurrentTime;
    ArrayList<String> listNameGroup;
    private ListView lvListGroup;
    Thread myThread = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_home);

//        setAdapter();
//        addListener();

    }
    @Override
    protected void onStart() {
        super.onStart();
        //cài UI
        setupUI();
        setAdapter();
        addListener();
    }


    @Override
    protected void onResume() {
        super.onResume();

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
        listNameGroup = handle.getAllGroup();
        ArrayList<Group> listGroup=new ArrayList<Group>();
        for(int i=0;i<listNameGroup.size();i++)
        {
            String nameGroup = listNameGroup.get(i).toString();
            listGroup.add(new Group(nameGroup,handle.getCountEmployeeInGroup(nameGroup)));
        }
        ListGroupAdapter adapter = new ListGroupAdapter(this,R.layout.item_list_group,listGroup);
        lvListGroup.setAdapter(adapter);


    }
    private void addListener()
    {
        btnAddEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddEmployeeActivity.class);
                startActivity(intent);
            }
        });

            lvListGroup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Log.e("check", "2");
                        check(listNameGroup.get(position));

                }
            });


        lvListGroup.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final DatabaseHandle handle = DatabaseHandle.getInstance(MainActivity.this);
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(listNameGroup.get(position));
                builder.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        handle.deleteGroup(listNameGroup.get(position));
                        onStart();
                    }
                });
                builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
                return true; // ko cho phép sự kiện nào hoạt động song song cùng con này
            }
        });
    }

    public void check(String nameGroup)
    {
        Intent intent = new Intent(this, ListEmployeeActivity.class);
        intent.putExtra(ListGroupAdapter.NAME_GROUP,nameGroup);
        startActivity(intent);
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
