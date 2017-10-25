package com.example.haihoang.managemaster.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.haihoang.managemaster.R;
import com.example.haihoang.managemaster.adapters.ListEmployeeAdapter;
import com.example.haihoang.managemaster.adapters.ListGroupAdapter;
import com.example.haihoang.managemaster.databases.DatabaseHandle;
import com.example.haihoang.managemaster.models.EmployeeModel;

import java.util.ArrayList;

public class ListEmployeeActivity extends AppCompatActivity {
    private ListView lvListEmployee;
    private String nameGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_employee);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_list_employee);
        setupUI();
        getDataIntent();
        addListener();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        getDataIntent();
        addListener();
        super.onResume();
    }

    private void addListener() {
        lvListEmployee.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                final DatabaseHandle handle = DatabaseHandle.getInstance(ListEmployeeActivity.this);
                final ArrayList<EmployeeModel> listEmployee = handle.getAllEmployeeByGroup(nameGroup);
                final AlertDialog.Builder builder = new AlertDialog.Builder(ListEmployeeActivity.this);
                builder.setTitle(listEmployee.get(position).getName());
                builder.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        handle.deleteEmployee(listEmployee.get(position));
                        onResume();
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
                return false;
            }
        });
    }

    private void setupUI() {
        lvListEmployee = (ListView) findViewById(R.id.lvListEmployee);
    }

    public void getDataIntent()
    {
        Intent intent = getIntent();
        nameGroup = intent.getStringExtra(ListGroupAdapter.NAME_GROUP);
        DatabaseHandle handle = DatabaseHandle.getInstance(this);
        ArrayList<EmployeeModel> listEmployee = handle.getAllEmployeeByGroup(nameGroup);
        ListEmployeeAdapter adapter = new ListEmployeeAdapter(this,R.layout.item_list_employee,listEmployee);
        lvListEmployee.setAdapter(adapter);
    }
}
