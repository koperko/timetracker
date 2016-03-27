package com.koper.timetracker;

import android.app.Application;
import android.content.Context;

import com.activeandroid.ActiveAndroid;

/**
 * Created by Matus on 07.02.2016.
 */
public class TrackingApplication extends Application {


    private static Context fAppContext;

    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
        fAppContext = getApplicationContext();
//        TrackingService.initializeService(getApplicationContext());
    }

    public static Context getAppContext(){
        return fAppContext;
    }

}
