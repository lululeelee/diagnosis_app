package com.test.project;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

public class alarmreceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        // Get id & message from intent.
        int notificationId = intent.getIntExtra("notificationId", 0);
        String message = intent.getStringExtra("todo");
        int repeat_time_hour = intent.getIntExtra("time_hour", 0);
        int repeat_time_min = intent.getIntExtra("time_min", 0);

        Log.d("debug/time:", Integer.toString(repeat_time_min));

        // When notification is tapped, call MainActivity.
        Intent mainIntent = new Intent(context, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, mainIntent, 0);

        NotificationManager myNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Prepare notification.
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context.getApplicationContext(), "1")
        .setSmallIcon(android.R.drawable.ic_dialog_info)
        .setContentTitle("吃藥提醒")
        .setContentText(message)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "1";
            NotificationChannel channel = new NotificationChannel(channelId,
                    "吃藥提醒",
                    NotificationManager.IMPORTANCE_DEFAULT);
            myNotificationManager.createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }

        // Notify
        myNotificationManager.notify(notificationId, mBuilder.build());
        Log.d("notify", "notification");

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        notificationManager.notify(notificationId, mBuilder.build());

        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, notificationId, intent, 0);
        setAlarm(context, Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), repeat_time_hour, repeat_time_min, alarmIntent);
    }

    public void setAlarm(Context context, int now_hour, int now_min, int hour, int min, PendingIntent intent){
        boolean next_day = false;
        AlarmManager alarm = (AlarmManager) context.getSystemService(ALARM_SERVICE);

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
}


