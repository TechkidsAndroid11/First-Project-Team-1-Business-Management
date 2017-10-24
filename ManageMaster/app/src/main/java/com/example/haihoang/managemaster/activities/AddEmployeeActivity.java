package com.example.haihoang.managemaster.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.haihoang.managemaster.R;
import com.example.haihoang.managemaster.utils.ImageUtils;

import java.io.IOException;

public class AddEmployeeActivity extends AppCompatActivity {

    ImageView imgAvatar;
    EditText edtName, edtDOB, edtHomeTown, edtExp, edtGroup, edtSalary;
    FloatingActionButton btnDone;
    String base64;
    Bitmap bitmap;
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

    }

    private void setupUI() {
        btnDone = (FloatingActionButton) findViewById(R.id.btnDone);
        imgAvatar = (ImageView) findViewById(R.id.ivAddImage);
        edtName = (EditText) findViewById(R.id.edtName);
        edtDOB = (EditText) findViewById(R.id.edtDOB);
        edtHomeTown = (EditText) findViewById(R.id.edtHomeTown);
        edtExp = (EditText) findViewById(R.id.edtExp);
        edtGroup = (EditText) findViewById(R.id.edtGroup);
        edtSalary = (EditText) findViewById(R.id.edtSalary);
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

        Uri uri = ImageUtils.getUriFromImage(this);

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
            }
            else if(requestCode == 2){
                Log.e("check request", "I'm here 222");
                    if (resultCode == RESULT_OK) {
                        Log.e("check request", "I'm here");
                        bitmap = ImageUtils.getBitmap(this);

                        ImageUtils imU = new ImageUtils();

                        base64 = imU.encodeTobase64(bitmap);
                    }
            }
        }

    }



}


