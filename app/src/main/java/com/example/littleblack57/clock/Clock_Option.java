package com.example.littleblack57.clock;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class Clock_Option extends AppCompatActivity {

    private ClockOptionRecyclerViewAdapter m_ClockOptionAdapter;
    private RecyclerView m_rv_clock_option;
    private int m_year, m_month, m_day, m_hour, m_minute;
    private TextView m_tv_option_title;
    private TextView m_tv_option_date;
    private TextView m_tv_option_content;
    private Button m_btn_option_delete;
    private TextView m_tv_date;
    private boolean[] m_date_selected;
    private int count = 0;
    public static final String BUNDLE_KEY_RECYCLERVIEW_COUNT
            = "com.littleblack57.android.com.recyclerviewCount";
    public static final String BUMDLE_KEY_CHECKBOX_CHECKED
            = "om.littleblack57.android.com.checkbox_checked";
    public static final String BUNDLE_KET_DATE_SELSECTD
            ="com.littleblack57.android.com.date_selected";
    private CheckBox m_option_checkBox;
    private Boolean m_ischecked;
    private ClockOptionSelectAdapter clockOptionSelectAdapter
            = ClockOptionSelectAdapterFactory.getClockOptionSelectAdapter();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock__option);

        m_rv_clock_option = (RecyclerView) findViewById(R.id.rv_clock_option);
        m_option_checkBox = (CheckBox)m_rv_clock_option.findViewById(R.id.cb_option_checkbox);
        m_btn_option_delete = (Button) findViewById(R.id.btn_option_delete);
        m_tv_date = (TextView)findViewById(R.id.tv_option_date);


        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        m_rv_clock_option.setLayoutManager(llm);

        m_rv_clock_option.setHasFixedSize(true);
        m_rv_clock_option.addItemDecoration(new DividerItemDecoration(Clock_Option.this, LinearLayoutManager.VERTICAL));
        m_ClockOptionAdapter = new ClockOptionRecyclerViewAdapter();
        m_rv_clock_option.setAdapter(m_ClockOptionAdapter);

        m_ClockOptionAdapter.setOnClockOptionRecyclerViewItemClickListener(new ClockOptionRecyclerViewAdapter.OnClockOptionRecyclerViewItemClickListener() {
            @Override
            public void OnClick(View view, int position) {

                switch (position) {
                    case 0 :
                        showTimePickerDialog(view);
                        break;
                    case 1:
                        clickAlertDialogItems(view);
                        break;
                    case 2:

                        break;
                    case 3:
                        setCheckBoxChecked(view);
                        break;
                }

            }
        });



    }



    public void setCheckBoxChecked(View view){

        m_option_checkBox = (CheckBox)view.findViewById(R.id.cb_option_checkbox);
        m_option_checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                clockOptionSelectAdapter = ClockOptionSelectAdapterFactory.getClockOptionSelectAdapter();
                clockOptionSelectAdapter.setIsChecked(isChecked);
                Log.d("ok","CheckBox is checked = " + isChecked);

            }
        });

    }

    private void selectedDateIsChecked(View view){

        boolean[] selected = clockOptionSelectAdapter.getSelected();
        m_tv_date = (TextView)view.findViewById(R.id.tv_option_date);
        m_tv_date.setText("");

            if(selected[0]) {
                        m_tv_date.setVisibility(View.VISIBLE);
                        m_tv_date.setText("一");
                    }

            if (selected[1]){
                        m_tv_date.setVisibility(View.VISIBLE);
                        m_tv_date.append(" 二");
                    }

            if (selected[2]){
                        m_tv_date.setVisibility(View.VISIBLE);
                        m_tv_date.append(" 三");
                    }

            if (selected[3]){
                        m_tv_date.setVisibility(View.VISIBLE);
                        m_tv_date.append(" 四");
                    }

            if (selected[4]){
                        m_tv_date.setVisibility(View.VISIBLE);
                        m_tv_date.append(" 五");
                    }

            if (selected[5]){
                        m_tv_date.setVisibility(View.VISIBLE);
                        m_tv_date.append(" 六");
                    }

            if (selected[6]){
                        m_tv_date.setVisibility(View.VISIBLE);
                        m_tv_date.append(" 日");
                    }


            }








    public void showTimePickerDialog(View view) {

        final Calendar calendar = Calendar.getInstance();
        m_hour = calendar.get(Calendar.HOUR_OF_DAY);
        m_minute = calendar.get(Calendar.MINUTE);
        m_tv_option_content = (TextView) view.findViewById(R.id.tv_option_content);


        TimePickerDialog tpd = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                clockOptionSelectAdapter.setHour(hourOfDay);
                clockOptionSelectAdapter.setMinute(minute);
                m_tv_option_content.setText(clockOptionSelectAdapter.toStringClock());


            }
        }, m_hour, m_minute, false);
        tpd.show();


    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    public void showDatePickerDialog(View view) {

        final Calendar calendar = Calendar.getInstance();
        m_year = calendar.get(Calendar.YEAR);
        m_month = calendar.get(Calendar.MONTH);
        m_day = calendar.get(Calendar.DAY_OF_MONTH);
        m_tv_option_date = (TextView) view.findViewById(R.id.tv_option_content);

        DatePickerDialog dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {



                m_tv_option_date.setText(monthOfYear + "/" + dayOfMonth);


            }
        }, m_year, m_month, m_day);

        dpd.show();
    }







    public void option_ok(View view) {


        ++count;
        final Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra(BUNDLE_KEY_RECYCLERVIEW_COUNT,count);
        ClockOptionSelectAdapter clockOptionSelectAdapter
                = ClockOptionSelectAdapterFactory.getClockOptionSelectAdapter();
        m_ischecked = clockOptionSelectAdapter.getIsChecked();
        m_date_selected = clockOptionSelectAdapter.getSelected();
        Log.d("ok","Intent IsChecked = " + m_ischecked);
        intent.putExtra(BUMDLE_KEY_CHECKBOX_CHECKED,m_ischecked);
        intent.putExtra(BUNDLE_KET_DATE_SELSECTD,m_date_selected);
        setResult(RESULT_OK,intent);
        Log.d("ok","Intent finish = " + m_ischecked);
        finish();

    }




    public void option_cancel(View view) {

        setResult(RESULT_CANCELED);
        finish();


    }

    public void option_delete(View view) {
        --count;
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra(BUNDLE_KEY_RECYCLERVIEW_COUNT,count);
        setResult(RESULT_OK,intent);
        Log.d("ok",String.valueOf(count));
        finish();


    }

    public void clickAlertDialogItems(final View view) {
        final String[] day_of_week = getResources().getStringArray(R.array.day_of_week);
        final boolean[] selected = clockOptionSelectAdapter.getSelected();
        //final boolean[] onclick_selected = new boolean[day_of_week.length];
        new AlertDialog.Builder(this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar)
                .setTitle("重複")
                .setMultiChoiceItems(day_of_week,selected, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                         selected[which] = isChecked;
                    }
                })
                .setNegativeButton("cancle",new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {



                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        clockOptionSelectAdapter.setSelected(selected);
                        selectedDateIsChecked(view);


                    }
                })

        .show();
    }



}

