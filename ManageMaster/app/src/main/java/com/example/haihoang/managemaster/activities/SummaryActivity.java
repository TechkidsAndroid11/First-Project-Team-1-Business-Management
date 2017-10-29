package com.example.haihoang.managemaster.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextClock;
import android.widget.Toast;

import com.example.haihoang.managemaster.R;
import com.example.haihoang.managemaster.adapters.ListGroupAdapter;
import com.example.haihoang.managemaster.databases.DatabaseHandle;
import com.example.haihoang.managemaster.models.AlarmService;
import com.example.haihoang.managemaster.models.Group;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class SummaryActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    FloatingActionButton btnAddEmployee;
    private boolean checkClick=true;
    ImageView btnCheckTotal;
    ArrayList<String> listNameGroup;
    private ListView lvListGroup;
    Thread myThread = null;
    private TextClock tcCurrenTime;
    private String myDate;
    private SearchView svGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_summary);

        Log.d(TAG, "onCreate: ");



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
        svGroup=(SearchView) findViewById(R.id.svGroup);
        lvListGroup = (ListView) findViewById(R.id.lvListGroup);
        btnAddEmployee = (FloatingActionButton) findViewById(R.id.btnAdd);
        tcCurrenTime = (TextClock) findViewById(R.id.tcCurrentTime);


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
        final ListGroupAdapter adapter = new ListGroupAdapter(this,R.layout.item_list_group,listGroup);
        lvListGroup.setAdapter(adapter);
        svGroup.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

    }
    private void addListener()
    {

        btnAddEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SummaryActivity.this, AddEmployeeActivity.class);
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
                final DatabaseHandle handle = DatabaseHandle.getInstance(SummaryActivity.this);
                final AlertDialog.Builder builder = new AlertDialog.Builder(SummaryActivity.this);
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
        Intent intent = new Intent(this, EmployeeSalaryActivity.class);
        intent.putExtra(ListGroupAdapter.NAME_GROUP,nameGroup);
        startActivity(intent);
    }




}
