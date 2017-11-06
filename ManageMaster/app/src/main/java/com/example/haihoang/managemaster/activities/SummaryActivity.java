package com.example.haihoang.managemaster.activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.haihoang.managemaster.R;
import com.example.haihoang.managemaster.adapters.ListSalaryAdapter;
import com.example.haihoang.managemaster.databases.DatabaseHandle;
import com.example.haihoang.managemaster.models.EmployeeModel;
import com.example.haihoang.managemaster.models.Group;
import com.example.haihoang.managemaster.utils.FormatNumber;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SummaryActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    ArrayList<String> listNameGroup;
    ArrayList<Group> listGroup;
    String date;
    ExpandableListView elvListSalary;
    public static final String EMPLOYEE = "employee";
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

    @Override
    protected void onResume() {
        super.onResume();
        setupUI();
        setAdapter();
        setOnclickList();
    }

    private void setOnclickList() {

        elvListSalary.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long l) {
                final DatabaseHandle handle = DatabaseHandle.getInstance(SummaryActivity.this);
                final ArrayList<EmployeeModel> listEmployee = handle.getAllEmployeeByGroup(listNameGroup.get(groupPosition));
                Intent intent = new Intent(SummaryActivity.this,EmployeeInfoSalary.class);
                intent.putExtra(EMPLOYEE,listEmployee.get(childPosition));
                startActivity(intent);
                return true;
            }
        });


        elvListSalary.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                long packedPosition = elvListSalary.getExpandableListPosition(position);

                int itemType = ExpandableListView.getPackedPositionType(packedPosition);
                int groupPosition = ExpandableListView.getPackedPositionGroup(packedPosition);
                int childPosition = ExpandableListView.getPackedPositionChild(packedPosition);
                if (itemType == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
                    onChildLongClick(groupPosition, childPosition);
                }
                return true;
            }
        });



    }
    private void onChildLongClick(final int groupPosition, final int childPosition) {
        AlertDialog.Builder builder = new AlertDialog.Builder(SummaryActivity.this);
        builder.setCancelable(true);
        builder.setTitle(listGroup.get(groupPosition).getListEmployee().get(childPosition).getName());
        builder.setNegativeButton("Thưởng", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EmployeeModel model = listGroup.get(groupPosition).getListEmployee().get(childPosition);
                showDialogAddSalary(model);
            }
        });
        builder.setPositiveButton("Ứng/Phạt", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EmployeeModel model = listGroup.get(groupPosition).getListEmployee().get(childPosition);
                showDialogMinusSalary(model);
            }
        });

        builder.setNeutralButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void showDialogAddSalary(final EmployeeModel model) {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date d = new Date();
        date = df.format(d);
        final Dialog dialog = new Dialog(this);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.custom_dialog_salary);
        FloatingActionButton btnDone = dialog.findViewById(R.id.btnDone);
        FloatingActionButton btnClose = dialog.findViewById(R.id.btnClose);
        TextView tvTitle = dialog.findViewById(R.id.tvTitle);
        tvTitle.setText("Thưởng Tiền");
        final EditText edtMoney = dialog.findViewById(R.id.edtMoney);
        final EditText edtNote = dialog.findViewById(R.id.edtNote);
        edtMoney.addTextChangedListener(new NumberTextWatcher(edtMoney));
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHandle handle = DatabaseHandle.getInstance(SummaryActivity.this);
                String sMoney = edtMoney.getText().toString();
                String note = edtNote.getText().toString();

                if(sMoney.equals(""))
                {
                    Toast.makeText(SummaryActivity.this, "Bạn chưa nhập số tiền", Toast.LENGTH_SHORT).show();
                }else if(note.equals(""))
                {
                    Toast.makeText(SummaryActivity.this, "Bạn chưa nhập ghi chú", Toast.LENGTH_SHORT).show();
                } else
                {
                    int money = FormatNumber.getNumber(sMoney);
                    String beforeNote = handle.getNote(model);
                    beforeNote += "- " + date + ":(Thưởng) " + FormatNumber.formatNumber(money) + "đ \n"
                            + note + "\n";
                    handle.addMoneyToTotalSalary(model,money,beforeNote);
                    dialog.dismiss();
                    onResume();
                }
            }
        });
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void showDialogMinusSalary(final EmployeeModel model) {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date d = new Date();
        final String date = df.format(d);
        final Dialog dialog = new Dialog(this);
        dialog.setCancelable(true);

        dialog.setContentView(R.layout.custom_dialog_salary);

        FloatingActionButton btnDone = dialog.findViewById(R.id.btnDone);
        FloatingActionButton btnClose = dialog.findViewById(R.id.btnClose);
        TextView tvTitle = dialog.findViewById(R.id.tvTitle);
        tvTitle.setText("Ứng/Phạt");
        final EditText edtMoney = dialog.findViewById(R.id.edtMoney);
        final EditText edtNote = dialog.findViewById(R.id.edtNote);
        edtMoney.addTextChangedListener(new NumberTextWatcher(edtMoney));
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHandle handle = DatabaseHandle.getInstance(SummaryActivity.this);
                String sMoney = edtMoney.getText().toString();
                String note = edtNote.getText().toString();
                if(sMoney.equals(""))
                {
                    Toast.makeText(SummaryActivity.this, "Bạn chưa nhập số tiền", Toast.LENGTH_SHORT).show();
                }else if(note.equals(""))
                {
                    Toast.makeText(SummaryActivity.this, "Bạn chưa nhập ghi chú", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    int money = FormatNumber.getNumber(sMoney);
                    int totalSalary = handle.getTotalSalaryById(model.getId());
                    if(money < totalSalary) {
                        String beforeNote = handle.getNote(model);

                        beforeNote += "- " + date + ":(Ứng/Phạt) " + FormatNumber.formatNumber(money) + "đ \n"

                                + note + "\r\n";
                        handle.minusMoneyToTotalSalary(model, money, beforeNote);
                        dialog.dismiss();
                        onResume();
                    }else{
                        Toast.makeText(SummaryActivity.this, "Lương tháng hiện tại không đủ để trừ", Toast.LENGTH_LONG ).show();
                        dialog.dismiss();
                    }
                }
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
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
        ListSalaryAdapter adapter = new ListSalaryAdapter(this,R.layout.item_parent_group,R.layout.item_list_salary,listGroup);
        elvListSalary.setAdapter(adapter);

    }





}