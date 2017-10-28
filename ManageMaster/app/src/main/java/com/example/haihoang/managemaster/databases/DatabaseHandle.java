package com.example.haihoang.managemaster.databases;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.haihoang.managemaster.models.EmployeeModel;

import java.util.ArrayList;

/**
 * Created by Linh Phan on 10/23/2017.
 */

public class DatabaseHandle extends SQLiteOpenHelper{
    private static final String TAG = "DatabaseHandle";
    public static String DATABASE_NAME = "EmployeeManagement";
    public static String ID = "Id";
    public static String NAME = "name";
    public static String GENDER = "gender";
    public static String DOB = "dob";
    public static String PHONE = "phone";
    public static String ADDRESS = "address";
    public static String AVATAR = "avatar";
    public static String EXPERIENCE = "experience";
    public static String GROUPNAME = "groupName";
    public static String FRISTDAYWORK = "firstdaywork";
    public static String DAYSALARY = "daysalary";
    public static String TOTALSALARY = "totalsalary";
    public static String PREVIOUSSALARY = "previoussalary";
    public static String STATUS = "status";
    public static String NOTE = "note";
    private static DatabaseHandle databaseHandle;
    private Context context;

    public DatabaseHandle(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context=context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate: ");
        String sqlQuery = "CREATE TABLE Employee (Id INTEGER PRIMARY KEY AUTOINCREMENT,Name text, Gender integer, DOB text,Phone text, Address text,Avatar text, Experience text,GroupName text, Firstdaywork text, DaySalary integer, TotalSalary integer,PreviousMonthSalary integer,Status integer,Note text);";
        db.execSQL(sqlQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXITS Employee");
        onCreate(db);
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

    public ArrayList<EmployeeModel> getAllEmployee()
    {
        ArrayList<EmployeeModel> listEmployee = new ArrayList<>();
        //String sql="select * from Employee";
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from Employee",null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                int gender = cursor.getInt(2);
                String date = cursor.getString(3);
                String phone =cursor.getString(4);
                String address = cursor.getString(5);
                String avatar = cursor.getString(6);
                String exp = cursor.getString(7);
                String group = cursor.getString(8);
                String firstDayWork = cursor.getString(9);
                int daySalary = cursor.getInt(10);
                int totalSalary = cursor.getInt(11);
                int previousMonthSalary = cursor.getInt(12);
                int status = cursor.getInt(13);
                String note = cursor.getString(14);

                listEmployee.add(new EmployeeModel(id, name,gender, date,phone,address,avatar,exp,group,firstDayWork,
                        daySalary,totalSalary,previousMonthSalary,status,note));
                cursor.moveToNext();

            }
        }
        sqLiteDatabase.close();
        return listEmployee;
    }
    public ArrayList<EmployeeModel> getAllEmployeeById(int id)
    {
        ArrayList<EmployeeModel> listEmployee = new ArrayList<>();
        String sql="select * from Employee where id="+id+"";
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sql,null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                // int id = cursor.getInt(0);
                String name = cursor.getString(1);
                int gender = cursor.getInt(2);
                String date = cursor.getString(3);
                String phone =cursor.getString(4);
                String address = cursor.getString(5);
                String avatar = cursor.getString(6);
                String exp = cursor.getString(7);
                String group = cursor.getString(8);
                String firstDayWork = cursor.getString(9);
                int daySalary = cursor.getInt(10);
                int totalSalary = cursor.getInt(11);
                int previousMonthSalary = cursor.getInt(12);
                int status = cursor.getInt(13);
                String note = cursor.getString(14);

                listEmployee.add(new EmployeeModel(id, name,gender, date,phone,address,avatar,exp,group,firstDayWork,
                        daySalary,totalSalary,previousMonthSalary,status,note));
                cursor.moveToNext();

            }
        }
        sqLiteDatabase.close();
        return listEmployee;
    }
    public ArrayList<EmployeeModel> getAllEmployeeByGroup(String groupName)
    {
        ArrayList<EmployeeModel> listEmployee = new ArrayList<>();
        String sql="select * from Employee where groupName = '"+groupName+"' ";
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sql,null);
        if (cursor != null) {
            cursor.moveToFirst();
            Log.d(TAG, "getAllEmployeeByGroup: " + cursor.getCount() + " " + cursor.getColumnCount());
            while (!cursor.isAfterLast()) {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                int gender = cursor.getInt(2);
                String date = cursor.getString(3);
                String phone =cursor.getString(4);
                String address = cursor.getString(5);
                String avatar = cursor.getString(6);
                String exp = cursor.getString(7);
                //String group = cursor.getString(8);
                String firstDayWork = cursor.getString(9);
                int daySalary = cursor.getInt(10);
                int totalSalary = cursor.getInt(11);
                int previousMonthSalary = cursor.getInt(12);
                int status = cursor.getInt(13);
                String note = cursor.getString(14);

                listEmployee.add(new EmployeeModel(id, name,gender, date,phone,address,avatar,exp,groupName,firstDayWork,
                        daySalary,totalSalary,previousMonthSalary,status,note));
                cursor.moveToNext();

            }
        }
        sqLiteDatabase.close();
        return listEmployee;
    }
    public void addEmployee(EmployeeModel model)
    {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        int id = model.getId();
        String name = model.getName();
        int gender = model.getGender();
        String date = model.getDate();
        String phone =model.getPhone();
        String address = model.getAddress();
        String avatar = model.getAvatar();
        String exp = model.getExperience();
        String group = model.getGroup();
        String firstDayWork = model.getFirstDayWork();
        int daySalary = model.getDaySalary();
        int totalSalary = model.getTotalSalary();
        int previousMonthSalary = model.getPreviousSalary();
        int status = model.getStatus();
        String note = model.getNote();

        String sql="insert into Employee(name,gender,dob,phone,address,avatar,experience,groupname," +
                "firstdaywork,daySalary,totalSalary,PreviousMonthSalary,status,note)" +
                " values('"+name+"',"+gender+",'"+date+"','"+phone+"','"+address+"','"+avatar+"'" +
                ",'"+exp+"','"+group+"','"+firstDayWork+"',"+daySalary+","+totalSalary+","+previousMonthSalary+","
                +status+",'"+note+"')";

        sqLiteDatabase.execSQL(sql);
        Log.d(TAG, "addEmployee: OK");
        sqLiteDatabase.close();
    }

    public void deleteEmployee(EmployeeModel model)
    {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String sql=" delete from Employee where id="+model.getId()+"";
        sqLiteDatabase.execSQL(sql);
        sqLiteDatabase.close();
    }
    public void resetStatusAllAbsent()
    {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String sql=" update Employee set status="+0+"";
        sqLiteDatabase.execSQL(sql);
        sqLiteDatabase.close();

    }
    public ArrayList<String> getAllGroup()
    {
        ArrayList<String> listGroup = new ArrayList<String>();
        String sql="select distinct groupName from Employee";
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sql,null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast())
            {
                String group = cursor.getString(0);
                listGroup.add(group);
                cursor.moveToNext();
            }
        }

        sqLiteDatabase.close();
        cursor.close();
        return listGroup;
    }
    public float getTotalSalaryById(int id)
    {
        float totalSalary=0;
        String sql="select totalSalary from Employee where id="+id+" ";
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sql,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast())
        {
            totalSalary=cursor.getInt(0);
            cursor.moveToNext();
        }
        sqLiteDatabase.close();
        cursor.close();
        return totalSalary;
    }
    public float getDaySalaryById(EmployeeModel model)
    {
        float salary=0;
        String sql="select daySalary from Employee where id="+model.getId()+"";
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sql,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast())
        {
            salary=cursor.getInt(0);
            cursor.moveToNext();
        }
        cursor.close();
        sqLiteDatabase.close();
        return salary;
    }
    public int getCountEmployeeInGroup(String groupName)
    {
        String sql="select count(GroupName) from Employee where GroupName='"+groupName+"'";
        int count=0;
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sql,null);
        if(cursor!=null)
        {
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                count=cursor.getInt(0);
                cursor.moveToNext();
            }
        }
        return count;
    }
    public void updateStatus(EmployeeModel model)
    {
        String sql="";
        int daySalary = model.getDaySalary();
        int totalSalary = model.getTotalSalary();
        //totalSalary+=daySalary;
        if(model.getStatus()==0)
        {
            totalSalary+=daySalary;
            sql="update Employee set status=1,TotalSalary= "+totalSalary+" where id="+model.getId()+"";
        }
        else
        {
            totalSalary-=daySalary;
            sql="update Employee set status=0,TotalSalary= "+totalSalary+" where id="+model.getId()+"";
        }
        SQLiteDatabase sqLiteDatabase= getWritableDatabase();
        sqLiteDatabase.execSQL(sql);
        sqLiteDatabase.close();
    }
    public void deleteGroup(String nameGroup)
    {
        String sql="delete from Employee where GroupName='"+nameGroup+"'";
        SQLiteDatabase sqLiteDatabase=getReadableDatabase();
        sqLiteDatabase.execSQL(sql);
        sqLiteDatabase.close();
    }
    public void addSalarytoTotal(EmployeeModel model)
    {
        int daySalary = model.getDaySalary();
        int totalSalary = model.getTotalSalary();
        totalSalary+=daySalary;
        String sql="update Employee set TotalSalary = "+totalSalary+" where id="+model.getId()+"";
        SQLiteDatabase sqLiteDatabase =getWritableDatabase();
        sqLiteDatabase.execSQL(sql);
        sqLiteDatabase.close();
    }
    public void minusSalarytoTotal(EmployeeModel model)
    {
        int daySalary = model.getDaySalary();
        int totalSalary = model.getTotalSalary();
        totalSalary-=daySalary;
        String sql="update Employee set TotalSalary = "+totalSalary+" where id="+model.getId()+"";
        SQLiteDatabase sqLiteDatabase =getWritableDatabase();
        sqLiteDatabase.execSQL(sql);
        sqLiteDatabase.close();
    }
//    public void addSalarytoTotal(EmployeeModel model)
//    {
//        int totalSalary = model.getTotalSalary();
//        int toalSalaryAfterAdd = totalSalary+ model.getDaySalary();
//        String sql="update Employee set TotalSalary = "+toalSalaryAfterAdd ;
//        SQLiteDatabase sqLiteDatabase= getWritableDatabase();
//        sqLiteDatabase
//    }
}
