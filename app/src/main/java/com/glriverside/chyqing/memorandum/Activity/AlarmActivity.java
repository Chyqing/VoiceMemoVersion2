package com.glriverside.chyqing.memorandum.Activity;

import android.app.AlertDialog;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.View;

import com.glriverside.chyqing.memorandum.R;

import java.io.IOException;

import androidx.appcompat.app.AppCompatActivity;

public class AlarmActivity extends AppCompatActivity implements DialogInterface.OnClickListener {
    public static AlarmActivity context = null;
    private MediaPlayer player = new MediaPlayer();
    PowerManager.WakeLock mWakeLock;

    @Override
    protected  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.memo_list);

        PowerManager powerManager = (PowerManager)getSystemService(Context.POWER_SERVICE);

        mWakeLock = powerManager.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP |
                                            PowerManager.FULL_WAKE_LOCK, ":AlertDialog");
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

                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("提醒");
                builder.setMessage(getIntent().getStringExtra("content"));
                builder.setPositiveButton("查看", this);
                builder.setNegativeButton("忽略", this);
                builder.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        switch (i){
            case DialogInterface.BUTTON1 :
                Intent intent = new Intent(AlarmActivity.this, MemoEditActivity.class);
                startActivity(intent);
                finish();
            case DialogInterface.BUTTON2:
                player.stop();
                finish();
        }
    }
}
