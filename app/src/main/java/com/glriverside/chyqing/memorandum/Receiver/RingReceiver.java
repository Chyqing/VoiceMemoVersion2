package com.glriverside.chyqing.memorandum.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.glriverside.chyqing.memorandum.Activity.MemoActivity;

public class RingReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if ("com.glriverside.chyqing.memorandum.Receiver.RING".equals(intent.getAction())){
            Log.i("Alarm", "收到闹铃");
            Intent intent1 = new Intent(context, MemoActivity.class);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent1);
        }
    }
}
