package com.example.littleblack57.clock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by littleblack57 on 2016/7/22.
 */
public class PlayReceiver extends BroadcastReceiver {

    private final String TAG = "ok";



    @Override
    public void onReceive(Context context, Intent intent) {



        Bundle bundle = intent.getExtras();



        if(bundle.get("msg").equals("ring")) {

            intent.setClass(context,Answer_Question.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
            context.startActivity(intent);
            Log.d(TAG,"Success");

        }
    }

    }

