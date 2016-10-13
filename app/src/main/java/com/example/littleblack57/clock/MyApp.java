package com.example.littleblack57.clock;

import android.app.Application;
import android.content.Context;

/**
 * Created by littleblack57 on 2016/10/7.
 */

public class MyApp extends Application {

    private static Context m_context;
    private static ClockOptionSelectAdapter m_clockOptionSelectAdapter;


    public static Context getContext(){

        return m_context;

    }

    public static ClockOptionSelectAdapter getClockoptionSelectAdapter(){

        return m_clockOptionSelectAdapter;

    }

    public static void setClockoptionSelectAdapter
            (ClockOptionSelectAdapter clockoptionSelectAdapter){

        MyApp.m_clockOptionSelectAdapter = clockoptionSelectAdapter;

    }

    public MyApp(){

        m_context = this;

    }


}
