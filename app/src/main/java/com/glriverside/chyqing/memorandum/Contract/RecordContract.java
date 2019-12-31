package com.glriverside.chyqing.memorandum.Contract;

import android.provider.BaseColumns;

public class RecordContract {
    private RecordContract(){}

    public static class RecordEntry implements BaseColumns {
        public static final String TABLE_NAME = "tbl_record";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_CONTENT_PATH = "content_path";
    }
}
