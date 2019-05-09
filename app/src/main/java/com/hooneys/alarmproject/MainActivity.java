package com.hooneys.alarmproject;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.hooneys.alarmproject.DataObject.MyAlarm;
import com.hooneys.alarmproject.ListPack.AlarmAdapter;
import com.hooneys.alarmproject.MyBroadCast.MyAlarmBroadCast;
import com.hooneys.alarmproject.MyDatabases.DatabaseHandler;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private ArrayList<MyAlarm> main_list;

    private RecyclerView alarm_recycler;
    private AlarmAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    @Override
    protected void onStart() {
        super.onStart();

        getAlarmList();
        setUi();
    }

    private void setUi() {
        if(adapter == null){
            adapter = new AlarmAdapter(main_list);
            adapter.setOnClickItemViewListener(new AlarmAdapter.OnClickItemViewListener() {
                @Override
                public void onClicked(View view, MyAlarm start, MyAlarm end) {
                    deleteAlarm(start, end);
                }
            });
            alarm_recycler.setAdapter(adapter);
        }else{
            adapter.notifyDataSetChanged();
        }
    }

    private void deleteAlarm(final MyAlarm start, final MyAlarm end) {
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        alert.setTitle("알람 삭제").setMessage("해당 알람을 삭제하시겠습니까?")
                .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteAlarm(start);
                        deleteAlarm(end);
                        DatabaseHandler.delete(start.getAlarm_id());
                        DatabaseHandler.delete(end.getAlarm_id());
                        main_list.remove(start);
                        main_list.remove(end);
                        adapter.notifyDataSetChanged();
                    }
                }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }

    private void getAlarmList() {
        DatabaseHandler.getInstance(getApplicationContext());
        main_list.addAll(DatabaseHandler.selectAll());
        Log.d(TAG, "List : " + main_list.size());
    }

    private void init() {
        main_list = new ArrayList<>();
        alarm_recycler = findViewById(R.id.main_recycler);
        alarm_recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL, false));
        alarm_recycler.setHasFixedSize(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.main_alarm_add:
                addAlarm();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addAlarm() {
        startActivity(new Intent(getApplicationContext(), AddActivity.class));
    }

    private void deleteAlarm(MyAlarm item){
        Intent intent = new Intent(getApplicationContext(), MyAlarmBroadCast.class);
        PendingIntent sender = PendingIntent.getBroadcast(
                getApplicationContext(),
                item.getAlarm_id(),   //Unique!
                intent,
                0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.cancel(sender);
    }
}
