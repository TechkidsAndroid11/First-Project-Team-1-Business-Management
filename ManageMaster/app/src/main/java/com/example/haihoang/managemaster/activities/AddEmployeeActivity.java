package com.example.haihoang.managemaster.activities;

import android.app.DatePickerDialog;
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
import com.example.haihoang.managemaster.databases.DatabaseHandle;
import com.example.haihoang.managemaster.models.EmployeeModel;
import com.example.haihoang.managemaster.utils.CircleTransform;
import com.example.haihoang.managemaster.utils.FormatNumber;
import com.example.haihoang.managemaster.utils.ImageUtils;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddEmployeeActivity extends AppCompatActivity{

    private ImageView imgAvatar, ivPickDate, ivPickDate2;
    private TextView edtDOB, edtFirstDayWork, tvTitle;
    private EditText edtName, edtPhone, edtHomeTown, edtExp, edtSalary;
    private AutoCompleteTextView actvGroup;
    private RadioGroup radioGender;
    private FloatingActionButton btnDone;
    String base64;
    Bitmap bitmap;
    int gender = 1;
    Uri uri;
    Calendar cal;
    Date date;
    int s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_add_employee);
        getSupportActionBar().setElevation(0);
        setupUI();
        setAdapterForAutoCompleteTextView();
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
            }
        });

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

        cal=Calendar.getInstance();
        SimpleDateFormat dft=null;
        dft=new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String strDate=dft.format(cal.getTime());
        edtDOB.setText(strDate);
        edtFirstDayWork.setText(strDate);

        edtSalary.addTextChangedListener(new NumberTextWatcher(edtSalary));

    }

    private void pickDateDialog() {
        DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                edtDOB.setText(day + "/" + (month +1) + "/" + year);
                cal.set(year, month, day);
                date = cal.getTime();
            }
        };
        String s=edtDOB.getText()+"";
        String strArrtmp[]=s.split("/");
        int ngay=Integer.parseInt(strArrtmp[0]);
        int thang=Integer.parseInt(strArrtmp[1]) - 1;
        int nam=Integer.parseInt(strArrtmp[2]);
        DatePickerDialog pic=new DatePickerDialog(
                AddEmployeeActivity.this,
                callback, nam, thang, ngay);
        pic.setTitle("Chọn ngày sinh");
        pic.show();
    }

    private void pickDateDialog2() {
        DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                edtFirstDayWork.setText(day + "/" + (month +1) + "/" + year);
                cal.set(year, month, day);
                date = cal.getTime();

            }
        };
        String s=edtFirstDayWork.getText()+"";
        String strArrtmp2[]=s.split("/");
        int ngay=Integer.parseInt(strArrtmp2[0]);
        int thang=Integer.parseInt(strArrtmp2[1]) - 1;
        int nam=Integer.parseInt(strArrtmp2[2]);
        DatePickerDialog pic=new DatePickerDialog(
                AddEmployeeActivity.this,
                callback, nam, thang, ngay);
        pic.setTitle("Chọn Ngày Gia Nhập");
        pic.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("check", "Onresume");

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("check", "Onrestart");
    }


    private void setupUI() {
        edtFirstDayWork = (TextView) findViewById(R.id.edtFirstDayWork);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        ivPickDate2 = (ImageView) findViewById(R.id.ivPickDate2);
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
        base64 = "";
    }

    private void selectFuntion() {
        final String[] item = {"Chụp ảnh", "Mở Bộ sưu tập", "Huỷ"};

        AlertDialog.Builder builder = new AlertDialog.Builder(AddEmployeeActivity.this);
        builder.setTitle("Thêm Ảnh");
        builder.setItems(item, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(item[i].equals("Chụp ảnh")){
                    cameraIntent();
                }
                else if(item[i].equals("Mở Bộ sưu tập")){
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
        intent.setType("image/*"); // mở tất cả các folder lưa trữ ảnh
        intent.setAction(Intent.ACTION_GET_CONTENT); // đi đến folder mình chọn
        startActivityForResult(Intent.createChooser(intent, "Chọn Ảnh"), 1);
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
        CircleTransform circleTransform = new CircleTransform();
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

                String tempBase64 = imU.encodeTobase64(bitmap);
                base64 = ImageUtils.resizeBase64Image(tempBase64);
                imgAvatar.setPadding(0,0,0,0);

                circleTransform.transform(bitmap);
                Picasso.with(AddEmployeeActivity.this).load(data.getData()).transform(new CircleTransform()).into((ImageView) findViewById(R.id.ivAddImage));

            }
            else if(requestCode == 2){
                Log.e("check request", "I'm here 222");
                    if (resultCode == RESULT_OK) {
                        Log.e("check request", "I'm here");
                        bitmap = ImageUtils.getBitmap(this);

                        ImageUtils imU = new ImageUtils();

                        String tempBase64 = imU.encodeTobase64(bitmap);
                        base64 = ImageUtils.resizeBase64Image(tempBase64);
                    }
                imgAvatar.setPadding(0,0,0,0);
                circleTransform.transform(bitmap);
                Picasso.with(AddEmployeeActivity.this).load(uri).transform(new CircleTransform()).into((ImageView) findViewById(R.id.ivAddImage));

            }

        }

    }


    public void getInfor() throws ParseException {
        boolean isSuccess = false;
        String avatar;
        if(base64 == null){
            Toast.makeText(AddEmployeeActivity.this, "Hãy chọn ảnh!",Toast.LENGTH_SHORT).show();
            return;
        }else{
            avatar = base64;
        }
        String name = edtName.getText().toString().trim();
        if(name.equals("")){
            Toast.makeText(AddEmployeeActivity.this, "Hãy điền tên!",Toast.LENGTH_SHORT).show();
            return;
        }
        String birthday = edtDOB.getText().toString().trim();
        if(birthday.equals("")){
            Toast.makeText(AddEmployeeActivity.this, "Hãy điền ngày sinh!",Toast.LENGTH_SHORT).show();
            Log.e("check: ", "adasdas");
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
        String exp = edtExp.getText().toString();
        if(exp.equals("")){
            Toast.makeText(AddEmployeeActivity.this, "Hãy điền kinh nghiệm!",Toast.LENGTH_SHORT).show();
            return;
        }
        String groupName = actvGroup.getText().toString().trim();
        if(groupName.equals("")){
            Toast.makeText(AddEmployeeActivity.this, "Hãy điền nhóm!",Toast.LENGTH_SHORT).show();
            return;
        }
        String firstDayWork = edtFirstDayWork.getText().toString();
        String sSalary;
        sSalary = edtSalary.getText().toString();
        if(edtSalary.getText().toString().equals("")){
            Toast.makeText(AddEmployeeActivity.this, "Hãy điền lương!",Toast.LENGTH_SHORT).show();
            return;
        }
        int totalSalary = 0;
        int previousMonthSalary = 0;
        int status = 0;
        String note = "";
        int salary = FormatNumber.getNumber(sSalary);
        EmployeeModel employeeModel = new EmployeeModel(name, gender,birthday,phone,address,avatar,exp,groupName
                ,firstDayWork,salary,totalSalary,previousMonthSalary,status,note);


        DatabaseHandle.getInstance(AddEmployeeActivity.this).addEmployee(employeeModel);
        Toast.makeText(AddEmployeeActivity.this, "Thêm nhân viên thành công!", Toast.LENGTH_LONG).show();
        clearEditText();
        finish();

    }


}


