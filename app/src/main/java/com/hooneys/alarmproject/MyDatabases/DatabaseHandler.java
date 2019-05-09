package com.hooneys.alarmproject.MyDatabases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hooneys.alarmproject.DataObject.MyAlarm;

import java.util.ArrayList;

public class DatabaseHandler {
    private static final String db_name = "main_database.sqlite";
    public static DatabaseHandler instance;
    public DatabaseOpenHelper helper;

    public static DatabaseHandler getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHandler();
            instance.helper = new DatabaseOpenHelper(context, db_name, null, 1);
        }
        return instance;
    }

    public static void insert(MyAlarm item){
        SQLiteDatabase db = instance.helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("alarm_id", item.getAlarm_id());
        values.put("alarm_op", item.getAlarm_op());
        values.put("alarm_time", item.getAlarm_time());
        db.insert("alarm_list", null, values);
    }

    public static void delete(int alarm_id){
        SQLiteDatabase db = instance.helper.getWritableDatabase();
        db.delete("alarm_list", "alarm_id=?", new String[]{(alarm_id+"")});
    }

    public static void deleteAll(){
        SQLiteDatabase db = instance.helper.getWritableDatabase();
        db.delete("alarm_list", null, null);
    }

    public static ArrayList<MyAlarm> selectAll(){
        SQLiteDatabase db = instance.helper.getWritableDatabase();
        Cursor cursor = db.query("alarm_list", null, null, null, null, null, null);
        ArrayList<MyAlarm> temp = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                MyAlarm item = new MyAlarm();
                item.setAlarm_id(cursor.getInt(1));
                item.setAlarm_op(cursor.getInt(2));
                item.setAlarm_time(cursor.getString(3));
                temp.add(item);
            }while (cursor.moveToNext());
        }
        return temp;
    }
}
