package com.glriverside.chyqing.memorandum.Manager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.glriverside.chyqing.memorandum.Contract.RecordContract;

public class RecordOpenHelper extends SQLiteOpenHelper {

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + RecordContract.RecordEntry.TABLE_NAME + " ( " +
                    RecordContract.RecordEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    RecordContract.RecordEntry.COLUMN_NAME_TITLE + " VARCHAR(32), " +
                    RecordContract.RecordEntry.COLUMN_NAME_CONTENT_PATH + "TEXT )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IT EXISTS " + RecordContract.RecordEntry.TABLE_NAME;

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "record.db";

    private Context context;

    public RecordOpenHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
       // initDb(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

  /*  public void initDb(SQLiteDatabase sqLiteDatabase){
        Resources resources = context.getResources();
    }*/
}
