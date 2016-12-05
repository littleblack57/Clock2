package com.example.littleblack57.clock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by littleblack57 on 2016/12/2.
 */

public class AlarmInitReceiver extends BroadcastReceiver {

    ClockOptionSelectAdapter clockOptionSelectAdapter;
    private static String FILENAME = "Data.java";
    public static final String BUNDLE_KEY_RINGTONETITLEURI
            ="com.littleblack57.android.com.ringtone_title_uri";



    @Override
    public void onReceive(Context context, Intent intent) {

        if (clockOptionSelectAdapter == null) {
            restoreData(context);
            clockOptionSelectAdapter = ClockOptionSelectAdapterFactory.getClockOptionSelectAdapter();
            for(int i = 0 ; i>=clockOptionSelectAdapter.getCount();i++){
                if(clockOptionSelectAdapter.getClockPowerArrayPosition(i)) {
                    int hour = clockOptionSelectAdapter.getHourArrayPosition(i);
                    int minute = clockOptionSelectAdapter.getMinuteArrayPosition(i);
                    clockOptionSelectAdapter.setHour(hour);
                    clockOptionSelectAdapter.setMinute(minute);
                    createClockAlarmRepeat(context);

                }
            }

            if (clockOptionSelectAdapter == null) {
                clockOptionSelectAdapter = ClockOptionSelectAdapterFactory.getClockOptionSelectAdapter();
                Log.d("666", "ClockOptionSelectAdapter Create");
            }
        }



    }

    private void restoreData(Context context) {

        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {

            fis = context.openFileInput(FILENAME);
            ois = new ObjectInputStream(fis);
            ClockOptionSelectAdapterFactory.setClockOptionSelectAdapter((ClockOptionSelectAdapter) ois.readObject());

        } catch (Exception e) {

            e.printStackTrace();
            Log.e("restoreErr",e.toString());

        } finally {

            if (ois != null) {

                try {

                    ois.close();

                } catch (IOException e) {

                    Log.e("restoreErr",e.toString());

                }

            }

        }

    }

    public void createClockAlarmRepeat(Context context) {

        boolean[] selected_array = clockOptionSelectAdapter.getSelected();
        int[] id_group = clockOptionSelectAdapter.getIdGroup();
        int id;

        for (int i = 0; i < selected_array.length; i++) {
            switch (i) {
                case 0:
                    if (selected_array[0]) {

                        Intent intent = new Intent();
                        intent.putExtra("msg", "ring");
                        intent.putExtra(BUNDLE_KEY_RINGTONETITLEURI,clockOptionSelectAdapter.getRingtoneUriString());
                        intent.setClass(context,PlayReceiver.class);
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                        calendar.set(Calendar.HOUR_OF_DAY, clockOptionSelectAdapter.getHour());
                        calendar.set(Calendar.MINUTE, clockOptionSelectAdapter.getMinute());
                        calendar.set(Calendar.SECOND, 0);

                        if (calendar.before(Calendar.getInstance())) {

                            long set_time = calendar.getTimeInMillis();
                            long now_time = Calendar.getInstance().getTimeInMillis();
                            int difference_day = (int) ((now_time - set_time) / (1000 * 60 * 60 * 24));
                            calendar.add(Calendar.HOUR, 7 * 24);

                            Log.d("test_time", "一  --->  " + String.valueOf(difference_day));
                            Date date = calendar.getTime();
                            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                            Log.d("time11", "pending is change  ---->" + df.format(date));

                        }

                        Date date = calendar.getTime();
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        Log.d("test_time", "pending is change  ---->" + df.format(date));

                        id = clockOptionSelectAdapter.getId();
                        clockOptionSelectAdapter.setIdArray(id);
                        id_group[0] = id;

                        Log.d("test_id", "id  ---->" + String.valueOf(id));

                        PendingIntent pending = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                        AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//                        alarm.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending);
                        alarm.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 7 * (1000 * 60 * 60 * 24), pending);

                    } else {


                    }
                    break;
                case 1:
                    if (selected_array[1]) {

                        Intent intent = new Intent();
                        intent.putExtra("msg", "ring");
                        intent.putExtra(BUNDLE_KEY_RINGTONETITLEURI,clockOptionSelectAdapter.getRingtoneUriString());
                        intent.setClass(context,PlayReceiver.class);
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
                        calendar.set(Calendar.HOUR_OF_DAY, clockOptionSelectAdapter.getHour());
                        calendar.set(Calendar.MINUTE, clockOptionSelectAdapter.getMinute());
                        calendar.set(Calendar.SECOND, 0);

                        if (calendar.before(Calendar.getInstance())) {

                            long set_time = calendar.getTimeInMillis();
                            long now_time = Calendar.getInstance().getTimeInMillis();
                            int difference_day = (int) ((now_time - set_time) / (1000 * 60 * 60 * 24));
                            calendar.add(Calendar.HOUR, 7 * 24);

                            Log.d("test_time", "二  ---> " + String.valueOf(difference_day));

                        }

                        Date date = calendar.getTime();
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        Log.d("test_time", "pending is change  ---->" + df.format(date));

                        id = clockOptionSelectAdapter.getId();
                        clockOptionSelectAdapter.setIdArray(id);
                        id_group[1] = id;

                        Log.d("test_id", "id  ---->" + String.valueOf(id));

                        PendingIntent pending = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                        AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//                        alarm.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending);
                        alarm.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 7 * (1000 * 60 * 60 * 24), pending);

                    } else {


                    }
                    break;
                case 2:
                    if (selected_array[2]) {

                        Intent intent = new Intent();
                        intent.putExtra("msg", "ring");
                        intent.putExtra(BUNDLE_KEY_RINGTONETITLEURI,clockOptionSelectAdapter.getRingtoneUriString());
                        intent.setClass(context,PlayReceiver.class);
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
                        calendar.set(Calendar.HOUR_OF_DAY, clockOptionSelectAdapter.getHour());
                        calendar.set(Calendar.MINUTE, clockOptionSelectAdapter.getMinute());
                        calendar.set(Calendar.SECOND, 0);

                        if (calendar.before(Calendar.getInstance())) {

                            long set_time = calendar.getTimeInMillis();
                            long now_time = Calendar.getInstance().getTimeInMillis();
                            int difference_day = (int) ((now_time - set_time) / (1000 * 60 * 60 * 24));
                            calendar.add(Calendar.HOUR, 7 * 24);

                            Log.d("test_time", "三  --->  " + String.valueOf(difference_day));

                        }

                        Date date = calendar.getTime();
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        Log.d("test_time", "pending is change  ---->" + df.format(date));

                        id = clockOptionSelectAdapter.getId();
                        clockOptionSelectAdapter.setIdArray(id);
                        id_group[2] = id;

                        Log.d("test_id", "id  ---->" + String.valueOf(id));

                        PendingIntent pending = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                        AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//                        alarm.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending);
                        alarm.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 7 * (1000 * 60 * 60 * 24), pending);

                    } else {


                    }
                    break;
                case 3:
                    if (selected_array[3]) {

                        Intent intent = new Intent();
                        intent.putExtra("msg", "ring");
                        intent.putExtra(BUNDLE_KEY_RINGTONETITLEURI,clockOptionSelectAdapter.getRingtoneUriString());
                        intent.setClass(context,PlayReceiver.class);
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
                        calendar.set(Calendar.HOUR_OF_DAY, clockOptionSelectAdapter.getHour());
                        calendar.set(Calendar.MINUTE, clockOptionSelectAdapter.getMinute());
                        calendar.set(Calendar.SECOND, 0);

                        if (calendar.before(Calendar.getInstance())) {

                            long set_time = calendar.getTimeInMillis();
                            long now_time = Calendar.getInstance().getTimeInMillis();
                            int difference_day = (int) ((now_time - set_time) / (1000 * 60 * 60 * 24));
                            calendar.add(Calendar.HOUR, 7 * 24);

                            Log.d("test_time", "四  --->  " + String.valueOf(difference_day));

                        }

                        Date date = calendar.getTime();
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        Log.d("test_time", "pending is change  ---->" + df.format(date));

                        id = clockOptionSelectAdapter.getId();
                        clockOptionSelectAdapter.setIdArray(id);
                        id_group[3] = id;

                        Log.d("test_id", "id  ---->" + String.valueOf(id));

                        PendingIntent pending = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                        AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//                        alarm.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending);
                        alarm.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 7 * (1000 * 60 * 60 * 24), pending);

                    } else {


                    }
                    break;
                case 4:
                    if (selected_array[4]) {

                        Intent intent = new Intent();
                        intent.putExtra("msg", "ring");
                        intent.putExtra(BUNDLE_KEY_RINGTONETITLEURI,clockOptionSelectAdapter.getRingtoneUriString());
                        intent.setClass(context,PlayReceiver.class);
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
                        calendar.set(Calendar.HOUR_OF_DAY, clockOptionSelectAdapter.getHour());
                        calendar.set(Calendar.MINUTE, clockOptionSelectAdapter.getMinute());
                        calendar.set(Calendar.SECOND, 0);

                        if (calendar.before(Calendar.getInstance())) {

                            long set_time = calendar.getTimeInMillis();
                            long now_time = Calendar.getInstance().getTimeInMillis();
                            int difference_day = (int) ((now_time - set_time) / (1000 * 60 * 60 * 24));
                            calendar.add(Calendar.HOUR, 7 * 24);

                            Log.d("test_time", String.valueOf(difference_day));

                        }

                        Date date = calendar.getTime();
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        Log.d("test_time", "pending is change  ---->" + df.format(date));

                        id = clockOptionSelectAdapter.getId();
                        clockOptionSelectAdapter.setIdArray(id);
                        id_group[4] = id;

                        Log.d("test_id", "id  ---->" + String.valueOf(id));

                        PendingIntent pending = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                        AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//                        alarm.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending);
                        alarm.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 7 * (1000 * 60 * 60 * 24), pending);

                    } else {


                    }

                    break;
                case 5:
                    if (selected_array[5]) {

                        Intent intent = new Intent();
                        intent.putExtra("msg", "ring");
                        intent.putExtra(BUNDLE_KEY_RINGTONETITLEURI,clockOptionSelectAdapter.getRingtoneUriString());
                        intent.setClass(context,PlayReceiver.class);
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
                        calendar.set(Calendar.HOUR_OF_DAY, clockOptionSelectAdapter.getHour());
                        calendar.set(Calendar.MINUTE, clockOptionSelectAdapter.getMinute());
                        calendar.set(Calendar.SECOND, 0);

                        if (calendar.before(Calendar.getInstance())) {

                            long set_time = calendar.getTimeInMillis();
                            long now_time = Calendar.getInstance().getTimeInMillis();
                            int difference_day = (int) ((now_time - set_time) / (1000 * 60 * 60 * 24));
                            calendar.add(Calendar.HOUR, 7 * 24);

                            Log.d("test_time", "六  --->  " + String.valueOf(difference_day));

                        }

                        Date date = calendar.getTime();
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        Log.d("test_time", "pending is change  ---->" + df.format(date));

                        id = clockOptionSelectAdapter.getId();
                        clockOptionSelectAdapter.setIdArray(id);
                        id_group[5] = id;

                        Log.d("test_id", "id  ---->" + String.valueOf(id));

                        PendingIntent pending = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                        AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//                        alarm.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending);
                        alarm.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 7 * (1000 * 60 * 60 * 24), pending);

                    } else {


                    }
                    break;
                case 6:
                    if (selected_array[6]) {

                        Intent intent = new Intent();
                        intent.putExtra("msg", "ring");
                        intent.putExtra(BUNDLE_KEY_RINGTONETITLEURI,clockOptionSelectAdapter.getRingtoneUriString());
                        intent.setClass(context,PlayReceiver.class);
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
                        calendar.set(Calendar.HOUR_OF_DAY, clockOptionSelectAdapter.getHour());
                        calendar.set(Calendar.MINUTE, clockOptionSelectAdapter.getMinute());
                        calendar.set(Calendar.SECOND, 0);

                        if (calendar.before(Calendar.getInstance())) {

                            long set_time = calendar.getTimeInMillis();
                            long now_time = Calendar.getInstance().getTimeInMillis();
                            int difference_day = (int) ((now_time - set_time) / (1000 * 60 * 60 * 24));
                            calendar.add(Calendar.HOUR, 7 * 24);

                            Log.d("test_time", "日  --->  " + String.valueOf(difference_day));

                        }

                        Date date = calendar.getTime();
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        Log.d("test_time", "pending is change  ---->" + df.format(date));

                        id = clockOptionSelectAdapter.getId();
                        clockOptionSelectAdapter.setIdArray(id);
                        id_group[6] = id;

                        Log.d("test_id", "id  ---->" + String.valueOf(id));

                        PendingIntent pending = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                        AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//                        alarm.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending);
                        alarm.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 7 * (1000 * 60 * 60 * 24), pending);

                    } else {


                    }
                    break;
            }

            clockOptionSelectAdapter.setIdGroup(id_group);
            clockOptionSelectAdapter.addIdGroupArray();

        }
    }

}