package com.example.haihoang.managemaster.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.haihoang.managemaster.R;
import com.example.haihoang.managemaster.models.EmployeeModel;
import com.example.haihoang.managemaster.utils.CircleTransform;
import com.example.haihoang.managemaster.utils.FormatNumber;

public class EmployeeInfoSalary extends AppCompatActivity {
    EmployeeModel employeeModel;
    private ImageView imgAvatar;
    private TextView tvName, tvMothSalary, tvPreMothSalary, tvTitle,tvContentNote;
    CircleTransform circleTransform = new CircleTransform();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_info_salary);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_inforsalary);
        getSupportActionBar().setElevation(0);
        setupUI();
        setContent();
    }

    private void setContent() {
        employeeModel = (EmployeeModel) getIntent().getSerializableExtra(SummaryActivity.EMPLOYEE);

        tvName.setText(tvName.getText() + employeeModel.getName());
        tvMothSalary.setText(tvMothSalary.getText() + " " + FormatNumber.formatNumber(employeeModel.getTotalSalary())+"đ");
        tvPreMothSalary.setText(tvPreMothSalary.getText() + " " + FormatNumber.formatNumber(employeeModel.getPreviousSalary())+"đ");
        String[] base64 = employeeModel.getAvatar().split(",");
        Bitmap bitmap = BitmapFactory.decodeByteArray(
                Base64.decode(base64[0],Base64.DEFAULT),
                0,// offset: vị trí bđ
                (Base64.decode(base64[0],Base64.DEFAULT)).length

        );
        imgAvatar.setPadding(0,0,0,0);
        imgAvatar.setImageBitmap(circleTransform.transform(bitmap));
        tvContentNote.setText(employeeModel.getNote());
    }

    private void setupUI() {
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvName = (TextView) findViewById(R.id.tvName);
        tvMothSalary = (TextView) findViewById(R.id.tvMonthSalary);
        tvPreMothSalary = (TextView) findViewById(R.id.tvPreMothSalary);
        imgAvatar = (ImageView) findViewById(R.id.ivAvatar);
        tvContentNote= (TextView) findViewById(R.id.tvContentNote);
    }
}
