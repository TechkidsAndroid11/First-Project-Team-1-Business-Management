package com.example.haihoang.managemaster.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.haihoang.managemaster.R;
import com.example.haihoang.managemaster.adapters.ListGroupAdapter;
import com.example.haihoang.managemaster.databases.DatabaseHandle;
import com.example.haihoang.managemaster.models.EmployeeModel;
import com.example.haihoang.managemaster.utils.CircleTransform;
import com.example.haihoang.managemaster.utils.FormatNumber;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class UpdateEmployee extends AppCompatActivity {
    EmployeeModel model;
    private ImageView imgAvatar, ivPickDate , ivPickDate2;
    private EditText edtName, edtPhone, edtHomeTown, edtExp, edtSalary;
    private AutoCompleteTextView actvGroup;
    private RadioGroup radioGender;
    private FloatingActionButton btnDone;
    private String[] base64;
    int gender = 1;
    Calendar cal;
    Date date;
    private TextView tvTitle,edtDOB, edtFirstDayWork;
    CircleTransform circleTransform = new CircleTransform();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_update_employee);
        getSupportActionBar().setElevation(0);
        setupUI();
        Intent intent = getIntent();
         model = (EmployeeModel) intent.getSerializableExtra("Employee");
        //tvTitle.setText("Cập nhật thông tin");
        edtName.setText(model.getName());
        edtDOB.setText(model.getDate());
        edtHomeTown.setText(model.getAddress());
        edtPhone.setText(model.getPhone());
        edtExp.setText(model.getExperience());
        edtSalary.setText(model.getDaySalary()+"");
        actvGroup.setText(model.getGroup());
        edtFirstDayWork.setText(model.getFirstDayWork());


        radioGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i){
                    case R.id.radio_nam:
                        gender = 1;
                        break;
                    case R.id.radio_nu:
                        gender = 0;
                        break;
                }
            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    getInfor();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        base64 = model.getAvatar().split(",");
        Bitmap bitmap = BitmapFactory.decodeByteArray(
                Base64.decode(base64[0],Base64.DEFAULT),
                0,// offset: vị trí bđ
                (Base64.decode(base64[0],Base64.DEFAULT)).length

        );
        imgAvatar.setPadding(0,0,0,0);
        imgAvatar.setImageBitmap(circleTransform.transform(bitmap));

        ivPickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickDateDialog();
            }
        });

        ivPickDate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickDateDialog2();
            }
        });

        edtSalary.addTextChangedListener(new NumberTextWatcher(edtSalary));

    }

    private void pickDateDialog() {
        DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                edtDOB.setText(day + "/" + (month +1) + "/" + year);
            }
        };
        String s=edtDOB.getText()+"";
        String strArrtmp[]=s.split("/");
        int ngay=Integer.parseInt(strArrtmp[0]);
        int thang=Integer.parseInt(strArrtmp[1]) - 1;
        int nam=Integer.parseInt(strArrtmp[2]);
        DatePickerDialog pic=new DatePickerDialog(
                UpdateEmployee.this,
                callback, nam, thang, ngay);
        pic.setTitle("Chọn ngày sinh");
        pic.show();
    }

    private void pickDateDialog2() {
        DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                edtFirstDayWork.setText(day + "/" + (month +1) + "/" + year);
            }
        };
        String s=edtFirstDayWork.getText()+"";
        String strArrtmp[]=s.split("/");
        int ngay=Integer.parseInt(strArrtmp[0]);
        int thang=Integer.parseInt(strArrtmp[1]) - 1;
        int nam=Integer.parseInt(strArrtmp[2]);
        DatePickerDialog pic=new DatePickerDialog(
                UpdateEmployee.this,
                callback, nam, thang, ngay);
        pic.setTitle("Chọn ngày sinh");
        pic.show();
    }

    private void setupUI() {
        ivPickDate2 = (ImageView) findViewById(R.id.ivPickDate2);
        edtFirstDayWork = (TextView) findViewById(R.id.edtFirstDayWork);
        ivPickDate = (ImageView) findViewById(R.id.ivPickDate);
        radioGender = (RadioGroup) findViewById(R.id.radio_gender);
        btnDone = (FloatingActionButton) findViewById(R.id.btnDone);
        imgAvatar = (ImageView) findViewById(R.id.ivAddImage);
        edtName = (EditText) findViewById(R.id.edtName);
        edtDOB = (TextView) findViewById(R.id.edtDOB);
        edtPhone = (EditText) findViewById(R.id.edtPhone);
        edtHomeTown = (EditText) findViewById(R.id.edtHomeTown);
        edtExp = (EditText) findViewById(R.id.edtExp);
        edtSalary = (EditText) findViewById(R.id.edtSalary);
        actvGroup= (AutoCompleteTextView) findViewById(R.id.actvGroup);
    }
    private void setAdapterForAutoCompleteTextView()
    {
        DatabaseHandle handle = DatabaseHandle.getInstance(this);
        ArrayList<String> listNameGroup = handle.getAllGroup();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.select_dialog_item,listNameGroup);
        actvGroup.setThreshold(1);

        actvGroup.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event){
                actvGroup.showDropDown();
                return false;
            }
        });
        actvGroup.setAdapter(adapter);
    }
    private void clearEditText(){
        edtPhone.setText("");
        actvGroup.setText("");
        edtSalary.setText("");
        edtExp.setText("");
        edtDOB.setText("");
        edtHomeTown.setText("");
        edtName.setText("");
        imgAvatar.setBackgroundResource(R.color.colorAccent);
    }


    public void getInfor() throws ParseException {
        boolean isSuccess = false;
        String avatar= model.getAvatar() ;
        String name = edtName.getText().toString().trim();
        if(name.equals("")){
            Toast.makeText(UpdateEmployee.this, "Hãy điền tên!",Toast.LENGTH_SHORT).show();
            return;
        }
        //int id = Integer.parseInt(edtId.getText().toString().trim());

        String birthday = edtDOB.getText().toString().trim();
        if(birthday.equals("")){
            Toast.makeText(UpdateEmployee.this, "Hãy điền ngày sinh!",Toast.LENGTH_SHORT).show();
            Log.e("check: ", "adasdas");
            return;
        }
        String phone = edtPhone.getText().toString().trim();
        if(phone.equals("")){
            Toast.makeText(UpdateEmployee.this, "Hãy điền số điện thoại!",Toast.LENGTH_SHORT).show();
            return;
        }
        String address = edtHomeTown.getText().toString().trim();
        if(address.equals("")){
            Toast.makeText(UpdateEmployee.this, "Hãy điền địa chỉ!",Toast.LENGTH_SHORT).show();
            return;
        }

        String exp = edtExp.getText().toString();
        if(exp.equals("")){
            Toast.makeText(UpdateEmployee.this, "Hãy điền kinh nghiệm!",Toast.LENGTH_SHORT).show();
            return;
        }
        String groupName = actvGroup.getText().toString().trim();
        if(groupName.equals("")){
            Toast.makeText(UpdateEmployee.this, "Hãy điền nhóm!",Toast.LENGTH_SHORT).show();
            return;
        }
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String firstDayWork = dateFormat.format(new Date()).toString();
        String sSalary;
        sSalary = edtSalary.getText().toString().trim();
        if(edtSalary.getText().toString().equals("")){
            Toast.makeText(UpdateEmployee.this, "Hãy điền lương!",Toast.LENGTH_SHORT).show();
            return;
        }
        int totalSalary = model.getTotalSalary();
        int previousMonthSalary = model.getPreviousSalary();
        int status = model.getStatus();
        String note = model.getNote();
        int id = model.getId();
        int salary = FormatNumber.getNumber(sSalary);
        EmployeeModel employeeModel = new EmployeeModel(id,name, gender,birthday,phone,address,avatar,exp,groupName
                ,firstDayWork,salary,totalSalary,previousMonthSalary,status,note);

        Log.e("data", employeeModel.getGender() + " ");

        DatabaseHandle.getInstance(UpdateEmployee.this).updateEmployee(employeeModel);
        Toast.makeText(UpdateEmployee.this, "Sửa nhân viên thành công!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(UpdateEmployee.this, ListEmployeeActivity.class);
        intent.putExtra(ListGroupAdapter.NAME_GROUP,model.getGroup());
        startActivity(intent);
        finish();
    }
}
