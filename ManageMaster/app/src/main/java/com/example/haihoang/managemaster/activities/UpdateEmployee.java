package com.example.haihoang.managemaster.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.haihoang.managemaster.R;
import com.example.haihoang.managemaster.adapters.ListGroupAdapter;
import com.example.haihoang.managemaster.databases.DatabaseHandle;
import com.example.haihoang.managemaster.models.EmployeeModel;
import com.example.haihoang.managemaster.utils.ImageUtils;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class UpdateEmployee extends AppCompatActivity {
    EmployeeModel model;
    ImageView imgAvatar;
    EditText edtName, edtDOB, edtPhone, edtHomeTown, edtExp, edtSalary;
    AutoCompleteTextView actvGroup;
    RadioGroup radioGender;
    FloatingActionButton btnDone;
    //String base64;
    String[] base64;
    Bitmap bitmap;
    int gender = 1;
    Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);
        setupUI();
        Intent intent = getIntent();
         model = (EmployeeModel) intent.getSerializableExtra("Employee");

        edtName.setText(model.getName());
        edtDOB.setText(model.getDate());
        edtHomeTown.setText(model.getAddress());
        edtPhone.setText(model.getPhone());
        edtExp.setText(model.getExperience());
        edtSalary.setText(model.getDaySalary()+"");
        actvGroup.setText(model.getGroup());


        radioGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                Log.e("radio", "Isaaaaaa");
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
        imgAvatar.setImageBitmap(bitmap);

    }
    private void setupUI() {
        radioGender = (RadioGroup) findViewById(R.id.radio_gender);
        btnDone = (FloatingActionButton) findViewById(R.id.btnDone);
        imgAvatar = (ImageView) findViewById(R.id.ivAddImage);
        edtName = (EditText) findViewById(R.id.edtName);
        edtDOB = (EditText) findViewById(R.id.edtDOB);
        //edtId = (EditText) findViewById(R.id.edtId);
        edtPhone = (EditText) findViewById(R.id.edtPhone);
        edtHomeTown = (EditText) findViewById(R.id.edtHomeTown);
        edtExp = (EditText) findViewById(R.id.edtExp);
        //  edtGroup = (EditText) findViewById(R.id.edtGroup);
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
       // base64 = "";
    }


    public void getInfor() throws ParseException {
        boolean isSuccess = false;
        String avatar= model.getAvatar() ;
//        if(base64 == null){
//            Toast.makeText(UpdateEmployee.this, "Hãy chọn ảnh!",Toast.LENGTH_SHORT).show();
//            return;
//        }else{
//            avatar = base64[0];
//        }
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
        int salary;
        try {
            salary = Integer.parseInt(edtSalary.getText().toString());
            if(edtSalary.getText().toString().equals("")){
                Toast.makeText(UpdateEmployee.this, "Hãy điền lương!",Toast.LENGTH_SHORT).show();
                return;
            }
        }catch (NumberFormatException e){
            Toast.makeText(UpdateEmployee.this, "Lương phải là số!",Toast.LENGTH_SHORT).show();
            return;
        }
        int totalSalary = model.getTotalSalary();
        int previousMonthSalary = model.getPreviousSalary();
        int status = model.getStatus();
        String note = model.getNote();
        int id = model.getId();
        EmployeeModel employeeModel = new EmployeeModel(id,name, gender,birthday,phone,address,avatar,exp,groupName
                ,firstDayWork,salary,totalSalary,previousMonthSalary,status,note);

        Log.e("data", employeeModel.getGender() + " ");

        DatabaseHandle.getInstance(UpdateEmployee.this).updateEmployee(employeeModel);
        Toast.makeText(UpdateEmployee.this, "Sửa nhân viên thành công!", Toast.LENGTH_SHORT).show();
        clearEditText();
        Intent intent = new Intent(UpdateEmployee.this, ListEmployeeActivity.class);
        intent.putExtra(ListGroupAdapter.NAME_GROUP,model.getGroup());
        startActivity(intent);

    }
}
