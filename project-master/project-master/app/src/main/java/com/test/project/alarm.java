package com.test.project;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

public class alarm extends AppCompatActivity implements View.OnClickListener{

    private int MAX_CODE = 0;
    private int now_hour = 0;
    private int now_min = 0;
    private boolean edit = false;
    private String item_name = "";
    String[] timef = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        TimePicker timePicker = findViewById(R.id.timePicker);
        EditText editText = findViewById(R.id.editText);

        timePicker.setIs24HourView(true);
        now_hour = timePicker.getCurrentHour();
        now_min = timePicker.getCurrentMinute();

        edit = getIntent().getBooleanExtra("Edit", false);
        SharedPreferences pref = this.getSharedPreferences("alarm_list", Context.MODE_PRIVATE);
        MAX_CODE = pref.getInt("MAX_CODE", 0);

        Log.d("show", Integer.toString(MAX_CODE));

        if (edit){
            item_name = getIntent().getStringExtra("item_name");
            editText.setText(item_name);
            timef = getIntent().getStringExtra("time").split(":");

            timePicker.setCurrentHour(Integer.parseInt(timef[0]));
            timePicker.setCurrentMinute(Integer.parseInt(timef[1]));
        }
        else {
            timePicker.setCurrentHour(0);
            timePicker.setCurrentMinute(0);
        }

        // Set Onclick Listener.
        findViewById(R.id.setBtn).setOnClickListener(this);
        findViewById(R.id.cancelBtn).setOnClickListener(this);
    }

    public void setAlarm(int now_hour, int now_min, int hour, int min, PendingIntent intent){
        boolean next_day = false;
        AlarmManager alarm = (AlarmManager) getSystemService(ALARM_SERVICE);

        min += now_min;
        if (min >= 60){
            min -= 60;
            hour += 1;
        }

        hour += now_hour;
        if (hour >= 24){
            hour -= 24;
            next_day = true;
        }

        // Create time.
        Calendar startTime = Calendar.getInstance();
        if (next_day){
            startTime.set(Calendar.DATE, Calendar.getInstance().get(Calendar.DATE)+1);
        }
        startTime.set(Calendar.HOUR_OF_DAY, hour);
        startTime.set(Calendar.MINUTE, min);
        startTime.set(Calendar.SECOND, 0);
        startTime.set(Calendar.MILLISECOND, 0);

        // Set alarm.
        // set(type, milliseconds, intent)
        alarm.set(AlarmManager.RTC_WAKEUP, startTime.getTimeInMillis(), intent);
    }

    @Override
    public void onClick(View view) {
        EditText editText = findViewById(R.id.editText);
        TimePicker timePicker = findViewById(R.id.timePicker);
        int hour = timePicker.getCurrentHour();
        int minute = timePicker.getCurrentMinute();

        // Set notificationId & text.
        Intent intent = new Intent(alarm.this, alarmreceiver.class);
        intent.putExtra("todo", "該吃" + editText.getText().toString() + "咯！");
        intent.putExtra("time_hour", hour);
        intent.putExtra("time_min", minute);

        // getBroadcast(context, requestCode, intent, flags)
        PendingIntent alarmIntent;
        String data = Integer.toString(hour) + ":" + Integer.toString(minute) + ":";
        if (edit){
            intent.putExtra("notificationId", Integer.parseInt(timef[2]));
            alarmIntent = PendingIntent.getBroadcast(alarm.this, Integer.parseInt(timef[2]), intent, 0);
            data += timef[2];
        }
        else{
            MAX_CODE += 1;
            intent.putExtra("notificationId", MAX_CODE);
            alarmIntent = PendingIntent.getBroadcast(alarm.this, MAX_CODE, intent, 0);
            data += Integer.toString(MAX_CODE);
        }


        switch (view.getId()) {
            case R.id.setBtn:
                SharedPreferences pref = this.getSharedPreferences("alarm_list", Context.MODE_PRIVATE);
                Set<String> set = new HashSet<String>(pref.getStringSet("alarm", new HashSet<String>()));

                if (!edit && set.contains(editText.getText().toString())){
                    Toast.makeText(this,"same thing has been added", Toast.LENGTH_SHORT).show();
                    return;
                }

                setAlarm(now_hour, now_min, hour, minute, alarmIntent);

                set.add(editText.getText().toString());

                Log.d("alarm_set", set.toString());

                SharedPreferences.Editor editor = pref.edit();
                editor.putStringSet("alarm", set);
                editor.putString(editText.getText().toString(), data);
                editor.putInt("MAX_CODE",MAX_CODE);
                editor.commit();

                Toast.makeText(this, "Done!", Toast.LENGTH_SHORT).show();
                finish();
                break;

            case R.id.cancelBtn:
                SharedPreferences pref1 = this.getSharedPreferences("alarm_list", Context.MODE_PRIVATE);
                Set<String> set1 = new HashSet<String>(pref1.getStringSet("alarm", new HashSet<String>()));

                set1.remove(item_name);

                SharedPreferences.Editor editor1 = pref1.edit();
                editor1.putStringSet("alarm", set1);
                editor1.remove(item_name);
                editor1.putInt("MAX_CODE",MAX_CODE-1);
                editor1.commit();

                AlarmManager alarm = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarm.cancel(alarmIntent);
                Toast.makeText(this, "Canceled.", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }

    }
}
