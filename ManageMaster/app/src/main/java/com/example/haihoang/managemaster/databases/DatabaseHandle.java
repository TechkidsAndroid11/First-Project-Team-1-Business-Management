package com.example.haihoang.managemaster.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

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
}
