package com.glriverside.chyqing.memorandum.Contract;

import android.provider.BaseColumns;

public class UserContract {
    public UserContract(){}

    public static class UserEntry implements BaseColumns{
        public static final String TABLE_NAME = "tbl_user";
        public static final String COLUMN_NAME_USER = "user";
        public static final String COLUMN_NAME_PASSWORD = "password";
    }
}
