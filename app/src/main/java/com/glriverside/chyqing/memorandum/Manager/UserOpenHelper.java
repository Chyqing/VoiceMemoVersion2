package com.glriverside.chyqing.memorandum.Manager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.glriverside.chyqing.memorandum.Contract.UserContract;

public class UserOpenHelper extends SQLiteOpenHelper {
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + UserContract.UserEntry.TABLE_NAME + " ( "
            + UserContract.UserEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + UserContract.UserEntry.COLUMN_NAME_USER + " VARCHAR(32), "
            + UserContract.UserEntry.COLUMN_NAME_PASSWORD + " VARCHAR(32) )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + UserContract.UserEntry.TABLE_NAME;

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "user.db";

    private Context context;

    public UserOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }
}
