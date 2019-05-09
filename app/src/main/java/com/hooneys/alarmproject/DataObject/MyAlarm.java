package com.hooneys.alarmproject.DataObject;

public class MyAlarm {
    private int alarm_id;
    private int alarm_op;
    private String alarm_time;

    public MyAlarm() {
        this.alarm_id = -1;
        this.alarm_op = -1;
        this.alarm_time = "None";
    }

    public MyAlarm(int alarm_id, int alarm_op, String alarm) {
        this.alarm_id = alarm_id;
        this.alarm_op = alarm_op;
        this.alarm_time = alarm;
    }

    public int getAlarm_id() {
        return alarm_id;
    }

    public void setAlarm_id(int alarm_id) {
        this.alarm_id = alarm_id;
    }

    public int getAlarm_op() {
        return alarm_op;
    }

    public void setAlarm_op(int alarm_op) {
        this.alarm_op = alarm_op;
    }

    public String getAlarm_time() {
        return alarm_time;
    }

    public void setAlarm_time(String alarm_time) {
        this.alarm_time = alarm_time;
    }
}
