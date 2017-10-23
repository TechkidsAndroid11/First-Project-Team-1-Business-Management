package com.example.haihoang.managemaster.databases;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by Linh Phan on 10/23/2017.
 */

public class DatabaseHandle {
    private AssetHelper assetHelper;
    private SQLiteDatabase sqLiteDatabase;
    private static DatabaseHandle databaseHandle;

    public DatabaseHandle(Context context)
    {
        assetHelper = new AssetHelper(context); // tạo DB
    }
    public static DatabaseHandle getInstance(Context context)
    {
        if(databaseHandle==null)
        {
            databaseHandle = new DatabaseHandle(context);
            return databaseHandle;
        }
        else
            return databaseHandle;
    }
    public void addEmployee(EmployeeModel model)
    {
        SQLiteDatabase sqLiteDatabase = assetHelper.getWritableDatabase();
        int id = model.getId();
        String name = model.getName();
        String date = model.getDate();
        int gender = model.getGender();
        String address = model.getAddress();
        String groupId = model.getGroupId();
        String exp = model.getExperience();
        String image = model.getImage();
        String firstDayWork = model.getFirstDayWork();
        int status = model.getStatus();
        float previousMonthSalary = model.getPrevivousMonthSalary();
        float totalSalary = model.getTotalSalary();
        String note = model.getNote();

        String sql="insert into Employee values("+id+",'"+name+"','"+date+"',"+gender+",'"+address+"','"+groupId+"','"+exp+"'" +
                ",'"+image+"','"+firstDayWork+"',"+status+","+previousMonthSalary+","+totalSalary+",'"+note+"')";
        sqLiteDatabase.execSQL(sql);
        sqLiteDatabase.close();
    }

    public ArrayList<EmployeeModel> getAllEmployee()
    {
        ArrayList<EmployeeModel> listEmployee = new ArrayList<>();
        String sql="select * from Employee";
        SQLiteDatabase sqLiteDatabase = assetHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sql,null);
        cursor.moveToFirst();
        while(cursor.isAfterLast())
        {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String date = cursor.getString(2);
            int gender =cursor.getInt(3);
            String address = cursor.getString(4);
            String groupId = cursor.getString(5);
            String exp = cursor.getString(6);
            String image = cursor.getString(7);
            String firstDayWork = cursor.getString(8);
            int status = cursor.getInt(9);
            float previousMonthSalary = cursor.getFloat(10);
            float totalSalary = cursor.getFloat(11);
            String note = cursor.getString(12);
            listEmployee.add(new EmployeeModel(id,name,date,gender,address,groupId,exp,image,firstDayWork,status,
                    previousMonthSalary,totalSalary,note));
            cursor.moveToNext();
        }
        return listEmployee;
    }
}
