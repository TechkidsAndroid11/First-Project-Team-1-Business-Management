package com.example.haihoang.managemaster.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.example.haihoang.managemaster.R;
import com.example.haihoang.managemaster.adapters.ListSalaryAdapter;
import com.example.haihoang.managemaster.databases.DatabaseHandle;
import com.example.haihoang.managemaster.models.Group;

import java.util.ArrayList;

public class SummaryActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    ArrayList<String> listNameGroup;
    ArrayList<Group> listGroup;
    ExpandableListView elvListSalary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_summary);
        setupUI();
        setAdapter();
        setOnclickList();

    }

    private void setOnclickList() {

        elvListSalary.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                long packedPosition = elvListSalary.getExpandableListPosition(position);
                if (ExpandableListView.getPackedPositionType(packedPosition) ==
                        ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
                    Toast.makeText(SummaryActivity.this, "Long click ", Toast.LENGTH_LONG);
                    // get item ID's
                    int groupPosition = ExpandableListView.getPackedPositionGroup(packedPosition);
                    int childPosition = ExpandableListView.getPackedPositionChild(packedPosition);


                    return true;
                }
                return false;
            }
        });

    }

    private void setupUI() {
        elvListSalary = (ExpandableListView) findViewById(R.id.elv_listSalary);
    }

    public void setAdapter()
    {
        DatabaseHandle handle = DatabaseHandle.getInstance(this);
        listNameGroup = handle.getAllGroup();
        listGroup = new ArrayList<>();

        for(int i=0;i<listNameGroup.size();i++)
        {
            listGroup.add(new Group(listNameGroup.get(i),handle.getAllEmployeeByGroup(listNameGroup.get(i))));
        }
        ListSalaryAdapter adapter = new ListSalaryAdapter(this,R.layout.item_group_salary,R.layout.item_list_salary,listGroup);
        elvListSalary.setAdapter(adapter);

    }





}
