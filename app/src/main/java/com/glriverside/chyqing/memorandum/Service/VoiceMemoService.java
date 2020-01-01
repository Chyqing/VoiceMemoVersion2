package com.glriverside.chyqing.memorandum.Service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;

import com.glriverside.chyqing.memorandum.Activity.MemoEditActivity;
import com.glriverside.chyqing.memorandum.R;

import androidx.core.app.NotificationCompat;

public class VoiceMemoService extends Service {

    private static final int ONGOING_NOTIFICATION_ID = 1001;
    private static final String CHANNEL_ID = "Memo channel";
    NotificationManager notificationManager;

    public VoiceMemoService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){

        String title = intent.getStringExtra("TITLE");
        String content = intent.getStringExtra("CONTENT");
        String date = intent.getStringExtra("DATE");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            notificationManager =
                    (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    "Memo Channel",
                    NotificationManager.IMPORTANCE_HIGH);

            if (notificationManager != null){
                notificationManager.createNotificationChannel(channel);
            }
        }

        Intent notificationIntent = new Intent(getApplicationContext(),
                MemoEditActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),
                0,
                notificationIntent,
                0);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(getApplicationContext(),
                        CHANNEL_ID);

        Notification notification = builder.setContentTitle(title)
                .setContentText(content)
                .setContentText(date)
                .setSmallIcon(R.drawable.logo)
                .setContentIntent(pendingIntent).build();

        startForeground(ONGOING_NOTIFICATION_ID, notification);

        return super.onStartCommand(intent,flags,startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
