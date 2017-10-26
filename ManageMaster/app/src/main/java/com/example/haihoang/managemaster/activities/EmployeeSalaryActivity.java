package com.example.haihoang.managemaster.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.haihoang.managemaster.R;
import com.example.haihoang.managemaster.adapters.ListGroupAdapter;
import com.example.haihoang.managemaster.adapters.ListSalaryAdapter;
import com.example.haihoang.managemaster.databases.DatabaseHandle;
import com.example.haihoang.managemaster.models.EmployeeModel;

import java.util.ArrayList;

/**
 * Created by haihoang on 10/25/17.
 */

public class EmployeeSalaryActivity extends AppCompatActivity {
    ArrayList<EmployeeModel> listEmployee;
    ListView lvListSalary;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_summary);

        getData();
        setupUI();
        setAdapter();
    }

    private void setupUI() {
        lvListSalary = (ListView) findViewById(R.id.lvListSalary);
    }

    private void getData() {
        Intent intent = getIntent();
        String nameGroup = intent.getStringExtra(ListGroupAdapter.NAME_GROUP);

        DatabaseHandle handle = DatabaseHandle.getInstance(this);
        listEmployee=handle.getAllEmployeeByGroup(nameGroup);
    }

    private void setAdapter()
    {
        ListSalaryAdapter adapter = new ListSalaryAdapter(this,R.layout.item_list_salary,listEmployee);
        lvListSalary.setAdapter(adapter);

    }

}
