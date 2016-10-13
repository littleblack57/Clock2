package com.example.littleblack57.clock;

import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class MainActivity extends AppCompatActivity {

     private static final int CREATE_CLOCK_REQUEST = 0;
    private static final int ITEM_CLOCK_SELECT = 1;
    private static final int Ringtone = 2;
    private final String TAG = "ok";
    private Button m_btn_click;
    private RelativeLayout m_rl_main;
    private RecyclerView m_rv_clock;
    private RecyclerViewAdapter mAdapter;
    private int count;
    public static final String BUNDLE_KEY_DELETE_VISIBLE
            = "com.littleblack57.android.com.delete_visible";
    private CheckBox m_checkbox;
    private int m_position;
    private static ClockOptionSelectAdapter m_clockOptionSelectAdapter;
    private static String FILENAME = "Data.java";
    private Context m_context;
    private String uri = null;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        m_rv_clock = (RecyclerView)findViewById(R.id.rv_clock);
        m_checkbox = (CheckBox)m_rv_clock.findViewById(R.id.cb_clock_check);
        m_rv_clock.setHasFixedSize(true);
        m_rv_clock.addItemDecoration(new DividerItemDecoration(MainActivity.this,LinearLayoutManager.VERTICAL));

        if(m_clockOptionSelectAdapter == null){
            restoreData();
            if(m_clockOptionSelectAdapter == null) {
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
            public void OnClick(View view,int position) {

                Intent intent = new Intent(MainActivity.this,Clock_Option.class);
                startActivityForResult(intent,ITEM_CLOCK_SELECT);

                m_position = position;
                m_clockOptionSelectAdapter
                        .setIsChecked(m_clockOptionSelectAdapter.getIsCheckedArrayPosition(position));
                m_clockOptionSelectAdapter.setSelected(m_clockOptionSelectAdapter.getDateSelectedArrayPosition(position));
                m_clockOptionSelectAdapter.setHour(m_clockOptionSelectAdapter.getHourArrayPosition(position));
                m_clockOptionSelectAdapter.setMinute(m_clockOptionSelectAdapter.getMinuteArrayPosition(position));

            }
        });


        m_context = this;


    }





    public void addClock(View view) {

        boolean[] date_selected = new boolean[]{false,false,false,false,false,false,false};

        m_clockOptionSelectAdapter.setIsChecked(false);
        m_clockOptionSelectAdapter.setSelected(date_selected);
        Intent intent = new Intent(MainActivity.this,Clock_Option.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.putExtra(BUNDLE_KEY_DELETE_VISIBLE,false);
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


    public void next(View view) throws FileNotFoundException {

        Log.e("666","m_count = ----->" + String.valueOf(m_clockOptionSelectAdapter.getCount()));
        File file = getDir(FILENAME,MODE_PRIVATE);
        Log.d("666",file.getName());
        Log.d("666",file.getPath());


//        Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
//        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE,RingtoneManager.TYPE_ALARM);
//        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE,"Select tone");
//        if(uri != null){
//
//            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI,Uri.parse(uri));
//
//        }else {
//
//            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI,(Uri)null);
//
//        }
//        startActivityForResult(intent, Ringtone);
//
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode != RESULT_OK){
            return;
        }else {

            Uri uri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
            Log.d("555","123123123"+uri);
            Toast.makeText(m_context,uri+"",Toast.LENGTH_LONG).show();
            if(uri != null){
                switch (requestCode){

                    case Ringtone:
                        RingtoneManager
                                .setActualDefaultRingtoneUri(this,RingtoneManager.TYPE_RINGTONE,uri);
                        Log.d("ok","123123123"+uri);
                        break;

                }
            }

        }


        if(requestCode == CREATE_CLOCK_REQUEST){
            if(resultCode == RESULT_OK){

                Bundle bundle = data.getExtras();
                count = bundle.getInt(Clock_Option.BUNDLE_KEY_RECYCLERVIEW_COUNT);
                if(count == 1) {
                    mAdapter.addItem();
                    int a = m_clockOptionSelectAdapter.getCount();
                    m_clockOptionSelectAdapter.setCount(a + 1);
                    Log.d("666", String.valueOf(m_clockOptionSelectAdapter.getCount()));
                    m_clockOptionSelectAdapter = ClockOptionSelectAdapterFactory.getClockOptionSelectAdapter();
                    m_clockOptionSelectAdapter.addIsCheckedArray();
                    m_clockOptionSelectAdapter.addDateSelectedArray();
                    m_clockOptionSelectAdapter.addHourArray();
                    m_clockOptionSelectAdapter.addMinuteArray();
                    saveData();

                    }

            }




        }else if(requestCode == ITEM_CLOCK_SELECT){
            if(resultCode == RESULT_OK){

                Bundle bundle = data.getExtras();
                count = bundle.getInt(Clock_Option.BUNDLE_KEY_RECYCLERVIEW_COUNT);
                if(count == -1){

                    mAdapter.deleteItem(m_position);
                    m_clockOptionSelectAdapter.setCount((m_clockOptionSelectAdapter.getCount())-1);
                    m_clockOptionSelectAdapter.deleteIsCheckedArrayPosition(m_position);
                    m_clockOptionSelectAdapter.deleteDateSelectedArrayPosition(m_position);
                    m_clockOptionSelectAdapter.deleteHourArray(m_position);
                    m_clockOptionSelectAdapter.deleteMinuteArray(m_position);
                    saveData();

                }else if(count == 1){

                    Boolean ischecked = bundle.getBoolean(Clock_Option.BUMDLE_KEY_CHECKBOX_CHECKED);
                    m_clockOptionSelectAdapter.setIsCheckedArrayPosition(m_position,ischecked);
                    m_clockOptionSelectAdapter.setDateSelectedArrayPosition(m_position,m_clockOptionSelectAdapter.getSelected());
                    m_clockOptionSelectAdapter.setHourArrayPosition(m_position,m_clockOptionSelectAdapter.getHour());
                    m_clockOptionSelectAdapter.setMinuteArrayPosition(m_position,m_clockOptionSelectAdapter.getMinute());
                    mAdapter.notifyItemChanged(m_position);
                    Log.d("ok","m_position = " + String.valueOf(m_position));
                    saveData();

                }

            }
        }

         }



    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        Log.d("666","UI_save");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d("666","UI_restore");
    }

    private void saveData() {

        FileOutputStream fos;
        ObjectOutputStream oos = null;
        try {
            Log.e("666","saveData");
            fos = openFileOutput(FILENAME,Context.MODE_PRIVATE);
            //fos = new FileOutputStream("clockOptionSelect.java");
            oos = new ObjectOutputStream(fos);
            Log.d("666","before saveData count == " + m_clockOptionSelectAdapter.getCount());
            oos.writeInt(m_clockOptionSelectAdapter.getCount());
            oos.writeObject(m_clockOptionSelectAdapter);
            Log.e("666","saveCount = " + String.valueOf(m_clockOptionSelectAdapter.getCount()));




        } catch (java.io.IOException e){

            Log.e("666",e.toString());
            e.printStackTrace();

        } finally {

            if(oos != null){

                try{

                    oos.close();
                    Log.d("666","outputstreamClose!!!");

                } catch (IOException e){

                    Log.e("666",e.toString());

                }

            }

        }


    }

    private void restoreData(){

        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {

            File file = getDir(FILENAME,MODE_PRIVATE);
            fis = openFileInput(FILENAME);
            ois = new ObjectInputStream(fis);
            m_clockOptionSelectAdapter.setCount(ois.readInt());
            m_clockOptionSelectAdapter = (ClockOptionSelectAdapter)ois.readObject();
            Log.e("666","restoreData");
            Log.e("666","restoreCount = " + String.valueOf(m_clockOptionSelectAdapter.getCount()));

        } catch (Exception e){

            Log.d("666",e.toString());
            e.printStackTrace();

        } finally {

            if(ois != null){

                try{

                    ois.close();
                    Log.d("666","inputstreamClose!!!");

                }catch (IOException e){

                    Log.e("666",e.toString());

                }

            }

        }

    }





}
