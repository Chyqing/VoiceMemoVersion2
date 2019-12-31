package com.glriverside.chyqing.memorandum.Activity;

import android.content.Intent;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.glriverside.chyqing.memorandum.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Time;
import java.time.Clock;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.SimpleFormatter;

public class RecordActivity extends AppCompatActivity {
    private String time;
    private int time_h;
    private int time_m;
    private int time_s;
    private boolean isRecord = false;
    private ImageButton record_btn;
    private ImageButton finish_btn;
    private ImageButton list_btn;
    private TextView textView;
    private DrawerLayout drawerLayout;
    private LinearLayout left_list;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(runnable, 1000);
            time_s++;
            if (time_s >= 60) {
                time_m++;
                time_s %= 60;
            }
            if (time_m >= 60) {
                time_h++;
                time_m %= 60;
            }
            time = time_h + ":" + time_m + ":" + time_s;
            textView.setText(time);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_list);
        init();
        toolbar();
    }

    public void init() {
        record_btn = findViewById(R.id.recrod_btn);
        textView = findViewById(R.id.record_text);
        finish_btn = findViewById(R.id.record_finish_btn);
        list_btn = findViewById(R.id.record_list_btn);
        drawerLayout = findViewById(R.id.record_drawerLayout);
        left_list = findViewById(R.id.left_list);
    }

    public void toolbar() {
        record_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRecord == false) {
                    handler.post(runnable);
                    record_btn.setImageResource(R.drawable.ic_radio_button_checked);
                    isRecord = true;
                } else {
                    handler.removeCallbacks(runnable);
                    record_btn.setImageResource(R.drawable.ic_record_button_unchecked);
                    isRecord = false;
                }
            }
        });

        finish_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time_s = time_m = time_h = 0;
                time = null;
                textView.setText("00:00:00");
                record_btn.callOnClick();
            }
        });

        list_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(left_list);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(RecordActivity.this,MemoActivity.class);
        startActivity(intent);
    }
}
