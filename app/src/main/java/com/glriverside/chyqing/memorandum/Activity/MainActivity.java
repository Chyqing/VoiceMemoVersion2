package com.glriverside.chyqing.memorandum.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.glriverside.chyqing.memorandum.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {

    private Button btMemo;
    private Button btRecord;
    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //当计时结束,跳转至主界面
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, MemoActivity.class);
                startActivity(intent);
                MainActivity.this.finish();
            }
        }, 1000);
    }
        /*btMemo = findViewById(R.id.bt_memo);
        btRecord = findViewById(R.id.bt_record);

        btMemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MemoActivity.class);
                startActivity(intent);
            }
        });

        btRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RecordActivity.class);
                startActivity(intent);
            }
        });*/
}
