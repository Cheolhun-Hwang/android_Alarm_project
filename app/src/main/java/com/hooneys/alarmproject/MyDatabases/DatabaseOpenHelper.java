package com.hooneys.alarmproject.MyDatabases;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseOpenHelper extends SQLiteOpenHelper {
    private final String TAG = DatabaseOpenHelper.class.getSimpleName();

    public DatabaseOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DatabaseOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "DB onCreate...");
        String create_table =
                "CREATE TABLE IF NOT EXISTS alarm_list("+
                        "alarm_num       INTEGER     PRIMARY KEY AUTOINCREMENT, "+
                        "alarm_id        int         NOT NULL, "+
                        "alarm_op        int         NOT NULL, "+
                        "alarm_time      text        NOT NULL "+
                        ")";
        db.execSQL(create_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String remove_table =
                "DROP TABLE IF EXISTS alarm_list";
        db.execSQL(remove_table);
        onCreate(db);
    }
}
