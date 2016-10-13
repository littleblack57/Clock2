package com.example.littleblack57.clock;

/**
 * Created by littleblack57 on 2016/8/15.
 */
public class ClockOptionSelectAdapterFactory {

    private static ClockOptionSelectAdapter clockOptionSelectAdapter;

    private ClockOptionSelectAdapterFactory(){

    }

    public static ClockOptionSelectAdapter getClockOptionSelectAdapter(){

        if (clockOptionSelectAdapter == null){

            clockOptionSelectAdapter = new ClockOptionSelectAdapter();

        }

        return clockOptionSelectAdapter;

    }

}
