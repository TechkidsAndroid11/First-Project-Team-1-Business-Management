package com.example.haihoang.managemaster.databases;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by Linh Phan on 10/23/2017.
 */

public class AssetHelper extends SQLiteAssetHelper{
    private static final String DATABASE_NAME="manage.db";
    private static final int DATABASE_VERSION=1;
    public AssetHelper(Context context) {
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }

}
