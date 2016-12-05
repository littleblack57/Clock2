package com.example.littleblack57.clock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    public static final String BUNDLE_KEY_DELETE_VISIBLE
            = "com.littleblack57.android.com.delete_visible";
    public static final String BUNDLE_KEY_RINGTONETITLEURI
            = "com.littleblack57.android.com.ringtone_title_uri";
    public static final String BUNDLE_KEY_VIBRATE
            = "com.littleblack57.android.com.vibrate";
    private static final int CREATE_CLOCK_REQUEST = 0;
    private static final int ITEM_CLOCK_SELECT = 1;
    private static final int Ringtone_SELECT = 2;
    private static ClockOptionSelectAdapter m_clockOptionSelectAdapter;
    private static String FILENAME = "Data.java";
    private RelativeLayout m_rl_main;
    private RecyclerView m_rv_clock;
    private RecyclerViewAdapter mAdapter;
    private TextView m_tv_text_clock;
    private int count;
    private CheckBox m_checkbox;
    private int m_position;
    private Context m_context;
    private int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        m_rv_clock = (RecyclerView) findViewById(R.id.rv_clock);
        m_tv_text_clock = (TextView) m_rv_clock.findViewById(R.id.tv_text_clock);
        m_checkbox = (CheckBox) m_rv_clock.findViewById(R.id.cb_clock_check);
        m_rv_clock.setHasFixedSize(true);
        m_rv_clock.addItemDecoration(new DividerItemDecoration(MainActivity.this, LinearLayoutManager.VERTICAL));

        if (m_clockOptionSelectAdapter == null) {
            restoreData();
            m_clockOptionSelectAdapter = ClockOptionSelectAdapterFactory.getClockOptionSelectAdapter();
            if (m_clockOptionSelectAdapter == null) {
                m_clockOptionSelectAdapter = ClockOptionSelectAdapterFactory.getClockOptionSelectAdapter();
                Log.d("666", "ClockOptionSelectAdapter Create");
            }
        }

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        m_rv_clock.setLayoutManager(llm);

        mAdapter = new RecyclerViewAdapter();
        m_rv_clock.setAdapter(mAdapter);


        mAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void OnClick(View view, int position) {

                final Intent intent = new Intent(MainActivity.this, Clock_Option.class);
                startActivityForResult(intent, ITEM_CLOCK_SELECT);
                m_clockOptionSelectAdapter.setButtonVisibilty(true);
                m_position = position;
                m_clockOptionSelectAdapter
                        .setIsChecked(m_clockOptionSelectAdapter.getIsCheckedArrayPosition(position));
                m_clockOptionSelectAdapter.setSelected(m_clockOptionSelectAdapter.getDateSelectedArrayPosition(position));
                m_clockOptionSelectAdapter.setHour(m_clockOptionSelectAdapter.getHourArrayPosition(position));
                m_clockOptionSelectAdapter.setMinute(m_clockOptionSelectAdapter.getMinuteArrayPosition(position));
                m_clockOptionSelectAdapter.setRingtoneUriString(m_clockOptionSelectAdapter.getRingtoneUriStringArrayPosition(position));
                m_clockOptionSelectAdapter.setRingtoneTitle(m_clockOptionSelectAdapter.getRingtoneTitleArrayPosition(position));

            }
        });


        m_context = this;


    }


    public void addClock(View view) {

        boolean[] date_selected = new boolean[]{false, false, false, false, false, false, false};
        m_clockOptionSelectAdapter.setButtonVisibilty(false);

        Calendar calendar = Calendar.getInstance();
        m_clockOptionSelectAdapter.setHour(calendar.get(Calendar.HOUR_OF_DAY));
        m_clockOptionSelectAdapter.setMinute(calendar.get(Calendar.MINUTE));
        m_clockOptionSelectAdapter.setClockPower(true);
        m_clockOptionSelectAdapter.setIsChecked(false);
        m_clockOptionSelectAdapter.setSelected(date_selected);
        m_clockOptionSelectAdapter.setRingtoneTitle(null);
        Intent intent = new Intent(MainActivity.this, Clock_Option.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.putExtra(BUNDLE_KEY_DELETE_VISIBLE, false);
        startActivityForResult(intent, CREATE_CLOCK_REQUEST);

//
//        Calendar cal = Calendar.getInstance();
//
//        Intent intent1 = new Intent(this,PlayReceiver.class);
//        intent1.putExtra("msg","ring");
//
//        PendingIntent pi = PendingIntent.getBroadcast(this,1,intent1,PendingIntent.FLAG_ONE_SHOT);
//
//        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//        am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),pi);

    }


    public void next(View view) {


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == CREATE_CLOCK_REQUEST) {
            if (resultCode == RESULT_OK) {

                Bundle bundle = data.getExtras();
                count = bundle.getInt(Clock_Option.BUNDLE_KEY_RECYCLERVIEW_COUNT);

                if (count == 1) {
                    mAdapter.addItem();
                    int a = m_clockOptionSelectAdapter.getCount();
                    m_clockOptionSelectAdapter.setCount(a + 1);
                    Log.d("666", String.valueOf(m_clockOptionSelectAdapter.getCount()));
                    m_clockOptionSelectAdapter = ClockOptionSelectAdapterFactory.getClockOptionSelectAdapter();
                    m_clockOptionSelectAdapter.addIsCheckedArray();
                    m_clockOptionSelectAdapter.addDateSelectedArray();
                    m_clockOptionSelectAdapter.addHourArray();
                    m_clockOptionSelectAdapter.addMinuteArray();
                    m_clockOptionSelectAdapter.addRingtoneTitleArray();
                    m_clockOptionSelectAdapter.addRingtoneUriStringArray();
                    m_clockOptionSelectAdapter.addClockPowerArray();

                    createClockAlarmRepeat();
                    saveData();

                }

            }


        } else if (requestCode == ITEM_CLOCK_SELECT) {
            if (resultCode == RESULT_OK) {

                Bundle bundle = data.getExtras();
                count = bundle.getInt(Clock_Option.BUNDLE_KEY_RECYCLERVIEW_COUNT);
                if (count == -1) {

                    deleteClockAlarm();


                    mAdapter.deleteItem(m_position);
                    mAdapter.notifyItemRemoved(m_position);
                    m_clockOptionSelectAdapter.setCount((m_clockOptionSelectAdapter.getCount()) - 1);
                    m_clockOptionSelectAdapter.deleteIsCheckedArrayPosition(m_position);
                    m_clockOptionSelectAdapter.deleteDateSelectedArrayPosition(m_position);
                    m_clockOptionSelectAdapter.deleteHourArray(m_position);
                    m_clockOptionSelectAdapter.deleteMinuteArray(m_position);
                    m_clockOptionSelectAdapter.removeIdArrayPosition(m_position);
                    m_clockOptionSelectAdapter.deleteRingtoneTitleArrayPosition(m_position);
                    m_clockOptionSelectAdapter.deleteRingtoneUriStringArrayPostion(m_position);
                    m_clockOptionSelectAdapter.deleteClockPowerArrayPosition(m_position);

                    saveData();

                } else if (count == 1) {

                    Boolean ischecked = bundle.getBoolean(Clock_Option.BUMDLE_KEY_CHECKBOX_CHECKED);

                    setClockAlarmRepeat();

                    m_clockOptionSelectAdapter.setIsCheckedArrayPosition(m_position, ischecked);
                    m_clockOptionSelectAdapter.setDateSelectedArrayPosition(m_position, m_clockOptionSelectAdapter.getSelected());
                    m_clockOptionSelectAdapter.setHourArrayPosition(m_position, m_clockOptionSelectAdapter.getHour());
                    m_clockOptionSelectAdapter.setMinuteArrayPosition(m_position, m_clockOptionSelectAdapter.getMinute());
                    m_clockOptionSelectAdapter.setRingtoneTitleArray(m_position);
                    m_clockOptionSelectAdapter.setRingtoneUriStringArrayPosition(m_position);
                    m_clockOptionSelectAdapter.setClockPowerArrayPosition(m_position, true);
                    mAdapter.notifyItemChanged(m_position);
                    saveData();

                }

            }
        }

    }


    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    private void saveData() {

        FileOutputStream fos;
        ObjectOutputStream oos = null;
        try {

            fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(ClockOptionSelectAdapterFactory.getClockOptionSelectAdapter());


        } catch (java.io.IOException e) {

            e.printStackTrace();
            Log.e("saveErr", e.toString());

        } finally {

            if (oos != null) {

                try {

                    oos.close();

                } catch (IOException e) {

                    Log.e("saveErr", e.toString());

                }

            }

        }

    }

    private void restoreData() {

        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {

            fis = openFileInput(FILENAME);
            ois = new ObjectInputStream(fis);
            ClockOptionSelectAdapterFactory.setClockOptionSelectAdapter((ClockOptionSelectAdapter) ois.readObject());

        } catch (Exception e) {

            e.printStackTrace();
            Log.e("restoreErr", e.toString());

        } finally {

            if (ois != null) {

                try {

                    ois.close();

                } catch (IOException e) {

                    Log.e("restoreErr", e.toString());

                }

            }

        }

    }

    public void setClockAlarm() {


        Intent intent = new Intent(this, PlayReceiver.class);
        intent.putExtra("msg", "ring");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, m_clockOptionSelectAdapter.getHour());
        calendar.set(Calendar.MINUTE, m_clockOptionSelectAdapter.getMinute());
        calendar.set(Calendar.SECOND, 0);

        if (calendar.before(Calendar.getInstance())) {

            calendar.add(Calendar.HOUR, 24);

        }


        id = m_clockOptionSelectAdapter.getId();
        m_clockOptionSelectAdapter.setIdArray(id);
        PendingIntent pending = PendingIntent.getBroadcast(this, id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarm = (AlarmManager) m_context.getSystemService(Context.ALARM_SERVICE);
        alarm.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending);

        Log.d("alarm", "alarm set successful"
                + String.valueOf(m_clockOptionSelectAdapter.getHour()) + " : "
                + String.valueOf(m_clockOptionSelectAdapter.getMinute()));

        Date date = calendar.getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Log.d("time11", df.format(date));


    }

    public void createClockAlarmRepeat() {

        boolean[] selected_array = m_clockOptionSelectAdapter.getSelected();
        int[] id_group = m_clockOptionSelectAdapter.getIdGroup();
        boolean date_selected = false;
        for (int i = 0; i < selected_array.length; i++) {
            switch (i) {
                case 0:
                    if (selected_array[0]) {

                        date_selected = true;
                        Intent intent = new Intent(this, PlayReceiver.class);
                        intent.putExtra("msg", "ring");
                        intent.putExtra(BUNDLE_KEY_RINGTONETITLEURI, m_clockOptionSelectAdapter.getRingtoneUriString());
                        intent.putExtra(BUNDLE_KEY_VIBRATE, m_clockOptionSelectAdapter.getIsChecked());
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                        calendar.set(Calendar.HOUR_OF_DAY, m_clockOptionSelectAdapter.getHour());
                        calendar.set(Calendar.MINUTE, m_clockOptionSelectAdapter.getMinute());
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

                        id = m_clockOptionSelectAdapter.getId();
                        m_clockOptionSelectAdapter.setIdArray(id);
                        id_group[0] = id;

                        Log.d("test_id", "id  ---->" + String.valueOf(id));

                        PendingIntent pending = PendingIntent.getBroadcast(this, id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                        AlarmManager alarm = (AlarmManager) m_context.getSystemService(Context.ALARM_SERVICE);
//                        alarm.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending);
                        alarm.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 7 * (1000 * 60 * 60 * 24), pending);

                    }
                    break;
                case 1:
                    if (selected_array[1]) {

                        date_selected = true;
                        Intent intent = new Intent(this, PlayReceiver.class);
                        intent.putExtra("msg", "ring");
                        intent.putExtra(BUNDLE_KEY_RINGTONETITLEURI, m_clockOptionSelectAdapter.getRingtoneUriString());
                        intent.putExtra(BUNDLE_KEY_VIBRATE, m_clockOptionSelectAdapter.getIsChecked());
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
                        calendar.set(Calendar.HOUR_OF_DAY, m_clockOptionSelectAdapter.getHour());
                        calendar.set(Calendar.MINUTE, m_clockOptionSelectAdapter.getMinute());
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

                        id = m_clockOptionSelectAdapter.getId();
                        m_clockOptionSelectAdapter.setIdArray(id);
                        id_group[1] = id;

                        Log.d("test_id", "id  ---->" + String.valueOf(id));

                        PendingIntent pending = PendingIntent.getBroadcast(this, id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                        AlarmManager alarm = (AlarmManager) m_context.getSystemService(Context.ALARM_SERVICE);
//                        alarm.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending);
                        alarm.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 7 * (1000 * 60 * 60 * 24), pending);

                    }
                    break;
                case 2:
                    if (selected_array[2]) {

                        date_selected = true;
                        Intent intent = new Intent(this, PlayReceiver.class);
                        intent.putExtra("msg", "ring");
                        intent.putExtra(BUNDLE_KEY_RINGTONETITLEURI, m_clockOptionSelectAdapter.getRingtoneUriString());
                        intent.putExtra(BUNDLE_KEY_VIBRATE, m_clockOptionSelectAdapter.getIsChecked());
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
                        calendar.set(Calendar.HOUR_OF_DAY, m_clockOptionSelectAdapter.getHour());
                        calendar.set(Calendar.MINUTE, m_clockOptionSelectAdapter.getMinute());
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

                        id = m_clockOptionSelectAdapter.getId();
                        m_clockOptionSelectAdapter.setIdArray(id);
                        id_group[2] = id;

                        Log.d("test_id", "id  ---->" + String.valueOf(id));

                        PendingIntent pending = PendingIntent.getBroadcast(this, id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                        AlarmManager alarm = (AlarmManager) m_context.getSystemService(Context.ALARM_SERVICE);
//                        alarm.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending);
                        alarm.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 7 * (1000 * 60 * 60 * 24), pending);

                    }
                    break;
                case 3:
                    if (selected_array[3]) {

                        date_selected = true;
                        Intent intent = new Intent(this, PlayReceiver.class);
                        intent.putExtra("msg", "ring");
                        intent.putExtra(BUNDLE_KEY_RINGTONETITLEURI, m_clockOptionSelectAdapter.getRingtoneUriString());
                        intent.putExtra(BUNDLE_KEY_VIBRATE, m_clockOptionSelectAdapter.getIsChecked());
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
                        calendar.set(Calendar.HOUR_OF_DAY, m_clockOptionSelectAdapter.getHour());
                        calendar.set(Calendar.MINUTE, m_clockOptionSelectAdapter.getMinute());
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

                        id = m_clockOptionSelectAdapter.getId();
                        m_clockOptionSelectAdapter.setIdArray(id);
                        id_group[3] = id;

                        Log.d("test_id", "id  ---->" + String.valueOf(id));

                        PendingIntent pending = PendingIntent.getBroadcast(this, id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                        AlarmManager alarm = (AlarmManager) m_context.getSystemService(Context.ALARM_SERVICE);
//                        alarm.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending);
                        alarm.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 7 * (1000 * 60 * 60 * 24), pending);

                    }
                    break;
                case 4:
                    if (selected_array[4]) {

                        date_selected = true;
                        Intent intent = new Intent(this, PlayReceiver.class);
                        intent.putExtra("msg", "ring");
                        intent.putExtra(BUNDLE_KEY_RINGTONETITLEURI, m_clockOptionSelectAdapter.getRingtoneUriString());
                        intent.putExtra(BUNDLE_KEY_VIBRATE, m_clockOptionSelectAdapter.getIsChecked());
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
                        calendar.set(Calendar.HOUR_OF_DAY, m_clockOptionSelectAdapter.getHour());
                        calendar.set(Calendar.MINUTE, m_clockOptionSelectAdapter.getMinute());
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

                        id = m_clockOptionSelectAdapter.getId();
                        m_clockOptionSelectAdapter.setIdArray(id);
                        id_group[4] = id;

                        Log.d("test_id", "id  ---->" + String.valueOf(id));

                        PendingIntent pending = PendingIntent.getBroadcast(this, id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                        AlarmManager alarm = (AlarmManager) m_context.getSystemService(Context.ALARM_SERVICE);
//                        alarm.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending);
                        alarm.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 7 * (1000 * 60 * 60 * 24), pending);

                    }
                    break;
                case 5:
                    if (selected_array[5]) {

                        date_selected = true;
                        Intent intent = new Intent(this, PlayReceiver.class);
                        intent.putExtra("msg", "ring");
                        intent.putExtra(BUNDLE_KEY_RINGTONETITLEURI, m_clockOptionSelectAdapter.getRingtoneUriString());
                        intent.putExtra(BUNDLE_KEY_VIBRATE, m_clockOptionSelectAdapter.getIsChecked());
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
                        calendar.set(Calendar.HOUR_OF_DAY, m_clockOptionSelectAdapter.getHour());
                        calendar.set(Calendar.MINUTE, m_clockOptionSelectAdapter.getMinute());
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

                        id = m_clockOptionSelectAdapter.getId();
                        m_clockOptionSelectAdapter.setIdArray(id);
                        id_group[5] = id;

                        Log.d("test_id", "id  ---->" + String.valueOf(id));

                        PendingIntent pending = PendingIntent.getBroadcast(this, id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                        AlarmManager alarm = (AlarmManager) m_context.getSystemService(Context.ALARM_SERVICE);
//                        alarm.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending);
                        alarm.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 7 * (1000 * 60 * 60 * 24), pending);

                    }
                    break;
                case 6:
                    if (selected_array[6]) {

                        date_selected = true;
                        Intent intent = new Intent(this, PlayReceiver.class);
                        intent.putExtra("msg", "ring");
                        intent.putExtra(BUNDLE_KEY_RINGTONETITLEURI, m_clockOptionSelectAdapter.getRingtoneUriString());
                        intent.putExtra(BUNDLE_KEY_VIBRATE, m_clockOptionSelectAdapter.getIsChecked());
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
                        calendar.set(Calendar.HOUR_OF_DAY, m_clockOptionSelectAdapter.getHour());
                        calendar.set(Calendar.MINUTE, m_clockOptionSelectAdapter.getMinute());
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

                        id = m_clockOptionSelectAdapter.getId();
                        m_clockOptionSelectAdapter.setIdArray(id);
                        id_group[6] = id;

                        Log.d("test_id", "id  ---->" + String.valueOf(id));

                        PendingIntent pending = PendingIntent.getBroadcast(this, id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                        AlarmManager alarm = (AlarmManager) m_context.getSystemService(Context.ALARM_SERVICE);
//                        alarm.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending);
                        alarm.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 7 * (1000 * 60 * 60 * 24), pending);

                    }
                    break;
            }


        }

        if (!date_selected) {

            Intent intent = new Intent(this, PlayReceiver.class);
            intent.putExtra("msg", "ring");
            intent.putExtra(BUNDLE_KEY_RINGTONETITLEURI, m_clockOptionSelectAdapter.getRingtoneUriString());
            intent.putExtra(BUNDLE_KEY_VIBRATE, m_clockOptionSelectAdapter.getIsChecked());
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            calendar.set(Calendar.HOUR_OF_DAY, m_clockOptionSelectAdapter.getHour());
            calendar.set(Calendar.MINUTE, m_clockOptionSelectAdapter.getMinute());
            calendar.set(Calendar.SECOND, 0);

            if (calendar.before(Calendar.getInstance())) {

                long set_time = calendar.getTimeInMillis();
                long now_time = Calendar.getInstance().getTimeInMillis();
                int difference_day = (int) ((now_time - set_time) / (1000 * 60 * 60 * 24));
                calendar.add(Calendar.HOUR, 24);

                Log.d("test_time", "一  --->  " + String.valueOf(difference_day));
                Date date = calendar.getTime();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Log.d("time11", "pending is change  ---->" + df.format(date));

            }

            Date date = calendar.getTime();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Log.d("test_time", "pending is change  ---->" + df.format(date));

            id = m_clockOptionSelectAdapter.getId();
            m_clockOptionSelectAdapter.setIdArray(id);
            id_group[0] = id;

            Log.d("test_id", "id  ---->" + String.valueOf(id));

            PendingIntent pending = PendingIntent.getBroadcast(this, id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
            AlarmManager alarm = (AlarmManager) m_context.getSystemService(Context.ALARM_SERVICE);
            alarm.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending);

        }
        m_clockOptionSelectAdapter.setIdGroup(id_group);
        m_clockOptionSelectAdapter.addIdGroupArray();
    }

    public void deleteClockAlarm() {

        int[] id_group = m_clockOptionSelectAdapter.getIdGroupArray(m_position);
        for (int id : id_group) {

            Intent intent = new Intent(this, PlayReceiver.class);
            Calendar calendar = Calendar.getInstance();
            AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
            alarmManager.cancel(pendingIntent);

        }

    }

    public void setClockAlarmRepeat() {

        boolean[] selected_array = m_clockOptionSelectAdapter.getSelected();
        int[] id_group = m_clockOptionSelectAdapter.getIdGroupArray(m_position);
        boolean date_selected = false;
        for (int i = 0; i < selected_array.length; i++) {
            switch (i) {
                case 0:
                    if (selected_array[0]) {

                        date_selected = true;
                        Intent intent = new Intent(this, PlayReceiver.class);
                        intent.putExtra("msg", "ring");
                        intent.putExtra(BUNDLE_KEY_RINGTONETITLEURI, m_clockOptionSelectAdapter.getRingtoneUriString());
                        intent.putExtra(BUNDLE_KEY_VIBRATE, m_clockOptionSelectAdapter.getIsChecked());
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                        calendar.set(Calendar.HOUR_OF_DAY, m_clockOptionSelectAdapter.getHour());
                        calendar.set(Calendar.MINUTE, m_clockOptionSelectAdapter.getMinute());
                        calendar.set(Calendar.SECOND, 0);

                        if (calendar.before(Calendar.getInstance())) {

                            long set_time = calendar.getTimeInMillis();
                            long now_time = Calendar.getInstance().getTimeInMillis();
                            int difference_day = (int) ((now_time - set_time) / (1000 * 60 * 60 * 24));
                            calendar.add(Calendar.HOUR, 7 * 24);

                            Log.d("test_time", String.valueOf(difference_day));

                        }


                        if (id_group[i] == 0) {

                            id = m_clockOptionSelectAdapter.getId();
                            m_clockOptionSelectAdapter.setIdArray(id);

                        } else {

                            id_group[i] = id;

                        }

                        PendingIntent pending = PendingIntent.getBroadcast(this, id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                        AlarmManager alarm = (AlarmManager) m_context.getSystemService(Context.ALARM_SERVICE);
//                        alarm.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending);
                        alarm.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 7 * (1000 * 60 * 60 * 24), pending);

                    } else {


                    }
                    break;
                case 1:
                    if (selected_array[1]) {

                        date_selected = true;
                        Intent intent = new Intent(this, PlayReceiver.class);
                        intent.putExtra("msg", "ring");
                        intent.putExtra(BUNDLE_KEY_RINGTONETITLEURI, m_clockOptionSelectAdapter.getRingtoneUriString());
                        intent.putExtra(BUNDLE_KEY_VIBRATE, m_clockOptionSelectAdapter.getIsChecked());
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
                        calendar.set(Calendar.HOUR_OF_DAY, m_clockOptionSelectAdapter.getHour());
                        calendar.set(Calendar.MINUTE, m_clockOptionSelectAdapter.getMinute());
                        calendar.set(Calendar.SECOND, 0);

                        if (calendar.before(Calendar.getInstance())) {

                            long set_time = calendar.getTimeInMillis();
                            long now_time = Calendar.getInstance().getTimeInMillis();
                            int difference_day = (int) ((now_time - set_time) / (1000 * 60 * 60 * 24));
                            calendar.add(Calendar.HOUR, 7 * 24);

                            Log.d("test_time", String.valueOf(difference_day));

                        }

                        if (id_group[i] == 0) {

                            id = m_clockOptionSelectAdapter.getId();
                            m_clockOptionSelectAdapter.setIdArray(id);

                        } else {

                            id_group[i] = id;

                        }
                        PendingIntent pending = PendingIntent.getBroadcast(this, id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                        AlarmManager alarm = (AlarmManager) m_context.getSystemService(Context.ALARM_SERVICE);
//                        alarm.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending);
                        alarm.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 7 * (1000 * 60 * 60 * 24), pending);

                    } else {


                    }
                    break;
                case 2:
                    if (selected_array[2]) {

                        date_selected = true;
                        Intent intent = new Intent(this, PlayReceiver.class);
                        intent.putExtra("msg", "ring");
                        intent.putExtra(BUNDLE_KEY_RINGTONETITLEURI, m_clockOptionSelectAdapter.getRingtoneUriString());
                        intent.putExtra(BUNDLE_KEY_VIBRATE, m_clockOptionSelectAdapter.getIsChecked());
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
                        calendar.set(Calendar.HOUR_OF_DAY, m_clockOptionSelectAdapter.getHour());
                        calendar.set(Calendar.MINUTE, m_clockOptionSelectAdapter.getMinute());
                        calendar.set(Calendar.SECOND, 0);

                        if (calendar.before(Calendar.getInstance())) {

                            long set_time = calendar.getTimeInMillis();
                            long now_time = Calendar.getInstance().getTimeInMillis();
                            int difference_day = (int) ((now_time - set_time) / (1000 * 60 * 60 * 24));
                            calendar.add(Calendar.HOUR, 7 * 24);

                            Log.d("test_time", String.valueOf(difference_day));

                        }

                        if (id_group[i] == 0) {

                            id = m_clockOptionSelectAdapter.getId();
                            m_clockOptionSelectAdapter.setIdArray(id);

                        } else {

                            id_group[i] = id;

                        }
                        PendingIntent pending = PendingIntent.getBroadcast(this, id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                        AlarmManager alarm = (AlarmManager) m_context.getSystemService(Context.ALARM_SERVICE);
//                        alarm.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending);
                        alarm.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 7 * (1000 * 60 * 60 * 24), pending);

                    } else {


                    }
                    break;
                case 3:
                    if (selected_array[3]) {

                        date_selected = true;
                        Intent intent = new Intent(this, PlayReceiver.class);
                        intent.putExtra("msg", "ring");
                        intent.putExtra(BUNDLE_KEY_RINGTONETITLEURI, m_clockOptionSelectAdapter.getRingtoneUriString());
                        intent.putExtra(BUNDLE_KEY_VIBRATE, m_clockOptionSelectAdapter.getIsChecked());
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
                        calendar.set(Calendar.HOUR_OF_DAY, m_clockOptionSelectAdapter.getHour());
                        calendar.set(Calendar.MINUTE, m_clockOptionSelectAdapter.getMinute());
                        calendar.set(Calendar.SECOND, 0);

                        if (calendar.before(Calendar.getInstance())) {

                            long set_time = calendar.getTimeInMillis();
                            long now_time = Calendar.getInstance().getTimeInMillis();
                            int difference_day = (int) ((now_time - set_time) / (1000 * 60 * 60 * 24));
                            calendar.add(Calendar.HOUR, 7 * 24);

                            Log.d("test_time", String.valueOf(difference_day));

                        }

                        if (id_group[i] == 0) {

                            id = m_clockOptionSelectAdapter.getId();
                            m_clockOptionSelectAdapter.setIdArray(id);

                        } else {

                            id_group[i] = id;

                        }
                        PendingIntent pending = PendingIntent.getBroadcast(this, id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                        AlarmManager alarm = (AlarmManager) m_context.getSystemService(Context.ALARM_SERVICE);
//                        alarm.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending);
                        alarm.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 7 * (1000 * 60 * 60 * 24), pending);

                    } else {


                    }
                    break;
                case 4:
                    if (selected_array[4]) {

                        date_selected = true;
                        Intent intent = new Intent(this, PlayReceiver.class);
                        intent.putExtra("msg", "ring");
                        intent.putExtra(BUNDLE_KEY_RINGTONETITLEURI, m_clockOptionSelectAdapter.getRingtoneUriString());
                        intent.putExtra(BUNDLE_KEY_VIBRATE, m_clockOptionSelectAdapter.getIsChecked());
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
                        calendar.set(Calendar.HOUR_OF_DAY, m_clockOptionSelectAdapter.getHour());
                        calendar.set(Calendar.MINUTE, m_clockOptionSelectAdapter.getMinute());
                        calendar.set(Calendar.SECOND, 0);

                        if (calendar.before(Calendar.getInstance())) {

                            long set_time = calendar.getTimeInMillis();
                            long now_time = Calendar.getInstance().getTimeInMillis();
                            int difference_day = (int) ((now_time - set_time) / (1000 * 60 * 60 * 24));
                            calendar.add(Calendar.HOUR, 7 * 24);

                            Log.d("test_time", String.valueOf(difference_day));

                        }

                        if (id_group[i] == 0) {

                            id = m_clockOptionSelectAdapter.getId();
                            m_clockOptionSelectAdapter.setIdArray(id);

                        } else {

                            id_group[i] = id;

                        }
                        PendingIntent pending = PendingIntent.getBroadcast(this, id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                        AlarmManager alarm = (AlarmManager) m_context.getSystemService(Context.ALARM_SERVICE);
//                        alarm.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending);
                        alarm.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 7 * (1000 * 60 * 60 * 24), pending);

                    } else {


                    }

                    break;
                case 5:
                    if (selected_array[5]) {

                        date_selected = true;
                        Intent intent = new Intent(this, PlayReceiver.class);
                        intent.putExtra("msg", "ring");
                        intent.putExtra(BUNDLE_KEY_RINGTONETITLEURI, m_clockOptionSelectAdapter.getRingtoneUriString());
                        intent.putExtra(BUNDLE_KEY_VIBRATE, m_clockOptionSelectAdapter.getIsChecked());
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
                        calendar.set(Calendar.HOUR_OF_DAY, m_clockOptionSelectAdapter.getHour());
                        calendar.set(Calendar.MINUTE, m_clockOptionSelectAdapter.getMinute());
                        calendar.set(Calendar.SECOND, 0);

                        if (calendar.before(Calendar.getInstance())) {

                            long set_time = calendar.getTimeInMillis();
                            long now_time = Calendar.getInstance().getTimeInMillis();
                            int difference_day = (int) ((now_time - set_time) / (1000 * 60 * 60 * 24));
                            calendar.add(Calendar.HOUR, 7 * 24);

                            Log.d("test_time", String.valueOf(difference_day));

                        }

                        if (id_group[i] == 0) {

                            id = m_clockOptionSelectAdapter.getId();
                            m_clockOptionSelectAdapter.setIdArray(id);

                        } else {

                            id_group[i] = id;

                        }
                        PendingIntent pending = PendingIntent.getBroadcast(this, id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                        AlarmManager alarm = (AlarmManager) m_context.getSystemService(Context.ALARM_SERVICE);
//                        alarm.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending);
                        alarm.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 7 * (1000 * 60 * 60 * 24), pending);

                    } else {


                    }
                    break;
                case 6:
                    if (selected_array[6]) {

                        date_selected = true;
                        Intent intent = new Intent(this, PlayReceiver.class);
                        intent.putExtra("msg", "ring");
                        intent.putExtra(BUNDLE_KEY_RINGTONETITLEURI, m_clockOptionSelectAdapter.getRingtoneUriString());
                        intent.putExtra(BUNDLE_KEY_VIBRATE, m_clockOptionSelectAdapter.getIsChecked());
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
                        calendar.set(Calendar.HOUR_OF_DAY, m_clockOptionSelectAdapter.getHour());
                        calendar.set(Calendar.MINUTE, m_clockOptionSelectAdapter.getMinute());
                        calendar.set(Calendar.SECOND, 0);

                        if (calendar.before(Calendar.getInstance())) {

                            long set_time = calendar.getTimeInMillis();
                            long now_time = Calendar.getInstance().getTimeInMillis();
                            int difference_day = (int) ((now_time - set_time) / (1000 * 60 * 60 * 24));
                            calendar.add(Calendar.HOUR, 7 * 24);

                            Log.d("test_time", String.valueOf(difference_day));

                        }

                        if (id_group[i] == 0) {

                            id = m_clockOptionSelectAdapter.getId();
                            m_clockOptionSelectAdapter.setIdArray(id);

                        } else {

                            id_group[i] = id;

                        }
                        PendingIntent pending = PendingIntent.getBroadcast(this, id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                        AlarmManager alarm = (AlarmManager) m_context.getSystemService(Context.ALARM_SERVICE);
//                        alarm.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending);
                        alarm.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 7 * (1000 * 60 * 60 * 24), pending);

                    } else {


                    }
                    break;
            }

        }

        if (!date_selected) {

            Intent intent = new Intent(this, PlayReceiver.class);
            intent.putExtra("msg", "ring");
            intent.putExtra(BUNDLE_KEY_RINGTONETITLEURI, m_clockOptionSelectAdapter.getRingtoneUriString());
            intent.putExtra(BUNDLE_KEY_VIBRATE, m_clockOptionSelectAdapter.getIsChecked());
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            calendar.set(Calendar.HOUR_OF_DAY, m_clockOptionSelectAdapter.getHour());
            calendar.set(Calendar.MINUTE, m_clockOptionSelectAdapter.getMinute());
            calendar.set(Calendar.SECOND, 0);

            if (calendar.before(Calendar.getInstance())) {

                long set_time = calendar.getTimeInMillis();
                long now_time = Calendar.getInstance().getTimeInMillis();
                int difference_day = (int) ((now_time - set_time) / (1000 * 60 * 60 * 24));
                calendar.add(Calendar.HOUR, 24);

                Log.d("test_time", "一  --->  " + String.valueOf(difference_day));
                Date date = calendar.getTime();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Log.d("time11", "pending is change  ---->" + df.format(date));

            }

            Date date = calendar.getTime();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Log.d("test_time", "pending is change  ---->" + df.format(date));

            id = m_clockOptionSelectAdapter.getId();
            m_clockOptionSelectAdapter.setIdArray(id);
            id_group[0] = id;

            Log.d("test_id", "id  ---->" + String.valueOf(id));

            PendingIntent pending = PendingIntent.getBroadcast(this, id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
            AlarmManager alarm = (AlarmManager) m_context.getSystemService(Context.ALARM_SERVICE);
            alarm.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending);

        }

        m_clockOptionSelectAdapter.setIdGroup(id_group);
        m_clockOptionSelectAdapter.setIdGroupArray(m_position, id_group);

    }

    public void clockPower(View view) {

        int position = (int) view.getTag();

        boolean b = m_clockOptionSelectAdapter.getClockPowerArrayPosition(position);
        Log.d("CheckBox", "No." + String.valueOf(position) + "  CheckBox Change--->" + String.valueOf(b));
        if (b) {

            turnOnClockAlarmRepeat(position);


        } else {

            turnOffClockAlarm(position);

        }


    }

    public void turnOnClockAlarmRepeat(int position) {

        boolean[] selected_array = m_clockOptionSelectAdapter.getDateSelectedArrayPosition(position);
        int[] id_group = m_clockOptionSelectAdapter.getIdGroupArray(position);
        int id;
        boolean date_selected = false;

        for (int i = 0; i < selected_array.length; i++) {
            switch (i) {
                case 0:
                    if (selected_array[0]) {

                        date_selected = true;
                        Intent intent = new Intent(this, PlayReceiver.class);
                        intent.putExtra("msg", "ring");
                        intent.putExtra(BUNDLE_KEY_RINGTONETITLEURI, m_clockOptionSelectAdapter.getRingtoneUriString());
                        intent.putExtra(BUNDLE_KEY_VIBRATE, m_clockOptionSelectAdapter.getIsChecked());
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                        calendar.set(Calendar.HOUR_OF_DAY, m_clockOptionSelectAdapter.getHourArrayPosition(position));
                        calendar.set(Calendar.MINUTE, m_clockOptionSelectAdapter.getMinuteArrayPosition(position));
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

                        id = id_group[0];

                        Log.d("test_id", "id  ---->" + String.valueOf(id));

                        PendingIntent pending = PendingIntent.getBroadcast(this, id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                        AlarmManager alarm = (AlarmManager) m_context.getSystemService(Context.ALARM_SERVICE);
//                        alarm.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending);
                        alarm.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 7 * (1000 * 60 * 60 * 24), pending);

                    }
                    break;
                case 1:
                    if (selected_array[1]) {

                        date_selected = true;
                        Intent intent = new Intent(this, PlayReceiver.class);
                        intent.putExtra("msg", "ring");
                        intent.putExtra(BUNDLE_KEY_RINGTONETITLEURI, m_clockOptionSelectAdapter.getRingtoneUriString());
                        intent.putExtra(BUNDLE_KEY_VIBRATE, m_clockOptionSelectAdapter.getIsChecked());
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
                        calendar.set(Calendar.HOUR_OF_DAY, m_clockOptionSelectAdapter.getHourArrayPosition(position));
                        calendar.set(Calendar.MINUTE, m_clockOptionSelectAdapter.getMinuteArrayPosition(position));
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

                        id = id_group[1];

                        Log.d("test_id", "id  ---->" + String.valueOf(id));

                        PendingIntent pending = PendingIntent.getBroadcast(this, id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                        AlarmManager alarm = (AlarmManager) m_context.getSystemService(Context.ALARM_SERVICE);
//                        alarm.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending);
                        alarm.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 7 * (1000 * 60 * 60 * 24), pending);

                    }
                    break;
                case 2:
                    if (selected_array[2]) {

                        date_selected = true;
                        Intent intent = new Intent(this, PlayReceiver.class);
                        intent.putExtra("msg", "ring");
                        intent.putExtra(BUNDLE_KEY_RINGTONETITLEURI, m_clockOptionSelectAdapter.getRingtoneUriString());
                        intent.putExtra(BUNDLE_KEY_VIBRATE, m_clockOptionSelectAdapter.getIsChecked());
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
                        calendar.set(Calendar.HOUR_OF_DAY, m_clockOptionSelectAdapter.getHourArrayPosition(position));
                        calendar.set(Calendar.MINUTE, m_clockOptionSelectAdapter.getMinuteArrayPosition(position));
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

                        id = id_group[2];

                        Log.d("test_id", "id  ---->" + String.valueOf(id));

                        PendingIntent pending = PendingIntent.getBroadcast(this, id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                        AlarmManager alarm = (AlarmManager) m_context.getSystemService(Context.ALARM_SERVICE);
//                        alarm.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending);
                        alarm.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 7 * (1000 * 60 * 60 * 24), pending);

                    }
                    break;
                case 3:
                    if (selected_array[3]) {

                        date_selected = true;
                        Intent intent = new Intent(this, PlayReceiver.class);
                        intent.putExtra("msg", "ring");
                        intent.putExtra(BUNDLE_KEY_RINGTONETITLEURI, m_clockOptionSelectAdapter.getRingtoneUriString());
                        intent.putExtra(BUNDLE_KEY_VIBRATE, m_clockOptionSelectAdapter.getIsChecked());
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
                        calendar.set(Calendar.HOUR_OF_DAY, m_clockOptionSelectAdapter.getHourArrayPosition(position));
                        calendar.set(Calendar.MINUTE, m_clockOptionSelectAdapter.getMinuteArrayPosition(position));
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

                        id = id_group[3];

                        Log.d("test_id", "id  ---->" + String.valueOf(id));

                        PendingIntent pending = PendingIntent.getBroadcast(this, id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                        AlarmManager alarm = (AlarmManager) m_context.getSystemService(Context.ALARM_SERVICE);
//                        alarm.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending);
                        alarm.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 7 * (1000 * 60 * 60 * 24), pending);

                    }
                    break;
                case 4:
                    if (selected_array[4]) {

                        date_selected = true;
                        Intent intent = new Intent(this, PlayReceiver.class);
                        intent.putExtra("msg", "ring");
                        intent.putExtra(BUNDLE_KEY_RINGTONETITLEURI, m_clockOptionSelectAdapter.getRingtoneUriString());
                        intent.putExtra(BUNDLE_KEY_VIBRATE, m_clockOptionSelectAdapter.getIsChecked());
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
                        calendar.set(Calendar.HOUR_OF_DAY, m_clockOptionSelectAdapter.getHourArrayPosition(position));
                        calendar.set(Calendar.MINUTE, m_clockOptionSelectAdapter.getMinuteArrayPosition(position));
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

                        id = id_group[4];

                        Log.d("test_id", "id  ---->" + String.valueOf(id));

                        PendingIntent pending = PendingIntent.getBroadcast(this, id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                        AlarmManager alarm = (AlarmManager) m_context.getSystemService(Context.ALARM_SERVICE);
//                        alarm.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending);
                        alarm.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 7 * (1000 * 60 * 60 * 24), pending);

                    }

                    break;
                case 5:
                    if (selected_array[5]) {

                        date_selected = true;
                        Intent intent = new Intent(this, PlayReceiver.class);
                        intent.putExtra("msg", "ring");
                        intent.putExtra(BUNDLE_KEY_RINGTONETITLEURI, m_clockOptionSelectAdapter.getRingtoneUriString());
                        intent.putExtra(BUNDLE_KEY_VIBRATE, m_clockOptionSelectAdapter.getIsChecked());
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
                        calendar.set(Calendar.HOUR_OF_DAY, m_clockOptionSelectAdapter.getHourArrayPosition(position));
                        calendar.set(Calendar.MINUTE, m_clockOptionSelectAdapter.getMinuteArrayPosition(position));
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

                        id = id_group[5];

                        Log.d("test_id", "id  ---->" + String.valueOf(id));

                        PendingIntent pending = PendingIntent.getBroadcast(this, id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                        AlarmManager alarm = (AlarmManager) m_context.getSystemService(Context.ALARM_SERVICE);
//                        alarm.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending);
                        alarm.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 7 * (1000 * 60 * 60 * 24), pending);

                    }
                    break;
                case 6:
                    if (selected_array[6]) {

                        date_selected = true;
                        Intent intent = new Intent(this, PlayReceiver.class);
                        intent.putExtra("msg", "ring");
                        intent.putExtra(BUNDLE_KEY_RINGTONETITLEURI, m_clockOptionSelectAdapter.getRingtoneUriString());
                        intent.putExtra(BUNDLE_KEY_VIBRATE, m_clockOptionSelectAdapter.getIsChecked());
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
                        calendar.set(Calendar.HOUR_OF_DAY, m_clockOptionSelectAdapter.getHourArrayPosition(position));
                        calendar.set(Calendar.MINUTE, m_clockOptionSelectAdapter.getMinuteArrayPosition(position));
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

                        id = id_group[6];

                        Log.d("test_id", "id  ---->" + String.valueOf(id));

                        PendingIntent pending = PendingIntent.getBroadcast(this, id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                        AlarmManager alarm = (AlarmManager) m_context.getSystemService(Context.ALARM_SERVICE);
//                        alarm.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending);
                        alarm.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 7 * (1000 * 60 * 60 * 24), pending);

                    }
                    break;
            }

            if (!date_selected) {

                Intent intent = new Intent(this, PlayReceiver.class);
                intent.putExtra("msg", "ring");
                intent.putExtra(BUNDLE_KEY_RINGTONETITLEURI, m_clockOptionSelectAdapter.getRingtoneUriString());
                intent.putExtra(BUNDLE_KEY_VIBRATE, m_clockOptionSelectAdapter.getIsChecked());
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                calendar.set(Calendar.HOUR_OF_DAY, m_clockOptionSelectAdapter.getHour());
                calendar.set(Calendar.MINUTE, m_clockOptionSelectAdapter.getMinute());
                calendar.set(Calendar.SECOND, 0);

                if (calendar.before(Calendar.getInstance())) {

                    long set_time = calendar.getTimeInMillis();
                    long now_time = Calendar.getInstance().getTimeInMillis();
                    int difference_day = (int) ((now_time - set_time) / (1000 * 60 * 60 * 24));
                    calendar.add(Calendar.HOUR, 24);

                    Log.d("test_time", "一  --->  " + String.valueOf(difference_day));
                    Date date = calendar.getTime();
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    Log.d("time11", "pending is change  ---->" + df.format(date));

                }

                Date date = calendar.getTime();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Log.d("test_time", "pending is change  ---->" + df.format(date));

                id = id_group[0];

                Log.d("test_id", "id  ---->" + String.valueOf(id));

                PendingIntent pending = PendingIntent.getBroadcast(this, id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                AlarmManager alarm = (AlarmManager) m_context.getSystemService(Context.ALARM_SERVICE);
                alarm.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending);

            }


        }
    }

    public void turnOffClockAlarm(int position) {

        int[] id_group = m_clockOptionSelectAdapter.getIdGroupArray(position);
        for (int id : id_group) {

            Intent intent = new Intent(this, PlayReceiver.class);
            Calendar calendar = Calendar.getInstance();
            AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
            alarmManager.cancel(pendingIntent);

        }

    }

}