package com.example.haihoang.managemaster.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.haihoang.managemaster.R;
import com.example.haihoang.managemaster.adapters.ListEmployeeAdapter;
import com.example.haihoang.managemaster.adapters.ListGroupAdapter;
import com.example.haihoang.managemaster.databases.DatabaseHandle;
import com.example.haihoang.managemaster.models.EmployeeModel;

import java.util.ArrayList;

public class ListEmployeeActivity extends AppCompatActivity {
    private ListView lvListEmployee;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_employee);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_list_employee);
        setupUI();
        getDataIntent();
    }

    private void setupUI() {
        lvListEmployee = (ListView) findViewById(R.id.lvListEmployee);
    }

    public void getDataIntent()
    {
        Intent intent = getIntent();
        String nameGroup = intent.getStringExtra(ListGroupAdapter.NAME_GROUP);
        DatabaseHandle handle = DatabaseHandle.getInstance(this);
        ArrayList<EmployeeModel> listEmployee = handle.getAllEmployeeByGroup(nameGroup);
        ListEmployeeAdapter adapter = new ListEmployeeAdapter(this,R.layout.item_list_employee,listEmployee);
        lvListEmployee.setAdapter(adapter);
    }
}
