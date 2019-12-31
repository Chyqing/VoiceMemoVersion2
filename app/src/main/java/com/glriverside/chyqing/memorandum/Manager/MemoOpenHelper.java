package com.glriverside.chyqing.memorandum.Manager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.glriverside.chyqing.memorandum.Contract.MemoContract;

public class MemoOpenHelper extends SQLiteOpenHelper {
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + MemoContract.MemoEntry.TABLE_NAME + " ( " +
                    MemoContract.MemoEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    MemoContract.MemoEntry.COLUMN_NAME_TITLE + " VARCHAR(32), " +
                    MemoContract.MemoEntry.COLUMN_NAME_CONTENT_PATH + " TEXT, " +
                    MemoContract.MemoEntry.COLUMN_NAME_DATE + " DATETIME NOT NULL, " +
                    MemoContract.MemoEntry.COLUMN_NAME_ALARM + " BOOLEAN, " +
                    MemoContract.MemoEntry.COLUMN_NAME_ALARM_TIME + " DATETIME, " +
                    MemoContract.MemoEntry.COLUMN_NAME_TODO + " BOOLEAN )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + MemoContract.MemoEntry.TABLE_NAME;

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "memo.db";

    private Context context;

    public MemoOpenHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
        //initDb(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }

  /*  public void initDb(SQLiteDatabase sqLiteDatabase){
        Resources resources = context.getResources();
    }*/
}
