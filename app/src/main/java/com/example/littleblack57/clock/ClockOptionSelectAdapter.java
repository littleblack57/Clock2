package com.example.littleblack57.clock;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by littleblack57 on 2016/8/15.
 */
public class ClockOptionSelectAdapter implements Serializable{

    private boolean m_ischecked = false;
    private ArrayList<Boolean> m_ischecked_array = new ArrayList<>();
    private int m_hour,m_minute;
    private boolean[] m_selected = new boolean[7];
    private ArrayList<Integer> m_hour_array = new ArrayList<>();
    private ArrayList<Integer> m_minute_array = new ArrayList<>();
    private ArrayList<boolean[]> m_dateselected_array = new ArrayList<>();
    private int m_count;





    public int getCount(){

        return m_count;
    }

    public void setCount(int count){

        this.m_count = count;

    }

    public boolean[] getSelected() {
        return m_selected;
    }

    public void setSelected(boolean[] selected) {
        this.m_selected = selected;
        Log.d("ok","DateOptionSet = " + m_dateselected_array);
    }

    public int getHour() {
        return m_hour;
    }

    public void setHour(int m_hour) {
        this.m_hour = m_hour;
    }

    public int getMinute() {
        return m_minute;
    }

    public String toStringMainClock(int position){

        return toStringMainHour(getHourArrayPosition(position)) + " : "
                + toStringMainMinute(getMinuteArrayPosition(position));

    }

    public String toStringClock(){

        return toStringHour() + " : "
                + toStringMinute();

    }

    public String toStringMainHour(int hour){

        if(hour < 10){

            String hour_string = "0" + String.valueOf(hour);
            return hour_string;

        }

        return String.valueOf(m_hour);
    }

    public String toStringMainMinute(int minute){

        if(minute < 10){

            return "0" +  String.valueOf(minute);

        }

        return String.valueOf(minute);
    }

    public String toStringHour(){

        if(m_hour < 10){

            String hour_string = "0" + String.valueOf(m_hour);
            return hour_string;

        }

        return String.valueOf(m_hour);
    }

    public String toStringMinute(){

        if(m_minute < 10){

            return "0" +  String.valueOf(m_minute);

        }

        return String.valueOf(m_minute);
    }

    public void setMinute(int m_minute) {
        this.m_minute = m_minute;
    }



    public Boolean getIsChecked(){

        return m_ischecked;

    }

    public void setIsChecked(Boolean ischecked){

        this.m_ischecked = ischecked;

    }

    public void addIsCheckedArray(){

        m_ischecked_array.add(m_ischecked);
        Log.d("ok","Array = " + m_ischecked_array);

    }

    public void deleteIsCheckedArrayPosition(int position){

        m_ischecked_array.remove(position);
        Log.d("ok","Array = " + m_ischecked_array);

    }

    public boolean getIsCheckedArrayPosition(int position){

        return m_ischecked_array.get(position);

    }

    public void setIsCheckedArrayPosition(int position, boolean isChecked){

        m_ischecked_array.remove(position);
        m_ischecked_array.add(position,isChecked);

    }

    public void setDateSelectedArrayPosition(int position,boolean[] ischecked_array){

        m_dateselected_array.remove(position);
        m_dateselected_array.add(position,ischecked_array);

    }

    public boolean[] getDateSelectedArrayPosition(int position){

        return m_dateselected_array.get(position);

    }

    public void addDateSelectedArray(){

        m_dateselected_array.add(m_selected);
        Log.d("ok","dateselected_array = " + m_dateselected_array);

    }

    public void deleteDateSelectedArrayPosition(int position){

        m_dateselected_array.remove(position);

    }

    public void addHourArray(){

        m_hour_array.add(m_hour);

    }

    public void deleteHourArray(int position){

        m_hour_array.remove(position);

    }

    public void setHourArrayPosition(int position,int hour){

        m_hour_array.remove(position);
        m_hour_array.add(position,hour);

    }

    public Integer getHourArrayPosition(int position){

        return m_hour_array.get(position);

    }

    public void addMinuteArray(){

        m_minute_array.add(m_minute);

    }

    public void deleteMinuteArray(int position){

        m_minute_array.remove(position);

    }

    public void setMinuteArrayPosition(int position,int minute){

        m_minute_array.remove(position);
        m_minute_array.add(position,minute);

    }

    public Integer getMinuteArrayPosition(int position){

        return m_minute_array.get(position);

    }



}
