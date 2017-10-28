package com.example.haihoang.managemaster.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.haihoang.managemaster.R;
import com.example.haihoang.managemaster.models.EmployeeModel;

public class EmployeeInforActivity extends AppCompatActivity {
    private EmployeeModel empl;
    private TextView tvname,tvgender,tvdob,tvphone,tvGroup,tvsalary,tvadd,tvExp,tvLastSalary,tvFDW;
    private ImageView ivImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_infor);
        setupUI();
        setData();
    }

    private void setupUI() {
        empl = (EmployeeModel) getIntent().getSerializableExtra(ListEmployeeActivity.EMPLOYEE);

        tvname = (TextView) findViewById(R.id.tvName);
        tvgender = (TextView) findViewById(R.id.tvGender);
        tvdob = (TextView) findViewById(R.id.tvDOB);
        tvphone = (TextView) findViewById(R.id.tvPhone);
        tvGroup = (TextView) findViewById(R.id.tvGroupName);
        tvsalary = (TextView) findViewById(R.id.tvSalary);
        tvadd = (TextView) findViewById(R.id.tvAddress);
        tvFDW = (TextView) findViewById(R.id.tvFirstDayWork);
        tvExp = (TextView) findViewById(R.id.tvExp);
        tvLastSalary = (TextView) findViewById(R.id.tvPreviousMonthSalary);
        ivImage = (ImageView) findViewById(R.id.ivAvatar);
    }
    private void setData() {
        tvname.setText(tvname.getText()+" "+empl.getName());
        tvgender.setText(tvgender.getText()+" "+(empl.getGender()==0?"Nữ":"Nam"));
        tvdob.setText(tvdob.getText()+" "+empl.getDate());
        tvphone.setText(tvphone.getText()+" "+empl.getPhone());
        tvGroup.setText(tvGroup.getText()+" "+empl.getGroup());
        tvsalary.setText(tvsalary.getText()+" "+empl.getDaySalary());
        tvadd.setText(tvadd.getText()+" "+empl.getAddress());
        tvFDW.setText(tvFDW.getText()+" "+empl.getFirstDayWork());
        tvExp.setText(tvExp.getText()+" "+empl.getExperience());
        tvLastSalary.setText(tvLastSalary.getText()+" "+empl.getPreviousSalary());

        String[] base64 = empl.getAvatar().split(",");
        Bitmap bitmap = BitmapFactory.decodeByteArray(
                Base64.decode(base64[0],Base64.DEFAULT),
                0,// offset: vị trí bđ
                (Base64.decode(base64[0],Base64.DEFAULT)).length

        );
        ivImage.setImageBitmap(bitmap);
    }
}
