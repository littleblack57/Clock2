package com.example.littleblack57.clock;

/**
 * Created by littleblack57 on 2016/8/15.
 */
public class ClockOptionSelectAdapterFactory {

    private static ClockOptionSelectAdapter m_clockOptionSelectAdapter;

    private ClockOptionSelectAdapterFactory(){

    }

    public static ClockOptionSelectAdapter getClockOptionSelectAdapter(){

        if (m_clockOptionSelectAdapter == null){

            m_clockOptionSelectAdapter = new ClockOptionSelectAdapter();

        }

        return m_clockOptionSelectAdapter;

    }

    public static void setClockOptionSelectAdapter(ClockOptionSelectAdapter clockOptionSelectAdapter){

         m_clockOptionSelectAdapter = clockOptionSelectAdapter;

    }


}
