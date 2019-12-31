package com.glriverside.chyqing.memorandum.Activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.glriverside.chyqing.memorandum.Contract.UserContract;
import com.glriverside.chyqing.memorandum.Manager.UserOpenHelper;
import com.glriverside.chyqing.memorandum.R;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {
    private EditText mAccount;
    private EditText mPwd;
    private Button mSureButton;
    private UserOpenHelper userOpenHelper;
    private String userName;
    private String userPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        init();
        //注册界面按钮的监听事件
        mSureButton.setOnClickListener(m_register_Listener);
    }

    public void init(){
        mAccount = findViewById(R.id.sign_phone_num);
        mPwd = findViewById(R.id.sign_secre);
        mSureButton = findViewById(R.id.bt_sure_sign_up);
    }

    View.OnClickListener m_register_Listener = new View.OnClickListener() {
        public void onClick(View v) {
            mSureButton.setClickable(false);
            if (isUserNameAndPwdValid()) {
                userName = mAccount.getText().toString().trim();
                userPwd = mPwd.getText().toString().trim();

                userOpenHelper = new UserOpenHelper(SignUpActivity.this);

                mSureButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SQLiteDatabase db = userOpenHelper.getWritableDatabase();
                        ContentValues contentValues = new ContentValues();

                        contentValues.put(UserContract.UserEntry.COLUMN_NAME_USER, userName);
                        contentValues.put(UserContract.UserEntry.COLUMN_NAME_PASSWORD, userPwd);

                        db.insert(UserContract.UserEntry.TABLE_NAME,
                                null,
                                contentValues);

                        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
            }
        }
    };

    public boolean isUserNameAndPwdValid() {
        if (mAccount.getText().toString().trim().equals("")||mPwd.getText().toString().trim().equals("")) {
            Toast.makeText(SignUpActivity.this,R.string.message1, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
