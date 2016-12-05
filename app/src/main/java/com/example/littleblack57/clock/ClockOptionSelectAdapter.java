package com.example.littleblack57.clock;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by littleblack57 on 2016/8/15.
 */
public class ClockOptionSelectAdapter implements Serializable {

    private boolean m_ischecked = false;
    private ArrayList<Boolean> m_ischecked_array = new ArrayList<>();
    private int m_hour, m_minute;
    private boolean[] m_selected = new boolean[7];
    private boolean m_clockpower;
    private ArrayList<Boolean> m_clockpower_array = new ArrayList<>();
    private ArrayList<Integer> m_hour_array = new ArrayList<>();
    private ArrayList<Integer> m_minute_array = new ArrayList<>();
    private ArrayList<boolean[]> m_dateselected_array = new ArrayList<>();
    private int m_count;
    private ArrayList<Integer> m_id_array = new ArrayList<>();
    private int m_id;
    private int[] m_id_group = new int[7];
    private ArrayList<int[]> m_id_group_array = new ArrayList<>();
    private String m_ringtone_title;
    private ArrayList<String> m_ringtone_title_array = new ArrayList<>();
    private boolean m_button_visibilty;
    private String m_ringtone_uri;
    private ArrayList<String> m_ringtone_uri_array = new ArrayList<>();



    public void setClockPower(boolean b){

        this.m_clockpower = b;

    }

    public boolean getClockPower(){

        return m_clockpower;

    }

    public void addClockPowerArray(){

        m_clockpower_array.add(m_clockpower);

    }

    public boolean getClockPowerArrayPosition(int index){

        return m_clockpower_array.get(index);

    }

    public void setClockPowerArrayPosition(int index , boolean b){

        m_clockpower_array.remove(index);
        m_clockpower_array.add(index,b);

    }

    public void deleteClockPowerArrayPosition(int index){

        m_clockpower_array.remove(index);

    }

    public String getRingtoneUriString(){

        return m_ringtone_uri;

    }

    public void setRingtoneUriString(String ringtone_uri){

        this.m_ringtone_uri = ringtone_uri;

    }

    public String getRingtoneUriStringArrayPosition(int index){

        return m_ringtone_uri_array.get(index);

    }

    public void setRingtoneUriStringArrayPosition(int index){

        m_ringtone_uri_array.remove(index);
        m_ringtone_uri_array.add(index,m_ringtone_uri);

    }

    public void addRingtoneUriStringArray(){

        m_ringtone_uri_array.add(m_ringtone_uri);

    }

    public void deleteRingtoneUriStringArrayPostion(int index){

        m_ringtone_uri_array.remove(index);

    }

    public void setButtonVisibilty(boolean a){

        this.m_button_visibilty = a;

    }

    public boolean getButtonVisibilty(){

        return m_button_visibilty;

    }

    public void setRingtoneTitle(String ringtonetitle){

        this.m_ringtone_title = ringtonetitle;

    }

    public String getRingtoneTitle(){

        return m_ringtone_title;

    }

    public void addRingtoneTitleArray(){

        m_ringtone_title_array.add(m_ringtone_title);

    }

    public void setRingtoneTitleArray(int index){

        m_ringtone_title_array.remove(index);
        m_ringtone_title_array.add(index, m_ringtone_title);

    }

    public String getRingtoneTitleArrayPosition(int index){

        return m_ringtone_title_array.get(index);

    }

    public void deleteRingtoneTitleArrayPosition(int index){

        m_ringtone_title_array.remove(index);

    }

    public int[] getIdGroupArray(int index) {
        return m_id_group_array.get(index);
    }

    public void setIdGroupArray(int index, int[] id_group) {
        m_id_group_array.remove(index);
        m_id_group_array.add(index, id_group);
    }

    public void addIdGroupArray() {

        m_id_group_array.add(m_id_group);

    }


    public int[] getIdGroup() {
        return m_id_group;
    }

    public void setIdGroup(int[] id_group) {
        this.m_id_group = id_group;
    }

    public int getId() {


        m_id = (int) (Math.random() * 100);
        if (m_id_array.size() > 0) {
            for (int x : m_id_array) {

                while (x == m_id) {

                    m_id = (int) (Math.random() * 100);

                }

            }
        }

        return m_id;

    }

    public void setIdArray(int id) {

        m_id_array.add(id);

    }

    public int getIdArrayPosition(int index) {

        m_id = m_id_array.get(index);

        return m_id;

    }

    public void removeIdArrayPosition(int index) {

        m_id_array.remove(index);

    }

    public int getCount() {

        return m_count;
    }

    public void setCount(int count) {

        this.m_count = count;

    }

    public boolean[] getSelected() {
        return m_selected;
    }

    public void setSelected(boolean[] selected) {
        this.m_selected = selected;
        Log.d("ok", "DateOptionSet = " + m_dateselected_array);
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

    public void setMinute(int m_minute) {
        this.m_minute = m_minute;
    }

    public String toStringMainClock(int position) {

        return toStringMainHour(getHourArrayPosition(position)) + " : "
                + toStringMainMinute(getMinuteArrayPosition(position));

    }

    public String toStringClock() {

        return toStringHour() + " : "
                + toStringMinute();

    }

    public String toStringMainHour(int hour) {

        if (hour < 10) {

            String hour_string = "0" + String.valueOf(hour);
            return hour_string;

        }

        return String.valueOf(m_hour);
    }

    public String toStringMainMinute(int minute) {

        if (minute < 10) {

            return "0" + String.valueOf(minute);

        }

        return String.valueOf(minute);
    }

    public String toStringHour() {

        if (m_hour < 10) {

            String hour_string = "0" + String.valueOf(m_hour);
            return hour_string;

        }

        return String.valueOf(m_hour);
    }

    public String toStringMinute() {

        if (m_minute < 10) {

            return "0" + String.valueOf(m_minute);

        }

        return String.valueOf(m_minute);
    }

    public Boolean getIsChecked() {

        return m_ischecked;

    }

    public void setIsChecked(Boolean ischecked) {

        this.m_ischecked = ischecked;

    }

    public void addIsCheckedArray() {

        m_ischecked_array.add(m_ischecked);

    }

    public void deleteIsCheckedArrayPosition(int position) {

        m_ischecked_array.remove(position);

    }

    public boolean getIsCheckedArrayPosition(int position) {

        return m_ischecked_array.get(position);

    }

    public void setIsCheckedArrayPosition(int position, boolean isChecked) {

        m_ischecked_array.remove(position);
        m_ischecked_array.add(position, isChecked);

    }

    public void setDateSelectedArrayPosition(int position, boolean[] ischecked_array) {

        m_dateselected_array.remove(position);
        m_dateselected_array.add(position, ischecked_array);

    }

    public boolean[] getDateSelectedArrayPosition(int position) {

        return m_dateselected_array.get(position);

    }

    public void addDateSelectedArray() {

        m_dateselected_array.add(m_selected);
        Log.d("ok", "dateselected_array = " + m_dateselected_array);

    }

    public void deleteDateSelectedArrayPosition(int position) {

        m_dateselected_array.remove(position);

    }

    public ArrayList<boolean[]> getDateSelectedArray(){

        return m_dateselected_array;

    }

    public void addHourArray() {

        m_hour_array.add(m_hour);

    }

    public void deleteHourArray(int position) {

        m_hour_array.remove(position);

    }

    public void setHourArrayPosition(int position, int hour) {

        m_hour_array.remove(position);
        m_hour_array.add(position, hour);

    }

    public Integer getHourArrayPosition(int position) {

        return m_hour_array.get(position);

    }

    public void addMinuteArray() {

        m_minute_array.add(m_minute);

    }

    public void deleteMinuteArray(int position) {

        m_minute_array.remove(position);

    }

    public void setMinuteArrayPosition(int position, int minute) {

        m_minute_array.remove(position);
        m_minute_array.add(position, minute);

    }

    public Integer getMinuteArrayPosition(int position) {

        return m_minute_array.get(position);

    }


}
