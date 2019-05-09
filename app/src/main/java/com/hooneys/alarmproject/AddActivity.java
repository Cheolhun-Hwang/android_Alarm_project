package com.hooneys.alarmproject;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import com.hooneys.alarmproject.DataObject.MyAlarm;
import com.hooneys.alarmproject.MyBroadCast.MyAlarmBroadCast;
import com.hooneys.alarmproject.MyDatabases.DatabaseHandler;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddActivity extends AppCompatActivity {
    private static final String TAG = AddActivity.class.getSimpleName();
    private TimePicker start, end;
    private Button addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        init();
    }

    private void init() {
        start = findViewById(R.id.add_start_time);
        end = findViewById(R.id.add_end_time);
        addBtn = findViewById(R.id.add_alarm_btn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    int s_h = start.getHour();
                    int s_m = start.getMinute();
                    int e_h = end.getHour();
                    int e_m = end.getMinute();
                    Log.d(TAG, "start : " + s_h+":"+s_m);
                    Log.d(TAG, "end : " + e_h+":"+e_m);

                    SimpleDateFormat dateFormat = new SimpleDateFormat("hhmmssss");
                    int alarm_id = Integer.valueOf(dateFormat.format(new Date(System.currentTimeMillis())));

                    addAlarm(s_h, s_m, "start", alarm_id);
                    addAlarm(e_h, e_m, "end", alarm_id+1);

                    finish();
                }
            }
        });
    }

    private void addAlarm(int h, int m, String action, int alarm_id) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, h);
        cal.set(Calendar.MINUTE, m);
        cal.set(Calendar.SECOND, 0);
        Intent intent = new Intent(getApplicationContext(), MyAlarmBroadCast.class);
        intent.putExtra("action", action);
        PendingIntent sender = PendingIntent.getBroadcast(
                getApplicationContext(),
                alarm_id,   //Unique!
                intent,
                0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 10*1000, sender); //AlarmManager.INTERVAL_DAY

        MyAlarm item = new MyAlarm();
        item.setAlarm_id(alarm_id);
        item.setAlarm_op( (action.equals("start")?0:1) );
        item.setAlarm_time(h+":"+m);
        DatabaseHandler.insert(item);
    }
}
