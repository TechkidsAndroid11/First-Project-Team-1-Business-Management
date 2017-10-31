package com.example.haihoang.managemaster.activities;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.example.haihoang.managemaster.R;
import com.example.haihoang.managemaster.adapters.ListGroupAdapter;
import com.example.haihoang.managemaster.databases.DatabaseHandle;
import com.example.haihoang.managemaster.models.AlarmService;
import com.example.haihoang.managemaster.models.Group;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;



public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private FloatingActionButton btnAddEmployee;
    private boolean checkClick=true;
    private ImageView btnCheckTotal;
    ArrayList<String> listNameGroup;
    private ListView lvListGroup;
    Thread myThread = null;
    private TextClock tcCurrenTime;
    private String myDate;
    private SearchView svGroup;
    private TextView tvTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_home);
        Log.d(TAG, "onCreate: ");
        createNotification();
    }
    @Override
    protected void onStart() {
        super.onStart();
        //cài UI
        setupUI();
        setAdapter();
        addListener();
        checkDate();
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    private void setupUI() {
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        svGroup = (SearchView) findViewById(R.id.svGroup);
        btnCheckTotal = (ImageView) findViewById(R.id.btnCheckTotal);
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
        svGroup.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("check", "click2");
                tvTitle.setVisibility(View.GONE);
                btnCheckTotal.setVisibility(View.GONE);
            }
        });

        svGroup.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                Log.e("check", "Close");
                tvTitle.setVisibility(View.VISIBLE);
                btnCheckTotal.setVisibility(View.VISIBLE);

                return false;
            }
        });


        btnCheckTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SummaryActivity.class);
                startActivity(intent);
            }
        });
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
                        showDialogUpdate(listNameGroup.get(position));
                    }
                });
                builder.setCancelable(true);
                builder.show();
                return true; // ko cho phép sự kiện nào hoạt động song song cùng con này
            }
        });
    }

    private void showDialogUpdate(final String oldName) {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setTitle("Cập nhật thông tin group: ");
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_dialog_update_group);
        final EditText edtUpdateGroupName = (EditText) dialog.findViewById(R.id.edtUpdateGroup);
        FloatingActionButton btnDone = (FloatingActionButton) dialog.findViewById(R.id.btnDone);
        FloatingActionButton btnClose = (FloatingActionButton) dialog.findViewById(R.id.btnClose);

        edtUpdateGroupName.setText(oldName);
        edtUpdateGroupName.requestFocus();
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHandle handle = DatabaseHandle.getInstance(MainActivity.this);
                handle.updateGroup(oldName,edtUpdateGroupName.getText().toString());
                MainActivity.this.onStart();
                dialog.cancel();
            }
        });
        dialog.show();
    }

    public void check(String nameGroup)
    {
        Intent intent = new Intent(this, ListEmployeeActivity.class);
        intent.putExtra(ListGroupAdapter.NAME_GROUP,nameGroup);
        startActivity(intent);
    }
    private void createNotification() {
        Log.d(TAG, "createNotification: ");

        AlarmManager alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmService.class);
        PendingIntent pendingIntent = PendingIntent.getService(this,0,intent,0);

        alarmMgr.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(),1000,pendingIntent);

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

                Toast.makeText(this,"Sang ngày mới!! Reset Absent.",Toast.LENGTH_LONG).show();
            }
        }
    }
    public int getDay(String s){
        String []aday = s.split("/");
        return Integer.parseInt(aday[0]);
    }


}
