package com.hooneys.alarmproject.MyBroadCast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyAlarmBroadCast extends BroadcastReceiver {
    private static final String TAG = MyAlarmBroadCast.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "Intent");
        Log.d(TAG, "Alarm!!");
        Log.d(TAG, "Action : " + intent.getStringExtra("action"));
    }
}
