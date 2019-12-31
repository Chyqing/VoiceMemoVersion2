package com.glriverside.chyqing.memorandum.Contract;

import android.provider.BaseColumns;

public class MemoContract {
    private MemoContract(){

    }

    public static class MemoEntry implements BaseColumns{
        public static final String TABLE_NAME = "tbl_memo";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_CONTENT_PATH = "content_path";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_TODO = "to_do";
        public static final String COLUMN_NAME_ALARM = "alarm";
        public static final String COLUMN_NAME_ALARM_TIME = "alarm_time";
    }
}
