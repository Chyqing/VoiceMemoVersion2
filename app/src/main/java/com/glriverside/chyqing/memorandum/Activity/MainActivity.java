package com.glriverside.chyqing.memorandum.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.glriverside.chyqing.memorandum.Contract.MemoContract;
import com.glriverside.chyqing.memorandum.Contract.UserContract;
import com.glriverside.chyqing.memorandum.Manager.UserOpenHelper;
import com.glriverside.chyqing.memorandum.R;
import com.glriverside.chyqing.memorandum.Values.MemoValues;
import com.glriverside.chyqing.memorandum.Values.UserValues;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {

    private EditText mAccount;      //用户名编辑控件
    private EditText mPwd;          //密码编辑控件
    private Button mRegisterButton; //注册按钮控件
    private Button mLoginButton;    //登录按钮控件
    private CheckBox cbRememberPwd; //记住密码控件
    private String spFileName;
    private String accountKey;
    private String passwordKey;
    private String rememberPasswordKey;
    private UserOpenHelper userOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
        mRegisterButton.setOnClickListener(mListener);
        mLoginButton.setOnClickListener(mListener);
    }

    public void init(){
        mAccount = findViewById(R.id.et_user);
        mPwd = findViewById(R.id.et_pwd);
        mRegisterButton = findViewById(R.id.bt_sign);
        mLoginButton = findViewById(R.id.bt_login);
        cbRememberPwd = findViewById(R.id.cb_remember_pwd);

        spFileName = getResources().getString(R.string.shared_preferences_file_name);
        accountKey = getResources().getString(R.string.login_account_name);
        passwordKey = getResources().getString(R.string.login_password);
        rememberPasswordKey = getResources().getString(R.string.login_remember_password);
        SharedPreferences spFile = getSharedPreferences(spFileName, Context.MODE_PRIVATE);

        String account = spFile.getString(accountKey, null);
        String password = spFile.getString(passwordKey, null);
        Boolean rememberPassword = spFile.getBoolean(rememberPasswordKey, false);

        if(account != null && !TextUtils.isEmpty(account)){
            mAccount.setText(account);
        }
        if (password != null && !TextUtils.isEmpty(password)){
            mPwd.setText(password);
        }
        cbRememberPwd.setChecked(rememberPassword);
    }

    View.OnClickListener mListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bt_sign:
                    Intent intent_Login_to_Register = new Intent(MainActivity.this, SignUpActivity.class) ;
                    startActivity(intent_Login_to_Register);
                    finish();
                    break;
                case R.id.bt_login:
                    login();
                    break;

            }
        }
    };

    public void login() {
        if (isUserNameAndPwdValid()) {
            String userName = mAccount.getText().toString().trim();
            String userPwd = mPwd.getText().toString().trim();

            userOpenHelper = new UserOpenHelper(MainActivity.this);
            SQLiteDatabase database = userOpenHelper.getReadableDatabase();
            Cursor cursor = database.query(UserContract.UserEntry.TABLE_NAME,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null);
            if ( cursor.moveToFirst()){

                while (!cursor.isAfterLast()) {

                    String name = cursor.getString(cursor.getColumnIndex(UserContract.UserEntry.COLUMN_NAME_USER));
                    String pwd = cursor.getString(cursor.getColumnIndex(UserContract.UserEntry.COLUMN_NAME_PASSWORD));

                    if (userName.equals(name)){
                        if (userPwd.equals(pwd)){

                            spFileName = getResources().getString(R.string.shared_preferences_file_name);
                            accountKey = getResources().getString(R.string.login_account_name);
                            passwordKey = getResources().getString(R.string.login_password);
                            rememberPasswordKey = getResources().getString(R.string.login_remember_password);
                            SharedPreferences spFile = getSharedPreferences(spFileName, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = spFile.edit();
                            String account = mAccount.getText().toString();
                            editor.putString(accountKey, account);
                            if(cbRememberPwd.isChecked()){
                                String password = mPwd.getText().toString();
                                editor.putString(passwordKey, password);
                                editor.putBoolean(rememberPasswordKey, true);
                                editor.apply();
                            }else{
                                editor.remove(passwordKey);
                                editor.remove(rememberPasswordKey);
                                editor.apply();
                            }

                            Intent intent = new Intent(MainActivity.this, MemoActivity.class);
                            startActivity(intent);
                            return;
                        }else{
                            Toast.makeText(MainActivity.this,
                                    "密码输入错误！",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    cursor.moveToNext();
                }
            }
            Toast.makeText(MainActivity.this,
                    "用户名不存在！",
                    Toast.LENGTH_SHORT).show();
        }
    }

    //判断道信息是否填写完成
    public boolean isUserNameAndPwdValid() {
        if (mAccount.getText().toString().trim().equals("")||mPwd.getText().toString().trim().equals("")) {
            Toast.makeText(MainActivity.this,R.string.message1,
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
