package com.glriverside.chyqing.memorandum.Activity;

import android.app.AlarmManager;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import com.glriverside.chyqing.memorandum.Adapter.MemoAdapter;
import com.glriverside.chyqing.memorandum.Contract.MemoContract;
import com.glriverside.chyqing.memorandum.Manager.MemoOpenHelper;
import com.glriverside.chyqing.memorandum.Service.VoiceMemoService;
import com.glriverside.chyqing.memorandum.Values.MemoValues;
import com.glriverside.chyqing.memorandum.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MemoActivity extends AppCompatActivity implements DialogInterface.OnClickListener{

    private MemoOpenHelper memoOpenHelper;
    private ListView lvMemo;
    private ImageButton add_btn;
    private BottomNavigationView bottomNavigationView;
    private MemoAdapter memoAdapter;
    private AlarmManager alarmManager;
    List<MemoValues> memoValuesList;

    public static final String MODEL = "false";
    public static final String DATA_URI =
            "com.glriverside.chyqing.memorandum.Activity.DATA_URI";

    public static MemoActivity context = null;
    private MediaPlayer player = new MediaPlayer();
    PowerManager.WakeLock mWakeLock;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Thread(new Runnable() {
            @Override
            public void run() {
                //此处运行耗时任务
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(MemoActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });

            }
        }).start();

        setContentView(R.layout.memo_list);
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=5dd504a7");
        memoOpenHelper = new MemoOpenHelper(MemoActivity.this);
        lvMemo = findViewById(R.id.lv_memo_list);
        add_btn = findViewById(R.id.memo_add_btn);
        bottomNavigationView = findViewById(R.id.bottom_nav);
        alarmManager = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);

        initDb();
        initNotification();
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MemoActivity.this, MemoEditActivity.class);
                intent.putExtra(MemoActivity.MODEL, "false");
                startActivity(intent);
            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.home_bottom_record:
                        Intent intent = new Intent(MemoActivity.this,RecordActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.home_bottom_search:
                        Intent intent1 = new Intent(MemoActivity.this,SearchActivity.class);
                        startActivity(intent1);
                        break;
                }
                return false;
            }
        });

        //点击条例查询
        lvMemo.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MemoActivity.this, MemoEditActivity.class);
                MemoValues memoValues = (MemoValues) lvMemo.getItemAtPosition(i);
                intent.putExtra(MemoContract.MemoEntry._ID, memoValues.getId().toString());
                intent.putExtra(MemoContract.MemoEntry.COLUMN_NAME_TITLE, memoValues.getTitle());
                intent.putExtra(MemoContract.MemoEntry.COLUMN_NAME_DATE, memoValues.getDate());
                intent.putExtra(MemoContract.MemoEntry.COLUMN_NAME_CONTENT_PATH, memoValues.getContent());
                intent.putExtra(MemoContract.MemoEntry.COLUMN_NAME_ALARM, ""+memoValues.getAlarm());
                intent.putExtra(MemoContract.MemoEntry.COLUMN_NAME_ALARM_TIME, memoValues.getAlarmTime());
                intent.putExtra(MemoContract.MemoEntry.COLUMN_NAME_TODO, ""+memoValues.getToDo());
                intent.putExtra(MemoActivity.MODEL, "true");
                startActivity(intent);
            }
        });

        //长按删除
        lvMemo.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                final MemoValues values = (MemoValues)lvMemo.getItemAtPosition(i);
                new AlertDialog.Builder(MemoActivity.this)
                        .setTitle("提示框")
                        .setMessage("是否删除？")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                SQLiteDatabase db = memoOpenHelper.getWritableDatabase();
                                db.delete(MemoContract.MemoEntry.TABLE_NAME,
                                        MemoContract.MemoEntry._ID + "=?",
                                        new String[]{String.valueOf(values.getId())});
                                db.close();
                                memoAdapter.removeItem(i);
                                lvMemo.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        memoAdapter.notifyDataSetChanged();
                                    }
                                });
                            }
                        }).setNegativeButton("否", null).show();
                return true;
            }
        });

       /* PowerManager powerManager = (PowerManager)getSystemService(Context.POWER_SERVICE);

        mWakeLock = powerManager.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.FULL_WAKE_LOCK, ":AlertDialog");
        mWakeLock.acquire();

        KeyguardManager keyguardManager = (KeyguardManager)getSystemService(KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock keyguardLock = keyguardManager.newKeyguardLock("AlertDialog");
        keyguardLock.disableKeyguard();
        context = this;

        Uri localUri = RingtoneManager.getActualDefaultRingtoneUri(context, RingtoneManager.TYPE_ALARM);
        if ((player != null)&&(localUri != null)){
            try {
                player.setDataSource(context, localUri);
                player.prepare();
                player.setLooping(false);
                player.start();

                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);

                builder.setTitle("提醒");
                builder.setMessage(getIntent().getStringExtra("content"));
                builder.setPositiveButton("查看", this);
                builder.setNegativeButton("忽略", this);
                builder.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        switch (i){
            case DialogInterface.BUTTON1 :
                Intent intent = new Intent(MemoActivity.this, MemoEditActivity.class);
                startActivity(intent);
                finish();
            case DialogInterface.BUTTON2:
                player.stop();
                finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    public void initDb() {
        //创建一个MemoValues的List，保存数据库的数据
        memoValuesList = new ArrayList<>();

        //获取一个可读的数据库对象
        SQLiteDatabase db = memoOpenHelper.getReadableDatabase();

        //查询
        Cursor cursor = db.query(MemoContract.MemoEntry.TABLE_NAME, null, null,
                null, null, null, MemoContract.MemoEntry.COLUMN_NAME_DATE + " DESC ");
        if (cursor.moveToFirst()) {
            MemoValues values;
            while (!cursor.isAfterLast()) {

                //实例化一个MemoValues
                values = new MemoValues();

                //将数据库中的数据赋给values
                values.setId(Integer.valueOf(cursor.getString(cursor.getColumnIndex(MemoContract.MemoEntry._ID))));
                values.setTitle(cursor.getString(cursor.getColumnIndex(MemoContract.MemoEntry.COLUMN_NAME_TITLE)));
                values.setDate(cursor.getString(cursor.getColumnIndex(MemoContract.MemoEntry.COLUMN_NAME_DATE)));
                values.setContent(cursor.getString(cursor.getColumnIndex(MemoContract.MemoEntry.COLUMN_NAME_CONTENT_PATH)));
                values.setAlarm(Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(MemoContract.MemoEntry.COLUMN_NAME_ALARM))));
                values.setAlarmTime(cursor.getString(cursor.getColumnIndex(MemoContract.MemoEntry.COLUMN_NAME_ALARM_TIME)));
                values.setToDo(Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(MemoContract.MemoEntry.COLUMN_NAME_TODO))));

                memoValuesList.add(values);
                cursor.moveToNext();

            }
        }
        cursor.close();
        db.close();

        //设置适配器
        memoAdapter = new MemoAdapter(memoValuesList, MemoActivity.this, R.layout.memo_list_item);
        lvMemo.setAdapter(memoAdapter);
    }


    //保存后更新列表信息
    @Override
    protected void onResume() {
        super.onResume();
        initDb();
    }

    public void initAlarm(){

    }

    public void initNotification(){
        Intent serviceIntent = new Intent(MemoActivity.this, VoiceMemoService.class);

        for (MemoValues i : memoValuesList){
            if (i.getToDo() == true){
                serviceIntent.putExtra("TITLE", i.getTitle());
                serviceIntent.putExtra("CONTENT", i.getContent());
                serviceIntent.putExtra("DATE", i.getDate());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    startForegroundService(serviceIntent);
                }
                return;
            }
        }

    }
}
