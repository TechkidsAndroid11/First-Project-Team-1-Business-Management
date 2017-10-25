package com.example.haihoang.managemaster.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.haihoang.managemaster.R;
import com.example.haihoang.managemaster.databases.DatabaseHandle;
import com.example.haihoang.managemaster.models.EmployeeModel;
import com.example.haihoang.managemaster.utils.ImageUtils;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddEmployeeActivity extends AppCompatActivity {

    ImageView imgAvatar;
    EditText edtName, edtId, edtDOB, edtPhone, edtHomeTown, edtExp, edtGroup, edtSalary;
    RadioGroup radioGender;
    FloatingActionButton btnDone;
    String base64;
    Bitmap bitmap;
    int gender = 1;
    Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_home);

        setupUI();

        imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectFuntion();
            }
        });

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
                clearEditText();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

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
        edtGroup = (EditText) findViewById(R.id.edtGroup);
        edtSalary = (EditText) findViewById(R.id.edtSalary);
    }

    private void clearEditText(){
        edtPhone.setText("");
        edtGroup.setText("");
        edtSalary.setText("");
        edtExp.setText("");
        edtDOB.setText("");
        edtHomeTown.setText("");
        edtName.setText("");
    }

    private void selectFuntion() {
        final String[] item = {"Take Photo", "Open Library", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(AddEmployeeActivity.this);
        builder.setTitle("Thêm Ảnh");
        builder.setItems(item, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(item[i].equals("Take Photo")){
                    cameraIntent();
                }
                else if(item[i].equals("Open Library")){
                    galleryIntent();
                }
                else{
                    dialogInterface.dismiss();
                }
            }
        }).show();

    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        uri = ImageUtils.getUriFromImage(this);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);

        if(intent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(intent, 2);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                if (data != null) {
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.e("MainActivity", "Data Null!!!!");
                }

                ImageUtils imU = new ImageUtils();

                base64 = imU.encodeTobase64(bitmap);

                Picasso.with(AddEmployeeActivity.this).load(data.getData()).noPlaceholder().fit().centerCrop()
                        .into((ImageView) findViewById(R.id.ivAddImage));
            }
            else if(requestCode == 2){
                Log.e("check request", "I'm here 222");
                    if (resultCode == RESULT_OK) {
                        Log.e("check request", "I'm here");
                        bitmap = ImageUtils.getBitmap(this);

                        ImageUtils imU = new ImageUtils();

                        base64 = imU.encodeTobase64(bitmap);
                    }
                Picasso.with(AddEmployeeActivity.this).load(uri).noPlaceholder().centerCrop().fit().into((ImageView) findViewById(R.id.ivAddImage));

            }

        }

    }


    public void getInfor() throws ParseException {
        String name = edtName.getText().toString().trim();
        if(name.equals("")){
            Toast.makeText(AddEmployeeActivity.this, "Hãy điền tên!",Toast.LENGTH_SHORT).show();
            return;
        }
        //int id = Integer.parseInt(edtId.getText().toString().trim());

        String birthday = edtDOB.getText().toString().trim();
        if(birthday.equals("")){
            Toast.makeText(AddEmployeeActivity.this, "Hãy điền ngày sinh!",Toast.LENGTH_SHORT).show();
            return;
        }
        String phone = edtPhone.getText().toString().trim();
        if(phone.equals("")){
            Toast.makeText(AddEmployeeActivity.this, "Hãy điền số điện thoại!",Toast.LENGTH_SHORT).show();
            return;
        }
        String address = edtHomeTown.getText().toString().trim();
        if(address.equals("")){
            Toast.makeText(AddEmployeeActivity.this, "Hãy điền địa chỉ!",Toast.LENGTH_SHORT).show();
            return;
        }
        String avatar;
        if(base64 == null){
            Toast.makeText(AddEmployeeActivity.this, "Hãy chọn ảnh!",Toast.LENGTH_SHORT).show();
            return;
        }else{
             avatar = base64;
        }
        String exp = edtExp.getText().toString();
        if(exp.equals("")){
            Toast.makeText(AddEmployeeActivity.this, "Hãy điền kinh nghiệm!",Toast.LENGTH_SHORT).show();
            return;
        }
        String groupName = edtGroup.getText().toString().trim();
        if(groupName.equals("")){
            Toast.makeText(AddEmployeeActivity.this, "Hãy điền nhóm!",Toast.LENGTH_SHORT).show();
            return;
        }
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String firstDayWork = dateFormat.format(new Date()).toString();
        int salary;
        try {
            salary = Integer.parseInt(edtSalary.getText().toString());
            if(edtSalary.getText().toString().equals("")){
                Toast.makeText(AddEmployeeActivity.this, "Hãy điền lương!",Toast.LENGTH_SHORT).show();
                return;
            }
        }catch (NumberFormatException e){
            Toast.makeText(AddEmployeeActivity.this, "Lương phải là số!",Toast.LENGTH_SHORT).show();
            return;
        }
        int totalSalary = 0;
        int previousMonthSalary = 0;
        int status = 0;
        String note = "";

        EmployeeModel employeeModel = new EmployeeModel(name, gender,birthday,phone,address,avatar,exp,groupName
                ,firstDayWork,salary,totalSalary,previousMonthSalary,status,note);

        Log.e("data", employeeModel.getGender() + " ");

        DatabaseHandle.getInstance(AddEmployeeActivity.this).addEmployee(employeeModel);

    }


}


