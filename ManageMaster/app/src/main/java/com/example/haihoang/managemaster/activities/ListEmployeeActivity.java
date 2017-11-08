package com.example.haihoang.managemaster.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.haihoang.managemaster.R;
import com.example.haihoang.managemaster.adapters.ListEmployeeAdapter;
import com.example.haihoang.managemaster.adapters.ListGroupAdapter;
import com.example.haihoang.managemaster.databases.DatabaseHandle;
import com.example.haihoang.managemaster.models.EmployeeModel;

import java.util.ArrayList;

public class ListEmployeeActivity extends AppCompatActivity {
    private static final String TAG = "ListEmployeeActivity";
    public static final String EMPLOYEE = "employee";
    private ListView lvListEmployee;
    private String nameGroup;
    private SearchView svEmployee;
    private TextView tvGroupName;
    private ImageView ivSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_employee);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_actionbar_title_listemployee);
        setupUI();
        getDataIntent();
        addListener();
        Log.d(TAG, "onCreate: ");
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

        svEmployee.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("check", "click2");
                tvGroupName.setVisibility(View.GONE);
                ivSave.setVisibility(View.GONE);
            }
        });

        svEmployee.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                Log.e("check", "Close");
                tvGroupName.setVisibility(View.VISIBLE);
                ivSave.setVisibility(View.VISIBLE);
                return false;
            }
        });

        lvListEmployee.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                final DatabaseHandle handle = DatabaseHandle.getInstance(ListEmployeeActivity.this);
                final ArrayList<EmployeeModel> listEmployee = handle.getAllEmployeeByGroup(nameGroup);
                final AlertDialog.Builder builder = new AlertDialog.Builder(ListEmployeeActivity.this);
                builder.setTitle(listEmployee.get(position).getName());
                builder.setNeutralButton("Xoá", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       // handle.deleteEmployee(listEmployee.get(position));
                        checkDelete(listEmployee.get(position),ListEmployeeActivity.this);

                    }
                });
                builder.setPositiveButton("Huỷ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("Cập nhật", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(ListEmployeeActivity.this,UpdateEmployee.class);
                        intent.putExtra("Employee",listEmployee.get(position));
                        startActivity(intent);
                        finish();
                    }
                });
                builder.setCancelable(true);
                builder.show();
                return true;
            }
        });
        lvListEmployee.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                final DatabaseHandle handle = DatabaseHandle.getInstance(ListEmployeeActivity.this);
                final ArrayList<EmployeeModel> listEmployee = handle.getAllEmployeeByGroup(nameGroup);
                Intent intent = new Intent(ListEmployeeActivity.this,EmployeeInforActivity.class);
                intent.putExtra(EMPLOYEE,listEmployee.get(position));
                startActivity(intent);
            }
        });
        ivSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void setupUI() {
        svEmployee= (SearchView) findViewById(R.id.svListEmployee);
        lvListEmployee = (ListView) findViewById(R.id.lvListEmployee);
        tvGroupName = (TextView) findViewById(R.id.txtGroupName);
        ivSave = (ImageView) findViewById(R.id.iv_luu);
    }
    public void checkDelete(final EmployeeModel model, Context context)
    {
        final DatabaseHandle handle = DatabaseHandle.getInstance(context);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Bạn có chắn chắn muốn xóa nhân viên: "+model.getName()+"?");
        builder.setCancelable(true);
        builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                handle.deleteEmployee(model);
                checkListGroupEmpty(model.getGroup());
                onResume();
            }
        });
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
    public void checkListGroupEmpty(String nameGroup)
    {
        DatabaseHandle handle = DatabaseHandle.getInstance(this);
        ArrayList<EmployeeModel> model = handle.getAllEmployeeByGroup(nameGroup);
        if(model.size()==0)
        {
            finish();
        }

    }
    public void getDataIntent()
    {
        Intent intent = getIntent();
        nameGroup = intent.getStringExtra(ListGroupAdapter.NAME_GROUP);
        tvGroupName.setText(nameGroup);
        DatabaseHandle handle = DatabaseHandle.getInstance(this);
        ArrayList<EmployeeModel> listEmployee = handle.getAllEmployeeByGroup(nameGroup);
        final ListEmployeeAdapter adapter = new ListEmployeeAdapter(this,R.layout.item_list_employee,listEmployee);
        lvListEmployee.setAdapter(adapter);

        svEmployee.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
}
