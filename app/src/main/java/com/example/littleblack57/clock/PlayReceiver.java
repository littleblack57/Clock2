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
    public static final String BUNDLE_KEY_RINGTONETITLEURI
            ="com.littleblack57.android.com.ringtone_title_uri";
    public static final String BUNDLE_KEY_VIBRATE
            = "com.littleblack57.android.com.vibrate";


    @Override
    public void onReceive(Context context, Intent intent) {



        Bundle bundle = intent.getExtras();

        Log.d("123456","ring ring");

        if(bundle.get("msg").equals("ring")) {
            String ringtonetitle_uri = bundle.getString(MainActivity.BUNDLE_KEY_RINGTONETITLEURI);
            boolean b = bundle.getBoolean(MainActivity.BUNDLE_KEY_VIBRATE);
            intent.putExtra(BUNDLE_KEY_RINGTONETITLEURI,ringtonetitle_uri);
            intent.putExtra(BUNDLE_KEY_VIBRATE,b);
            intent.setClass(context,Answer_Question.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
            context.startActivity(intent);
            Log.d(TAG,"Success");

        }
    }

    }

